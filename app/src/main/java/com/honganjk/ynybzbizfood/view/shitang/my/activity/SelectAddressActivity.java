package com.honganjk.ynybzbizfood.view.shitang.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.AddressBean;
import com.honganjk.ynybzbizfood.pressenter.shitang.my.SelectAddressPresenter;
import com.honganjk.ynybzbizfood.utils.other.SharedPreferencesUtils;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.shitang.my.interfaces.MyParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/6.
 */
public class SelectAddressActivity extends BaseListActivity<MyParentInterfaces.ISelectAddress, SelectAddressPresenter> implements MyParentInterfaces.ISelectAddress, View.OnClickListener {
    private static final String TAG = SelectAddressActivity.class.getSimpleName();
    private List<AddressBean> list = new ArrayList<>();
    @BindView(R.id.ll_addaddress)
    LinearLayout ll_addaddress;
    CommonAdapter adapter;
    private double mLatuitu;    //传过来的经纬度
    private double mLongtitu;

    /**
     * @param context
     */
    public static void startUi(Context context) {
        Intent intent = new Intent(context, SelectAddressActivity.class);
        context.startActivity(intent);
    }

    /**
     * @param activity
     */
    public static void startForResultUi(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 根据商家的位置显示可选的位置
     * @param activity
     * @param latuitu
     * @param longtitu
     * @param isShowValid
     * @param requestCode
     */
    public static void startForResultUi(Activity activity, double latuitu, double longtitu, boolean isShowValid,int requestCode) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
        intent.putExtra("latuitu", latuitu);
        intent.putExtra("longtitu", longtitu);
        intent.putExtra("isShowValid", isShowValid);
        activity.startActivityForResult(intent, requestCode);
    }

    int nWidth;

    @Override
    public void initView() {
        super.initView();
        toolbar.setTitle("选择服务地址");
        toolbar.setBack((Activity) this);
        ll_addaddress.setOnClickListener(this);
        listSwipeView.getRecyclerView().setLoadMoreEnabled(false);
        presenter.getUserAddress(true);
        nWidth = new ScreenInfoUtils().getWidth();


    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {

        adapter = new CommonAdapter<AddressBean>(this, R.layout.item_address, list) {
            @Override
            public void convert(ViewHolder holder, final AddressBean dataBean) {

                View view = holder.getView(R.id.relativeLayout);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = nWidth;
                view.setLayoutParams(params);

                holder.setText(R.id.tv_name, dataBean.name);
                int sexInt = dataBean.sex;
                String sex = "男士";
                if (sexInt == 2) {
                    sex = "女士";
                }
                holder.setText(R.id.sex_and_phone, sex + "  " + dataBean.contact);
                holder.setText(R.id.tv_address, dataBean.address);
                holder.setOnClickListener(R.id.iv_reset, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //开启修改
                        EditAddressActivity.startForResultUi(SelectAddressActivity.this, dataBean);
                    }
                });

                holder.setOnClickListener(R.id.tv_dele, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除
                        presenter.deleAddress(dataBean.id);
                    }
                });

                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        intent.putExtra("name", dataBean.getName());
                        int sexInt = dataBean.sex;
                        String sex = "男士";
                        if (sexInt == 2) {
                            sex = "女士";
                        }
                        intent.putExtra("addressid", dataBean.getId());
                        intent.putExtra("sex", sex);
                        intent.putExtra("phone", dataBean.getContact());
                        intent.putExtra("address", dataBean.getAddress());
                        intent.putExtra("addresslatu", dataBean.getLatitude());
                        intent.putExtra("addresslong", dataBean.getLongitude());
                        setResult(Activity.RESULT_OK, intent);
                        // TODO: 2017-09-08
                        SharedPreferencesUtils.setSharedPreferencesKeyAndValue(SelectAddressActivity.this,"aid","aid", dataBean.getId());
                        finish();
                    }
                });


            }

        };


        return adapter;
    }

    @Override
    public void onRefresh() {
        presenter.getUserAddress(true);

    }

    @Override
    public SelectAddressPresenter initPressenter() {
        return new SelectAddressPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_selectaddress;
    }

    @Override
    public void parseIntent(Intent intent) {
        boolean isShowValid = intent.getBooleanExtra("isShowValid", false);
        if (isShowValid) {
            mLatuitu = intent.getDoubleExtra("latuitu", 0.0);
            mLongtitu = intent.getDoubleExtra("longtitu", 0.0);
            Log.e(TAG, "parseIntent: mLatuitu" + mLatuitu);
            Log.e(TAG, "parseIntent: longtitu" + mLongtitu);
        }

    }

    @Override
    public void onLoadding() {
        presenter.getUserAddress(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getUserAddress(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getUserAddress(true);
    }

    @Override
    public void clearData() {
        list.clear();
    }

    /**
     * 服务器返回的数据
     *
     * @param list
     */
    @Override
    public void showAddress(List<AddressBean> list) {
        if (mLatuitu == 0.0 && mLongtitu == 0.0) {
            this.list.addAll(list);

        } else {
            //拿到做比较判断
            LatLng shanhu = new LatLng(mLatuitu, mLongtitu);
            for (int x = 0; x < list.size(); x++) {
                LatLng latLng = new LatLng(list.get(x).getLatitude(), list.get(x).getLongitude());
                //距离超过5千米不配送
                Log.e(TAG, "showAddress: 距离--------" + (DistanceUtil.getDistance(shanhu, latLng)));
                if (DistanceUtil.getDistance(shanhu, latLng) > 5000) {
                    list.remove(list.get(x));
                    --x;
                }
            }

            this.list.addAll(list);
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * 是否删处成功
     *
     * @param bool
     */
    @Override
    public void isDeleAddress(boolean bool) {
        presenter.getUserAddress(true);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        AddAddressActivity.startForResultUi(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (resultCode == RESULT_OK) {
                presenter.getUserAddress(true);
            }
        }
    }
}
