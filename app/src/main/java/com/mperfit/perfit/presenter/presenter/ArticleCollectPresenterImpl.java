package com.mperfit.perfit.presenter.presenter;
import com.mperfit.perfit.app.Constants;
import com.mperfit.perfit.base.RxPresenter;
import com.mperfit.perfit.model.bean.AddLikeBean;
import com.mperfit.perfit.model.bean.ArticleCollectBean;
import com.mperfit.perfit.model.http.RetrofitHelper;
import com.mperfit.perfit.presenter.contract.ArticleCollectContract;
import com.mperfit.perfit.utils.RxUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
* Created by zhangbing on 2016/11/28
*/

public class ArticleCollectPresenterImpl extends RxPresenter<ArticleCollectContract.View> implements ArticleCollectContract.Presenter{
    private RetrofitHelper retrofitHelper;
    @Inject
    public ArticleCollectPresenterImpl(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
//        this.realmHelper = realmHelper;
    }

    @Override
    public void getArticleCollectInfo(int page) {
        Subscription subscription = retrofitHelper.fetchArticleCollect(page)
                .compose(RxUtil.<ArticleCollectBean>rxSchedulerHelper())
                .subscribe(new Action1<ArticleCollectBean>() {
                    @Override
                    public void call(ArticleCollectBean articleCollectBean) {
                        mView.showContent(articleCollectBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }


    @Override
    public void deleteLike(final String id, int type) {
        Subscription subscribe = retrofitHelper.fetchDeleteLike(id, type)
                .compose(RxUtil.<AddLikeBean>rxSchedulerHelper())
                .subscribe(new Action1<AddLikeBean>() {
                    @Override
                    public void call(AddLikeBean addLikeBean) {
                        if (addLikeBean.getCode() == 100) {
                            mView.showDeleteResult(Constants.LIKE_RESULT_SUCCESS, addLikeBean.getMessage());
//                            String[] split = id.split(",");
//                            for (int i = 0; i < split.length; i++) {
//                                realmHelper.deleteLikeBean(split[i]);
//                                Logger.e("文章收藏+数据库删除" +split[i]+"成功");
//                            }
                        }
                        else if (addLikeBean.getCode() == 10003) {
                            //2鉴权失败
                            mView.showDeleteResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else if (addLikeBean.getCode() == 10006) {
                            //重新登录
                            mView.showDeleteResult(Constants.LIKE_RESULT_SINGFILED, addLikeBean.getMessage());
                        } else {
                            mView.showDeleteResult(Constants.LIKE_TYPE_FILED, addLikeBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.getMessage());
                    }
                });

        addSubscrebe(subscribe);

    }

    @Override
    public void deleteLikeFromDb(String id) {

    }

    @Override
    public void getMoreData(int page) {

        Subscription subscription = retrofitHelper.fetchArticleCollect(page)
                .compose(RxUtil.<ArticleCollectBean>rxSchedulerHelper())
                .subscribe(new Action1<ArticleCollectBean>() {
                    @Override
                    public void call(ArticleCollectBean articleCollectBean) {
                        mView.showMore(articleCollectBean);
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