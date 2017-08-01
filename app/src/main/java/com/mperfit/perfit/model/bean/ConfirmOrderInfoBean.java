package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/11/30.
 */

public class ConfirmOrderInfoBean {


    /**
     * message : 成功！
     * data : {"price":221,"course_name":"冬天和个智障一样。.","number":0,"course_id":791209632671465472,"order_id":803938734448836608,"pay_price":0}
     * code : 100
     */

    private String message;
    private DataBean data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * price : 221.0
         * course_name : 冬天和个智障一样。.
         * number : 0
         * course_id : 791209632671465472
         * order_id : 803938734448836608
         * pay_price : 0.0
         */

        private double price;
        private String course_name;
        private int number;
        private long course_id;
        private long order_id;
        private double pay_price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public long getCourse_id() {
            return course_id;
        }

        public void setCourse_id(long course_id) {
            this.course_id = course_id;
        }

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public double getPay_price() {
            return pay_price;
        }

        public void setPay_price(double pay_price) {
            this.pay_price = pay_price;
        }
    }
}
