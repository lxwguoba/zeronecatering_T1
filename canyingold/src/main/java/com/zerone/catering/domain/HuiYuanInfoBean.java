package com.zerone.catering.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 *
 */

public class HuiYuanInfoBean implements Serializable {

    /**
     * membernum : 22
     * memberlist : [{"id":"76","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0f21cXnzF1sQMYsgIS78048","realname":"薛志豪","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1500356617","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmhead/PiajxSqBRaEJPFpsKXbvnGemCUbWf24QUvRWE3TL08vZgjd9xSyC3iaA/0","title":""},{"id":"75","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0UVYlpHBNrP1kJ9cwxqKX2s","realname":"自己","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1500344147","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmClMAKN2o6RXiclAyufTMmYHROuOw19YkqukBxHuJo5jYicmGZYoGTdnpvMgHicFRia8TX6vk475x8GgKha2ARMMh2I/0","title":""},{"id":"73","weid":"3","branchid":"0","shareid":"38","from_user":"osHXB0c_hFwZrI57XIZnzZiMxNs4","realname":"资医堂问题肌肤全面修复张主任","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1500026316","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmAib9iaBe5DbbC33gBRD3wcVpj27x29rNJGEYtzQibmLGNeEwiajaGVPkHM41Lg0IbFGu9icJTNlEf3GBGCNwqheFK0J/0","title":""},{"id":"65","weid":"3","branchid":"5","shareid":"41","from_user":"osHXB0WHn7dmhAhQ7akB_ulEoy4w","realname":"锅巴来也","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499671026","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/W6WPqL2EUdZt1nItE6pgqSYc9A717adYhtFibfI2Uibpc9Tfnpqn4Mzt2KaPOlmibaZH4wGtOMkKArib754icMUibtQSJMXWRib2MCM/0","title":""},{"id":"63","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0V-LvTzGtpuOQgcdrO5eAho","realname":"Dog Dog Duck","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499654794","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"2","avatar":"http://wx.qlogo.cn/mmopen/IbQhd2Y9oKfT6kaJAw74wPCPCBCncTV1Urq8XicZq6iayQiaTJNpAmwIraZic0kgyvicicLDJ6aibtMu1psxofuuhkr1zViaKXn0eUEn/0","title":""},{"id":"60","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0QqnDcjqjLF_QmnQ4nplrRk","realname":"?王桀","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499076371","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmB9E7gSQniaibBXD9w2s7npb8pFX9AeA4zdiaY8zoaCribWUFcZkbwQOkUCCLS6S3XVP6MMB3XgsKzOaGlZw8DBRG1J/0","title":""},{"id":"59","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0TLLOindw6AvD_mvEeCsygg","realname":"千道芸缘","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499068000","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/W6WPqL2EUdaYXic1QDRBDiaSDpgR48xKFfLbFibnGpztZHTLAFW2tDeAlF1F8kVb321ySbD31tOHiaiaF6c20Sfdwd4MzbrV4NiaP4/0","title":""},{"id":"58","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0fZRwUuJ2eLl-zynynArgqE","realname":"Chi","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499059838","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"2","avatar":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEKcxwDTxJFDANTbK3mg6GT1mo4oYicxARfROpr9G8qQwJNg3iaoHBj3OajYAfiaMStYWOiaf7qkm0uC6w/0","title":""},{"id":"57","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0SVHPsN8mN4eqCj1e3nOkes","realname":"王炳橙","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499056967","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"2","avatar":"http://wx.qlogo.cn/mmopen/IbQhd2Y9oKftaicRo0K9VnxSGult8I97mhQGd9GB2EX4Ne5BicWerCNBcqgibAKwkPCgdOibj2GIBLz3tgqhFWHJjdfHodibNBddu/0","title":""},{"id":"56","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0chVYQHMjuaSSOd2_Xn2n2A","realname":"A王楠 13246671028","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499055405","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmBto2XyuMF6uXO8XvxWaR5kOKLicH0xt7c9O01TpZAlbjc39sR0kOncalRcuicpIzbPBT2BqrM8T98ajicuXOmiaQc0/0","title":""},{"id":"55","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0W9_1ye5l0L2Sm8suLPME_0","realname":"掌尚易商","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1499054816","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/W6WPqL2EUda90KJWsX8WibG9Tw6NmhrvtMjlBibFQSOP4AVic8NaQJCwclXEGIib0KxniaqT1hsgf9fRbmHRSYkSodJjTQ4zfUpJw/0","title":""},{"id":"54","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0ZoyzzpdQdWpW-hYaKVhOSY","realname":"时光取名叫无心","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498815493","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmBm48ibnqZ411lGITvLew1AzP7tKXMoFWxKAkW3NKQmsvdC97iazibvkMDupVmQoVRyLIic2njoicGvbSd3ve7KLjuPV/0","title":""},{"id":"53","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0VP4PqMHmACp6Z5KapP9xIo","realname":"沈莉","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498810303","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"2","avatar":"http://wx.qlogo.cn/mmopen/X2KwIHKw78KgOw7GQ9ibCe4P0oFvHN23wNzYa0Q4rssInn56oRiaiachtiaVGZOgWeGIBuCkyyx2TibFiaRUzYh3umtXahibeibE4qFf/0","title":""},{"id":"52","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0TKha2FY9pKUr_lddx93hnw","realname":"倾听雪落","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498803732","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"6","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmCa34suMicNicyia5EERlknibHZIn13qB8Jo7spu3hkBUichT8qDcNy0V0JiclRumXhURIB59hYzEJZ7HCIEUnNO5FWFt/0","title":""},{"id":"49","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0Zl3X1tXMCM46CFN_mBe2us","realname":"果粒橙","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498530458","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/X2KwIHKw78LFrmCE3REMlnvhNwy0UnvJHX8cZxBaRKdZx4UOA0jGaVBp8CauBQicUQuM1mZ5PpdNFdHlZQSrYPL8ciclQIe9Go/0","title":""},{"id":"48","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0SBi-q79x-m-wAnjqLw7EkY","realname":"追梦小窝","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498530389","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/vmfNAPoxJbuJia29qW7xdKDxLibG2cGyFPVwI8khF2K81lesJMOHISNcyDicxXZkC35AGmW1awndVe6vz00eMiabSXdiahQQWDsvp/0","title":""},{"id":"47","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0UNUIQQXmVrgwPlT20WusUE","realname":"??","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498441898","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"4","avatar":"http://wx.qlogo.cn/mmopen/X2KwIHKw78Jh6EqL8ms7eZXdoib7KVNk2gch67QsMlZsUgGyI2KWzFiaJaBENGCpeic57atvWUXgqLnm2gJReHTvaqIx0nEtI6o/0","title":""},{"id":"46","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0S7zFzYXN0WznQ9PQ-Z_Otw","realname":"丽","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498363006","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/W6WPqL2EUdY2ZGhLW3WEWqqJK25p4a20TooNDVjpYApzK2ZMHkWzstSqUhRJcBeVAia1ic63ev5Z3uRNs9A9WQ4uJJab9pKUbX/0","title":""},{"id":"44","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0Qwm3vdQxfkTNHyLSXtd1Wo","realname":"二 哥","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498019896","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"4","avatar":"http://wx.qlogo.cn/mmopen/ibH3DRwic4UmAzSNTZ4mg8ZausiaRbQDWKdAYYxOFQtMdv1tsib5QfHqgkWxEmMftSRuGVR1aoepxqBYbmu1jCySkx4CWVKbiaQcB/0","title":""},{"id":"43","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0d25jqN8SZgXrWS-BnBPy5o","realname":"彬哥","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498019891","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"0","avatar":"http://wx.qlogo.cn/mmopen/W6WPqL2EUdZt1nItE6pgqXBRYhkbf7kkToNlXlwiaH8PMCV6Yb5zd5gibHLRu0KOcdh6sGmDerZMO3AOReUwIWpZlxUPSpCf1K/0","title":""},{"id":"42","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0YgWqB8Ozczu_wTdkzXail4","realname":"陈宏","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1498015907","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"6","avatar":"http://wx.qlogo.cn/mmopen/X2KwIHKw78Jh6EqL8ms7eepdX75pkADoVXAp5IibVN26NmSPHVwHTWKDhwLS7m4p1JrWiatnI5fzmkp9WvWB7wEIicCEYcu1Bian/0","title":""},{"id":"38","weid":"3","branchid":"0","shareid":"0","from_user":"osHXB0bBEKSmueVTyeCnm3lem6OM","realname":"O2O云服务平台-赵向阳","mobile":"","myqq":"","commission":"0.00","zhifu":"0.00","content":null,"createtime":"1497946951","flag":"0","levelid":"0","flagtime":"0","credit2":"0","status":"1","clickcount":"0","mbbinded":"0","birthdate":"0000-00-00","remark":"","isworking":"0","credit1":"4","avatar":"http://wx.qlogo.cn/mmopen/X2KwIHKw78Lk3kqHNfzNFgaQ2iaeKwMPe5FU0queHibgo0jI7ogoib6zhsgVDicFeEpq7nGvaGl5M0w7iaD2AnqxA21FMmKpjqMFr/0","title":""}]
     */

    private int membernum;
    private List<MemberlistBean> memberlist;

    public int getMembernum() {
        return membernum;
    }

    public void setMembernum(int membernum) {
        this.membernum = membernum;
    }

    public List<MemberlistBean> getMemberlist() {
        return memberlist;
    }

    public void setMemberlist(List<MemberlistBean> memberlist) {
        this.memberlist = memberlist;
    }

    public static class MemberlistBean  implements Serializable{
        /**
         * id : 76
         * weid : 3
         * branchid : 0
         * shareid : 0
         * from_user : osHXB0f21cXnzF1sQMYsgIS78048
         * realname : 薛志豪
         * mobile :
         * myqq :
         * commission : 0.00
         * zhifu : 0.00
         * content : null
         * createtime : 1500356617
         * flag : 0
         * levelid : 0
         * flagtime : 0
         * credit2 : 0
         * status : 1
         * clickcount : 0
         * mbbinded : 0
         * birthdate : 0000-00-00
         * remark :
         * isworking : 0
         * credit1 : 0
         * avatar : http://wx.qlogo.cn/mmhead/PiajxSqBRaEJPFpsKXbvnGemCUbWf24QUvRWE3TL08vZgjd9xSyC3iaA/0
         * title :
         */

        private String id;
        private String weid;
        private String branchid;
        private String shareid;
        private String from_user;
        private String realname;
        private String mobile;
        private String myqq;
        private String commission;
        private String zhifu;
        private Object content;
        private String createtime;
        private String flag;
        private String levelid;
        private String flagtime;
        private String credit2;
        private String status;
        private String clickcount;
        private String mbbinded;
        private String birthdate;
        private String remark;
        private String isworking;
        private String credit1;
        private String avatar;
        private String title;

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

        public String getBranchid() {
            return branchid;
        }

        public void setBranchid(String branchid) {
            this.branchid = branchid;
        }

        public String getShareid() {
            return shareid;
        }

        public void setShareid(String shareid) {
            this.shareid = shareid;
        }

        public String getFrom_user() {
            return from_user;
        }

        public void setFrom_user(String from_user) {
            this.from_user = from_user;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMyqq() {
            return myqq;
        }

        public void setMyqq(String myqq) {
            this.myqq = myqq;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getZhifu() {
            return zhifu;
        }

        public void setZhifu(String zhifu) {
            this.zhifu = zhifu;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getLevelid() {
            return levelid;
        }

        public void setLevelid(String levelid) {
            this.levelid = levelid;
        }

        public String getFlagtime() {
            return flagtime;
        }

        public void setFlagtime(String flagtime) {
            this.flagtime = flagtime;
        }

        public String getCredit2() {
            return credit2;
        }

        public void setCredit2(String credit2) {
            this.credit2 = credit2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getClickcount() {
            return clickcount;
        }

        public void setClickcount(String clickcount) {
            this.clickcount = clickcount;
        }

        public String getMbbinded() {
            return mbbinded;
        }

        public void setMbbinded(String mbbinded) {
            this.mbbinded = mbbinded;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIsworking() {
            return isworking;
        }

        public void setIsworking(String isworking) {
            this.isworking = isworking;
        }

        public String getCredit1() {
            return credit1;
        }

        public void setCredit1(String credit1) {
            this.credit1 = credit1;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        @Override
        public String toString() {
            return "MemberlistBean{" +
                    "id='" + id + '\'' +
                    ", weid='" + weid + '\'' +
                    ", branchid='" + branchid + '\'' +
                    ", shareid='" + shareid + '\'' +
                    ", from_user='" + from_user + '\'' +
                    ", realname='" + realname + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", myqq='" + myqq + '\'' +
                    ", commission='" + commission + '\'' +
                    ", zhifu='" + zhifu + '\'' +
                    ", content=" + content +
                    ", createtime='" + createtime + '\'' +
                    ", flag='" + flag + '\'' +
                    ", levelid='" + levelid + '\'' +
                    ", flagtime='" + flagtime + '\'' +
                    ", credit2='" + credit2 + '\'' +
                    ", status='" + status + '\'' +
                    ", clickcount='" + clickcount + '\'' +
                    ", mbbinded='" + mbbinded + '\'' +
                    ", birthdate='" + birthdate + '\'' +
                    ", remark='" + remark + '\'' +
                    ", isworking='" + isworking + '\'' +
                    ", credit1='" + credit1 + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
