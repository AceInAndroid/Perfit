package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/6.
 */

public class AddShopCommentBean {

    /**
     * code : 100
     * data : {}
     * message : 成功！
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
    }
}
