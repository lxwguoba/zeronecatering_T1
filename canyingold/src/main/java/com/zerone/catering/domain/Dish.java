package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/28.
 * �˵�ʵ����
 */

public class Dish implements Serializable {


    /**
     * branchid : 1 �ֵ� id
     * list_thumb :
     * thumb : images/2017/06/tEgh4ssS4cN3dtRC0qWtSSEgENGEcc.jpg
     * description : ��Ʒ����
     * displayorder : 0
     * parentid : 0
     * enabled : 0
     * url :
     * isrecommand : 0
     * name : ˿��  ����
     * commission : 0
     * id : 36   ��id
     * sn :
     * weid : 1
     */
    private String branchid;
    private String list_thumb;
    private String thumb;
    private String description;
    private String displayorder;
    private String parentid;
    private String enabled;
    private String url;
    private String isrecommand;
    private String name;
    private String commission;
    private String id;
    private String sn;
    private String weid;

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public void setList_thumb(String list_thumb) {
        this.list_thumb = list_thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsrecommand(String isrecommand) {
        this.isrecommand = isrecommand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getBranchid() {
        return branchid;
    }

    public String getList_thumb() {
        return list_thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public String getParentid() {
        return parentid;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getUrl() {
        return url;
    }

    public String getIsrecommand() {
        return isrecommand;
    }

    public String getName() {
        return name;
    }

    public String getCommission() {
        return commission;
    }

    public String getId() {
        return id;
    }

    public String getSn() {
        return sn;
    }

    public String getWeid() {
        return weid;
    }
}
