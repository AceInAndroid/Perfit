package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/24.
 */

public class DayRankingListBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"user_hot":18,"total_page":1,"user_rank":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/1/w/80/h/80","total":18,"rank":1,"name":"张冰","user_id":798042230735503360},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20170321155724648.jpg?imageView2/1/w/80/h/80","total":9,"rank":2,"name":"尘沉","user_id":816549945766051840},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/80/h/80","total":6,"rank":3,"name":"甜蜜的梦╮。","user_id":777776048468131840},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/80/h/80","total":6,"rank":4,"name":"PERFIT7344","user_id":845102506357817344}],"current_page":1}
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
         * user_hot : 18
         * total_page : 1
         * user_rank : 1
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/1/w/80/h/80","total":18,"rank":1,"name":"张冰","user_id":798042230735503360},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20170321155724648.jpg?imageView2/1/w/80/h/80","total":9,"rank":2,"name":"尘沉","user_id":816549945766051840},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/80/h/80","total":6,"rank":3,"name":"甜蜜的梦╮。","user_id":777776048468131840},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/default.jpg?imageView2/1/w/80/h/80","total":6,"rank":4,"name":"PERFIT7344","user_id":845102506357817344}]
         * current_page : 1
         */

        private int user_hot;
        private int total_page;
        private int current_page;
        private int user_rank;
        private List<ListBean> list;

        public int getUser_hot() {
            return user_hot;
        }

        public void setUser_hot(int user_hot) {
            this.user_hot = user_hot;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getUser_rank() {
            return user_rank;
        }

        public void setUser_rank(int user_rank) {
            this.user_rank = user_rank;
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/1/w/80/h/80
             * total : 18
             * rank : 1
             * name : 张冰
             * user_id : 798042230735503360
             */

            private String img_url;
            private int total;
            private int rank;
            private String name;
            private long user_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
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
