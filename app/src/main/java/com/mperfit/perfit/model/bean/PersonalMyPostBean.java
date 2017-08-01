package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/23.
 */

public class PersonalMyPostBean {

    /**
     * message : 成功！
     * data : {"total_page":2,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221162008383.jpg?imageView2/1/w/233/h/233","note_id":833954446944763900},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221161535732.jpg?imageView2/1/w/233/h/233","note_id":833953299404161000},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221101632803.jpg?imageView2/1/w/233/h/233","note_id":833862984706031600},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221101221252.jpg?imageView2/1/w/233/h/233","note_id":833861889556480000},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221100928396.jpg?imageView2/1/w/233/h/233","note_id":833861161601466400},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221100846511.jpg?imageView2/1/w/233/h/233","note_id":833860988657729500},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170220190650288.jpg?imageView2/1/w/233/h/233","note_id":833634009765380100},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170220190448975.jpg?imageView2/1/w/233/h/233","note_id":833633499410858000}],"current_page":2}
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
         * total_page : 2
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221162008383.jpg?imageView2/1/w/233/h/233","note_id":833954446944763900},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221161535732.jpg?imageView2/1/w/233/h/233","note_id":833953299404161000},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221101632803.jpg?imageView2/1/w/233/h/233","note_id":833862984706031600},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221101221252.jpg?imageView2/1/w/233/h/233","note_id":833861889556480000},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221100928396.jpg?imageView2/1/w/233/h/233","note_id":833861161601466400},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170221100846511.jpg?imageView2/1/w/233/h/233","note_id":833860988657729500},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170220190650288.jpg?imageView2/1/w/233/h/233","note_id":833634009765380100},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com//note/20170220190448975.jpg?imageView2/1/w/233/h/233","note_id":833633499410858000}]
         * current_page : 2
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com//note/20170221162008383.jpg?imageView2/1/w/233/h/233
             * note_id : 833954446944763900
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
