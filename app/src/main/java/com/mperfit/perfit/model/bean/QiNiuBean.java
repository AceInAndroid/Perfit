package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/5.
 */

public class QiNiuBean {


    /**
     * message : 成功！
     * data : {"token":"aTYGSSdVHYdZDSbQHQP_b5B8d01VNcfGgX-LXPBG:OCLU9DPzTaEa0kGsuKCT4IaqFWc=:eyJzY29wZSI6InBlcmZpdC1pbWFnZSIsImRlYWRsaW5lIjoxNDc5ODc4MTkzfQ=="}
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
         * token : aTYGSSdVHYdZDSbQHQP_b5B8d01VNcfGgX-LXPBG:OCLU9DPzTaEa0kGsuKCT4IaqFWc=:eyJzY29wZSI6InBlcmZpdC1pbWFnZSIsImRlYWRsaW5lIjoxNDc5ODc4MTkzfQ==
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
