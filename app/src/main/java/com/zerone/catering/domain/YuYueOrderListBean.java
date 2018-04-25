package com.zerone.catering.domain;
import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/8/21.
 *
 */

public class YuYueOrderListBean implements Serializable {

    /**
     * status : 1
     * data : [{"id":"520","ordersn":"17082110417252","datenum":"11","in_other_table":0},{"id":"519","ordersn":"17082110660627","datenum":"12","in_other_table":0}]
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
         * id : 520
         * ordersn : 17082110417252
         * datenum : 11
         * in_other_table : 0
         */

        private String id;
        private String ordersn;
        private String datenum;
        private int in_other_table;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getDatenum() {
            return datenum;
        }

        public void setDatenum(String datenum) {
            this.datenum = datenum;
        }

        public int getIn_other_table() {
            return in_other_table;
        }

        public void setIn_other_table(int in_other_table) {
            this.in_other_table = in_other_table;
        }
    }
}
