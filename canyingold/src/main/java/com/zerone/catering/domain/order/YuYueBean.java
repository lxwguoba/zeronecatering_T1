package com.zerone.catering.domain.order;

import java.io.Serializable;

/**
 * Created by on 2017/11/20 0020 14 47.
 * Author  LiuXingWen
 */

public class YuYueBean implements Serializable {


    /**
     * id : 13
     * weid : 1
     * branchid : 3
     * from_user : oBwtR050kf3jtyON9T3KcdcyQhEc
     * datenum : 2
     * mobile : 15217750014
     * realname : 汐颜
     * datetime_st : 1511165700
     * status : 1
     * create_at : 1511165354
     * is_delete : 0
     */

    private String id;
    private String weid;
    private String branchid;
    private String from_user;
    private String datenum;
    private String mobile;
    private String realname;
    private String datetime_st;
    private String status;
    private String create_at;
    private String is_delete;

    public YuYueBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getDatenum() {
        return datenum;
    }

    public void setDatenum(String datenum) {
        this.datenum = datenum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDatetime_st() {
        return datetime_st;
    }

    public void setDatetime_st(String datetime_st) {
        this.datetime_st = datetime_st;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }
}
