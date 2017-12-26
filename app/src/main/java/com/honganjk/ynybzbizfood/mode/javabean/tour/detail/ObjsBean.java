package com.honganjk.ynybzbizfood.mode.javabean.tour.detail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-11-27.
 */

public class ObjsBean implements Serializable{
    /**
     * img :
     * price : 0
     * days : ["1502770393000","1502770395000"]
     * id : 5
     * sales : 0
     * title : Schiff维骨力氨基3倍强化片170粒/瓶
     * tags : 避暑圣地-无购物-历史古迹
     */

    public String img;
    public String price;
    public int id;
    public int sales;
    public String title;
    public String tags;
    public List<String> days;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }
}
