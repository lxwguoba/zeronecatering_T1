package com.zerone.catering.refreshandclose.shuaxin;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public class OrderListFlush  {
      private  Integer pageid;
      private  String  pageName;


    public OrderListFlush(Integer pageid, String pageName) {
        this.pageid = pageid;
        this.pageName = pageName;
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
