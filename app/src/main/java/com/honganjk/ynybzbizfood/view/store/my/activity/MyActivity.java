package com.honganjk.ynybzbizfood.view.store.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.view.store.classify.activity.ClassifyActivity;
import com.honganjk.ynybzbizfood.view.store.collect.activity.CollectActivity;
import com.honganjk.ynybzbizfood.view.store.home.activity.HomeActivity;
import com.honganjk.ynybzbizfood.view.store.order.activity.StoreOrderActivity;
import com.honganjk.ynybzbizfood.view.store.refund.activity.ReFundOrderBill;
import com.honganjk.ynybzbizfood.view.store.shoppingcar.activity.ShoppingCarActivity;

/**
 * 说明:商城-个人中心
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class MyActivity extends com.honganjk.ynybzbizfood.view.shitang.my.activity.MyActivity {
    private static String broadFlag = "Buttom_BroadFlag";

    LinearLayout ivHllParentead;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, MyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    public void initView() {
        ahBottomNavigation.setAccentColor(getResources().getColor(R.color.main_store));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.home),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_home_green, R.mipmap.ic_store_home_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.classify),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_classify_green, R.mipmap.ic_store_classify_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.shoppingCar),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_shoppingcar_green, R.mipmap.ic_store_shoppingcar_gray)));

        ahBottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.my),
                DrawableUtils.getStateSelectDrawable(R.mipmap.ic_store_my_green, R.mipmap.ic_store_my_gray)));


        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        ahBottomNavigation.setCurrentItem(currentItem(), true);
        ahBottomNavigation.setForceTitlesDisplay(true);
        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setBehaviorTranslationEnabled(false);
//        ahBottomNavigation.setTitleTextSize(25,20);//设置文字的大小，选中时和没有选中时。


        ivHllParentead = (LinearLayout) findViewById(R.id.llParent);
        View view = UiUtils.getInflaterView(this, R.layout.store_activity_my);
        ivHllParentead.addView(view, 0);

        view.findViewById(R.id.order).setOnClickListener(onClickListener);
        view.findViewById(R.id.fuKuan).setOnClickListener(onClickListener);
        view.findViewById(R.id.faHuo).setOnClickListener(onClickListener);
        view.findViewById(R.id.shouHuo).setOnClickListener(onClickListener);
        view.findViewById(R.id.pingJia).setOnClickListener(onClickListener);
        view.findViewById(R.id.tuiHuan).setOnClickListener(onClickListener);
        view.findViewById(R.id.collect).setOnClickListener(onClickListener);
    }


    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position != currentItem()) {
            Intent intent = new Intent(broadFlag);
            intent.putExtra("position", position);
            sendBroadcast(intent);
            switch (position) {
                case 0:
                    HomeActivity.startUI(this);
                    break;
                case 1:
                    ClassifyActivity.startUI(this);
                    break;
                case 2:
                    ShoppingCarActivity.startUI(this);
                    break;
                case 3:
                    MyActivity.startUI(this);
                    break;
            }
        }
        return true;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AnimUtils.pressAnimationListener(v);
            switch (v.getId()) {
                case R.id.order:
                    StoreOrderActivity.startUI(mActivity, 0);
                    break;
                case R.id.fuKuan:
                    StoreOrderActivity.startUI(mActivity, 1);
                    break;
                case R.id.faHuo:
                    StoreOrderActivity.startUI(mActivity, 2);
                    break;
                case R.id.shouHuo:
                    StoreOrderActivity.startUI(mActivity, 3);
                    break;
                case R.id.pingJia:
                    StoreOrderActivity.startUI(mActivity, 4);
                    break;
                case R.id.tuiHuan:
                    Intent intent = new Intent(MyActivity.this, ReFundOrderBill.class);
                    startActivity(intent);

                    /**
                     * https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095
                     */
//                    AgreementActivity.startUI(mActivity,"https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095");
//                    Uri uri = Uri.parse("https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);

                    break;
                case R.id.collect:
                    CollectActivity.startUI(mActivity);
                    break;

            }
        }
    };

}
