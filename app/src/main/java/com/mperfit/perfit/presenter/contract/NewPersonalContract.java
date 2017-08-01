package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.component.ActivityLifeCycleEvent;
import com.mperfit.perfit.model.bean.NewPersonalBean;
import com.mperfit.perfit.model.bean.OthersUserCenterBean;
import com.mperfit.perfit.model.bean.PersonalMyPostBean;

import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by zhangbing on 2017/2/20.
 */

public class NewPersonalContract {
    
public interface View extends BaseView{
    void showContent(NewPersonalBean.DataBean dataBean);
    void showMorePost(PersonalMyPostBean.DataBean noteListBean);
    void showPost(PersonalMyPostBean.DataBean noteListBean);
    void showMoreLike(PersonalMyPostBean.DataBean likeListBean);
    void showLike(PersonalMyPostBean.DataBean likeListBean);

}

public interface Presenter extends BasePresenter<View>{
    void getOthersPersonalInfo( ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
    void getMoreMyselfPost(int page, long userId,ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
    void getMyselfPost(int page, long userId,ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
    void getMoreMyselfLike(int page, long userId,ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);
    void getMyselfLike(int page, long userId,ActivityLifeCycleEvent lifeCycleEvent, PublishSubject<ActivityLifeCycleEvent> subject);

}

}