package com.honganjk.ynybzbizfood.view.store.collect.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;

import java.util.List;

/**
 * 说明:商城
 * 作者： 杨阳; 创建于：  2017-06-29  14:38
 */
public interface ICollectParentInterfaces {
    /**
     * 商城-收藏
     */
    interface ICollectInterface extends BaseSwipeView, BaseListView {
        /**
         * 获取数据
         *
         * @param datas
         */
        void getHttpData(List<StoreHomeData.ObjsBean> datas);


    }


}
