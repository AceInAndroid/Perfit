package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

/**
 * Created by zhangbing on 2017/2/27.
 */

public class APIExceptionBean extends BaseBean {
    public static final int TYPE_SUCCSESS = 0x101;
    public static final int TYPE_RELOGIN = 0x102;
    public static final int TYPE_AUTHERRO = 0x103;
    private int TYPE;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public APIExceptionBean(int TYPE) {
        this.TYPE = TYPE;
    }


    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }
}
