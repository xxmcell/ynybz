package com.honganjk.ynybzbizfood.mode.javabean.common;

import android.app.Activity;

import com.honganjk.ynybzbizfood.mode.javabean.base.BannerAdData;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.other.activity.AgreementActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.ChongZhiActivity;

/**
 * 说明:广告位实体类
 * 360621904@qq.com 杨阳 2017/3/1  10:21
 * <p>
 * "img": "https://hajk.image.alimmdn.com/bz/hongan.png",//图片
 * "url": "https://www.honganjk.com/",	//地址url
 * "type": 1，	//活动页类型，1-充值优惠； 2-跳转官网；3-跳转店铺
 * "bid": 8997,//店铺id，type=3时使用
 * "sort": 1		//排序顺序，默认结果已由小到大排序
 */
public class BannerData extends BannerAdData {
    private String img;
    private int bid;
    private int sort;


    public BannerData(String img,int type) {
        this.img = img;
        this.type = type;
    }

    @Override
    public String getImg_url() {
        return img_url = img;
    }

    @Override
    public int getId() {
        return id = bid;
    }

    public int getSort() {
        return sort;
    }

    /**
     * 根据类型跳转到要指定的页面
     * "type": 1，	//活动页类型，1-充值优惠； 2-跳转官网；3-跳转店铺
     */
    public void transformPage(Activity activity) {
        if (type == 1) {
            if (StringUtils.isBlank(url))
                ChongZhiActivity.startUi(activity);
            return;
        } else if (type == 2) {
            if (StringUtils.isBlank(url))
                return;
            AgreementActivity.startUI(activity, getUrl());
        } else if (type == 3) {
            CarteenDetailActivity.startUI(activity, getId());
        }
    }


}
