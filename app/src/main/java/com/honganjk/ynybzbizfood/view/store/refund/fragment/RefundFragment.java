package com.honganjk.ynybzbizfood.view.store.refund.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundProgress;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
import com.honganjk.ynybzbizfood.pressenter.store.refund.RefundProgressPresenter;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.store.order.fragment.textBaseFragment;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;
import com.honganjk.ynybzbizfood.view.store.refund.activity.RefundActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;
import com.honganjk.ynybzbizfood.widget.button.FlatButton;
import com.honganjk.ynybzbizfood.widget.empty_layout.LoadingAndRetryManager;

import java.util.ArrayList;

import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.honganjk.ynybzbizfood.R.id.ImageTop;

/**
 * 说明:产品详情的-评价
 * 作者： 杨阳; 创建于：  2017-07-07  16:01
 */
public class RefundFragment extends textBaseFragment<StoreOrderParentInterfaces.IOrderDetails, RefundProgressPresenter> implements View.OnClickListener ,StoreOrderParentInterfaces.IOrderDetails{

    int mType;
    int mid;

    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>();
    ArrayList<PopupPulldown.PullDownData> mTypeDatas = new ArrayList<>();
    ArrayList<PopupPulldown.PullDownData> companyName = new ArrayList<>();
    PopupPulldown ppCompanyName;
    PopupPulldown pp;
    PopupPulldown ppType;
    Unbinder unbinder;
    RefundActivity mActivity;
    RefundRequestData requestData;
    public EditHelper editHelper;


    int mReason;
    int theType;

    private double p;
    private int stats;
    private String jsonStr;
    private StoreOrderData2.ObjsBean ObjsData;
    private String flowCNtext;
    private String flowCNnumbtext;

    private TextView type;
    private LinearLayout llType;
    private TextView cause;
    private EditText price;
    private EditText remark;
    private FlatButton commit;
    private RelativeLayout relativeLayoutfund;
    private TextView state;
    private TextView theTime;
    private LinearLayout contants;
    private LinearLayout linearLayout2;
    private TextView textViewType;
    private TextView textViewSum;
    private TextView textviewreson;
    private LinearLayout refundinformation;
//    private TextView textlogistics;
//    private TextView textviewbill;
    private Button sure;
    private LinearLayout flowinformation;
    private Button cancel;
    private Button ensure;
    private RelativeLayout relativeLayoutflow;
    private ImageView imageView2;
    private TextView flowName;
    private TextView textfirstline1;
    private TextView textfirstline2;
    private TextView textsecondline1;
    private TextView textsecondline2;
    private TextView textthirdline1;
    private TextView textthirdline2;
    private TextView titel;
    private EditText flowCNeditText;
    private EditText flowCNnumbedittext;
    private LinearLayout llselectCN;
    private LinearLayout llCause;
    private StoreOrderData2.ObjsBean.DetailsBean detailsBean;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean listBean;
    private LinearLayout repayment;
    private TextView refundTime;
    private TextView refundTimeStart;
    private ImageView imageTop;
    private ImageView imageMid;
    private ImageView imageBottom;
    private LinearLayout bottom_buttons;
    private String theNameOfFC;


    /**
     *
     */
    @SuppressLint("ValidFragment")
    public RefundFragment(int i, String json, RefundRequestData mRefunddatas,int  States) {
//        this.mType = i;
        this.jsonStr = json;
        this.requestData = mRefunddatas;
        this.stats=States;

    }

    public static RefundFragment getInstance(int i, String json, RefundRequestData mRefunddatas,int  States) {
        return new RefundFragment(i, json, mRefunddatas,States);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.store_fragment_refund,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void init() {
        Gson gson = new Gson();
        ObjsData = gson.fromJson(jsonStr, StoreOrderData2.ObjsBean.class);

        for (int i = 0; i < ObjsData.getDetails().size(); i++) {
            detailsBean = ObjsData.getDetails().get(i);
        }
        for (int i = 0; i < detailsBean.getList().size(); i++) {
            listBean = detailsBean.getList().get(i);
        }
        mid=ObjsData.getId();
        if (stats==1) {
            titel.setText("仅退款");
            llType.setVisibility(View.GONE);
            mType=0;

        } else if (stats==2||stats==3) {
            titel.setText("退钱退款");
            mType=1;
        }
        llCause.setOnClickListener(this);
        commit.setOnClickListener(this);
        llType.setOnClickListener(this);
        llselectCN.setOnClickListener(this);
        sure.setOnClickListener(this);
        initpopupwindow();
        initrefundBill();
    }

    private void initView() {
        type = getActivity().findViewById(R.id.type);
        llType = getActivity().findViewById(R.id.llType);
        cause = getActivity().findViewById(R.id.cause);
        price = getActivity().findViewById(R.id.price);
        remark = getActivity().findViewById(R.id.remark);
        commit = getActivity().findViewById(R.id.commit);
        relativeLayoutfund = getActivity().findViewById(R.id.RelativeLayoutfund);
        state = getActivity().findViewById(R.id.state);
        theTime = getActivity().findViewById(R.id.theTime);
        contants = getActivity().findViewById(R.id.contants);
        linearLayout2 = getActivity().findViewById(R.id.linearLayout2);
        textViewType = getActivity().findViewById(R.id.textViewType);
        textViewSum = getActivity().findViewById(R.id.textViewSum);
        textviewreson = getActivity().findViewById(R.id.textviewreson);
        refundinformation = getActivity().findViewById(R.id.refundinformation);
//        textlogistics = getActivity().findViewById(R.id.textlogistics);
//        textviewbill = getActivity().findViewById(R.id.textviewbill);
        sure = getActivity().findViewById(R.id.sure);
        flowinformation = getActivity().findViewById(R.id.flowinformation);
        cancel = getActivity().findViewById(R.id.cancel);
        ensure = getActivity().findViewById(R.id.ensure);
        relativeLayoutflow = getActivity().findViewById(R.id.RelativeLayoutflow);
        imageView2 = getActivity().findViewById(R.id.imageView2);
        flowName = getActivity().findViewById(R.id.flowName);
        textfirstline1 = getActivity().findViewById(R.id.textfirstline1);
        textfirstline2 = getActivity().findViewById(R.id.textfirstline2);
        textsecondline1 = getActivity().findViewById(R.id.textsecondline1);
        textsecondline2 = getActivity().findViewById(R.id.textsecondline2);
        textthirdline1 = getActivity().findViewById(R.id.textthirdline1);
        textthirdline2 = getActivity().findViewById(R.id.textthirdline2);
        titel = getActivity().findViewById(R.id.titel);
        flowCNeditText = getActivity().findViewById(R.id.flowCNeditText);
        flowCNnumbedittext = getActivity().findViewById(R.id.flowCNnumbedittext);
        llselectCN = getActivity().findViewById(R.id.llselectCN);
        llCause = getActivity().findViewById(R.id.llCause);
        //退款过程
        repayment = getActivity().findViewById(R.id.repayment);
        refundTime = getActivity().findViewById(R.id.refundTime);
        refundTimeStart = getActivity().findViewById(R.id.refundTimeStart);
        imageTop = getActivity().findViewById(ImageTop);
        imageMid = getActivity().findViewById(R.id.ImageMid);
        imageBottom = getActivity().findViewById(R.id.ImageBottom);
        bottom_buttons = getActivity().findViewById(R.id.bottom_buttons);
        init();
    }



    private void initrefundBill() {
        //一进来,获得数据,看是否符合
        if (stats != 5 || stats != 6 || stats != 9 || stats != 7 ||
                stats != 20 || stats != 15 || stats != 16 || stats != 17 ||
                stats != 12 || stats != 13 || stats != 8 || stats != 14||stats!=25) {
            relativeLayoutflow.setVisibility(View.GONE);
        }

        if(stats==8||stats==14){
            if(presenter!=null){
                presenter.getRefundProgressData(ObjsData.getId());
            }else {

            }

        }
        //获得时间差
        long time = requestData.getTime() - requestData.getCurrent();

        long days = time / (1000 * 60 * 60 * 24);
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

        if (stats == 5 || stats == 6 || stats == 9) {
            //等待处理
            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            flowinformation.setVisibility(View.GONE);
            textViewType.setText("仅退款");
            textViewSum.setText("￥" + ObjsData.getPrice());
            textfirstline1.setText("如果商家同意:");

            textfirstline2.setText("申请将达成,原路返回您的退款");

            textsecondline1.setText("如果商家拒绝");

            textsecondline2.setText("您可以修改退款申请,或者申请服务介入");

            textthirdline1.setText("如果商家未处理");

            textthirdline2.setText("超过2天申请达成并为您退款");

        } else if (stats == 7 || stats == 20||stats==25) {
            //商家处理中

            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            flowinformation.setVisibility(View.GONE);
            textfirstline1.setText("卖家已经收到您的退款商品,退款将于2个工作人原路退回您的账户,请您注意查收");
            state.setText("商家处理中");
            textViewType.setText("仅退款");
            textViewSum.setText("￥" + ObjsData.getPrice());

        } else if (stats == 15 || stats == 16 || stats == 17) {
            //商家拒绝
            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            flowinformation.setVisibility(View.GONE);
            state.setText("商家拒绝退款申请");
            theTime.setText("自动确认收货时间:剩余" + days + "天" + hours + "小时");
            theTime.setTextColor(R.color.blue);
            textViewType.setText("仅退款");
            textViewSum.setText("￥" + ObjsData.getPrice());
            textfirstline1.setText("商家拒绝了您的退款申请,您可以修改退款申请,或者申请客服介入");

        } else if (stats == 12) {
            //商家同意
            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            state.setText("商家同意退款");
            theTime.setText("自动确认收货时间:剩余" + days + "天" + hours + "小时");
            theTime.setTextColor(R.color.blue);
            textViewType.setText("仅退款");
            textViewSum.setText("￥" + ObjsData.getPrice());
            textfirstline1.setText("商家已经同意您的退款申请,请尽快退回商品");
            flowName.setText("物流信息");
            flowName.setTextColor(R.color.black);
            //物流公司名称
            flowCNtext = flowCNeditText.getText().toString().trim();
            //快递单号
            flowCNnumbtext = flowCNnumbedittext.getText().toString().trim();

        } else if (stats == 13) {
            //物流中
            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            state.setText("物流中");
            textfirstline1.setText("物流退货中,请耐心等待.若商家超出10天23小时未进行处理,系统将自动退回到您的账户");
            ;
        } else if (stats == 8 | stats == 14) {
            //退款成功
            ToastUtils.getToastShort("进来了");
            relativeLayoutfund.setVisibility(View.GONE);
            relativeLayoutflow.setVisibility(View.VISIBLE);
            refundinformation.setVisibility(View.GONE);
            flowinformation.setVisibility(View.GONE);
            sure.setVisibility(View.GONE);
            bottom_buttons.setVisibility(View.GONE);
            repayment.setVisibility(View.VISIBLE);
            imageView2.setImageResource(R.mipmap.bj_rundsce);
            textfirstline1.setText(String.valueOf(listBean.getTitle()));
            textsecondline2.setText("规格:" + listBean.getLabel());
            textthirdline1.setText("退款金额 ￥ " + ObjsData.getPrice());
            textthirdline1.setTextColor(R.color.red_ff573f);
            refundTimeStart.setText(refundProgress+"  14时查询,若超时未查询到,请咨询银行");
            if(refundProgress.getSuccess()!=0){
                refundTime.setText(refundProgress.getSuccess()+"14时查询,若超时未查询到,请咨询银行");
                imageTop.setImageResource(R.mipmap.greentopv);
            }


        }
    }

    private void initpopupwindow() {
        //物流公司的
        ppCompanyName=new PopupPulldown(getActivity(),companyName);
        companyName.add(new PopupPulldown.PullDownData(1,"顺丰速运"));
        companyName.add(new PopupPulldown.PullDownData(2,"邮政EMS"));
        companyName.add(new PopupPulldown.PullDownData(3,"韵达快递"));
        companyName.add(new PopupPulldown.PullDownData(4,"百世汇通"));
        companyName.add(new PopupPulldown.PullDownData(5,"中通快递"));
        companyName.add(new PopupPulldown.PullDownData(6,"圆通速运"));
        companyName.add(new PopupPulldown.PullDownData(7,"天天快递"));

        //退款类型
        ppType = new PopupPulldown(getActivity(), mTypeDatas);
        mTypeDatas.add(new PopupPulldown.PullDownData(3, "已收货"));
        mTypeDatas.add(new PopupPulldown.PullDownData(2, "未收货"));
        /**
         * 退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
         */
        pp = new PopupPulldown(getActivity(), mFiltrareDatas);
        mFiltrareDatas.add(new PopupPulldown.PullDownData(1, "质量问题"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(2, "不在保质期"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(3, "使用后过敏"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(4, "发票问题"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(5, "变质/发霉"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(6, "少件/漏发"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(7, "发错货"));


        editHelper = new EditHelper(getActivity());
        if (getActivity() instanceof RefundActivity) {
            mActivity = (RefundActivity) getActivity();
        }
        editHelper.addEditHelperData(new EditHelper.EditHelperData(cause, Validators.getLenghMinRegex(1), "退款原因没有选择"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(price, Validators.getLenghMinRegex(1), "退款价格没有填写"));
        if (mType != 0) {
            editHelper.addEditHelperData(new EditHelper.EditHelperData(type, Validators.getLenghMinRegex(1), "是否已收货没有选择"));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //退款原因选择
            case R.id.llCause:
                pp.showPopupWindow(llCause);
                pp.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        cause.setText(content);
                        mReason = id;
                    }
                });
                break;
            //退款状态选择
            case R.id.llType:
                ppType.showPopupWindow(llType);
                ppType.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        type.setText(content);
                        theType = id;
                    }
                });
                break;
            //物流公司选择
            case R.id.llselectCN:
                ppCompanyName.showPopupWindow(llselectCN);
                ppCompanyName.setOnClickCallback(new PopupPulldown.OnClickCallback() {
                    @Override
                    public void onClick(int id, String content) {
                        theNameOfFC = content;

                    }
                });
                break;
            //确认提交
            case R.id.sure:
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sure.setTextColor(R.color.red);
                    }
                });
                /**
                 "msg": "ok",
                 "code": "A00000",
                 "data": {
                 "code": null,			//快递单号
                 "express": null,			//快递公司名
                 "reason": 1,	//退款货原因code，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货
                 "money": 10.2,	//退款金额，double
                 "state": 10,	//退款状态，5-待发货申请退款，6-待收货申请退款，7-退款中，8-退款完成，9-待收货申请退货、10-待评价申请退货、11-已收货申请退货，12-退货中待买家发货，13-退货中-待卖家收货，14-退货退款完成，15-拒绝退款-待发货，16-拒绝退款-待收货，17-拒绝退货-待收货，18-拒绝退货-待评价，19-拒绝退货-已收货，20-待发货-取消退货，25-退货退款中
                 "time": 1499326517000,	//退款自动处理时间
                 "current": 1501575389316,	//当前时间戳
                 "type": 1		//1-退款，2-退货退款
                 */
            case R.id.commit:
                if (editHelper.check()) {
                    p = Double.parseDouble(price.getText().toString());
                    if (p > (listBean.getMoney() * listBean.getNum())) {
//                        showInforSnackbar("价格不能大于订单价格");
                        return;
                    }
                    if(stats==12){
                        if(theNameOfFC==null){
                            requestData.setExpress(flowCNtext);
                        }else {
                            requestData.setExpress(theNameOfFC);
                        }

                        requestData.setCode(flowCNnumbtext);
                    }
                    stats = ObjsData.getState();
                    requestData.setType(mType);
                    requestData.setReason(mReason);
                    requestData.setMoney(p);
                    mActivity.presenter.setData(theType,requestData, mid);
                    relativeLayoutfund.setVisibility(View.GONE);
                    relativeLayoutflow.setVisibility(View.VISIBLE);
                    initrefundBill();
                }
                break;
        }
    }

    @Override
    public void getData(StoreOrderDetailsData data) {

    }

    @Override
    public void getHttpDataRefund(RefundRequestData data) {

    }
    RefundProgress refundProgress;
    @Override
    public void RefundProgress(RefundProgress data) {
        refundProgress=data;
    }

    @Override
    public boolean isLogin(boolean isToLogin) {
        return false;
    }

    @Override
    public void showDialog(String msg, boolean isCancelable) {

    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public LoadingAndRetryManager getLoadingAndRetryManager() {
        return null;
    }

    @Override
    public void addSubscription(Subscription s) {

    }

    @Override
    public CompositeSubscription getCompositeSubscription() {
        return null;
    }

    @Override
    public void showErrorSnackbar(String result) {

    }

    @Override
    public void showErrorSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result, boolean isFinish) {

    }

    @Override
    public void showWarningSnackbar(String result) {

    }

    @Override
    public void showSnackbar(String result, int type, boolean isFinish) {

    }

    @Override
    public RefundProgressPresenter initPressenter() {
        return new RefundProgressPresenter(REQUEST_CODE);
    }
}
