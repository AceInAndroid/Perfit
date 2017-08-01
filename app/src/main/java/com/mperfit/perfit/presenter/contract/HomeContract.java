package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.HomeDataBean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/15.
 */

public class HomeContract  {
    
    
public interface View extends BaseView{

    void showContent(HomeDataBean.DataBean data);
}

public interface Presenter extends BasePresenter<View>{
    void getHomeData();

}




}