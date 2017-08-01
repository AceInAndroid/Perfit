package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/12/3.
 */

public class OrderDetailBean {

    /**
     * message : 成功！
     * data : {"order":{"refund_id":"","seller_name":"你胆魄","phone":"343435045","begin_time":"上午3点","status":2,"score":"4.1","course_name":"大大大大大小小小小小小","number":1,"code":"201612151604114705898103","order_id":809308057082265600,"order_create_time":1481789051000,"price":0.01,"end_time":"下午4点","specific_address":"多过分 震天天更多的房地走在获得法国的电饭锅大概一","longitude":116.453903,"seller_id":778479287597531136,"latitude":39.938782,"course_id":786406103729569792,"business_hours":"10:30-31:00","pay_price":0.01,"course_img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161208102355325.jpg?imageView2/0/w/750/h/540"},"qd_list":[{"id":809731454941528064,"order_refund_id":"","status":1,"code":"371613424123"}]}
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
         * order : {"refund_id":"","seller_name":"你胆魄","phone":"343435045","begin_time":"上午3点","status":2,"score":"4.1","course_name":"大大大大大小小小小小小","number":1,"code":"201612151604114705898103","order_id":809308057082265600,"order_create_time":1481789051000,"price":0.01,"end_time":"下午4点","specific_address":"多过分 震天天更多的房地走在获得法国的电饭锅大概一","longitude":116.453903,"seller_id":778479287597531136,"latitude":39.938782,"course_id":786406103729569792,"business_hours":"10:30-31:00","pay_price":0.01,"course_img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161208102355325.jpg?imageView2/0/w/750/h/540"}
         * qd_list : [{"id":809731454941528064,"order_refund_id":"","status":1,"code":"371613424123"}]
         */

        private OrderBean order;
        private List<QdListBean> qd_list;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<QdListBean> getQd_list() {
            return qd_list;
        }

        public void setQd_list(List<QdListBean> qd_list) {
            this.qd_list = qd_list;
        }

        public static class OrderBean {
            /**
             * refund_id :
             * seller_name : 你胆魄
             * phone : 343435045
             * begin_time : 上午3点
             * status : 2
             * score : 4.1
             * course_name : 大大大大大小小小小小小
             * number : 1
             * code : 201612151604114705898103
             * order_id : 809308057082265600
             * order_create_time : 1481789051000
             * price : 0.01
             * end_time : 下午4点
             * specific_address : 多过分 震天天更多的房地走在获得法国的电饭锅大概一
             * longitude : 116.453903
             * seller_id : 778479287597531136
             * latitude : 39.938782
             * course_id : 786406103729569792
             * business_hours : 10:30-31:00
             * pay_price : 0.01
             * course_img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20161208102355325.jpg?imageView2/0/w/750/h/540
             */

            private String refund_id;
            private String seller_name;
            private String phone;
            private String begin_time;
            private int status;
            private String score;
            private String course_name;
            private int number;
            private String code;
            private long order_id;
            private long order_create_time;
            private double price;
            private String end_time;
            private String specific_address;
            private double longitude;
            private long seller_id;
            private double latitude;
            private long course_id;
            private String business_hours;
            private double pay_price;
            private String course_img_url;

            public String getRefund_id() {
                return refund_id;
            }

            public void setRefund_id(String refund_id) {
                this.refund_id = refund_id;
            }

            public String getSeller_name() {
                return seller_name;
            }

            public void setSeller_name(String seller_name) {
                this.seller_name = seller_name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
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

            public long getOrder_create_time() {
                return order_create_time;
            }

            public void setOrder_create_time(long order_create_time) {
                this.order_create_time = order_create_time;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getSpecific_address() {
                return specific_address;
            }

            public void setSpecific_address(String specific_address) {
                this.specific_address = specific_address;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public long getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(long seller_id) {
                this.seller_id = seller_id;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public long getCourse_id() {
                return course_id;
            }

            public void setCourse_id(long course_id) {
                this.course_id = course_id;
            }

            public String getBusiness_hours() {
                return business_hours;
            }

            public void setBusiness_hours(String business_hours) {
                this.business_hours = business_hours;
            }

            public double getPay_price() {
                return pay_price;
            }

            public void setPay_price(double pay_price) {
                this.pay_price = pay_price;
            }

            public String getCourse_img_url() {
                return course_img_url;
            }

            public void setCourse_img_url(String course_img_url) {
                this.course_img_url = course_img_url;
            }
        }

        public static class QdListBean {
            /**
             * id : 809731454941528064
             * order_refund_id :
             * status : 1
             * code : 371613424123
             */

            private long id;
            private long order_refund_id;
            private int status;
            private String code;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getOrder_refund_id() {
                return order_refund_id;
            }

            public void setOrder_refund_id(long order_refund_id) {
                this.order_refund_id = order_refund_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
