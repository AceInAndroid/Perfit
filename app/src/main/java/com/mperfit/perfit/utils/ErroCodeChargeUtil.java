package com.mperfit.perfit.utils;

import com.mperfit.perfit.app.Constants;

/**
 * Created by zhangbing on 2016/11/12.
 */

public class ErroCodeChargeUtil {

    public static int checkCode(int code) {
        if (code == 10003) {
            //2鉴权失败
            return Constants.LIKE_RESULT_SINGFILED;
        } else if (code == 100) {
            return Constants.LIKE_RESULT_SUCCESS;
        } else if (code == 10006) {
            return Constants.LIKE_RESULT_SINGFILED;
        } else if (code == 10002){
            return Constants.LIKE_TYPE_DISABLE;
        } else {
            return Constants.LIKE_TYPE_FILED;
        }
    }

}
