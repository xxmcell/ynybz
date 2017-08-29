package com.honganjk.ynybzbizfood.view.store.refund.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseFragment;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.ProductDetailsData;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
import com.honganjk.ynybzbizfood.utils.other.EditHelper;
import com.honganjk.ynybzbizfood.utils.other.Validators;
import com.honganjk.ynybzbizfood.view.store.refund.activity.RefundActivity;
import com.honganjk.ynybzbizfood.widget.PopupPulldown;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 说明:产品详情的-评价
 * 作者： 杨阳; 创建于：  2017-07-07  16:01
 */
public class RefundFragment extends BaseFragment {
    int mType;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.llType)
    LinearLayout llType;
    @BindView(R.id.llCause)
    LinearLayout llCause;
    @BindView(R.id.cause)
    TextView cause;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.remark)
    EditText remark;
    ArrayList<PopupPulldown.PullDownData> mFiltrareDatas = new ArrayList<>();
    PopupPulldown pp;
    Unbinder unbinder;
    RefundActivity mActivity;
    RefundRequestData requestData;
    public EditHelper editHelper;
    private int mReason;

    public RefundFragment() {
    }

    /**
     * @param type
     */
    @SuppressLint("ValidFragment")
    public RefundFragment(int type) {
        this.mType = type;
    }

    public static RefundFragment getInstance(int type) {
        return new RefundFragment(type);
    }

    @Override
    public int getContentView() {
        return R.layout.store_fragment_refund;
    }

    @Override
    public void initView() {
        if (mType == 0) {
            llType.setVisibility(View.GONE);
        }
        /**
         * 退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
         */
        pp = new PopupPulldown(activity, mFiltrareDatas);
        mFiltrareDatas.add(new PopupPulldown.PullDownData(1, "质量问题"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(2, "不在保质期"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(3, "使用后过敏"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(4, "发票问题"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(5, "变质/发霉"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(6, "少件/漏发"));
        mFiltrareDatas.add(new PopupPulldown.PullDownData(7, "发错货"));

        requestData = new RefundRequestData();
        editHelper = new EditHelper(activity);
        if (activity instanceof RefundActivity) {
            mActivity = (RefundActivity) activity;
        }

        editHelper.addEditHelperData(new EditHelper.EditHelperData(cause, Validators.getLenghMinRegex(1), "退款原因没有选择"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(price, Validators.getLenghMinRegex(1), "退款价格没有填写"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(remark, Validators.getLenghMinRegex(1), "请填写退款说明"));
    }

    public void setAdapter(List<ProductDetailsData.CommentsBean> details) {
    }


    @OnClick(R.id.llCause)
    public void onViewClicked() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llCause, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            /**
             *id	必选，int	订单Id
             remark	可选	退款/退货说明
             reach	可选，int	是否已送达，0-否，1-是，默认为0
             type	必选，int	处置类型，1-确认收货，2-申请退款，3-申请退货
             reason	可选，int	退款/退货说明原因，1-质量问题，2-不在保质期，3-使用后过敏， 4-发票问题，5-变质/发霉，6-少件/漏发，7-发错货，默认为1
             money	可选，double	退款金额，需小于订单总价
             */
            case R.id.commit:
                if (editHelper.check()) {
                    double p = Double.parseDouble(price.getText().toString());
                    if (p > (mActivity.mData.getMoney() * mActivity.mData.getNum())) {
                        showInforSnackbar("价格不能大于订单价格");
                        return;
                    }

                    requestData.setId(mActivity.mData.getId());
                    requestData.setRemark(remark.getText().toString());
                    requestData.setReach(mType == 0 ? 0 : 1);
                    requestData.setType(mActivity.mData.getState() == 1 ? 2 : 1);
                    requestData.setReason(mReason);
                    requestData.setMoney(p);
                    mActivity.presenter.getData(requestData);
                }

                break;
        }
    }
}
