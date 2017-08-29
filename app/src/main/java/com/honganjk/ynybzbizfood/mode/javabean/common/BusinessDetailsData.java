package com.honganjk.ynybzbizfood.mode.javabean.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class BusinessDetailsData implements Parcelable {


    /**
     * id : 8997
     * name : VIP包月定制套餐
     * area : 杭州市
     * city : 杭州市
     * address : 中山花园-风荷苑
     * addrEnter :
     * addrOpt : 中山花园-风荷苑
     * longitude : 120.17417
     * latitude : 30.281819
     * extent : 2
     * contact : 15268551332
     * hours : 10:00--19:00
     * lowest : 10
     * descs : 为了能够更好的服务于广大社区老年人，我们特此开办了一家VIP套餐食堂。有一荤二素一汤一饭和二荤二素一汤一饭两种包月套餐可供选择，三日不重样。每种套餐均由我们专业营养师上门按您的要求和身体状况量身定制。所选食材除了有机、新鲜、无农药残留以外，都含有人体所必需的多种营养素蛋白质、脂肪、糖、无机盐、矿物质、维生素、水和纤维素7类、还包含许多必须营养素，适合各种老年人。让您吃得放心、吃得舒心。
     * fare : 0
     * license_img_url : https://hajk.image.alimmdn.com/RefectoryBiz/Mac_020000000000_Registor/1482721487000?77CF1460D4694C298157774F8718DF82-14827214608427673-3-nht
     * permit_img_url : https://hajk.image.alimmdn.com/RefectoryBiz/Mac_020000000000_Registor/1482721492000?77CF1460D4694C298157774F8718DF82-14827214608427673-4-bGk
     * owner : 胡稼婧
     * mobile : 15268551332
     * card : 6212261202008935422
     * bank : 杭州城站支行本级业务部
     * positive_img_url : https://hajk.image.alimmdn.com/RefectoryBiz/Mac_020000000000_Registor/1482721537000?77CF1460D4694C298157774F8718DF82-14827214608427673-5-mOo
     * account : 15268551332
     * password : e10adc3949ba59abbe56e057f20f883e
     * remark : 为了保证让大家能够吃到健康新鲜的饭菜，我们采用新型保温技术和国内先进的EPP保温箱，可以显著延长保鲜时间，保证食物的温度和质量。
     * obverse_img_url : https://hajk.image.alimmdn.com/RefectoryBiz/Mac_020000000000_Registor/1482721546000?77CF1460D4694C298157774F8718DF82-14827214608427673-7-3ME
     * bulletin : 各位用户请注意：所有套餐在下单后我们会派出专业营养师上门做评估，制定最科学的饮食配方后和客户一起确定送餐时间。
     * img : https://hajk.image.alimmdn.com/bz/desc.jpg?t=1483085287555
     * head : https://hajk.image.alimmdn.com/bz/desc.jpg?t=1483085287555
     * create_time : 1451099186000
     * update_time : 1488779614000
     * type : 1
     * ctype : 1
     * kind : 1
     * keep : false
     * score : 4.79
     * rank : 2
     * sale : 19
     * favors : [{"id":105,"bid":8997,"title":"店铺首单立减5.0元","amount":5,"type":1,"createTime":1488875797000,"bname":"VIP包月定制套餐"}]
     */

    private int id;
    private String name;
    private String area;
    private String city;
    private String address;
    private String addrEnter;
    private String addrOpt;
    private double longitude;
    private double latitude;
    private String extent;
    private String contact;
    private String hours;
    private int lowest;
    private String descs;
    private int fare;
    private String license_img_url;
    private String permit_img_url;
    private String owner;
    private String mobile;
    private String card;
    private String bank;
    private String positive_img_url;
    private String account;
    private String password;
    private String remark;
    private String obverse_img_url;
    private String bulletin;
    private String img;
    private String head;
    private long create_time;
    private long update_time;
    private int type;
    private int ctype;
    private int kind;
    private boolean keep;
    private double score;
    private int rank;
    private int sale;
    private List<FavorsBean> favors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddrEnter() {
        return addrEnter;
    }

    public void setAddrEnter(String addrEnter) {
        this.addrEnter = addrEnter;
    }

    public String getAddrOpt() {
        return addrOpt;
    }

    public void setAddrOpt(String addrOpt) {
        this.addrOpt = addrOpt;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getLowest() {
        return lowest;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getLicense_img_url() {
        return license_img_url;
    }

    public void setLicense_img_url(String license_img_url) {
        this.license_img_url = license_img_url;
    }

    public String getPermit_img_url() {
        return permit_img_url;
    }

    public void setPermit_img_url(String permit_img_url) {
        this.permit_img_url = permit_img_url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPositive_img_url() {
        return positive_img_url;
    }

    public void setPositive_img_url(String positive_img_url) {
        this.positive_img_url = positive_img_url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getObverse_img_url() {
        return obverse_img_url;
    }

    public void setObverse_img_url(String obverse_img_url) {
        this.obverse_img_url = obverse_img_url;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
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

    public List<FavorsBean> getFavors() {
        return favors;
    }

    public void setFavors(List<FavorsBean> favors) {
        this.favors = favors;
    }

    public static class FavorsBean implements Parcelable {
        /**
         * id : 105
         * bid : 8997
         * title : 店铺首单立减5.0元
         * amount : 5.0
         * type : 1
         * createTime : 1488875797000
         * bname : VIP包月定制套餐
         */

        private int id;
        private int bid;
        private String title;
        private double amount;
        private int type;
        private long createTime;
        private String bname;

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

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
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

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
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
            dest.writeDouble(this.amount);
            dest.writeInt(this.type);
            dest.writeLong(this.createTime);
            dest.writeString(this.bname);
        }

        public FavorsBean() {
        }

        protected FavorsBean(Parcel in) {
            this.id = in.readInt();
            this.bid = in.readInt();
            this.title = in.readString();
            this.amount = in.readDouble();
            this.type = in.readInt();
            this.createTime = in.readLong();
            this.bname = in.readString();
        }

        public static final Creator<FavorsBean> CREATOR = new Creator<FavorsBean>() {
            @Override
            public FavorsBean createFromParcel(Parcel source) {
                return new FavorsBean(source);
            }

            @Override
            public FavorsBean[] newArray(int size) {
                return new FavorsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.area);
        dest.writeString(this.city);
        dest.writeString(this.address);
        dest.writeString(this.addrEnter);
        dest.writeString(this.addrOpt);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeString(this.extent);
        dest.writeString(this.contact);
        dest.writeString(this.hours);
        dest.writeInt(this.lowest);
        dest.writeString(this.descs);
        dest.writeInt(this.fare);
        dest.writeString(this.license_img_url);
        dest.writeString(this.permit_img_url);
        dest.writeString(this.owner);
        dest.writeString(this.mobile);
        dest.writeString(this.card);
        dest.writeString(this.bank);
        dest.writeString(this.positive_img_url);
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeString(this.remark);
        dest.writeString(this.obverse_img_url);
        dest.writeString(this.bulletin);
        dest.writeString(this.img);
        dest.writeString(this.head);
        dest.writeLong(this.create_time);
        dest.writeLong(this.update_time);
        dest.writeInt(this.type);
        dest.writeInt(this.ctype);
        dest.writeInt(this.kind);
        dest.writeByte(this.keep ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.score);
        dest.writeInt(this.rank);
        dest.writeInt(this.sale);
        dest.writeList(this.favors);
    }

    public BusinessDetailsData() {
    }

    protected BusinessDetailsData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.area = in.readString();
        this.city = in.readString();
        this.address = in.readString();
        this.addrEnter = in.readString();
        this.addrOpt = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.extent = in.readString();
        this.contact = in.readString();
        this.hours = in.readString();
        this.lowest = in.readInt();
        this.descs = in.readString();
        this.fare = in.readInt();
        this.license_img_url = in.readString();
        this.permit_img_url = in.readString();
        this.owner = in.readString();
        this.mobile = in.readString();
        this.card = in.readString();
        this.bank = in.readString();
        this.positive_img_url = in.readString();
        this.account = in.readString();
        this.password = in.readString();
        this.remark = in.readString();
        this.obverse_img_url = in.readString();
        this.bulletin = in.readString();
        this.img = in.readString();
        this.head = in.readString();
        this.create_time = in.readLong();
        this.update_time = in.readLong();
        this.type = in.readInt();
        this.ctype = in.readInt();
        this.kind = in.readInt();
        this.keep = in.readByte() != 0;
        this.score = in.readDouble();
        this.rank = in.readInt();
        this.sale = in.readInt();
        this.favors = new ArrayList<FavorsBean>();
        in.readList(this.favors, FavorsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<BusinessDetailsData> CREATOR = new Parcelable.Creator<BusinessDetailsData>() {
        @Override
        public BusinessDetailsData createFromParcel(Parcel source) {
            return new BusinessDetailsData(source);
        }

        @Override
        public BusinessDetailsData[] newArray(int size) {
            return new BusinessDetailsData[size];
        }
    };
}
