package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/22.
 */

public class CourseListBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288","distance":"","price":34,"begin_time":"上午3点","market_price":333,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"大大大大大小小小小小小","seller_id":778479287597531136,"course_id":786406103729569792},{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161116171705931.jpg?imageView2/1/w/340/h/288","distance":"","price":321,"begin_time":"上午3点","market_price":123,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"123321cpcpap#","seller_id":778479287597531136,"course_id":788704124651700224},{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161114103151621.jpg?imageView2/1/w/340/h/288","distance":"","price":221,"begin_time":"上午3点","market_price":2112,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"冬天和个智障一样。.","seller_id":778479287597531136,"course_id":791209632671465472},{"seller_name":"3T健身工作室（魏公村店）","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161017113608868.jpg?imageView2/1/w/340/h/288","distance":"","price":33,"begin_time":"2016-10-12 00:00:00","market_price":133,"address":"天作国际","end_time":"2016-10-31 00:00:00","score":"3.0","course_name":"体验卡券，单车10节课。","seller_id":779223595439095808,"course_id":1}],"current_page":1}
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
         * total_page : 1
         * list : [{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288","distance":"","price":34,"begin_time":"上午3点","market_price":333,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"大大大大大小小小小小小","seller_id":778479287597531136,"course_id":786406103729569792},{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161116171705931.jpg?imageView2/1/w/340/h/288","distance":"","price":321,"begin_time":"上午3点","market_price":123,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"123321cpcpap#","seller_id":778479287597531136,"course_id":788704124651700224},{"seller_name":"spacecycle健身中心","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161114103151621.jpg?imageView2/1/w/340/h/288","distance":"","price":221,"begin_time":"上午3点","market_price":2112,"address":"朝阳区三里屯","end_time":"下午4点","score":"4.7","course_name":"冬天和个智障一样。.","seller_id":778479287597531136,"course_id":791209632671465472},{"seller_name":"3T健身工作室（魏公村店）","img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161017113608868.jpg?imageView2/1/w/340/h/288","distance":"","price":33,"begin_time":"2016-10-12 00:00:00","market_price":133,"address":"天作国际","end_time":"2016-10-31 00:00:00","score":"3.0","course_name":"体验卡券，单车10节课。","seller_id":779223595439095808,"course_id":1}]
         * current_page : 1
         */

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
             * seller_name : spacecycle健身中心
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288
             * distance :
             * price : 34.0
             * begin_time : 上午3点
             * market_price : 333.0
             * address : 朝阳区三里屯
             * end_time : 下午4点
             * score : 4.7
             * course_name : 大大大大大小小小小小小
             * seller_id : 778479287597531136
             * course_id : 786406103729569792
             */

            private String seller_name;
            private String img_url;
            private String distance;
            private double price;
            private String begin_time;
            private double market_price;
            private String address;
            private String end_time;
            private String score;
            private String course_name;
            private long seller_id;
            private long course_id;

            public String getSeller_name() {
                return seller_name;
            }

            public void setSeller_name(String seller_name) {
                this.seller_name = seller_name;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
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

            public double getMarket_price() {
                return market_price;
            }

            public void setMarket_price(double market_price) {
                this.market_price = market_price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
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

            public long getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(long seller_id) {
                this.seller_id = seller_id;
            }

            public long getCourse_id() {
                return course_id;
            }

            public void setCourse_id(long course_id) {
                this.course_id = course_id;
            }
        }
    }
}
