package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class RefoundComoleteInfoBean {


    /**
     * message : 成功！
     * data : {"refund":{"refund_id":805612895114100736}}
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
         * refund : {"refund_id":805612895114100736}
         */

        private RefundBean refund;

        public RefundBean getRefund() {
            return refund;
        }

        public void setRefund(RefundBean refund) {
            this.refund = refund;
        }

        public static class RefundBean {
            /**
             * refund_id : 805612895114100736
             */

            private long refund_id;

            public long getRefund_id() {
                return refund_id;
            }

            public void setRefund_id(long refund_id) {
                this.refund_id = refund_id;
            }
        }
    }
}
