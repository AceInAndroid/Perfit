package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/24.
 */

public class ScoreBoardBean {


    /**
     * message : 成功！
     * data : {"user_score":0,"user_rank":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20170307211401055.jpg?imageView2/1/w/80/h/80","id":10019,"total":100,"name":"17701085590"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/1/w/80/h/80","id":798042230735503400,"total":90,"name":"张冰"}]}
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
         * user_score : 0
         * user_rank : 1
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20170307211401055.jpg?imageView2/1/w/80/h/80","id":10019,"total":100,"name":"17701085590"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/1/w/80/h/80","id":798042230735503400,"total":90,"name":"张冰"}]
         */

        private int user_score;
        private int user_rank;
        private List<ListBean> list;

        private int total_page;
        private int current_page;

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

        public int getUser_score() {
            return user_score;
        }

        public void setUser_score(int user_score) {
            this.user_score = user_score;
        }

        public int getUser_rank() {
            return user_rank;
        }

        public void setUser_rank(int user_rank) {
            this.user_rank = user_rank;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/user/20170307211401055.jpg?imageView2/1/w/80/h/80
             * id : 10019
             * total : 100
             * name : 17701085590
             */

            private String img_url;
            private int id;
            private int total;
            private int rank;
            private long user_id;
            private String name;

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
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
