package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/17.
 */

public class OthersUserCenterBean extends BaseBean {

    /**
     * message : 成功！
     * data : {"is_follow":1,"note_total_page":2,"like_total_page":1,"note_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170322164119862.jpg?imageView2/1/w/233/h/233","note_id":844469028016619520},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321195040563.jpg?imageView2/1/w/233/h/233","note_id":844154292821557248},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321181651384.jpg?imageView2/1/w/233/h/233","note_id":844130681092046848},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321175406548.jpg?imageView2/1/w/233/h/233","note_id":844124955229552640},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185637115.jpg?imageView2/1/w/233/h/233","note_id":839067754597187584},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185816574.jpg?imageView2/1/w/233/h/233","note_id":839067754592993280},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185622692.jpg?imageView2/1/w/233/h/233","note_id":839067309245988864},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185637115.jpg?imageView2/1/w/233/h/233","note_id":839067309229211648},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185622692.jpg?imageView2/1/w/233/h/233","note_id":839067208540749824},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185335084.jpg?imageView2/1/w/233/h/233","note_id":839066870685368320},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185223218.jpg?imageView2/1/w/233/h/233","note_id":839066870601482240},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185255690.jpg?imageView2/1/w/233/h/233","note_id":839066870672785408}],"like_list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321124429762.jpg?imageView2/1/w/233/h/233","note_id":844047053330841600}],"user":{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/200/h/200","follow_count":4,"sex":"2","total_hot":89,"fans_count":6,"name":"小时候可俊了","user_id":777775369972350976,"signature":"123，11111111111111111111111111"}}
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
         * is_follow : 1
         * note_total_page : 2
         * like_total_page : 1
         * note_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170322164119862.jpg?imageView2/1/w/233/h/233","note_id":844469028016619520},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321195040563.jpg?imageView2/1/w/233/h/233","note_id":844154292821557248},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321181651384.jpg?imageView2/1/w/233/h/233","note_id":844130681092046848},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321175406548.jpg?imageView2/1/w/233/h/233","note_id":844124955229552640},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185637115.jpg?imageView2/1/w/233/h/233","note_id":839067754597187584},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185816574.jpg?imageView2/1/w/233/h/233","note_id":839067754592993280},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185622692.jpg?imageView2/1/w/233/h/233","note_id":839067309245988864},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185637115.jpg?imageView2/1/w/233/h/233","note_id":839067309229211648},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185622692.jpg?imageView2/1/w/233/h/233","note_id":839067208540749824},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185335084.jpg?imageView2/1/w/233/h/233","note_id":839066870685368320},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185223218.jpg?imageView2/1/w/233/h/233","note_id":839066870601482240},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170307185255690.jpg?imageView2/1/w/233/h/233","note_id":839066870672785408}]
         * like_list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/note/20170321124429762.jpg?imageView2/1/w/233/h/233","note_id":844047053330841600}]
         * user : {"img_url":"http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/200/h/200","follow_count":4,"sex":"2","total_hot":89,"fans_count":6,"name":"小时候可俊了","user_id":777775369972350976,"signature":"123，11111111111111111111111111"}
         */

        private int is_follow;
        private int note_total_page;
        private int like_total_page;
        private UserBean user;
        private List<NoteListBean> note_list;
        private List<LikeListBean> like_list;

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public int getNote_total_page() {
            return note_total_page;
        }

        public void setNote_total_page(int note_total_page) {
            this.note_total_page = note_total_page;
        }

        public int getLike_total_page() {
            return like_total_page;
        }

        public void setLike_total_page(int like_total_page) {
            this.like_total_page = like_total_page;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<NoteListBean> getNote_list() {
            return note_list;
        }

        public void setNote_list(List<NoteListBean> note_list) {
            this.note_list = note_list;
        }

        public List<LikeListBean> getLike_list() {
            return like_list;
        }

        public void setLike_list(List<LikeListBean> like_list) {
            this.like_list = like_list;
        }

        public static class UserBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/user/20161124130232384.jpg?imageView2/0/w/200/h/200
             * follow_count : 4
             * sex : 2
             * total_hot : 89
             * fans_count : 6
             * name : 小时候可俊了
             * user_id : 777775369972350976
             * signature : 123，11111111111111111111111111
             */

            private String img_url;
            private int follow_count;
            private String sex;
            private int total_hot;
            private int fans_count;
            private String name;
            private long user_id;
            private String signature;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getTotal_hot() {
                return total_hot;
            }

            public void setTotal_hot(int total_hot) {
                this.total_hot = total_hot;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getUser_id() {
                return user_id;
            }

            public void setUser_id(long user_id) {
                this.user_id = user_id;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }
        }

        public static class NoteListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/note/20170322164119862.jpg?imageView2/1/w/233/h/233
             * note_id : 844469028016619520
             */

            private String img_url;
            private long note_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public long getNote_id() {
                return note_id;
            }

            public void setNote_id(long note_id) {
                this.note_id = note_id;
            }
        }

        public static class LikeListBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/note/20170321124429762.jpg?imageView2/1/w/233/h/233
             * note_id : 844047053330841600
             */

            private String img_url;
            private long note_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public long getNote_id() {
                return note_id;
            }

            public void setNote_id(long note_id) {
                this.note_id = note_id;
            }
        }
    }
}
