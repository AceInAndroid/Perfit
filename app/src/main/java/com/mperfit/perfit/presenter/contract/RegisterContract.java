package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * Created by zhangbing on 16/10/28.
 */

public class RegisterContract {
    
public interface View extends BaseView{
    void showRegistResult(int result,String msg);
    void showRegistResult(String msg);
}

public interface Presenter extends BasePresenter<View>{
    void getInvalidateCode(String phone,int type);
    void goRegist(String phoneNum, String password,String code,String uMengToken);
}


}