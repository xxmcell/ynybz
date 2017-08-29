package com.honganjk.ynybzbizfood.view.shitang.order.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.CreateOrderSucceedData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;

import java.util.List;

/**
 * 说明:我订单父类接口
 * 360621904@qq.com 杨阳 2017/3/6  14:25
 */
public interface OrderParentInterfaces {
    /**
     * 我的订单
     */
    interface IOrder extends BaseListView, BaseSwipeView {
        void getHttpData(List<OrderData> datas);

        //确认收货
        void commitOrder(boolean datas);

        //取消订单
        void cancelOrder(boolean datas);
    }

    /**
     * 我的订单-详情
     */
    interface IOrderDetails extends BaseView, BaseSwipeView {
        //获取订单详情
        void getHttpData(OrderDetailsData datas);

        //确认收货
        void commitOrder(boolean datas);

        //取消订单
        void cancelOrder(boolean datas);
    }

    /**
     * 评价
     */
    interface IWritingEvaluation extends BaseView {
        void isCommit(boolean bool);
    }


    /**
     * 评价
     */
    interface IPay extends BaseView {
        /**
         * 余额支付
         *
         * @param bool
         */
        void balancePay(boolean bool, double balance);

        /**
         * 获取食堂订单支付要素
         *
         * @param data
         */
        void otherPay(String data);

        /**
         * 获取余额
         *
         * @param bool
         */
        void getBalance(boolean bool, double balance);
    }

    /**
     * 确认订单
     */
    interface IQueRenXiaDan extends BaseView {
        /**
         * 创建订单
         *
         * @param data
         */
        void isXiandan(CreateOrderSucceedData data);

        /**
         * 获取优惠信息
         *
         * @param data
         */
        void getFavorableInfo(FavorableData data);

        /**
         * 获取运费
         *
         * @param data
         */
        void getTheShoppingFee(int data);

        /**
         * 获取默认地址
         *
         * @param data
         */
        void getDefaultAddress(DefaultAddressData data);


    }
}
