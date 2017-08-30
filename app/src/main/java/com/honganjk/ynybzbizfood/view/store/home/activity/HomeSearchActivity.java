package com.honganjk.ynybzbizfood.view.store.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.dao.Mydao;
import com.honganjk.ynybzbizfood.view.store.home.ui.XCFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-08-30.
 */

public class HomeSearchActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_search)
    LinearLayout ll_Search;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.im_deletehistory)
    ImageView imDeletehistory;
    @BindView(R.id.nothistory)
    TextView nothistory;
    @BindView(R.id.flowlayout)
    XCFlowLayout mFlowLayout;
    private boolean haveData;
    private Mydao mydao;

    public static void startUI(Activity activity) {
        Intent intent = new Intent(activity, HomeSearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat"
    };

    List historyList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        initview();
        initChildViews();
    }

    private void initview() {
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
                    //点击展示
                    Toast.makeText(HomeSearchActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            });
            mFlowLayout.addView(view, lp);
        }
    }

    public boolean isHaveData() {
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

    @OnClick({R.id.et_search, R.id.cancel, R.id.im_deletehistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                break;
            case R.id.cancel:
                break;
            case R.id.im_deletehistory:
                break;
        }
    }
}
