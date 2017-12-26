package com.honganjk.ynybzbizfood.view.tour.detail.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.pressenter.tour.detail.TourDetailPresenter;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.common.activity.LoginActivity;
import com.honganjk.ynybzbizfood.view.tour.detail.fragment.CommentsFragment;
import com.honganjk.ynybzbizfood.view.tour.detail.fragment.DetailsFragment;
import com.honganjk.ynybzbizfood.view.tour.detail.fragment.InstructionsFragment;
import com.honganjk.ynybzbizfood.view.tour.detail.interfaces.TourDetailInterface;
import com.honganjk.ynybzbizfood.view.tour.order.activity.TourOrderDetailActiviry;
import com.honganjk.ynybzbizfood.widget.autoloadding.StatusChangListener;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.sdk.android.impl.KernelContext.context;

/**
 * 旅游详情页
 * Created by Administrator on 2017-11-21.
 */

public class DetailActivity extends BaseMvpActivity<TourDetailInterface, TourDetailPresenter>
        implements TourDetailInterface, CBViewHolderCreator {

    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;  //banner
    @BindView(R.id.rv_datas)
    RecyclerView rvDatas;           //日期列表
    @BindView(R.id.tv_brand)
    TextView tvBrand;               //产品品牌
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;            //产品详情  用户评价  费用及须知
    @BindView(R.id.viewPage2)
    ViewPager viewPage2;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_reservation)
    TextView tvReservation;       //立即订购
    @BindView(R.id.consult)
    LinearLayout consult;          //电话咨询
    @BindView(R.id.collect)
    LinearLayout collect;           //收藏
    @BindView(R.id.tv_title)
    TextView tvTitle;               //标题
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.collectIm)
    ImageView collectIm;
    private int id;
    private List<BannerData> advertisementDatas = new ArrayList<>();
    private ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    private ArrayList<TourDetailBean.DataBean.Formats> formatsList = new ArrayList<>(); //日期集合
    private CommentsFragment commentsFragment;
    private ObjsBean objsBean;

    private boolean isCollect;


    public static void startUi(Context context, ObjsBean objs) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("objs", objs);
        context.startActivity(intent);
    }
    public static void startUiForResult(Activity activity, ObjsBean objs, int requestCode) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("objs", objs);
        activity.startActivityForResult(intent,requestCode);
    }

    public static void startUi(Context context,int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void getHttpData(TourDetailBean.DataBean data) {
        if (data != null) {
            advertisementDatas.clear();
            for (int i = 0; i < data.getBanners().size(); i++) {
                advertisementDatas.add(new BannerData(data.getBanners().get(i), i));
            }
            tvTitle.setText(data.getTitle());
            String[] split = data.getTags().split("-");
            for (int i = 0; i < split.length; i++) {
                TextView textView = new TextView(this);
                textView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.dp_5), this.getResources().getDimensionPixelSize(R.dimen.dp_1), this.getResources().getDimensionPixelSize(R.dimen.dp_5), this.getResources().getDimensionPixelSize(R.dimen.dp_1));
                textView.setBackground(this.getDrawable(R.drawable.bg_shape_corner));
                textView.setTextSize(10);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    layoutParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.dp_4), 0, 0, 0);
                }
                textView.setLayoutParams(layoutParams);
                textView.setText(split[i]);
                ((LinearLayout) findViewById(R.id.ll_tag_group)).addView(textView);
            }
            tvPrice.setText(data.getPrice() + "");
            tvBrand.setText(data.getBrand());
            advertisement.notifyDataSetChanged();

            //选择时间
            if (data.getFormats() != null && data.getFormats().size() != 0) {
                formatsList.addAll(data.getFormats());
            }else{
                for (int i = 0; i < 6; i++) {
                    TourDetailBean.DataBean.Formats formats = new TourDetailBean.DataBean.Formats();
                    formats.setHotelPrice(20);
                    formats.setId(i);
                    formats.setPrice(200);
                    formats.setNum(3);
                    formats.setTime(DateUtils.getFormatTime(DateUtils.addDay(DateUtils.currentStartTime(),i),DateUtils.TIME));
                    formatsList.add(formats);
                }
            }
            initFormats(data.getFormats());

//            Bundle bundle = new Bundle();
//            bundle.putSerializable("comments", data.getComments());
//            commentsFragment.setArguments(bundle);
            String[] titles = {"产品详情", "用户评价(" + data.getComments().getAll() + ")", "费用及须知"};
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
            viewPage2.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPage2);
            ((DetailsFragment) fragmentDatas.get(0)).setAdapter(data);

            //是否收藏
            collectIm.setSelected(data.getKeep());
            isCollect = data.getKeep();
        }
    }

    @Override
    public void collect(boolean collectOrRecollect) {
        collectIm.setSelected(collectOrRecollect);
        isCollect = collectOrRecollect;
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
    }

    private void initFormats(List<TourDetailBean.DataBean.Formats> formats) {

        rvDatas.setAdapter(new CommonAdapter<TourDetailBean.DataBean.Formats>(this, R.layout.item_tour_detail_days, formatsList) {
            @Override
            public void convert(ViewHolder holder, List t) {

            }

            @Override
            public void convert(final ViewHolder holder, final TourDetailBean.DataBean.Formats formats) {

                holder.setText(R.id.tv_day,formats.getTime().replace("2017-","")+" 周"+DateUtils.weekTwo(formats.getTime()));
//                holder.setText(R.id.tv_day,formats.getTime());
                holder.setText(R.id.tv_price, "￥" + formats.getPrice());
                holder.getView(R.id.relativeLayout).setBackground(getResources().getDrawable(R.drawable.bg_shape_corner));
                holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectDate(formats, holder.getmPosition());
                    }
                });
            }

            @Override
            public int getItemCount() {
                if (formatsList.size() > 8) {
                    return 8;
                }
                return formatsList.size();
            }
        });
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        if (objsBean!=null) {
            id = objsBean.getId();
        }else{
            id = getIntent().getIntExtra("id",0);
        }

        presenter.getData(id);

        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        fragmentDatas.add(DetailsFragment.getInstance());
        commentsFragment = CommentsFragment.getInstance(id);
        fragmentDatas.add(commentsFragment);
        fragmentDatas.add(InstructionsFragment.getInstance());

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        rvDatas.setLayoutManager(new GridLayoutManager(this, 4));
    }

    int lastSelectPosition = -1;
    private TourDetailBean.DataBean.Formats selectFormats;
    public void selectDate(TourDetailBean.DataBean.Formats formats, int position) {
        if (lastSelectPosition == position) {
            return;
        }
        if (lastSelectPosition != -1) {
            rvDatas.getChildAt(lastSelectPosition).findViewById(R.id.relativeLayout).setBackground(getResources().getDrawable(R.drawable.bg_shape_corner));
        }
        rvDatas.getChildAt(position).findViewById(R.id.relativeLayout).setBackground(getResources().getDrawable(R.drawable.bg_shape_corner_red));
        lastSelectPosition = position;
        selectFormats = formats;
    }

    @Override
    public void parseIntent(Intent intent) {
        objsBean = (ObjsBean) getIntent().getSerializableExtra("objs");
    }


    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                //轮播图 图片点击

            }
        };
    }

    @Override
    public void clearData() {
        presenter.getData(id);
    }

    @Override
    public TourDetailPresenter initPressenter() {
        return new TourDetailPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_detail;
    }

    @OnClick({R.id.back, R.id.tv_reservation, R.id.consult, R.id.collect, R.id.select_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_data: //选择日期
                SelectDataActivity.startUiForResult(this,formatsList,REQUEST_CODE);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_reservation: //预定
                onReservation();
                break;
            case R.id.consult://电话咨询
                DeviceUtil.callByPhone(mActivity, Global.SERVER_PHONE);
                break;
            case R.id.collect:  //收藏
                onCollect();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                selectFormats = (TourDetailBean.DataBean.Formats) data.getSerializableExtra("Formats");
                break;
        }
    }

    private void onCollect() {
        if (UserInfo.isSLogin()) {
            if (!isCollect) {
                presenter.collect(objsBean.getId()); //收藏
            }else{
                presenter.reCollect(objsBean.getId());//取消收藏
            }
        }
    }

    private void onReservation() {
        if (UserInfo.isSLogin()) {
            if (selectFormats == null) {
                ToastUtils.getToastShort("请选择日期");
            } else {
                ToastUtils.getToastShort("选择了" + selectFormats.getTime());
                Intent intent = new Intent(DetailActivity.this, TourOrderDetailActiviry.class);
                intent.putExtra("objsBean", objsBean);
                intent.putExtra("selectFormats", selectFormats);
                startActivity(intent);
            }
        }else{
            LoginActivity.startUI(this);
        }
    }

    @Override
    public StatusChangListener getStatusChangListener() {
        return null;
    }

    @Override
    public <T> Collection<T> getValidData(Collection<T> c) {
        return null;
    }

    @Override
    public void clearPagingData() {

    }

    @Override
    public int getPageindex() {
        return 0;
    }

    @Override
    public int getPageCount() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
