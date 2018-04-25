package com.zerone.catering.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 * 测试用的菜系详情
 */

public class FoodItemsBean implements Serializable {

    /**
     * goodsnum : 11
     * goodslist : [{"goodsid":"48","goodsname":"墨西哥鸡肉卷十小可","price":"8.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/Xy31zXxenGZ25ZXEM1Z5j5njyeUJJj.jpg"},{"goodsid":"49","goodsname":"凉瓜炒蛋饭","price":"11.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/AL3Q5zmEOt3h6QNdXTq69z0z3pXhN9.jpg"},{"goodsid":"1709","goodsname":"早餐冰激凌","price":"25.00","hasoption":1,"options":[{"optionid":"6","optionname":"特辣"},{"optionid":"3","optionname":"微辣"},{"optionid":"4","optionname":"中辣"},{"optionid":"5","optionname":"超级无敌辣"},{"optionid":"7","optionname":"超级辣"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/bKZ2duaQHB0MkzKAQ2D9MM2FnN0U02.jpg"},{"goodsid":"1708","goodsname":"原味鸡块","price":"10.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/Xpxm99fMbxP0TDuHkAbT0hUXEMdJXg.jpg"},{"goodsid":"1707","goodsname":"油炸鸡翅","price":"50.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/fWyfG13sDJfp21QjF65m18J383qPZL.jpg"},{"goodsid":"1706","goodsname":"汉堡甜挞","price":"15.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/qxZOUOrG7I7PAPdUdPr76UXDvZdGUj.jpg"},{"goodsid":"1705","goodsname":"丝瓜冰淇淋","price":"11.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/OQ2Wkrc3Kw447cxZXZg22W7zY26Yy2.jpg"},{"goodsid":"2526","goodsname":"丝瓜","price":"10.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/H6n36t7TZbNatnwWhr37RBsbIiKwp3.jpg"},{"goodsid":"2527","goodsname":"叉烧拉面","price":"20.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/uKqurky6QQLKTKt0tLkkT0dtFczyrQ.jpg"},{"goodsid":"2528","goodsname":"水果汁","price":"12.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/mj835zElYQT8aaaJjjAz5IAQw8tejA.jpg"},{"goodsid":"2529","goodsname":"草莓奶油冰淇淋","price":"20.00","hasoption":"0","options":[{"optionid":0,"optionname":"无"}],"thumb":"http://ctest.greengod.cn/resource/attachment/images/2017/06/YHHf7H5YyTaY5464Fq7H9zhp0q44Uo.jpg"}]
     */

    private int goodsnum;
    private List<GoodslistBean> goodslist;

    public int getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public List<GoodslistBean> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<GoodslistBean> goodslist) {
        this.goodslist = goodslist;
    }

    public static class GoodslistBean {
        /**
         * goodsid : 48
         * goodsname : 墨西哥鸡肉卷十小可
         * price : 8.00
         * hasoption : 0
         * options : [{"optionid":0,"optionname":"无"}]
         * thumb : http://ctest.greengod.cn/resource/attachment/images/2017/06/Xy31zXxenGZ25ZXEM1Z5j5njyeUJJj.jpg
         */

        private String goodsid;
        private String goodsname;
        private String price;
        private String hasoption;
        private String thumb;
        private List<OptionsBean> options;

        @Override
        public String toString() {
            return "GoodslistBean{" +
                    "goodsid='" + goodsid + '\'' +
                    ", goodsname='" + goodsname + '\'' +
                    ", price='" + price + '\'' +
                    ", hasoption='" + hasoption + '\'' +
                    ", thumb='" + thumb + '\'' +
                    '}';
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
        }

        public String getGoodsname() {
            return goodsname;
        }

        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHasoption() {
            return hasoption;
        }

        public void setHasoption(String hasoption) {
            this.hasoption = hasoption;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public List<OptionsBean> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsBean> options) {
            this.options = options;
        }

        public static class OptionsBean {
            /**
             * optionid : 0
             * optionname : 无
             */

            private int optionid;
            private String optionname;

            public int getOptionid() {
                return optionid;
            }

            public void setOptionid(int optionid) {
                this.optionid = optionid;
            }

            public String getOptionname() {
                return optionname;
            }

            public void setOptionname(String optionname) {
                this.optionname = optionname;
            }
        }
    }
}
