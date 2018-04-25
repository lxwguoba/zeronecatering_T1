package com.zerone.catering.domain.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class YyList {
    private List<YyData> list;
    private int size;

    public YyList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }

    public void setList(List<YyData> list) {
        this.list = list;
    }
    public List<YyData> getList() {
        return this.list;
    }
    public void add(YyData yydata) {
        this.list.add(yydata);
    }

    public String getId(int i){return this.list.get(i).getId();}
    public String getTableid(int i){return this.list.get(i).getTableid();}
    public String getDatetime_st(int i){return this.list.get(i).getDatetime_st();}
    public String getDatetime_nd(int i){return this.list.get(i).getDatetime_nd();}
    public String getDatetime_st_format(int i){return this.list.get(i).getDatetime_st_format();}
    public String getDatetime_nd_format(int i){return this.list.get(i).getDatetime_nd_format();}
    public String getOrderSn(int i){return this.list.get(i).getOrdersn();}

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
