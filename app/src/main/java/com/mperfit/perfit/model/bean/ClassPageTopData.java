package com.mperfit.perfit.model.bean;

import java.util.List;

/**
 * Created by zhangbing on 2016/11/21.
 */

public class ClassPageTopData {


    /**
     * message : 成功！
     * data : {"sort_list":[{"id":1,"name":"人气最靓"}],"category_list":[{"second_list":[{"name":"滑雪","category_id":2},{"name":"spacecycle","category_id":34}],"name":"动感单车","category_id":1}],"area_list":[{"area_id":0,"name":"附近"}]}
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
        private List<SortListBean> sort_list;
        private List<CategoryListBean> category_list;
        private List<AreaListBean> area_list;

        public List<SortListBean> getSort_list() {
            return sort_list;
        }

        public void setSort_list(List<SortListBean> sort_list) {
            this.sort_list = sort_list;
        }

        public List<CategoryListBean> getCategory_list() {
            return category_list;
        }

        public void setCategory_list(List<CategoryListBean> category_list) {
            this.category_list = category_list;
        }

        public List<AreaListBean> getArea_list() {
            return area_list;
        }

        public void setArea_list(List<AreaListBean> area_list) {
            this.area_list = area_list;
        }

        public static class SortListBean {
            /**
             * id : 1
             * name : 人气最靓
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CategoryListBean {
            /**
             * second_list : [{"name":"滑雪","category_id":2},{"name":"spacecycle","category_id":34}]
             * name : 动感单车
             * category_id : 1
             */

            private String name;
            private int category_id;
            private List<SecondListBean> second_list;
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public List<SecondListBean> getSecond_list() {
                return second_list;
            }

            public void setSecond_list(List<SecondListBean> second_list) {
                this.second_list = second_list;
            }

            public static class SecondListBean {
                /**
                 * name : 滑雪
                 * category_id : 2
                 */

                private String name;
                private int category_id;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(int category_id) {
                    this.category_id = category_id;
                }
            }
        }

        public static class AreaListBean {
            /**
             * area_id : 0
             * name : 附近
             */

            private boolean isSelected;
            public boolean isSelected() {
                return isSelected;
            }
            public void setSelected(boolean selected) {
                isSelected = selected;
            }



            private int area_id;
            private String name;

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
