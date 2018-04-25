package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/14.
 */

public class GoodsUp implements Serializable {
    // 商品id
    private  String goodsid;
    // 规格id
    private String optionid;
    //数量
    private int number;

    public GoodsUp() {
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
