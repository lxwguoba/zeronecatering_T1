package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/7.
 */

public class FoodDetailsListBean implements Serializable {
    private  String foodId;
    private  String foodName;
    private  String foodCount;
    private  String foodPrice;
    private  String foodSuccesPrice;
    private  String  yongjin;


    public FoodDetailsListBean(String foodId, String foodName, String foodCount, String foodPrice, String foodSuccesPrice, String yongjin) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodCount = foodCount;
        this.foodPrice = foodPrice;
        this.foodSuccesPrice = foodSuccesPrice;
        this.yongjin = yongjin;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(String foodCount) {
        this.foodCount = foodCount;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodSuccesPrice() {
        return foodSuccesPrice;
    }

    public void setFoodSuccesPrice(String foodSuccesPrice) {
        this.foodSuccesPrice = foodSuccesPrice;
    }

    public String getYongjin() {
        return yongjin;
    }

    public void setYongjin(String yongjin) {
        this.yongjin = yongjin;
    }


}
