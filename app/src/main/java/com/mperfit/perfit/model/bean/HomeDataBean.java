package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/17.
 */

public class HomeDataBean  {

    /**
     * message : 成功！
     * data : {"articele_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460","content":"SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。","biz_id":786496370671353800,"tag":"音乐 单车","name":"音乐and单车 "}],"articele_category_str":"健身/减肥/娱乐","seller_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/null?imageView2/1/w/234/h/234","name":"Iron life 铁生活健身工作室 ","seller_id":806432245840609300}],"banner_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161021200410891.jpg?imageView2/0/w/330/h/216","depict":"测试","biz_type":1,"biz_id":1,"url":"www.baidu.com","is_click":0}]}
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
         * articele_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460","content":"SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。","biz_id":786496370671353800,"tag":"音乐 单车","name":"音乐and单车 "}]
         * articele_category_str : 健身/减肥/娱乐
         * seller_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/null?imageView2/1/w/234/h/234","name":"Iron life 铁生活健身工作室 ","seller_id":806432245840609300}]
         * banner_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161021200410891.jpg?imageView2/0/w/330/h/216","depict":"测试","biz_type":1,"biz_id":1,"url":"www.baidu.com","is_click":0}]
         */

        private String articele_category_str;
        private List<ArticeleListBean> articele_list;
        private List<SellerListBean> seller_list;
        private List<BannerListBean> banner_list;

        public String getArticele_category_str() {
            return articele_category_str;
        }

        public void setArticele_category_str(String articele_category_str) {
            this.articele_category_str = articele_category_str;
        }

        public List<ArticeleListBean> getArticele_list() {
            return articele_list;
        }

        public void setArticele_list(List<ArticeleListBean> articele_list) {
            this.articele_list = articele_list;
        }

        public List<SellerListBean> getSeller_list() {
            return seller_list;
        }

        public void setSeller_list(List<SellerListBean> seller_list) {
            this.seller_list = seller_list;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public static class ArticeleListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20161013171841642.jpg?imageView2/1/w/750/h/460
             * content : SpaceCycle，运动应当成为一种生活方式，成为生活中的一部分。
             * biz_id : 786496370671353800
             * tag : 音乐 单车
             * name : 音乐and单车
             */

            private String img_url;
            private String content;
            private long biz_id;
            private String tag;
            private String name;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getBiz_id() {
                return biz_id;
            }

            public void setBiz_id(long biz_id) {
                this.biz_id = biz_id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class SellerListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/null?imageView2/1/w/234/h/234
             * name : Iron life 铁生活健身工作室
             * seller_id : 806432245840609300
             */

            private String img_url;
            private String name;
            private long seller_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
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
        }

        public static class BannerListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20161021200410891.jpg?imageView2/0/w/330/h/216
             * depict : 测试
             * biz_type : 1
             * biz_id : 1
             * url : www.baidu.com
             * is_click : 0
             */

            private String img_url;
            private String depict;
            private int biz_type;
            private long biz_id;
            private String url;
            private int is_click;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getDepict() {
                return depict;
            }

            public void setDepict(String depict) {
                this.depict = depict;
            }

            public int getBiz_type() {
                return biz_type;
            }

            public void setBiz_type(int biz_type) {
                this.biz_type = biz_type;
            }

            public long getBiz_id() {
                return biz_id;
            }

            public void setBiz_id(long biz_id) {
                this.biz_id = biz_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getIs_click() {
                return is_click;
            }

            public void setIs_click(int is_click) {
                this.is_click = is_click;
            }
        }
    }
}
