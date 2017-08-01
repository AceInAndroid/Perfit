package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.ArticleListBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.FitnessFragmentContract;
import com.mperfit.perfit.utils.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/18
*/

public class FitnessFragmentPresenterImpl extends RxPresenter<FitnessFragmentContract.View> implements FitnessFragmentContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FitnessFragmentPresenterImpl(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;

    }

    @Override
    public void getArticleList(final int page, int category) {
        Subscription subscription = mRetrofitHelper.fetchArticleList(page, category)
                .compose(RxUtil.<ArticleListBean>rxSchedulerHelper())
                .subscribe(new Action1<ArticleListBean>() {
                    @Override
                    public void call(ArticleListBean articleListBean) {
                        if (page > 1) {
                            mView.showMore(articleListBean);
                        } else {
                            mView.showContent(articleListBean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }


}