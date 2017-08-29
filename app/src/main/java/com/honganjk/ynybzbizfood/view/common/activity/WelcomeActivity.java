package com.honganjk.ynybzbizfood.view.common.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.ActivityManager;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo;
import com.honganjk.ynybzbizfood.mode.third.BaiDuLocationUtils;
import com.honganjk.ynybzbizfood.view.home.activity.MainHomeActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {
    private int time = 3000;
    private boolean isToNext = false;
    private boolean isFirst = false;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.shimmer_tv)
    ShimmerTextView text;
    Shimmer shimmer;
    boolean isLocation = false;

    @Override
    public int getStatusBarResource() {
        return R.color.translucent;
    }

    @Override
    public int getStatusBarPaddingTop() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否从信鸽推送通知栏打开的，
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            finish();
            return;
        }

        Observable.timer(time, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        while (!isToNext) {
                        }
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aLong) {
                        if (isFirst) {
                        } else {
                            MainHomeActivity.startUI(WelcomeActivity.this);
                            isLocation = true;
                            finish();
                        }
                    }
                });
        next();
        getLocation();
    }

    /**
     * 获取当前位置
     */
    private void getLocation() {
        UserInfo.setUserLocation(new UserInfo.LocationListener() {
            @Override
            public void callBack(boolean isLocation) {
                if (!(ActivityManager.getInstance().currentActivity() instanceof WelcomeActivity)) {
                    finish();
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_welcom;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaiDuLocationUtils.getInstance().stop();
    }

    @Override
    public void initView() {
        final AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.7f, 1.4f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.7f, 1.4f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.6f, 1f);

        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(text, "scaleX", 0.5f, 1.3f, 1f);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(text, "scaleY", 0.5f, 1.3f, 1f);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(text, "alpha", 0.2f, 1f);

        scaleX.setDuration(time);
        scaleY.setDuration(time);
        alpha.setDuration(time);
        scaleX2.setDuration(time);
        scaleY2.setDuration(time);
        alpha2.setDuration(time);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setDuration(time);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
//        animSet.playTogether(scaleX, scaleY, alpha, scaleX2, scaleY2, alpha2);
//        animSet.start();
        shimmer = new Shimmer();
        shimmer.start(text);
        text.setVisibility(View.GONE);
    }

    @Override
    public void parseIntent(Intent intent) {
    }

    private void next() {
        if (isLogin(false)) {
            // 检查密码的正确性
            isToNext = true;
        } else {
            // 未登录
            isToNext = true;
        }
    }

    public void finish() {
        shimmer.cancel();
        super.finish();
    }

}
