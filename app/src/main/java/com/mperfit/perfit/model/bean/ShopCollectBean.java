package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/30.
 */

public class ShopCollectBean {

    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160923160404894.jpg?imageView2/0/w/640/h/460","address":"苏州街","name":"MUSCLEMANNA健身工作室（中关村店）","seller_id":779229833631629312,"introduction":"MUSCLE MANNA健身工作室以强大的教练团队为核心资源，为满足您的锻炼需求提供个性化的特色课程方案、饮食计划和应有尽有的周边服务项目"}],"current_page":1}
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
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20160923160404894.jpg?imageView2/0/w/640/h/460","address":"苏州街","name":"MUSCLEMANNA健身工作室（中关村店）","seller_id":779229833631629312,"introduction":"MUSCLE MANNA健身工作室以强大的教练团队为核心资源，为满足您的锻炼需求提供个性化的特色课程方案、饮食计划和应有尽有的周边服务项目"}]
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20160923160404894.jpg?imageView2/0/w/640/h/460
             * address : 苏州街
             * name : MUSCLEMANNA健身工作室（中关村店）
             * seller_id : 779229833631629312
             * introduction : MUSCLE MANNA健身工作室以强大的教练团队为核心资源，为满足您的锻炼需求提供个性化的特色课程方案、饮食计划和应有尽有的周边服务项目
             */

            private String img_url;
            private String address;
            private String name;
            private long seller_id;
            private String introduction;
            private boolean checked = true;

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

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

            public long getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(long seller_id) {
                this.seller_id = seller_id;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
    }
}
