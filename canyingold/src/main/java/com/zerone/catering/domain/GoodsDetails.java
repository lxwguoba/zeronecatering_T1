package com.zerone.catering.domain;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class GoodsDetails {
    private String goodsid;
    private String categoryid;
    private String goodsname;
    private String price;
    private String thumb;
    private String hasoption;
    private  String optionsid;



    public GoodsDetails() {
    }

    public String getOptionsid() {
        return optionsid;
    }

    public void setOptionsid(String optionsid) {
        this.optionsid = optionsid;
    }

    public String getHasoption() {
        return hasoption;
    }

    public void setHasoption(String hasoption) {
        this.hasoption = hasoption;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
