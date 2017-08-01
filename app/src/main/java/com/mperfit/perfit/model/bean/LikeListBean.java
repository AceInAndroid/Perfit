package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/9.
 */

public class LikeListBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161108191249177.jpg?imageView2/0/w/330/h/216","time":"10.9-1.1","activity_id":785054266669662208,"status":2,"address":"SpaceCyc","name":"还是花花"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161109163438210.jpg?imageView2/0/w/330/h/216","time":"11.11-12.1","activity_id":791942042316963840,"status":1,"address":"北京市昌平区老牛湾村","differ_day":"还有2天","name":"测试数据"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923174110629.jpg?imageView2/0/w/330/h/216","time":"10.6-1.1","activity_id":779254269785145344,"status":2,"address":"东直门","name":"【国庆】体验石林峡宇宙之眼，走世界第一钛合金玻璃观景台"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/null?imageView2/0/w/330/h/216","time":"11.6-11.27","activity_id":779153266477891584,"status":2,"address":"北京","name":"我从你的全世界走过.."}],"current_page":1}
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
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161108191249177.jpg?imageView2/0/w/330/h/216","time":"10.9-1.1","activity_id":785054266669662208,"status":2,"address":"SpaceCyc","name":"还是花花"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161109163438210.jpg?imageView2/0/w/330/h/216","time":"11.11-12.1","activity_id":791942042316963840,"status":1,"address":"北京市昌平区老牛湾村","differ_day":"还有2天","name":"测试数据"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923174110629.jpg?imageView2/0/w/330/h/216","time":"10.6-1.1","activity_id":779254269785145344,"status":2,"address":"东直门","name":"【国庆】体验石林峡宇宙之眼，走世界第一钛合金玻璃观景台"},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/null?imageView2/0/w/330/h/216","time":"11.6-11.27","activity_id":779153266477891584,"status":2,"address":"北京","name":"我从你的全世界走过.."}]
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20161108191249177.jpg?imageView2/0/w/330/h/216
             * time : 10.9-1.1
             * activity_id : 785054266669662208
             * status : 2
             * address : SpaceCyc
             * name : 还是花花
             * differ_day : 还有2天
             */
            private boolean isSelect=true;
            private String img_url;
            private String time;
            private long activity_id;
            private int status;
            private String address;
            private String name;
            private String differ_day;


            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

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

            public String getDiffer_day() {
                return differ_day;
            }

            public void setDiffer_day(String differ_day) {
                this.differ_day = differ_day;
            }
        }
    }
}
