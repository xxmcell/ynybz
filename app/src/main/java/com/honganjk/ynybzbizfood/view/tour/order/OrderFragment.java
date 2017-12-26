package com.honganjk.ynybzbizfood.view.tour.order;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.widget.BadgeViewNew;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 旅游订单页
 * Created by Administrator on 2017-11-07.
 */

public class OrderFragment extends Fragment {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    Unbinder unbinder;
    String[] titles = {"待付款", "待出行", "已完成"};
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    public ArrayList<BadgeViewNew> views = new ArrayList<>();
    private ViewPagerAdapter adapter;

    public OrderFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_tour_order, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void init() {

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        fragmentDatas.clear();
        fragmentDatas.add(OrderListFragment.getInstance(0));//待付款
        fragmentDatas.add(OrderListFragment.getInstance(1));//待出行
        fragmentDatas.add(OrderListFragment.getInstance(2));//已完成

        adapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        viewPage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPage);
        for (int i = 0; i < titles.length; i++) {
            views.add(setUpTabBadge(i));
        }
    }

    /**
     * 当参数isVisibleToUser为true的时候，fragment的UI是可见的
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            isLogin(true);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    public boolean isLogin(boolean isToLogin) {
        if (!UserInfo.isSLogin()) {
            if (isToLogin) {
                LoginActivity.startUI(getActivity());
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置Tablayout上的标题的角标
     */
    private BadgeViewNew setUpTabBadge(int position) {
        try {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_title_layout, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(titles[position]);

            //控制角标的位置
            TextView tv = (TextView) view.findViewById(R.id.tv_title);
            BadgeViewNew badgeView = new BadgeViewNew(getContext(), (View) tv);
            tab.setCustomView(view);

//            BadgeViewNew badgeView = new BadgeViewNew(this, (View) view.getParent());
            badgeView.setBadgePosition(BadgeViewNew.POSITION_TOP_RIGHT);
            badgeView.setTextSize(DimensUtils.px2dip(getContext(), getResources().getDimension(R.dimen.text_m)));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}
