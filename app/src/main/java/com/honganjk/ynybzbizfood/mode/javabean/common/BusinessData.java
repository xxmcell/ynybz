package com.honganjk.ynybzbizfood.mode.javabean.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BusinessData implements Parcelable {


    /**
     * count : null
     * distance : 1.2573234897E7
     * price : 1299.0
     * name : 动脉硬化包月套餐一
     * img : https://hajk.image.alimmdn.com/dev/1484184158108
     * did : 638
     * bname : VIP包月定制套餐
     * bid : 8997
     * bimg : https://hajk.image.alimmdn.com/bz/desc.jpg?t=1483085287555
     * bulletin : 各位用户请注意：所有套餐在下单后我们会派出专业营养师上门做评估，制定最科学的饮食配方后和客户一起确定送餐时间。
     * score : 4.77
     * rank : 2
     * sale : 2119
     * latitude : 30.281819
     * longitude : 120.17417
     * lowest : 10
     * fare : 10
     * extent : 2公里
     * hours : 10:00--19:00
     * type : 1
     * favors : [{"id":87,"bid":8997,"title":"店铺首单立减1.0元","amount":1,"type":1,"createTime":1488435675000},{"id":96,"bid":8997,"title":"满20.0减19.0;","amount":null,"type":2,"createTime":1488445181000}]
     */

    private int distance;
    private String img;
    private String bname;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id = -1;

    public int getId() {
        return id==-1?bid:id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int bid = -1;
    private String bimg;
    private String bulletin;
    private double score;
    private int rank;
    private int sale;
    private int lowest;
    private int fare;
    private List<Favors> favors;

    public List<Favors> getFavors() {
        return favors;
    }

    public void setFavors(List<Favors> favors) {
        this.favors = favors;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getImg() {
        return StringUtils.dataFilter(img);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBname() {
        return StringUtils.dataFilter(TextUtils.isEmpty(bname)?name:bname);
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getBid() {
        return bid==-1?id:bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBimg() {
        return StringUtils.dataFilter(TextUtils.isEmpty(bimg)?img:bimg);
    }

    public void setBimg(String bimg) {
        this.bimg = bimg;
    }

    public String getBulletin() {
        return StringUtils.dataFilter(bulletin);
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getLowest() {
        return lowest;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "BusinessData{" +
                "distance=" + distance +
                ", img='" + img + '\'' +
                ", bname='" + bname + '\'' +
                ", bid=" + bid +
                ", bimg='" + bimg + '\'' +
                ", bulletin='" + bulletin + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                ", sale=" + sale +
                ", lowest=" + lowest +
                ", fare=" + fare +
                '}';
    }


    public BusinessData() {
    }

     public static class Favors implements Parcelable{

        /**
         * id : 87
         * bid : 8997
         * title : 店铺首单立减1.0元
         * amount : 1
         * type : 1                 //1 :首单立减  2：满减优惠
         * createTime : 1488435675000
         */

        private int id;
        private int bid;
        private String title;
        private int amount;
        private int type;
        private long createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel dest, int flags) {
             dest.writeInt(this.id);
             dest.writeInt(this.bid);
             dest.writeString(this.title);
             dest.writeInt(this.amount);
             dest.writeInt(this.type);
             dest.writeLong(this.createTime);
         }

         public Favors() {
         }

         protected Favors(Parcel in) {
             this.id = in.readInt();
             this.bid = in.readInt();
             this.title = in.readString();
             this.amount = in.readInt();
             this.type = in.readInt();
             this.createTime = in.readLong();
         }

         public static final Creator<Favors> CREATOR = new Creator<Favors>() {
             @Override
             public Favors createFromParcel(Parcel source) {
                 return new Favors(source);
             }

             @Override
             public Favors[] newArray(int size) {
                 return new Favors[size];
             }
         };
     }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.distance);
        dest.writeString(this.img);
        dest.writeString(this.bname);
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.bid);
        dest.writeString(this.bimg);
        dest.writeString(this.bulletin);
        dest.writeDouble(this.score);
        dest.writeInt(this.rank);
        dest.writeInt(this.sale);
        dest.writeInt(this.lowest);
        dest.writeInt(this.fare);
        dest.writeList(this.favors);
    }

    protected BusinessData(Parcel in) {
        this.distance = in.readInt();
        this.img = in.readString();
        this.bname = in.readString();
        this.name = in.readString();
        this.id = in.readInt();
        this.bid = in.readInt();
        this.bimg = in.readString();
        this.bulletin = in.readString();
        this.score = in.readDouble();
        this.rank = in.readInt();
        this.sale = in.readInt();
        this.lowest = in.readInt();
        this.fare = in.readInt();
        this.favors = new ArrayList<Favors>();
        in.readList(this.favors, Favors.class.getClassLoader());
    }

    public static final Creator<BusinessData> CREATOR = new Creator<BusinessData>() {
        @Override
        public BusinessData createFromParcel(Parcel source) {
            return new BusinessData(source);
        }

        @Override
        public BusinessData[] newArray(int size) {
            return new BusinessData[size];
        }
    };
}
