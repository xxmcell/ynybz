package com.honganjk.ynybzbizfood.view.tour.me.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.PassengerDetail;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */

public interface TourMeInterface extends BaseView {

    /**
     * 旅游_我的常用旅客
     */
    interface MyCommonPassengerInrerface extends BaseListView,BaseSwipeView {
        void getCommonPassenger(List<CommonPassengerBean> dataList);
        void deleteSuccess(int id);
    }

    /**
     * 添加常用旅客
     */
    interface MyAddPassengerInterface extends BaseView{
        void addSucess(boolean isSuccess);
        void resetSuccess(boolean isSuccess);
        void getPassengerDetail(PassengerDetail data);
    }

    /**
     * 旅游_我的收藏
     */
    interface MyKeepsInrerface extends BaseListView,BaseSwipeView {
        void getKeeps(List<ObjsBean> dataList);
    }
}
