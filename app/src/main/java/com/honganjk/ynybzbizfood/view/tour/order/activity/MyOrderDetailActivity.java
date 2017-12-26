package com.honganjk.ynybzbizfood.view.tour.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.MyOrderDetailBean;
import com.honganjk.ynybzbizfood.pressenter.tour.order.MyOrderDetailPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.DetailActivity;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.OnLinePayActivity;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;
import com.honganjk.ynybzbizfood.widget.dialog.tour.DialogTourPriceDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单详情
 * Created by Administrator on 2017-12-13.
 */

public class MyOrderDetailActivity extends BaseMvpActivity<OrderTourPresentInterface.MyOrderDetailInterface, MyOrderDetailPresenter>
        implements OrderTourPresentInterface.MyOrderDetailInterface {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.iv_to_tour_detail)
    ImageView ivToTourDetail;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_price_detail)
    TextView tvOrderPriceDetail;
    @BindView(R.id.tv_outset_time)
    TextView tvOutsetTime;
    @BindView(R.id.tv_return_time)
    TextView tvReturnTime;
    @BindView(R.id.tv_passenger_num)
    TextView tvPassengerNum;
    @BindView(R.id.tv_provider)
    TextView tvProvider;
    @BindView(R.id.tv_otime)
    TextView tvOtime;
    @BindView(R.id.tv_outset)
    TextView tvOutset;
    @BindView(R.id.tv_mudidi)
    TextView tvMudidi;
    @BindView(R.id.tv_f_atime)
    TextView tvFAtime;
    @BindView(R.id.tv_f_arrive)
    TextView tvFArrive;
    @BindView(R.id.tv_f_provider)
    TextView tvFProvider;
    @BindView(R.id.tv_f_otime)
    TextView tvFOtime;
    @BindView(R.id.tv_f_outset)
    TextView tvFOutset;
    @BindView(R.id.tv_f_mudidi)
    TextView tvFMudidi;
    @BindView(R.id.tv_atime)
    TextView tvAtime;
    @BindView(R.id.tv_arrive)
    TextView tvArrive;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_hotel_desc)
    TextView tvHotelDesc;
    @BindView(R.id.tv_room_count_and_time)
    TextView tvRoomCountAndTime;
    @BindView(R.id.tv_safe_name)
    TextView tvSafeName;
    @BindView(R.id.safe_num)
    TextView safeNum;
    @BindView(R.id.tv_passenger_name)
    TextView tvPassengerName;
    @BindView(R.id.tv_passenger_sex)
    TextView tvPassengerSex;
    @BindView(R.id.tv_passenger_nation)
    TextView tvPassengerNation;
    @BindView(R.id.tv_passenger_mobile)
    TextView tvPassengerMobile;
    @BindView(R.id.tv_passenger_sn)
    TextView tvPassengerSn;
    @BindView(R.id.tv_passenger_birthday)
    TextView tvPassengerBirthday;
    @BindView(R.id.ll_passenger_info_group)
    LinearLayout llPassengerInfoGroup;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_to_pay)
    TextView tvToPay;
    private int id;
    private MyOrderDetailBean mDetailBean;
    private int position;

    /**
     * @param id 订单id
     */
    public static void startUi(Context context, int id,int position) {
        Intent intent = new Intent(context, MyOrderDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    @Override
    public void clearData() {

    }

    @Override
    public void getOrderDetail(MyOrderDetailBean detailBean) {
        if (detailBean!=null){
            mDetailBean = detailBean;
        }else{
            mDetailBean = new MyOrderDetailBean();
            mDetailBean.setTid(56);
            mDetailBean.setSn("222");
            mDetailBean.setTitle("测试订单");
            mDetailBean.setPrice(66.66);
            mDetailBean.setState(position);
        }
        init(mDetailBean);
    }

    /**
     *   //状态，0-待付款，1-待出行，2-待评价，3-已完成，4-退款中，5-退款完成，6-退款被拒
     * @param detailBean
     */
    private void init(MyOrderDetailBean detailBean){
        switch (detailBean.getState()){
            case 0:
                tvState.setText("待付款");
                tvToPay.setText("去支付");
                break;
            case 1:
                tvState.setText("待出行");
                tvToPay.setText("待出行");
                break;
            case 2:
                tvState.setText("待评价");
                tvToPay.setText("去评价");
                break;
            case 3:
                tvState.setText("已完成");
                tvToPay.setText("去评价");
                break;
            case 4:
                tvState.setText("退款中");
                tvToPay.setText("退款中");
                break;
            case 5:
                tvState.setText("退款完成");
                tvToPay.setText("去评价");
                break;
            case 6:
                tvState.setText("退款被拒");
                tvToPay.setText("退款被拒");
                break;
        }
    }


    @Override
    public MyOrderDetailPresenter initPressenter() {
        return new MyOrderDetailPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_my_order_detail;
    }

    @Override
    public void initView() {
        presenter.getOrderDetail(id);

    }

    @Override
    public void parseIntent(Intent intent) {
        id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_to_tour_detail, R.id.tv_to_pay,R.id.tv_order_price_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_to_tour_detail:
                //跳转旅游详情
                DetailActivity.startUi(this,mDetailBean.getTid());
                break;
            case R.id.tv_to_pay: //状态，0-待付款，1-待出行，2-待评价，3-已完成，4-退款中，5-退款完成，6-退款被拒
                //支付
                onTvToPayClick();
                break;
            case R.id.tv_order_price_detail:
                //费用明细
                showPriceDetail();
                break;
        }
    }

    private void onTvToPayClick() {
        switch (mDetailBean.getState()){
            case 0:
                OnLinePayActivity.StartUi(this,String.valueOf(mDetailBean.getSn()),String.valueOf(mDetailBean.getPrice()),mDetailBean.getTitle());
                break;
            case 1:
                ToastUtils.getToastShort("待出行");
                break;
            case 2://去评价界面
                EditEvaluationActivity.startUI(this,id,"https://hajk.image.alimmdn.com/dev/1504162230989",REQUEST_CODE);
                break;
            case 3://去评价界面
                EditEvaluationActivity.startUI(this,id,"https://hajk.image.alimmdn.com/dev/1504162230989",REQUEST_CODE);
                break;
            case 4:
                ToastUtils.getToastShort("退款中");
                break;
            case 5://去评价界面
                EditEvaluationActivity.startUI(this,id,"https://hajk.image.alimmdn.com/dev/1504162230989",REQUEST_CODE);
                break;
            case 6:
                ToastUtils.getToastShort("退款被拒");
                break;
        }
    }

    private void showPriceDetail() {
        DialogTourPriceDetail dialog = new DialogTourPriceDetail(this,1980,50,800,1130);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                break;
        }
    }
}
