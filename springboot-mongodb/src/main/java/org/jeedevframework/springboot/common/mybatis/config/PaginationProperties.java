package org.jeedevframework.springboot.common.mybatis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 分页参数配置
 */
@Component("paginationProperties")
@ConfigurationProperties("pagination")
public class PaginationProperties {
    private static final int MAX_PAGE_SIZE = 2000;
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private int maxPageSize = MAX_PAGE_SIZE;
    private int pageSize = DEFAULT_PAGE_SIZE;

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getDefaultPageNum() {
        return DEFAULT_PAGE_NUM;
    }

    public int getDefaultPageSize() {
        return pageSize;
    }
}
