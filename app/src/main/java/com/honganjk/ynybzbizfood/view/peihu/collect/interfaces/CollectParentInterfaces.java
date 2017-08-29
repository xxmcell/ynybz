package com.honganjk.ynybzbizfood.view.peihu.collect.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;

import java.util.List;

/**
 * 说明:陪护-收藏
 * 360621904@qq.com 杨阳 2017/3/24  13:29
 */
public interface CollectParentInterfaces {
    /**
     *:陪护-收藏
     */
    interface ICollect extends BaseListView, BaseSwipeView {
        void getHttpData(List<NurserCommendData> datas);
    }

}
