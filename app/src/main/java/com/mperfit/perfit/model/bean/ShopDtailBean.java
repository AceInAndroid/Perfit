package com.mperfit.perfit.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangbing on 2016/11/24.
 */

public class ShopDtailBean {



    /**
     * message : 成功！
     * data : {"comment_list":[{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":"2016-11-1619: 30: 58.0","score":4,"user_id":777760321111588864},{"content":"测试评论测试评论测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/a.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/b.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":"2016-11-0114: 01: 51.0","score":5,"user_id":777760321111588864}],"course_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288","price":34,"market_price":333,"name":"大大大大大小小小小小小","course_id":786406103729569792},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161116171705931.jpg?imageView2/1/w/340/h/288","price":321,"market_price":123,"name":"123321cpcpap#","course_id":788704124651700224}],"activity_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123200530375.jpg?imageView2/0/w/330/h/216","content":"测试","time":"11.26-2.24","price":132,"market_price":321,"activity_id":801396350330601472,"name":"无敌测试活动"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161114193845564.jpg?imageView2/0/w/330/h/216","content":"adw ad ","time":"11.11-12.1","price":1212,"market_price":12121,"activity_id":791942042316963840,"name":"测试数据"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/0/w/330/h/216","content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","time":"10.9 02:00 - 1.1 16:00","price":0,"market_price":199,"activity_id":785054266669662208,"name":"还是花花"}],"seller":{"comment_count":3,"phone":"010-57209482","score":4.6667,"is_collect":0,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113404167.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113414656.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125123354488.jpg?imageView2/0/w/640/h/500"],"specific_address":" 北京朝阳区酒仙桥芳园南街滨河花园底商二楼B-16(巴黎贝甜楼上)","name":"Uppercut 搏击健身馆","seller_id":778479287597531136,"longitude":116.453903,"latitude":39.938782,"course_count":5,"introduction":"关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。\n","business_hours":"10:00-21:00"}}
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
         * comment_list : [{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":"2016-11-1619: 30: 58.0","score":4,"user_id":777760321111588864},{"content":"测试评论测试评论测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/a.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/b.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200","create_time":"2016-11-0114: 01: 51.0","score":5,"user_id":777760321111588864}]
         * course_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288","price":34,"market_price":333,"name":"大大大大大小小小小小小","course_id":786406103729569792},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161116171705931.jpg?imageView2/1/w/340/h/288","price":321,"market_price":123,"name":"123321cpcpap#","course_id":788704124651700224}]
         * activity_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123200530375.jpg?imageView2/0/w/330/h/216","content":"测试","time":"11.26-2.24","price":132,"market_price":321,"activity_id":801396350330601472,"name":"无敌测试活动"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161114193845564.jpg?imageView2/0/w/330/h/216","content":"adw ad ","time":"11.11-12.1","price":1212,"market_price":12121,"activity_id":791942042316963840,"name":"测试数据"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/0/w/330/h/216","content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","time":"10.9 02:00 - 1.1 16:00","price":0,"market_price":199,"activity_id":785054266669662208,"name":"还是花花"}]
         * seller : {"comment_count":3,"phone":"010-57209482","score":4.6667,"is_collect":0,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113404167.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113414656.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125123354488.jpg?imageView2/0/w/640/h/500"],"specific_address":" 北京朝阳区酒仙桥芳园南街滨河花园底商二楼B-16(巴黎贝甜楼上)","name":"Uppercut 搏击健身馆","seller_id":778479287597531136,"longitude":116.453903,"latitude":39.938782,"course_count":5,"introduction":"关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。\n","business_hours":"10:00-21:00"}
         */

        private SellerBean seller;
        private List<CommentListBean> comment_list;
        private List<CourseListBean> course_list;


        public SellerBean getSeller() {
            return seller;
        }

        public void setSeller(SellerBean seller) {
            this.seller = seller;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public List<CourseListBean> getCourse_list() {
            return course_list;
        }

        public void setCourse_list(List<CourseListBean> course_list) {
            this.course_list = course_list;
        }



        public static class SellerBean {
            /**
             * comment_count : 3
             * phone : 010-57209482
             * score : 4.6667
             * is_collect : 0
             * img_list : ["http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113404167.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125113414656.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/seller/20161125123354488.jpg?imageView2/0/w/640/h/500"]
             * specific_address :  北京朝阳区酒仙桥芳园南街滨河花园底商二楼B-16(巴黎贝甜楼上)
             * name : Uppercut 搏击健身馆
             * seller_id : 778479287597531136
             * longitude : 116.453903
             * latitude : 39.938782
             * course_count : 5
             * introduction : 关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。

             * business_hours : 10:00-21:00
             */

            private int comment_count;
            private String phone;
            private double score;
            private int is_collect;
            private String specific_address;
            private String name;
            private long seller_id;
            private double longitude;
            private double latitude;
            private int course_count;
            private String introduction;
            private String business_hours;
            private List<String> img_list;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public int getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(int is_collect) {
                this.is_collect = is_collect;
            }

            public String getSpecific_address() {
                return specific_address;
            }

            public void setSpecific_address(String specific_address) {
                this.specific_address = specific_address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(long seller_id) {
                this.seller_id = seller_id;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public int getCourse_count() {
                return course_count;
            }

            public void setCourse_count(int course_count) {
                this.course_count = course_count;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getBusiness_hours() {
                return business_hours;
            }

            public void setBusiness_hours(String business_hours) {
                this.business_hours = business_hours;
            }

            public List<String> getImg_list() {
                return img_list;
            }

            public void setImg_list(List<String> img_list) {
                this.img_list = img_list;
            }
        }

        public static class CommentListBean implements Serializable {
            /**
             * content : 下班啦啦
             * user_name : 18518481875
             * comment_id : 793332225725693959
             * img_list : ["http://ocjp9x6x9.bkt.clouddn.com/test.jpg?imageView2/0/w/640/h/500"]
             * user_img_url : http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/200/h/200
             * create_time : 2016-11-1619: 30: 58.0
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

        public static class CourseListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20160926140812638.jpg?imageView2/1/w/340/h/288
             * price : 34
             * market_price : 333
             * name : 大大大大大小小小小小小
             * course_id : 786406103729569792
             */

            private String img_url;
            private float price;
            private int market_price;
            private String name;
            private long course_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public int getMarket_price() {
                return market_price;
            }

            public void setMarket_price(int market_price) {
                this.market_price = market_price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getCourse_id() {
                return course_id;
            }

            public void setCourse_id(long course_id) {
                this.course_id = course_id;
            }
        }

        private List<ActivityListBean> activity_list;


        public List<ActivityListBean> getActivity_list() {
            return activity_list;
        }

        public void setActivity_list(List<ActivityListBean> activity_list) {
            this.activity_list = activity_list;
        }


        public static class ActivityListBean {
            /**
             * img_url : http://image.mperfit.com/activity/20161009174817482.jpg?imageView2/0/w/330/h/216
             * content : 以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和
             * time : 12.23 10:00-12:00
             * price : 0
             * market_price : 199
             * activity_id : 785054266669662208
             * name : 给自己再放个假 | 地表最强健身轰趴火热报名
             */

            private String img_url;
            private String content;
            private String time;
            private int price;
            private int market_price;
            private long activity_id;
            private String name;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getMarket_price() {
                return market_price;
            }

            public void setMarket_price(int market_price) {
                this.market_price = market_price;
            }

            public long getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(long activity_id) {
                this.activity_id = activity_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }


    }
}
