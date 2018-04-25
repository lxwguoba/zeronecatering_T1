package com.zerone.catering.domain.table;

/**
 * Created by Administrator on 2017/8/5.
 */

public class TableGoodsData {
    private String id;
    private String total;
    private String price;
    private String title;
    private String marketprice;

    public void setId(String id){this.id=id;}
    public String getId(){return this.id;}
    public void setTotal(String total){this.total=total;}
    public String getTotal(){return this.total;}
    public void setPrice(String price){this.price=price;}
    public String getPrice(){return this.price;}
    public void setTitle(String title){this.title=title;}
    public String getTitle(){return this.title;}
    public void setMarketprice(String marketprice){this.marketprice = marketprice;}
    public String getMarketprice(){return this.marketprice;}
}
