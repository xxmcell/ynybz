package com.honganjk.ynybzbizfood.view.shitang.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.common.BusinessDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.common.FoodData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.CreateOrderSucceedData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.FavorableData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.home.OrderCommitData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.center.DefaultAddressData;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.QueRenXiaDinDanPresenter;
import com.honganjk.ynybzbizfood.utils.animator.AnimUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.shitang.my.activity.SelectAddressActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.adapter.DindanAdapter;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.AnimCheckBox;
import com.honganjk.ynybzbizfood.widget.dialog.DialogTime2Select;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.R.id.peisong;


/**
 * 确认下单
 */
public class QueRenXiaDanActivity extends BaseMvpActivity<OrderParentInterfaces.IQueRenXiaDan, QueRenXiaDinDanPresenter> implements OrderParentInterfaces.IQueRenXiaDan {
    @BindView(R.id.tv_name)
    TextView address;
    @BindView(R.id.recy_show_dindan)
    RecyclerView recyShowDindan;
    @BindView(peisong)
    TextView theShoppingFee;
    @BindView(R.id.tv_heji)
    TextView tvHeji;
    @BindView(R.id.tv_shangjian_name)
    TextView tv_shangjian_name;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.youhui_price)
    TextView youhui_price;
    @BindView(R.id.tv_shifu)
    TextView tv_shifu;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_tianjiabeizhu)
    TextView tv_tianjiabeizhu;
    @BindView(R.id.favorableContent)
    TextView favorableContent;
    @BindView(R.id.packagingFee)
    TextView packagingFee;
    @BindView(R.id.take)
    AnimCheckBox take;
    //下单成功的id
    private int mdindainID;

    //餐饮类型，1:今天中午 2：今天晚上 3：明天中午 4：明天晚上，0：早餐
    private int timeType;
    //广播,监听支付成功
    private MyBroadcastReceiver mMyBroadcastReceiver;
    //菜品列表
    private ArrayList<FoodData.DishsBeanX.DishsBean> mOrderList;
    //商家的实体类
    private BusinessDetailsData mBizData;
    //请求的实体的类
    private OrderCommitData mRequestData = new OrderCommitData();

    /**
     * @param context
     * @param list    菜品列表
     * @param data    商家的实体类
     * @param mid     菜的id
     */
    public static void starUi(Context context, ArrayList<FoodData.DishsBeanX.DishsBean> list, BusinessDetailsData data, int mid) {
        Intent intent = new Intent(context, QueRenXiaDanActivity.class);
        intent.putParcelableArrayListExtra("dindanInfo", list);
        intent.putExtra("BusinessDetailsData", data);
        intent.putExtra("mid", mid);
        context.startActivity(intent);
    }

    @Override
    public QueRenXiaDinDanPresenter initPressenter() {
        return new QueRenXiaDinDanPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.querenxiadan;
    }

    @Override
    public void parseIntent(Intent intent) {
        mBizData = intent.getParcelableExtra("BusinessDetailsData");
        mOrderList = intent.getParcelableArrayListExtra("dindanInfo");
        if (mOrderList == null || mOrderList.size() == 0 || mBizData == null)
            finish();
        timeType = mOrderList.get(0).getTimeType();
        mRequestData.setDids(mOrderList);
        mRequestData.setPrice(presenter.getSumPrice(mOrderList));
        mRequestData.setFee(presenter.getFee(mOrderList));
        mRequestData.setMid(timeType == 0 ? -1 : intent.getIntExtra("mid", -1));

    }

    @Override
    public void initView() {
        //注册广播
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intent = new IntentFilter(Global.ST_PAY_SUCCEED);
        registerReceiver(mMyBroadcastReceiver, intent);

        toolbar.setBack(this);
        toolbar.setTitle("确认下单");
        tv_shangjian_name.setText(mBizData.getName());
        tv_type.setText(presenter.getCateringContext(timeType));
        packagingFee.setText(mRequestData.getFeeStr());
        recyShowDindan.setLayoutManager(new LinearLayoutManager(this));
        recyShowDindan.setAdapter(new DindanAdapter(this, mOrderList));
        presenter.getFavorable(mBizData.getId(), mRequestData.getPrice());
        presenter.getDefaultAddress(mBizData.getId());


        take.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
            @Override
            public void onChange(AnimCheckBox checkBox, boolean checked) {
                setPrice();
            }
        });
        setPrice();
    }

    @OnClick({R.id.re_selectaddress, R.id.ll_selecttime, R.id.add_beizhu, R.id.tv_qurenxiadian, R.id.takeIssue, R.id.theShoppingFeeIssue})
    public void onClick(View view) {
        switch (view.getId()) {
            //选择地址
            case R.id.re_selectaddress:
                SelectAddressActivity.startForResultUi(this, mBizData.getLatitude(), mBizData.getLongitude(), true, REQUEST_CODE + 1);
                break;
            //选择时间
            case R.id.ll_selecttime:
                DialogTime2Select dialogTime2Select = new DialogTime2Select(this);
                dialogTime2Select.setCallBack(new DialogTime2Select.CallBack() {
                    @Override
                    public void setData(String time, long startTime, long endTime) {
                        mRequestData.setTime(time);
                        tv_time.setText(time);
                        mRequestData.setStartTime(startTime);
                        mRequestData.setEndTime(endTime);
                    }
                });
                dialogTime2Select.show();
                dialogTime2Select.setData((mOrderList.get(0).getTimeType() + 1) / 2, mBizData.getHours(), timeType % 2 == 0 ? 2 : 1);
                break;

            //添加备注
            case R.id.add_beizhu:
                BeizhuActivity.startUiForresult(this, REQUEST_CODE, mRequestData.getRemark());
                break;
            case R.id.tv_qurenxiadian:
                //确认下订单//请求网络
                if (TextUtils.isEmpty(mRequestData.getTime())) {
                    showWarningMessage("请选择配送时间");
                    return;
                }
                if (mRequestData.getAid() == 0) {
                    showWarningMessage("请选择配送地址");
                    return;
                }
                mRequestData.setTime(mRequestData.getTime());
                mRequestData.setType(timeType == 0 ? 0 : 1);//餐饮类型，0-早餐，1-中晚餐
                mRequestData.setBid(mBizData.getId());
                mRequestData.setSelf(take.isChecked() ? 1 : 0);
                presenter.commitOrder(mRequestData);

                break;
            //自提-提示
            case R.id.takeIssue:
                presenter.setHint(this, getResources().getString(R.string.takeHint));
                break;
            //自提-提示
            case R.id.theShoppingFeeIssue:
                presenter.setHint(this, getResources().getString(R.string.theShoppingFeeHint));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            if (resultCode == RESULT_OK) {
                //地址的回调
                if (requestCode == REQUEST_CODE + 1) {
                    String name = data.getStringExtra("name");
                    String sex = data.getStringExtra("sex");
                    String phone = data.getStringExtra("phone");
                    StringBuffer sb = new StringBuffer(name + "\t" + sex + "\t" + phone);

                    mRequestData.setUserName(name);
                    mRequestData.setPhone(phone);
                    mRequestData.setAid(data.getIntExtra("addressid", 0));
                    mRequestData.setAddress(data.getStringExtra("address"));
                    address.setText(StringUtils.convertTextColor((sb.toString() + "\n" + mRequestData.getAddress()), (sb.toString()), getResources().getColor(R.color.black)));
                    //获取运费信息
                    presenter.theShoppingFee(mBizData.getId(), mRequestData.getAid());
                }
                //备注的回调
                if (requestCode == REQUEST_CODE) {
                    mRequestData.setRemark(data.getStringExtra("beizhu"));
                    tv_tianjiabeizhu.setText(mRequestData.getRemark());
                }
            }
        }
    }

    /**
     * 下单成功的回调
     *
     * @param data
     */
    @Override
    public void isXiandan(CreateOrderSucceedData data) {
        mdindainID = data.getId();
        OrderPayData orderpaydata = new OrderPayData(mBizData.getHead(), 1, data.getId(), mBizData.getName(), mRequestData.getAddress(),
                data.getPrice(), mRequestData.getUserName(), mRequestData.getPhone(), take.isChecked());
        PayActivity.startUI(this, orderpaydata);
    }

    @Override
    public void getFavorableInfo(FavorableData data) {
        favorableContent.setText(data.getDescs());
        youhui_price.setText(data.getAmountContent());
        mRequestData.setPrice(mRequestData.getPrice() - data.getAmount());
        mRequestData.setFid(data.getId());
        setPrice();
    }

    @Override
    public void getTheShoppingFee(int data) {
        theShoppingFee.setText("¥" + data);
        mRequestData.setPrice(mRequestData.getPrice() - mRequestData.getTheShoppingFee());
        mRequestData.setTheShoppingFee(data);
        mRequestData.setPrice(mRequestData.getPrice() + data);
        setPrice();
    }

    @Override
    public void getDefaultAddress(DefaultAddressData data) {
        String name = data.getName();
        String sex = data.getSexStr();
        String phone = data.getContact();
        StringBuffer sb = new StringBuffer(name + "\t" + sex + "\t" + phone);
        mRequestData.setAid(data.getId());
        mRequestData.setAddress(data.getAddress());
        address.setText(StringUtils.convertTextColor((sb.toString() + "\n" + mRequestData.getAddress()), (sb.toString()), getResources().getColor(R.color.black)));

        mRequestData.setUserName(name);
        mRequestData.setPhone(phone);
        theShoppingFee.setText("¥" + data.getFare());
        mRequestData.setPrice(mRequestData.getPrice() - mRequestData.getTheShoppingFee());
        mRequestData.setTheShoppingFee(data.getFare());
        mRequestData.setPrice(mRequestData.getPrice() + data.getFare());
        setPrice();

    }

    /**
     * 设置实付与合计价格
     */
    private void setPrice() {
        tv_shifu.setText("¥" + StringUtils.dataFilter(take.isChecked() ? mRequestData.getPrice() - mRequestData.getTheShoppingFee() + mRequestData.getFee() : mRequestData.getPrice() + mRequestData.getFee(), 2));
        tvHeji.setText("合计：¥" + StringUtils.dataFilter((take.isChecked() ? mRequestData.getPrice() - mRequestData.getTheShoppingFee() + mRequestData.getFee() : mRequestData.getPrice() + mRequestData.getFee()), 2));
        theShoppingFee.setText("¥" + StringUtils.dataFilter((take.isChecked() ? 0 : mRequestData.getTheShoppingFee()), 2));

        AnimUtils.scaleAnim(tv_shifu);
        AnimUtils.scaleAnim(tvHeji);
    }

    @Override
    public void clearData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    /**
     * 广播
     */
    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //打开订单详情页
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                OrderDetailsActivity.startUI(QueRenXiaDanActivity.this, mdindainID, mBizData.getHead());
                finish();
            }
        }
    }
}
