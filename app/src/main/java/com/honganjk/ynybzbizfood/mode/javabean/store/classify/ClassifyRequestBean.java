package com.honganjk.ynybzbizfood.mode.javabean.store.classify;


/**
 * 说明:分类的请求参数
 * 作者： 杨阳; 创建于：  2017-07-07  11:08
 * <p>
 * type	可选,int	种类，由接口3动态获取
 * brand	可选,int	品牌，由接口4动态获取
 * sort	可选,int	排序方案，1-价格升序；2-价格降序；3-销量降序（首页推荐用此）
 */
public class ClassifyRequestBean {
    private int type;
    private int brand;
    private int sort;
    private boolean isFirstRequest = true;

    public ClassifyRequestBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isFirstRequest() {
        return isFirstRequest;
    }

    public void setFirstRequest(boolean firstRequest) {
        isFirstRequest = firstRequest;
    }
}
