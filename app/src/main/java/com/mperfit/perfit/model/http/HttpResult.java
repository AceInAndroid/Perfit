package com.mperfit.perfit.model.http;


/**
 * Created by zhangbing on 2017/2/15.
 */

public class HttpResult<T> {
    private int code;
    private String message;  // 返回信息
    private T data;  // 包装的对象

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
