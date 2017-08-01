package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 16/10/24.
 */

public class ActivityBean {

    /**
     * message : 成功！
     * data : {"total_page":1,"list":[{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20170105095142767.jpg?imageView2/1/w/750/h/460","content":"adw ad ","time":"2016.11.11","activity_id":791942042316963840,"status":2,"address":"北京市昌平区老牛湾村","tag":"dawd ","name":"测试数据","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161130173706193.jpg?imageView2/1/w/750/h/460","content":"运动+美食给你带来双重享受 本活动由随","time":"2016.9.24","activity_id":778935736362598400,"status":1,"address":"林匹克森林公园","tag":"跑步","name":"首届\u201c奔跑吧，上班族\u201d活动\u2014\u2014麻小跑","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/1/w/750/h/460","content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","time":"2016.10.9","activity_id":785054266669662208,"status":2,"address":"北京朝阳区三里屯路11号院西座N4-40A","tag":"运动 社交","name":"还是花花","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161101192148047.jpg?imageView2/1/w/750/h/460","content":"测试","time":"2016.10.21","activity_id":789294585657425920,"status":1,"address":"测试","tag":"健身","name":"测试","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161026174011585.jpg?imageView2/1/w/750/h/460","content":"【万圣节活动剧透】不让鬼混就捣乱，18禁玩的就是心跳","time":"2016.10.30","activity_id":791212859060125696,"status":2,"address":"测试","tag":"活动","name":"万圣节活动party","random":2},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161018163213346.jpg?imageView2/1/w/750/h/460","content":"相识一个人 恋上一座城","time":"2016.10.23","activity_id":788296613599641600,"status":2,"address":"卓展购物中心","tag":"强势推荐","name":"城市坐标·北京","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923172706976.jpg?imageView2/1/w/750/h/460","content":"体验马致远笔下的古道西风瘦马的意境...","time":"2016.11.7","activity_id":779250729884844032,"status":2,"address":"北京朝阳","tag":"徒步","name":"【国庆】徒步京西古道，感受千年古风遗韵","random":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019150418385.jpg?imageView2/1/w/750/h/460","content":"以爬山交友的为目的的户外活动","time":"2016.10.22","activity_id":788636877384056832,"status":2,"address":"南邵地铁站","tag":"爬山 交友","name":"最美银杏林休闲摄影","random":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019153544384.jpg?imageView2/1/w/750/h/460","content":"约\u201c泡\u201d新姿势，弹，撞，吹，震，浪！","time":"2016.10.23","activity_id":788644789061943296,"status":2,"address":"朝阳公园","tag":"泡泡足球","name":" 向世界开\u201c泡\u201d","random":2},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019155857642.jpg?imageView2/1/w/750/h/460","content":"陪伴盲人跑步和体验无视力运动来点燃秋季的热情！","time":"2016.10.23","activity_id":788650632197177344,"status":2,"address":"奥林匹克森林公园","tag":"公益 跑步","name":"助盲跑-秋季赛","random":3}],"current_page":1}
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
         * list : [{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20170105095142767.jpg?imageView2/1/w/750/h/460","content":"adw ad ","time":"2016.11.11","activity_id":791942042316963840,"status":2,"address":"北京市昌平区老牛湾村","tag":"dawd ","name":"测试数据","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161130173706193.jpg?imageView2/1/w/750/h/460","content":"运动+美食给你带来双重享受 本活动由随","time":"2016.9.24","activity_id":778935736362598400,"status":1,"address":"林匹克森林公园","tag":"跑步","name":"首届\u201c奔跑吧，上班族\u201d活动\u2014\u2014麻小跑","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161123201220811.jpg?imageView2/1/w/750/h/460","content":"以音乐为灵魂，通过音乐引领，为每一天注入音乐的感动和","time":"2016.10.9","activity_id":785054266669662208,"status":2,"address":"北京朝阳区三里屯路11号院西座N4-40A","tag":"运动 社交","name":"还是花花","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161101192148047.jpg?imageView2/1/w/750/h/460","content":"测试","time":"2016.10.21","activity_id":789294585657425920,"status":1,"address":"测试","tag":"健身","name":"测试","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161026174011585.jpg?imageView2/1/w/750/h/460","content":"【万圣节活动剧透】不让鬼混就捣乱，18禁玩的就是心跳","time":"2016.10.30","activity_id":791212859060125696,"status":2,"address":"测试","tag":"活动","name":"万圣节活动party","random":2},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161018163213346.jpg?imageView2/1/w/750/h/460","content":"相识一个人 恋上一座城","time":"2016.10.23","activity_id":788296613599641600,"status":2,"address":"卓展购物中心","tag":"强势推荐","name":"城市坐标·北京","random":4},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20160923172706976.jpg?imageView2/1/w/750/h/460","content":"体验马致远笔下的古道西风瘦马的意境...","time":"2016.11.7","activity_id":779250729884844032,"status":2,"address":"北京朝阳","tag":"徒步","name":"【国庆】徒步京西古道，感受千年古风遗韵","random":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019150418385.jpg?imageView2/1/w/750/h/460","content":"以爬山交友的为目的的户外活动","time":"2016.10.22","activity_id":788636877384056832,"status":2,"address":"南邵地铁站","tag":"爬山 交友","name":"最美银杏林休闲摄影","random":1},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019153544384.jpg?imageView2/1/w/750/h/460","content":"约\u201c泡\u201d新姿势，弹，撞，吹，震，浪！","time":"2016.10.23","activity_id":788644789061943296,"status":2,"address":"朝阳公园","tag":"泡泡足球","name":" 向世界开\u201c泡\u201d","random":2},{"img_url":"http://ocjp9x6x9.bkt.clouddn.com/activity/20161019155857642.jpg?imageView2/1/w/750/h/460","content":"陪伴盲人跑步和体验无视力运动来点燃秋季的热情！","time":"2016.10.23","activity_id":788650632197177344,"status":2,"address":"奥林匹克森林公园","tag":"公益 跑步","name":"助盲跑-秋季赛","random":3}]
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
             * img_url : http://ocjp9x6x9.bkt.clouddn.com/activity/20170105095142767.jpg?imageView2/1/w/750/h/460
             * content : adw ad
             * time : 2016.11.11
             * activity_id : 791942042316963840
             * status : 2
             * address : 北京市昌平区老牛湾村
             * tag : dawd
             * name : 测试数据
             * random : 4
             */

            private String img_url;
            private String content;
            private String time;
            private long activity_id;
            private int status;
            private String address;
            private String tag;
            private String name;
            private int random;

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
        }
    }
}
