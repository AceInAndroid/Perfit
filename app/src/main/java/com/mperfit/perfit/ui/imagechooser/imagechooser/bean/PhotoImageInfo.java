package com.mperfit.perfit.ui.imagechooser.imagechooser.bean;

/**
 * Created by zhangbing on 2017/1/16.
 */

public class PhotoImageInfo {

    //state用于判断选中 1是选中 0是没选中
    public int state=1;
    public String path;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
