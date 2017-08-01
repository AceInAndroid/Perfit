package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/12/10.
 */

public class VenueAreaListBean {


    /**
     * message : 成功！
     * data : {"area_id":null,"list":[{"area_id":0,"name":"附近"},{"area_id":1,"name":"中关村"},{"area_id":2,"name":"国贸"},{"area_id":3,"name":"望京"},{"area_id":4,"name":"三里屯"},{"area_id":5,"name":"海淀区"},{"area_id":10,"name":"朝阳区"},{"area_id":11,"name":"西城区"},{"area_id":12,"name":"五道口"}]}
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
         * area_id : null
         * list : [{"area_id":0,"name":"附近"},{"area_id":1,"name":"中关村"},{"area_id":2,"name":"国贸"},{"area_id":3,"name":"望京"},{"area_id":4,"name":"三里屯"},{"area_id":5,"name":"海淀区"},{"area_id":10,"name":"朝阳区"},{"area_id":11,"name":"西城区"},{"area_id":12,"name":"五道口"}]
         */

        private Object area_id;
        private List<ListBean> list;

        public Object getArea_id() {
            return area_id;
        }

        public void setArea_id(Object area_id) {
            this.area_id = area_id;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * area_id : 0
             * name : 附近
             */

            private int area_id;
            private String name;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
