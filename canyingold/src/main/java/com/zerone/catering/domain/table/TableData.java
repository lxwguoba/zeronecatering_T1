package com.zerone.catering.domain.table;

/**
 * Created by Administrator on 2017/8/2.
 * 桌子数据
 * 薛志豪
 */

public class TableData {
    private String id;
    private String  roomid;
    private String tablename;
    private String maxseat;
    private String using;
    private String ydstatus;
    //这个是开始预约时间
    private String time_st;
    //最低消费的值
    private String minconsume;

    public String getMinconsume() {
        return minconsume;
    }
    public void setMinconsume(String minconsume) {
        this.minconsume = minconsume;
    }
    public void setId(String id){this.id=id;}
    public String getId(){return this.id;}
    public void setRoomid(String roomid){this.roomid=roomid;}
    public String getRoomid(){return this.roomid;}
    public void setTablename(String tablename){this.tablename = tablename;}
    public String getTablename(){return this.tablename;}
    public void setMaxseat(String maxseat){this.maxseat = maxseat;}
    public String getMaxseat(){return this.maxseat;}
    public void setUsing(String using){this.using = using;}
    public String getUsing(){return this.using;}
    public void setYdstatus(String ydstatus){this.ydstatus = ydstatus;}
    public String getYdstatus(){return this.ydstatus;}
    public void setTime_st(String time_st){this.time_st = time_st;}
    public String getTime_st(){return this.time_st;}
}
