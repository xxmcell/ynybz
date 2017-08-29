package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


import android.os.Parcel;
import android.os.Parcelable;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

/**
 * 陪护-首页-推荐实体
 */
public class NurserCommendData implements Parcelable {

    /**
     * "id": 6,	//护理人员id
     * "img": 	"https://hajk.image.alimmdn.com/YNYBZ/tempUpload/file_83cbc6f1f26f437aa5ecaf2269d70302",	//头像
     * "name": "郑涛",		//名字
     * "type": 1,			//护工类型，1-全天工；2-钟点工
     * "price": 0,			//价格
     * "point": 0,			//评分
     * "age": 26,			//年龄
     * "years": "2年",		//经验
     * "birthplace": "天津",	//籍贯
     * "introduction": "哦哦",	//介绍
     * "sex": 1,			//性别，1-女；2-男
     * "collect": 0			//被收藏数
     */

    private int id;
    private String img;
    private String name;
    private double price;
    private double point;
    private int age;
    private String years;
    private String birthplace;
    private String introduction;
    private int sex;
    private int collect;
    private int type;
    private int month;


    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    /**
     * "type": 1,			//护工类型，1-全天工；2-钟点工
     *
     * @return
     */
    public String getNameDetails() {
        return type == 1 ? name + " 【护工】" : name + " 【康复师】";
    }

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return "¥" + price;
    }

    public double getPoint() {
        return point;
    }

    public int getAge() {
        return age;
    }

    public String getDetails() {
        return StringUtils.dataFilter(age + " | " + point + " | " + years + "经验 | \n" + introduction);
    }

    public String getYears() {
        return years;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getSex() {
        return sex;
    }

    public int getCollect() {
        return collect;
    }

    public int getType() {
        return type;
    }

    public int getMonth() {
        return month;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.img);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeDouble(this.point);
        dest.writeInt(this.age);
        dest.writeString(this.years);
        dest.writeString(this.birthplace);
        dest.writeString(this.introduction);
        dest.writeInt(this.sex);
        dest.writeInt(this.collect);
        dest.writeInt(this.type);
        dest.writeInt(this.month);
    }

    public NurserCommendData() {
    }

    protected NurserCommendData(Parcel in) {
        this.id = in.readInt();
        this.img = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.point = in.readDouble();
        this.age = in.readInt();
        this.years = in.readString();
        this.birthplace = in.readString();
        this.introduction = in.readString();
        this.sex = in.readInt();
        this.collect = in.readInt();
        this.type = in.readInt();
        this.month = in.readInt();
    }

    public static final Creator<NurserCommendData> CREATOR = new Creator<NurserCommendData>() {
        @Override
        public NurserCommendData createFromParcel(Parcel source) {
            return new NurserCommendData(source);
        }

        @Override
        public NurserCommendData[] newArray(int size) {
            return new NurserCommendData[size];
        }
    };
}
