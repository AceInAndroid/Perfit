package com.mperfit.perfit.presenter;


import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.presenter.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by zhangbing on 16/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    @Inject
    public MainPresenter() {

    }


}
