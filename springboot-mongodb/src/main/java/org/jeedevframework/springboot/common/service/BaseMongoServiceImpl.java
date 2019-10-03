package org.jeedevframework.springboot.common.service;

import org.jeedevframework.springboot.common.mybatis.config.PaginationProperties;
import org.jeedevframework.springboot.common.exception.BusinessException;
import org.jeedevframework.springboot.common.dao.BaseMongoDao;
import org.jeedevframework.springboot.common.entity.BaseDynamicEntity;
import org.jeedevframework.springboot.common.entity.Page;
import org.jeedevframework.springboot.common.entity.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务层Mongo CRUD基础实现类
 */
public abstract class BaseMongoServiceImpl<R extends BaseMongoDao<E>, E extends BaseDynamicEntity> extends BaseServiceImpl implements BaseMongoService<E> {
    private static final int DEFAULT_PAGE_NUM = 1;

    @Autowired
    @Qualifier("paginationProperties")
    private PaginationProperties paginationProperties;
    @Autowired
    protected R dao;

    @Override
    public E get(E entity, Query query) {
        return dao.findOne(entity, query);
    }

    @Override
    public List<E> find(E entity, Query query) {
        return dao.find(entity, query);
    }

    @Override
    public Page<E> find(E entity, Query query, PageInfo pageInfo) {
        PageRequest pageRequest = startPage(pageInfo);
        if (query == null) {
            query = new Query();
        }
        long total = dao.count(entity, query);
        int pages = (int) (total / pageInfo.getPageSize() + (total % pageInfo.getPageSize() > 0 ? 1 : 0));
        List<E> list = dao.find(entity, query.with(pageRequest));

        return new Page<>(list, pageRequest.getPageNumber(), pageRequest.getPageSize(), total, pages);
    }

    @Override
    public long count(E entity, Query query) {
        return dao.count(entity, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(E entity) {
        entity.preInsert();
        dao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(E entity) throws BusinessException {
        try {
            entity.preUpdate();
            dao.save(entity);
        } catch (OptimisticLockingFailureException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(E entity) {
        dao.remove(entity);
    }

    /**
     * 开启分页
     *
     * @param pageInfo
     * @return
     */
    protected PageRequest startPage(PageInfo pageInfo) {
        int pageNum = getReasonablePageNum(pageInfo.getPageNum()) - 1;
        int pageSize = getReasonablePageSize(pageInfo.getPageSize());

        if (!CollectionUtils.isEmpty(pageInfo.getOrderBy())) {
            List<Sort.Order> orderList = new ArrayList<>();
            pageInfo.getOrderBy().forEach((key, value) -> {
                Sort.Order order;
                if (Sort.Direction.DESC.toString().equalsIgnoreCase(value)) {
                    order = Sort.Order.desc(key);
                } else {
                    order = Sort.Order.asc(key);
                }
                orderList.add(order);
            });
            Sort sort = Sort.by(orderList);
            return PageRequest.of(pageNum, pageSize, sort);
        }

        return PageRequest.of(pageNum, pageSize);
    }

    /**
     * 获取有效的页数
     *
     * @param pageNum
     * @return
     */
    private int getReasonablePageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        return pageNum;
    }

    /**
     * 获取有效的每页数据数
     *
     * @param pageSize
     * @return
     */
    private int getReasonablePageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = paginationProperties.getDefaultPageSize();
        } else if (pageSize > paginationProperties.getMaxPageSize()) {
            pageSize = paginationProperties.getMaxPageSize();
        }
        return pageSize;
    }

}
