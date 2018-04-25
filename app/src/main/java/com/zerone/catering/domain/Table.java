package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Table implements Serializable {

    /**
     * branchid : 1
     * using : 0  使用状态
     * id : 81
     * tablename : 桌子的名称
     * weid : 1
     */
    private String branchid;
    private String using;
    private String id;
    private String tablename;
    private String weid;

    public Table() {
    }

    public Table(String branchid, String using, String id, String tablename, String weid) {
        this.branchid = branchid;
        this.using = using;
        this.id = id;
        this.tablename = tablename;
        this.weid = weid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public void setUsing(String using) {
        this.using = using;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getBranchid() {
        return branchid;
    }

    public String getUsing() {
        return using;
    }

    public String getId() {
        return id;
    }

    public String getTablename() {
        return tablename;
    }

    public String getWeid() {
        return weid;
    }
}
