package com.honganjk.ynybzbizfood.view.tour.detail.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.DetailCommentsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;

/**
 * 旅游详情
 * Created by Administrator on 2017-11-21.
 */

public interface TourDetailInterface extends BaseListView,BaseView{
    void getHttpData(TourDetailBean.DataBean data);
    void collect(boolean collectOrRecollect);

    /**
     * 旅游详情-评论
     */
    interface DetailCommentsInterface extends BaseListView,BaseView{
        void getHttpData(DetailCommentsBean.Data data);
    }
    /**
     *
     */
    /**
     * 支付
     */
    interface IPayInterface extends BaseView{
        /**
         * 余额支付
         *
         * @param bool
         */
        void balancePay(boolean bool, double balance);

        /**
         * 获取支付要素
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
}
