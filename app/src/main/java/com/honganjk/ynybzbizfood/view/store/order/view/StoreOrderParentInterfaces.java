package com.honganjk.ynybzbizfood.view.store.order.view;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderDetailsData;

import java.util.List;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-17  15:42
 */
public interface StoreOrderParentInterfaces {
    /**
     * :商城-我的订单
     */
    interface IOrder extends BaseListView, BaseSwipeView {
        void getHttpData(int total, List<StoreOrderData.ObjsBean> datas);

        /**
         * 确认完成订单
         *
         * @param data
         */
        void confirmCompleted(boolean data);

        /**
         * 删除订单
         *
         * @param data
         */
        void deleteOrder(boolean data);

        /**
         * 取消订单
         *
         * @param data
         */
        void cancleOrder(boolean data);


    }

    /**
     * 共用订单的的操作回调
     */
    interface IOrderParent extends BaseView {
        /**
         * 删除订单
         *
         * @param data
         */
        void deleteOrder(boolean data);

        /**
         * 取消订单
         *
         * @param data
         */
        void cancleOrder(boolean data);


    }


    /**
     * 订单详情
     */
    interface IOrderDetails extends BaseView {

        void getData(StoreOrderDetailsData data);
    }


}
