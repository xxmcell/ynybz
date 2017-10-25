package com.honganjk.ynybzbizfood.view.peihu.order.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.mode.enumeration.OrderClickStatus;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.order.NurserOrderdData;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.view.peihu.home.activity.NursingRecoveryDetailsActivity;
import com.honganjk.ynybzbizfood.view.peihu.order.fragment.OrderFragment;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
import com.honganjk.ynybzbizfood.widget.dialog.DialogSelectCause;

import java.util.List;

import static com.honganjk.ynybzbizfood.R.id.orderStatus;

/**
 * 说明:陪护-我的订单-适配器
 * 360621904@qq.com 杨阳 2017/3/24  14:17
 */
public class NurserOrderAdapter extends CommonAdapter<NurserOrderdData.ObjsBean> {
    OrderFragment mContext;

    public NurserOrderAdapter(OrderFragment context, List<NurserOrderdData.ObjsBean> datas) {
        super(context.getContext(), R.layout.item_nurser_order, datas);
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final NurserOrderdData.ObjsBean data) {
        holder.setImageBitmapRound(R.id.picture, data.getImg(), 5);
        holder.setText(R.id.name, data.getNameDetails());
        holder.setText(R.id.type, data.getTitle());
        holder.setText(R.id.text, data.getServiceTimeDetails());
        holder.setText(R.id.price, data.getMoneyStr());
        holder.setText(orderStatus, data.getStateStr());
        /**
         * 灰色按钮
         * 取消订单，删除订单
         */
        final TextView statusGray = holder.getView(R.id.cancel);
        /**
         * 绿色按钮
         * 支付金额，联系平台，去评价
         */
        final TextView statusGreen = holder.getView(R.id.status);
        View boundary1 = holder.getView(R.id.boundary1);//分割线
        TextView price = holder.getView(R.id.price);//价格
        statusGray.setVisibility(View.VISIBLE);
        statusGreen.setVisibility(View.VISIBLE);
        boundary1.setVisibility(View.VISIBLE);
        price.setVisibility(View.VISIBLE);

        /**
         *0待支付；1已接单；2在服务；3待服务介入中；4已完成；5待评价；6用户删除；7待接单；8用户取消；9护工取消；10不接单；11服务中介入
         */
        switch (data.getState()) {

            /**
             * 待服务
             */
            case 0:
            case 1:
            case 3:
            case 7:
                statusGray.setText(OrderClickStatus.QU_XIAO_DING_DAN.getValue());
                statusGreen.setText(OrderClickStatus.ZHI_FU_JIN_E.getValue());

                if (data.getState() == 1) {
                    statusGreen.setVisibility(View.GONE);

                } else if (data.getState() == 3) {
                    statusGray.setVisibility(View.GONE);
                    statusGreen.setVisibility(View.GONE);
                    boundary1.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);

                } else if (data.getState() == 7) {
                    statusGreen.setVisibility(View.GONE);

                }
                break;
            /**
             * 服务中
             */
            case 2:
                statusGreen.setText(OrderClickStatus.LIAN_XI_PING_TAI.getValue());
                statusGray.setVisibility(View.GONE);
                break;
            case 11:
                statusGreen.setVisibility(View.GONE);
                statusGray.setVisibility(View.GONE);
                boundary1.setVisibility(View.GONE);
                break;
            /**
             * 已完成
             */
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
                statusGray.setText(OrderClickStatus.SHAN_CHU_DING_DAN.getValue());
                statusGreen.setVisibility(View.GONE);
                break;
            case 5:
                statusGreen.setText(OrderClickStatus.QU_PING_JIA.getValue());
                break;
        }


        //取消订单，删除订单
        statusGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickEvent(statusGray.getText().toString(), data);
            }
        });

        //支付金额，联系平台，去评价
        statusGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickEvent(statusGreen.getText().toString(), data);
            }
        });

        //进入个人详情页
        holder.setOnClickListener(R.id.name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NursingRecoveryDetailsActivity.startUI(mContext.getContext(), data.getNid(), data.getType());
            }
        });


    }

    @Override
    public void convert(ViewHolder holder, List<NurserOrderdData.ObjsBean> t) {

    }

    /**
     * @param str 按键的点击内容
     */
    private void buttonClickEvent(String str, final NurserOrderdData.ObjsBean data) {

        //取消订单
        if (str.equals(OrderClickStatus.QU_XIAO_DING_DAN.getValue())) {
            DialogSelectCause dialog = new DialogSelectCause(mContext.getContext());
            dialog.show();
            dialog.setClickCallback(new DialogSelectCause.OnClickCallback() {
                @Override
                public void onClick(String type) {
                    mContext.presenter.cancleOrder(data.getId(), type);
                }
            });

            //删除订单
        } else if (str.equals(OrderClickStatus.SHAN_CHU_DING_DAN.getValue())) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(mContext.getContext())
                    .title("提示")
                    .positiveText("确定")
                    .negativeColorRes(R.color.gray)
                    .negativeText("取消")
                    .content("确定要删除订单吗？")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mContext.presenter.deleteOrder(data.getId());
                        }
                    })
                    .build();
            materialDialog.show();


            //支付金额(跳转到支付页面)
        } else if (str.equals(OrderClickStatus.ZHI_FU_JIN_E.getValue())) {
            OrderPayData opd = new OrderPayData(
                    data.getImg(),
                    2,
                    data.getId(),
                    data.getName(),
                    Double.parseDouble(data.getMoney()),
                    data.getServiceTime()
            );
            PayActivity.startUI(mContext.getContext(), opd);

            //联系平台
        } else if (str.equals(OrderClickStatus.LIAN_XI_PING_TAI.getValue())) {
            DeviceUtil.callByPhone(mContext.getContext(), Global.SERVER_PHONE);

            //去评价
        } else if (str.equals(OrderClickStatus.QU_PING_JIA.getValue())) {
            WritingEvaluationActivity.startUI(mContext, data.getId(), data.getImg(), mContext.REQUEST_CODE + 1);
        }
    }


}
