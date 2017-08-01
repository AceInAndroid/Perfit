package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * Created by zhangbing on 16/10/28.
 */

public class LoginContract  {

public interface View extends BaseView{
    void showLoginResult(int type, long userId, String message);
    void toHome();

}

public interface Presenter extends BasePresenter<LoginContract.View>{
    void usePhoneLogin(String phone,String passWord,String uMengToken);
    void useWeixinLogin();

}



}