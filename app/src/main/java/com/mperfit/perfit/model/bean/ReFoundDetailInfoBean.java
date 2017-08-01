package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 2016/12/4.
 */

public class ReFoundDetailInfoBean {

    /**
     * message : 成功！
     * data : {"refund":{"id":810742671810232320,"price":0.01,"status":2,"pay_type":2,"finish_time":null,"create_time":1482131090000,"kf_phone":"17701085590","audit_time":1482131090000,"number":1,"out_refund_no":"201612191504507288203061"}}
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
         * refund : {"id":810742671810232320,"price":0.01,"status":2,"pay_type":2,"finish_time":null,"create_time":1482131090000,"kf_phone":"17701085590","audit_time":1482131090000,"number":1,"out_refund_no":"201612191504507288203061"}
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
             * id : 810742671810232320
             * price : 0.01
             * status : 2
             * pay_type : 2
             * finish_time : null
             * create_time : 1482131090000
             * kf_phone : 17701085590
             * audit_time : 1482131090000
             * number : 1
             * out_refund_no : 201612191504507288203061
             */

            private long id;
            private double price;
            private int status;
            private int pay_type;
            private long finish_time;
            private long create_time;
            private String kf_phone;
            private long audit_time;
            private int number;
            private String out_refund_no;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public long getFinish_time() {
                return finish_time;
            }

            public void setFinish_time(long finish_time) {
                this.finish_time = finish_time;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getKf_phone() {
                return kf_phone;
            }

            public void setKf_phone(String kf_phone) {
                this.kf_phone = kf_phone;
            }

            public long getAudit_time() {
                return audit_time;
            }

            public void setAudit_time(long audit_time) {
                this.audit_time = audit_time;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getOut_refund_no() {
                return out_refund_no;
            }

            public void setOut_refund_no(String out_refund_no) {
                this.out_refund_no = out_refund_no;
            }
        }
    }
}
