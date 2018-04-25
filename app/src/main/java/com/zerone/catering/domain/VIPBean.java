package com.zerone.catering.domain;

import java.util.List;

/**
 * Created by on 2017/10/19 0019 14 33.
 * Author  LiuXingWen
 */

public class VIPBean  {

    /**
     * status : 1
     * data : [{"id":"253","weid":"1","goodsid":"53","levelid":"1","levelprice":"21.00","levelcommission":"5","levelname":"ceshi"},{"id":"279","weid":"1","goodsid":"53","levelid":"6","levelprice":"21.00","levelcommission":"5","levelname":"6.8折会员"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 253
         * weid : 1
         * goodsid : 53
         * levelid : 1
         * levelprice : 21.00
         * levelcommission : 5
         * levelname : ceshi
         */

        private String id;
        private String weid;
        private String goodsid;
        private String levelid;
        private String levelprice;
        private String levelcommission;
        private String levelname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWeid() {
            return weid;
        }

        public void setWeid(String weid) {
            this.weid = weid;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getLevelid() {
            return levelid;
        }

        public void setLevelid(String levelid) {
            this.levelid = levelid;
        }

        public String getLevelprice() {
            return levelprice;
        }

        public void setLevelprice(String levelprice) {
            this.levelprice = levelprice;
        }

        public String getLevelcommission() {
            return levelcommission;
        }

        public void setLevelcommission(String levelcommission) {
            this.levelcommission = levelcommission;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }
    }
}
