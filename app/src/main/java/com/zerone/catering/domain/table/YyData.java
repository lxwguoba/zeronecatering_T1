package com.zerone.catering.domain.table;

/**
 * Created by Administrator on 2017/8/4.
 */

public class YyData {
    private String id;
    private String tableid;
    private String datetime_st;
    private String datetime_nd;
    private String datetime_st_format;
    private String datetime_nd_format;
    private String ordersn;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}

    public void setTableid(String tableid){this.tableid = tableid;}
    public String getTableid(){return this.tableid;}

    public void setDatetime_st(String datetime_st){this.datetime_st = datetime_st;}
    public String getDatetime_st(){return this.datetime_st;}

    public void setDatetime_nd(String datetime_nd){this.datetime_nd = datetime_nd;}
    public String getDatetime_nd(){return this.datetime_nd;}

    public void setDatetime_st_format(String datetime_st_format){this.datetime_st_format = datetime_st_format;}
    public String getDatetime_st_format(){return this.datetime_st_format;}

    public void setDatetime_nd_format(String datetime_nd_format){this.datetime_nd_format = datetime_nd_format;}
    public String getDatetime_nd_format(){return this.datetime_nd_format;}
}
