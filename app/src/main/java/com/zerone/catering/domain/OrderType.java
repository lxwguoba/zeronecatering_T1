package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/19.
 */

public class OrderType implements Serializable {

    //1==外卖订单
    //2==预约就餐订单
    //3==现场订单订单
    private int orderType;
    private String typeName;

    public OrderType(int orderType, String typeName) {
        this.orderType = orderType;
        this.typeName = typeName;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
