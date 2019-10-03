package org.jeedevframework.springboot.service;

import org.jeedevframework.springboot.common.service.BaseMongoService;
import org.jeedevframework.springboot.entity.DataEntity;

/**
 * 数据维护服务接口
 */
public interface DataService extends BaseMongoService<DataEntity> {
    /**
     * 以ID获取单个新增数据
     *
     * @param data
     * @return
     */
    DataEntity getById(DataEntity data);

    /**
     * 以SN获取单个新增数据
     *
     * @param data
     * @return
     * @throws BusinessException
     */
    DataEntity getBySn(DataEntity data);

   

    /**
     * 创建新增数据
     *
     * @param data
     */
    <T extends DataEntity> void create(T data);

    /**
     * 删除数据
     *
     * @param data
     */
    void delete(DataEntity data);
    
    Object findReport ();
}
