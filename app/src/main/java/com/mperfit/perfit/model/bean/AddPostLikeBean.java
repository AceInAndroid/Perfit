package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2017/2/16.
 */

public class AddPostLikeBean {


    /**
     * message : 成功！
     * data : {}
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
    }
}
