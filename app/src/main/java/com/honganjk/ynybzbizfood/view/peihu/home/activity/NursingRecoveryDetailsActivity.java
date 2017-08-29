package com.honganjk.ynybzbizfood.view.peihu.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseMvpActivity;
import com.honganjk.ynybzbizfood.mode.javabean.peihu.home.NurserCommendDetailsData;
import com.honganjk.ynybzbizfood.pressenter.peihu.home.NursingRecoveryDetailsPresenter;
import com.honganjk.ynybzbizfood.utils.adapter.ViewPagerAdapter;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.other.DeviceUtil;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryGradeFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryHintFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.fragment.NursingRecoveryServiceFragment;
import com.honganjk.ynybzbizfood.view.peihu.home.interfaces.IHomeParentInterfaces;
import com.honganjk.ynybzbizfood.view.peihu.order.activity.SubscribeActivity;
import com.honganjk.ynybzbizfood.widget.CustomCollectView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 注释说明: 护工与康复师-详情页
 * <p>
 * 阳：2017/4/1-11:25
 */
public class NursingRecoveryDetailsActivity extends BaseMvpActivity<IHomeParentInterfaces.INursingRecoveryDetails, NursingRecoveryDetailsPresenter> implements IHomeParentInterfaces.INursingRecoveryDetails {
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.collectNumber)
    TextView collectNumber;
    @BindView(R.id.collect)
    CustomCollectView collect;
    @BindView(R.id.serverNumber)
    TextView serverNumber;
    @BindView(R.id.grade)
    TextView grade;
    @BindView(R.id.experience)
    TextView experience;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.skill)
    TextView skill;
    // 1为护工，2为康复师
    private int mType = -1;
    //个人id
    private int mId;
    ArrayList<Fragment> fragmentDatas = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    String[] titles = {"服务内容", "个人评价", "温馨提示"};
    NurserCommendDetailsData mData;


    /**
     * @param activity activity
     * @param type     1为护工，2为康复师
     * @param nid       护工或康复的个人id
     */
    public static void startUI(Context activity, int nid, int type) {
        Intent intent = new Intent(activity, NursingRecoveryDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("type", type);
        intent.putExtra("nid", nid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public NursingRecoveryDetailsPresenter initPressenter() {
        return new NursingRecoveryDetailsPresenter();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_nursing_recovery_details;
    }

    @Override
    public void parseIntent(Intent intent) {
        mType = intent.getIntExtra("type", mType);
        mId = intent.getIntExtra("nid", mId);
    }

    @Override
    public void initView() {
        if (toolbar != null) {
            toolbar.setTitle(mType == 1 ? "护工详情" : "康复师详情");
            toolbar.setBack(this, R.drawable.material_arrwos_black_left);
            toolbar.setTitleColor(R.color.black);
            toolbar.addAction(1, "联系", R.drawable.material_phone);
        }
        addListener();

        fragmentDatas.add(NursingRecoveryServiceFragment.getInstance());
        fragmentDatas.add(NursingRecoveryGradeFragment.getInstance());
        fragmentDatas.add(NursingRecoveryHintFragment.getInstance());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentDatas, titles);
        viewPage.setAdapter(adapter);
        viewPage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPage);

        presenter.getData(mId, userData.isLogin() ? userData.getCode() : -1);
        presenter.getHint();
    }

    private void addListener() {
        if (toolbar != null)
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    DeviceUtil.callByPhone(NursingRecoveryDetailsActivity.this, mData.getMobile());
                    return false;
                }
            });

        collect.setOnClickCallback(new CustomCollectView.OnClickCallback() {
            @Override
            public void onClick(boolean isSelect) {
                if (isLogin(true)) {
                    if (isSelect) {
                        presenter.setCollectCancel(mId);
                        mData.setCollect(mData.getCollect()-1);
                        collectNumber.setText(mData.getCollectStr());
                    } else {
                        presenter.setCollect(mId);
                        mData.setCollect(mData.getCollect()+1);
                        collectNumber.setText(mData.getCollectStr());
                    }
                    return;
                }
                collect.setCollect(mData.isCollected());
            }
        });


    }

    @Override
    public void clearData() {
    }

    @Override
    public void getData(NurserCommendDetailsData data) {
        mData = data;
        GlideUtils.showCircle(picture, data.getHead());
        name.setText(data.getName());
        sex.setText(data.getInfo());
        price.setText(data.getPriceStr());
        collectNumber.setText(data.getCollectStr());
        serverNumber.setText(data.getCountStr(this));
        grade.setText(data.getPointStr(this));
        experience.setText(data.getYearsStr(this));
        introduce.setText(data.getIntroduction());
        skill.setText(data.getSkill());
        //设置服务内容
        if (data.getItems() != null) {
            ((NursingRecoveryServiceFragment) fragmentDatas.get(0)).setDatas(data.getItems());
        }
        //设置评价列表
        if (data.getObjs() != null) {
            ((NursingRecoveryGradeFragment) fragmentDatas.get(1)).setDatas(data.getObjs());
        }
        collect.setCollect(data.isCollected());
    }

    @Override
    public void getHint(List<String> data) {
        //设置提示
        if (data != null) {
            ((NursingRecoveryHintFragment) fragmentDatas.get(2)).setDatas(data);
        }
    }

    @Override
    public void setCollect(boolean isCollect) {
        mData.setCollected(isCollect);
        collect.setCollect(mData.isCollected());
    }

    @Override
    public void setCollectCancel(boolean isCollect) {
        mData.setCollected(false);
        collect.setCollect(mData.isCollected());
    }


    @OnClick({R.id.serviceScope, R.id.immediatelySubscribe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.serviceScope:
                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .title("服务商圈")
                        .positiveText("确定")
                        .content(mData.getArea())
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .build();
                materialDialog.show();

                break;
            case R.id.immediatelySubscribe:
                if (isLogin(true)) {
                    SubscribeActivity.starUi(this, mId, mData.getType(),mData.getImg());
                }
                break;
        }
    }
}
