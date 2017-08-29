package com.honganjk.ynybzbizfood.view.store.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.PlaceTheOrderData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsTypeData;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomePayData;
import com.honganjk.ynybzbizfood.pressenter.store.home.StoreSubscribePresenter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.BeizhuActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
import com.honganjk.ynybzbizfood.view.store.home.interfaces.IHomeParentInterfaces;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城-预约下单
 */
public class StoreSubscribeActivity extends BaseMvpActivity<IHomeParentInterfaces.IStoreSubscribe, StoreSubscribePresenter>
        implements IHomeParentInterfaces.IStoreSubscribe {
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.selectNumber)
    NumberSelectRect selectNumber;
    //请求的实体对象
    private PlaceTheOrderData mData = new PlaceTheOrderData();

    private EditHelper editHelper = new EditHelper(this);
    //广播,监听支付成功
    private MyBroadcastReceiver mMyBroadcastReceiver;
    ProductDetailsTypeData mProductDetailsTypeData;

    /**
     * @param context
     */
    public static void starUi(Context context, ProductDetailsTypeData data) {
        Intent intent = new Intent(context, StoreSubscribeActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    public StoreSubscribePresenter initPressenter() {
        return new StoreSubscribePresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_subscribe;
    }

    @Override
    public void parseIntent(Intent intent) {
        mProductDetailsTypeData = intent.getParcelableExtra("data");

        if (mProductDetailsTypeData != null) {
            GlideUtils.show(picture, mProductDetailsTypeData.getImg());
            price.setText(mProductDetailsTypeData.getPriceStr());
            type.setText(mProductDetailsTypeData.getLabel());
            number.setText(mProductDetailsTypeData.getNumberStr());
            selectNumber.setSelectNum(mProductDetailsTypeData.getNumber());
            name.setText(mProductDetailsTypeData.getTitle());
            sumPrice.setText(("合计：¥" + mProductDetailsTypeData.getNumber() * mProductDetailsTypeData.getPrice()));

            mData.setFare(0);
            mData.setNum(mProductDetailsTypeData.getNumber());
            mData.setType(mProductDetailsTypeData.getType());
            mData.setBid(mProductDetailsTypeData.getId());

        }
        selectNumber.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
            @Override
            public boolean onClick(boolean addSubtract, int content) {
                mProductDetailsTypeData.setNumber(content);
                addTimeData();
                return false;
            }
        });
    }

    @Override
    public void initView() {
        //注册广播
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intent = new IntentFilter(Global.ST_PAY_SUCCEED);
        registerReceiver(mMyBroadcastReceiver, intent);
        toolbar.setBack(this);
        toolbar.setTitle("预约下单");
        presenter.getDefaultAddress();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //地址选择的回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mData.setAddress(data.getStringExtra("address"));
            mData.setName(data.getStringExtra("name"));
            mData.setSex(data.getStringExtra("sex"));
            mData.setPhone(data.getStringExtra("phone"));
            mData.setAddressid(data.getIntExtra("addressid", -1));
            address.setText(mData.getInfor(this));
        }

        //备注的回调
        if (requestCode == REQUEST_CODE + 1 && resultCode == RESULT_OK && data != null) {
            mData.setRemark(data.getStringExtra("beizhu"));
            remark.setText(mData.getRemark());
        }
    }

    @Override
    public void getDefaultAddress(DefaultAddressData data) {
        mData.setAddress(data.getAddress());
        mData.setName(data.getName());
        mData.setSex(data.getSexStr());
        mData.setPhone(data.getContact());
        mData.setAddressid(data.getId());
        address.setText(mData.getInfor(this));

    }


    @Override
    public void placeTheOrderIsSucceed(StoreHomePayData da) {


        OrderPayData data = new OrderPayData(
                mProductDetailsTypeData.getImg(),
                mProductDetailsTypeData.getTitle(),
                da.getPrice(),
                mProductDetailsTypeData.getLabel(),
               3,
                da.getId()

        );
        //跳转到支付页面
        PayActivity.startUI(this, data);
//        finish();
    }

    @Override
    public void clearData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    @OnClick({R.id.address, R.id.remarkParent, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            //地址选择
            case R.id.address:
                SelectAddressActivity.startForResultUi(this, REQUEST_CODE);
                break;

            //备注
            case R.id.remarkParent:
                BeizhuActivity.startUiForresult(this, REQUEST_CODE + 1, mData.getRemark());
                break;

            //提交
            case R.id.commit:
                if (editHelper.check()) {
                    mData.setBid(1);
                    presenter.commitOrder(mData);
                }
                break;
        }
    }

    /**
     * 价格与数量赋值
     */
    private void addTimeData() {
        number.setText(mProductDetailsTypeData.getNumberStr());
        sumPrice.setText(("合计：¥" + mProductDetailsTypeData.getNumber() * mProductDetailsTypeData.getPrice()));
        mData.setNum(mProductDetailsTypeData.getNumber());
    }

    /**
     * 广播
     */
    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 支付成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                finish();
            }
        }
    }
}
