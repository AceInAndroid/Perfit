package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.PostDetailBean;

import rx.subjects.PublishSubject;

/**
 * Created by zhangbing on 2017/2/17.
 */

public class PostDetailContract {

    public interface View extends BaseView{
        void showContent(PostDetailBean.DataBean dataBean);
        void showAddLikeResult();
        void showRemoveLikeResult();
        void showDeleteResult();
    }

    public interface Presenter extends BasePresenter<View>{
        void getPostDetailInfo(long postId, ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
        void addLike(long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
        void removeLike(long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
        void deletePost(long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);


    }
}