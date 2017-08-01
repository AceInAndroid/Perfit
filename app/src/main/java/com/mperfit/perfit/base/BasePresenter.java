package com.mperfit.perfit.base;

/**
 * Created by zhangbing on 2016/10/13.
 * Presenter基类
 */
public interface BasePresenter<T extends BaseView>{
    //和View绑定的方法 啦啦啦啦
    void attachView(T view);

    void detachView();
}
