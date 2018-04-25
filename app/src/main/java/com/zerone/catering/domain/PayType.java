package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/19.
 */

public class PayType implements Serializable {
    //状态码所对应的名称
    //1==在线余额支付
    //2==在线微信支付,微支付单号：transid
    // 3==现场现金支付,现场确认人ID号confirmedby
    // 4==现场刷卡支付,现场确认人ID号：confirmedby
    // 5==现场支付宝付,现场确认人ID号：confirmedby
    // 6==现场微信支付,现场确认人ID号：confirmedby
    // 7==在线手动确认付款,确认人ID号：confirmedby
    //支付状态码
    private int payTypeStaus;
    //支付名称
    private String payTypeName;

    public PayType(int payTypeStaus, String payTypeName) {
        this.payTypeStaus = payTypeStaus;
        this.payTypeName = payTypeName;
    }

    public int getPayTypeStaus() {
        return payTypeStaus;
    }

    public void setPayTypeStaus(int payTypeStaus) {
        this.payTypeStaus = payTypeStaus;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
