package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/12/9.
 */

public class VenueListInfoBean {


    /**
     * message : 成功！
     * data : {"total_page":3,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161128174028138.jpg?imageView2/1/w/340/h/288","distance":"","address":"儿饿 圩幵夺城","tag":"会所","name":"你胆魄","random":2,"seller_id":778479287597531136,"introduction":"关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。\n"}],"current_page":1}
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
         * total_page : 3
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/seller/20161128174028138.jpg?imageView2/1/w/340/h/288","distance":"","address":"儿饿 圩幵夺城","tag":"会所","name":"你胆魄","random":2,"seller_id":778479287597531136,"introduction":"关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。\n"}]
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/seller/20161128174028138.jpg?imageView2/1/w/340/h/288
             * distance :
             * address : 儿饿 圩幵夺城
             * tag : 会所
             * name : 你胆魄
             * random : 2
             * seller_id : 778479287597531136
             * introduction : 关于 Uppercut 搏击健身馆Uppercut 是一家拳击泰拳馆，提供搏击健身、儿童家庭拳击，高端非职业拳击技术学习，以及职业拳击赛前训练。 我们通过小规模群体课和私教相结合的教学方式，对不同年龄的儿童、不同健身基础的成人进行定制体系训练。　 教练都具有长期的职业搏击经验，多年以来通过独特的教学方式帮助了数千人达到理想的健身塑身的效果和拳击技术训练的目标。搏击健身近几年广泛的被认知，它不仅在训练过程中提高个人运动体能，更能良好的训练身体的协调性和敏捷度，结合不同课程的配合训练使身体达到最理想的健康状态。

             */

            private String img_url;
            private String distance;
            private String address;
            private String tag;
            private String name;
            private int random;
            private long seller_id;
            private String introduction;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public int getRandom() {
                return random;
            }

            public void setRandom(int random) {
                this.random = random;
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
