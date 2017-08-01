package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2017/2/18.
 */

public class EventCommentBean {

    private int position;
    private int count;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EventCommentBean(int mPosition) {
        this.position = mPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
