package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by on 2017/10/27 0027 15 06.
 * Author  LiuXingWen
 */

public class TestOptionBean implements Serializable {
     private  String key;
     private  String value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
