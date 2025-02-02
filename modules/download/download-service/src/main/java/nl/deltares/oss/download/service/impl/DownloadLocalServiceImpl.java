/*
  Copyright (c) 2000-present Liferay, Inc. All rights reserved.
  <p>
  This library is free software; you can redistribute it and/or modify it under
  the terms of the GNU Lesser General Public License as published by the Free
  Software Foundation; either version 2.1 of the License, or (at your option)
  any later version.
  <p>
  This library is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
  details.
 */

package nl.deltares.oss.download.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import nl.deltares.oss.download.model.Download;
import nl.deltares.oss.download.service.base.DownloadLocalServiceBaseImpl;
import nl.deltares.oss.download.service.persistence.DownloadUtil;

import java.util.List;

/**
 * The implementation of the download local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>nl.deltares.oss.download.service.DownloadLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadLocalServiceBaseImpl
 */
public class DownloadLocalServiceImpl extends DownloadLocalServiceBaseImpl {

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. Use <code>nl.deltares.oss.download.service.DownloadLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>nl.deltares.oss.download.service.DownloadLocalServiceUtil</code>.
     */
    public Download fetchUserDownload(long groupId, long userId, long downloadId) {
        return DownloadUtil.fetchByUserDownload(groupId, userId, downloadId);
    }

    public List<Download> findDirectDownloads(long groupId){
        final DynamicQuery dynamicQuery = getDirectDownloadQuery(groupId);
        return DownloadUtil.findWithDynamicQuery(dynamicQuery);
    }

    public List<Download> findDirectDownloads(long groupId, int start, int end){
        final DynamicQuery dynamicQuery = getDirectDownloadQuery(groupId);
        return DownloadUtil.findWithDynamicQuery(dynamicQuery, start, end);
    }

    public int countDirectDownloads(long groupId){
        final DynamicQuery dynamicQuery = getDirectDownloadQuery(groupId);
        return (int) DownloadUtil.countWithDynamicQuery(dynamicQuery);
    }

    public List<Download> findPaymentPendingDownloads(long groupId){
        final DynamicQuery dynamicQuery = getPaymentPendingQuery(groupId);
        return DownloadUtil.findWithDynamicQuery(dynamicQuery);
    }

    public List<Download> findPaymentPendingDownloads(long groupId, int start, int end){
        final DynamicQuery dynamicQuery = getPaymentPendingQuery(groupId);
        return DownloadUtil.findWithDynamicQuery(dynamicQuery, start, end);
    }

    private DynamicQuery getDirectDownloadQuery(long groupId) {
        final DynamicQuery dynamicQuery = dynamicQuery();
        dynamicQuery
                .add(RestrictionsFactoryUtil.eq("groupId", groupId))
                .add(RestrictionsFactoryUtil.eq("shareId", -1))
                .add(RestrictionsFactoryUtil.isNotNull("directDownloadUrl"));
        return dynamicQuery;
    }

    private DynamicQuery getPaymentPendingQuery(long groupId) {
        final DynamicQuery dynamicQuery = dynamicQuery();
        dynamicQuery
                .add(RestrictionsFactoryUtil.eq("groupId", groupId))
                .add(RestrictionsFactoryUtil.eq("shareId", -1))
                .add(RestrictionsFactoryUtil.isNull("directDownloadUrl"));
        return dynamicQuery;
    }

    public int countPaymentPendingDownloads(long groupId){
        return (int) DownloadUtil.countWithDynamicQuery(getPaymentPendingQuery(groupId));
    }

    public List<Download> findDownloads(long groupId){
        return DownloadUtil.findByGroupDownloads(groupId);
    }

    public List<Download> findDownloads(long groupId, int start, int end){
        return DownloadUtil.findByGroupDownloads(groupId, start, end);
    }

    public int countDownloads(long groupId){
        return DownloadUtil.countByGroupDownloads(groupId);
    }

    public List<Download> findDownloadsByArticleId(long groupId, long articleId){
        return DownloadUtil.findByDownloads(groupId, articleId);
    }

    public List<Download> findDownloadsByArticleId(long groupId, long articleId, int start, int end){
        return DownloadUtil.findByDownloads(groupId, articleId, start, end);
    }

    public List<Download> findUserDownloadsByShareId(long groupId, long userId, int shareId){
        return DownloadUtil.findByUserDownloadsByShareId(groupId, userId, shareId);
    }

    public List<Download> findDownloadsByShareId(long groupId, int shareId, int start, int end){
        return DownloadUtil.findByDownloadsByShareId(groupId, shareId, start, end);
    }

    public List<Download> findDownloadsByShareId(long groupId, int shareId){
        return DownloadUtil.findByDownloadsByShareId(groupId, shareId);
    }

    public int countDownloadsByArticleId(long groupId, long articleId){
        return DownloadUtil.countByDownloads(groupId, articleId);
    }

    public int countDownloadsByShareId(long groupId, int shareId){
        return DownloadUtil.countByDownloadsByShareId(groupId, shareId);
    }

    public List<Download> findDownloadsByUserId(long groupId, long userId){
        return DownloadUtil.findByUserDownloads(groupId, userId);
    }

    public List<Download> findDownloadsByUserId(long groupId, long userId, int start, int end){
        return DownloadUtil.findByUserDownloads(groupId, userId);
    }

    public int countDownloadsByUserId(long groupId, long userId){
        return DownloadUtil.countByUserDownloads(groupId, userId);
    }


}