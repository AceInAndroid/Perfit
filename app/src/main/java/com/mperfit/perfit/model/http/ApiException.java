package com.mperfit.perfit.model.http;

import com.mperfit.perfit.model.bean.APIExceptionBean;
import com.mperfit.perfit.utils.RxBusUtils;

/**
 * 请求
 * Created by zhangbing on 2016/8/4.
 */
public class ApiException extends RuntimeException{

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    public static final int WRONG_DEFEATED = 10001;
    public static final int WRONG_PARAMETER_ERROR = 10002;
    public static final int WRONG_AUTHENTICATION_FAILED = 10003;
    public static final int WRONG_THE_HEAD_PARAMETER_ERROR = 10004;
    public static final int WRONG_SYSTEM_PARAMETER_ERROR = 10005;
    public static final int WRONG_LOG_BACK_IN = 10006;

    private static String message;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        switch (code) {
            case WRONG_DEFEATED:
                message = "失败";
                break;
            case WRONG_PARAMETER_ERROR:
                message = "请求参数错误";
                break;
            case WRONG_AUTHENTICATION_FAILED:
                message = "鉴权失败，请重新登录";
                RxBusUtils.getDefault().postSticky(new APIExceptionBean(APIExceptionBean.TYPE_RELOGIN));
                break;
            case WRONG_THE_HEAD_PARAMETER_ERROR:
                message = "头部参数错误";
                break;
            case WRONG_SYSTEM_PARAMETER_ERROR:
                message = "系统参数错误";
                break;
            case WRONG_LOG_BACK_IN:
                message = "重新登录";
                RxBusUtils.getDefault().postSticky(new APIExceptionBean(APIExceptionBean.TYPE_AUTHERRO));
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
