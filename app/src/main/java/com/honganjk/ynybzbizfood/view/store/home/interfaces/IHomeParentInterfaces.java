package com.honganjk.ynybzbizfood.view.store.home.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsTypeData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomePayData;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;

import java.util.List;

/**
 * 说明:商城
 * 作者： 杨阳; 创建于：  2017-06-29  14:38
 */
public interface IHomeParentInterfaces {
    /**
     * 商城首页
     */
    interface IHomeInterface extends BaseSwipeView, BaseListView {
        /**
         * 获取推荐的数据
         * @param datas
         */
        void getHttpData(List<StoreHomeData.ObjsBean> datas);

        //轮播图
        void getAdvertisement(List<String> data);
    }

    /**
     * 产品详情
     */
    interface IProductDetails extends BaseView, OnRetryClickListion {
        //获取详情
        void getData(ProductDetailsData data);

        //获取产品规格
        void getProductType(List<ProductDetailsTypeData> data);

        //收藏
        void collect(boolean data);

        /**
         * 添加到购物车
         */
        void addShoppingCar(boolean datas);
    }

    /**
     * :预约下单
     */
    interface IStoreSubscribe extends BaseView {
        /**
         * 得到默认地址
         */
        void getDefaultAddress(DefaultAddressData data);

        /**
         * 下单是否成功
         */
        void placeTheOrderIsSucceed(StoreHomePayData data);
    }




    /**
     * :搜索关键字
     */
    interface ISearchSubscribe extends BaseView {
        /**
         * 获得搜索数据
         */
       void getSearchdata(List<StoreHomeData.ObjsBean>  datas);
    }



}
