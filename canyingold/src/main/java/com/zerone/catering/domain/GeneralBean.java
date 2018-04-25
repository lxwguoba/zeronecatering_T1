package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/19.
 */

public class GeneralBean implements Serializable {
    /**
     * status : response返回的状态码
     * data : response返回的提示消息
     */
    private int status;
    private String data;

    public GeneralBean() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
