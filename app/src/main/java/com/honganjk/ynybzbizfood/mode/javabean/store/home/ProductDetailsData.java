package com.honganjk.ynybzbizfood.mode.javabean.store.home;

import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.List;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-07-07  15:36
 * <p>
 * : {
 * "banners": [//banner图片
 * "https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099",
 * "https://hajk.image.alimmdn.com/bz/banner2.jpg?t=1499067308439",
 * "https://hajk.image.alimmdn.com/bz/banner3.jpg?t=1499067398096"
 * ],
 * "comments": [//评论
 * {
 * "content": "测试6",//评论内容
 * "img": "https://hajk.image.alimmdn.com/bz/head.jpg",//用户头像
 * "name": "qaz",	//评论人昵称
 * "score": 5,		//评分
 * "time": 1466504264000	//评论时间戳
 * },
 * <p>
 * ],
 * "details": [	//详情图片
 * "https://hajk.image.alimmdn.com/bz/detail1.jpg?t=1499067507610",
 * "https://hajk.image.alimmdn.com/bz/detail2.jpg?t=1499067635697",
 * "https://hajk.image.alimmdn.com/bz/detail3.jpg?t=1499067739519"
 * ],
 * "feature": "GNC美国官网供货",	//亮点
 * "id": 1,					//数据id
 * "money": 98,				//真实价格，double
 * "origin": "浙江杭州",			//发货地
 * "price": 218,				//参考价格，double
 * "sales": 10,				//销量
 * "stock": 10,				//库存
 * "title": "Swisse 钙&维他命D片 柠檬酸钙 150片/罐"	//标题
 * "keep": true, //三种值，null无用户登录收藏未知，true已收藏，false未收藏
 * }
 * }
 */
public class ProductDetailsData {
    private String feature;
    private int id;
    private double money;
    private String origin;
    private double price;
    private String sales;
    private String stock;
    private String title;
    private String keep;//三种值，null无用户登录收藏未知，true已收藏，false未收藏
    private List<String> banners;//banner图片
    private List<CommentsBean> comments;//评论
    private List<DetailsBean> details; //详情的图片

    public String getFeature() {
        return StringUtils.dataFilter(feature);
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getKeep() {
        return keep;
    }

    public boolean getKeepBoolean() {
        if (StringUtils.isBlank(keep)) {
            return false;
        } else if (keep.equals("true")) {
            return true;
        } else if (keep.equals("false")) {
            return false;
        }
        return false;
    }

    public void setKeep(Boolean keep) {
        this.keep = String.valueOf(keep);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public String getMoneyStr() {
        return "¥" + money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrigin() {
        return StringUtils.dataFilter(origin);
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceStr() {
        return "价格：¥" + price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSales() {
        return "销量：" + StringUtils.dataFilter(sales);
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getStock() {
        return "库存：" + StringUtils.dataFilter(stock);
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getTitle() {
        return StringUtils.dataFilter(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }


    public static class DetailsBean {


        /**
         * height : 590
         * img : https://hajk.image.alimmdn.com/bz/detail1.jpg?t=1499067507610
         * width : 712
         */

        private int height;
        private String img;
        private int width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    public static class CommentsBean {
        /**
         * content : 测试6
         * img : https://hajk.image.alimmdn.com/bz/head.jpg
         * name : qaz
         * score : 5
         * time : 1466504264000
         */

        private String content;
        private String img;
        private String name;
        private int score;
        private long time;

        public String getContent() {
            return StringUtils.dataFilter(content);
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return StringUtils.dataFilter(img);
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return StringUtils.dataFilter(name);
        }

        public String getTimeStr() {
            return DateUtils.getTime(time);
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
