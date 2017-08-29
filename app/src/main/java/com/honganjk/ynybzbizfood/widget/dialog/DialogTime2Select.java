package com.honganjk.ynybzbizfood.widget.dialog;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.LoopViewData;
import com.honganjk.ynybzbizfood.utils.other.DateUtils;
import com.honganjk.ynybzbizfood.utils.ui.ScreenInfoUtils;
import com.honganjk.ynybzbizfood.utils.ui.ToastUtils;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopListener;
import com.honganjk.ynybzbizfood.widget.wheelview3d.LoopView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：城市选择的对话框
 * 作者　　: 杨阳
 * 创建时间: 2016/11/21 10:41
 */

public class DialogTime2Select extends Dialog {
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.selectName)
    TextView selectName;
    @BindView(R.id.affirm)
    TextView affirm;
    //记录选择的名字与id
    PCCData pccData = new PCCData();
    Context context;
    CallBack callBack;
    //保存从资源的解析出来的json对象
    JSONArray jsonArrayParent;
    @BindView(R.id.dialog_root)
    LinearLayout dialogRoot;
    ArrayList<LoopViewData> days = new ArrayList<>(), times = new ArrayList<>();
    @BindView(R.id.day)
    LoopView day;
    @BindView(R.id.time)
    LoopView time;

    public DialogTime2Select(Context context) {
        this(context, R.style.Dialog_select);
        this.context = context;
    }

    public DialogTime2Select(Context context, int themeResId) {
        super(context, themeResId);

    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_dialog_time2_select);
        ButterKnife.bind(this, getWindow().getDecorView().findViewById(R.id.dialog_root));

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        ScreenInfoUtils screen = new ScreenInfoUtils();
        lp.width = (screen.getWidth()); //设置宽度
        lp.height = (ActionBar.LayoutParams.WRAP_CONTENT); //设置宽度
        getWindow().setAttributes(lp);
        getWindow().getAttributes().gravity = Gravity.BOTTOM;

        init();
        addListener();

    }

    /**
     * @param day  1为今天 ，2为明天
     * @param time 时间范围
     * @param type 餐饮类型：1午餐，2晚餐
     */

    public void setData(int day, String time, int type) {

        int beginHours = Integer.valueOf(time.substring(0, time.indexOf(":")));
        int endHours = Integer.valueOf(time.substring(time.lastIndexOf("-") + 1, time.lastIndexOf(":")));
        int beginMins = Integer.valueOf(time.substring(time.indexOf(":") + 1, time.indexOf("-")));
        int endMins = Integer.valueOf(time.substring(time.lastIndexOf(":") + 1, time.length()));
        //根据餐饮类型，显示对应的时间
        if (type == 1 && endHours >= 14) {
            endHours = 14;
        } else if (type == 2 && beginHours <= 16) {
            beginHours = 16;
        }


        if (day == 1) {
            days.add(new LoopViewData(day, "今天"));
            Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
            t.setToNow(); // 取得系统时间。
            int hour = t.hour; // 0-23
            int min = t.minute;
            int startHours, startMins;
            if (hour > beginHours) {
                startHours = hour;
                startMins = min;
            } else if (hour == beginHours) {
                startHours = hour;
                startMins = Math.max(beginMins, min);
            } else {
                startHours = beginHours;
                startMins = beginMins;
            }
            for (int h = startHours; h < endHours; h++) {
                LoopViewData data;
                if ((h + 1 >= endHours) && (startMins > endMins)) {
                    data = new LoopViewData(h, get2b(h) + ":" + get2b(startMins) + "-" + get2b(endHours) + ":" + get2b(endMins));
                } else {
                    data = new LoopViewData(h, get2b(h) + ":" + get2b(startMins) + "-" + get2b(h + 1) + ":" + get2b(startMins));
                }
                times.add(data);

            }
        } else {

            days.add(new LoopViewData(day, "明天"));
            for (int h = beginHours; h < endHours; h++) {
                LoopViewData data;
                if (h + 1 >= endHours && endMins <= beginMins) {
                    data = new LoopViewData(h, get2b(h) + ":" + get2b(beginMins) + "-" + get2b(endHours) + ":" + get2b(endMins));
                } else {
                    data = new LoopViewData(h, get2b(h) + ":" + get2b(beginMins) + "-" + get2b(h + 1) + ":" + get2b(beginMins));
                }

                times.add(data);
            }
        }


        initJsonData();
    }

    private String get2b(int time) {
        if (time < 10) {
            return new String("0" + time);
        } else {
            return new String("" + time);
        }
    }

    private void init() {
        day.setNotLoop();
        time.setNotLoop();
        day.setTextSize(18f);
        time.setTextSize(18f);
    }

    private void addListener() {
        day.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                addData(data, 1);
            }
        });
        time.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item, LoopViewData data) {
                addData(data, 2);
            }
        });

    }

    @OnClick({R.id.cancel, R.id.affirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.affirm:
                if (callBack != null) {

                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                            "yyyy-MM-dd");
                    java.util.Calendar calendar = java.util.Calendar
                            .getInstance();
                    calendar.roll(java.util.Calendar.DAY_OF_YEAR, 1);
                    String format = df.format(calendar.getTime());
                    String todaytime = "";
                    Time time = new Time();
                    time.setToNow();
                    int year = time.year;
                    int month = (time.month + 1);
                    int monthDay = time.monthDay;

                    if (pccData.day.equals("今天")) {
                        todaytime = (year + "-" + month + "-" + monthDay);
                    } else if (pccData.day.equals("明天")) {
                        todaytime = format;
                    }
                    String sendtime = todaytime + " " + pccData.time + "送达";

                    String startTime=todaytime+" "+pccData.time.substring(0,pccData.time.indexOf("-"))+":00";
                    String endTime=todaytime+" "+pccData.time.substring(pccData.time.indexOf("-")+1,pccData.time.length())+":00";

                    callBack.setData(sendtime, DateUtils.dateToLong(startTime,"yyyy-MM-dd HH:mm:ss"),DateUtils.dateToLong(endTime,"yyyy-MM-dd HH:mm:ss"));
                }
                dismiss();
                break;
        }
    }

    /**
     * @param data
     */
    private void addData(LoopViewData data, int type) {
        if (type == 1) {
            pccData.day = data.getName();
            pccData.dayId = data.getId();

        } else if (type == 2) {
            pccData.time = data.getName();
            pccData.timeId = data.getId();
        }
        selectName.setText(pccData.day + "-" + pccData.time);
    }

    /**
     * 给选择控件赋值
     */
    private void initJsonData() {
        day.setArrayList(days);
        time.setArrayList(times);
        day.setInitPosition(0);
        time.setInitPosition(0);
        if (days.size() == 0 || times.size() == 0) {
            this.dismiss();
            ToastUtils.getToastShort("未在服务时间");
        } else {
            addData(days.get(0), 1);
            addData(times.get(0), 2);
        }
    }


    /**
     * 选择的内容
     */
    public class PCCData {
        public String day;
        public String time;
        public int dayId;
        public int timeId;
    }

    public interface CallBack {
        void setData(String time,long startTime,long endTime);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;

    }
}
