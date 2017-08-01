package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/19.
 */

public class PublishTopicBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"topicList":[{"id":831322361595691008,"name":"淡定","random":4},{"id":831322105596346368,"name":"悲剧","random":4}]}
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
        private List<TopicListBean> topicList;

        public List<TopicListBean> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<TopicListBean> topicList) {
            this.topicList = topicList;
        }

        public static class TopicListBean {
            /**
             * id : 831322361595691008
             * name : 淡定
             * random : 4
             */

            private long id;
            private String name;
            private int random;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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
