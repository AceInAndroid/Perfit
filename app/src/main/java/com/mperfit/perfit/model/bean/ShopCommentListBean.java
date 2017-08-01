package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/12/19.
 */

public class ShopCommentListBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20161114135845976?imageView2/1/w/200/h/200","create_time":1479295858000,"score":2,"user_id":777760321111588864},{"content":"测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20161114135845976?imageView2/1/w/200/h/200","create_time":1477980111000,"score":4,"user_id":777760321111588864},{"content":"课程真好","user_name":"小时候可俊了","comment_id":1,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/200/h/200","create_time":1477885920000,"score":1,"user_id":777775369972350976}],"current_page":1}
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
         * list : [{"content":"下班啦啦","user_name":"18518481875","comment_id":793332225725693959,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20161114135845976?imageView2/1/w/200/h/200","create_time":1479295858000,"score":2,"user_id":777760321111588864},{"content":"测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评论测试评测","user_name":"18518481875","comment_id":793332225725693952,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20161114135845976?imageView2/1/w/200/h/200","create_time":1477980111000,"score":4,"user_id":777760321111588864},{"content":"课程真好","user_name":"小时候可俊了","comment_id":1,"img_list":["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"],"user_img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/1/w/200/h/200","create_time":1477885920000,"score":1,"user_id":777775369972350976}]
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
             * content : 下班啦啦
             * user_name : 18518481875
             * comment_id : 793332225725693959
             * img_list : ["http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500","http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/640/h/500"]
             * user_img_url : http://ocjp9x6x9.bkt.clouddn.com//user/20161114135845976?imageView2/1/w/200/h/200
             * create_time : 1479295858000
             * score : 2
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
