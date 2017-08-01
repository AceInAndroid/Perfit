package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2017/1/13.
 */

public class PhotoSelectCountMsgBean {

    public PhotoSelectCountMsgBean(int count) {
        this.count = count;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
