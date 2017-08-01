package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/29.
 */

public class WeixinLoginBean {


    /**
     * message : 成功！
     * data : {"auth_id":"eanDMtI+oK2I+zbwUbc0oT8oOKiDr1hxUtHskd/LDeE=","auth_code":"1c223e432d7c4ba94a6f96afa247ebacea3005afe9908096aba8eeeb0dcb9177","user_id":792277396307836928}
     * code : 100
     */

    private String message;
    /**
     * auth_id : eanDMtI+oK2I+zbwUbc0oT8oOKiDr1hxUtHskd/LDeE=
     * auth_code : 1c223e432d7c4ba94a6f96afa247ebacea3005afe9908096aba8eeeb0dcb9177
     * user_id : 792277396307836928
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
