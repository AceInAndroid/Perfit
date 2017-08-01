package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/18.
 */

public class PostCommentBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"count":4,"list":[{"content":"张冰啦啦啦啦啦啦啦啦","id":832858087407747072,"icon":"http://image.mperfit.com//user/20170120171802642?imageView2/1/w/340/h/288","time":"1分钟前","name":"张冰","user_id":798042230735503360},{"content":"8888888888888","id":831809464246992896,"icon":"http://image.mperfit.com/user/20161124130232384.jpg?imageView2/1/w/340/h/288","time":"02-15 18:16","name":"小时候可俊了","user_id":777775369972350976},{"content":"sdsdasda","id":1111,"icon":"http://image.mperfit.com/user/20161124130232384.jpg?imageView2/1/w/340/h/288","time":"02-15 17:48","name":"小时候可俊了","user_id":777775369972350976},{"content":"测试测试","id":1,"icon":"http://image.mperfit.com/user/20161010200401298.jpg?imageView2/1/w/340/h/288","time":"02-15 10:27","name":"一个人的旅行丶","user_id":778489477118033920}]}
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
         * count : 4
         * list : [{"content":"张冰啦啦啦啦啦啦啦啦","id":832858087407747072,"icon":"http://image.mperfit.com//user/20170120171802642?imageView2/1/w/340/h/288","time":"1分钟前","name":"张冰","user_id":798042230735503360},{"content":"8888888888888","id":831809464246992896,"icon":"http://image.mperfit.com/user/20161124130232384.jpg?imageView2/1/w/340/h/288","time":"02-15 18:16","name":"小时候可俊了","user_id":777775369972350976},{"content":"sdsdasda","id":1111,"icon":"http://image.mperfit.com/user/20161124130232384.jpg?imageView2/1/w/340/h/288","time":"02-15 17:48","name":"小时候可俊了","user_id":777775369972350976},{"content":"测试测试","id":1,"icon":"http://image.mperfit.com/user/20161010200401298.jpg?imageView2/1/w/340/h/288","time":"02-15 10:27","name":"一个人的旅行丶","user_id":778489477118033920}]
         */

        private int total_page;
        private int count;
        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content : 张冰啦啦啦啦啦啦啦啦
             * id : 832858087407747072
             * icon : http://image.mperfit.com//user/20170120171802642?imageView2/1/w/340/h/288
             * time : 1分钟前
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
