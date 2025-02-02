package nl.deltares.portal.utils.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.message.boards.exception.NoSuchMailingListException;
import com.liferay.message.boards.kernel.service.MBThreadFlagLocalServiceUtil;
import com.liferay.message.boards.model.*;
import com.liferay.message.boards.service.*;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import nl.deltares.portal.utils.AdminUtils;
import nl.deltares.portal.utils.KeycloakUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component(
        immediate = true,
        service = AdminUtils.class
)
public class OssAdminUtils implements AdminUtils {

    @Reference
    KeycloakUtils keycloakUtils;

    public KeycloakUtils getKeycloakUtils() {
        return keycloakUtils;
    }

    @Override
    public void deleteUserRelatedContent(long siteId, User user, PrintWriter writer) {

        String screenName = user.getScreenName();
        String email = user.getEmailAddress();
        long userId = user.getUserId();
        writer.printf("********** Start deleting content for user %s (%s, %d) in site %d, company %d  ***********\n", screenName, email, userId, siteId, user.getCompanyId());

        //Remove user message for group
        deleteMBMessages(writer, userId, siteId);

        //remove thread flags for user
        deleteThreadFlags(writer, userId);

        //remove user asset entries for site
        writer.printf("Deleting asset entries for groupId '%s':\n", siteId);
        deleteAssetEntries(writer, userId, siteId);

        try {
            writer.printf("Deleting asset entries for groupId 'Control Panel':\n");
            Group controlPanelGroup = GroupLocalServiceUtil.getGroup(user.getCompanyId(), "Control Panel");
            deleteAssetEntries(writer, userId, controlPanelGroup.getGroupId());
        } catch (PortalException e) {
            writer.printf("Could not find group 'Control Panel': %s\n", e.getMessage());
        }

        deleteAssetTags(writer, userId, siteId);

        deleteRemainingMBFolders(writer, userId, siteId);

        writer.printf("********** Finished deleting content for user %s (%s, %d) in site %d, company %d   ***********\n", screenName, email, userId, siteId, user.getCompanyId());
    }

    private void deleteUserGroups(PrintWriter writer,long userId) {
        try {
            final List<Group> userGroups = GroupLocalServiceUtil.getUserGroups(userId);
            writer.printf("Deleting %d User Groups\n", userGroups.size());
            GroupLocalServiceUtil.deleteUserGroups(userId, userGroups);
        } catch (Exception e) {
            writer.printf("Could not delete user Groups: %s\n", e.getMessage());
        }
    }

    @Override
    public User getOrCreateRegistrationUser(long companyId, User loggedInUser, String registrationEmail,
                                            String firstName, String lastName, Locale locale) throws Exception {

        if (registrationEmail == null || registrationEmail.isEmpty()) {
            throw new IllegalArgumentException("Registration email missing");
        }
        final User registrationUser = UserLocalServiceUtil.fetchUserByEmailAddress(companyId, registrationEmail);
        if (registrationUser != null) return registrationUser; //user already exists.

        final Map<String, String> keycloakUser = keycloakUtils.getUserInfo(registrationEmail);
        String userName = null;
        if (keycloakUser.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                String testUserName = KeycloakUtils.extractUsernameFromEmail(registrationEmail, i);
                if (!keycloakUtils.isExistingUsername(testUserName)) {
                    //do not create user, instead check if username is taken.
                    userName = testUserName;
                    break;
                }
            }
        } else {
            userName = keycloakUser.get("username");
        }
        long id = CounterLocalServiceUtil.increment(User.class.getName());
        if (userName == null) {
            userName = String.valueOf(id); //let's assume that id is unieque
        }

        final ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(loggedInUser.getGroupId());
        final Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(loggedInUser.getGroupId());
        final User user = UserLocalServiceUtil.addUser(loggedInUser.getUserId(), companyId, true,
                null, null, false, userName, registrationEmail, 0, null,
                locale, firstName, null, lastName, 0, 0, true,
                1, 1, 1970, null, loggedInUser.getGroupIds(),
                loggedInUser.getOrganizationIds(), new long[]{defaultGroupRole.getRoleId()}, loggedInUser.getUserGroupIds(), false, serviceContext);
        user.setPasswordReset(false);
        UserLocalServiceUtil.updateUser(user);
        return user;

    }

    public void deleteLiferayUser(User user, PrintWriter writer) {
        deleteUserPortrait(writer, user);

        deletePortalPreferences(writer, user.getUserId());

        deleteNotificationDeliveries(writer, user.getUserId());

        try {
            UserLocalServiceUtil.deleteUser(user);
            writer.printf("Deleted user from liferay\n");
        } catch (Exception e) {
            writer.printf("Failed to delete user from Liferay: %s\n", e.getMessage());
        }
    }

    private void deleteNotificationDeliveries(PrintWriter writer, long userId) {
        writer.println("Deleting notification deliveries:");
        //four option types
        deleteNotificationDelivery(writer, userId, 0, 10000);
        deleteNotificationDelivery(writer, userId, 1, 10000);
        deleteNotificationDelivery(writer, userId, 0, 10002);
        deleteNotificationDelivery(writer, userId, 1, 10002);

    }

    private void deleteNotificationDelivery(PrintWriter writer, long userId, int notificationType, int deliveryType) {
        try {
            UserNotificationDelivery delivery = UserNotificationDeliveryLocalServiceUtil.fetchUserNotificationDelivery(
                    userId, "com_liferay_message_boards_web_portlet_MBPortlet", 0, notificationType, deliveryType);
            if (delivery != null) {
                try {
                    UserNotificationDeliveryLocalServiceUtil.deleteUserNotificationDelivery(delivery.getUserNotificationDeliveryId());
                    writer.printf("-Deleted user notification delivery %s\n", delivery.getUserNotificationDeliveryId());
                } catch (Exception e) {
                    writer.printf("-Failed to delete user notification delivery %d: %s\n", delivery.getUserNotificationDeliveryId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            writer.printf("-Failed to get user notification delivery: %s\n", e.getMessage());
        }
    }

    private void deletePortalPreferences(PrintWriter writer, long userId) {
        writer.println("Deleting portal preferences:");
        try {
            PortalPreferences preferences = PortalPreferencesLocalServiceUtil.fetchPortalPreferences(userId, 4);
            if (preferences != null) {
                try {
                    PortalPreferencesLocalServiceUtil.deletePortalPreferences(preferences);
                    writer.printf("-Deleted portal preferences %d\n", preferences.getPortalPreferencesId());
                } catch (Exception e) {
                    writer.printf("-Failed to delete portal preferences %d: %s\n", preferences.getPortalPreferencesId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            writer.printf("-Failed to get user preferences: %s\n", e.getMessage());
        }
    }

    private void deleteRemainingMBFolders(PrintWriter writer, long userId, long siteId) {
        writer.println("Deleting remaining MBFolders:");
        try {
            Repository tempFileRepository = RepositoryLocalServiceUtil.getRepository(siteId, TempFileEntryUtil.class.getName(), null);
            List<DLFolder> repositoryFolders = DLFolderLocalServiceUtil.getRepositoryFolders(tempFileRepository.getRepositoryId(), 0, 1000);
            repositoryFolders.forEach(dlFolder -> {
                if (dlFolder.getUserId() == userId) {
                    try {
                        DLFolderLocalServiceUtil.deleteDLFolder(dlFolder.getFolderId());
                        writer.printf("-Deleted repository folder %s (%d)\n", dlFolder.getName(), dlFolder.getFolderId());
                    } catch (PortalException e) {
                        writer.printf("-Failed to delete repository folder %s (%d): %s\n", dlFolder.getName(), dlFolder.getFolderId(), e.getMessage());
                    }

                    try {
                        ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
                                tempFileRepository.getCompanyId(), DLFolder.class.getName(), 4, dlFolder.getFolderId());
                        writer.printf("-Deleted resource permission for folder %s (%d)\n", dlFolder.getName(), dlFolder.getFolderId());
                    } catch (PortalException e) {
                        writer.printf("-Failed to delete resource permission for folder %s (%d): %s\n", dlFolder.getName(), dlFolder.getFolderId(), e.getMessage());
                    }
                }
            });
        } catch (PortalException e) {
            writer.printf("-Failed to get repository for %s: %s\n", TempFileEntryUtil.class.getName(), e.getMessage());
        }
    }


    private void deleteMBMessages(PrintWriter writer, long userId, long siteId) {

        writer.println("Deleting messages:");
        List<MBMessage> messages = MBMessageLocalServiceUtil.getGroupMessages(siteId, userId, 0, 0, 1000);
        int messageCount = 0;
        List<MBThread> messageThreads = new ArrayList<>();
        for (MBMessage message : messages) {

            writer.printf("-Start deleting content related to message %s (%d)\n", message.getSubject(), message.getMessageId());
            try {
                List<FileEntry> attachmentsFileEntries = message.getAttachmentsFileEntries();
                attachmentsFileEntries.forEach(fileEntry -> {
                    try {
                        DLFileEntryLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
                        writer.printf("--Deleted attachment %s (%d)\n", fileEntry.getFileName(), fileEntry.getFileEntryId());
                    } catch (Exception e) {
                        writer.printf("--Failed to delete attachment %s (%d): %s\n", fileEntry.getFileName(), fileEntry.getFileEntryId(), e.getMessage());
                    }

                    try {
                        AssetEntryLocalServiceUtil.deleteEntry(FileEntry.class.getName(), fileEntry.getFileEntryId());
                        writer.printf("--Deleted asset entry for attachment %s (%d)\n", fileEntry.getFileName(), fileEntry.getFileEntryId());
                    } catch (Exception e) {
                        writer.printf("--Failed to delete asset entry for attachment %s (%d): %s\n",
                                fileEntry.getFileName(), fileEntry.getFileEntryId(), e.getMessage());
                    }

                });
            } catch (PortalException e) {
                writer.printf("--Failed to get attachments for message %s (%d): %s\n", message.getSubject(), message.getMessageId(), e.getMessage());
            }

            try {
                MBThread thread = message.getThread();
                if (thread.getUserId() == userId && !messageThreads.contains(thread)) {
                    messageThreads.add(thread);
                }
            } catch (PortalException e) {
                writer.printf("--Failed to get thread for message %s (%d): %s\n", message.getSubject(), message.getMessageId(), e.getMessage());
            }
            messageCount += deleteMBMessageAndChildren(writer, message);

        }
        writer.printf("Deleted %d messages\n", messageCount);

        deleteMBThreads(writer, messageThreads);

        //Remove subscriptions
        deleteMBCategories(writer, userId, siteId);
    }

    private void deleteMBThreads(PrintWriter writer, List<MBThread> messageThreads) {
        writer.println("Deleting MBThreads:");
        messageThreads.forEach(mbThread -> deleteMBThread(writer, mbThread));
    }

    private int deleteMBMessageAndChildren(PrintWriter writer, MBMessage message) {

        int[] deletionCount = new int[1];
        try {
            List<MBMessage> childMessages = MBMessageLocalServiceUtil.getChildMessages(message.getMessageId(), 0);
            childMessages.forEach(child -> {
                try {
                    deletionCount[0] += deleteMBMessageAndChildren(writer, child);
                    writer.printf("--Deleted child message %s (%d) of parent message %s (%d) \n",
                            child.getSubject(), child.getMessageId(), message.getSubject(), message.getMessageId());
                } catch (Exception e) {
                    writer.printf("--Failed to delete child message %s (%d) of parent message %s (%d): %s \n",
                            child.getSubject(), child.getMessageId(), message.getSubject(), message.getMessageId(), e.getMessage());
                }
            });
        } catch (Exception e) {
            writer.printf("--Failed to get child messages for message %s (%d): %s\n", message.getSubject(), message.getMessageId(), e.getMessage());
        }

        try {
            MBMessageLocalServiceUtil.deleteMessage(message.getMessageId());
            writer.printf("-Deleted message %s (%d)\n", message.getSubject(), message.getMessageId());
            deletionCount[0]++;
        } catch (PortalException e) {
            writer.printf("-Failed to delete message %s (%d): %s\n", message.getSubject(), message.getMessageId(), e.getMessage());
        }
        return deletionCount[0];
    }

    private void deleteMBThread(PrintWriter writer, MBThread mbThread) {
        try {
            MBThreadLocalServiceUtil.deleteMBThread(mbThread);
            writer.printf("-Deleted thread %s (%d)\n", mbThread.getTitle(), mbThread.getThreadId());
        } catch (Exception e) {
            writer.printf("-Failed to delete thread %s (%d): %s\n", mbThread.getTitle(), mbThread.getThreadId(), e.getMessage());
        }
    }

    private void deleteMBCategories(PrintWriter writer, long userId, long siteId) {

        writer.println("Deleting categories:");
        try {
            List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(siteId);
            for (MBCategory category : categories) {

                if (category.getUserId() == userId) {

                    try {
                        MBMailingList mailingList = MBMailingListLocalServiceUtil.fetchCategoryMailingList(siteId, category.getCategoryId());
                        if (mailingList != null && mailingList.getUserId() == userId) {
                            try {
                                MBMailingListLocalServiceUtil.deleteMBMailingList(mailingList.getMailingListId());
                                writer.printf("--Removed mailing list %s of category %s (%d)\n",
                                        mailingList.getMailingListId(), category.getName(), category.getCategoryId());
                            } catch (NoSuchMailingListException e) {
                                writer.printf("--Failed to remove mailing list %s of category %s (%d): %s\n",
                                        mailingList.getMailingListId(), category.getName(), category.getCategoryId(), e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        writer.printf("--Failed to get mailing list for category %s (%d): %s\n", category.getName(), category.getCategoryId(), e.getMessage());
                    }

                    try {
                        MBCategoryLocalServiceUtil.deleteCategory(category.getCategoryId());
                        writer.printf("-Deleted category %s (%d)\n", category.getName(), category.getCategoryId());
                    } catch (PortalException e) {
                        writer.printf("-Failed to delete category %s (%d): %s\n", category.getName(), category.getCategoryId(), e.getMessage());
                    }
                }

            }
        } catch (Exception e) {
            writer.printf("Failed to get categories for groupId %s: %s\n", siteId, e.getMessage());
        }
    }

    private void deleteThreadFlags(PrintWriter writer, long userId) {
        writer.println("Deleting thread flags:");
        try {
            MBThreadFlagLocalServiceUtil.deleteThreadFlagsByUserId(userId);
            writer.printf("-Deleted thread flags\n");
        } catch (Exception e) {
            writer.printf("-Failed to delete thread flags: %s\n", e.getMessage());
        }
    }

    private void deleteUserPortrait(PrintWriter writer, User user) {
        writer.println("Deleting user portrait:");
        if (user.getPortraitId() > 0) {
            try {
                ImageLocalServiceUtil.deleteImage(user.getPortraitId());
                writer.printf("-Delete user portrait image %d\n", user.getPortraitId());
            } catch (PortalException e) {
                writer.printf("-Failed to delete user portrait image %d: %s\n", user.getPortraitId(), e.getMessage());
            }
        }
    }

    private void deleteAssetEntries(PrintWriter writer, long userId, long siteId) {
        try {
            List<AssetEntry> entries = AssetEntryLocalServiceUtil.getGroupEntries(siteId);
            for (AssetEntry entry : entries) {
                if (entry.getUserId() != userId) continue;
                try {
                    AssetEntryLocalServiceUtil.deleteAssetEntry(entry);
                    writer.printf("-Deleted asset entry %s (%d)\n", entry.getTitle(), entry.getEntryId());
                } catch (Exception e) {
                    writer.printf("-Failed to delete asset entry %s (%d): %s\n", entry.getTitle(), entry.getEntryId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            writer.printf("-Failed to get asset entries for group %d: %s\n", siteId, e.getMessage());
        }
    }

    private void deleteAssetTags(PrintWriter writer, long userId, long siteId) {
        writer.println("Deleting AssetTags:");

        try {
            List<AssetTag> groupTags = AssetTagServiceUtil.getGroupTags(siteId);
            groupTags.forEach(assetTag -> {
                if (assetTag.getUserId() == userId) {
                    try {
                        AssetTagServiceUtil.deleteTag(assetTag.getTagId());
                        writer.printf("-Deleted asset tag %s (%d)\n", assetTag.getName(), assetTag.getTagId());
                    } catch (PortalException e) {
                        writer.printf("-Failed to delete asset tag %s (%d): %s\n", assetTag.getName(), assetTag.getTagId(), e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            writer.printf("-Failed to get asset tags: %s\n", e.getMessage());
        }
    }

}
