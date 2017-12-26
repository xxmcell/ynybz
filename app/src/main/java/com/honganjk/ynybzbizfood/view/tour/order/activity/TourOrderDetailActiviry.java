package com.honganjk.ynybzbizfood.view.tour.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.ObjsBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderBean;
import com.honganjk.ynybzbizfood.mode.javabean.tour.order.OrderDetailBean;
import com.honganjk.ynybzbizfood.pressenter.tour.order.OrderTourDetailPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.tour.detail.activity.OnLinePayActivity;
import com.honganjk.ynybzbizfood.view.tour.me.activity.CommonPassengerActivity;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;
import com.honganjk.ynybzbizfood.widget.NumberSelectRect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确认订单页面
 * Created by Administrator on 2017-11-29.
 */

public class TourOrderDetailActiviry extends BaseMvpActivity<OrderTourPresentInterface.IOrderDetailInterface, OrderTourDetailPresenter>
        implements OrderTourPresentInterface.IOrderDetailInterface, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;    //返回键
    @BindView(R.id.select_man_number)
    NumberSelectRect selectManNumber; //选择人数
    @BindView(R.id.ll_passenger_info_group)
    LinearLayout llPassengerInfoGroup;// 点击选择人数 ,动态加载旅客信息
    @BindView(R.id.tv_provider)
    TextView tvProvider;//服务商信息
    @BindView(R.id.tv_otime)
    TextView tvOtime;//出发时间
    @BindView(R.id.tv_outset)
    TextView tvOutset;//出发地点
    @BindView(R.id.tv_f_atime)
    TextView tvFAtime;//到达时间
    @BindView(R.id.tv_f_arrive)
    TextView tvFArrive;//到达地点
    @BindView(R.id.tv_f_provider)
    TextView tvFProvider; //返程服务商信息
    @BindView(R.id.tv_f_otime)
    TextView tvFOtime; //返程出发时间
    @BindView(R.id.tv_f_outset)
    TextView tvFOutset; //返程出发地点
    @BindView(R.id.tv_atime)
    TextView tvAtime; //返程服务商信息
    @BindView(R.id.tv_arrive)
    TextView tvArrive; //返程到达时间
    @BindView(R.id.tv_check_in_time)
    TextView tvCheckInTime; //入住时间 "2017-07-09入住丨2017-07-10退房"
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;  //酒店名
    @BindView(R.id.tv_hotel_desc)
    TextView tvHotelDesc;    //酒店描述信息
    @BindView(R.id.select_room_number)
    NumberSelectRect selectRoomNumber;//选择房间数
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime; //游玩时间
    @BindView(R.id.tv_ticket_name)
    TextView tvTicketName; //门票名称
    @BindView(R.id.tv_ticket_desc)
    TextView tvTicketDesc;//门票描述
    @BindView(R.id.tv_safe_name)
    TextView tvSafeName;//保险名称
    @BindView(R.id.iv_help)
    ImageView ivHelp;//帮助(保险详情)
    @BindView(R.id.iv_safe_select)
    ImageView ivSafeSelect;//选择是否购买保险
    @BindView(R.id.tv_safe_price)
    TextView tvSafePrice; //保险价格
    @BindView(R.id.tv_price)
    TextView tvPrice;   //价格
    @BindView(R.id.tv_reservation)
    TextView tvReservation; //立即预定
    @BindView(R.id.tv_mudidi)
    TextView tvMudidi;
    @BindView(R.id.tv_f_mudidi)
    TextView tvFMudidi;

    private ObjsBean objsBean;  //商品信息
    private TourDetailBean.DataBean.Formats selectFormats; //选择时间信息

    @Override
    public void clearData() {

    }

    @Override
    public void getHttpData(OrderDetailBean.Data data) {
        if (data != null) {
            initDate(data);
        }
    }

    private void initDate(OrderDetailBean.Data data) {
        //交通
        List<OrderDetailBean.Data.Traffics> trafficsList = data.getTraffics();
        for (OrderDetailBean.Data.Traffics traffics : trafficsList) {
            if (traffics.getType() == 1) {
                if (!TextUtils.isEmpty(traffics.getProvider()))
                    tvProvider.setText(traffics.getOtime());
                if (!TextUtils.isEmpty(traffics.getOtime())) tvOtime.setText(traffics.getOtime());
                if (!TextUtils.isEmpty(traffics.getOutset()))
                    tvOutset.setText(traffics.getOutset());
                if (!TextUtils.isEmpty(traffics.getAtime())) tvFAtime.setText(traffics.getAtime());
                if (!TextUtils.isEmpty(traffics.getArrive()))
                    tvFArrive.setText(traffics.getArrive());
            } else {
                if (!TextUtils.isEmpty(traffics.getProvider()))
                    tvFProvider.setText(traffics.getOtime());
                if (!TextUtils.isEmpty(traffics.getOtime())) tvFOtime.setText(traffics.getOtime());
                if (!TextUtils.isEmpty(traffics.getOutset()))
                    tvFOutset.setText(traffics.getOutset());
                if (!TextUtils.isEmpty(traffics.getAtime())) tvAtime.setText(traffics.getAtime());
                if (!TextUtils.isEmpty(traffics.getArrive()))
                    tvArrive.setText(traffics.getArrive());
            }
        }
        //旅店信息
        if (!TextUtils.isEmpty(data.getHotelName())) tvHotelName.setText(data.getHotelName());
        if (!TextUtils.isEmpty(data.getHotelDesc())) tvHotelDesc.setText(data.getHotelName());
        //门票信息
        if (!TextUtils.isEmpty(data.getTicketName())) tvTicketName.setText(data.getTicketName());
        if (!TextUtils.isEmpty(data.getTicketDesc())) tvTicketDesc.setText(data.getTicketName());
        //保险信息
        if (!TextUtils.isEmpty(data.getSafeName())) tvSafeName.setText(data.getSafeName());
        tvSafePrice.setText("￥" + data.getSafePrice() + "/人");
        safePrice = data.getSafePrice();
        refreshPrice();
    }

    public int safePrice = 0;
    public int passengerNum = 1;
    public int roomNum = 1;

    public void refreshPrice() {
        float price = Float.parseFloat(String.valueOf(selectFormats.getPrice())) * passengerNum + roomNum * selectFormats.getHotelPrice();
        if (isBuySafe) {
            price = safePrice * passengerNum + price;
        }
        tvPrice.setText("￥" + price);
    }

    @Override
    public OrderTourDetailPresenter initPressenter() {
        return new OrderTourDetailPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_tour_order_details;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.getData(objsBean.getId());
        selectManNumber.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
            @Override
            public boolean onClick(boolean addSubtract, final int content) {
                if (addSubtract) {
                    View view = View.inflate(TourOrderDetailActiviry.this, R.layout.item_passneger_info, null);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CommonPassengerActivity.startForResultUi(TourOrderDetailActiviry.this, REQUEST_CODE, content - 1);
                        }
                    });
                    llPassengerInfoGroup.addView(view);
                } else {
                    if (content > 0) {
                        if (passengerIdList.size() >= llPassengerInfoGroup.getChildCount()) {
                            passengerIdList.remove(llPassengerInfoGroup.getChildCount() - 1);
                        }
                        llPassengerInfoGroup.removeViewAt(llPassengerInfoGroup.getChildCount() - 1);
                    }
                }
                passengerNum = content;
                refreshPrice();
                return false;
            }
        });
        llPassengerInfoGroup.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonPassengerActivity.startForResultUi(TourOrderDetailActiviry.this, REQUEST_CODE, 0);
            }
        });
        selectRoomNumber.setOnClickCallback(new NumberSelectRect.OnClickCallback() {
            @Override
            public boolean onClick(boolean addSubtract, int content) {
                roomNum = content;
                refreshPrice();
                return false;
            }
        });
        ivBack.setOnClickListener(this);
        ivHelp.setOnClickListener(this);
        ivSafeSelect.setOnClickListener(this);
        tvReservation.setOnClickListener(this);
    }

    List<Integer> passengerIdList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            CommonPassengerBean commonPassengerBean = (CommonPassengerBean) data.getSerializableExtra("commonPassengerBean");
            String name =commonPassengerBean.getName();
            String mobile = commonPassengerBean.getMobile();
            String sn = commonPassengerBean.getSn();
            int id = commonPassengerBean.getId();
            int type =commonPassengerBean.getType();
            int content = data.getIntExtra("content", -1);
            if (content != -1) {
                for (Integer mId :passengerIdList){
                    if (mId==commonPassengerBean.getId()){
                        showInforSnackbar("这位旅客以存在");
                        return;
                    }
                }
                passengerIdList.add(content, id);
                View childAt = llPassengerInfoGroup.getChildAt(content);
                ((TextView) childAt.findViewById(R.id.et_name)).setText(name);
                ((TextView) childAt.findViewById(R.id.et_phone_num)).setText(mobile);
                ((TextView) childAt.findViewById(R.id.et_id_card)).setText(sn);
            }
        }
    }

    @Override
    public void parseIntent(Intent intent) {
        objsBean = (ObjsBean) intent.getSerializableExtra("objsBean");
        selectFormats = (TourDetailBean.DataBean.Formats) intent.getSerializableExtra("selectFormats");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private boolean isBuySafe = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_help:
                ToastUtils.getToastShort("保险详情");
                break;
            case R.id.iv_safe_select:
                if (isBuySafe) {
                    ivSafeSelect.setBackground(getResources().getDrawable(R.mipmap.iv_pay_choice_down));
                } else {
                    ivSafeSelect.setBackground(getResources().getDrawable(R.mipmap.iv_pay_choice_up));
                }
                isBuySafe = !isBuySafe;
                refreshPrice();
                break;
            case R.id.tv_reservation:
                onReservation();
                break;
        }
    }

    private void onReservation() {
        StringBuffer users = new StringBuffer();
        if (passengerIdList.size() == 0) {
            ToastUtils.getToastShort("请填写旅客信息");
            return;
        }
        for (int i = 0; i < passengerIdList.size(); i++) {
            if (i == passengerIdList.size() - 1) {
                users.append(passengerIdList.get(i));
            } else {
                users.append(passengerIdList.get(i) + ";");
            }
        }

        //此处下单  获取订单号  和总额  测试直接跳过
        //  presenter.createOrder(objsBean.getId(), selec;tFormats.getId(), users.toString(), passengerIdList.size(), roomNum, 0, isBuySafe ? 1 : 0);
        OnLinePayActivity.StartUi(this, 1111 + "", 1111 + "", objsBean.getTitle());
        finish();
    }

    @Override
    public void getOrder(OrderBean orderBean) {
        if (orderBean != null) {
            OnLinePayActivity.StartUi(this, orderBean.getId(), orderBean.getPrice(), objsBean.getTitle());
            finish();
        }
    }
}
