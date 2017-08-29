package com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/14.
 */

public class PromotionInfoBean implements Parcelable {

    /**
     * id : 6
     * descs : 1
     * reality : 600
     * extra : 60
     * create_time : 1470214641000
     * update_time : 1470214641000
     */

    private int id;
    private String descs;
    private int reality;
    private int extra;
    private long create_time;
    private long update_time;
    private boolean isSelect=false;

    protected PromotionInfoBean(Parcel in) {
        id = in.readInt();
        descs = in.readString();
        reality = in.readInt();
        extra = in.readInt();
        create_time = in.readLong();
        update_time = in.readLong();
    }

    public static final Creator<PromotionInfoBean> CREATOR = new Creator<PromotionInfoBean>() {
        @Override
        public PromotionInfoBean createFromParcel(Parcel in) {
            return new PromotionInfoBean(in);
        }

        @Override
        public PromotionInfoBean[] newArray(int size) {
            return new PromotionInfoBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public int getReality() {
        return reality;
    }

    public void setReality(int reality) {
        this.reality = reality;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(descs);
        dest.writeInt(reality);
        dest.writeInt(extra);
        dest.writeLong(create_time);
        dest.writeLong(update_time);
    }
}
