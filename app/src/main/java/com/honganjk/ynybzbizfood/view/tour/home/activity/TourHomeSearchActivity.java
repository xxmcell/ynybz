package com.honganjk.ynybzbizfood.view.tour.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.dao.Mydao;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.view.store.home.ui.XCFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.R.id.et_search;


/**
 * Created by Administrator on 2017-08-30.
 */

public class TourHomeSearchActivity extends AppCompatActivity{

    @BindView(et_search)
    EditText etSearch;
    @BindView(R.id.ll_search)
    LinearLayout ll_Search;
    @BindView(R.id.tv_search)
    TextView tv_Seartory;
    @BindView(R.id.im_back)
    ImageView im_Back;
    @BindView(R.id.im_deletehistory)
    ImageView imDeletehistory;
    @BindView(R.id.nothistory)
    TextView nothistory;
    @BindView(R.id.flowlayout)
    XCFlowLayout mFlowLayout;
    private boolean haveData;
    private Mydao mydao;
    List historyList = new ArrayList();

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, TourHomeSearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        initview();
    }

    public void initview() {
        mydao = Mydao.getDao();
        mydao.setcontext(getBaseContext());

        if (isHaveData()) {
            showHistory();
        } else {
            showdefault();
        }
    }

    private void initChildViews() {
        // TODO Auto-generated method stub
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;

        for (int i = 0; i < historyList.size(); i++) {
            TextView view = new TextView(this);
            view.setText(historyList.get(i).toString());
            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_search));
            final String str = view.getText().toString();
            //点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转   分类
                    startSearch(str.toString().trim());
                }
            });
            mFlowLayout.addView(view, lp);
        }
    }

    public boolean isHaveData() {
        historyList.clear();
        historyList = mydao.queryhaveData();
        haveData = historyList.size() == 0 || null == historyList ? false : true;
        return haveData;
    }


    private void showdefault() {
        nothistory.setVisibility(View.VISIBLE);
        mFlowLayout.setVisibility(View.GONE);
    }

    private void showHistory() {
        nothistory.setVisibility(View.GONE);
        mFlowLayout.setVisibility(View.VISIBLE);
        initChildViews();
    }

    private void startSearch(String keyword) {
        if (TextUtils.isEmpty(keyword)){
            ToastUtils.getToastShort("请输入搜索内容");
            return;
        }

        sethistory(keyword);
        //跳转到分类
        TourSearchResultActivity.startUi(this,keyword);
        finish();
    }

    private void sethistory(String keyword) {
        boolean hasData = mydao.hasData(keyword);
        if (!hasData){
            mydao.insertData(keyword);
        }
    }


    @OnClick({R.id.im_back, R.id.tv_search, R.id.im_deletehistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_search://搜索
                startSearch(etSearch.getText().toString().trim());
                break;
            case R.id.im_deletehistory:
                mydao.deleteData();
                historyList.clear();
                mFlowLayout.removeAllViews();
                break;
        }
    }

}
