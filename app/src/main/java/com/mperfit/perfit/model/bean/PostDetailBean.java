package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/17.
 */

public class PostDetailBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"comment":[{"content":"哈哈哈哈哈哈","id":834664690360516608,"icon":"http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/340/h/288","time":"15分钟前","name":"张冰","user_id":798042230735503360}],"note":{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170222163221561.jpg","comment_count":1,"icon":"http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/60/h/60","img_height":457,"like":1,"type":2,"id":834319908077568000,"content":"绿色特特他太爬爬服特特嘟嘟嘟怕啊其实11萨特屠夫打他7部嘟嘟嘟","user_name":"张冰","time":"昨天 16:32","like_count":1,"user_id":798042230735503360,"img_width":460,"friend":"0"}}
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
         * comment : [{"content":"哈哈哈哈哈哈","id":834664690360516608,"icon":"http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/340/h/288","time":"15分钟前","name":"张冰","user_id":798042230735503360}]
         * note : {"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170222163221561.jpg","comment_count":1,"icon":"http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/60/h/60","img_height":457,"like":1,"type":2,"id":834319908077568000,"content":"绿色特特他太爬爬服特特嘟嘟嘟怕啊其实11萨特屠夫打他7部嘟嘟嘟","user_name":"张冰","time":"昨天 16:32","like_count":1,"user_id":798042230735503360,"img_width":460,"friend":"0"}
         */

        private NoteBean note;
        private List<CommentBean> comment;

        public NoteBean getNote() {
            return note;
        }

        public void setNote(NoteBean note) {
            this.note = note;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class NoteBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/note/20170222163221561.jpg
             * comment_count : 1
             * icon : http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/60/h/60
             * img_height : 457
             * like : 1
             * type : 2
             * id : 834319908077568000
             * content : 绿色特特他太爬爬服特特嘟嘟嘟怕啊其实11萨特屠夫打他7部嘟嘟嘟
             * user_name : 张冰
             * time : 昨天 16:32
             * like_count : 1
             * user_id : 798042230735503360
             * img_width : 460
             * friend : 0
             */

            private String img_url;
            private int comment_count;
            private String icon;
            private int img_height;
            private int like;
            private int type;
            private long id;
            private String content;
            private String user_name;
            private String time;
            private int like_count;
            private long user_id;
            private int img_width;
            private String friend;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
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

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
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

        public static class CommentBean {
            /**
             * content : 哈哈哈哈哈哈
             * id : 834664690360516608
             * icon : http://ocjp9x6x9.bkt.clouddn.com//user/20170120171802642?imageView2/1/w/340/h/288
             * time : 15分钟前
             * name : 张冰
             * user_id : 798042230735503360
             */

            private String content;
            private long id;
            private String icon;
            private String time;
            private String name;
            private long user_id;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }
        }
    }
}
