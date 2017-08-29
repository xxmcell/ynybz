package com.honganjk.ynybzbizfood.view.shitang.home.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.EvaluateData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/3.
 */

public interface HomeParentInterface {


    interface homeParent extends BaseView {
        void banner(List<BannerData> data);

    }

    interface home extends BaseListView, BaseSwipeView {
        void businessList(BusinessData datas);//推荐食堂

        void commonList(List<BusinessData> datas);//常吃厨房
    }

    interface communityCarteen extends BaseListView, BaseSwipeView {
        void businessList(List<BusinessData> datas);
    }

    /**
     * 店家-fragment-管理类
     */
    interface carteenDetail extends BaseView {
        void carteenDetail(BusinessDetailsData data);

        /**
         * 获取优惠列表
         *
         * @param datas
         */
        void getFavorableList(List<FavorableListData> datas);
    }

    interface foodsMenu extends BaseView {
        void foodsMenu(FoodData data);
    }

    interface breakfastMenu extends BaseView {
        void breakfastMenu(ArrayList<FoodData.DishsBeanX.DishsBean> datas);
    }

    /**
     * 菜品详情页
     */
    interface foodDetail extends BaseView {
        void foodDetail();

        /**
         * 获取优惠列表
         *
         * @param datas
         */
        void getFavorableList(List<FavorableListData> datas);
    }

    interface evaluate extends BaseListView, BaseSwipeView {
        void evaluate(EvaluateData data, List<EvaluateData.ListBean> datas);
    }

    interface businessInfo extends BaseView {
        void businessInfo(BusinessDetailsData data);

        void collectBusiness(boolean success);
    }

}
