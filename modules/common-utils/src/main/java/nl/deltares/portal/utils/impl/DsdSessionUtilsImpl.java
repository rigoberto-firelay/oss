package nl.deltares.portal.utils.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import nl.deltares.dsd.registration.service.RegistrationLocalService;
import nl.deltares.portal.exception.ValidationException;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.model.impl.Event;
import nl.deltares.portal.model.impl.Registration;
import nl.deltares.portal.model.impl.SessionRegistration;
import nl.deltares.portal.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component(
        immediate = true,
        service = DsdSessionUtils.class
)
public class DsdSessionUtilsImpl implements DsdSessionUtils {

    private final long dayMillis = TimeUnit.DAYS.toMillis(1);

    @Reference
    KeycloakUtils keycloakUtils;

    @Reference
    GotoUtils gotoUtils;

    @Reference
    DsdParserUtils parserUtils;

    @Reference
    DsdJournalArticleUtils dsdJournalArticleUtils;

    private RegistrationLocalService registrationLocalService;

    @Reference(unbind = "-")
    private void setRepositoryLogLocalService(RegistrationLocalService registrationLocalService) {

        this.registrationLocalService = registrationLocalService;
    }
    @Override
    public void deleteRegistrationsFor(Registration registration) {
        registrationLocalService.deleteAllRegistrationsAndChildRegistrations(registration.getGroupId(), registration.getResourceId());
    }

    @Override
    public void registerUser(User user, Registration registration, Map<String, String> userProperties) throws PortalException {

        if (gotoUtils.isGotoMeeting(registration)){
            registerGotoUser(user, (SessionRegistration) registration, userProperties);
        }
        long parentId = registration.getParentRegistration() == null ? 0 : registration.getParentRegistration().getResourceId();
        registrationLocalService.addUserRegistration(
                registration.getCompanyId(), registration.getGroupId(), registration.getResourceId(),
                parentId, user.getUserId(),
                registration.getStartTime(), registration.getEndTime(), JsonContentUtils.formatMapToJson(userProperties));
    }

    private void registerGotoUser(User user, SessionRegistration registration, Map<String, String> userProperties) throws PortalException {
        try {
            Map<String, String> responseValues = gotoUtils.registerUser(user, registration.getWebinarKey(), GroupServiceUtil.getGroup(registration.getGroupId()).getName());
            userProperties.put("registrantKey", responseValues.get("registrantKey"));
            userProperties.put("joinUrl", responseValues.get("joinUrl"));
        } catch (Exception e) {
            throw new PortalException(e);
        }
    }

    @Override
    public void unRegisterUser(User user, Registration registration) throws PortalException {

        if (gotoUtils.isGotoMeeting(registration)){
            unRegisterGotUser(user, registration);
        }

        registrationLocalService.deleteUserRegistrationAndChildRegistrations(
                registration.getGroupId(), registration.getResourceId(), user.getUserId());
    }

    private void unRegisterGotUser(User user, Registration registration) throws PortalException {
        List<nl.deltares.dsd.registration.model.Registration> registrations = registrationLocalService.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        if (registrations.size() > 0){
            Map<String, String> preferences = getUserPreferencesMap(registrations.get(0));
            String registrantKey = preferences.get("registrantKey");
            if (registrantKey == null){
                throw new PortalException(String.format("No user registrantKey for user %s and registration %s", user.getEmailAddress(), registration.getTitle()));
            }

            try {
                gotoUtils.unregisterUser(registrantKey, ((SessionRegistration) registration).getWebinarKey());
            } catch (Exception e) {
                throw new PortalException(e);
            }
        }
    }

    private Map<String, String> getUserPreferencesMap(nl.deltares.dsd.registration.model.Registration dbRegistration) throws PortalException {
        String userPreferences = dbRegistration.getUserPreferences();
        if (userPreferences == null){
            return Collections.emptyMap();
        }
        return JsonContentUtils.parseJsonToMap(userPreferences);
    }

    @Override
    public void validateRegistrations(User user, List<Registration> registrations) throws PortalException {
        //checks registrations in list
        double maxPrice = 0;
        for (Registration registration : registrations) {
            if (!registration.isOpen()) {
                throw new ValidationException(String.format("Registration %s is not open!", registration.getTitle()));
            }
            if (registration.getPrice() > maxPrice) {
                maxPrice = registration.getPrice();
            }
        }
        List<String> missingInfo = getMissingUserInformation(user, maxPrice);
        if (missingInfo.size() > 0) {
            throw new ValidationException("Missing user data for following fields: " + Arrays.toString(missingInfo.toArray()));
        }
        List<Registration> overlapping = checkIfRegistrationsOverlap(registrations);
        if (overlapping.size() > 0){
            StringBuilder titles = new StringBuilder();
            overlapping.forEach(registration -> {titles.append(registration.getTitle()); titles.append(", ");});
            throw new ValidationException("Overlapping periods found for registrations: " + titles.toString());
        }

        //check registrations in database
        for (Registration registration : registrations) {
            dbValidationChecks(user, registration);
        }
    }

    private void dbValidationChecks(User user, Registration registration) throws PortalException {
        if (isUserRegisteredFor(user, registration)) {
            throw new ValidationException(String.format("User already registered for %s !", registration.getTitle()));
        }

        if (registration.getCapacity() != Integer.MAX_VALUE && getRegistrationCount(registration) >= registration.getCapacity()) {
            throw new ValidationException(String.format("Registration %s is full!", registration.getTitle()));
        }

        long[] overlappingRegistrationIds = getOverlappingRegistrationIds(user, registration);
        if (overlappingRegistrationIds.length > 0) {
            throw new ValidationException(String.format("Registration period for %s overlaps with other existing registrations: %s",
                    registration.getTitle(), Arrays.toString(getTitles(registration.getGroupId(), overlappingRegistrationIds))));
        }

        if (registration.getParentRegistration() != null && !isUserRegisteredFor(user, registration.getParentRegistration())) {
            throw new ValidationException("User not registered for required parent registration: " + registration.getParentRegistration().getTitle());
        }
    }

    private String[] getTitles(long groupId, long[] articleIds) {
        String[] titles = new String[articleIds.length];
        for (int i = 0; i < articleIds.length; i++) {
            try {
                JournalArticle journalArticle = dsdJournalArticleUtils.getJournalArticle(groupId, String.valueOf(articleIds[i]));
                titles[i] = journalArticle == null ? String.valueOf(articleIds[i]) : journalArticle.getTitle();
            } catch (PortalException e) {
                titles[i] = String.valueOf(articleIds[i]);
            }
        }
        return titles;
    }

    public List<Registration> getChildRegistrations(Registration registration) throws PortalException {
        Event event = parserUtils.getEvent(registration.getGroupId(), String.valueOf(registration.getEventId()));

        List<Registration> registrations = event.getRegistrations();
        ArrayList<Registration> children = new ArrayList<>();
        for (Registration eventRegistration : registrations) {
            if (eventRegistration.getParentRegistration() != null && eventRegistration.getParentRegistration().getResourceId() == registration.getResourceId()) {
                children.add(eventRegistration);
            }
        }
        return children;
    }

    @Override
    public Map<String, String> getUserPreferences(User user, Registration registration) throws PortalException {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations =
                registrationLocalService.getRegistrations(registration.getGroupId(), user.getUserId(), registration.getResourceId());

        for (nl.deltares.dsd.registration.model.Registration dbRegistration : dbRegistrations) {
            return getUserPreferencesMap(dbRegistration);
        }
        return Collections.emptyMap();
    }

    @Override
    public int getRegistrationCount(Registration registration) {
        return registrationLocalService.getRegistrationsCount(registration.getGroupId(), registration.getResourceId());
    }

    private List<Registration> checkIfRegistrationsOverlap(List<Registration> registrations){

        ArrayList<Registration> overlapping = new ArrayList<>();
        Registration[] list = registrations.toArray(new Registration[0]);
        for (Registration reg1 : list) {
            registrations.forEach(registration -> {
                if (registration == reg1) return;
                if (registration.getParentRegistration() == reg1 && registration.isOverlapWithParent()) return;
                if (reg1.getParentRegistration() == registration && reg1.isOverlapWithParent()) return;
                if (periodsOverlap(reg1, registration)){
                    if (!overlapping.contains(reg1)) overlapping.add(reg1);
                }
            });

        }
        return overlapping;
    }

    private boolean periodsOverlap(Registration reg1, Registration reg2) {

        long reg1Start = reg1.getStartTime().getTime();
        long reg2Start = reg2.getStartTime().getTime();
        long reg1End = reg1.getEndTime().getTime();
        long reg2End = reg2.getEndTime().getTime();
        return
                (reg1Start <= reg2Start && reg1End >= reg2Start) ||
                        (reg1Start <= reg2End && reg1End >= reg2End) ||
                        (reg2Start <= reg1Start && reg2End >= reg1Start) ||
                        (reg2Start <= reg1End && reg2End >= reg1End);

    }

    private long[] getOverlappingRegistrationIds(User user, Registration registration) throws PortalException {
        long[] registrationsWithOverlappingPeriod = registrationLocalService.getRegistrationsWithOverlappingPeriod(registration.getGroupId(), user.getUserId(),
                registration.getStartTime(), registration.getEndTime());

        /*
         * Some parallel sessions can overlap with their parent session. These need to be removed.
         */
        long parentId = registration.getParentRegistration() == null ? -1 : registration.getParentRegistration().getResourceId();
        boolean overlapWithParent = registration.isOverlapWithParent();

        for (int i = 0; i < registrationsWithOverlappingPeriod.length; i++) {
            //skip if child can overlap with parent session
            if (registrationsWithOverlappingPeriod[i] == parentId && overlapWithParent) {
                registrationsWithOverlappingPeriod[i] = 0;
            //skip bus transfers and other registrations that can always overlap
            } else if (canOverlap(registrationsWithOverlappingPeriod[i], registration.getArticleId())) {
                registrationsWithOverlappingPeriod[i] = 0;
            }
        }
        return ArrayUtil.remove(registrationsWithOverlappingPeriod, 0);
    }

    private boolean canOverlap(long overlappingResourcePrimaryKey, String validatingArticleId) throws PortalException {
        Registration overlappingRegistration = parserUtils.getRegistration(JournalArticleLocalServiceUtil.fetchLatestArticle(overlappingResourcePrimaryKey));
        return overlappingRegistration.isOverlapWithParent() &&
                (overlappingRegistration.getParentRegistration() == null || overlappingRegistration.getParentRegistration().getArticleId().equals(validatingArticleId));
    }

    @Override
    public List<String> getMissingUserInformation(User user, double price) throws PortalException {

        ArrayList<String> missingInfo = new ArrayList<>();
        if (user.getFirstName() == null) {
            missingInfo.add("First name");
        }
        if (user.getLastName() == null) {
            missingInfo.add("Last name");
        }

        Map<String, String> userAttributes;
        try {
            userAttributes = keycloakUtils.getUserAttributes(user.getEmailAddress());
        } catch (Exception e) {
            throw new PortalException(e);
        }
        for (DsdArticle.DSD_REQUIRED_REGISTRATION_ATTRIBUTES value : DsdArticle.DSD_REQUIRED_REGISTRATION_ATTRIBUTES.values()) {
            if (userAttributes.containsKey(value.name())) continue;
            missingInfo.add(value.name());
        }

        if (price > 0) {
            for (DsdArticle.DSD_REQUIRED_PAID_REGISTRATION_ATTRIBUTES value : DsdArticle.DSD_REQUIRED_PAID_REGISTRATION_ATTRIBUTES.values()) {
                if (userAttributes.containsKey(value.name())) continue;
                missingInfo.add(value.name());
            }
        }

        return missingInfo;

    }

    @Override
    public boolean isUserRegisteredFor(User user, Registration registration) {
        int registrationsCount = registrationLocalService.getRegistrationsCount(registration.getGroupId(), user.getUserId(), registration.getResourceId());
        return registrationsCount > 0;
    }

    @Override
    public List<Map<String, Object>> getUserRegistrations(User user, Event event) {
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations = registrationLocalService.getUserRegistrations(event.getGroupId(), user.getUserId(), event.getStartTime(), event.getEndTime());
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }

    @Override
    public List<Map<String, Object>> getRegistrations(Event event) {
        //extend search window to include bustransfers
        List<nl.deltares.dsd.registration.model.Registration> dbRegistrations = registrationLocalService.getRegistrations(event.getGroupId(),
                new Date(event.getStartTime().getTime() - dayMillis), new Date(event.getEndTime().getTime()+ dayMillis));
        List<Map<String, Object>> registrations = new ArrayList<>();
        dbRegistrations.forEach(dbRegistration -> registrations.add(dbRegistration.getModelAttributes()));
        return registrations;
    }
}