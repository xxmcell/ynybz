package com.honganjk.ynybzbizfood.view.peihu.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.SubscribeOrderBean;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明:预约成功的提示
 * 作者： 杨阳; 创建于：  2017-06-21  14:42
 */
public class SubscribeSucceedActivity extends BaseActivity {

    SubscribeOrderBean mData;
    @BindView(R.id.serviceTime)
    TextView serviceTime;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;

    /**
     * @param activity activity
     */
    public static void startUI(Context activity, SubscribeOrderBean data) {
        Intent intent = new Intent(activity, SubscribeSucceedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_subscribe_succeed;
    }

    /**
     * 1:护工；2康复师
     *
     * @param intent
     */
    @Override
    public void parseIntent(Intent intent) {
        mData = (SubscribeOrderBean) intent.getSerializableExtra("data");
        if (mData != null) {
//            String s=mData.getOrderType()
            GlideUtils.setImageBitmapRound(picture, mData.getPortrait(),1);
            serviceTime.setText("您已经成功预约,于" + mData.getmNursingStartTimeStr() + "上门服务。如有变故请及时联系服务人员");
            name.setText(mData.getServiceName());
            time.setText("服务时间：" + mData.getServiceTime());

        } else {
            showErrorSnackbar("订单错误", true);
        }
    }

    @Override
    public void initView() {
        toolbar.setTitle("预约成功");
        toolbar.setBack(this, R.drawable.material_arrwos_black_left);
        toolbar.setTitleColor(R.color.black);
//        toolbar.addAction(1, "联系", R.drawable.material_phone);
//
//        if (toolbar != null)
//            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//
//                    DeviceUtil.callByPhone(SubscribeSucceedActivity.this, mData.getPhone());
//                    return false;
//                }
//            });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.immediatelySubscribe)
    public void onViewClicked() {
        finish();
    }
}
