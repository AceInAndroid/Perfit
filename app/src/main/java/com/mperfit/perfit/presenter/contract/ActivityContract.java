package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ActivityBean;
import com.mperfit.perfit.model.bean.CompetitionGameBean;

/**
 * Created by zhangbing on 16/10/24.
 */

public class ActivityContract  {
    
public interface View extends BaseView {
    void showContent(ActivityBean activityBean);
    void showGameContent(CompetitionGameBean.DataBean competitionGameBean);
    void showMoreContent(ActivityBean activityBean);
    void showMoreGameContent(CompetitionGameBean.DataBean competitionGameBean);
}

public interface Presenter extends BasePresenter<View>{
    void getActivityData();
    void getGameData(int page);
    void getMoreDate(int currentPage);
}


}