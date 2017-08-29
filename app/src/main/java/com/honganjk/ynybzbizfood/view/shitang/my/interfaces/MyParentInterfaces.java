package com.honganjk.ynybzbizfood.view.shitang.my.interfaces;

import com.honganjk.ynybzbizfood.code.base.view.iview.BaseListView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseSwipeView;
import com.honganjk.ynybzbizfood.code.base.view.iview.BaseView;
import com.honganjk.ynybzbizfood.mode.javabean.LoginData.UserInfoData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.AddressBean;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.PromotionInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface MyParentInterfaces {
    /**
     * 获取用户信息
     */
    interface IMy extends BaseView {
        void getUserInfo(UserInfoData userInfoData);
    }

    interface IRename extends BaseView {
        void saveNewname(boolean bool);
    }

    interface IUserFangKui extends BaseView {
        void showData(boolean bool);
    }

    interface IRecharge extends BaseView {
        void backZhifuYaoSui(String str);
    }

    /**
     * 添加地址
     */
    interface ISelectAddress extends BaseListView,BaseSwipeView {
        void showAddress(List<AddressBean> list);

        void isDeleAddress(boolean bool);
    }

    interface IAddAddress extends BaseView {
        void isCommitSccess(boolean bool);
    }

    interface IEditAddress extends BaseView {
        void isEditSccess(boolean bool);

    }

    interface IPromotionInfo extends BaseListView, BaseSwipeView {
        void showYouHui(ArrayList<PromotionInfoBean> list);
    }

}
