package org.jeedevframework.springboot.common.dao;

import org.jeedevframework.springboot.common.entity.BaseDynamicEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Mongo数据CRUD基础接口
 */
public interface BaseMongoDao<T extends BaseDynamicEntity> {
    T findOne(T entity, Query query);

    List<T> find(T entity, Query query);

    long count(T entity, Query query);

    void insert(T entity);

    void save(T entity);

    void remove(T entity);
}
