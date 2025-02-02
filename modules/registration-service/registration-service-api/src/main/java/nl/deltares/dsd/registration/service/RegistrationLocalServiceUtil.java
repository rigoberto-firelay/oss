/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package nl.deltares.dsd.registration.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Registration. This utility wraps
 * <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Erik de Rooij @ Deltares
 * @see RegistrationLocalService
 * @generated
 */
@ProviderType
public class RegistrationLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>nl.deltares.dsd.registration.service.impl.RegistrationLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the registration to the database. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was added
	 */
	public static nl.deltares.dsd.registration.model.Registration
		addRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().addRegistration(registration);
	}

	public static void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date startTime,
		java.util.Date endTime, String preferences, long registeredByUserId) {

		getService().addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, startTime, endTime, preferences, registeredByUserId);
	}

	public static void addUserRegistration(
		long companyId, long groupId, long resourceId, long eventResourceId,
		long parentResourceId, long userId, java.util.Date transferDate,
		long registeredByUserId) {

		getService().addUserRegistration(
			companyId, groupId, resourceId, eventResourceId, parentResourceId,
			userId, transferDate, registeredByUserId);
	}

	public static int countUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourceId) {

		return getService().countUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourceId);
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	public static nl.deltares.dsd.registration.model.Registration
		createRegistration(long registrationId) {

		return getService().createRegistration(registrationId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public static void deleteAllEventRegistrations(
		long groupId, long eventResourceId) {

		getService().deleteAllEventRegistrations(groupId, eventResourceId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 */
	public static void deleteAllRegistrationsAndChildRegistrations(
		long groupId, long resourceId) {

		getService().deleteAllRegistrationsAndChildRegistrations(
			groupId, resourceId);
	}

	/**
	 * Delete all registrations related to 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param userId User id
	 * @param eventResourceId Article Identifier of Event being removed.
	 */
	public static void deleteAllUserEventRegistrations(
		long groupId, long userId, long eventResourceId) {

		getService().deleteAllUserEventRegistrations(
			groupId, userId, eventResourceId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
			deleteRegistration(long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRegistration(registrationId);
	}

	/**
	 * Deletes the registration from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was removed
	 */
	public static nl.deltares.dsd.registration.model.Registration
		deleteRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().deleteRegistration(registration);
	}

	/**
	 * Delete user registrations for 'resourceId' and a start date equal to 'startDate'
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 * @param startDate Start date for which to remove registration
	 */
	public static void deleteUserRegistration(
			long groupId, long resourceId, long userId,
			java.util.Date startDate)
		throws nl.deltares.dsd.registration.exception.
			NoSuchRegistrationException {

		getService().deleteUserRegistration(
			groupId, resourceId, userId, startDate);
	}

	/**
	 * Delete user registrations for 'resourceId'. This includes all registration with a parentArticleId
	 * that matches 'resourceId'.
	 *
	 * @param groupId Site Identifier
	 * @param resourceId Article Identifier being removed.
	 * @param userId User for which to remove registration
	 */
	public static void deleteUserRegistrationAndChildRegistrations(
		long groupId, long resourceId, long userId) {

		getService().deleteUserRegistrationAndChildRegistrations(
			groupId, resourceId, userId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static nl.deltares.dsd.registration.model.Registration
		fetchRegistration(long registrationId) {

		return getService().fetchRegistration(registrationId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getArticleRegistrations(long groupId, long articleResourceId) {

		return getService().getArticleRegistrations(groupId, articleResourceId);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getEventRegistrations(
			long groupId, long eventResourceId) {

		return getService().getEventRegistrations(groupId, eventResourceId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the registration with the primary key.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws PortalException if a registration with the primary key could not be found
	 */
	public static nl.deltares.dsd.registration.model.Registration
			getRegistration(long registrationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegistration(registrationId);
	}

	public static java.util.List<java.util.Date> getRegistrationDates(
		long groupId, long userId, long resourceId) {

		return getService().getRegistrationDates(groupId, userId, resourceId);
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getRegistrations(
			int start, int end) {

		return getService().getRegistrations(start, end);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getRegistrations(
			long groupId, java.util.Date start, java.util.Date end) {

		return getService().getRegistrations(groupId, start, end);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getRegistrations(
			long groupId, long userId, long resourceId) {

		return getService().getRegistrations(groupId, userId, resourceId);
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	public static int getRegistrationsCount() {
		return getService().getRegistrationsCount();
	}

	public static int getRegistrationsCount(long groupId, long resourceId) {
		return getService().getRegistrationsCount(groupId, resourceId);
	}

	public static int getRegistrationsCount(
		long groupId, long resourceId, java.util.Date startDate) {

		return getService().getRegistrationsCount(
			groupId, resourceId, startDate);
	}

	public static int getRegistrationsCount(
		long groupId, long userId, long resourceId) {

		return getService().getRegistrationsCount(groupId, userId, resourceId);
	}

	public static int getRegistrationsCount(
		long groupId, long userId, long resourceId, java.util.Date startDate) {

		return getService().getRegistrationsCount(
			groupId, userId, resourceId, startDate);
	}

	public static long[] getRegistrationsWithOverlappingPeriod(
		long groupId, long userId, java.util.Date startTime,
		java.util.Date endTime) {

		return getService().getRegistrationsWithOverlappingPeriod(
			groupId, userId, startTime, endTime);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getUserEventRegistrations(
				long groupId, long userId, long eventResourceId) {

		return getService().getUserEventRegistrations(
			groupId, userId, eventResourceId);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getUserEventRegistrationsMadeForOthers(
				long groupId, long registeredByUserId, long eventResourceId) {

		return getService().getUserEventRegistrationsMadeForOthers(
			groupId, registeredByUserId, eventResourceId);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration> getUserRegistrations(
			long groupId, long userId, java.util.Date start,
			java.util.Date end) {

		return getService().getUserRegistrations(groupId, userId, start, end);
	}

	public static java.util.List
		<nl.deltares.dsd.registration.model.Registration>
			getUsersRegisteredByOtherUser(
				long groupId, long otherUserId, long registrationResourceId) {

		return getService().getUsersRegisteredByOtherUser(
			groupId, otherUserId, registrationResourceId);
	}

	/**
	 * Updates the registration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param registration the registration
	 * @return the registration that was updated
	 */
	public static nl.deltares.dsd.registration.model.Registration
		updateRegistration(
			nl.deltares.dsd.registration.model.Registration registration) {

		return getService().updateRegistration(registration);
	}

	public static RegistrationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RegistrationLocalService, RegistrationLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(RegistrationLocalService.class);

		ServiceTracker<RegistrationLocalService, RegistrationLocalService>
			serviceTracker =
				new ServiceTracker
					<RegistrationLocalService, RegistrationLocalService>(
						bundle.getBundleContext(),
						RegistrationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}