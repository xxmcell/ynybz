package com.honganjk.ynybzbizfood.mode.javabean.home;


import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.io.Serializable;

/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/14 14:34
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：获取首页九宫格,图与名称
 */

public class HomeData implements Serializable{
    private String icon_url;
    private String title;

    public HomeData(String title) {
        this.title = title;
    }

    public String getIcon_url() {
        return StringUtils.dataFilter(icon_url);
    }


    public String getTitle() {
        return StringUtils.dataFilter(title);
    }

}
