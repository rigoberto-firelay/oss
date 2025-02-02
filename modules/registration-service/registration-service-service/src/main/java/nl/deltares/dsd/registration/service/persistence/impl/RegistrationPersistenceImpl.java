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

package nl.deltares.dsd.registration.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.deltares.dsd.registration.exception.NoSuchRegistrationException;
import nl.deltares.dsd.registration.model.Registration;
import nl.deltares.dsd.registration.model.impl.RegistrationImpl;
import nl.deltares.dsd.registration.model.impl.RegistrationModelImpl;
import nl.deltares.dsd.registration.service.persistence.RegistrationPersistence;

/**
 * The persistence implementation for the registration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @generated
 */
@ProviderType
public class RegistrationPersistenceImpl
	extends BasePersistenceImpl<Registration>
	implements RegistrationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RegistrationUtil</code> to access the registration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RegistrationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByEventRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByEventRegistrations;
	private FinderPath _finderPathCountByEventRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByEventRegistrations(
			groupId, eventResourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByEventRegistrations(
		long groupId, long eventResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByEventRegistrations;
			finderArgs = new Object[] {groupId, eventResourcePrimaryKey};
		}
		else {
			finderPath = _finderPathWithPaginationFindByEventRegistrations;
			finderArgs = new Object[] {
				groupId, eventResourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(eventResourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByEventRegistrations_First(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByEventRegistrations_First(
			groupId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByEventRegistrations_First(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByEventRegistrations(
			groupId, eventResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByEventRegistrations_Last(
			long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByEventRegistrations_Last(
			groupId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByEventRegistrations_Last(
		long groupId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByEventRegistrations(groupId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByEventRegistrations(
			groupId, eventResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByEventRegistrations_PrevAndNext(
				session, registration, groupId, eventResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByEventRegistrations_PrevAndNext(
				session, registration, groupId, eventResourcePrimaryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByEventRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

		query.append(
			_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByEventRegistrations(
					groupId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByEventRegistrations(
		long groupId, long eventResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByEventRegistrations;

		Object[] finderArgs = new Object[] {groupId, eventResourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(eventResourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_EVENTREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_EVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByUserEventRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserEventRegistrations;
	private FinderPath _finderPathCountByUserEventRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserEventRegistrations;
			finderArgs = new Object[] {
				groupId, userId, eventResourcePrimaryKey
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUserEventRegistrations;
			finderArgs = new Object[] {
				groupId, userId, eventResourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(eventResourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrations_First(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserEventRegistrations_First(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrations_First(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrations_Last(
			long groupId, long userId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserEventRegistrations_Last(
			groupId, userId, eventResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrations_Last(
		long groupId, long userId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserEventRegistrations(
			groupId, userId, eventResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserEventRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserEventRegistrations_PrevAndNext(
				session, registration, groupId, userId, eventResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserEventRegistrations_PrevAndNext(
				session, registration, groupId, userId, eventResourcePrimaryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserEventRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

		query.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByUserEventRegistrations(
					groupId, userId, eventResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserEventRegistrations(
		long groupId, long userId, long eventResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserEventRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, eventResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(eventResourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String _FINDER_COLUMN_USEREVENTREGISTRATIONS_USERID_2 =
		"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONS_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByUserRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByUserRegistrations;
	private FinderPath _finderPathCountByUserRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId) {

		return findByUserRegistrations(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end) {

		return findByUserRegistrations(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserRegistrations(
			groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrations(
		long groupId, long userId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUserRegistrations;
			finderArgs = new Object[] {groupId, userId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUserRegistrations;
			finderArgs = new Object[] {
				groupId, userId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_First(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_First(
			groupId, userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_First(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserRegistrations(
			groupId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrations_Last(
			long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrations_Last(
			groupId, userId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrations_Last(
		long groupId, long userId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserRegistrations(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserRegistrations(
			groupId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserRegistrations_PrevAndNext(
				session, registration, groupId, userId, orderByComparator,
				true);

			array[1] = registration;

			array[2] = getByUserRegistrations_PrevAndNext(
				session, registration, groupId, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserRegistrations(long groupId, long userId) {
		for (Registration registration :
				findByUserRegistrations(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserRegistrations(long groupId, long userId) {
		FinderPath finderPath = _finderPathCountByUserRegistrations;

		Object[] finderArgs = new Object[] {groupId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERREGISTRATIONS_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String _FINDER_COLUMN_USERREGISTRATIONS_USERID_2 =
		"registration.userId = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe;
	private FinderPath
		_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe;
	private FinderPath _finderPathCountByUserRegistrationsRegisteredByMe;

	/**
	 * Returns all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe;
			finderArgs = new Object[] {groupId, registeredByUserId};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe;
			finderArgs = new Object[] {
				groupId, registeredByUserId, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(registeredByUserId !=
							registration.getRegisteredByUserId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			query.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(registeredByUserId);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrationsRegisteredByMe_First(
			long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserRegistrationsRegisteredByMe_First(
				groupId, registeredByUserId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", registeredByUserId=");
		msg.append(registeredByUserId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrationsRegisteredByMe_First(
		long groupId, long registeredByUserId,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserRegistrationsRegisteredByMe_Last(
			long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserRegistrationsRegisteredByMe_Last(
			groupId, registeredByUserId, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", registeredByUserId=");
		msg.append(registeredByUserId);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserRegistrationsRegisteredByMe_Last(
		long groupId, long registeredByUserId,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserRegistrationsRegisteredByMe(
			groupId, registeredByUserId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserRegistrationsRegisteredByMe_PrevAndNext(
			long registrationId, long groupId, long registeredByUserId,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserRegistrationsRegisteredByMe_PrevAndNext(
		Session session, Registration registration, long groupId,
		long registeredByUserId,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

		query.append(
			_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(registeredByUserId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and registeredByUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 */
	@Override
	public void removeByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		for (Registration registration :
				findByUserRegistrationsRegisteredByMe(
					groupId, registeredByUserId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and registeredByUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId) {

		FinderPath finderPath =
			_finderPathCountByUserRegistrationsRegisteredByMe;

		Object[] finderArgs = new Object[] {groupId, registeredByUserId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			query.append(
				_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(registeredByUserId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2 =
			"registration.registeredByUserId = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe;
	private FinderPath
		_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe;
	private FinderPath _finderPathCountByUserEventRegistrationsRegisteredByMe;

	/**
	 * Returns all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		int start, int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe;
			finderArgs = new Object[] {
				groupId, registeredByUserId, eventResourcePrimaryKey
			};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe;
			finderArgs = new Object[] {
				groupId, registeredByUserId, eventResourcePrimaryKey, start,
				end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(registeredByUserId !=
							registration.getRegisteredByUserId()) ||
						(eventResourcePrimaryKey !=
							registration.getEventResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(registeredByUserId);

				qPos.add(eventResourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrationsRegisteredByMe_First(
			long groupId, long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserEventRegistrationsRegisteredByMe_First(
				groupId, registeredByUserId, eventResourcePrimaryKey,
				orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", registeredByUserId=");
		msg.append(registeredByUserId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrationsRegisteredByMe_First(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserEventRegistrationsRegisteredByMe_Last(
			long groupId, long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration =
			fetchByUserEventRegistrationsRegisteredByMe_Last(
				groupId, registeredByUserId, eventResourcePrimaryKey,
				orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", registeredByUserId=");
		msg.append(registeredByUserId);

		msg.append(", eventResourcePrimaryKey=");
		msg.append(eventResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserEventRegistrationsRegisteredByMe_Last(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserEventRegistrationsRegisteredByMe(
			groupId, registeredByUserId, eventResourcePrimaryKey, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[]
			findByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				long registrationId, long groupId, long registeredByUserId,
				long eventResourcePrimaryKey,
				OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				eventResourcePrimaryKey, orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
				session, registration, groupId, registeredByUserId,
				eventResourcePrimaryKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration
		getByUserEventRegistrationsRegisteredByMe_PrevAndNext(
			Session session, Registration registration, long groupId,
			long registeredByUserId, long eventResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator,
			boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

		query.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

		query.append(
			_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(registeredByUserId);

		qPos.add(eventResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 */
	@Override
	public void removeByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		for (Registration registration :
				findByUserEventRegistrationsRegisteredByMe(
					groupId, registeredByUserId, eventResourcePrimaryKey,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and registeredByUserId = &#63; and eventResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param registeredByUserId the registered by user ID
	 * @param eventResourcePrimaryKey the event resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserEventRegistrationsRegisteredByMe(
		long groupId, long registeredByUserId, long eventResourcePrimaryKey) {

		FinderPath finderPath =
			_finderPathCountByUserEventRegistrationsRegisteredByMe;

		Object[] finderArgs = new Object[] {
			groupId, registeredByUserId, eventResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2);

			query.append(
				_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(registeredByUserId);

				qPos.add(eventResourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_REGISTEREDBYUSERID_2 =
			"registration.registeredByUserId = ? AND ";

	private static final String
		_FINDER_COLUMN_USEREVENTREGISTRATIONSREGISTEREDBYME_EVENTRESOURCEPRIMARYKEY_2 =
			"registration.eventResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByArticleRegistrations;
	private FinderPath _finderPathWithoutPaginationFindByArticleRegistrations;
	private FinderPath _finderPathCountByArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByArticleRegistrations(
			groupId, resourcePrimaryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByArticleRegistrations(
		long groupId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByArticleRegistrations;
			finderArgs = new Object[] {groupId, resourcePrimaryKey};
		}
		else {
			finderPath = _finderPathWithPaginationFindByArticleRegistrations;
			finderArgs = new Object[] {
				groupId, resourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(resourcePrimaryKey !=
							registration.getResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(resourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_First(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_First(
			groupId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", resourcePrimaryKey=");
		msg.append(resourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_First(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByArticleRegistrations(
			groupId, resourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByArticleRegistrations_Last(
			long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByArticleRegistrations_Last(
			groupId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", resourcePrimaryKey=");
		msg.append(resourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByArticleRegistrations_Last(
		long groupId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByArticleRegistrations(groupId, resourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByArticleRegistrations(
			groupId, resourcePrimaryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, resourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByArticleRegistrations_PrevAndNext(
				session, registration, groupId, resourcePrimaryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(resourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	@Override
	public void removeByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		for (Registration registration :
				findByArticleRegistrations(
					groupId, resourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByArticleRegistrations(
		long groupId, long resourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, resourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(resourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ARTICLEREGISTRATIONS_GROUPID_2 =
		"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_ARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2 =
			"registration.resourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByUserArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserArticleRegistrations;
	private FinderPath _finderPathCountByUserArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start,
		int end) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserArticleRegistrations;
			finderArgs = new Object[] {groupId, userId, resourcePrimaryKey};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, resourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(resourcePrimaryKey !=
							registration.getResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(resourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_First(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_First(
			groupId, userId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", resourcePrimaryKey=");
		msg.append(resourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_First(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserArticleRegistrations_Last(
			long groupId, long userId, long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserArticleRegistrations_Last(
			groupId, userId, resourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", resourcePrimaryKey=");
		msg.append(resourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserArticleRegistrations_Last(
		long groupId, long userId, long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserArticleRegistrations(
			groupId, userId, resourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long resourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, resourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId, resourcePrimaryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long resourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

		query.append(
			_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(resourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 */
	@Override
	public void removeByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		for (Registration registration :
				findByUserArticleRegistrations(
					groupId, userId, resourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and resourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param resourcePrimaryKey the resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserArticleRegistrations(
		long groupId, long userId, long resourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserArticleRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, resourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(resourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERARTICLEREGISTRATIONS_RESOURCEPRIMARYKEY_2 =
			"registration.resourcePrimaryKey = ?";

	private FinderPath
		_finderPathWithPaginationFindByUserChildArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
	private FinderPath _finderPathCountByUserChildArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator) {

		return findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey, int start,
		int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByUserChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, parentResourcePrimaryKey
			};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByUserChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, userId, parentResourcePrimaryKey, start, end,
				orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(userId != registration.getUserId()) ||
						(parentResourcePrimaryKey !=
							registration.getParentResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(parentResourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_First(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_First(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", parentResourcePrimaryKey=");
		msg.append(parentResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_First(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByUserChildArticleRegistrations_Last(
			long groupId, long userId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByUserChildArticleRegistrations_Last(
			groupId, userId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", parentResourcePrimaryKey=");
		msg.append(parentResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByUserChildArticleRegistrations_Last(
		long groupId, long userId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByUserChildArticleRegistrations(
			groupId, userId, parentResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByUserChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long userId,
			long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId,
				parentResourcePrimaryKey, orderByComparator, true);

			array[1] = registration;

			array[2] = getByUserChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, userId,
				parentResourcePrimaryKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByUserChildArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId, long userId,
		long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

		query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

		query.append(
			_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(parentResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	@Override
	public void removeByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		for (Registration registration :
				findByUserChildArticleRegistrations(
					groupId, userId, parentResourcePrimaryKey,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and userId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByUserChildArticleRegistrations(
		long groupId, long userId, long parentResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByUserChildArticleRegistrations;

		Object[] finderArgs = new Object[] {
			groupId, userId, parentResourcePrimaryKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2);

			query.append(
				_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(parentResourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_USERID_2 =
			"registration.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_USERCHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2 =
			"registration.parentResourcePrimaryKey = ?";

	private FinderPath _finderPathWithPaginationFindByChildArticleRegistrations;
	private FinderPath
		_finderPathWithoutPaginationFindByChildArticleRegistrations;
	private FinderPath _finderPathCountByChildArticleRegistrations;

	/**
	 * Returns all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator) {

		return findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching registrations
	 */
	@Override
	public List<Registration> findByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey, int start, int end,
		OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByChildArticleRegistrations;
			finderArgs = new Object[] {groupId, parentResourcePrimaryKey};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByChildArticleRegistrations;
			finderArgs = new Object[] {
				groupId, parentResourcePrimaryKey, start, end, orderByComparator
			};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Registration registration : list) {
					if ((groupId != registration.getGroupId()) ||
						(parentResourcePrimaryKey !=
							registration.getParentResourcePrimaryKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(RegistrationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentResourcePrimaryKey);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByChildArticleRegistrations_First(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByChildArticleRegistrations_First(
			groupId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentResourcePrimaryKey=");
		msg.append(parentResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the first registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByChildArticleRegistrations_First(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		List<Registration> list = findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration
	 * @throws NoSuchRegistrationException if a matching registration could not be found
	 */
	@Override
	public Registration findByChildArticleRegistrations_Last(
			long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = fetchByChildArticleRegistrations_Last(
			groupId, parentResourcePrimaryKey, orderByComparator);

		if (registration != null) {
			return registration;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentResourcePrimaryKey=");
		msg.append(parentResourcePrimaryKey);

		msg.append("}");

		throw new NoSuchRegistrationException(msg.toString());
	}

	/**
	 * Returns the last registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching registration, or <code>null</code> if a matching registration could not be found
	 */
	@Override
	public Registration fetchByChildArticleRegistrations_Last(
		long groupId, long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator) {

		int count = countByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey);

		if (count == 0) {
			return null;
		}

		List<Registration> list = findByChildArticleRegistrations(
			groupId, parentResourcePrimaryKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the registrations before and after the current registration in the ordered set where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param registrationId the primary key of the current registration
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration[] findByChildArticleRegistrations_PrevAndNext(
			long registrationId, long groupId, long parentResourcePrimaryKey,
			OrderByComparator<Registration> orderByComparator)
		throws NoSuchRegistrationException {

		Registration registration = findByPrimaryKey(registrationId);

		Session session = null;

		try {
			session = openSession();

			Registration[] array = new RegistrationImpl[3];

			array[0] = getByChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, parentResourcePrimaryKey,
				orderByComparator, true);

			array[1] = registration;

			array[2] = getByChildArticleRegistrations_PrevAndNext(
				session, registration, groupId, parentResourcePrimaryKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Registration getByChildArticleRegistrations_PrevAndNext(
		Session session, Registration registration, long groupId,
		long parentResourcePrimaryKey,
		OrderByComparator<Registration> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REGISTRATION_WHERE);

		query.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

		query.append(
			_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RegistrationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentResourcePrimaryKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(registration)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Registration> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the registrations where groupId = &#63; and parentResourcePrimaryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 */
	@Override
	public void removeByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		for (Registration registration :
				findByChildArticleRegistrations(
					groupId, parentResourcePrimaryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations where groupId = &#63; and parentResourcePrimaryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentResourcePrimaryKey the parent resource primary key
	 * @return the number of matching registrations
	 */
	@Override
	public int countByChildArticleRegistrations(
		long groupId, long parentResourcePrimaryKey) {

		FinderPath finderPath = _finderPathCountByChildArticleRegistrations;

		Object[] finderArgs = new Object[] {groupId, parentResourcePrimaryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REGISTRATION_WHERE);

			query.append(_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2);

			query.append(
				_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentResourcePrimaryKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_GROUPID_2 =
			"registration.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_CHILDARTICLEREGISTRATIONS_PARENTRESOURCEPRIMARYKEY_2 =
			"registration.parentResourcePrimaryKey = ?";

	public RegistrationPersistenceImpl() {
		setModelClass(Registration.class);
	}

	/**
	 * Caches the registration in the entity cache if it is enabled.
	 *
	 * @param registration the registration
	 */
	@Override
	public void cacheResult(Registration registration) {
		entityCache.putResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey(), registration);

		registration.resetOriginalValues();
	}

	/**
	 * Caches the registrations in the entity cache if it is enabled.
	 *
	 * @param registrations the registrations
	 */
	@Override
	public void cacheResult(List<Registration> registrations) {
		for (Registration registration : registrations) {
			if (entityCache.getResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, registration.getPrimaryKey()) ==
						null) {

				cacheResult(registration);
			}
			else {
				registration.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all registrations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RegistrationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the registration.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Registration registration) {
		entityCache.removeResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Registration> registrations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Registration registration : registrations) {
			entityCache.removeResult(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationImpl.class, registration.getPrimaryKey());
		}
	}

	/**
	 * Creates a new registration with the primary key. Does not add the registration to the database.
	 *
	 * @param registrationId the primary key for the new registration
	 * @return the new registration
	 */
	@Override
	public Registration create(long registrationId) {
		Registration registration = new RegistrationImpl();

		registration.setNew(true);
		registration.setPrimaryKey(registrationId);

		registration.setCompanyId(CompanyThreadLocal.getCompanyId());

		return registration;
	}

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration remove(long registrationId)
		throws NoSuchRegistrationException {

		return remove((Serializable)registrationId);
	}

	/**
	 * Removes the registration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration that was removed
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration remove(Serializable primaryKey)
		throws NoSuchRegistrationException {

		Session session = null;

		try {
			session = openSession();

			Registration registration = (Registration)session.get(
				RegistrationImpl.class, primaryKey);

			if (registration == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegistrationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(registration);
		}
		catch (NoSuchRegistrationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Registration removeImpl(Registration registration) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(registration)) {
				registration = (Registration)session.get(
					RegistrationImpl.class, registration.getPrimaryKeyObj());
			}

			if (registration != null) {
				session.delete(registration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (registration != null) {
			clearCache(registration);
		}

		return registration;
	}

	@Override
	public Registration updateImpl(Registration registration) {
		boolean isNew = registration.isNew();

		if (!(registration instanceof RegistrationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(registration.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					registration);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in registration proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Registration implementation " +
					registration.getClass());
		}

		RegistrationModelImpl registrationModelImpl =
			(RegistrationModelImpl)registration;

		Session session = null;

		try {
			session = openSession();

			if (registration.isNew()) {
				session.save(registration);

				registration.setNew(false);
			}
			else {
				registration = (Registration)session.merge(registration);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RegistrationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getEventResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByEventRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByEventRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId(),
				registrationModelImpl.getEventResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByUserEventRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserEventRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByUserRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getRegisteredByUserId()
			};

			finderCache.removeResult(
				_finderPathCountByUserRegistrationsRegisteredByMe, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe,
				args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getRegisteredByUserId(),
				registrationModelImpl.getEventResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByUserEventRegistrationsRegisteredByMe, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe,
				args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByArticleRegistrations, args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId(),
				registrationModelImpl.getResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByUserArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserArticleRegistrations,
				args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getUserId(),
				registrationModelImpl.getParentResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByUserChildArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
				args);

			args = new Object[] {
				registrationModelImpl.getGroupId(),
				registrationModelImpl.getParentResourcePrimaryKey()
			};

			finderCache.removeResult(
				_finderPathCountByChildArticleRegistrations, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByChildArticleRegistrations,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByEventRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByEventRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEventRegistrations, args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByEventRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEventRegistrations, args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserEventRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId(),
					registrationModelImpl.getOriginalEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserEventRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserEventRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId(),
					registrationModelImpl.getEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserEventRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserEventRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrations, args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrations, args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalRegisteredByUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrationsRegisteredByMe, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getRegisteredByUserId()
				};

				finderCache.removeResult(
					_finderPathCountByUserRegistrationsRegisteredByMe, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalRegisteredByUserId(),
					registrationModelImpl.getOriginalEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserEventRegistrationsRegisteredByMe,
					args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getRegisteredByUserId(),
					registrationModelImpl.getEventResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserEventRegistrationsRegisteredByMe,
					args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId(),
					registrationModelImpl.getOriginalResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId(),
					registrationModelImpl.getResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserArticleRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserChildArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalUserId(),
					registrationModelImpl.getOriginalParentResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getUserId(),
					registrationModelImpl.getParentResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByUserChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserChildArticleRegistrations,
					args);
			}

			if ((registrationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByChildArticleRegistrations.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					registrationModelImpl.getOriginalGroupId(),
					registrationModelImpl.getOriginalParentResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByChildArticleRegistrations,
					args);

				args = new Object[] {
					registrationModelImpl.getGroupId(),
					registrationModelImpl.getParentResourcePrimaryKey()
				};

				finderCache.removeResult(
					_finderPathCountByChildArticleRegistrations, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByChildArticleRegistrations,
					args);
			}
		}

		entityCache.putResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			registration.getPrimaryKey(), registration, false);

		registration.resetOriginalValues();

		return registration;
	}

	/**
	 * Returns the registration with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegistrationException {

		Registration registration = fetchByPrimaryKey(primaryKey);

		if (registration == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegistrationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return registration;
	}

	/**
	 * Returns the registration with the primary key or throws a <code>NoSuchRegistrationException</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration
	 * @throws NoSuchRegistrationException if a registration with the primary key could not be found
	 */
	@Override
	public Registration findByPrimaryKey(long registrationId)
		throws NoSuchRegistrationException {

		return findByPrimaryKey((Serializable)registrationId);
	}

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	@Override
	public Registration fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED, RegistrationImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Registration registration = (Registration)serializable;

		if (registration == null) {
			Session session = null;

			try {
				session = openSession();

				registration = (Registration)session.get(
					RegistrationImpl.class, primaryKey);

				if (registration != null) {
					cacheResult(registration);
				}
				else {
					entityCache.putResult(
						RegistrationModelImpl.ENTITY_CACHE_ENABLED,
						RegistrationImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return registration;
	}

	/**
	 * Returns the registration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param registrationId the primary key of the registration
	 * @return the registration, or <code>null</code> if a registration with the primary key could not be found
	 */
	@Override
	public Registration fetchByPrimaryKey(long registrationId) {
		return fetchByPrimaryKey((Serializable)registrationId);
	}

	@Override
	public Map<Serializable, Registration> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Registration> map =
			new HashMap<Serializable, Registration>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Registration registration = fetchByPrimaryKey(primaryKey);

			if (registration != null) {
				map.put(primaryKey, registration);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Registration)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		query.append(_SQL_SELECT_REGISTRATION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Registration registration : (List<Registration>)q.list()) {
				map.put(registration.getPrimaryKeyObj(), registration);

				cacheResult(registration);

				uncachedPrimaryKeys.remove(registration.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					RegistrationModelImpl.ENTITY_CACHE_ENABLED,
					RegistrationImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the registrations.
	 *
	 * @return the registrations
	 */
	@Override
	public List<Registration> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @return the range of registrations
	 */
	@Override
	public List<Registration> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of registrations
	 */
	@Override
	public List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the registrations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>RegistrationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of registrations
	 * @param end the upper bound of the range of registrations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of registrations
	 */
	@Override
	public List<Registration> findAll(
		int start, int end, OrderByComparator<Registration> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<Registration> list = null;

		if (retrieveFromCache) {
			list = (List<Registration>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REGISTRATION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REGISTRATION;

				if (pagination) {
					sql = sql.concat(RegistrationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Registration>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the registrations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Registration registration : findAll()) {
			remove(registration);
		}
	}

	/**
	 * Returns the number of registrations.
	 *
	 * @return the number of registrations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_REGISTRATION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RegistrationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the registration persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByEventRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByEventRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByEventRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
			RegistrationModelImpl.EVENTRESOURCEPRIMARYKEY_COLUMN_BITMASK |
			RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByEventRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEventRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserEventRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserEventRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserEventRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.USERID_COLUMN_BITMASK |
				RegistrationModelImpl.EVENTRESOURCEPRIMARYKEY_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserEventRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserEventRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
			RegistrationModelImpl.USERID_COLUMN_BITMASK |
			RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserRegistrationsRegisteredByMe =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserRegistrationsRegisteredByMe =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserRegistrationsRegisteredByMe",
				new String[] {Long.class.getName(), Long.class.getName()},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.REGISTEREDBYUSERID_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserRegistrationsRegisteredByMe = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserRegistrationsRegisteredByMe",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserEventRegistrationsRegisteredByMe =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserEventRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserEventRegistrationsRegisteredByMe =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserEventRegistrationsRegisteredByMe",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.REGISTEREDBYUSERID_COLUMN_BITMASK |
				RegistrationModelImpl.EVENTRESOURCEPRIMARYKEY_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserEventRegistrationsRegisteredByMe = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserEventRegistrationsRegisteredByMe",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, RegistrationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()},
			RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
			RegistrationModelImpl.RESOURCEPRIMARYKEY_COLUMN_BITMASK |
			RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.USERID_COLUMN_BITMASK |
				RegistrationModelImpl.RESOURCEPRIMARYKEY_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUserChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByUserChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName()
				},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.USERID_COLUMN_BITMASK |
				RegistrationModelImpl.PARENTRESOURCEPRIMARYKEY_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByUserChildArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserChildArticleRegistrations",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByChildArticleRegistrations",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByChildArticleRegistrations =
			new FinderPath(
				RegistrationModelImpl.ENTITY_CACHE_ENABLED,
				RegistrationModelImpl.FINDER_CACHE_ENABLED,
				RegistrationImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByChildArticleRegistrations",
				new String[] {Long.class.getName(), Long.class.getName()},
				RegistrationModelImpl.GROUPID_COLUMN_BITMASK |
				RegistrationModelImpl.PARENTRESOURCEPRIMARYKEY_COLUMN_BITMASK |
				RegistrationModelImpl.STARTTIME_COLUMN_BITMASK);

		_finderPathCountByChildArticleRegistrations = new FinderPath(
			RegistrationModelImpl.ENTITY_CACHE_ENABLED,
			RegistrationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByChildArticleRegistrations",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(RegistrationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REGISTRATION =
		"SELECT registration FROM Registration registration";

	private static final String _SQL_SELECT_REGISTRATION_WHERE_PKS_IN =
		"SELECT registration FROM Registration registration WHERE registrationId IN (";

	private static final String _SQL_SELECT_REGISTRATION_WHERE =
		"SELECT registration FROM Registration registration WHERE ";

	private static final String _SQL_COUNT_REGISTRATION =
		"SELECT COUNT(registration) FROM Registration registration";

	private static final String _SQL_COUNT_REGISTRATION_WHERE =
		"SELECT COUNT(registration) FROM Registration registration WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "registration.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Registration exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Registration exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RegistrationPersistenceImpl.class);

}