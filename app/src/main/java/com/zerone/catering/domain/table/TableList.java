package com.zerone.catering.domain.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 * 桌子列表
 * 薛志豪
 */

public class TableList {
    private List<TableData> list;
    private int size;

    public TableList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }

    public void setList(List<TableData> list) {
        this.list = list;
    }
    public List<TableData> getList() {
        return this.list;
    }

    public void add(TableData tableData) {
        this.list.add(tableData);
    }

    public String getId(int i){return this.list.get(i).getId();}
    public String getTablename(int i){return this.list.get(i).getTablename();}
    public String getMaxseat(int i){return this.list.get(i).getMaxseat();}
    public String getRoomid(int i){return this.list.get(i).getRoomid();}
    public String getUsing(int i){return this.list.get(i).getUsing();}
    public String getYdstatus(int i){return this.list.get(i).getYdstatus();}
    public String getTime_st(int i){return this.list.get(i).getTime_st();}
    public String getgetMinconsume(int i){return this.list.get(i).getMinconsume();}
    public void clearSize() {
        this.size = 0;
    }
    public void clearList() { this.list = new ArrayList<>();}

    public void addSize() {
        this.size = this.size + 1;
    }

    public int getSize() {
        return this.size;
    }
}
