package com.mperfit.perfit.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/19.
 */

public class CategoryCompetitionBean   {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/0/w/750/h/650","address":"奥森公园","name":"复古运动会","match_id":1,"is_lock":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/0/w/750/h/650","time":"2017.3.1 00:00","status":1,"address":"798艺术区","name":"极速前进","match_id":840043772388573184,"is_lock":0}],"current_page":1}
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
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/0/w/750/h/650","address":"奥森公园","name":"复古运动会","match_id":1,"is_lock":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/0/w/750/h/650","time":"2017.3.1 00:00","status":1,"address":"798艺术区","name":"极速前进","match_id":840043772388573184,"is_lock":0}]
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

        public static class ListBean implements MultiItemEntity{
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/0/w/750/h/650
             * address : 奥森公园
             * name : 复古运动会
             * match_id : 1
             * is_lock : 1
             * time : 2017.3.1 00:00
             * status : 1
             */

            public static final int LOCKED = 1;
            public static final int UNLOCKED = 0;

            private String img_url;
            private String address;
            private String name;
            private long match_id;
            private int is_lock;
            private String time;
            private int status;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
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

            public long getMatch_id() {
                return match_id;
            }

            public void setMatch_id(long match_id) {
                this.match_id = match_id;
            }

            public int getIs_lock() {
                return is_lock;
            }

            public void setIs_lock(int is_lock) {
                this.is_lock = is_lock;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            @Override
            public int getItemType() {
                return is_lock;
            }
        }
    }
}
