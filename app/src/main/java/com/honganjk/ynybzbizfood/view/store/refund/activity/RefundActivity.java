package com.honganjk.ynybzbizfood.view.store.refund.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData2;
import com.honganjk.ynybzbizfood.mode.javabean.store.refund.RefundRequestData;
import com.honganjk.ynybzbizfood.pressenter.store.refund.RefundPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.store.refund.fragment.RefundFragment;
import com.honganjk.ynybzbizfood.view.store.refund.view.IRefundParent;

import java.util.ArrayList;

/**
 * 说明:商城-退款
 * <p>
 * 作者： 杨阳; 创建于：  2017-06-29  10:21
 */
public class RefundActivity extends BaseMvpActivity<IRefundParent.IRefund, RefundPresenter>
        implements IRefundParent.IRefund {


    ArrayList<Fragment> fragmentDatas = new ArrayList<>();

    String[] titles;
    public StoreOrderData2.ObjsBean.DetailsBean.ListBean mData;
    public RefundRequestData mRefunddatas;
    private String json;
    private StoreOrderData2.ObjsBean data;
    private StoreOrderData2.ObjsBean.DetailsBean detailsBean;
    private StoreOrderData2.ObjsBean.DetailsBean.ListBean listBean;
    private String jsonString;
    private LinearLayout thecontent1;
    private LinearLayout thecontent;


    public static void startUI(Activity activity, String json, String jsonRefund) {
        Intent intent = new Intent(activity, RefundActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("data", json);
        intent.putExtra("datas", jsonRefund);
        activity.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.store_activity_refund;

    }

    @Override
    public void parseIntent(Intent intent) {

        json = intent.getStringExtra("data");

        Gson gson = new Gson();
        data = gson.fromJson(json, StoreOrderData2.ObjsBean.class);
        for (int i = 0; i < data.getDetails().size(); i++) {
            detailsBean = data.getDetails().get(i);
        }
        for (int i = 0; i < detailsBean.getList().size(); i++) {
            listBean = detailsBean.getList().get(i);
        }

    }

    @Override
    public void initView() {
        int id = data.getId();
        initToobar();
        presenter.getData(id);
        thecontent = (LinearLayout) findViewById(R.id.thecontent);


    }

    private void initFragmentState(String jsonStr) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        if(data!=null) {
            transaction.add(R.id.thecontent, RefundFragment.getInstance(0, jsonStr, efundRequestData,data.getState()));
        }
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void initToobar() {
        toolbar.setTitle("退款申请");
        toolbar.setBack(mActivity);
        toolbar.setTitleColor(R.color.black);
        toolbar.setNavigationIcon(R.drawable.material_arrwos_white_green);
    }
    @Override
    public RefundPresenter initPressenter() {
        return new RefundPresenter();
    }


    public void clearData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setHttpData(boolean data) {
        if (data) {
            ToastUtils.getToastShort("成功");
            showInforSnackbar("成功");
        } else {
            ToastUtils.getToastShort("失败");
            showInforSnackbar("失败");
        }
    }
    RefundRequestData efundRequestData;
    @Override
    public void getHttpData(RefundRequestData data) {
        efundRequestData = data;
        Gson gson=new Gson();
        String string=gson.toJson(efundRequestData);
        initFragmentState(json);
    }


}
