package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/31.
 */

public class RegisterBean {

    /**
     * message : 成功！
     * data : {"auth_id":"sadasd","auth_code":"sdassadasd","user_id":10000}
     * code : 200
     */

    private String message;
    /**
     * auth_id : sadasd
     * auth_code : sdassadasd
     * user_id : 10000
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
        private int user_id;

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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
