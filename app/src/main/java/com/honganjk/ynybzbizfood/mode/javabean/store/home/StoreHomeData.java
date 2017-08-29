package com.honganjk.ynybzbizfood.mode.javabean.store.home;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017-07-06.
 */

public class StoreHomeData {

    /**
     * total : 2
     * objs : [{"feature":"GNC美国官网供货","id":1,"img":"https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099","money":98,"price":218,"title":"Swisse 钙&维他命D片 柠檬酸钙 150片/罐"},{"feature":"GNC美国官网供货","id":2,"img":"https://hajk.image.alimmdn.com/bz/b1.jpg?t=1499068192916","money":165,"price":359,"title":"Schiff movefree维骨力氨基酸葡萄糖3倍强化片170粒/瓶 "}]
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
         * feature : GNC美国官网供货
         * id : 1
         * img : https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099
         * money : 98.0
         * price : 218.0
         * title : Swisse 钙&维他命D片 柠檬酸钙 150片/罐
         */

        private String feature;
        private int id;
        private String img;
        private double money;
        private double price;
        private String title;

        public String getFeature() {
            return StringUtils.dataFilter(feature);
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return StringUtils.dataFilter(img);
        }

        public void setImg(String img) {
            this.img = img;
        }

        public double getMoney() {
            return money;
        }

        public String getMoneyStr() {
            return StringUtils.dataFilter("¥" + money);
        }

        public void setMoney(double money) {
            this.money = money;
        }


        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTitle() {
            return StringUtils.dataFilter(title);
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
