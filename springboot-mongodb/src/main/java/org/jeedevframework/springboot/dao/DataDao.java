package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.common.dao.BaseMongoDao;
import org.jeedevframework.springboot.entity.DataEntity;

public interface DataDao extends BaseMongoDao<DataEntity> {
	Object findReport ();
}
