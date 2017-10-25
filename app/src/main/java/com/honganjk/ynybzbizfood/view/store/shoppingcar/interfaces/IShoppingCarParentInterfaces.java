package com.honganjk.ynybzbizfood.view.store.shoppingcar.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.mode.javabean.store.shoppingcar.ShoppingcarData;

import java.util.List;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-07-04  15:50
 */
public interface IShoppingCarParentInterfaces {
    /**
     *
     */
    interface IShoppingCarInterface extends BaseListView, BaseSwipeView {
        /**
         * 获取推荐的数据
         *
         * @param datas
         */
        void getHttpData(List<ShoppingcarData.ObjsBean> datas);

        /**
         * 数量的添加与删除
         *
         * @param isAddNumber
         */
        void addAndSubtractNumberHttp(boolean isAddNumber);
    }


}
