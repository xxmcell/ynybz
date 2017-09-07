package com.honganjk.ynybzbizfood.mode.javabean.store.home;

/**
 * Created by Administrator on 2017-09-01. 搜索结果
 */
public class storeBean {

    /**
     * msg : ok
     * code : A00000
     * data : {"total":2,"objs":[{"feature":"GNC ","icon":"https://hajk.image.alimmdn.com/bz/baner1.jpg","id":1,"img":"https://hajk.image.alimmdn.com/bz/baner1.jpg","money":98,"price":218,"title":"Swisse 钙&维他命D片 柠檬酸钙 150片/罐"},{"feature":"GNC","id":2,"img":"https://hajk.image.alimmdn.com/bz/b1.jpg","money":165,"price":359,"title":"Schiff movefree维骨力氨基酸葡萄糖3倍强化片170粒/瓶 "}]}
     */

    private String msg;
    private String code;
    private StoreHomeData data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StoreHomeData getData() {
        return data;
    }

    public void setData(StoreHomeData data) {
        this.data = data;
    }

//    public static class StoreHomeData {
//        /**
//         * total : 2
//         * objs : [{"feature":"GNC ",
//         * "icon":"https://hajk.image.alimmdn.com/bz/baner1.jpg",
//         * "id":1,
//         * "img":"https://hajk.image.alimmdn.com/bz/baner1.jpg",
//         * "money":98,
//         * "price":218,
//         * "title":"Swisse 钙&维他命D片 柠檬酸钙 150片/罐"},
//         *
//         *
//         * {"feature":"GNC",
//         * "id":2,
//         * "img":"https://hajk.image.alimmdn.com/bz/b1.jpg",
//         * "money":165,
//         * "price":359,
//         * "title":"Schiff movefree维骨力氨基酸葡萄糖3倍强化片170粒/瓶 "}]
//         */
//
//        private int total;
//        private List<ObjsBean> objs;
//
//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public List<ObjsBean> getObjs() {
//            return objs;
//        }
//
//        public void setObjs(List<ObjsBean> objs) {
//            this.objs = objs;
//        }
//
//        public static class ObjsBean {
//            /**
//             * feature : GNC
//             * icon : https://hajk.image.alimmdn.com/bz/baner1.jpg
//             * id : 1
//             * img : https://hajk.image.alimmdn.com/bz/baner1.jpg
//             * money : 98
//             * price : 218
//             * title : Swisse 钙&维他命D片 柠檬酸钙 150片/罐
//             */
//
//            private String feature;
//            private String icon;
//            private int id;
//            private String img;
//            private int money;
//            private int price;
//            private String title;
//
//            public String getFeature() {
//                return feature;
//            }
//
//            public void setFeature(String feature) {
//                this.feature = feature;
//            }
//
//            public String getIcon() {
//                return icon;
//            }
//
//            public void setIcon(String icon) {
//                this.icon = icon;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getImg() {
//                return img;
//            }
//
//            public void setImg(String img) {
//                this.img = img;
//            }
//
//            public int getMoney() {
//                return money;
//            }
//
//            public void setMoney(int money) {
//                this.money = money;
//            }
//
//            public int getPrice() {
//                return price;
//            }
//
//            public void setPrice(int price) {
//                this.price = price;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//        }
//    }
}
