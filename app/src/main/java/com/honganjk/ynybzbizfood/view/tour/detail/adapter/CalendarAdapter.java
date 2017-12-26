package com.honganjk.ynybzbizfood.view.tour.detail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.tour.detail.TourDetailBean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * 日历gridview中的每一个item显示的textview
 */
public class CalendarAdapter extends BaseAdapter {

    private static String TAG = "CalendarAdapter";
    private boolean isLeapyear = false; //是否为闰年
    private int daysOfMonth = 0;   //某月的天数
    private int dayOfWeek = 0;    //具体某一天是星期几
    private int lastDaysOfMonth = 0; //上一个月的总天数
    private Context context;
    private String[] dayNumber = new String[42]; //一个gridview中的日期存入此数组中
    private SpecialCalendar sc = null;
    private int currentYear = 0;
    private int currentMonth = 0;
    /**
     * 当前选中的日期位置
     */
    private int currentFlag = -1;
    /**
     * 当前选中天的字符串 例:20170830
     */
    private String currentDayStr;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private String showYear = "";  //用于在头部显示的年份
    private String showMonth = ""; //用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = "";  //闰哪一个月
    private Set<String> mSet = null;
    private List<TourDetailBean.DataBean.Formats> mFormatsList; //旅游时间信息

    public CalendarAdapter(Context context, int year, int month, String currentDayStr, List<TourDetailBean.DataBean.Formats> formatsList) {
        this.context = context;
        sc = new SpecialCalendar();
        currentYear = year;
        currentMonth = month; //得到跳转到的月份
        this.currentDayStr = currentDayStr;
        mFormatsList = formatsList;
        getCalendar(currentYear, currentMonth);

    }

    @Override
    public int getCount() {
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myViewHolder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_time, null);
            myViewHolder = new ViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }
        myViewHolder.mIdTvItemSelectTimeDay.setText(dayNumber[position]);
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            // 当前月信息显示
            if (position < currentFlag) {
                myViewHolder.mIdTvItemSelectTimeDay.setTextColor(Color.GRAY);//今天之前设置为灰
                myViewHolder.mTvPrice.setText("");
                myViewHolder.mIdTvItemSelectTimeDay.setTag(false);
            } else {
                myViewHolder.mIdTvItemSelectTimeDay.setTextColor(Color.GRAY);// 当月字体设黑
                myViewHolder.mTvPrice.setText("");
                for (TourDetailBean.DataBean.Formats formats : mFormatsList) {
                    if (TextUtils.equals(formats.getTime().split("-")[1], String.valueOf(currentMonth))) {
                        if (TextUtils.equals(formats.getTime().split("-")[2], String.valueOf(dayNumber[position]))) {
                            myViewHolder.mIdTvItemSelectTimeDay.setTextColor(Color.BLACK);// 当月并且是有项目字体设黑
                            myViewHolder.mTvPrice.setText("￥" + formats.getPrice() + "起");
                            myViewHolder.mIdTvItemSelectTimeDay.setTag(true);
                        } else {
                            myViewHolder.mIdTvItemSelectTimeDay.setTag(false);
                        }
                    } else {
                        myViewHolder.mIdTvItemSelectTimeDay.setTag(false);
                    }
                }
            }
        } else {
            myViewHolder.mIdTvItemSelectTimeDay.setTextColor(Color.WHITE);//不是当前月为白
            myViewHolder.mTvPrice.setText("");
            myViewHolder.mIdTvItemSelectTimeDay.setTag(false);// 当月字体设黑
        }

        if (currentFlag != -1 && currentFlag == position) {
            //设置当天的背景
            myViewHolder.mIdTvItemSelectTimeDay.setText("今天");
            myViewHolder.mIdTvItemSelectTimeDay.setTextColor(R.color.green);
        } else {
            myViewHolder.mIdTvItemSelectTimeDay.setBackgroundColor(0);
        }
        return convertView;
    }

    /**
     * 得到某年的某月的天数且这月的第一天是星期几
     *
     * @param year
     * @param month
     */
    private void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);       //是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); //某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month);   //某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); //上一个月的总天数
        getWeek(year, month);
    }

    /**
     * 将一个月中的每一天的值添加入数组dayNuber中
     *
     * @param year
     * @param month
     */
    private void getWeek(int year, int month) {
        currentFlag = -1;
        int j = 1;
        //得到当前月的所有日程日期(这些日期需要标记)
        for (int i = 0; i < dayNumber.length; i++) {
            if (i < dayOfWeek) { //前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                dayNumber[i] = (temp + i) + "";
            } else if (i < daysOfMonth + dayOfWeek) {//本月
                int day = i - dayOfWeek + 1;  //得到的日期
                dayNumber[i] = i - dayOfWeek + 1 + "";
                //对于当前月才去标记当前日期
                String yearStr = String.valueOf(year);
                String monthStr = getStr(String.valueOf(month), 2);
                String dayStr = getStr(String.valueOf(day), 2);
                String timeAll = yearStr + monthStr + dayStr;
                if (timeAll.equals(currentDayStr)) {//判断选中的位置
                    currentFlag = i;
                }
                setShowYear(yearStr);
                setShowMonth(String.valueOf(month));
            } else {  //下一个月
                dayNumber[i] = j + "";
                j++;
            }
        }
    }

    /**
     * 保留N位整数,不足前面补0
     *
     * @param file String
     * @param bit  位数
     * @return
     */
    public static String getStr(String file, int bit) {
        while (file.length() < bit)
            file = "0" + file;
        return file;
    }

    /**
     * 点击每一个item时返回item中的日期
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        StringBuffer sb = new StringBuffer();
        sb.append(currentYear);
        sb.append("-");
        sb.append(currentMonth);
        sb.append("-");
        sb.append(dayNumber[position]);
        return sb.toString();
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }


    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public Set<String> getSet() {
        return mSet;
    }

    public void setSet(Set<String> set) {
        mSet = set;
    }

    class ViewHolder {
        TextView mIdTvItemSelectTimeDay;
        TextView mTvPrice;

        ViewHolder(View view) {
            mIdTvItemSelectTimeDay = view.findViewById(R.id.id_tv_item_select_time_day);
            mTvPrice = view.findViewById(R.id.tv_price);
        }
    }

    private OnDateSelectListener listener;

    public interface OnDateSelectListener {
        void onSelectDate(String dayNum);
    }

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        listener = onDateSelectListener;
    }
}