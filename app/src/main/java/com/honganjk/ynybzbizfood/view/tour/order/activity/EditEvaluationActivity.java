package com.honganjk.ynybzbizfood.view.tour.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.tour.order.EditEvaluationPresenter;
import com.honganjk.ynybzbizfood.view.tour.order.interfaces.OrderTourPresentInterface;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-12-20.
 */

public class EditEvaluationActivity extends BaseMvpActivity<OrderTourPresentInterface.EditEvaluationInterface, EditEvaluationPresenter>
        implements OrderTourPresentInterface.EditEvaluationInterface {


    @BindView(R.id.iv_caiping)
    ImageView ivCaiping;
    @BindView(R.id.et_pingjia)
    TextInputEditText etPingjia;
    @BindView(R.id.evalua_view)
    SimpleRatingBar evaluaView;

    private int mBid = -1;
    private int mOid = -1;
    private String mImageurl;
    int pingFen = 5;
    /**
     * @param context     activity
     * @param oid         订单id
     * @param url         图片路径
     * @param requestCode requestCode
     */

    public static void startUI(Activity context, int oid, String url, int requestCode) {
        Intent intent = new Intent(context, EditEvaluationActivity.class);
        intent.putExtra("oid", oid);
        intent.putExtra("image", url);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void clearData() {

    }

    @Override
    public EditEvaluationPresenter initPressenter() {
        return new EditEvaluationPresenter();
    }

    @Override
    public void isCommit(boolean bool) {
        showInforSnackbar("评论成功", true);
        setResult(RESULT_OK);
    }

    @Override
    public int getContentView() {
        return R.layout.writingevaluation;
    }

    @Override
    public void initView() {
        Glide.with(this).load(mImageurl).into(ivCaiping);
        toolbar.setBack(this);
        toolbar.setTitle("写评价");
        toolbar.addAction(1, "发布");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //请求网络
                commitEvaluation();
                return true;
            }
        });

        evaluaView.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                pingFen = (int) rating;
            }
        });
    }
    /**
     * 提交评论
     */
    private void commitEvaluation() {
        String content = null;
        if (!TextUtils.isEmpty(etPingjia.getText().toString().trim())){
            content = etPingjia.getText().toString().trim();
        }
        presenter.commitTour(mOid,pingFen,content,null);
    }

    @Override
    public void parseIntent(Intent intent) {

        mBid = intent.getIntExtra("bid", -1);
        mOid = intent.getIntExtra("oid", -1);
        mImageurl = intent.getStringExtra("image");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
