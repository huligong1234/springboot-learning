package org.jeedevframework.springboot.common.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页信息
 */
public class PageInfo {
    private static final String ORDER_BY_SEPARATOR = ",";
    private static final String DIRECTION_SEPARATOR = " ";
    private Integer pageNum;
    private Integer pageSize;
    /**
     * key = fieldName
     * value = sortDirection
     */
    private Map<String, String> orderBy;

    public PageInfo(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageInfo(Integer pageNum, Integer pageSize, String orderBy) {
        this(pageNum, pageSize);

        Map<String, String> orderMap = null;
        if (StringUtils.isNotBlank(orderBy)) {
            orderMap = new LinkedHashMap<>();
            String[] orderByArr = orderBy.split(ORDER_BY_SEPARATOR);
            for (String ob : orderByArr) {
                if (StringUtils.isBlank(ob)) {
                    continue;
                }
                String key = ob;
                String value = null;
                int i = ob.lastIndexOf(DIRECTION_SEPARATOR);
                if (i >= 0) {
                    key = ob.substring(0, i);
                    value = ob.substring(i + 1);
                }
                if (StringUtils.isNotBlank(key)) {
                    orderMap.put(key, value);
                }
            }
        }
        this.orderBy = orderMap;
    }

    public PageInfo(Integer pageNum, Integer pageSize, Map<String, String> orderBy) {
        this(pageNum, pageSize);

        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }


}
