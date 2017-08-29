package com.honganjk.ynybzbizfood.widget.dialog.therapisttime;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ValideDataBean;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类说明:康复师的时间选择
 * 阳（360621904@qq.com）  2017/4/19  10:02
 */
public class DialogTherapistTime extends Dialog implements View.OnClickListener {
    //回调对象
    private OnClickCallback onClickCallback;
    //日期的view
    private ArrayList<TextView> weekViews = new ArrayList<>();
    //时间的view
    private ArrayList<TextView> timeViews = new ArrayList<>();
    //返回数据
    private HashMap<Integer, ArrayList<Integer>> mHm = new HashMap<>();
    //时间对象
    private DateBean mDate;
    //周几的记录
    private int week = 0;
    //是不是多选
    private boolean mIsMore;
    ValideDataBean mValideData;

    /**
     * @param context
     * @param data    有效的数据(如果是筛选传null即可)
     * @param isMore  是不多选：true多选，false单选
     */
    public DialogTherapistTime(@NonNull Context context, ValideDataBean data, boolean isMore) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_threapist_time);
        ButterKnife.bind(this);
        mValideData = data;
        mDate = new DateBean();
        mDate.setValidTime(mValideData);
        mIsMore = isMore;
        if (!isMore) {
            findViewById(R.id.commit).setVisibility(View.GONE);
        }
        intView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void intView() {
        weekViews.add((TextView) findViewById(R.id.dayOne));
        weekViews.add((TextView) findViewById(R.id.dayTwo));
        weekViews.add((TextView) findViewById(R.id.dayThree));
        weekViews.add((TextView) findViewById(R.id.dayFour));
        weekViews.add((TextView) findViewById(R.id.dayFive));
        weekViews.add((TextView) findViewById(R.id.daySix));
        weekViews.add((TextView) findViewById(R.id.daySeven));

        timeViews.add((TextView) findViewById(R.id.timeOne));
        timeViews.add((TextView) findViewById(R.id.timeTwo));
        timeViews.add((TextView) findViewById(R.id.timeThree));
        timeViews.add((TextView) findViewById(R.id.timeFour));
        timeViews.add((TextView) findViewById(R.id.timeFive));
        setViewOnClickListener();
    }

    /**
     * 选择的单个时间打开时显示
     *
     * @param week 周
     * @param time 选择的时间
     */
    public void setItemSelected(int week, int time) {
        mDate.getValidTimes().get(week - 1).getIsSelectTime()[time-1] = !(mDate.getValidTimes().get(week - 1).getIsSelectTime()[time-1]);
        for (int i = 0; i < weekViews.size(); i++) {
            if (((Integer) weekViews.get(i).getTag()).equals(week)) {
                weekViews.get(i).callOnClick();
            }
        }
        setTimeColor();
    }

    /**
     * 选择的多个时间打开时显示
     *
     * @param week 周
     * @param time 选择的时间
     */
    public void setItemSelected(int week, ArrayList<Integer> time) {

        for (int i = 0; i < weekViews.size(); i++) {
            if (((Integer) weekViews.get(i).getTag()).equals(week)) {
                weekViews.get(i).callOnClick();
            }
        }
        for (int i = 0; i < time.size(); i++) {
            mDate.getValidTimes().get(week - 1).getIsSelectTime()[time.get(i) - 1] = true;
            addValidData(week, time.get(i));

        }
        this.week = week;
        setTimeColor();
    }

    /**
     * 添加已经选择的数据
     *
     * @param week 选择的周
     * @param time 选择的时间
     */
    private void addValidData(int week, int time) {
        //把选择的时间放在返回的map中
        if (mHm.get(week) != null) {
            if (mHm.get(week).contains(time)) {
                mHm.get(week).remove((Object) (time));
            } else {
                mHm.get(week).add(time);
            }
        } else {
            ArrayList<Integer> list = new ArrayList();
            list.add(time);
            mHm.put(week, list);
        }

    }

    /**
     * 添加监听与数据
     */
    private void setViewOnClickListener() {
        for (int i = 0; i < weekViews.size(); i++) {
            weekViews.get(i).setOnClickListener(this);
            weekViews.get(i).setText(mDate.getWeekStr().get(i).getWeekStr());
            weekViews.get(i).setTag(mDate.getWeekStr().get(i).getWeek());
        }
        for (int i = 0; i < timeViews.size(); i++) {
            timeViews.get(i).setOnClickListener(this);
            timeViews.get(i).setText(mDate.getTimeStr()[i]);
        }
        week = mDate.getWeekStr().get(0).getWeek();
        setTimeColor();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView && (((TextView) v).getText().toString().contains("-"))) {
            //时间段
            for (int i = 0; i < timeViews.size(); i++) {
                if (v.getId() == timeViews.get(i).getId()) {
                    if (mDate.getValidTimes().get(week - 1).getValidTime()[i]) {
                        mDate.getValidTimes().get(week - 1).getIsSelectTime()[i] = !(mDate.getValidTimes().get(week - 1).getIsSelectTime()[i]);
                        setTimeColor();
                        //把选择的时间放在返回的map中
                        if (mHm.get(week) != null) {
                            if (mHm.get(week).contains(i + 1)) {
                                mHm.get(week).remove((Object) (i + 1));
                                //如果这一天的没有选择的时间了，就把这天删除掉
                                if (mHm.get(week).size()==0) {
                                    mHm.remove(week);
                                }
                            } else {
                                mHm.get(week).add(i + 1);
                            }
                        } else {
                            ArrayList<Integer> list = new ArrayList();
                            list.add(i + 1);
                            mHm.put(week, list);
                        }

//                        单选直接返回
                        if (!mIsMore) {
                            if (onClickCallback != null) {
                                onClickCallback.onClick(mHm);
                            }
                            dismiss();
                        }

                    } else {
                        ToastUtils.getToastLong("当前时间不可预约");
                    }
                }
            }
            return;
        }
        //日期与星期
        for (int i = 0; i < weekViews.size(); i++) {
            if (v.getId() == weekViews.get(i).getId()) {
                weekViews.get(i).setBackgroundColor(getContext().getResources().getColor(R.color.main_color));
                weekViews.get(i).setTextColor(getContext().getResources().getColor(R.color.white));
                //是筛选时清空选择的记录
                if (mValideData == null) {
                    mHm.remove(week);
                    for (int j = 0; j < mDate.getValidTimes().get(week - 1).getIsSelectTime().length; j++) {
                        mDate.getValidTimes().get(week - 1).getIsSelectTime()[j] = false;
                    }
                }
                week = mDate.getWeekStr().get(i).getWeek();
                setTimeColor();
            } else {
                weekViews.get(i).setBackgroundColor(getContext().getResources().getColor(R.color.white));
                weekViews.get(i).setTextColor(getContext().getResources().getColor(R.color.main_color));
            }

        }
    }

    /**
     * 时间段的样式更改
     */
    private void setTimeColor() {
        ArrayList<DateBean.TimeBean> validTime = mDate.getValidTimes();
        //时间段
        for (int i = 0; i < validTime.get(week - 1).getValidTime().length; i++) {
            //已经选择
            if (validTime.get(week - 1).getIsSelectTime()[i]) {
                timeViews.get(i).setBackgroundColor(getContext().getResources().getColor(R.color.main_color));
                timeViews.get(i).setTextColor(getContext().getResources().getColor(R.color.white));
                //没有选择
            } else {
                timeViews.get(i).setBackgroundColor(getContext().getResources().getColor(R.color.white));
                timeViews.get(i).setTextColor(getContext().getResources().getColor(R.color.gray_99));
                //可选时间
                if (validTime.get(week - 1).getValidTime()[i]) {
                    timeViews.get(i).setTextColor(getContext().getResources().getColor(R.color.main_color));
                    //不可选时间
                } else {
                    timeViews.get(i).setTextColor(getContext().getResources().getColor(R.color.gray_99));
                }
            }

        }

    }

    /**
     * 根据周几返回日期
     *
     * @param week 周几
     * @return 日期
     */
    public String getWeekDateString(int week) {
        for (int i = 0; i < mDate.getWeekStr().size(); i++) {
            if (week == mDate.getWeekStr().get(i).getWeek()) {
                return mDate.getWeekStr().get(i).getWeekStr();
            }
        }
        return "";
    }

    /**
     * 根据周几返回日期
     *
     * @param time 时间 段
     * @return 时间
     */
    public String getTimeDataString(int time) {
        return mDate.getTimeStr()[time-1];
    }


    @OnClick({R.id.icon, R.id.commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon:
                dismiss();
                break;
            case R.id.commit:
                if (onClickCallback != null) {
                    onClickCallback.onClick(mHm);
                    dismiss();
                }
                break;
        }
    }


    /**
     * 回调
     */
    public interface OnClickCallback {
        void onClick(HashMap<Integer, ArrayList<Integer>> selectData);
    }

    /**
     * 设置回调
     *
     * @param callback
     */
    public void setOnClickCallback(OnClickCallback callback) {
        onClickCallback = callback;
    }


}
