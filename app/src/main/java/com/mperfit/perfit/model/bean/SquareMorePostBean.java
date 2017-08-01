package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/16.
 */

public class SquareMorePostBean {

    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"comment_count":1,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170224103130101.jpg?imageView2/1/w/1080/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20170224114702765.jpg?imageView2/1/w/60/h/60","img_height":2001,"like":"1","type":1,"id":834954126138802176,"content":"","user_name":"许谦😩","time":"今天 10:32","like_count":1,"user_id":778932531926925312,"img_width":1125,"friend":"1"},{"comment_count":1,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170613478.jpg?imageView2/1/w/1080/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":600,"like":"0","type":2,"id":834690882618785792,"content":"你\n","user_name":"小时候可俊了","time":"昨天 17:06","like_count":0,"user_id":777775369972350976,"img_width":800,"friend":"1"}],"current_page":1}
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
         * list : [{"comment_count":1,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170224103130101.jpg?imageView2/1/w/1080/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20170224114702765.jpg?imageView2/1/w/60/h/60","img_height":2001,"like":"1","type":1,"id":834954126138802176,"content":"","user_name":"许谦😩","time":"今天 10:32","like_count":1,"user_id":778932531926925312,"img_width":1125,"friend":"1"},{"comment_count":1,"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170613478.jpg?imageView2/1/w/1080/h/936","icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":600,"like":"0","type":2,"id":834690882618785792,"content":"你\n","user_name":"小时候可俊了","time":"昨天 17:06","like_count":0,"user_id":777775369972350976,"img_width":800,"friend":"1"}]
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
             * comment_count : 1
             * img_url_android : http://ocjp9x6x9.bkt.clouddn.com/note/20170224103130101.jpg?imageView2/1/w/1080/h/936
             * icon : http://ocjp9x6x9.bkt.clouddn.com/user/20170224114702765.jpg?imageView2/1/w/60/h/60
             * img_height : 2001
             * like : 1
             * type : 1
             * id : 834954126138802176
             * content :
             * user_name : 许谦😩
             * time : 今天 10:32
             * like_count : 1
             * user_id : 778932531926925312
             * img_width : 1125
             * friend : 1
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
    }
}
