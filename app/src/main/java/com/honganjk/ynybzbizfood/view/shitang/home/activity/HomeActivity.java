package com.honganjk.ynybzbizfood.view.shitang.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMainActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.HomeParentPresenter;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.BusinessFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.fragment.CommonFragment;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.widget.AppBarStateChangeListener;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;


/**
 * 说明:食堂-主页
 * f
 * 2017/3/2-18:51
 * mm
 */
public class HomeActivity extends BaseMainActivity<HomeParentInterface.homeParent, HomeParentPresenter> implements
        HomeParentInterface.homeParent, CBViewHolderCreator, View.OnClickListener, OnItemClickListener, com.honganjk.ynybzbizfood.widget.banner.listener.OnItemClickListener {

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.rl_communitycanteen)
    RelativeLayout rlCommunitycanteen;
    @BindView(R.id.rl_breakfast)
    RelativeLayout rlBreakfast;
    @BindView(R.id.rg_classify)
    RadioGroup rgClassify;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    ArrayList<BusinessData> datas = new ArrayList<>();
    List<BannerData> advertisementDatas = new ArrayList<>();
    ArrayList<Fragment> fragmetns = new ArrayList<>();

    @BindView(R.id.toolB)
    public Toolbar toolBar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.title)
    TextView title;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public HomeParentPresenter initPressenter() {
        return new HomeParentPresenter();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_home_shitang;
    }


    @Override
    public void initView() {
        super.initView();
        setRecyclerView();
        initHeadView();
        toolBar.setNavigationIcon(R.drawable.ic_arrows_left);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //扩张时候的title颜色：
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.translucent));
        //收缩后在Toolbar上显示时的title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.main_color));

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                //展开状态
                if (state == State.EXPANDED) {
                    toolBar.setNavigationIcon(R.drawable.ic_arrows_left);
                    title.setVisibility(View.GONE);
                    //折叠状态
                } else if (state == State.COLLAPSED) {
                    toolBar.setNavigationIcon(R.drawable.material_arrwos_white_left);
                    title.setVisibility(View.VISIBLE);

                    //中间状态
                } else {

                }
            }
        });

        /**
         * "img": "https://hajk.image.alimmdn.com/dev/1489052149980",
         "url": "",
         "sort": 0,
         "type": 3,
         "bid": 8997
         */

        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        fragmetns.add(new BusinessFragment());
        fragmetns.add(new CommonFragment());
        viewPage.setAdapter(new MyAdapter(getSupportFragmentManager()));
        presenter.getHttpData();

    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmetns.get(position);
        }

        @Override
        public int getCount() {
            return fragmetns.size();
        }


    }


    private void initHeadView() {
        tvLocation.setText(userData.getAddress());
        title.setText(userData.getAddress());
        toolBar.setTitle(getResources().getString(R.string.app_name_simple));
        tvLocation.setOnClickListener(this);
        rgClassify.setOnCheckedChangeListener(onCheckedChangeListener);
        ((RadioButton) rgClassify.getChildAt(0)).setChecked(true);
        rlCommunitycanteen.setOnClickListener(this);
        rlBreakfast.setOnClickListener(this);

        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) rgClassify.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.indexOfChild(group.findViewById(checkedId))) {
                //推荐商家
                case 0:
                    viewPage.setCurrentItem(0);
                    break;
                //常吃厨房
                case 1:
                    viewPage.setCurrentItem(1);
                    break;
            }
        }
    };

    private void setRecyclerView() {
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public int currentItem() {
        return 0;
    }


    @Override
    public void clearData() {
        datas.clear();

    }


    @Override
    public void banner(List<BannerData> data) {
        advertisementDatas.clear();
        advertisementDatas.addAll(data);
        advertisement.notifyDataSetChanged();

    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                data.transformPage(HomeActivity.this);

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //社区食堂
            case R.id.rl_communitycanteen:
                CommunitycanteenActivity.startUI(this, 1);
                break;
            //早餐
            case R.id.rl_breakfast:
                Time t = new Time();
                t.setToNow(); // 取得系统时间。
                int hour = t.hour; // 0-23
                if (hour > 18 || hour < 9) {
                    CommunitycanteenActivity.startUI(this, 3);
                } else {
                    MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                            .title(getResources().getString(R.string.app_name_simple))
                            .titleColorRes(R.color.main_color)
                            .negativeText("确定")
                            .negativeColorRes(R.color.main_color)
                            .content(getResources().getString(R.string.tv_notice_breakfast))
                            .build();
                    materialDialog.show();
                }

                break;
            case R.id.tv_location:
                SelectAddressActivity.startForResultUi(this,REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String address = data.getStringExtra("address");
                if (!TextUtils.isEmpty(address)) {
                    tvLocation.setText(address);
                    title.setText(address);
                }
            }
        }
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, Object data, int position) {

        BusinessData data1 = datas.get(position);
        CarteenDetailActivity.startUI(this, data1.getBid());
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }

    @Override
    public void onBackPressed() {
        MainHomeActivity.startUI(this);
        finish();
    }
  /*  @OnClick(R.id.iv_back)
    public void onClick() {
        MainHomeActivity.startUI(HomeActivity.this);
    }*/
}