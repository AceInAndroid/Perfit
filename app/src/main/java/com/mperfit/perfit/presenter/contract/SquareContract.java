package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.SquareListBean;
import com.mperfit.perfit.model.bean.SquareMorePostBean;

import rx.subjects.PublishSubject;

/**
 * Created by zhangbing on 2017/2/16.
 */

public class SquareContract {
    
    
public interface View extends BaseView{
    void showContent(SquareListBean.DataBean dataBean);
    void loadMoreContent(SquareMorePostBean.DataBean dataBean);
    void showTagsContent(SquareMorePostBean.DataBean dataBean);
    void showAddLikeResult(int position);
    void showRemoveLikeResult(int position);
    void showFollowResult(int position);
    void showRemoveFollowResult(int position);
    void showDeleteResult(int position);
}

public interface Presenter extends BasePresenter<View>{
    void getSquareList(int page, long topic_id, ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void getSquareTagsList(int page, long topic_id, ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void getMoreSquarePostList(int page, long topic_id, ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void addLike(int position,long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void removeLike(int position,long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void addFollow(int position,long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void removeFollow(int position,long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void deletePost(int position,long postId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);

}




}