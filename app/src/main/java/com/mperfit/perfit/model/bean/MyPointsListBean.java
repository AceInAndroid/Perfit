package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/17.
 */

public class MyPointsListBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"total_score":90,"list":[{"content":"跑腿获得10分","id":3,"create_time":1489670336000,"score":10,"user_id":798042230735503360,"type":1},{"content":"做坏事扣除20分","id":2,"create_time":1489670320000,"score":-20,"user_id":798042230735503360,"type":2},{"content":"写代码获得100分","id":1,"create_time":1489667078000,"score":100,"user_id":798042230735503360,"type":1}],"current_page":1}
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
         * total_score : 90
         * list : [{"content":"跑腿获得10分","id":3,"create_time":1489670336000,"score":10,"user_id":798042230735503360,"type":1},{"content":"做坏事扣除20分","id":2,"create_time":1489670320000,"score":-20,"user_id":798042230735503360,"type":2},{"content":"写代码获得100分","id":1,"create_time":1489667078000,"score":100,"user_id":798042230735503360,"type":1}]
         * current_page : 1
         */

        private int total_page;
        private int total_score;
        private int current_page;
        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getTotal_score() {
            return total_score;
        }

        public void setTotal_score(int total_score) {
            this.total_score = total_score;
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
             * content : 跑腿获得10分
             * id : 3
             * create_time : 1489670336000
             * score : 10
             * user_id : 798042230735503360
             * type : 1
             */

            private String content;
            private int id;
            private long create_time;
            private int score;
            private long user_id;
            private int type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
