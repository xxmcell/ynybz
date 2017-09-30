package com.honganjk.ynybzbizfood.view.store.refund.view;


import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;

public interface IRefundParent {
    /**
     * 退款
     */
    interface IRefund extends BaseView{
        //上传数据
        void setHttpData(boolean data);
        //下载数据
        void getHttpData(RefundRequestData data);
    }
}
