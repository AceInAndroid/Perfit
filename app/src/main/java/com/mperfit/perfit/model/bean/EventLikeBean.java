package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2017/2/18.
 */

public class EventLikeBean {

    private  int type;
    private int position;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EventLikeBean(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
