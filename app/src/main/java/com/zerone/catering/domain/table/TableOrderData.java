package com.zerone.catering.domain.table;

/**
 * Created by Administrator on 2017/8/7.
 */

public class TableOrderData {
    private String id;
    private String orderid;
    private String dispatchchoice;
    private String tableid;
    private String datenum;
    private String datetime_st;
    private String datetime_nd;
    private String status;
    private String ordersn;

    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}
    public void setOrderid(String orderid){this.orderid = orderid;}
    public String getOrderid(){return this.orderid;}
    public void setDispatchchoice(String dispatchchoice){ this.dispatchchoice=dispatchchoice;}
    public String getDispatchchoice(){return this.dispatchchoice;}
    public void setTableid(String tableid){this.tableid = tableid;}
    public String getTableid(){return this.tableid;}
    public void setDatenum(String datenum){
        this.datenum = datenum;
    }
    public String getDatenum() {
        return this.datenum;
    }
    public void setDatetime_st(String datetime_st){
        this.datetime_st = datetime_st;
    }
    public String getDatetime_st(){
        return this.datetime_st;
    }

    public void setDatetime_nd(String datetime_nd) {
        this.datetime_nd = datetime_nd;
    }

    public String getDatetime_nd() {
        return this.datetime_nd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
    public String getOrdersn(){
        return this.ordersn;
    }
}
