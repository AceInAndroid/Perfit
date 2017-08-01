package com.mperfit.perfit.model.bean;

/**
 * Created by zhangbing on 16/10/25.
 */

public class ActivityDetailBean {


    /**
     * message : 成功！
     * data : {"activity":{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/1/w/750/h/540","activity_id":785054266669662200,"tag":"运动 社交","now_enroll_num":18,"content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","enroll_status":1,"is_enroll":"","time":"10.9 02:00 - 1.1 16:00","is_collect":1,"price":0,"market_price":199,"address":"北京朝阳区三里屯路11号院西座N4-40A","img_text":"xxxxx","specific_address":"SpaceCyc","name":"还是花花","enroll_num":50,"longitude":116.45493,"latitude":39.93757}}
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
         * activity : {"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/1/w/750/h/540","activity_id":785054266669662200,"tag":"运动 社交","now_enroll_num":18,"content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","enroll_status":1,"is_enroll":"","time":"10.9 02:00 - 1.1 16:00","is_collect":1,"price":0,"market_price":199,"address":"北京朝阳区三里屯路11号院西座N4-40A","img_text":"xxxxx","specific_address":"SpaceCyc","name":"还是花花","enroll_num":50,"longitude":116.45493,"latitude":39.93757}
         */

        private ActivityBean activity;

        public ActivityBean getActivity() {
            return activity;
        }

        public void setActivity(ActivityBean activity) {
            this.activity = activity;
        }

        public static class ActivityBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/1/w/750/h/540
             * activity_id : 785054266669662200
             * tag : 运动 社交
             * now_enroll_num : 18
             * content : 以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和
             * enroll_status : 1
             * is_enroll :
             * time : 10.9 02:00 - 1.1 16:00
             * is_collect : 1
             * price : 0
             * market_price : 199
             * address : 北京朝阳区三里屯路11号院西座N4-40A
             * img_text : xxxxx
             * specific_address : SpaceCyc
             * name : 还是花花
             * enroll_num : 50
             * longitude : 116.45493
             * latitude : 39.93757
             */

            private String img_url;
            private long activity_id;
            private String tag;
            private int now_enroll_num;
            private String content;
            private int enroll_status;
            private String is_enroll;
            private String time;
//            private int is_collect;
            private int price;
            private int market_price;
            private String address;
            private String img_text;
            private String specific_address;
            private String name;
            private int enroll_num;
            private double longitude;
            private double latitude;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public long getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(long activity_id) {
                this.activity_id = activity_id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getNow_enroll_num() {
                return now_enroll_num;
            }

            public void setNow_enroll_num(int now_enroll_num) {
                this.now_enroll_num = now_enroll_num;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getEnroll_status() {
                return enroll_status;
            }

            public void setEnroll_status(int enroll_status) {
                this.enroll_status = enroll_status;
            }

            public String getIs_enroll() {
                return is_enroll;
            }

            public void setIs_enroll(String is_enroll) {
                this.is_enroll = is_enroll;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

//            public int getIs_collect() {
//                return is_collect;
//            }
//
//            public void setIs_collect(int is_collect) {
//                this.is_collect = is_collect;
//            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getMarket_price() {
                return market_price;
            }

            public void setMarket_price(int market_price) {
                this.market_price = market_price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getImg_text() {
                return img_text;
            }

            public void setImg_text(String img_text) {
                this.img_text = img_text;
            }

            public String getSpecific_address() {
                return specific_address;
            }

            public void setSpecific_address(String specific_address) {
                this.specific_address = specific_address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getEnroll_num() {
                return enroll_num;
            }

            public void setEnroll_num(int enroll_num) {
                this.enroll_num = enroll_num;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }
        }
    }
}
