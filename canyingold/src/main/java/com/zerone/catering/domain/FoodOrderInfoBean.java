package com.zerone.catering.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */

public class FoodOrderInfoBean  implements Serializable{
    private String foodName;
    private Integer foodCount;
    private String foodPrice;
    private String foodId;
    private String optionid;
    private String specs;
    private String  title;

    private   Integer hasoption;




    public FoodOrderInfoBean() {
    }

    public FoodOrderInfoBean(String foodName, int foodCount, String foodPrice, String foodId,String optionid,String specs,String  title,Integer hasoption) {
        this.foodName = foodName;
        this.foodCount = foodCount;
        this.foodPrice = foodPrice;
        this.foodId=foodId;
        this.optionid=optionid;
        this.specs=specs;
        this.title=title;
        this.hasoption=hasoption;
    }

    public Integer getHasoption() {
        return hasoption;
    }

    public void setHasoption(Integer hasoption) {
        this.hasoption = hasoption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
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

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getOptionid() {
        return optionid;
    }
    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }
    @Override
    public String toString() {
        return "FoodOrderInfoBean{" +
                "foodName='" + foodName + '\'' +
                ", foodCount=" + foodCount +
                ", foodPrice='" + foodPrice + '\'' +
                ", foodId='" + foodId + '\'' +
                '}';
    }
}
