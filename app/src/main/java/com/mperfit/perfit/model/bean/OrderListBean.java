package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/12/3.
 */

public class OrderListBean {




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


        private int total_page;
        private int current_page;
        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * seller_name : 3T健身工作室（魏公村店）
             * price : 0.01
             * begin_time : 上午10点
             * end_time : 下午8点
             * status : 2
             * course_name : 体验卡券，单车10节课。
             * create_time : 1480596794000
             * number : 1
             * course_id : 1
             * order_id : 804307366559350784
             * course_img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20161017113608868.jpg?imageView2/1/w/750/h/460
             * pay_price : 0.01
             */

            private String seller_name;
            private double price;
            private String begin_time;
            private String end_time;
            private int status;
            private String course_name;
            private long create_time;
            private int number;
            private long course_id;
            private long order_id;
            private String course_img_url;
            private double pay_price;

            public String getSeller_name() {
                return seller_name;
            }

            public void setSeller_name(String seller_name) {
                this.seller_name = seller_name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCourse_name() {
                return course_name;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
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

            public String getCourse_img_url() {
                return course_img_url;
            }

            public void setCourse_img_url(String course_img_url) {
                this.course_img_url = course_img_url;
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
