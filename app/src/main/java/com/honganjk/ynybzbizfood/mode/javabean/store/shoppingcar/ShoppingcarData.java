package com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar;

import android.widget.TextView;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * {
 * "total": 2,
 * "objs": [
 * {
 * "feature": "GNC",	//供应商
 * "icon": "https://hajk.image.alidn.com/bz/b1.jpg",//供应商图标
 * "list": [
 * {
 * "bid": 2,		//商品id
 * "createTime": 1500283428000,
 * "id": 273,
 * "num": 3,	//数量
 * "type": 1,		//商品规格code
 * "uid": 46,
 * "updateTime": 1500283428000
 * },
 * {
 * "bid": 1,
 * "createTime": 1500283169000,
 * "id": 272,
 * "num": 3,
 * "type": 1,
 * "uid": 46,
 * "updateTime": 1500283212000
 */
public class ShoppingcarData {
//    private int total;
//    private List<ObjsBean> objs;
//
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//
//    public List<ObjsBean> getObjs() {
//        return objs;
//    }
//
//    public void setObjs(List<ObjsBean> objs) {
//        this.objs = objs;
//    }
//
//    public static class ObjsBean {
//        /**
//         * feature : GNC
//         * icon : https://hajk.image.alimmdn.com/bz/b1.jpg?t=1499068192916
//         * list : [{"bid":2,"createTime":1500368281000,"id":274,"num":4,"type":2,"uid":35,"updateTime":1500371884000},{"bid":2,"createTime":1500371594000,"id":276,"num":2,"type":1,"uid":35,"updateTime":1500371863000},{"bid":1,"createTime":1500369035000,"id":275,"num":2,"type":1,"uid":35,"updateTime":1500369071000}]
//         */
//
//        private String feature;
//        private String icon;
//        private List<ListBean> list;
//
//        public String getFeature() {
//            return feature;
//        }
//
//        public void setFeature(String feature) {
//            this.feature = feature;
//        }
//
//        public String getIcon() {
//            return icon;
//        }
//
//        public void setIcon(String icon) {
//            this.icon = icon;
//        }
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public class ListBean {
//            /**
//             * bid : 2
//             * createTime : 1500368281000
//             * id : 274
//             * num : 4
//             * type : 2
//             * uid : 35
//             * updateTime : 1500371884000
//             */
//
//            private int bid;
//            private long createTime;
//            private int id;
//            private int num;
//            private int type;
//            private int uid;
//            private long updateTime;
//            private boolean isSelect;
//            private String img;
//            private String lable;
//            private String title;
//            private double money;
//            private double price;
//
//            public boolean getIsSelect() {
//                return isSelect;
//            }
//
//            public void setIsSelect(boolean isSelect, @NonNull SelectListenerView mSelectListenerView) {
//                this.isSelect = isSelect;
//                if (mSelectListenerView != null) {
//                    mSelectListenerView.isSelect(isSelect);
//                }
//            }
//
//            public void setIsSelect(boolean isSelect) {
//                this.isSelect = isSelect;
//
//            }
//
//            public void setNum(int num) {
//                this.num = num;
//            }
//            public boolean isSelect() {
//                return isSelect;
//            }
//
//            public void setSelect(boolean select) {
//                isSelect = select;
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
//            public String getLable() {
//                return lable;
//            }
//
//            public void setLable(String lable) {
//                this.lable = lable;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public double getMoney() {
//                return money;
//            }
//
//            public String getMoneyStr() {
//
//                return "¥" + money;
//            }
//
//            public void setMoney(double money) {
//                this.money = money;
//            }
//
//            public String getPriceStr(TextView view) {
//                view.setText("¥" + money);
//                StringUtils.convertToFlags(view);
//                return "¥" + price;
//            }
//
//            public void setPrice(double price) {
//                this.price = price;
//            }
//
//            public int getBid() {
//                return bid;
//            }
//
//            public void setBid(int bid) {
//                this.bid = bid;
//            }
//
//            public long getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(long createTime) {
//                this.createTime = createTime;
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
//            public int getNum() {
//                return num;
//            }
//
//
//
//            public int getType() {
//                return type;
//            }
//
//            public void setType(int type) {
//                this.type = type;
//            }
//
//            public int getUid() {
//                return uid;
//            }
//
//            public void setUid(int uid) {
//                this.uid = uid;
//            }
//
//            public long getUpdateTime() {
//                return updateTime;
//            }
//
//            public void setUpdateTime(long updateTime) {
//                this.updateTime = updateTime;
//            }
//        }
//    }
private int total;
    private List<ObjsBean> objs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ObjsBean> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjsBean> objs) {
        this.objs = objs;
    }

    public static class ObjsBean {
        /**
         * bid : 42
         * feature : 3尔23
         * icon : https://hajk.image.alimmdn.com/dev/1500432479685
         * img : https://hajk.image.alimmdn.com/dev/1504086727075
         * label : 续航18km（红色）
         * money : 2700
         * num : 1
         * price : 3200
         * title : 英格威老年人代步车 四轮电动车锂电池老人观光电动电瓶车
         * type : 1
         */

        private int bid;
        private String feature;
        private String icon;
        private String img;
        private String label;
        private double money;
        private int num;
        private double price;
        private String title;
        private int type;

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getMoneyStr() {

            return "¥" + money;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getPrice() {
            return price;
        }

        public String getPriceStr(TextView view){
            view.setText("¥"+money);
            StringUtils.convertToFlags(view);
            return "¥" + price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }

}
