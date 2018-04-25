package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/3.
 */

public class FoodXi implements Serializable {

    /**
     * id : 37
     * name : 凉菜
     * parentid : 0
     */

    private String id;
    private String name;
    private String parentid;

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
        return "FoodXi{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}
