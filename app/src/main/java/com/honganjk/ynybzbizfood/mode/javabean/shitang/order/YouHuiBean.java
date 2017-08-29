package com.honganjk.ynybzbizfood.mode.javabean.shitang.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/13.
 */

public class YouHuiBean implements Parcelable {

    /**
     * id : 2
     * total : 35
     * amount : 18
     * descs : 满35减18
     * type : 2
     */

    private int id;
    private int total;
    private int amount;
    private String descs;
    private int type;

    protected YouHuiBean(Parcel in) {
        id = in.readInt();
        total = in.readInt();
        amount = in.readInt();
        descs = in.readString();
        type = in.readInt();
    }

    public static final Creator<YouHuiBean> CREATOR = new Creator<YouHuiBean>() {
        @Override
        public YouHuiBean createFromParcel(Parcel in) {
            return new YouHuiBean(in);
        }

        @Override
        public YouHuiBean[] newArray(int size) {
            return new YouHuiBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(total);
        dest.writeInt(amount);
        dest.writeString(descs);
        dest.writeInt(type);
    }
}
