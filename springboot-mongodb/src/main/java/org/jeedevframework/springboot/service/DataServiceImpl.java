package org.jeedevframework.springboot.service;

import org.apache.commons.lang3.StringUtils;
import org.jeedevframework.springboot.common.service.BaseMongoServiceImpl;
import org.jeedevframework.springboot.dao.DataDao;
import org.jeedevframework.springboot.entity.DataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据维护服务实现
 *
 */
@Service("dataService")
public class DataServiceImpl extends BaseMongoServiceImpl<DataDao, DataEntity> implements DataService {

    @Autowired
    private DataDao dataDao;

    @Override
    public DataEntity getById(DataEntity data) {
        String id = data.getId();
        String modelCode = data.getModelCode();
        if (StringUtils.isBlank(modelCode) || StringUtils.isBlank(id)) {
            return null;
        }

        DataEntity queryModel = new DataEntity();
        queryModel.setModelCode(modelCode);
        DataEntity result = get(queryModel, Query.query(Criteria.where(DataEntity.ID).is(id)));
      
        return result;
    }

    @Override
    public DataEntity getBySn(DataEntity data) {
        String sn = data.getSn();
        String modelCode = data.getModelCode();
        if (StringUtils.isBlank(modelCode) || StringUtils.isBlank(sn)) {
            return null;
        }

        DataEntity queryModel = new DataEntity();
        queryModel.setModelCode(modelCode);
        return get(queryModel, Query.query(Criteria.where(DataEntity.SERIAL_NUM).is(sn)));
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends DataEntity> void create(T data) {
        this.add(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DataEntity data) {
        super.update(data);
    }

	@Override
	public Object findReport() {
		return dataDao.findReport();	
	}

}
