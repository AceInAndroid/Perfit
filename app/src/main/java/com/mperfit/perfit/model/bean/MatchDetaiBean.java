package com.mperfit.perfit.model.bean;

import com.mperfit.perfit.base.BaseBean;

/**
 * Created by zhangbing on 2017/3/23.
 */

public class MatchDetaiBean extends BaseBean {

    /**
     * message : 成功！
     * data : {"match":{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/1/w/750/h/540","match_id":840043772388573184,"now_enroll_num":3,"content":"2221","enroll_status":1,"is_enroll":"","time":"3.1-3.31","price":0,"market_price":0,"address":"798艺术区","img_text":"<img src=\"http://ocjp9x6x9.bkt.clouddn.com/activity/20170310121038947.jpg\" alt=\"\" />dddsd","specific_address":"北京798艺术区","name":"极速前进","enroll_num":500,"longitude":0,"latitude":0}}
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
         * match : {"img_url":"http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/1/w/750/h/540","match_id":840043772388573184,"now_enroll_num":3,"content":"2221","enroll_status":1,"is_enroll":"","time":"3.1-3.31","price":0,"market_price":0,"address":"798艺术区","img_text":"<img src=\"http://ocjp9x6x9.bkt.clouddn.com/activity/20170310121038947.jpg\" alt=\"\" />dddsd","specific_address":"北京798艺术区","name":"极速前进","enroll_num":500,"longitude":0,"latitude":0}
         */

        private MatchBean match;

        public MatchBean getMatch() {
            return match;
        }

        public void setMatch(MatchBean match) {
            this.match = match;
        }

        public static class MatchBean {
            /**
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/match/123.jpg?imageView2/1/w/750/h/540
             * match_id : 840043772388573184
             * now_enroll_num : 3
             * content : 2221
             * enroll_status : 1
             * is_enroll :
             * time : 3.1-3.31
             * price : 0.0
             * market_price : 0.0
             * address : 798艺术区
             * img_text : <img src="http://ocjp9x6x9.bkt.clouddn.com/activity/20170310121038947.jpg" alt="" />dddsd
             * specific_address : 北京798艺术区
             * name : 极速前进
             * enroll_num : 500
             * longitude : 0.0
             * latitude : 0.0
             */

            private String img_url;
            private long match_id;
            private int now_enroll_num;
            private String content;
            private int enroll_status;
            private String is_enroll;
            private String time;
            private double price;
            private double market_price;
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

            public long getMatch_id() {
                return match_id;
            }

            public void setMatch_id(long match_id) {
                this.match_id = match_id;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getMarket_price() {
                return market_price;
            }

            public void setMarket_price(double market_price) {
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
