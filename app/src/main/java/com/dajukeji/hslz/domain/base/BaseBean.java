package com.dajukeji.hslz.domain.base;

/**
 * Created by cdr on 2017/12/4.
 */

public class BaseBean {

    private int totalPage;
    private int currentPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
