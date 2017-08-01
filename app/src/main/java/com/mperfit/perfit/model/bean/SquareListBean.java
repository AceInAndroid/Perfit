package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/15.
 */

public class SquareListBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"total_page":5,"topic_list":[{"id":831322105596346400,"name":"我要脱单","random":1}],"note_list":[{"comment_count":0,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170306200055395.jpg?imageView2/1/w/750/h/500","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/2/w/60/h/60","img_height":500,"like":"0","type":1,"id":838721053445324800,"content":"","user_name":"小时候可俊了","time":"昨天 20:00","like_count":0,"user_id":777775369972351000,"img_width":750,"friend":"0"},{"comment_count":0,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170306195945985.jpg?imageView2/1/w/750/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/2/w/60/h/60","img_height":936,"like":"0","type":1,"id":838720759940513800,"content":"","user_name":"小时候可俊了","time":"昨天 19:59","like_count":0,"user_id":777775369972351000,"img_width":750,"friend":"0"}],"banner_list":[{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161207155624999.jpg?imageView2/1/w/1080/h/576","depict":"","biz_type":1,"biz_id":791212859060125700,"is_click":1,"url":""},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161207155816184.jpg?imageView2/1/w/1080/h/576","depict":"描述","biz_type":2,"biz_id":779238889897328600,"is_click":0,"url":"www.qq.com"}]}
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
         * total_page : 5
         * topic_list : [{"id":831322105596346400,"name":"我要脱单","random":1}]
         * note_list : [{"comment_count":0,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170306200055395.jpg?imageView2/1/w/750/h/500","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/2/w/60/h/60","img_height":500,"like":"0","type":1,"id":838721053445324800,"content":"","user_name":"小时候可俊了","time":"昨天 20:00","like_count":0,"user_id":777775369972351000,"img_width":750,"friend":"0"},{"comment_count":0,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170306195945985.jpg?imageView2/1/w/750/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/2/w/60/h/60","img_height":936,"like":"0","type":1,"id":838720759940513800,"content":"","user_name":"小时候可俊了","time":"昨天 19:59","like_count":0,"user_id":777775369972351000,"img_width":750,"friend":"0"}]
         * banner_list : [{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161207155624999.jpg?imageView2/1/w/1080/h/576","depict":"","biz_type":1,"biz_id":791212859060125700,"is_click":1,"url":""},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161207155816184.jpg?imageView2/1/w/1080/h/576","depict":"描述","biz_type":2,"biz_id":779238889897328600,"is_click":0,"url":"www.qq.com"}]
         */

        private int total_page;
        private List<TopicListBean> topic_list;
        private List<NoteListBean> note_list;
        private List<BannerListBean> banner_list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<TopicListBean> getTopic_list() {
            return topic_list;
        }

        public void setTopic_list(List<TopicListBean> topic_list) {
            this.topic_list = topic_list;
        }

        public List<NoteListBean> getNote_list() {
            return note_list;
        }

        public void setNote_list(List<NoteListBean> note_list) {
            this.note_list = note_list;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public static class TopicListBean {
            /**
             * id : 831322105596346400
             * name : 我要脱单
             * random : 1
             */

            private long id;
            private String name;
            private int random;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRandom() {
                return random;
            }

            public void setRandom(int random) {
                this.random = random;
            }
        }

        public static class NoteListBean {
            /**
             * comment_count : 0
             * img_url_android : http://ocjp9x6x9.bkt.clouddn.com/note/20170306200055395.jpg?imageView2/1/w/750/h/500
             * icon : http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/2/w/60/h/60
             * img_height : 500
             * like : 0
             * type : 1
             * id : 838721053445324800
             * content :
             * user_name : 小时候可俊了
             * time : 昨天 20:00
             * like_count : 0
             * user_id : 777775369972351000
             * img_width : 750
             * friend : 0
             */

            private int comment_count;
            private String img_url_android;
            private String icon;
            private int img_height;
            private String like;
            private int type;
            private long id;
            private String content;
            private String user_name;
            private String time;
            private int like_count;
            private long user_id;
            private int img_width;
            private String friend;

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getImg_url_android() {
                return img_url_android;
            }

            public void setImg_url_android(String img_url_android) {
                this.img_url_android = img_url_android;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getImg_height() {
                return img_height;
            }

            public void setImg_height(int img_height) {
                this.img_height = img_height;
            }

            public String getLike() {
                return like;
            }

            public void setLike(String like) {
                this.like = like;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public int getImg_width() {
                return img_width;
            }

            public void setImg_width(int img_width) {
                this.img_width = img_width;
            }

            public String getFriend() {
                return friend;
            }

            public void setFriend(String friend) {
                this.friend = friend;
            }
        }

        public static class BannerListBean {
            /**
             * img_url_android : http://ocjp9x6x9.bkt.clouddn.com/activity/20161207155624999.jpg?imageView2/1/w/1080/h/576
             * depict :
             * biz_type : 1
             * biz_id : 791212859060125700
             * is_click : 1
             * url :
             */

            private String img_url;
            private String depict;
            private int biz_type;
            private long biz_id;
            private int is_click;
            private String url;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getDepict() {
                return depict;
            }

            public void setDepict(String depict) {
                this.depict = depict;
            }

            public int getBiz_type() {
                return biz_type;
            }

            public void setBiz_type(int biz_type) {
                this.biz_type = biz_type;
            }

            public long getBiz_id() {
                return biz_id;
            }

            public void setBiz_id(long biz_id) {
                this.biz_id = biz_id;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
