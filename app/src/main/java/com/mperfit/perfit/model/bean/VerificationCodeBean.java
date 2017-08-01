package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/31.
 */

public class VerificationCodeBean {


    private String data;
    private String message;
    private int code;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerificationCodeBean{" +
                "data='" + data + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
