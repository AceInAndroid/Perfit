package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/21.
 */

public class MyFollowListBean {

    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"user_name":"小时候可俊了","user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/80/h/80","user_id":777775369972350976},{"user_name":"月曲","user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161011190027621.jpg?imageView2/1/w/80/h/80","user_id":785797203942703104}],"current_page":1}
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
         * list : [{"user_name":"小时候可俊了","user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/80/h/80","user_id":777775369972350976},{"user_name":"月曲","user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161011190027621.jpg?imageView2/1/w/80/h/80","user_id":785797203942703104}]
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
             * user_name : 小时候可俊了
             * user_img_url : http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/80/h/80
             * user_id : 777775369972350976
             */

            private String user_name;
            private String user_img_url;
            private long user_id;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_img_url() {
                return user_img_url;
            }

            public void setUser_img_url(String user_img_url) {
                this.user_img_url = user_img_url;
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
