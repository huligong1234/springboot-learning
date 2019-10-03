package org.jeedevframework.springboot.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务层基础实现类
 */
public abstract class BaseServiceImpl implements BaseService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
