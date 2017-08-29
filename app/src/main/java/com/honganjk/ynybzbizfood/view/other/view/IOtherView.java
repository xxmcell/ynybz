package com.honganjk.ynybzbizfood.view.other.view;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;

import java.util.List;

/**
 * Created by yang on 2016/10/22.
 */

public interface IOtherView {
    /**
     * 作者　　: 杨阳
     * 创建时间: 2016/10/22 16:05
     * 邮箱　　：360621904@qq.com
     * <p>
     * 功能介绍：协议
     */
    interface IAgreement extends BaseView {
        void getHttpData(String url);
    }

    /**
     * 作者　　: 杨阳
     * 创建时间: 2016/10/26 16:34
     * 邮箱　　：360621904@qq.com
     * <p>
     * 功能介绍：广告
     */
    interface IAdverdisement extends BaseSwipeView {
        void getAdvertisement(List<BannerData> data);
    }


}
