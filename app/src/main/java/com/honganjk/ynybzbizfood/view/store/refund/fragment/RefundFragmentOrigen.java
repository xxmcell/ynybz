//package com.honganjk.ynybzbizfood.view.store.refund.fragment;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.honganjk.ynybzbizfood.R;
//import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
//import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
//import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
//import com.honganjk.ynybzbizfood.utils.other.EditHelper;
//import com.honganjk.ynybzbizfood.utils.other.Validators;
//import com.honganjk.ynybzbizfood.view.store.refund.activity.RefundActivity;
//import com.honganjk.ynybzbizfood.widget.PopupPulldown;
//import com.honganjk.ynybzbizfood.widget.button.FlatButton;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//
///**
// * 说明:产品详情的-评价
// * 作者： 杨阳; 创建于：  2017-07-07  16:01
// */
//public class RefundFragmentOrigen extends BaseFragment {
//
//    int mType;
//    int mid;
//
//    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>();
//    ArrayList<PopupPulldown.PullDownData> mTypeDatas = new ArrayList<>();
//    ArrayList<PopupPulldown.PullDownData> companyName = new ArrayList<>();
//    PopupPulldown ppCompanyName;
//    PopupPulldown pp;
//    PopupPulldown ppType;
//    Unbinder unbinder;
//    RefundActivity mActivity;
//    RefundRequestData requestData;
//    StoreOrderData2.ObjsBean.DetailsBean.ListBean mData;
//    public EditHelper editHelper;
//
//
//    int mReason;
//    int theType;
//    @BindView(R.id.type)
//    TextView type;
//    @BindView(R.id.llType)
//    LinearLayout llType;
//    @BindView(R.id.cause)
//    TextView cause;
//    @BindView(R.id.llCause)
//    LinearLayout llCause;
//    @BindView(R.id.price)
//    EditText price;
//    @BindView(R.id.remark)
//    EditText remark;
//    @BindView(R.id.commit)
//    FlatButton commit;
//    @BindView(R.id.RelativeLayoutfund)
//    RelativeLayout RelativeLayoutfund;
//    @BindView(R.id.state)
//    TextView state;
//    @BindView(R.id.theTime)
//    TextView theTime;
//    @BindView(R.id.contants)
//    LinearLayout contants;
//    @BindView(R.id.linearLayout2)
//    LinearLayout linearLayout2;
//    @BindView(R.id.textViewType)
//    TextView textViewType;
//    @BindView(R.id.textViewSum)
//    TextView textViewSum;
//    @BindView(R.id.textviewreson)
//    TextView textviewreson;
//    @BindView(R.id.refundinformation)
//    LinearLayout refundinformation;
//    @BindView(R.id.textlogistics)
//    TextView textlogistics;
//    @BindView(R.id.textviewbill)
//    TextView textviewbill;
//    @BindView(R.id.sure)
//    Button sure;
//    @BindView(R.id.flowinformation)
//    LinearLayout flowinformation;
//    @BindView(R.id.cancel)
//    Button cancel;
//    @BindView(R.id.ensure)
//    Button ensure;
//    @BindView(R.id.RelativeLayoutflow)
//    RelativeLayout RelativeLayoutflow;
//
//    @BindView(R.id.imageView2)
//    ImageView imageView2;
//    @BindView(R.id.flowName)
//    TextView flowName;
//    @BindView(R.id.textfirstline1)
//    TextView textfirstline1;
//    @BindView(R.id.textfirstline2)
//    TextView textfirstline2;
//    @BindView(R.id.textsecondline1)
//    TextView textsecondline1;
//    @BindView(R.id.textsecondline2)
//    TextView textsecondline2;
//    @BindView(R.id.textthirdline1)
//    TextView textthirdline1;
//    @BindView(R.id.textthirdline2)
//    TextView textthirdline2;
//    @BindView(R.id.titel)
//    TextView titel;
//    @BindView(R.id.flowCNeditText)
//    EditText flowCNeditText;
//    @BindView(R.id.llselectCN)
//    LinearLayout llselectCN;
//    @BindView(R.id.flowCNnumbedittext)
//    EditText flowCNnumbedittext;
//
//
//    private double p;
//    private int stats;
//    private String jsonStr;
//    private StoreOrderData2.ObjsBean data;
//    private String flowCNtext;
//    private String flowCNnumbtext;
//    private View rootView;
//
//
//    public RefundFragmentOrigen() {
//    }
//
//    /**
//     *
//     */
//    @SuppressLint("ValidFragment")
//    public RefundFragmentOrigen(int i, String json, RefundRequestData mRefunddatas) {
//        this.mType = i;
//        this.jsonStr = json;
//        this.requestData = mRefunddatas;
//    }
//
//    public static RefundFragmentOrigen getInstance(int i, String json, RefundRequestData mRefunddatas) {
//        return new RefundFragmentOrigen(i, json, mRefunddatas);
//    }
//
//    @Override
//    public int getContentView() {
//        return R.layout.store_fragment_refund;
//    }
//
//
//    @Override
//    public void initView() {
//        Gson gson = new Gson();
//        data = gson.fromJson(jsonStr, StoreOrderData2.ObjsBean.class);
//            mType=0;
//        if (mType == 0) {
//            titel.setText("仅退款");
//            llType.setVisibility(View.GONE);
//
//        } else if (mType == 1) {
//            titel.setText("退钱退款");
//        }
//        stats = data.getState();
//        initpopupwindow();
//        initrefundBill();
//
//    }
//
//    private void initrefundBill() {
//        //一进来,获得数据,看是否符合
//        if (stats != 5 || stats != 6 || stats != 9 || stats != 7 ||
//                stats != 20 || stats != 15 || stats != 16 || stats != 17 ||
//                stats != 12 || stats != 13 || stats != 8 || stats != 14) {
//            RelativeLayoutflow.setVisibility(View.GONE);
//        }
//
//        //获得时间差
//        long time = requestData.getTime() - requestData.getCurrent();
//
//
//        long days = time / (1000 * 60 * 60 * 24);
//        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//
//        if (stats == 5 || stats == 6 || stats == 9) {
//            //等待处理
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//            flowinformation.setVisibility(View.GONE);
//            textViewType.setText("仅退款");
//            textViewSum.setText("￥" + mData.getPrice());
//            textfirstline1.setText("如果商家同意:");
//
//            textfirstline2.setText("申请将达成,原路返回您的退款");
//
//            textsecondline1.setText("如果商家拒绝");
//
//            textsecondline2.setText("您可以修改退款申请,或者申请服务介入");
//
//            textthirdline1.setText("如果商家未处理");
//
//            textthirdline2.setText("超过2天申请达成并为您退款");
//
//        } else if (stats == 7 || stats == 20) {
//            //商家处理中
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//            flowinformation.setVisibility(View.GONE);
//            textfirstline1.setText("卖家已经收到您的退款商品,退款将于2个工作人原路退回您的账户,请您注意查收");
//
//            textViewType.setText("仅退款");
//            textViewSum.setText("￥" + mData.getPrice());
//
//        } else if (stats == 15 || stats == 16 || stats == 17) {
//            //商家拒绝
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//            flowinformation.setVisibility(View.GONE);
//            theTime.setText("自动确认收货时间:剩余" + days + "天" + hours + "小时");
//            theTime.setTextColor(R.color.blue);
//            textViewType.setText("仅退款");
//            textViewSum.setText("￥" + mData.getPrice());
//            textfirstline1.setText("商家拒绝了您的退款申请,您可以修改退款申请,或者申请客服介入");
//
//        } else if (stats == 12) {
//            //商家同意
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//            theTime.setText("自动确认收货时间:剩余" + days + "天" + hours + "小时");
//            theTime.setTextColor(R.color.blue);
//            textViewType.setText("仅退款");
//            textViewSum.setText("￥" + mData.getPrice());
//            textfirstline1.setText("商家已经同意您的退款申请,请尽快退回商品");
//            flowName.setText("物流信息");
//            flowName.setTextColor(R.color.black);
//
//            //物流公司名称
//            flowCNtext = flowCNeditText.getText().toString().trim();
//            //快递单号
//            flowCNnumbtext = flowCNnumbedittext.getText().toString().trim();
//
//        } else if (stats == 13) {
//            //物流中
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//
//            textfirstline1.setText("物流退货中,请耐心等待.若商家超出10天23小时未进行处理,系统将自动退回到您的账户");
//            ;
//        } else if (stats == 8 | stats == 14) {
//            //退款成功
//            RelativeLayoutfund.setVisibility(View.GONE);
//            RelativeLayoutflow.setVisibility(View.VISIBLE);
//            textfirstline1.setText(String.valueOf(mData.getTitle()));
//            textsecondline2.setText("规格:" + mData.getLabel());
//            textthirdline1.setText("退款金额 ￥ " + mData.getPrice());
//            textthirdline1.setText(R.color.red_ff573f);
//        }
//    }
//
//    private void initpopupwindow() {
//        //物流公司的
//        ppCompanyName=new PopupPulldown(activity,companyName);
//        companyName.add(new PopupPulldown.PullDownData(1,"顺丰速运"));
//        companyName.add(new PopupPulldown.PullDownData(2,"邮政EMS"));
//        companyName.add(new PopupPulldown.PullDownData(3,"韵达快递"));
//        companyName.add(new PopupPulldown.PullDownData(4,"百世汇通"));
//        companyName.add(new PopupPulldown.PullDownData(5,"中通快递"));
//        companyName.add(new PopupPulldown.PullDownData(6,"圆通速运"));
//        companyName.add(new PopupPulldown.PullDownData(7,"天天快递"));
//
//        //退款类型
//        ppType = new PopupPulldown(activity, mTypeDatas);
//        mTypeDatas.add(new PopupPulldown.PullDownData(1, "已收货"));
//        mTypeDatas.add(new PopupPulldown.PullDownData(2, "未收货"));
//        /**
//         * 退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
//         */
//        pp = new PopupPulldown(activity, mFiltrareDatas);
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(1, "质量问题"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(2, "不在保质期"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(3, "使用后过敏"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(4, "发票问题"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(5, "变质/发霉"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(6, "少件/漏发"));
//        mFiltrareDatas.add(new PopupPulldown.PullDownData(7, "发错货"));
//
//
//        editHelper = new EditHelper(activity);
//        if (activity instanceof RefundActivity) {
//            mActivity = (RefundActivity) activity;
//        }
//
//
//        editHelper.addEditHelperData(new EditHelper.EditHelperData(cause, Validators.getLenghMinRegex(1), "退款原因没有选择"));
//        editHelper.addEditHelperData(new EditHelper.EditHelperData(price, Validators.getLenghMinRegex(1), "退款价格没有填写"));
//        if (mType != 0) {
//            editHelper.addEditHelperData(new EditHelper.EditHelperData(type, Validators.getLenghMinRegex(1), "是否已收货没有选择"));
//        }
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView =super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnClick({R.id.llCause, R.id.commit, R.id.llType,R.id.llselectCN,R.id.sure})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            //退款原因选择
//            case R.id.llCause:
//                pp.showPopupWindow(llCause);
//                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
//                    @Override
//                    public void onClick(int id, String content) {
//                        cause.setText(content);
//                        mReason = id;
//                    }
//                });
//                break;
//            //退款状态选择
//            case R.id.llType:
//                ppType.showPopupWindow(llType);
//                ppType.setOnClickCallback(new PopupPulldown.OnClickCallback() {
//                    @Override
//                    public void onClick(int id, String content) {
//                        type.setText(content);
//                        theType = id;
//                    }
//                });
//                break;
//            //物流公司选择
//            case R.id.llselectCN:
//                ppCompanyName.showPopupWindow(llselectCN);
//                ppCompanyName.setOnClickCallback(new PopupPulldown.OnClickCallback() {
//                    @Override
//                    public void onClick(int id, String content) {
//                        //物流公司名称
//
//                    }
//                });
//                break;
//            //确认提交
//            case R.id.sure:
//                sure.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        sure.setTextColor(R.color.red);
//                    }
//                });
//            /**
//             "msg": "ok",
//             "code": "A00000",
//             "data": {
//             "code": null,			//快递单号
//             "express": null,			//快递公司名
//             "reason": 1,	//退款货原因code，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货
//             "money": 10.2,	//退款金额，double
//             "state": 10,	//退款状态，5-待发货申请退款，6-待收货申请退款，7-退款中，8-退款完成，9-待收货申请退货、10-待评价申请退货、11-已收货申请退货，12-退货中待买家发货，13-退货中-待卖家收货，14-退货退款完成，15-拒绝退款-待发货，16-拒绝退款-待收货，17-拒绝退货-待收货，18-拒绝退货-待评价，19-拒绝退货-已收货，20-待发货-取消退货，25-退货退款中
//             "time": 1499326517000,	//退款自动处理时间
//             "current": 1501575389316,	//当前时间戳
//             "type": 1		//1-退款，2-退货退款
//             */
//            case R.id.commit:
//                if (editHelper.check()) {
//                    p = Double.parseDouble(price.getText().toString());
//                    if (p > (mActivity.mData.getMoney() * mActivity.mData.getNum())) {
//                        showInforSnackbar("价格不能大于订单价格");
//                        return;
//                    }
//                    stats = data.getState();
//                    requestData.setType(mType);
//                    requestData.setReason(mReason);
//                    requestData.setMoney(p);
//                    mActivity.presenter.setData(requestData, mid);
//                    RelativeLayoutfund.setVisibility(View.GONE);
//                    RelativeLayoutflow.setVisibility(View.VISIBLE);
//                    initrefundBill();
//                }
//                break;
//        }
//    }
//
//
//}
