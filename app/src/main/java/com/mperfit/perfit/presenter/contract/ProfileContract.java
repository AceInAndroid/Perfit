package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;
import com.mperfit.perfit.model.bean.ProfileBean;

import okhttp3.RequestBody;

/**
 * Created by zhangbing on 16/11/3.
 */

public class ProfileContract {
    
public interface View extends BaseView{
    void showProfileInfo(ProfileBean profileBean);
    void showSaveReult(int type , String msg);
}

public interface Presenter extends BasePresenter<View> {
    void getProfileInfo();
    void saveProfileInfo(String name, int sex, String head,
                         String birth, String profession,
                         String enmotionState, String signature);


    void saveProfileInfo(String name, int sex,
                         String birth, String profession,
                         String enmotionState, String signature);


}



}