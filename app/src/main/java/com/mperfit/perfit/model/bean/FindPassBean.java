package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/31.
 */

public class FindPassBean {

    /**
     * message : 成功！
     * data :
     * code : 200
     */

    private String message;
    private String data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
