package com.honganjk.ynybzbizfood.view.tour.order.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.MyOrderDetailBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderDetailBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderTourBean;

public interface OrderTourPresentInterface {
    /**
     * 我的订单
     */
    interface IOrderInterface extends BaseListView {
        void getHttpData(OrderTourBean.Data data);
    }
    /**
     * 获取旅行项目行程、酒店、门票、保险信息 ,生成订单
     */
    interface IOrderDetailInterface extends BaseView{
        void getHttpData(OrderDetailBean.Data data);
        void getOrder(OrderBean orderBean);
    }

    /**
     * 订单详情
     */
    interface MyOrderDetailInterface extends BaseView{
        void getOrderDetail(MyOrderDetailBean detailBean);
    }

    /**
     * 评论订单
     */
    interface EditEvaluationInterface extends BaseView{
        void isCommit(boolean bool);
    }
}
