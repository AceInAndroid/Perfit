package com.mperfit.perfit.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangbing on 2016/12/1.
 */

public class CourseOrderCompletedBean {


    /**
     * message : 成功！
     * data : {"qd_list":[{"id":810479731156713472,"order_refund_id":"","status":1,"code":"474698376456"},{"id":810479731160907776,"order_refund_id":"","status":1,"code":"040495178372"}],"course_name":"大大大大大小小小小小小","course_id":786406103729569792,"order_id":810479041852211200}
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
         * qd_list : [{"id":810479731156713472,"order_refund_id":"","status":1,"code":"474698376456"},{"id":810479731160907776,"order_refund_id":"","status":1,"code":"040495178372"}]
         * course_name : 大大大大大小小小小小小
         * course_id : 786406103729569792
         * order_id : 810479041852211200
         */

        private String course_name;
        private long course_id;
        private long order_id;
        private List<QdListBean> qd_list;

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public long getCourse_id() {
            return course_id;
        }

        public void setCourse_id(long course_id) {
            this.course_id = course_id;
        }

        public long getOrder_id() {
            return order_id;
        }

        public void setOrder_id(long order_id) {
            this.order_id = order_id;
        }

        public List<QdListBean> getQd_list() {
            return qd_list;
        }

        public void setQd_list(List<QdListBean> qd_list) {
            this.qd_list = qd_list;
        }

        public static class QdListBean implements Serializable {
            /**
             * id : 810479731156713472
             * order_refund_id :
             * status : 1
             * code : 474698376456
             */

            private long id;
            private String order_refund_id;
            private int status;
            private String code;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getOrder_refund_id() {
                return order_refund_id;
            }

            public void setOrder_refund_id(String order_refund_id) {
                this.order_refund_id = order_refund_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
