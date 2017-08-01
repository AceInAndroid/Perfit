package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/28.
 */

public class ArticleCollectBean {


    /**
     * code : 100
     * data : {"current_page":1,"list":[{"article_id":1,"img_url":"http:activity/20160829121518131.jpg","name":"测试文章"}],"total_page":1}
     * message : 成功！
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * current_page : 1
         * list : [{"article_id":1,"img_url":"http:activity/20160829121518131.jpg","name":"测试文章"}]
         * total_page : 1
         */

        private int current_page;
        private int total_page;
        private List<ListBean> list;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * article_id : 1
             * img_url : http:activity/20160829121518131.jpg
             * name : 测试文章
             */
            private boolean isCheck;
            private long article_id;
            private String img_url;
            private String name;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                this.isCheck = check;
            }

            public long getArticle_id() {
                return article_id;
            }

            public void setArticle_id(long article_id) {
                this.article_id = article_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
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
