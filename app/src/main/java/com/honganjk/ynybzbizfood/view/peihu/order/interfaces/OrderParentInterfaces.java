package com.honganjk.ynybzbizfood.view.peihu.order.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ServiceBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ValideDataBean;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.NurserOrderdData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.OrderDetailsBean;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;

import java.util.List;

/**
 * 说明:陪护-我的订单
 * 360621904@qq.com 杨阳 2017/3/24  13:29
 */
public interface OrderParentInterfaces {
    /**
     * :陪护-我的订单
     */
    interface IOrder extends BaseListView, BaseSwipeView {
        void getHttpData(int total, List<NurserOrderdData.ObjsBean> datas);

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
     * :陪护-预约下单
     */
    interface ISubscribe extends BaseView {
        /**
         * 得到默认地址
         */
        void getDefaultAddress(DefaultAddressData data);

        /**
         * 获取服务信息
         */
        void getData(ServiceBean data);

        /**
         * 返回护工的可约时间
         *
         * @param data
         */
        void getNursingValidTime(int[] data);

        /**
         * 返回康复师的可约时间
         *
         * @param data
         */
        void getTherapistValidTime(ValideDataBean data);

        /**
         * 下单是否成功
         *
         * @param isSucceed 是否成功生成订单
         * @param orderId   生成的订单Id
         */
        void placeTheOrderIsSucceed(boolean isSucceed, int orderId);


    }

    interface IOrderDetails extends BaseView {
        /**
         * 获取服务信息
         */
        void getData(OrderDetailsBean data);




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


}
