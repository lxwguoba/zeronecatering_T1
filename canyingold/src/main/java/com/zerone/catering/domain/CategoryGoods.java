package com.zerone.catering.domain;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class CategoryGoods  {

    private String  id;
    private String     name;
    private String   parentid;

    public CategoryGoods(){

    }

    public CategoryGoods(String id, String name, String parentid) {
        this.id = id;
        this.name = name;
        this.parentid = parentid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "CategoryGoods{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}
