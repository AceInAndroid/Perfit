package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/23.
 */

public class MyFollowPostBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223172034265.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":242,"like":"0","type":1,"id":834694439740899328,"content":"","user_name":"小时候可俊了","time":"2分钟前","like_count":0,"user_id":777775369972350976,"img_width":240,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223171839773.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":640,"like":"0","type":2,"id":834694033656774656,"content":"不说","user_name":"小时候可俊了","time":"3分钟前","like_count":0,"user_id":777775369972350976,"img_width":640,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223171744671.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":705,"like":"0","type":2,"id":834693885136470016,"content":"不说","user_name":"小时候可俊了","time":"4分钟前","like_count":0,"user_id":777775369972350976,"img_width":720,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170909164.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":391,"like":"0","type":2,"id":834691631096528896,"content":"，测得的咯哦哦\n","user_name":"小时候可俊了","time":"13分钟前","like_count":0,"user_id":777775369972350976,"img_width":440,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170613478.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":600,"like":"0","type":2,"id":834690882618785792,"content":"你\n","user_name":"小时候可俊了","time":"16分钟前","like_count":0,"user_id":777775369972350976,"img_width":800,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223165725819.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":640,"like":"0","type":2,"id":834688686279884800,"content":"？我想说","user_name":"小时候可俊了","time":"25分钟前","like_count":1,"user_id":777775369972350976,"img_width":640,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/course/20170220183329354.jpg?imageView2/1/w/1080/h/936","comment_count":19,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":null,"like":"1","type":2,"id":831796737742995456,"content":"***","user_name":"小时候可俊了","time":"02-15 17:25","like_count":3,"user_id":777775369972350976,"img_width":null,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/shds?imageView2/1/w/1080/h/936","comment_count":5,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":null,"like":"1","type":1,"id":831119303272038400,"content":"11111","user_name":"小时候可俊了","time":"02-13 20:33","like_count":3,"user_id":777775369972350976,"img_width":null,"friend":"1"}],"current_page":1}
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
         * list : [{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223172034265.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":242,"like":"0","type":1,"id":834694439740899328,"content":"","user_name":"小时候可俊了","time":"2分钟前","like_count":0,"user_id":777775369972350976,"img_width":240,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223171839773.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":640,"like":"0","type":2,"id":834694033656774656,"content":"不说","user_name":"小时候可俊了","time":"3分钟前","like_count":0,"user_id":777775369972350976,"img_width":640,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223171744671.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":705,"like":"0","type":2,"id":834693885136470016,"content":"不说","user_name":"小时候可俊了","time":"4分钟前","like_count":0,"user_id":777775369972350976,"img_width":720,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170909164.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":391,"like":"0","type":2,"id":834691631096528896,"content":"，测得的咯哦哦\n","user_name":"小时候可俊了","time":"13分钟前","like_count":0,"user_id":777775369972350976,"img_width":440,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223170613478.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":600,"like":"0","type":2,"id":834690882618785792,"content":"你\n","user_name":"小时候可俊了","time":"16分钟前","like_count":0,"user_id":777775369972350976,"img_width":800,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/note/20170223165725819.jpg?imageView2/1/w/1080/h/936","comment_count":0,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":640,"like":"0","type":2,"id":834688686279884800,"content":"？我想说","user_name":"小时候可俊了","time":"25分钟前","like_count":1,"user_id":777775369972350976,"img_width":640,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/course/20170220183329354.jpg?imageView2/1/w/1080/h/936","comment_count":19,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":null,"like":"1","type":2,"id":831796737742995456,"content":"***","user_name":"小时候可俊了","time":"02-15 17:25","like_count":3,"user_id":777775369972350976,"img_width":null,"friend":"1"},{"img_url_android":"http://ocjp9x6x9.bkt.clouddn.com/shds?imageView2/1/w/1080/h/936","comment_count":5,"icon":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60","img_height":null,"like":"1","type":1,"id":831119303272038400,"content":"11111","user_name":"小时候可俊了","time":"02-13 20:33","like_count":3,"user_id":777775369972350976,"img_width":null,"friend":"1"}]
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
             * img_url_android : http://ocjp9x6x9.bkt.clouddn.com/note/20170223172034265.jpg?imageView2/1/w/1080/h/936
             * comment_count : 0
             * icon : http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/60/h/60
             * img_height : 242
             * like : 0
             * type : 1
             * id : 834694439740899328
             * content :
             * user_name : 小时候可俊了
             * time : 2分钟前
             * like_count : 0
             * user_id : 777775369972350976
             * img_width : 240
             * friend : 1
             */

            private String img_url_android;
            private int comment_count;
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

            public String getImg_url_android() {
                return img_url_android;
            }

            public void setImg_url_android(String img_url_android) {
                this.img_url_android = img_url_android;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
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
