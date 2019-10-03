package org.jeedevframework.springboot.common.service;

import org.jeedevframework.springboot.common.entity.BaseDynamicEntity;
import org.jeedevframework.springboot.common.entity.Page;
import org.jeedevframework.springboot.common.entity.PageInfo;
import org.jeedevframework.springboot.common.exception.BusinessException;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * mongodb访问基础服务接口
 */
public interface BaseMongoService<E extends BaseDynamicEntity> extends BaseService {
    E get(E entity, Query query);

    List<E> find(E entity, Query query);

    Page<E> find(E entity, Query query, PageInfo pageInfo);

    long count(E entity, Query query);

    void add(E entity);

    void update(E entity) throws BusinessException;

    void remove(E entity);
}
