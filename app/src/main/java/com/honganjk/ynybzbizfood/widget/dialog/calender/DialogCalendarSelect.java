package com.honganjk.ynybzbizfood.widget.dialog.calender;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.widget.dialog.WrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明:日期选择
 * 360621904@qq.com 杨阳 2017/4/12  19:01
 */
public class DialogCalendarSelect extends Dialog implements CustomCalendarView.CustomCalendarViewOnClickCallback {
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    //月份的view
    ArrayList<View> mViews = new ArrayList<>();
    ViewPageAdapter adapter;
    //当前日期
    Calendar mCalendar;
    int mYear;
    int mMonth;
    int mDay;
    //可选择日期
    int[] mValidData;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.currentTime)
    TextView currentTime;
    @BindView(R.id.nextTime)
    TextView nextTime;
    @BindView(R.id.title)
    TextView title;
    //要返回的数据
    private ArrayList<TimeSelectData> selectData = new ArrayList<>();
    //回调对象
    private OnClickCallback onClickCallback;
    //当前日期对象
    private CustomCalendarView view1;
    //下个月的对象
    private CustomCalendarView view2;
    //标题
    private String mTitle;
    //是否为多选
    private boolean mIsMore;

    /**
     * @param context
     * @param validData 可选择日期
     * @param title     标题
     * @param isMore    是否多选
     */
    public DialogCalendarSelect(@NonNull Context context, int[] validData, String title, boolean isMore) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_calendar_select);
        ButterKnife.bind(this);
        mValidData = validData;
        mTitle = title;
        mIsMore = isMore;
        intView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void intView() {
        mCalendar = Calendar.getInstance();

        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mViews.add(view1 = new CustomCalendarView(getContext(), mValidData, false, mCalendar));
        mCalendar.set(Calendar.MONTH, mMonth);
        mViews.add(view2 = new CustomCalendarView(getContext(), mValidData, true, mCalendar));
        view1.setOnClickCallback(this);
        view2.setOnClickCallback(this);

        adapter = new ViewPageAdapter(mViews);
        viewPager.setAdapter(adapter);

        addData();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeStaus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 设置已经选择的日期
     */
    public void setSelectData(ArrayList<Integer> selectTime) {
        view1.setSelectData(selectTime);
        view2.setSelectData(selectTime);

        for (int i = 0; i < selectTime.size(); i++) {
            //添加返回的数据
            TimeSelectData data1 = new TimeSelectData();
            data1.setData(selectTime.get(i));
            selectData.add(data1);
        }

    }

    /**
     * 赋值
     */
    private void addData() {
        title.setText(mYear + "年" + mMonth + "月");
        currentTime.setText(mMonth + "月");
        nextTime.setText(((mMonth + 1) % 12 == 0 ? 12 : (mMonth + 1) % 12) + "月");
        hint.setText(mTitle + "(灰色表示不可选择)");
    }

    /**
     * 改变标题月份的状态
     *
     * @param position
     */
    private void changeStaus(int position) {
        //本月
        if (position == 0) {
            title.setText(mYear + "年" + ((mMonth) % 12 == 0 ? 12 : (mMonth) % 12) + "月");
            currentTime.setBackgroundResource(R.drawable.bg_circle_green_main);
            currentTime.setTextColor(getContext().getResources().getColor(R.color.white));
            nextTime.setBackgroundResource(R.drawable.bg_circle_order_maincolor);
            nextTime.setTextColor(getContext().getResources().getColor(R.color.main_color));
            //下一个月
        } else {
            title.setText((mMonth + 1 == 12 ? mYear + 1 : mYear) + "年" + ((mMonth + 1) % 12 == 0 ? 12 : (mMonth + 1) % 12) + "月");
            nextTime.setBackgroundResource(R.drawable.bg_circle_green_main);
            nextTime.setTextColor(getContext().getResources().getColor(R.color.white));
            currentTime.setBackgroundResource(R.drawable.bg_circle_order_maincolor);
            currentTime.setTextColor(getContext().getResources().getColor(R.color.main_color));
        }
    }


    @Override
    public void onClick(boolean isSelect, Integer data) {
        //去掉重复的数据
        for (int i = 0; i < selectData.size(); i++) {
            if (data == selectData.get(i).getData()) {
                selectData.remove(i);
            }
        }
        if (isSelect) {
            //添加返回的数据
            TimeSelectData data1 = new TimeSelectData();
            data1.setData(data);
            selectData.add(data1);
        }
        if (!mIsMore) {
            //返回选择的数据
            if (onClickCallback != null) {
                onClickCallback.onClick(selectData);
            }
        }

        //不是多选选择后关闭
        if (!mIsMore) {
            dismiss();
        }
    }

    @OnClick({R.id.currentTime, R.id.nextTime, R.id.icon, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.currentTime:
                viewPager.setCurrentItem(0);
                changeStaus(viewPager.getCurrentItem());
                break;
            case R.id.nextTime:
                viewPager.setCurrentItem(1);
                changeStaus(viewPager.getCurrentItem());
                break;
            case R.id.icon:
                dismiss();
                break;
            case R.id.commit:
                //返回选择的数据
                if (onClickCallback != null) {
                    onClickCallback.onClick(selectData);
                }
                dismiss();
                break;


        }
    }

    /**
     * 返回选择的数据
     */
    private void selectData() {

    }


    /**
     * 回调
     */
    public interface OnClickCallback {
        void onClick(ArrayList<TimeSelectData> selectData);
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
