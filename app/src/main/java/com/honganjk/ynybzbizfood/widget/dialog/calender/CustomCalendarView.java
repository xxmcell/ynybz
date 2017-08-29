package com.honganjk.ynybzbizfood.widget.dialog.calender;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;


public class CustomCalendarView extends LinearLayout {
    private Context mContext;
    //可选择日期
    private int[] mValidData;
    private Calendar mCalendar;
    // 最大有效日期（如果为true:表示大于当前日期设置为都不可选择，false:表可以选择）
    private boolean mMaxData;
    //当前日期
    int mDay;
    //回调对象
    private CustomCalendarViewOnClickCallback onClickCallback = null;

    ArrayList<CalendarData> mDateData = new ArrayList<>();

    /**
     * @param context
     * @param validData
     * @param maxData
     */
    /**
     * @param context
     * @param validData 可选择日期
     * @param maxData   最大有效日期（如果为true:表示大于当前日期设置为都不可选择，false:表可以选择）
     * @param calender  日期对象
     */
    public CustomCalendarView(Context context, int[] validData, boolean maxData, Calendar calender) {
        super(context);
        this.mContext = context;
        this.mValidData = validData;
        this.mMaxData = maxData;
        this.mCalendar = calender;
        addViews();
    }

    /**
     * 添加View
     */
    private void addViews() {
        setPadding(DimensUtils.dip2px(mContext, 0), DimensUtils.dip2px(mContext, 10), DimensUtils.dip2px(mContext, 0), DimensUtils.dip2px(mContext, 10));
        setGravity(TEXT_ALIGNMENT_CENTER);
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        RecyclerView recycleview = new RecyclerView(getContext());
        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 7));
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        recycleview.setLayoutParams(params);
        addView(recycleview);
        addData();
        recycleview.setAdapter(mAdapter);

    }




    CommonAdapter<CalendarData> mAdapter=new CommonAdapter<CalendarData>(getContext(), R.layout.dialog_calendar_select_item, mDateData) {
        @Override
        public void convert(ViewHolder holder, final CalendarData data) {
            //如果是日期为今天显示中文
            if (data.getCalendar() == mDay && !mMaxData) {
                holder.setText(R.id.content, "今天");
            } else {
                holder.setText(R.id.content, data.getCalendarStr());
            }
            //判断日期是否可选，更改字体颜色
            if (data.isChangeColor()) {
                ((TextView) holder.getView(R.id.content)).setTextColor(mContext.getResources().getColor(R.color.main_color));
            } else {
                ((TextView) holder.getView(R.id.content)).setTextColor(mContext.getResources().getColor(R.color.gray_99));
            }
            //已经选择的日期更改背景
            if (data.isSelect()) {
                ((TextView) holder.getView(R.id.content)).setBackgroundResource(R.drawable.bg_circle_green_main);
                ((TextView) holder.getView(R.id.content)).setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                ((TextView) holder.getView(R.id.content)).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }

            //日期的点击
            holder.setOnClickListener(R.id.ll, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!data.isChangeColor()) {
                        ToastUtils.getToastLong("当前日期不可选");
                        return;
                    } else {
                        data.setSelect(!data.isSelect());
                        notifyDataSetChanged();
                    }
                    if (onClickCallback != null) {
                        onClickCallback.onClick(data.isSelect(),data.getCalendar());
                    }
                }
            });
        }
    };

    /**
     * 设置已经选择的日期
     */
    public void setSelectData( ArrayList<Integer> selectTime){
        for (int i = 0; i < selectTime.size(); i++) {
            for (int j = 0; j <mDateData.size() ; j++) {
                if (selectTime.get(i) == mDateData.get(j).getCalendar()) {
                    //根据当天日期设置显示的选择的日期
                    if (!mMaxData && mDateData.get(j).getCalendar() >= mDay) {
                        mDateData.get(j).setSelect(true);

                    }
                    if (mMaxData && mDateData.get(j).getCalendar() <= mDay) {
                        mDateData.get(j).setSelect(true);
                    }

                }
            }
        }


        mAdapter.notifyDataSetChanged();
    }
    /**
     * 设置日期数据
     *
     * @return
     */
    private ArrayList<CalendarData> addData() {

        //获取1号为周几
        int week = DateUtils.getDayOfWeek(mCalendar, 1) - 1;
        //获取当月最大几呈
        int day = DateUtils.getMaxDay(mCalendar);

        for (int i = 0; i < day + week; i++) {
            CalendarData d = new CalendarData();
            //如果不是周一前面添加空白
            if (i < week) {
                d.setCalendar(-1);
                mDateData.add(d);
                //添加日期数据
            } else {
                d.setCalendar(i - week + 1);
                //如果设置了最日期不可超过当前日期，后的天数都不可选择
                if (mMaxData && d.getCalendar() >= mDay) {
                    d.setChangeColor(false);
                } else {
                    //判断是否可选日期
                    if (mValidData != null) {
                        for (int j = 0; j < mValidData.length; j++) {
                            if (d.getCalendar() == mValidData[j]) {
                                d.setChangeColor(true);
                            }
                        }
                    } else {
                        d.setChangeColor(true);
                    }

                    //低于当前日期也不可以选择
                    if (!mMaxData && d.getCalendar() <= mDay) {
                        d.setChangeColor(false);
                    }
                }
                mDateData.add(d);
            }
        }


        return mDateData;
    }


    /**
     * 回调
     */
    public interface CustomCalendarViewOnClickCallback {
        /**
         * 把选择的数据回调过去
         *
         * @param isSelect 是否被选择
         * @param data     点击的日期
         */
        void onClick(boolean isSelect, Integer data);
    }

    /**
     * 设置回调
     *
     * @param callback
     */
    public void setOnClickCallback(CustomCalendarViewOnClickCallback callback) {
        onClickCallback = callback;
    }
}



