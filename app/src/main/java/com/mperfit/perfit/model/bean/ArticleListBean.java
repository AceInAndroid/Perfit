package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/18.
 */

public class ArticleListBean {


    /**
     * message : 成功！
     * data : {"total_page":2,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460","content":"SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。","article_id":786496370671353800,"tag":"音乐 单车","name":"音乐and单车 |spacecycle让你hight到高潮","random":2}],"current_page":1}
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
         * total_page : 2
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460","content":"SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。","article_id":786496370671353800,"tag":"音乐 单车","name":"音乐and单车 |spacecycle让你hight到高潮","random":2}]
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460
             * content : SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。
             * article_id : 786496370671353800
             * tag : 音乐 单车
             * name : 音乐and单车 |spacecycle让你hight到高潮
             * random : 2
             */

            private String img_url;
            private String content;
            private long article_id;
            private String tag;
            private String name;
            private int random;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getArticle_id() {
                return article_id;
            }

            public void setArticle_id(long article_id) {
                this.article_id = article_id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRandom() {
                return random;
            }

            public void setRandom(int random) {
                this.random = random;
            }
        }
    }
}
