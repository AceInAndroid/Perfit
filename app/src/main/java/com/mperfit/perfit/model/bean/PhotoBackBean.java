package com.mperfit.perfit.model.bean;

import java.util.ArrayList;

/**
 * Created by zhangbing on 2017/1/19.
 */

public class PhotoBackBean {

    public PhotoBackBean(ArrayList<String> mlist) {

        this.mlist = mlist;
    }

    private ArrayList<String> mlist;

    public ArrayList<String> getMlist() {
        return mlist;
    }

    public void setMlist(ArrayList<String> mlist) {
        this.mlist = mlist;
    }
}
