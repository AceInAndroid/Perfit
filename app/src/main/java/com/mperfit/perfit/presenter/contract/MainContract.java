package com.mperfit.perfit.presenter.contract;


import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * Created by zhangbing on 16/8/9.
 */

public interface MainContract {

    interface View extends BaseView {

    }

    interface  Presenter extends BasePresenter<View> {

    }
}
