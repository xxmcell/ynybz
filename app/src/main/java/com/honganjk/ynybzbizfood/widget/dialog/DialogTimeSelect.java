package com.honganjk.ynybzbizfood.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.LoopViewData;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopListener;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DialogTimeSelect extends Dialog {
    @BindView(value = R.id.loopViewLeft)
    protected LoopView loopViewLeft;
    @BindView(value = R.id.loopViewRight)
    protected LoopView loopViewRight;
    @BindView(value = R.id.quxiao)
    protected TextView quxiaoTv;
    @BindView(value = R.id.queding)
    protected TextView quedingTv;
    protected ArrayList<String> listLeft = new ArrayList<>();
    protected ArrayList<String> listRight = new ArrayList<>();

    private int leftSelectPostion = -1;

    private OnClickCallback clickCallback;

    public DialogTimeSelect(Context context) {
        this(context, R.style.Dialog_Loadding);
    }

    public DialogTimeSelect(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.base_dialog_time_select);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        listLeft.add("今天");
        listLeft.add("明天");
        listLeft.add("后天");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.applyFont(getWindow().getDecorView().findViewById(
                android.R.id.content));
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(
                android.R.id.content));
        initView();
    }

    /**
     * 点击事件
     */
    @OnClick(R.id.quxiao)
    protected void quxiaoOnClick(View view) {
        dismiss();
    }

    @OnClick(R.id.queding)
    protected void quedingOnClick(View view) {
        dismiss();
        if (clickCallback != null) {
            clickCallback.onClick(listLeft.get(loopViewLeft.getSelectedItem()), listRight.get(loopViewRight.getSelectedItem()));
        }
    }

    private void initView() {

        loopViewLeft.setArrayList(listLeft);
        loopViewLeft.setNotLoop();
        loopViewLeft.setInitPosition(0);
        loopViewLeft.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                if (item == leftSelectPostion) {
                    return;
                }
                getRightData(item);
                leftSelectPostion = item;
            }
        });
        getRightData(0);
        loopViewRight.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
            }
        });
    }


    /**
     * @param leftPostion 左边被选择的postion
     */
    private void getRightData(int leftPostion) {

        listRight.clear();
        loopViewRight.setInitPosition(0);
        if (leftPostion == 1 || leftPostion == 2) {
            for (int i = 9; i < 21; i++) {
                listRight.add(String.format("%02d:00-%02d:00", i, i + 1));
                loopViewRight.setArrayList(listRight);
            }
        } else if (leftPostion == 0) {
            //获取当前时间
            int hh = Integer.valueOf(DateUtils.getCurrentTime("HH"));

            for (int i = hh; i < 21; i++) {
                String aaa = String.format("%02d:00-%02d:00", i, i + 1);
                listRight.add(aaa);
                loopViewRight.setArrayList(listRight);
            }
        }
    }


    public void setClickCallback(OnClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface OnClickCallback {
        void onClick(String month, String day);
    }
}
