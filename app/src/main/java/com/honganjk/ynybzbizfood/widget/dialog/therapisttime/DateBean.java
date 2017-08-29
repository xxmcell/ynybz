package com.honganjk.ynybzbizfood.widget.dialog.therapisttime;

import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.ValideDataBean;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 类说明:康复师时间对象
 * 阳（360621904@qq.com）  2017/4/20  17:41
 */

public class DateBean {
    //周日期
    private ArrayList<WeekTimeBean> weekStr = new ArrayList<>();
    //时间段
    private ArrayList<TimeBean> validTimes = new ArrayList<>();

    //时间段
    private String[] timeStr = new String[]{"7:00-9:00", "9:30-11:30", "12:00-14:00", "14:30-16:30", "17:00-19:00"};

    /**
     * 添加日期
     *
     * @return
     */
    public ArrayList<WeekTimeBean> getWeekStr() {
        //日期对象
        Calendar mCalendar = Calendar.getInstance();

        //今天
        mCalendar.set(Calendar.DATE, (mCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        WeekTimeBean data1 = new WeekTimeBean();
        String str1 = "【明天】" + (mCalendar.get(Calendar.MONTH) + 1) + "月" + mCalendar.get(Calendar.DAY_OF_MONTH) + "日";
        data1.setWeekStr(str1);
        data1.setWeek(DateUtils.getDayOfWeekInt(mCalendar, mCalendar.get(Calendar.DAY_OF_MONTH)));
        weekStr.add(data1);
        //明天
        mCalendar.set(Calendar.DATE, (mCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        WeekTimeBean data2 = new WeekTimeBean();
        String str2 = "【后天】" + (mCalendar.get(Calendar.MONTH) + 1) + "月" + mCalendar.get(Calendar.DAY_OF_MONTH) + "日";
        data2.setWeekStr(str2);
        data2.setWeek(DateUtils.getDayOfWeekInt(mCalendar, mCalendar.get(Calendar.DAY_OF_MONTH)));
        weekStr.add(data2);

        //其它日期
        for (int i = 2; i < 7; i++) {
            mCalendar.set(Calendar.DATE, (mCalendar.get(Calendar.DAY_OF_MONTH) + 1));
            WeekTimeBean data = new WeekTimeBean();
            String str = "【" + "周" + DateUtils.getDayOfWeekStr(mCalendar, mCalendar.get(Calendar.DAY_OF_MONTH)) + "】" + (mCalendar.get(Calendar.MONTH) + 1) + "月" + mCalendar.get(Calendar.DAY_OF_MONTH) + "日";
            data.setWeekStr(str);
            data.setWeek(DateUtils.getDayOfWeekInt(mCalendar, mCalendar.get(Calendar.DAY_OF_MONTH)));
            weekStr.add(data);
        }
        return weekStr;
    }

    public String[] getTimeStr() {
        return timeStr;
    }

    public ArrayList<TimeBean> getValidTimes() {
        return validTimes;
    }

    /**
     * 添加有效时间
     *
     * @param validTime
     */
    public void setValidTime(ValideDataBean validTime) {
        ArrayList<int[]> list = new ArrayList<>();
        if (validTime != null) {
            list.add(validTime.getA1());
            list.add(validTime.getA2());
            list.add(validTime.getA3());
            list.add(validTime.getA4());
            list.add(validTime.getA5());
            list.add(validTime.getA6());
            list.add(validTime.getA7());
        }
        //没有传有效日期，长度默认为7
        for (int i = 0; i < (list.size() == 0 ? 7 : list.size()); i++) {
            TimeBean data = new TimeBean();
            data.setValidTime(new boolean[5]);
            data.setIsSelectTime(new boolean[5]);
            data.setWeek(i + 1);
            if (validTime != null && list.size() == 7) {
                for (int j = 0; j < list.get(i).length; j++) {
                    data.getValidTime()[list.get(i)[j] - 1] = true;
                }
            } else {
                for (int j = 0; j < 5; j++) {
                    data.getValidTime()[j] = true;
                }
            }
            validTimes.add(data);
        }

    }

    /**
     * 选择与有效的时间
     */
    class TimeBean {
        //周
        private int week;
        //有效时间
        private boolean[] ValidTime;
        //是否选择
        private boolean[] isSelectTime;


        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public boolean[] getValidTime() {
            return ValidTime;
        }

        public void setValidTime(boolean[] validTime) {
            ValidTime = validTime;
        }

        public boolean[] getIsSelectTime() {
            return isSelectTime;
        }

        public void setIsSelectTime(boolean[] isSelectTime) {
            this.isSelectTime = isSelectTime;
        }
    }


    /**
     * 周的时间
     */
    class WeekTimeBean {
        private int week;
        private String weekStr;

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getWeekStr() {
            return weekStr;
        }

        public void setWeekStr(String weekStr) {
            this.weekStr = weekStr;
        }
    }


}
