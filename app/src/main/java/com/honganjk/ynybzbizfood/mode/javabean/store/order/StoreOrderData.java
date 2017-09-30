//package com.honganjk.ynybzbizfood.mode.javabean.store.order;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.TextView;
//
//import com.afollestad.materialdialogs.DialogAction;
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.honganjk.ynybzbizfood.R;
//import com.honganjk.ynybzbizfood.mode.enumeration.StoreOrderClickStatus;
//import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
//import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderParentPresenter;
//import com.honganjk.ynybzbizfood.utils.other.StringUtils;
//import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
//import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
//import com.honganjk.ynybzbizfood.widget.dialog.DialogSelectCause;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2017-07-12.
// * <p>
// * "total": 2,	//总数
// * "objs": [
// * {
// * "code": null,		//快递单号，当state>1时有效
// * "express": "222",		//快递名称，当state>1时有效
// * "fare": 0,		//运费
// * "feature": null,	//供货商
// * "icon": null,	//供货商图标
// * "id": 1251,		//数据id
// * "img": "https://hajk.image.alimmdn.com/bz/banner1.jpg",//商品图片
// * "money": 98,	//真实单价，总价=money*num
// * "num": 1,		//售出数量
// * "price": 218,	//参考单价
// * "state": 0,	//0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
// * "title": "Swisse 钙&维他命D片 柠檬酸钙 150片/罐"	//商品标题
// * },
// */
//
//public class StoreOrderData {
//
//
//    private int total;
//    private List<ObjsBean> objs;
//
//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }
//
//    public List<ObjsBean> getObjs() {
//        return objs;
//    }
//
//    public void setObjs(List<ObjsBean> objs) {
//        this.objs = objs;
//    }
//
//    public static class ObjsBean implements Parcelable {
//
//
//        /**
//         * code : null
//         * express : null
//         * fare : 0
//         * feature : GNC
//         * icon : https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099
//         * id : 1275
//         * img : https://hajk.image.alimmdn.com/bz/banner1.jpg?t=1499067204099
//         * money : 190.0
//         * num : 1
//         * price : 218.0
//         * state : 0
//         * title : Swisse 钙&维他命D片 柠檬酸钙 150片/罐
//         * <p>
//         * <p>
//         * <p>
//         * <p>
//         * <p>
//         * <p>
//         * "bid":31,//**
//         * "img":"https://hajk.image.alimmdn.com/dev/1501828301237",
//         * "label":"5支",//**
//         * "money":300,
//         * "num":1,
//         * "price":500,
//         * "title":"东北老家林下参地出货，特价出5组12年林下鲜参（每组含5支），每组300元包邮，送冰袋。用法：炖汤、泡酒、切片泡茶。",
//         * "type":1//***
//         */
//
//
//        private String code;
//        private String express;
//        private int fare;
//        private String feature;
//        private String icon;
//        private int id;
//        private String img;
//        private double money;
//        private int num;
//        private double price;
//        private int state;
//        private String title;
//        private String label;
//
//        public String getLabel() {
//            return StringUtils.dataFilter(label);
//        }
//
//        public void setLabel(String label) {
//            this.label = label;
//        }
//
//        public Object getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public Object getExpress() {
//            return express;
//        }
//
//        public void setExpress(String express) {
//            this.express = express;
//        }
//
//        public int getFare() {
//            return fare;
//        }
//
//        public void setFare(int fare) {
//            this.fare = fare;
//        }
//
//        public String getFeature() {
//            return StringUtils.dataFilter(feature);
//        }
//
//        public void setFeature(String feature) {
//            this.feature = feature;
//        }
//
//        public String getIcon() {
//            return StringUtils.dataFilter(icon);
//        }
//
//        public void setIcon(String icon) {
//            this.icon = icon;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getImg() {
//            return StringUtils.dataFilter(img);
//        }
//
//        public void setImg(String img) {
//            this.img = img;
//        }
//
//        public double getMoney() {
//            return money;
//        }
//
//        public String getMoneyStr() {
//            return "¥" + money;
//        }
//
//        public String getSumMoneyStr() {
//            StringBuffer sb = new StringBuffer();
//            sb.append("共").append(num).append("件商品 ").append("合计:¥").append(money * num).append("含运费(¥0.00)");
//            return sb.toString();
//        }
//
//        public void setMoney(double money) {
//            this.money = money;
//        }
//
//        public int getNum() {
//            return num;
//        }
//
//        public void setNum(int num) {
//            this.num = num;
//        }
//
//        public String getNumStr() {
//            return "X" + num;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void getPriceStr(TextView textView) {
//            textView.setText("¥" + price);
//            StringUtils.convertToFlags(textView);
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public int getState() {
//            return state;
//        }
//
//        /**
//         * //0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
//         *
//         * @return
//         */
//        public String getStateStr() {
//            String str = "未知";
//            switch (state) {
//                case 0:
//                    str = "待付款";
//                    break;
//                case 1:
//                    str = "等待商家发货";
//                    break;
//                case 2:
//                    str = "确认收货";
//                    break;
//                case 3:
//                    str = "待评价";
//                    break;
//                case 4:
//                    str = "已完成";
//                    break;
//
//            }
//            return str;
//        }
//
//
//        public void setState(int state) {
//            this.state = state;
//        }
//
//        public String getTitle() {
//            return StringUtils.dataFilter(title);
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        /**
//         * 按钮的显示
//         *
//         * @param statusGray
//         * @param statusGreen
//         * @param boundary1
//         */
//        public void setViewShowStatus(@Nullable TextView statusGray, @Nullable TextView statusGreen, View boundary1) {
//
//            /**
//             * //0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
//             */
//            switch (state) {
//                /**
//                 * 待付款
//                 */
//                case 0:
//                    statusGray.setText(StoreOrderClickStatus.CANCLE_ORDER.getValue());
//                    statusGreen.setText(StoreOrderClickStatus.PAY_THE_AMOUNT.getValue());
//                    break;
//                /**
//                 * 待发货
//                 */
//                case 1:
//                    statusGray.setVisibility(View.GONE);
//                    statusGreen.setVisibility(View.GONE);
//                    boundary1.setVisibility(View.GONE);
//                    break;
//                /**
//                 * 待收货
//                 */
//                case 2:
//                    statusGray.setText(StoreOrderClickStatus.CHECK_THE_LOGISTICS.getValue());
//                    statusGreen.setText(StoreOrderClickStatus.CONFIRM_THE_GOODS.getValue());
//
//                    break;
//                /**
//                 * 待评价
//                 */
//                case 3:
//                    statusGray.setText(StoreOrderClickStatus.DELETE_ORDER.getValue());
//                    statusGreen.setText(StoreOrderClickStatus.EVALUATION.getValue());
//
//                    break;
//                /**
//                 * 正常结束
//                 */
//                case 4:
//                    statusGray.setText(StoreOrderClickStatus.DELETE_ORDER.getValue());
//                    statusGray.setVisibility(View.GONE);
//                    break;
//            }
//
//        }
//
//
//        /**
//         * @param str 按键的点击内容
//         *            订单的状态
//         *            0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
//         *            对应的操作
//         *            取消订单,支付金额，确认收货，查看物流，去评价，删除订单
//         */
//        public void buttonClickEvent(Activity context, String str, final StoreOrderData.ObjsBean data, final StoreOrderParentPresenter presenter) {
//
//            //取消订单
//            if (str.equals(StoreOrderClickStatus.CANCLE_ORDER.getValue())) {
//                DialogSelectCause dialog = new DialogSelectCause(context);
//                dialog.show();
//                dialog.setClickCallback(new DialogSelectCause.OnClickCallback() {
//                    @Override
//                    public void onClick(String type) {
//                        presenter.cancleOrder(data.getId(), type);
//                    }
//                });
//
//                //删除订单
//            } else if (str.equals(StoreOrderClickStatus.DELETE_ORDER.getValue())) {
//                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
//                        .title("提示")
//                        .positiveText("确定")
//                        .negativeColorRes(R.color.gray)
//                        .negativeText("取消")
//                        .content("确定要删除订单吗？")
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//
//                            }
//                        })
//                        .build();
//                materialDialog.show();
//
//
//                //支付金额(跳转到支付页面)
//            } else if (str.equals(StoreOrderClickStatus.PAY_THE_AMOUNT.getValue())) {
//                OrderPayData orderPayData = new OrderPayData(
//                        data.getImg(),
//                        data.getTitle(),
//                        (data.getMoney() * data.getNum()),
//                        data.getLabel(),
//                        3,
//                        data.getId()
//
//                );
//                //跳转到支付页面
//                PayActivity.startUI(context, orderPayData);
//
//                //查看物流
//            } else if (str.equals(StoreOrderClickStatus.CHECK_THE_LOGISTICS.getValue())) {
//                Uri uri = Uri.parse("https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                context.startActivity(intent);
//
//                //去评价
//            } else if (str.equals(StoreOrderClickStatus.EVALUATION.getValue())) {
//                WritingEvaluationActivity.startUI(context, data.getId(), data.getImg(), presenter.getRequestCode());
//
//            } else if (str.equals(StoreOrderClickStatus.CONFIRM_THE_GOODS.getValue())) {
//                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
//                        .title("提示")
//                        .positiveText("确定")
//                        .negativeColorRes(R.color.gray)
//                        .negativeText("取消")
//                        .content("确定收到货了吗？")
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                                presenter.deleteOrder(data.getId());
//                            }
//                        })
//                        .build();
//                materialDialog.show();
//            }
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(this.code);
//            dest.writeString(this.express);
//            dest.writeInt(this.fare);
//            dest.writeString(this.feature);
//            dest.writeString(this.icon);
//            dest.writeInt(this.id);
//            dest.writeString(this.img);
//            dest.writeDouble(this.money);
//            dest.writeInt(this.num);
//            dest.writeDouble(this.price);
//            dest.writeInt(this.state);
//            dest.writeString(this.title);
//            dest.writeString(this.label);
//        }
//
//        public ObjsBean() {
//        }
//
//        protected ObjsBean(Parcel in) {
//            this.code = in.readString();
//            this.express = in.readString();
//            this.fare = in.readInt();
//            this.feature = in.readString();
//            this.icon = in.readString();
//            this.id = in.readInt();
//            this.img = in.readString();
//            this.money = in.readDouble();
//            this.num = in.readInt();
//            this.price = in.readDouble();
//            this.state = in.readInt();
//            this.title = in.readString();
//            this.label = in.readString();
//        }
//
//        public static final Parcelable.Creator<ObjsBean> CREATOR = new Parcelable.Creator<ObjsBean>() {
//            @Override
//            public ObjsBean createFromParcel(Parcel source) {
//                return new ObjsBean(source);
//            }
//
//            @Override
//            public ObjsBean[] newArray(int size) {
//                return new ObjsBean[size];
//            }
//        };
//    }
//}
