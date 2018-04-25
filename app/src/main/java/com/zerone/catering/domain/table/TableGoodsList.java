package com.zerone.catering.domain.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */

public class TableGoodsList {
    private List<TableGoodsData> list;
    private int size;

    public TableGoodsList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }
    public void setList(List<TableGoodsData> list) {
        this.list = list;
    }
    public List<TableGoodsData> getList() {
        return this.list;
    }

    public void add(TableGoodsData tableGoodsData) {
        this.list.add(tableGoodsData);
    }

    public String getId(int i){
        return this.list.get(i).getId();
    }
    public String  getTotal(int i){
        return this.list.get(i).getTotal();
    }
    public String getTitle(int i){
        return this.list.get(i).getTitle();
    }
    public String getPrice(int i){
        return this.list.get(i).getPrice();
    }
    public String getMarketprice(int i){
        return this.list.get(i).getMarketprice();
    }

    public void clearSize() {
        this.size = 0;
    }
    public void addSize() {
        this.size = this.size + 1;
    }
    public int getSize() {
        return this.size;
    }
}
