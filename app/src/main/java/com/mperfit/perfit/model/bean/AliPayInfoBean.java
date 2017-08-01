package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/1.
 */

public class AliPayInfoBean {


    /**
     * message : 成功！
     * data : {"sign":"app_id=2016101202114739&biz_content=%7B%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%E4%BD%93%E9%AA%8C%E5%8D%A1%E5%88%B8%EF%BC%8C%E5%8D%95%E8%BD%A610%E8%8A%82%E8%AF%BE%E3%80%82%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22out_trade_no%22%3A%22201611291626389244463496%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest-app.mperfit.com%2Falipay%2Fcourse%2Fnotify&sign=wSBEssVtCNHwegYcWVOjczTUXH4ZwIxZqlJ8NLEMSANora3CYDwU5iMBsG3VNCRRSeBNAL9hc4%2FrwhwWgZ5hWmacL6WXT4JYMeYTAzAVvrOKh3wHj404iEJ18DifmTe3Q%2FRO3QLnOpK723EwiZRWxg1DCLnl%2F%2Ba3gxkHzub4xJY%3D&sign_type=RSA&timestamp=2016-11-29+16%3A42%3A50&version=1.0","code":"201611291626389244463496","order_id":803515499102076900}
     * code : 100
     */

    private String message;
    private DataBean data;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * sign : app_id=2016101202114739&biz_content=%7B%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%E4%BD%93%E9%AA%8C%E5%8D%A1%E5%88%B8%EF%BC%8C%E5%8D%95%E8%BD%A610%E8%8A%82%E8%AF%BE%E3%80%82%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22out_trade_no%22%3A%22201611291626389244463496%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest-app.mperfit.com%2Falipay%2Fcourse%2Fnotify&sign=wSBEssVtCNHwegYcWVOjczTUXH4ZwIxZqlJ8NLEMSANora3CYDwU5iMBsG3VNCRRSeBNAL9hc4%2FrwhwWgZ5hWmacL6WXT4JYMeYTAzAVvrOKh3wHj404iEJ18DifmTe3Q%2FRO3QLnOpK723EwiZRWxg1DCLnl%2F%2Ba3gxkHzub4xJY%3D&sign_type=RSA&timestamp=2016-11-29+16%3A42%3A50&version=1.0
         * code : 201611291626389244463496
         * order_id : 803515499102076900
         */

        private String sign;
        private String code;
        private long order_id;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }
    }
}
