package com.honganjk.ynybzbizfood.view.shitang.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.pressenter.shitang.order.WritingEvaluationPresenter;
import com.honganjk.ynybzbizfood.view.shitang.order.interfaces.OrderParentInterfaces;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;

/**
 * 写评价
 * bid	必选,int	被评论的商户id
 * oid	必选,int	所评论的订单id
 * score	必选	评分，限制1-2-3-4-5
 * content	可选
 * 评论内容
 */

public class WritingEvaluationActivity extends BaseMvpActivity<OrderParentInterfaces.IWritingEvaluation, WritingEvaluationPresenter> implements OrderParentInterfaces.IWritingEvaluation {
    @BindView(R.id.iv_caiping)
    ImageView ivCaiping;
    @BindView(R.id.et_pingjia)
    EditText etPingjia;
    @BindView(R.id.evalua_view)
    SimpleRatingBar evaluaView;
    private int mBid = -1;
    private int mOid = -1;
    private String mImageurl;
    int pingFen = 5;

    /**
     * 食堂订单
     *
     * @param context
     * @param bid     被评论的商户id
     * @param oid     所评论的订单id
     * @param url     图片路径
     */
    public static void startUI(Activity context, int bid, int oid, String url, int requestCode) {
        Intent intent = new Intent(context, WritingEvaluationActivity.class);
        intent.putExtra("bid", bid);
        intent.putExtra("oid", oid);
        intent.putExtra("image", url);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 食堂订单
     *
     * @param context     启动对象
     * @param bid         被评论的商户id
     * @param oid         所评论的订单id
     * @param url         图片路径
     * @param requestCode 请求code
     */
    public static void startUI(Fragment context, int bid, int oid, String url, int requestCode) {
        Intent intent = new Intent(context.getContext(), WritingEvaluationActivity.class);
        intent.putExtra("bid", bid);
        intent.putExtra("oid", oid);
        intent.putExtra("image", url);
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 评论护理订单
     *
     * @param context     启动对象
     * @param oid         所评论的订单id
     * @param url         图片路径
     * @param requestCode 请求code
     */
    public static void startUI(Fragment context, int oid, String url, int requestCode) {
        Intent intent = new Intent(context.getContext(), WritingEvaluationActivity.class);
        intent.putExtra("oid", oid);
        intent.putExtra("image", url);
        context.startActivityForResult(intent, requestCode);
    }
    public static void startUI(Activity context, int oid, String url, int requestCode) {
        Intent intent = new Intent(context, WritingEvaluationActivity.class);
        intent.putExtra("oid", oid);
        intent.putExtra("image", url);
        context.startActivityForResult(intent, requestCode);
    }



    @Override
    public WritingEvaluationPresenter initPressenter() {
        return new WritingEvaluationPresenter();
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
                commitEvaltion();
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
    private void commitEvaltion() {
        //食堂订单评价
        if (mBid != -1) {
            presenter.commitEvaluation(mBid, mOid, pingFen, etPingjia.getText().toString().trim());

            //护理订单评价
        } else {
            presenter.commitNurse(mOid, pingFen, etPingjia.getText().toString().trim());
        }


    }

    @Override
    public void parseIntent(Intent intent) {
        mBid = intent.getIntExtra("bid", -1);
        mOid = intent.getIntExtra("oid", -1);
        mImageurl = intent.getStringExtra("image");

    }

    /**
     * 后台返回的数据
     */
    @Override
    public void isCommit(boolean bool) {
        showInforSnackbar("评论成功", true);
        setResult(RESULT_OK);
    }

    @Override
    public void clearData() {

    }
}
