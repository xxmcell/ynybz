package com.honganjk.ynybzbizfood.utils.other;


import com.honganjk.ynybzbizfood.mode.third.ALBCUtils;
import com.honganjk.ynybzbizfood.view.health.activity.HealthManagerActivity;
import com.honganjk.ynybzbizfood.view.health.activity.HealthReportActivity;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.view.other.activity.TestActivity;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryDetailsActivity;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryManagerActivity;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryGradeFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryHintFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryServiceFragment;
import com.honganjk.ynybzbizfood.view.peihu.order.activity.SubscribeActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CarteenDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.CommunitycanteenActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.activity.FoodDetailActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BreakFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BreakfastFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessInfoFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessListFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.CommonFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.EvaluateFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.GoodsFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.GoodsListFragment;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.AccountActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.MyActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.RechargeActivity;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.OrderDetailsActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.QueRenXiaDanActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;

/**
 * 结构与帮助文档
 * Created by yang on 2016/7/5.
 * ￥ ¥￥
 * 1，不能以数字开头
 * 2，不能包含中文
 * 3，不能包含‘-’
 * 4，可以用'_'
 * 5，其它符号都不能用
 * 6,不能有大写字母
 * <p>
 */
public class Document {

    private String common = "================================================ 公共 ================================================";

    private void common() {
        /**
         * {@link TestActivity}                         测试类
         * {@link MainHomeActivity}                     选择首页
         * {@link CartBottomLayout}                     购物车的布局
         *
         */
    }

    private String business = "================================================ 食堂 ================================================";

    private void business() {
        /**
         * ================================================ 主页 ================================================
         *
         *{@link HomeActivity}                          食堂-首页
         *{@link BusinessFragment}                      食堂-首页-推荐
         *{@link CommonFragment}                        食堂-首页-常吃
         *{@link CarteenDetailActivity}                 店家-fragment-管理类
         *{@link GoodsFragment}                         店家-商品 (早餐)
         *{@link EvaluateFragment}                      店家-评价
         *{@link BusinessListFragment}                  商家列表（社区食堂，早餐）
         *{@link BusinessInfoFragment}                  店家-商家
         *{@link BusinessInfoFragment}                  店家-商家
         *{@link GoodsListFragment}                     菜单列表
         *{@link CommunitycanteenActivity}              早餐
         *{@link BreakfastFragment}                     早餐-距离最近，评分最高(管理列表的Fragment)
         *{@link BreakFragment}                         早餐-列表
         *{@link FoodDetailActivity}                    产品详情页
         *
         *
         *
         *{@link CartBottomLayout}                      购物的底部栏
         *
         * ================================================ 收藏 ================================================
         *
         *
         * ================================================ 订单 ================================================
         *{@link OrderActivity}                             我的订单管理类
         *{@link QueRenXiaDanActivity}                   确认下单
         *{@link OrderFragment}                            订单列表
         *{@link OrderDetailsActivity}                   订单详情
         *{@link WritingEvaluationActivity}              订单评价
         *
         * ================================================ 我的 ================================================
         *{@link MyActivity}                              个人中心
         *{@link SelectAddressActivity}                  地址选择的
         *{@link RechargeActivity}                        充值
         *{@link ChongZhiActivity}                          充值列表
         *{@link AccountActivity}                         更改个人信息
         *
         *
         *
         */
    }

    private String user = "================================================ 陪护 ================================================";

    private void user() {
        /**
         * ================================================ 主页 ================================================
         *{@link NursingRecoveryManagerActivity}           护工与康复师-师列表管理类
         *{@link NursingRecoveryFragment}                   护工与康复师-列表页
         *{@link NursingRecoveryDetailsActivity}           护工与康复师-详情页
         *{@link NursingRecoveryGradeFragment}             护工与康复师-详情页-评价列表
         *{@link NursingRecoveryHintFragment}              护工与康复师-详情页-提示
         *{@link NursingRecoveryServiceFragment}           护工与康复师-详情页-服务内容
         *
         *
         *
         *
         *
         * ================================================ 收藏 ================================================
         *@link CollectActivity}                            陪护-预约下单
         *
         *
         *
         * ================================================ 订单 ================================================
         *{@link SubscribeActivity}                        陪护-预约下单
         *{@link OrderDetailsActivity}                     陪护-订单详情
         *{@link OrderActivity}                              陪护-订单管理类
         *{@link OrderFragment}                              陪护-订单列表
         *
         *
         *
         * ================================================ 我的 ================================================
         * */
    }
    private String health = "================================================ 健康 ================================================";

    private void health() {
        /**
         *{@link HealthManagerActivity}                     健康管理
         *{@link HealthReportActivity}                      健康报告
         *
         */
    }

    private String thirdParty = "================================================ 第三方 ================================================";

    private void thirdParty() {
        /**
         *{@link ALBCUtils}               阿里百川工具类
         *
         */
    }


    /**
     * therapist 康复师
     * nursing   护理（护工）
     * recovery  康复
     *
     *
     *
     */

}
