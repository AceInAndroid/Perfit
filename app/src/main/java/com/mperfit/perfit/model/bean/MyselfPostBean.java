package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

import java.util.List;

/**
 * Created by zhangbing on 2017/2/22.
 */

public class MyselfPostBean extends BaseBean {


    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://image.mperfit.com/imgimgimgimg?imageView2/1/w/1080/h/460","note_id":831454353574330400}],"current_page":1}
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
         * list : [{"img_url":"http://image.mperfit.com/imgimgimgimg?imageView2/1/w/1080/h/460","note_id":831454353574330400}]
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
             * img_url : http://image.mperfit.com/imgimgimgimg?imageView2/1/w/1080/h/460
             * note_id : 831454353574330400
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
