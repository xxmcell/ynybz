package com.honganjk.ynybzbizfood.view.shitang.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.presenter.BasePresenter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessListFragment;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/4 0004.
 */

public class CommunitycanteenActivity extends BaseMvpActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.tv_rank_hight)
    TextView tvRankHight;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.toolbar)
    SupperToolBar toolbar;
    @BindView(R.id.view_left)
    View viewLeft;
    @BindView(R.id.view_right)
    View viewRight;
    private BusinessListFragment distanceFragment;
    private BusinessListFragment rankFragment;
    private int businessType = 1;

    public static void startUI(Activity activity,int businessType) {
        Intent intent = new Intent(activity, CommunitycanteenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("businessType",businessType);
        activity.startActivity(intent);
    }

    @Override
    public BasePresenter initPressenter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_communitycanteen;
    }

    @Override
    public void initView() {

        switch (businessType){
            case 1:
                toolbar.setTitle(R.string.tv_communitycanteen);
                break;
            case 2:
//                toolbar.setTitle(R.string.tv_communitycanteen);
                break;
            case 3:
                toolbar.setTitle(R.string.tv_breakfast);
                break;
        }
        toolbar.setBack(this);

        distanceFragment = new BusinessListFragment();
        Bundle distanceBundle = new Bundle();
        distanceBundle.putInt("businessType", businessType);
        distanceBundle.putInt("sortType", 1);
        distanceFragment.setArguments(distanceBundle);

        rankFragment = new BusinessListFragment();
        Bundle rankBundle = new Bundle();
        rankBundle.putInt("businessType", businessType);
        rankBundle.putInt("sortType", 2);
        rankFragment.setArguments(rankBundle);

        fragments.add(distanceFragment);
        fragments.add(rankFragment);

        viewpager.setOnPageChangeListener(this);
        viewpager.setAdapter(adapter);

    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    };

    @Override
    public void parseIntent(Intent intent) {
        this.businessType = intent.getIntExtra("businessType",1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_near, R.id.tv_rank_hight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_near:
                if (viewpager.getCurrentItem() == 1) {
                    viewpager.setCurrentItem(0);
                }
                break;
            case R.id.tv_rank_hight:
                if (viewpager.getCurrentItem() == 0) {
                    viewpager.setCurrentItem(1);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                viewLeft.setVisibility(View.VISIBLE);
                viewRight.setVisibility(View.INVISIBLE);
                break;
            case 1:
                viewLeft.setVisibility(View.INVISIBLE);
                viewRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
