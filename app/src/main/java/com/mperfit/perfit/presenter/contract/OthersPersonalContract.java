package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;

import rx.subjects.PublishSubject;

/**
 * Created by zhangbing on 2017/2/17.
 */

public class OthersPersonalContract {
    
    
public interface View extends BaseView{
    void showContent(OthersUserCenterBean.DataBean dataBean);
    void showFollowResult();
    void showRemoveFollowResult();
    void showLoadMorePostResult(PersonalMyPostBean.DataBean postDataBean);
    void showLoadMoreLikeResult(PersonalMyPostBean.DataBean likeDataBean);

}

public interface Presenter extends BasePresenter<View>{
    void getOthersPersonalInfo(long id,ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
    void addFollow(long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void removeFollow(long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void getMorePost(int page,long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);
    void getMoreLike(int page,long userId,ActivityLifeCycleEvent lifeCycleEvent,PublishSubject<ActivityLifeCycleEvent> subject);

}


}