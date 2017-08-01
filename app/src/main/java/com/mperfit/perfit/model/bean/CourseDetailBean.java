package com.mperfit.perfit.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangbing on 2016/11/23.
 */

public class CourseDetailBean {


    /**
     * message : 成功！
     * data : {"course":{"comment_count":3,"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/0/w/750/h/540","seller_name":"spacecycle健身中心","phone":"010-84516101","begin_time":"上午3点","img_array":"","score":"4.7","course_name":"大大大大大小小小小小小","course_content":"222222222222222222222222","img_list":null,"price":34,"market_price":333,"course_title":"","end_time":"下午4点","specific_address":"三里屯路11号院西座N4-40A","longitude":116.453903,"seller_id":778479287597531136,"latitude":39.938782,"course_id":786406103729569792,"business_hours":"9：00-22：00"},"comment_list":[{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":1479295858000,"score":4,"user_id":777760321111588864},{"content":"测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/a.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/b.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":1477980111000,"score":5,"user_id":777760321111588864}]}
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
         * course : {"comment_count":3,"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/0/w/750/h/540","seller_name":"spacecycle健身中心","phone":"010-84516101","begin_time":"上午3点","img_array":"","score":"4.7","course_name":"大大大大大小小小小小小","course_content":"222222222222222222222222","img_list":null,"price":34,"market_price":333,"course_title":"","end_time":"下午4点","specific_address":"三里屯路11号院西座N4-40A","longitude":116.453903,"seller_id":778479287597531136,"latitude":39.938782,"course_id":786406103729569792,"business_hours":"9：00-22：00"}
         * comment_list : [{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":1479295858000,"score":4,"user_id":777760321111588864},{"content":"测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/a.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/b.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":1477980111000,"score":5,"user_id":777760321111588864}]
         */

        private CourseBean course;
        private List<CommentListBean> comment_list;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class CourseBean {
            /**
             * comment_count : 3
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/0/w/750/h/540
             * seller_name : spacecycle健身中心
             * phone : 010-84516101
             * begin_time : 上午3点
             * img_array :
             * score : 4.7
             * course_name : 大大大大大小小小小小小
             * course_content : 222222222222222222222222
             * img_list : null
             * price : 34.0
             * market_price : 333.0
             * course_title :
             * end_time : 下午4点
             * specific_address : 三里屯路11号院西座N4-40A
             * longitude : 116.453903
             * seller_id : 778479287597531136
             * latitude : 39.938782
             * course_id : 786406103729569792
             * business_hours : 9：00-22：00
             */

            private int comment_count;
            private String img_url;
            private String seller_name;
            private String phone;
            private String begin_time;
            private String img_array;
            private String score;
            private String course_name;
            private String course_content;
            private List<String> img_list;
            private double price;
            private double market_price;
            private String course_title;
            private String end_time;
            private String specific_address;
            private double longitude;
            private long seller_id;
            private double latitude;
            private long course_id;
            private String business_hours;

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
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

            public String getImg_array() {
                return img_array;
            }

            public void setImg_array(String img_array) {
                this.img_array = img_array;
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

            public String getCourse_content() {
                return course_content;
            }

            public void setCourse_content(String course_content) {
                this.course_content = course_content;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getMarket_price() {
                return market_price;
            }

            public void setMarket_price(double market_price) {
                this.market_price = market_price;
            }

            public String getCourse_title() {
                return course_title;
            }

            public void setCourse_title(String course_title) {
                this.course_title = course_title;
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
        }

        public static class CommentListBean implements Serializable {
            /**
             * content : 下班啦啦
             * user_name : 18518481875
             * comment_id : 793332225725693959
             * img_list : ["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"]
             * user_img_url : http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200
             * create_time : 1479295858000
             * score : 4
             * user_id : 777760321111588864
             */

            private String content;
            private String user_name;
            private long comment_id;
            private String user_img_url;
            private long create_time;
            private int score;
            private long user_id;
            private List<String> img_list;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public long getComment_id() {
                return comment_id;
            }

            public void setComment_id(long comment_id) {
                this.comment_id = comment_id;
            }

            public String getUser_img_url() {
                return user_img_url;
            }

            public void setUser_img_url(String user_img_url) {
                this.user_img_url = user_img_url;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }
        }
    }
}
