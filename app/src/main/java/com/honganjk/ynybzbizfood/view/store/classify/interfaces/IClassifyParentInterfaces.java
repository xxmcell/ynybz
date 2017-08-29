package com.honganjk.ynybzbizfood.view.store.classify.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;

import java.util.List;

/**
 * 说明:商城
 * 作者： 杨阳; 创建于：  2017-06-29  14:38
 */
public interface IClassifyParentInterfaces {
    /**
     * 商城-分类
     */
    interface IClassifyInterface extends BaseSwipeView, BaseListView {
        /**
         * 获取数据
         *
         * @param datas
         */
        void getHttpData(List<StoreHomeData.ObjsBean> datas);

        /**
         * 获取筛选的种类
         *
         * @param datas
         */
        void filtrateClassify(List<PopupPulldown.PullDownData> datas);

        /**
         * 获取筛选的品牌
         *
         * @param datas
         */
        void filtrateBrand(List<PopupPulldown.PullDownData> datas);

        /**
         * 添加到购物车
         */
        void addShoppingCar(boolean datas);

    }


}
