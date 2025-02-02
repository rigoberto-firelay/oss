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

package nl.deltares.oss.download.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Download service. Represents a row in the &quot;Downloads_Download&quot; database table, with each column mapped to a property of this class.
 *
 * @author Erik de Rooij @ Deltares
 * @see DownloadModel
 * @generated
 */
@ImplementationClassName("nl.deltares.oss.download.model.impl.DownloadImpl")
@ProviderType
public interface Download extends DownloadModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>nl.deltares.oss.download.model.impl.DownloadImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Download, Long> ID_ACCESSOR =
		new Accessor<Download, Long>() {

			@Override
			public Long get(Download download) {
				return download.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Download> getTypeClass() {
				return Download.class;
			}

		};

}