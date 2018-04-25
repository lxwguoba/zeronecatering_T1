package com.zerone.catering.domain.table;

/**
 * Created by Administrator on 2017/8/2.
 * 房间数据
 * 薛志豪
 */

public class Room {
    private String id;         //ID
    private String weid;       //微信标识
    private String branchid;   //分店ID
    private String roomname;   //房间名称

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getWeid() {
        return this.weid;
    }

    private void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBranchid() {
        return this.branchid;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomname() {
        return this.roomname;
    }
}
