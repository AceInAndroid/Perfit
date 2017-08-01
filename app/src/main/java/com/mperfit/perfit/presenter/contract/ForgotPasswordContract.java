package com.mperfit.perfit.presenter.contract;

import com.mperfit.perfit.base.BasePresenter;
import com.mperfit.perfit.base.BaseView;

/**
 * Created by zhangbing on 16/10/31.
 */

public class ForgotPasswordContract {

    public interface View extends BaseView {
        void showFindResult(int type,String msg);
        void showGetCodeResult(String msg);
    }

    public interface Presenter extends BasePresenter<ForgotPasswordContract.View> {
        void getInvalidateCode(String phone,int type);
        void goFindPass(String phoneNum, String password, String code);
    }


}