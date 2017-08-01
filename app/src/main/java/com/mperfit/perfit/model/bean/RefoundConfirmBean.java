package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class RefoundConfirmBean {

    /**
     * message : 成功！
     * data : {"order":{"price":0.01,"list_count":0,"pay_type":2,"course_name":"体验卡券，单车10节课。","number":1,"code":"747752524575","order_id":801974647066198000,"pay_price":0.01}}
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
         * order : {"price":0.01,"list_count":0,"pay_type":2,"course_name":"体验卡券，单车10节课。","number":1,"code":"747752524575","order_id":801974647066198000,"pay_price":0.01}
         */

        private OrderBean order;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * price : 0.01
             * list_count : 0
             * pay_type : 2
             * course_name : 体验卡券，单车10节课。
             * number : 1
             * code : 747752524575
             * order_id : 801974647066198000
             * pay_price : 0.01
             */

            private double price;
            private int list_count;
            private int pay_type;
            private String course_name;
            private int number;
            private String code;
            private long order_id;
            private double pay_price;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getList_count() {
                return list_count;
            }

            public void setList_count(int list_count) {
                this.list_count = list_count;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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
}
