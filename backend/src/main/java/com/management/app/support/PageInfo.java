package com.management.app.support;

import java.util.List;

/**
 * 分页数据包装
 */
public class PageInfo {

    /**
     * 页号
     */
    private int pageNumber;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 共计多少
     */
    private int totalCount;

    /**
     * 共计多少页
     */
    private int totalNumber;

    /**
     * 内容
     */
    private List<?> content;

    public PageInfo(int pageNumber, int pageSize, int totalCount, List<?> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.content = content;
        this.totalNumber=(int)Math.ceil((this.totalCount*1.0/this.pageSize));
    }

    public PageInfo() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<?> getContent() {
        return content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }
}
