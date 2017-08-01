package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ArticleContract;

import javax.inject.Inject;

/**
* Created by zhangbing on 2016/10/18
*/

public class ArticlePresenterImpl extends RxPresenter<ArticleContract.View> implements ArticleContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ArticlePresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getData() {

    }

}