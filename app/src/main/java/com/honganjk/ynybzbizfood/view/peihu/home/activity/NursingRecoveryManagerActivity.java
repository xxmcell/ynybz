package com.honganjk.ynybzbizfood.view.peihu.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserTherapistFiltrateData;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryFragment;
import com.honganjk.ynybzbizfood.widget.dialog.calender.DialogCalendarSelect;
import com.honganjk.ynybzbizfood.widget.dialog.calender.TimeSelectData;
import com.honganjk.ynybzbizfood.widget.dialog.therapisttime.DialogTherapistTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注释说明: 护工与康复师列表管理类
 * 阳：2017/4/1-11:25
 */
public class NursingRecoveryManagerActivity extends BaseActivity {
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    String[] titles = {"销量", "评分"};
    private int mType = -1;
    //筛选的请求对象
    public NurserTherapistFiltrateData mFiltrate;
    /**
     * 记录上次选择的时间
     * selectWeek选择的周;selectTime选择的时间段
     */
    private int selectWeek = -1;
    //记录已经选择的时间
    private ArrayList<Integer> selectTime = new ArrayList<>();

    /**
     * @param activity
     * @param type     1为护工，2为康复师
     */
    public static void startUI(Activity activity, int type) {
        Intent intent = new Intent(activity, NursingRecoveryManagerActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_nursing_recovery;
    }

    @Override
    public void parseIntent(Intent intent) {
        mType = intent.getIntExtra("type", mType);
    }

    @Override
    public void initView() {
        toolbar.setTitle(mType == 1 ? "护工" : "康复师");
        toolbar.setBack(this);
        mFiltrate = new NurserTherapistFiltrateData();
        fragmentDatas.add(NursingRecoveryFragment.getInstance(mType, 1));
        fragmentDatas.add(NursingRecoveryFragment.getInstance(mType, 2));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);

    }
    @OnClick(R.id.filtrate)
    public void onViewClicked() {
        //护工
        if (mType == 1) {
            DialogCalendarSelect d1 = new DialogCalendarSelect(this, null, "开始时间", true);

            d1.setOnClickCallback(new DialogCalendarSelect.OnClickCallback() {
                @Override
                public void onClick(ArrayList<TimeSelectData> selectData) {
                    if (selectData!=null&&selectData.size()>0) {
                        mFiltrate.setDayS(selectData);
                        mFiltrate.setFiltrate(true);
                    }else {
                        mFiltrate.setFiltrate(false);
                    }

                    ((NursingRecoveryFragment) fragmentDatas.get(0)).getFiltrateData();
                    ((NursingRecoveryFragment) fragmentDatas.get(1)).getFiltrateData();

                    //记录已经选择的时间
                    selectTime.clear();
                    for (int i = 0; i < selectData.size(); i++) {
                        selectTime.add(selectData.get(i).getData());
                    }
                }
            });
            //设置显示选择记录
            if (selectTime.size() != 0) {
                d1.setSelectData(selectTime);
            }

            d1.show();
            //康复师
        } else {
            final DialogTherapistTime dialogThreapistTime = new DialogTherapistTime(this, null, true);
            dialogThreapistTime.setOnClickCallback(new DialogTherapistTime.OnClickCallback() {
                @Override
                public void onClick(HashMap<Integer, ArrayList<Integer>> selectData) {
                    Iterator iter = selectData.entrySet().iterator();
                    //如果没有选择筛选条件，把筛选设置为false
                    if (selectData.size() == 0 && selectData.size() == 0) {
                        mFiltrate.setFiltrate(false);
                        selectWeek = -1;
                        selectTime = null;
                    } else {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Integer key = (Integer) entry.getKey();
                        ArrayList val = (ArrayList) entry.getValue();
                        mFiltrate.setFiltrate(true);
                        mFiltrate.setWeek(key);
                        mFiltrate.setTime(val);
                        selectWeek = key;
                        selectTime = val;
                    }
                    ((NursingRecoveryFragment) fragmentDatas.get(0)).getFiltrateData();
                    ((NursingRecoveryFragment) fragmentDatas.get(1)).getFiltrateData();
                }
            });
            if (selectWeek != -1 && selectTime != null && selectTime.size() > 0) {
                dialogThreapistTime.setItemSelected(selectWeek, selectTime);
            }
            dialogThreapistTime.show();

        }
    }
}
