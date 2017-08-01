package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/11/4.
 */

public class ProfileBean {


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
        private String token;
        /**
         * img_url : http://image.mperfit.com//user/default.jpg?imageView2/0/w/300/h/300
         * birth :
         * sex :
         * feeling :
         * profession :
         * name : 18567738448
         * user_id : 792973475840000000
         * signature : 这家伙很懒，什么都没留下！
         */

        private UserBean user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private String img_url;
            private String birth;
            private String sex;
            private String feeling;
            private String profession;
            private String name;
            private long user_id;
            private String signature;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getBirth() {
                return birth;
            }

            public void setBirth(String birth) {
                this.birth = birth;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getFeeling() {
                return feeling;
            }

            public void setFeeling(String feeling) {
                this.feeling = feeling;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
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
