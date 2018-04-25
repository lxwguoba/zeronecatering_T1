package com.zerone.catering.refreshandclose.shuaxin;

/**
 * Created by Administrator on 2017/8/10.
 * 用于标记是那个页面需要刷新数据
 */

public class ShuaXinShuJu  {
    private int page;
    private String name;

    public ShuaXinShuJu(int page, String name) {
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
