package com.zerone.catering.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class DishItems implements Serializable {


    /**
     * goodsnum : 1
     * goodslist : [{"thumb":"http://catering.dz-ck.com/resource/attachment/images/2017/06/bKZ2duaQHB0MkzKAQ2D9MM2FnN0U02.jpg","goodsid":"1709","price":null,"options":[{"optionid":"3","optionname":"����"},{"optionid":"4","optionname":"����"},{"optionid":"5","optionname":"΢��"}],"hasoption":1,"goodsname":"��ͱ�����"}]
     */
    private int goodsnum;
    private List<GoodslistEntity> goodslist;

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public void setGoodslist(List<GoodslistEntity> goodslist) {
        this.goodslist = goodslist;
    }

    public int getGoodsnum() {
        return goodsnum;
    }

    public List<GoodslistEntity> getGoodslist() {
        return goodslist;
    }

    public static class GoodslistEntity {
        /**
         * thumb : http://catering.dz-ck.com/resource/attachment/images/2017/06/bKZ2duaQHB0MkzKAQ2D9MM2FnN0U02.jpg
         * goodsid : 1709
         * price : null
         * options : [{"optionid":"3","optionname":"����"},{"optionid":"4","optionname":"����"},{"optionid":"5","optionname":"΢��"}]
         * hasoption : 1
         * goodsname : ��ͱ�����
         */
        private String thumb;
        private String goodsid;
        private String price;
        private List<OptionsEntity> options;
        private int hasoption;
        private String goodsname;
        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setOptions(List<OptionsEntity> options) {
            this.options = options;
        }

        public void setHasoption(int hasoption) {
            this.hasoption = hasoption;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getThumb() {
            return thumb;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public String getPrice() {
            return price;
        }

        public List<OptionsEntity> getOptions() {
            return options;
        }

        public int getHasoption() {
            return hasoption;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public class OptionsEntity {
            /**
             * optionid : 3
             * optionname : ����
             */
            private String optionid;
            private String optionname;

            public void setOptionid(String optionid) {
                this.optionid = optionid;
            }

            public void setOptionname(String optionname) {
                this.optionname = optionname;
            }

            public String getOptionid() {
                return optionid;
            }

            public String getOptionname() {
                return optionname;
            }
        }
    }

    @Override
    public String toString() {
        return "DishItems{" +
                "goodsnum=" + goodsnum +
                ", goodslist=" + goodslist +
                '}';
    }
}
