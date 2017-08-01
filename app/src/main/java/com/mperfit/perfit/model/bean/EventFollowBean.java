package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2017/2/18.
 */

public class EventFollowBean {

    //1表示关注了 0表示未关注
    private int type ;
    private long userid ;


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public EventFollowBean(int type, long userid) {
        this.type = type;
        this.userid = userid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
