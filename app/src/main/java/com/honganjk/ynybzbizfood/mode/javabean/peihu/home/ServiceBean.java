package com.honganjk.ynybzbizfood.mode.javabean.peihu.home;


/**
 * 服务内容
 * <p>
 * "id": 1,
 * "img": "https://hajk.image.alimmdn.com/bz/item.png",//图片
 * "content": "护理冠心病老人",	//服务项目介绍
 * "title": "冠心病老人全天护理",	//服务项目标题
 * "price": 100.12,			//服务价格，单位为元，精确到分
 * "type": 1,		//服务类型，1-全天；2-半天
 * "createTime": 1474440600000,
 * "updateTime": 1474440596000
 */
public class ServiceBean {
    private int id;
    private String img;
    private String content;
    private double price;
    private int type;
    private long createTime;
    private long updateTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }
    public String getPriceStr() {
        return String.valueOf(price);
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
