package com.zerone.catering.refreshandclose.shuaxin;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class CloseLoginActvity  {
    private int page;
    private String name;


    public CloseLoginActvity(int page, String name) {
        this.page = page;
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
