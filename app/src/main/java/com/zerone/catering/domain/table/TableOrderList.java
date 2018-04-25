package com.zerone.catering.domain.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 * 房间列表
 * 薛志豪
 */

public class TableOrderList {
    private List<TableOrderData> list;
    private int size;

    public TableOrderList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }
    public void setList(List<TableOrderData> list) {
        this.list = list;
    }
    public List<TableOrderData> getList() {
        return this.list;
    }

    public void add(TableOrderData tableOrderData) {
        this.list.add(tableOrderData);
    }

    public String getId(int i) {
        return this.list.get(i).getId();
    }
    public String getOrdersn(int i) {
        return this.list.get(i).getOrdersn();
    }
    public String getDatenum(int i) {
        return this.list.get(i).getDatenum();
    }
    public String getOrderid(int i) {
        return this.list.get(i).getOrderid();
    }
    public String getTableid(int i) {
        return this.list.get(i).getTableid();
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
