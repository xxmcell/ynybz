package com.honganjk.ynybzbizfood.view.peihu.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.view.peihu.order.fragment.OrderFragment;
import com.honganjk.ynybzbizfood.widget.BadgeViewNew;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 说明:陪护-我的订单
 * 2017/3/2-18:51
 */
public class OrderActivity extends BaseMainActivity {
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    public ArrayList<BadgeViewNew> views = new ArrayList<>();

    String[] titles = {"待服务", "服务中", "已完成"};

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_shit_order;
    }

    @Override
    public void initView() {
        super.initView();
        toolbar.setTitle("全部订单");
        toolbar.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final TextView titleView = toolbar.getTitleView();
        Drawable drawable = getResources().getDrawable(R.drawable.material_arrwos_white_down);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        titleView.setCompoundDrawables(null, null, drawable, null);
        //筛选显示订单类型
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //护理类型，1-全天、2-钟点
                ArrayList<PopupPulldown.PullDownData> datas = new ArrayList<>();
                datas.add(new PopupPulldown.PullDownData(0, "全部"));
                datas.add(new PopupPulldown.PullDownData(1, "护工"));
                datas.add(new PopupPulldown.PullDownData(2, "康复师"));
                PopupPulldown pp = new PopupPulldown(OrderActivity.this, datas);
                pp.showPopupWindow(toolbar);
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        ((OrderFragment) fragmentDatas.get(0)).setOrderStatus(id);
                        ((OrderFragment) fragmentDatas.get(1)).setOrderStatus(id);
                        ((OrderFragment) fragmentDatas.get(2)).setOrderStatus(id);
                        toolbar.setTitle(content + "订单");
                    }
                });
            }
        });

//        fragmentDatas.add(OrderFragment.getInstance(0));
        fragmentDatas.add(OrderFragment.getInstance(1));
        fragmentDatas.add(OrderFragment.getInstance(2));
        fragmentDatas.add(OrderFragment.getInstance(4));

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setOffscreenPageLimit(3);
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);
        for (int i = 0; i < titles.length; i++) {
            views.add(setUpTabBadge(i));
        }
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public int currentItem() {
        return 2;
    }


    /**
     * 设置Tablayout上的标题的角标
     */
    private BadgeViewNew setUpTabBadge(int position) {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View view = LayoutInflater.from(this).inflate(R.layout.tab_title_layout, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(titles[position]);

            //控制角标的位置
            TextView tv = (TextView) view.findViewById(R.id.tv_title);
            BadgeViewNew badgeView = new BadgeViewNew(this, tv);
            tab.setCustomView(view);

//            BadgeViewNew badgeView = new BadgeViewNew(this, (View) view.getParent());
            badgeView.setBadgePosition(BadgeViewNew.POSITION_TOP_RIGHT);
            badgeView.setTextSize(DimensUtils.px2dip(this, getResources().getDimension(R.dimen.text_xm)));
            badgeView.setVisibility(View.GONE);
            return badgeView;

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setCount(int position, int count) {
        views.get(position).show(count == 0 ? true : false);
        views.get(position).setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        AnimUtils.scaleAnim(views.get(position));
        views.get(position).setText(count + "");
    }


    /**
     * Activity被系统杀死后再重建时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("AAAA", "onRestoreInstanceState");
        //如果系统杀死该页面则关闭
        OrderActivity.this.finish();
        super.onRestoreInstanceState(savedInstanceState);
    }

}
