package com.honganjk.ynybzbizfood.view.store.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BannerData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsTypeData;
import com.honganjk.ynybzbizfood.pressenter.store.home.ProductDetailsPresenter;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.store.home.frament.ProductDetailsEvaluateFragment;
import com.honganjk.ynybzbizfood.view.store.home.frament.ProductDetailsFragment;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;
import com.honganjk.ynybzbizfood.widget.banner.ConvenientBanner;
import com.honganjk.ynybzbizfood.widget.banner.NetworkImageHolderView;
import com.honganjk.ynybzbizfood.widget.banner.holder.CBViewHolderCreator;
import com.honganjk.ynybzbizfood.widget.dialog.strore.DialogStoreBuyProduct;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明:商城-产品详情
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class ProductDetailsActivity extends BaseMvpActivity<IHomeParentInterfaces.IProductDetails, ProductDetailsPresenter> implements CBViewHolderCreator, IHomeParentInterfaces.IProductDetails {
    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    List<BannerData> advertisementDatas = new ArrayList<>();
    @BindView(R.id.supply)
    TextView supply;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.oldPirce)
    TextView oldPirce;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.inventory)
    TextView inventory;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.reselect)
    TextView reselect;
    @BindView(R.id.collectIm)
    ImageView collectIm;
    private int mId;
    ProductDetailsData mData;
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage2)
    ViewPager viewPage;
    String[] titles = {"商品详情", "商品评价"};
    boolean isAddShoppingCar = false;
    private boolean changeStats;

    public static void startUI(Activity activity, int id) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_product_details;
    }

    @Override
    public void parseIntent(Intent intent) {
        mId = intent.getIntExtra("id", mId);
    }

    @Override
    public void initView() {
        advertisement.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(Global.ADVERTISEMENT_TIME);

        fragmentDatas.add(ProductDetailsFragment.getInstance());
        fragmentDatas.add(ProductDetailsEvaluateFragment.getInstance());

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.main_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.gray_99), getResources().getColor(R.color.main_color));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.gray_f8));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);

        presenter.getData(mId);

        AnimUtils.pressAnimationListener(findViewById(R.id.consult));
        AnimUtils.pressAnimationListener(findViewById(R.id.collect));
        AnimUtils.pressAnimationListener(findViewById(R.id.addShoppingCar));
        AnimUtils.pressAnimationListener(findViewById(R.id.buy));
        AnimUtils.pressAnimationListener(findViewById(R.id.reselect));
        AnimUtils.pressAnimationListener(findViewById(R.id.back));
    }


    @Override
    public ProductDetailsPresenter initPressenter() {
        return new ProductDetailsPresenter();
    }

    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }

    public void clearData() {

    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView<BannerData>() {
            @Override
            public void onItemClicklistener(View item, int position, BannerData data) {
                data.transformPage(ProductDetailsActivity.this);
            }
        };
    }

    @Override
    public void onRetryClick(ContextData data) {
    }

    @Override
    public void onEmptyClick(ContextData data) {
    }

    @Override
    public void getData(ProductDetailsData data) {
        mData = data;
        if (data.getBanners() != null) {
            advertisementDatas.clear();
            for (int i = 0; i < data.getBanners().size(); i++) {
                BannerData d = new BannerData(data.getBanners().get(i), 0);
                advertisementDatas.add(d);
            }
            advertisement.notifyDataSetChanged();
        }

        if (data.getBanners() != null) {
            ((ProductDetailsFragment) fragmentDatas.get(0)).setAdapter(data.getDetails());
        }

        if (data.getComments() != null) {   //评价
            ((ProductDetailsEvaluateFragment) fragmentDatas.get(1)).setAdapter(data.getComments());
        }

        supply.setText(data.getFeature());
        name.setText(data.getTitle());
        price.setText(data.getMoneyStr());
        oldPirce.setText(StringUtils.setTextStyle(data.getPriceStr()));
        number.setText(data.getSales());
        inventory.setText(data.getStock());
        address.setText(data.getOrigin());

        collectIm.setSelected(mData.getKeepBoolean());
        //记录是否点击状态
        changeStats = mData.getKeepBoolean();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.reselect, R.id.back, R.id.buy, R.id.consult, R.id.collect, R.id.addShoppingCar})
    public void onViewClicked(final View view) {

        AnimUtils.pressAnimationListener(view, new AnimUtils.OnClickListenerCallback() {
            @Override
            public void clickCallback(boolean isOnClick) {
                switch (view.getId()) {
                    //选择规格
                    case R.id.reselect:
                        presenter.getProductType(mId);
                        break;
                    //返回
                    case R.id.back:
                        finish();
                        break;
                    //购买
                    case R.id.buy:
                        if (isLogin(true)) {
                            isAddShoppingCar = false;
                            presenter.getProductType(mId);
                        }
                        break;
                    //收藏
                    case R.id.collect:
                        if (isLogin(true)) {
                            if (changeStats==true) {
                                collectIm.setSelected(false);
                                presenter.cancleCollect(mData.getId());
                            } else if (changeStats==false) {
                                collectIm.setSelected(true);
                                presenter.collect(mData.getId());
                            }
                        }
                        break;
                    //加入购物车
                    case R.id.addShoppingCar:
                        if (isLogin(true)) {
                            isAddShoppingCar = true;
                            presenter.getProductType(mId);
                        }
                        break;
                    //咨询
                    case R.id.consult:
                        DeviceUtil.callByPhone(mActivity, Global.SERVER_PHONE);
                        break;
                }
            }
        });
    }

    @Override
    public void getProductType(final List<ProductDetailsTypeData> data) {
        // TODO: 2017-09-08  加入购物车/购买
        //弹出的dialgog来完成支付流程
        DialogStoreBuyProduct dialogStroreBuyProduct = new DialogStoreBuyProduct(this, data, mId, mData.getTitle());
        dialogStroreBuyProduct.show();

        if (isAddShoppingCar) {//加入购物车
            dialogStroreBuyProduct.setAddShoppingCarListener(new DialogStoreBuyProduct.AddShoppingCarListener() {
                @Override
                public void addShoppingCar(ProductDetailsTypeData mData) {
                    presenter.addShopingCar(mData.getId(), mData.getType(), mData.getNumber());
                }
            });
        }
    }

    @Override
    public void collect(boolean data) {
        changeStats=data;
    }

    @Override   //  加入成功否
    public void addShoppingCar(boolean datas) {

    }


}
