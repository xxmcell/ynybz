package com.honganjk.ynybzbizfood.mode.javabean.store.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

/**
 * 说明:产品的规格
 * 作者： 杨阳; 创建于：  2017-07-10  13:44
 * <p>
 * "img": "https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099",//图片
 * "label": "500g",	//显示名称
 * "price": 98,		//价格
 * "type": 1		//类型
 * "stock": 10,		//库存
 */
public class ProductDetailsTypeData implements Parcelable {
    private String img;
    private String label;
    private String stock;
    private String title;
    private double price;
    private int type;
    private int number;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return StringUtils.dataFilter("¥" + price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getNumberStr() {
        return "X" + number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStock() {
        return "库存：" + StringUtils.dataFilter(stock);
    }

    public void setStock(String stock) {
        this.stock = stock;
    }


    public ProductDetailsTypeData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.label);
        dest.writeString(this.stock);
        dest.writeString(this.title);
        dest.writeDouble(this.price);
        dest.writeInt(this.type);
        dest.writeInt(this.number);
        dest.writeInt(this.id);
    }

    protected ProductDetailsTypeData(Parcel in) {
        this.img = in.readString();
        this.label = in.readString();
        this.stock = in.readString();
        this.title = in.readString();
        this.price = in.readDouble();
        this.type = in.readInt();
        this.number = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<ProductDetailsTypeData> CREATOR = new Creator<ProductDetailsTypeData>() {
        @Override
        public ProductDetailsTypeData createFromParcel(Parcel source) {
            return new ProductDetailsTypeData(source);
        }

        @Override
        public ProductDetailsTypeData[] newArray(int size) {
            return new ProductDetailsTypeData[size];
        }
    };
}
