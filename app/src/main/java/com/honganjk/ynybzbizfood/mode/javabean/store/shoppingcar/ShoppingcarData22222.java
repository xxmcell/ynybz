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
public class ShoppingcarData22222 {
    /**
     * total : 3
     * objs : [{"bid":42,"feature":"3尔23 ","icon":"https://hajk.image.alimmdn.com/dev/1500432479685","img":"https://hajk.image.alimmdn.com/dev/1504086727075","label":"续航18km（红色）","money":2700,"num":1,"price":3200,"title":"英格威老年人代步车 四轮电动车锂电池老人观光电动电瓶车","type":1},{"bid":43,"feature":"养你一辈子","icon":"https://hajk.image.alimmdn.com/dev/1505271316750","img":"https://hajk.image.alimmdn.com/dev/1504087344238","label":"JH-R3米白色充电揉捏","money":170,"num":1,"price":200,"title":"充电揉捏按摩披肩多功能颈椎按摩器 ","type":1},{"bid":40,"feature":"养你一辈子","icon":"https://hajk.image.alimmdn.com/dev/1505271316750","img":"https://hajk.image.alimmdn.com/dev/1504083847438","label":"500g","money":35,"num":1,"price":40,"title":"新疆和田大红枣夹核桃仁散装500g ","type":1}]
     */

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
