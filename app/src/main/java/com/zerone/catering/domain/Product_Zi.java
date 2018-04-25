package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/1.
 */

public class Product_Zi implements Serializable {
    private  String pName_Zi;

    public Product_Zi(String pName_Zi) {
        this.pName_Zi = pName_Zi;
    }

    public String getpName_Zi() {
        return pName_Zi;
    }

    public void setpName_Zi(String pName_Zi) {
        this.pName_Zi = pName_Zi;
    }
}
