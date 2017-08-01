package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.MyJoinedActivityBean;
import com.mperfit.perfit.model.bean.PersonalMyGameBean;

/**
 * Created by zhangbing on 16/11/3.
 */

public class MyJoinContract {
    
public interface View extends BaseView{
    void showMyJoinedGame(PersonalMyGameBean myJoinedActivityBean);
    void showMyJoinedActivity(MyJoinedActivityBean myJoinedActivityBean);
    void showMoreMyJoinedGame(PersonalMyGameBean myJoinedActivityBean);
    void showMoreMyJoinedActivity(MyJoinedActivityBean myJoinedActivityBean);
    void reLogin();
}

public interface Presenter extends BasePresenter<View>{
    void getMyJoinedGame();
    void getMyJoinedActivity();
    void getMyJoinedGameMore(int page);
    void getMyJoinedActivityMore(int page);
}


}