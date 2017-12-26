package com.honganjk.ynybzbizfood.mode.javabean.tour.detail;

import java.io.Serializable;
import java.util.List;

public class TourDetailBean {

    private String msg;
    private String code;
    private DataBean data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }
    public static class DataBean {

        private List<String> banners;//banner图片
        private Comments comments;//评论
        private List<Details> details;
        private List<Formats> formats;//出行日期
        private String feature;//行程亮点
        private String brand;//产品品牌
        private boolean keep;//三种值，null无用户登录收藏未知，true已收藏，false未收藏
        private int id;//数据id
        private int price;//参考价格
        private int self;	//自由用餐次数
        private String outset;//出发地
        private String dest;//目的地
        private int team;//团体用餐次数
        private int shopping;//购物次数
        private int viewpoint;//景点数
        private int free;//自由活动次数
        private String tags;//标签
        private String title;//标题
        public void setBanners(List<String> banners) {
            this.banners = banners;
        }
        public List<String> getBanners() {
            return banners;
        }

        public void setComments(Comments comments) {
            this.comments = comments;
        }
        public Comments getComments() {
            return comments;
        }

        public void setDetails(List<Details> details) {
            this.details = details;
        }
        public List<Details> getDetails() {
            return details;
        }

        public void setFormats(List<Formats> formats) {
            this.formats = formats;
        }
        public List<Formats> getFormats() {
            return formats;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }
        public String getFeature() {
            return feature;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
        public String getBrand() {
            return brand;
        }

        public void setKeep(boolean keep) {
            this.keep = keep;
        }
        public boolean getKeep() {
            return keep;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setPrice(int price) {
            this.price = price;
        }
        public int getPrice() {
            return price;
        }

        public void setSelf(int self) {
            this.self = self;
        }
        public int getSelf() {
            return self;
        }

        public void setOutset(String outset) {
            this.outset = outset;
        }
        public String getOutset() {
            return outset;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }
        public String getDest() {
            return dest;
        }

        public void setTeam(int team) {
            this.team = team;
        }
        public int getTeam() {
            return team;
        }

        public void setShopping(int shopping) {
            this.shopping = shopping;
        }
        public int getShopping() {
            return shopping;
        }

        public void setViewpoint(int viewpoint) {
            this.viewpoint = viewpoint;
        }
        public int getViewpoint() {
            return viewpoint;
        }

        public void setFree(int free) {
            this.free = free;
        }
        public int getFree() {
            return free;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }
        public String getTags() {
            return tags;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
        public static class Comments implements Serializable{

            private int all;//评论总数
            private int normal;//中评数
            private int imgs;//有图评论数
            private int bad;//差评数
            private int good;//好评数
            public void setAll(int all) {
                this.all = all;
            }
            public int getAll() {
                return all;
            }

            public void setNormal(int normal) {
                this.normal = normal;
            }
            public int getNormal() {
                return normal;
            }

            public void setImgs(int imgs) {
                this.imgs = imgs;
            }
            public int getImgs() {
                return imgs;
            }

            public void setBad(int bad) {
                this.bad = bad;
            }
            public int getBad() {
                return bad;
            }

            public void setGood(int good) {
                this.good = good;
            }
            public int getGood() {
                return good;
            }
        }
        public static class Details {

            private int height;//图片高度
            private String img;//详情图片
            private int width;//图片宽度
            public void setHeight(int height) {
                this.height = height;
            }
            public int getHeight() {
                return height;
            }

            public void setImg(String img) {
                this.img = img;
            }
            public String getImg() {
                return img;
            }

            public void setWidth(int width) {
                this.width = width;
            }
            public int getWidth() {
                return width;
            }
        }
        public static class Formats implements Serializable{

            private int id;//数据id
            private int price;//价格
            private int hotelPrice;//酒店价格
            private int num;//行程天数
            private String time;//出发时间ms时间戳
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setPrice(int price) {
                this.price = price;
            }
            public int getPrice() {
                return price;
            }

            public void setHotelPrice(int hotelPrice) {
                this.hotelPrice = hotelPrice;
            }
            public int getHotelPrice() {
                return hotelPrice;
            }

            public void setNum(int num) {
                this.num = num;
            }
            public int getNum() {
                return num;
            }

            public void setTime(String time) {
                this.time = time;
            }
            public String getTime() {
                return time;
            }
        }
    }
}
