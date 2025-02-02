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

package nl.deltares.dsd.registration.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the Registration service. Represents a row in the &quot;Registrations_Registration&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>nl.deltares.dsd.registration.model.impl.RegistrationModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>nl.deltares.dsd.registration.model.impl.RegistrationImpl</code>.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see Registration
 * @generated
 */
@ProviderType
public interface RegistrationModel
	extends BaseModel<Registration>, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a registration model instance should use the {@link Registration} interface instead.
	 */

	/**
	 * Returns the primary key of this registration.
	 *
	 * @return the primary key of this registration
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this registration.
	 *
	 * @param primaryKey the primary key of this registration
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the registration ID of this registration.
	 *
	 * @return the registration ID of this registration
	 */
	public long getRegistrationId();

	/**
	 * Sets the registration ID of this registration.
	 *
	 * @param registrationId the registration ID of this registration
	 */
	public void setRegistrationId(long registrationId);

	/**
	 * Returns the group ID of this registration.
	 *
	 * @return the group ID of this registration
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this registration.
	 *
	 * @param groupId the group ID of this registration
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the event resource primary key of this registration.
	 *
	 * @return the event resource primary key of this registration
	 */
	public long getEventResourcePrimaryKey();

	/**
	 * Sets the event resource primary key of this registration.
	 *
	 * @param eventResourcePrimaryKey the event resource primary key of this registration
	 */
	public void setEventResourcePrimaryKey(long eventResourcePrimaryKey);

	/**
	 * Returns the company ID of this registration.
	 *
	 * @return the company ID of this registration
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this registration.
	 *
	 * @param companyId the company ID of this registration
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this registration.
	 *
	 * @return the user ID of this registration
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this registration.
	 *
	 * @param userId the user ID of this registration
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this registration.
	 *
	 * @return the user uuid of this registration
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this registration.
	 *
	 * @param userUuid the user uuid of this registration
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the resource primary key of this registration.
	 *
	 * @return the resource primary key of this registration
	 */
	public long getResourcePrimaryKey();

	/**
	 * Sets the resource primary key of this registration.
	 *
	 * @param resourcePrimaryKey the resource primary key of this registration
	 */
	public void setResourcePrimaryKey(long resourcePrimaryKey);

	/**
	 * Returns the user preferences of this registration.
	 *
	 * @return the user preferences of this registration
	 */
	@AutoEscape
	public String getUserPreferences();

	/**
	 * Sets the user preferences of this registration.
	 *
	 * @param userPreferences the user preferences of this registration
	 */
	public void setUserPreferences(String userPreferences);

	/**
	 * Returns the start time of this registration.
	 *
	 * @return the start time of this registration
	 */
	public Date getStartTime();

	/**
	 * Sets the start time of this registration.
	 *
	 * @param startTime the start time of this registration
	 */
	public void setStartTime(Date startTime);

	/**
	 * Returns the end time of this registration.
	 *
	 * @return the end time of this registration
	 */
	public Date getEndTime();

	/**
	 * Sets the end time of this registration.
	 *
	 * @param endTime the end time of this registration
	 */
	public void setEndTime(Date endTime);

	/**
	 * Returns the parent resource primary key of this registration.
	 *
	 * @return the parent resource primary key of this registration
	 */
	public long getParentResourcePrimaryKey();

	/**
	 * Sets the parent resource primary key of this registration.
	 *
	 * @param parentResourcePrimaryKey the parent resource primary key of this registration
	 */
	public void setParentResourcePrimaryKey(long parentResourcePrimaryKey);

	/**
	 * Returns the registered by user ID of this registration.
	 *
	 * @return the registered by user ID of this registration
	 */
	public long getRegisteredByUserId();

	/**
	 * Sets the registered by user ID of this registration.
	 *
	 * @param registeredByUserId the registered by user ID of this registration
	 */
	public void setRegisteredByUserId(long registeredByUserId);

	/**
	 * Returns the registered by user uuid of this registration.
	 *
	 * @return the registered by user uuid of this registration
	 */
	public String getRegisteredByUserUuid();

	/**
	 * Sets the registered by user uuid of this registration.
	 *
	 * @param registeredByUserUuid the registered by user uuid of this registration
	 */
	public void setRegisteredByUserUuid(String registeredByUserUuid);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(
		nl.deltares.dsd.registration.model.Registration registration);

	@Override
	public int hashCode();

	@Override
	public CacheModel<nl.deltares.dsd.registration.model.Registration>
		toCacheModel();

	@Override
	public nl.deltares.dsd.registration.model.Registration toEscapedModel();

	@Override
	public nl.deltares.dsd.registration.model.Registration toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();

}