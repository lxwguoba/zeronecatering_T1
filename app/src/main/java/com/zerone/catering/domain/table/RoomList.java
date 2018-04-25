package com.zerone.catering.domain.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 * 房间列表
 * 薛志豪
 */

public class RoomList {
    private List<Room> list;
    private int size;

    public RoomList() {
        this.list = new ArrayList<>();
        this.size = 0;
    }
    public void setList(List<Room> list) {
        this.list = list;
    }
    public List<Room> getList() {
        return this.list;
    }

    public void add(Room room) {
        this.list.add(room);
    }

    public String getId(int i) {
        return this.list.get(i).getId();
    }

    public String getRoomname(int i) {
        return this.list.get(i).getRoomname();
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
