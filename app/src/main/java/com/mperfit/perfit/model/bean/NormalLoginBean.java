package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/31.
 */

public class NormalLoginBean {


    /**
     * message : 成功！
     * data : {"auth_id":"Klv70uN4AVzj89aath5sSmp/Fb1eOf6qwH/ifmj3EU0=","auth_code":"10b1692a60103bf49a79f616c58aa5e50b6284bdce52e91d3064c0b135bf6367","user_id":798042230735503360}
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
         * auth_id : Klv70uN4AVzj89aath5sSmp/Fb1eOf6qwH/ifmj3EU0=
         * auth_code : 10b1692a60103bf49a79f616c58aa5e50b6284bdce52e91d3064c0b135bf6367
         * user_id : 798042230735503360
         */

        private String auth_id;
        private String auth_code;
        private long user_id;

        public String getAuth_id() {
            return auth_id;
        }

        public void setAuth_id(String auth_id) {
            this.auth_id = auth_id;
        }

        public String getAuth_code() {
            return auth_code;
        }

        public void setAuth_code(String auth_code) {
            this.auth_code = auth_code;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }
    }
}
