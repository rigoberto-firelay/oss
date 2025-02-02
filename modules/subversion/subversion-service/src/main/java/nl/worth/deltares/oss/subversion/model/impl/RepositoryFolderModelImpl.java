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

package nl.worth.deltares.oss.subversion.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import nl.worth.deltares.oss.subversion.model.RepositoryFolder;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderModel;
import nl.worth.deltares.oss.subversion.model.RepositoryFolderSoap;

/**
 * The base model implementation for the RepositoryFolder service. Represents a row in the &quot;Subversion_RepositoryFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>RepositoryFolderModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RepositoryFolderImpl}.
 * </p>
 *
 * @author Pier-Angelo Gaetani @ Worth Systems
 * @see RepositoryFolderImpl
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class RepositoryFolderModelImpl
	extends BaseModelImpl<RepositoryFolder> implements RepositoryFolderModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a repository folder model instance should use the <code>RepositoryFolder</code> interface instead.
	 */
	public static final String TABLE_NAME = "Subversion_RepositoryFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"folderId", Types.BIGINT}, {"repositoryId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"worldRead", Types.BOOLEAN},
		{"worldWrite", Types.BOOLEAN}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("worldRead", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("worldWrite", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Subversion_RepositoryFolder (folderId LONG not null primary key,repositoryId LONG,name VARCHAR(75) null,worldRead BOOLEAN,worldWrite BOOLEAN,createDate DATE null,modifiedDate DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table Subversion_RepositoryFolder";

	public static final String ORDER_BY_JPQL =
		" ORDER BY repositoryFolder.folderId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Subversion_RepositoryFolder.folderId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.nl.worth.deltares.oss.subversion.model.RepositoryFolder"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.nl.worth.deltares.oss.subversion.model.RepositoryFolder"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.nl.worth.deltares.oss.subversion.model.RepositoryFolder"),
		true);

	public static final long REPOSITORYID_COLUMN_BITMASK = 1L;

	public static final long FOLDERID_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static RepositoryFolder toModel(RepositoryFolderSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		RepositoryFolder model = new RepositoryFolderImpl();

		model.setFolderId(soapModel.getFolderId());
		model.setRepositoryId(soapModel.getRepositoryId());
		model.setName(soapModel.getName());
		model.setWorldRead(soapModel.isWorldRead());
		model.setWorldWrite(soapModel.isWorldWrite());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<RepositoryFolder> toModels(
		RepositoryFolderSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<RepositoryFolder> models = new ArrayList<RepositoryFolder>(
			soapModels.length);

		for (RepositoryFolderSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		nl.worth.deltares.oss.subversion.service.util.ServiceProps.get(
			"lock.expiration.time.nl.worth.deltares.oss.subversion.model.RepositoryFolder"));

	public RepositoryFolderModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _folderId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFolderId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _folderId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return RepositoryFolder.class;
	}

	@Override
	public String getModelClassName() {
		return RepositoryFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<RepositoryFolder, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<RepositoryFolder, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RepositoryFolder, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((RepositoryFolder)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<RepositoryFolder, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<RepositoryFolder, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(RepositoryFolder)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<RepositoryFolder, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<RepositoryFolder, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, RepositoryFolder>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			RepositoryFolder.class.getClassLoader(), RepositoryFolder.class,
			ModelWrapper.class);

		try {
			Constructor<RepositoryFolder> constructor =
				(Constructor<RepositoryFolder>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<RepositoryFolder, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<RepositoryFolder, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<RepositoryFolder, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<RepositoryFolder, Object>>();
		Map<String, BiConsumer<RepositoryFolder, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<RepositoryFolder, ?>>();

		attributeGetterFunctions.put(
			"folderId",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getFolderId();
				}

			});
		attributeSetterBiConsumers.put(
			"folderId",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object folderId) {

					repositoryFolder.setFolderId((Long)folderId);
				}

			});
		attributeGetterFunctions.put(
			"repositoryId",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getRepositoryId();
				}

			});
		attributeSetterBiConsumers.put(
			"repositoryId",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object repositoryId) {

					repositoryFolder.setRepositoryId((Long)repositoryId);
				}

			});
		attributeGetterFunctions.put(
			"name",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getName();
				}

			});
		attributeSetterBiConsumers.put(
			"name",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object name) {

					repositoryFolder.setName((String)name);
				}

			});
		attributeGetterFunctions.put(
			"worldRead",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getWorldRead();
				}

			});
		attributeSetterBiConsumers.put(
			"worldRead",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object worldRead) {

					repositoryFolder.setWorldRead((Boolean)worldRead);
				}

			});
		attributeGetterFunctions.put(
			"worldWrite",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getWorldWrite();
				}

			});
		attributeSetterBiConsumers.put(
			"worldWrite",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object worldWrite) {

					repositoryFolder.setWorldWrite((Boolean)worldWrite);
				}

			});
		attributeGetterFunctions.put(
			"createDate",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getCreateDate();
				}

			});
		attributeSetterBiConsumers.put(
			"createDate",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object createDate) {

					repositoryFolder.setCreateDate((Date)createDate);
				}

			});
		attributeGetterFunctions.put(
			"modifiedDate",
			new Function<RepositoryFolder, Object>() {

				@Override
				public Object apply(RepositoryFolder repositoryFolder) {
					return repositoryFolder.getModifiedDate();
				}

			});
		attributeSetterBiConsumers.put(
			"modifiedDate",
			new BiConsumer<RepositoryFolder, Object>() {

				@Override
				public void accept(
					RepositoryFolder repositoryFolder, Object modifiedDate) {

					repositoryFolder.setModifiedDate((Date)modifiedDate);
				}

			});

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getFolderId() {
		return _folderId;
	}

	@Override
	public void setFolderId(long folderId) {
		_columnBitmask = -1L;

		_folderId = folderId;
	}

	@JSON
	@Override
	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_columnBitmask |= REPOSITORYID_COLUMN_BITMASK;

		if (!_setOriginalRepositoryId) {
			_setOriginalRepositoryId = true;

			_originalRepositoryId = _repositoryId;
		}

		_repositoryId = repositoryId;
	}

	public long getOriginalRepositoryId() {
		return _originalRepositoryId;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@JSON
	@Override
	public boolean getWorldRead() {
		return _worldRead;
	}

	@JSON
	@Override
	public boolean isWorldRead() {
		return _worldRead;
	}

	@Override
	public void setWorldRead(boolean worldRead) {
		_worldRead = worldRead;
	}

	@JSON
	@Override
	public boolean getWorldWrite() {
		return _worldWrite;
	}

	@JSON
	@Override
	public boolean isWorldWrite() {
		return _worldWrite;
	}

	@Override
	public void setWorldWrite(boolean worldWrite) {
		_worldWrite = worldWrite;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			0, RepositoryFolder.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public RepositoryFolder toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, RepositoryFolder>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		RepositoryFolderImpl repositoryFolderImpl = new RepositoryFolderImpl();

		repositoryFolderImpl.setFolderId(getFolderId());
		repositoryFolderImpl.setRepositoryId(getRepositoryId());
		repositoryFolderImpl.setName(getName());
		repositoryFolderImpl.setWorldRead(isWorldRead());
		repositoryFolderImpl.setWorldWrite(isWorldWrite());
		repositoryFolderImpl.setCreateDate(getCreateDate());
		repositoryFolderImpl.setModifiedDate(getModifiedDate());

		repositoryFolderImpl.resetOriginalValues();

		return repositoryFolderImpl;
	}

	@Override
	public int compareTo(RepositoryFolder repositoryFolder) {
		int value = 0;

		if (getFolderId() < repositoryFolder.getFolderId()) {
			value = -1;
		}
		else if (getFolderId() > repositoryFolder.getFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RepositoryFolder)) {
			return false;
		}

		RepositoryFolder repositoryFolder = (RepositoryFolder)obj;

		long primaryKey = repositoryFolder.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		RepositoryFolderModelImpl repositoryFolderModelImpl = this;

		repositoryFolderModelImpl._originalRepositoryId =
			repositoryFolderModelImpl._repositoryId;

		repositoryFolderModelImpl._setOriginalRepositoryId = false;

		repositoryFolderModelImpl._setModifiedDate = false;

		repositoryFolderModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<RepositoryFolder> toCacheModel() {
		RepositoryFolderCacheModel repositoryFolderCacheModel =
			new RepositoryFolderCacheModel();

		repositoryFolderCacheModel.folderId = getFolderId();

		repositoryFolderCacheModel.repositoryId = getRepositoryId();

		repositoryFolderCacheModel.name = getName();

		String name = repositoryFolderCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			repositoryFolderCacheModel.name = null;
		}

		repositoryFolderCacheModel.worldRead = isWorldRead();

		repositoryFolderCacheModel.worldWrite = isWorldWrite();

		Date createDate = getCreateDate();

		if (createDate != null) {
			repositoryFolderCacheModel.createDate = createDate.getTime();
		}
		else {
			repositoryFolderCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			repositoryFolderCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			repositoryFolderCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		return repositoryFolderCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<RepositoryFolder, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<RepositoryFolder, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RepositoryFolder, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((RepositoryFolder)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<RepositoryFolder, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<RepositoryFolder, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<RepositoryFolder, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((RepositoryFolder)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, RepositoryFolder>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _folderId;
	private long _repositoryId;
	private long _originalRepositoryId;
	private boolean _setOriginalRepositoryId;
	private String _name;
	private boolean _worldRead;
	private boolean _worldWrite;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _columnBitmask;
	private RepositoryFolder _escapedModel;

}