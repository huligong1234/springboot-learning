package org.jeedevframework.springboot.common.entity;

import java.util.List;

/**
 * 数据分页
 */
public class Page<T> extends PageInfo {
    private List<T> list;
    private long total;
    private int pages;

    public Page(com.github.pagehelper.Page<T> page) {
        super(page.getPageNum(), page.getPageSize());
        this.list = page.getResult();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

    public Page(org.springframework.data.domain.Page<T> page) {
        // Mongo page number start from 0
        super(page.getNumber() + 1, page.getSize());
        this.list = page.getContent();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
    }

    public Page(List<T> list, int pageNum, int pageSize, long total, int pages) {
        super(pageNum, pageSize);
        this.list = list;
        this.total = total;
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
