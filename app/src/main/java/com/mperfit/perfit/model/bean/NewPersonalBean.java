package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

/**
 * Created by zhangbing on 2017/2/20.
 */

public class NewPersonalBean extends BaseBean {

    /**
     * message : 成功！
     * data : {"user":{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/0/w/200/h/200","follow_count":9,"sex":"1","total_hot":100,"fans_count":6,"name":"张冰","user_id":798042230735503360,"note_count":27,"signature":"的\n"}}
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
         * user : {"img_url":"http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/0/w/200/h/200","follow_count":9,"sex":"1","total_hot":100,"fans_count":6,"name":"张冰","user_id":798042230735503360,"note_count":27,"signature":"的\n"}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com//user/20170307210120701?imageView2/0/w/200/h/200
             * follow_count : 9
             * sex : 1
             * total_hot : 100
             * fans_count : 6
             * name : 张冰
             * user_id : 798042230735503360
             * note_count : 27
             * signature : 的

             */

            private String img_url;
            private int follow_count;
            private String sex;
            private int total_hot;
            private int fans_count;
            private String name;
            private long user_id;
            private int note_count;
            private String signature;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getTotal_hot() {
                return total_hot;
            }

            public void setTotal_hot(int total_hot) {
                this.total_hot = total_hot;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
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

            public int getNote_count() {
                return note_count;
            }

            public void setNote_count(int note_count) {
                this.note_count = note_count;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
