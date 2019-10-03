package org.jeedevframework.springboot.common.dao;

import org.jeedevframework.springboot.common.dao.BaseMongoDao;
import org.jeedevframework.springboot.common.entity.BaseDynamicEntity;
import org.jeedevframework.springboot.utils.MongoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Mongo数据CRUD基类，实现
 */
public abstract class BaseMongoDaoImpl<E extends BaseDynamicEntity> implements BaseMongoDao<E> {
    @Value("${custom.mongo.transaction.enabled:false}")
    protected Boolean transactionEnabled = false;
    protected MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        if (transactionEnabled) {
            // 开启MongoDB事务
            mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);
        }
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public E findOne(E entity, Query query) {
        String collectionName = MongoUtils.getCollectionName(entity);
        if (query == null) {
            query = new Query();
        }

        return mongoTemplate.findOne(query, MongoUtils.getEntityClass(entity), collectionName);
    }

    @Override
    public List<E> find(E entity, Query query) {
        String collectionName = MongoUtils.getCollectionName(entity);
        if (query == null) {
            return mongoTemplate.findAll(MongoUtils.getEntityClass(entity), collectionName);
        }

        return mongoTemplate.find(query, MongoUtils.getEntityClass(entity), collectionName);
    }

    @Override
    public long count(E entity, Query query) {
        // mongo开启事务之后，不支持count操作
//        String collectionName = MongoUtils.getCollectionName(entity);
//        if (query == null) {
//            query = new Query();
//        }
//
//        return mongoTemplate.count(query, MongoUtils.getEntityClass(entity), collectionName);
        return this.find(entity, query).size();
    }

    @Override
    public void insert(E entity) {
        mongoTemplate.insert(entity, MongoUtils.getCollectionName(entity));
    }

    @Override
    public void save(E entity) {
        mongoTemplate.save(entity, MongoUtils.getCollectionName(entity));
    }

    @Override
    public void remove(E entity) {
        mongoTemplate.remove(entity, MongoUtils.getCollectionName(entity));
    }
}
