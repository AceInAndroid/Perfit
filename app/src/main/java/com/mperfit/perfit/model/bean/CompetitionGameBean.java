package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/19.
 */

public class CompetitionGameBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"list":[{"content":"获得100基础积分","img_url":"http://ocjp9x6x9.bkt.clouddn.com/qt.jpg?imageView2/0/w/750/h/650","unlock_content":"随时参与","name":"青铜","status":1,"score":0,"category_id":1},{"content":"获得200基础积分","img_url":"http://ocjp9x6x9.bkt.clouddn.com/by.jpg?imageView2/0/w/750/h/650","unlock_content":"积分达到1000","name":"白银","status":2,"score":0,"category_id":2}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content : 获得100基础积分
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/qt.jpg?imageView2/0/w/750/h/650
             * unlock_content : 随时参与
             * name : 青铜
             * status : 1
             * score : 0
             * category_id : 1
             */

            private String content;
            private String img_url;
            private String unlock_content;
            private String name;
            private int status;
            private int score;
            private int category_id;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getUnlock_content() {
                return unlock_content;
            }

            public void setUnlock_content(String unlock_content) {
                this.unlock_content = unlock_content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }
        }
    }
}
