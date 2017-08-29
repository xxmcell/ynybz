package com.honganjk.ynybzbizfood.view.shitang.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableListData;
import com.honganjk.ynybzbizfood.pressenter.shitang.home.FoodDetailPresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.shitang.home.interfaces.HomeParentInterface;
import com.honganjk.ynybzbizfood.widget.appbarlayout.SupperToolBar;
import com.honganjk.ynybzbizfood.widget.cartlayout.CartBottomLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class FoodDetailActivity extends BaseMvpActivity<HomeParentInterface.foodDetail, FoodDetailPresenter>
        implements HomeParentInterface.foodDetail, CartBottomLayout.cartBottomLayoutListener {


    @BindView(R.id.toolbar)
    SupperToolBar toolbar;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_reduce)
    ImageView ivReduce;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_brief)
    TextView tvBrief;
    @BindView(R.id.cartbottomlayout)
    CartBottomLayout cartbottomlayout;
    @BindView(R.id.favorableContent)
    TextView favorableContent;


    private FoodData.DishsBeanX.DishsBean data;
    private BusinessDetailsData businessDetailsData;
    private ArrayList<FoodData.DishsBeanX.DishsBean> datas;
    private int mid;

    @Override
    public FoodDetailPresenter initPressenter() {
        return new FoodDetailPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_fooddetail;
    }

    /**
     * @param fragment
     * @param data                菜品对象
     * @param businessDetailsData 商家对象
     * @param datas               购物的数据
     * @param mid                 菜单id
     */
    public static void startUIForResult(Fragment fragment, FoodData.DishsBeanX.DishsBean data, BusinessDetailsData businessDetailsData,
                                        ArrayList datas, int mid) {
        Intent intent = new Intent(fragment.getContext(), FoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putParcelable("businessDetailsData", businessDetailsData);
        bundle.putParcelableArrayList("datas", (ArrayList<? extends Parcelable>) datas);
        bundle.putInt("mid", mid);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        fragment.startActivityForResult(intent, 0x10);
    }


    @Override
    public void initView() {

        toolbar.setTitle(data.getName());
        toolbar.setBack(this);

        tvSurplus.setText(StringUtils.convertTextColor(String.format(getResources().getString(R.string.tv_surplus), data.getTotal() + ""), data.getTotal() + "", getResources().getColor(R.color.text_red)));
        tvName.setText(data.getName());
        tvPrice.setText(String.format(getResources().getString(R.string.tv_price), data.getPrice() + ""));
        GlideUtils.show(imageview, data.getImg(), new GlideOptions.Builder().bulider());
        tvTitle.setText(data.getName());
        tvBrief.setText(data.getDescs());
        setFoodNum(data.getNum());

        cartbottomlayout.setListener(this);
        cartbottomlayout.setProperty(businessDetailsData);

        cartbottomlayout.setData(datas);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setNum(data.getNum() + 1);
                setFoodNum(data.getNum());
                cartbottomlayout.changeFoodNum(data, false);


            }
        });
        ivReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setNum(data.getNum() - 1);
                setFoodNum(data.getNum());
                cartbottomlayout.changeFoodNum(data, false);
            }
        });

        //购物车的价格改变监听
        cartbottomlayout.setPriceListener(new CartBottomLayout.PriceListener() {
            @Override
            public void changeCallback(double price) {
                changeFavorableInfo(price);
            }
        });
        presenter.getFavorable(businessDetailsData.getId());

    }

    private void setFoodNum(int num) {
        if (num == 0) {
            tvNum.setVisibility(View.GONE);
            ivReduce.setVisibility(View.GONE);
        } else {
            tvNum.setVisibility(View.VISIBLE);
            tvNum.setText(num + "");
            ivReduce.setVisibility(View.VISIBLE);
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("datas", datas);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            data = bundle.getParcelable("data");
            businessDetailsData = bundle.getParcelable("businessDetailsData");
            datas = bundle.getParcelableArrayList("datas");
            mid = bundle.getInt("mid");
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void foodDetail() {

    }



    @Override
    public void clearData() {

    }

    @Override
    public void setDatas(ArrayList<FoodData.DishsBeanX.DishsBean> datas) {

    }

    @Override
    public void changeFoodNum(FoodData.DishsBeanX.DishsBean data) {
        if (data.getId() == this.data.getId()) {
            this.data.setNum(data.getNum());
            setFoodNum(data.getNum());
        }
    }

    @Override
    public void clearDatas() {

    }

    @Override
    public void statusChanged(boolean enable) {

    }

    @Override
    public ArrayList getDatas() {
        return null;
    }

    @Override
    public int getMenuId() {
        return mid;
    }


    /**
     * 优惠信息的集合
     */
    List<FavorableListData> mFavorableDatas = new ArrayList<>();


    @Override
    public void getFavorableList(List<FavorableListData> datas) {
        setFavorableContent(datas);
    }
    /**
     * 设置优惠信息
     *
     * @param datas
     */
    public void setFavorableContent(List<FavorableListData> datas) {
        if (datas == null || datas.size() == 0) {
            favorableContent.setVisibility(View.GONE);
            return;
        } else {
            favorableContent.setVisibility(View.VISIBLE);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            sb.append(datas.get(i).getDescs() + "；");
            mFavorableDatas.add(datas.get(i));
        }
        favorableContent.setText(sb.toString());
    }





    /**
     * 通过价格改变优惠信息
     *
     * @param price
     */
    private void changeFavorableInfo(double price) {
        if (mFavorableDatas.size() == 0) {
            favorableContent.setVisibility(View.GONE);
            return;
        } else {
            favorableContent.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < mFavorableDatas.size(); i++) {
            if (price >= mFavorableDatas.get(i).getTotal()) {
                favorableContent.setText(mFavorableDatas.get(i).getDescs());
            }
        }
        if (price == 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mFavorableDatas.size(); i++) {
                sb.append(mFavorableDatas.get(i).getDescs() + "；");
            }
            favorableContent.setText(sb.toString());
        }
    }


}
