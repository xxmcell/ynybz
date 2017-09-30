package com.honganjk.ynybzbizfood.mode.javabean.store.order;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.enumeration.StoreOrderClickStatus;
import com.honganjk.ynybzbizfood.mode.javabean.shitang.order.OrderPayData;
import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderParentPresenter;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.PayActivity;
import com.honganjk.ynybzbizfood.view.shitang.order.activity.WritingEvaluationActivity;
import com.honganjk.ynybzbizfood.widget.dialog.DialogSelectCause;

import java.util.List;

/**
 * Created by Administrator on 2017-07-12.
 * <p>
 * "total": 2,	//总数
 * "objs": [
 * {
 * "code": null,		//快递单号，当state>1时有效
 * "express": "222",		//快递名称，当state>1时有效
 * "fare": 0,		//运费
 * "feature": null,	//供货商
 * "icon": null,	//供货商图标
 * "id": 1251,		//数据id
 * "img": "https://hajk.image.alimmdn.com/bz/banner1.jpg",//商品图片
 * "money": 98,	//真实单价，总价=money*num
 * "num": 1,		//售出数量
 * "price": 218,	//参考单价
 * "state": 0,	//0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
 * "title": "Swisse 钙&维他命D片 柠檬酸钙 150片/罐"	//商品标题
 * },
 */

public class StoreOrderData2 {


    /**
     * total : 1
     * objs : [{"code":null,"details":[{"feature":"迪士尼梦幻童年","icon":"https://hajk.ima","list":[{"bid":31,"img":"https://hajk.image.alimmdn.com/dev/1501828301237","label":"5支","money":300,"num":1,"price":500,"title":"东北老家林下参地出货，特价出5组12年林下鲜参（每组含5支），每组300元包邮，送冰袋。用法：炖汤、泡酒、切片泡茶。","type":1}]}],"express":null,"fare":0,"id":1351,"price":300,"state":7}]
     */

    private int total;
    private List<ObjsBean> objs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ObjsBean> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjsBean> objs) {
        this.objs = objs;
    }

    public static class ObjsBean implements Parcelable {
        /**
         * code : null
         * details : [{"feature":"迪士尼梦幻童年","icon":"https://hajk.ima",
         * "list":[{"bid":31,"img":"https://hajk.image.alimmdn.com/dev/1501828301237",
         * "label":"5支","money":300,"num":1,"price":500,
         * "title":"东北老家林下参地出货，特价出5组12年林下鲜参（每组含5支），每组300元包邮，送冰袋。用法：炖汤、泡酒、切片泡茶。",
         * "type":1}]}]
         * express : null
         * fare : 0
         * id : 1351
         * price : 300
         * state : 7
         */

        public Object code;
        public Object express;
        public int fare;
        public int id;
        public Double price;
        public  int state;
        public List<DetailsBean> details;


        public static void buttonClickEvent(Activity context, String str,final StoreOrderData2.ObjsBean Data,
                                            final StoreOrderData2.ObjsBean.DetailsBean.ListBean data ,final StoreOrderParentPresenter presenter) {

            //取消订单
            if (str.equals(StoreOrderClickStatus.CANCLE_ORDER.getValue())) {
                DialogSelectCause dialog = new DialogSelectCause(context);
                dialog.show();
                dialog.setClickCallback(new DialogSelectCause.OnClickCallback() {
                    @Override
                    public void onClick(String type) {
                        presenter.cancleOrder(Data.getId(), type);
                    }
                });

                //删除订单
            } else if (str.equals(StoreOrderClickStatus.DELETE_ORDER.getValue())) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                        .title("提示")
                        .positiveText("确定")
                        .negativeColorRes(R.color.gray)
                        .negativeText("取消")
                        .content("确定要删除订单吗？")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                presenter.deleteOrder(Data,2);
                            }
                        })
                        .build();
                materialDialog.show();


                //支付金额(跳转到支付页面)
            } else if (str.equals(StoreOrderClickStatus.PAY_THE_AMOUNT.getValue())) {
                OrderPayData orderPayData = new OrderPayData(

                        data.getImg(),
                        data.getTitle(),
                        (data.getMoney() * data.getNum()),
                        data.getLabel(),
                        3,
                        Data.getId()

                );
                //跳转到支付页面
                PayActivity.startUI(context, orderPayData);

                //查看物流
            } else if (str.equals(StoreOrderClickStatus.CHECK_THE_LOGISTICS.getValue())) {
                Uri uri = Uri.parse("https://m.kuaidi100.com/result.jsp?com=zhongtong&nu=445034874095");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);

                //去评价
            } else if (str.equals(StoreOrderClickStatus.EVALUATION.getValue())) {
                WritingEvaluationActivity.startUI(context, Data.getId(), data.getImg(), presenter.getRequestCode());

            } else if (str.equals(StoreOrderClickStatus.CONFIRM_THE_GOODS.getValue())) {
                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                        .title("提示")
                        .positiveText("确定")
                        .negativeColorRes(R.color.gray)
                        .negativeText("取消")
                        .content("确定收到货了吗？")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                presenter.deleteOrder(Data,2);
                            }
                        })
                        .build();
                materialDialog.show();
            }
        }


        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public Object getExpress() {
            return express;
        }

        public void setExpress(Object express) {
            this.express = express;
        }

        public int getFare() {
            return fare;
        }

        public void setFare(int fare) {
            this.fare = fare;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        /**
         *
         *     "state": 0,	//状态，0-待付款，
         *     1-待发货，2-待收货，
         *     3-待评价，4-正常结束
         *     ，5-待发货申请退款，6-待收货申请退款，
         *     7-退款中，8-退款完成，9-待收货申请退货，
         *     12-退货中待买家发货，13-退货中-待卖家收货，
         *     14-退货退款完成，15-拒绝退款-待发货，16-拒绝退款-待收货，
         *     17-拒绝退货-待收货，20-待发货-取消退款，21-待收货-取消退款，
         *     22-待收货取消退货，25-退货退款中
         */
        public String getStateStr() {
            String str = "未知";
            switch (state) {
                case 0:
                    str = "待付款";
                    break;
                case 1:
                    str = "等待商家发货";
                    break;
                case 2:
                    str = "待收货";
                    break;
                case 3:
                    str = "待评价";
                    break;
                case 4:
                    str = "已完成";
                    break;
                case 5:
                    str="待发货申请退款";
                    break;
                case 6:
                    str="待收货申请退款";
                    break;
                case 7:
                    str="退款中";
                    break;
                case 8:
                    str="退款完成";
                    break;
                case 9:
                    str="待收货申请退货";
                    break;
                case 12:
                    str="退货中待买家发货";
                    break;
                case 13:
                    str="退货中-待卖家收货";
                    break;
                case 14:
                    str="退货退款完成";
                    break;
                case 15:
                    str="拒绝退款-待发货";
                    break;
                case 16:
                    str="拒绝退款-待收货";
                    break;
                case 17:
                    str="拒绝退货-待收货";
                    break;
                case 20:
                    str="待发货-取消退款";
                    break;
                case 21:
                    str="待收货-取消退款";
                    break;
                case 22:
                    str="待收货取消退货";
                    break;
                case 25:
                    str="退货退款中";
                    break;
            }
            return str;
        }
        public void setViewShowStatus(@Nullable TextView statusGray, @Nullable TextView statusGreen, View boundary1) {

            /**
             *
             *     "state": 0,	//状态，0-待付款，
             *     1-待发货，2-待收货，
             *     3-待评价，4-正常结束
             *     ，5-待发货申请退款，6-待收货申请退款，
             *     7-退款中，8-退款完成，9-待收货申请退货，
             *     12-退货中待买家发货，13-退货中-待卖家收货，
             *     14-退货退款完成，15-拒绝退款-待发货，16-拒绝退款-待收货，
             *     17-拒绝退货-待收货，20-待发货-取消退款，21-待收货-取消退款，
             *     22-待收货取消退货，25-退货退款中
             */
            switch (state) {
                /**
                 * 待付款
                 */
                case 0:
                    statusGray.setText(StoreOrderClickStatus.CANCLE_ORDER.getValue());
                    statusGreen.setText(StoreOrderClickStatus.PAY_THE_AMOUNT.getValue());
                    break;
                /**
                 * 待发货
                 */
                case 1:
                    statusGray.setVisibility(View.GONE);
                    statusGreen.setVisibility(View.GONE);
                    boundary1.setVisibility(View.GONE);
                    break;
                /**
                 * 待收货
                 */
                case 2:
                    statusGray.setText(StoreOrderClickStatus.CHECK_THE_LOGISTICS.getValue());
                    statusGreen.setText(StoreOrderClickStatus.CONFIRM_THE_GOODS.getValue());

                    break;
                /**
                 * 待评价
                 */
                case 3:
                    statusGray.setText(StoreOrderClickStatus.DELETE_ORDER.getValue());
                    statusGreen.setText(StoreOrderClickStatus.EVALUATION.getValue());

                    break;
                /**
                 * 正常结束
                 */
                case 4:
                    statusGray.setText(StoreOrderClickStatus.DELETE_ORDER.getValue());
                    statusGray.setVisibility(View.GONE);
                    break;
            }

        }
        /**
         *            订单的状态
         *            0-待付款，1-待发货，2-待收货，3-待评价，4-正常结束
         *            对应的操作
         *            取消订单,支付金额，确认收货，查看物流，去评价，删除订单
         */


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(fare);
            parcel.writeInt(id);
            parcel.writeDouble(price);
            parcel.writeInt(state);
        }
        public ObjsBean() {
        }

        protected ObjsBean(Parcel in) {
            this.code = in.readString();
            this.express = in.readString();
            this.fare = in.readInt();
            this.price = in.readDouble();
            this.state = in.readInt();

        }

        public static final Parcelable.Creator<ObjsBean> CREATOR = new Parcelable.Creator<ObjsBean>() {
            @Override
            public ObjsBean createFromParcel(Parcel source) {
                return new ObjsBean(source);
            }

            @Override
            public ObjsBean[] newArray(int size) {
                return new ObjsBean[size];
            }
        };
        public static class DetailsBean implements Parcelable{
            /**
             * feature : 迪士尼梦幻童年
             * icon : https://hajk.ima
             * list : [{"bid":31,"img":"https://hajk.image.alimmdn.com/dev/1501828301237",
             * "label":"5支",
             * "money":300,"num":1,"price":500,
             * "title":"东北老家林下参地出货，特价出5组12年林下鲜参（每组含5支），每组300元包邮，送冰袋。用法：炖汤、泡酒、切片泡茶。",
             * "type":1}
             * ]
             */

            public String feature;
            public String icon;
            public String express;
            public int id;
            public int fare;
            public double price;
            public int state;

            public String getExpress() {
                return express;
            }

            public void setExpress(String express) {
                this.express = express;
            }

            public int getFare() {
                return fare;
            }

            public void setFare(int fare) {
                this.fare = fare;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public static Creator<DetailsBean> getCREATOR() {
                return CREATOR;
            }


            public List<ListBean> list;

            public DetailsBean(Parcel in) {
                feature = in.readString();
                icon = in.readString();
            }

            public static final Creator<DetailsBean> CREATOR = new Creator<DetailsBean>() {
                @Override
                public DetailsBean createFromParcel(Parcel in) {
                    return new DetailsBean(in);
                }

                @Override
                public DetailsBean[] newArray(int size) {
                    return new DetailsBean[size];
                }
            };

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(feature);
                parcel.writeString(icon);


            }

            public static class ListBean implements Parcelable{
                /**
                 * bid : 31
                 * img : https://hajk.image.alimmdn.com/dev/1501828301237
                 * label : 5支
                 * money : 300
                 * num : 1
                 * price : 500
                 * title : 东北老家林下参地出货，特价出5组12年林下鲜参（每组含5支），每组300元包邮，送冰袋。用法：炖汤、泡酒、切片泡茶。
                 * type : 1
                 */

                public int bid;
                public String img;
                public String label;
                public int money;
                public int num;
                public int price;
                public String title;
                public int type;




                protected ListBean(Parcel in) {
                    bid = in.readInt();
                    img = in.readString();
                    label = in.readString();
                    money = in.readInt();
                    num = in.readInt();
                    price = in.readInt();
                    title = in.readString();
                    type = in.readInt();
                }

                public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                    @Override
                    public ListBean createFromParcel(Parcel in) {
                        return new ListBean(in);
                    }

                    @Override
                    public ListBean[] newArray(int size) {
                        return new ListBean[size];
                    }
                };

                public int getBid() {
                    return bid;
                }

                public void setBid(int bid) {
                    this.bid = bid;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public int getMoney() {
                    return money;
                }

                public void setMoney(int money) {
                    this.money = money;
                }
                public String getMoneyStr() {
                    return "¥" + money;
                }

                public String getSumMoneyStr() {
                    StringBuffer sb = new StringBuffer();
                    sb.append("共").append(num).append("件商品 ").append("合计:¥").append(money * num).append("含运费(¥0.00)");
                    return sb.toString();
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getNumStr() {
                    return "X" + num;
                }


                public void getPriceStr(TextView textView) {
                    textView.setText("¥" + price);
                    StringUtils.convertToFlags(textView);
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeInt(bid);
                    parcel.writeString(img);
                    parcel.writeString(label);
                    parcel.writeInt(money);
                    parcel.writeInt(num);
                    parcel.writeInt(price);
                    parcel.writeString(title);
                    parcel.writeInt(type);
                }
            }
        }
    }
}
