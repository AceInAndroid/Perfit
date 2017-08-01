package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 16/11/3.
 */

public class MyJoinedActivityBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923204807168.jpg?imageView2/0/w/330/h/216","time":"9.28-12.1","activity_id":779301318668845056,"status":3,"address":"王府井","name":"萤火虫跑团壹季体能训练体验课"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161114194731429.jpg?imageView2/0/w/330/h/216","time":"9.21-9.30","activity_id":778172872056111105,"status":3,"address":"三里屯工体广场","name":"【心跳的感觉】跑起来寻找诗和远方。3333333333333"}],"current_page":1}
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
         * total_page : 1
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923204807168.jpg?imageView2/0/w/330/h/216","time":"9.28-12.1","activity_id":779301318668845056,"status":3,"address":"王府井","name":"萤火虫跑团壹季体能训练体验课"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161114194731429.jpg?imageView2/0/w/330/h/216","time":"9.21-9.30","activity_id":778172872056111105,"status":3,"address":"三里屯工体广场","name":"【心跳的感觉】跑起来寻找诗和远方。3333333333333"}]
         * current_page : 1
         */

        private int total_page;
        private int current_page;
        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20160923204807168.jpg?imageView2/0/w/330/h/216
             * time : 9.28-12.1
             * activity_id : 779301318668845056
             * status : 3
             * address : 王府井
             * name : 萤火虫跑团壹季体能训练体验课
             */

            private String img_url;
            private String time;
            private long activity_id;
            private int status;
            private String address;
            private String name;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public long getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(long activity_id) {
                this.activity_id = activity_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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
