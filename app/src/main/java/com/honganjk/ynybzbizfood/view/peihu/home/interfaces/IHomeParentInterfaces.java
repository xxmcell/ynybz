package com.honganjk.ynybzbizfood.view.peihu.home.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserTypeData;
import com.honganjk.ynybzbizfood.view.other.view.IOtherView;

import java.util.List;

/**
 * Created by admin on 2017/3/3.
 */

public interface IHomeParentInterfaces {
    /**
     * 陪护首页
     */
    interface IHomeInterface extends BaseListView, BaseSwipeView, IOtherView.IAdverdisement {
        /**
         * 获取推荐的数据
         *
         * @param datas
         */
        void getRecommendData(List<NurserCommendData> datas);
    }

    /**
     * 护工与康复师-列表页
     */
    interface INursingRecover extends BaseListView, BaseSwipeView {

        /**
         * 服务人员列表
         */
        void getNursingTherapistDatas(List<NurserCommendData> datas);

        /**
         * 服务类型
         */
        void serviceType(List<NurserTypeData> datas);


    }

    /**
     * 护工与康复师-详情页
     */
    interface INursingRecoveryDetails extends BaseView {
        /**
         * 详情信息
         *
         * @param data
         */
        void getData(NurserCommendDetailsData data);

        /**
         * 提示内容
         *
         * @param data
         */
        void getHint(List<String> data);

        /**
         * 设置收藏
         *
         * @param isCollect
         */
        void setCollect(boolean isCollect);

        /**
         * 取消收藏
         *
         * @param isCollect
         */
        void setCollectCancel(boolean isCollect);
    }


}
