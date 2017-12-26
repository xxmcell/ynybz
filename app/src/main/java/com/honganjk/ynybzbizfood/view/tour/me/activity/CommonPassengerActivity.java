package com.honganjk.ynybzbizfood.view.tour.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseListActivity;
import com.honganjk.ynybzbizfood.mode.javabean.tour.me.CommonPassengerBean;
import com.honganjk.ynybzbizfood.pressenter.tour.me.CommonPassengerPresenter;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.tour.me.interfaces.TourMeInterface;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用旅客页面
 * Created by Administrator on 2017-12-04.
 */

public class CommonPassengerActivity extends BaseListActivity<TourMeInterface.MyCommonPassengerInrerface, CommonPassengerPresenter>
        implements TourMeInterface.MyCommonPassengerInrerface {

    private List<CommonPassengerBean> list = new ArrayList<>();
    private CommonAdapter<CommonPassengerBean> adapter;
    private int content;

    public static void startForResultUi(Activity activity, int requestCode, int content) {
        Intent intent = new Intent(activity, CommonPassengerActivity.class);
        intent.putExtra("content", content);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onLoadding() {
        presenter.getData(false);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getData(true);
    }

    @Override
    public void getCommonPassenger(List<CommonPassengerBean> dataList) {
        this.list.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess(int id) {
        presenter.getData(true);
    }

    @Override
    public void clearData() {
        list.clear();
    }

    @Override
    public CommonPassengerPresenter initPressenter() {
        return new CommonPassengerPresenter();
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_1).build();
    }

    int nWidth;

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.ll_add_passenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPassengerActivity.startForResultUi(CommonPassengerActivity.this, REQUEST_CODE);
            }
        });
        listSwipeView.getRecyclerView().setLoadMoreEnabled(false);
        presenter.getData(true);
        nWidth = new ScreenInfoUtils().getWidth();
    }

    @Override
    public void onBackPressed() {
        if (content == -1) {
            Intent intent = new Intent();
            intent.putExtra("passengerNum", list.size());
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new CommonAdapter<CommonPassengerBean>(this, R.layout.item_passenger, list) {
            @Override
            public void convert(ViewHolder holder, final CommonPassengerBean commonPassengerBean) {
                View view = holder.getView(R.id.relativeLayout);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = nWidth;
                view.setLayoutParams(params);
                holder.setText(R.id.tv_name, commonPassengerBean.getName());

                int mType = commonPassengerBean.getType();
                String identity = "成人";
                if (mType == 1) {
                    identity = "儿童";
                }
                if (mType == 2) {
                    identity = "军人";
                }
                holder.setText(R.id.identity_and_idcard, identity + "  " + commonPassengerBean.getSn());
                holder.setOnClickListener(R.id.iv_reset, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//修改
                        EditPassengerActivity.startForResultUi(CommonPassengerActivity.this, REQUEST_CODE, commonPassengerBean);
                    }
                });
                holder.setOnClickListener(R.id.tv_dele, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//删除
                        presenter.delePassenger(commonPassengerBean.getId());
                    }
                });
                holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        if (content == -1) {
                            intent.putExtra("passengerNum", list.size());
                        } else {
                            intent.putExtra("content", content);
                            intent.putExtra("commonPassengerBean",commonPassengerBean);
                        }
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }

            @Override
            public void convert(ViewHolder holder, List<CommonPassengerBean> t) {

            }
        };
        return adapter;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_common_passenger;
    }

    @Override
    public void parseIntent(Intent intent) {
        content = intent.getIntExtra("content", -1);
    }

    @Override
    public void onRefresh() {
        presenter.getData(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                presenter.getData(true);
                break;
        }
    }
}
