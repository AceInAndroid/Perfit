package com.mperfit.perfit.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangbing on 2016/12/1.
 */

public class WeixinPayInfoBean {


    /**
     * message : 成功！
     * data : {"appid":"wx11ef503bfa72cb61","code":"622016206632","noncestr":"14c4f36143b4b09cbc320d7c95a50ee7","order_id":801645360404496400,"package":"Sign=WXPay","partnerid":"1398739502","prepayid":"wx20161124123522c05b1060390649738228","sign":"3C1F88B97AEC9F933B7F0D6C98590AD6","timestamp":1479962122}
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
         * appid : wx11ef503bfa72cb61
         * code : 622016206632
         * noncestr : 14c4f36143b4b09cbc320d7c95a50ee7
         * order_id : 801645360404496400
         * package : Sign=WXPay
         * partnerid : 1398739502
         * prepayid : wx20161124123522c05b1060390649738228
         * sign : 3C1F88B97AEC9F933B7F0D6C98590AD6
         * timestamp : 1479962122
         */

        private String appid;
        private String code;
        private String noncestr;
        private long order_id;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private long timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
