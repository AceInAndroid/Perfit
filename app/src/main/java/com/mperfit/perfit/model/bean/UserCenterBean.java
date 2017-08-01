package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/11/7.
 */

public class UserCenterBean {


    /**
     * message : 成功！
     * data : {"userMap":{"img_url":"http://image.mperfit.com//user/default.jpg?imageView2/0/w/500/h/500","sex":"1","name":"18567738448","user_id":792973475840000000,"signature":"这家伙很懒，什么都没留下！"}}
     * code : 100
     */

    private String message;
    /**
     * userMap : {"img_url":"http://image.mperfit.com//user/default.jpg?imageView2/0/w/500/h/500","sex":"1","name":"18567738448","user_id":792973475840000000,"signature":"这家伙很懒，什么都没留下！"}
     */

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
         * img_url : http://image.mperfit.com//user/default.jpg?imageView2/0/w/500/h/500
         * sex : 1
         * name : 18567738448
         * user_id : 792973475840000000
         * signature : 这家伙很懒，什么都没留下！
         */

        private UserMapBean userMap;

        public UserMapBean getUserMap() {
            return userMap;
        }

        public void setUserMap(UserMapBean userMap) {
            this.userMap = userMap;
        }

        public static class UserMapBean {
            private String img_url;
            private String sex;
            private String name;
            private long user_id;
            private String signature;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
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

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }
    }
}
