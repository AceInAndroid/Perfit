package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * Created by zhangbing on 16/10/27.
 */

public class EnrollContract  {
    
public interface View extends BaseView{
    void showActivityInfo();
    void showIdentifyingCode(String msg);
    void showEnrollResult(int type,String msg);
    void showMatchEnrollResult(int type,String msg);

}

public interface Presenter extends BasePresenter<EnrollContract.View>{
    void getActivityInfo();
    void putEnrollFormInfo(String activityId, String name, String phone,
                           int sex, int age, String remark, String code);

    void putMatchEnrollFormInfo(String activityId);


    void getPhoneCode(String phone,int type);


}


}