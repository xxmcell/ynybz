package com.honganjk.ynybzbizfood.view.shitang.collect.interfaces;


import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;

import java.util.List;

/**
 * 说明:收藏的接口
 * 360621904@qq.com 杨阳 2017/3/6  10:10
 */
public interface CollectParentInterfaces {
    /**
     * 我的收藏
     */
    interface ICollect extends BaseListView, BaseSwipeView {
        void getHttpData(List<BusinessData> datas);
    }

}
