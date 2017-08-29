package com.honganjk.ynybzbizfood.utils.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.widget.autoloadding.PagingHelp;

import java.util.List;

/**
 * **
 * 创建时间:2016/9/2　13:38
 *
 * <p>
 * 功能介绍：
 */

public class AnimUtils {
    /*
     * listview item 3d delete
	 */

    public static void deleteItemAnimationRotationX(final View item
            , final int position,
                                                    final PagingHelp pagingHelp, int duration, final OnItemDeleteListener onItemDeleteListener) {
        final int originHeight = item.getMeasuredHeight();
        final int originWidth = item.getMeasuredWidth();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(item, "alpha", 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(item, "rotationX", -80);
        item.setPivotX(originWidth / 2);
        scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator value) {
                float cVal = (float) value.getCurrentPlayTime()
                        / value.getDuration();
                item.getLayoutParams().height = (int) (originHeight - originHeight
                        * cVal);
                item.requestLayout();

            }
        });
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha).with(scaleY);
        animatorSet.setDuration(duration);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator arg0) {

                item.setRotationX(0);
                item.setAlpha(1);
                item.setPivotX(0);
                if (pagingHelp != null) {
                    pagingHelp.deleteOneItem();
                }
                onItemDeleteListener.onDelete();

            }

        });
        animatorSet.start();
    }

    public static void deleteItemAnimationRotationX(final View item, final int position, int duration, final OnItemDeleteListener onItemDeleteListener) {
        deleteItemAnimationRotationX(item, position, null, duration, onItemDeleteListener);
    }

    public static void deleteItemAnimationRotationX(final View item,
                                                    final List<?> listDatas, final int position, final OnItemDeleteListener onItemDeleteListener) {
        deleteItemAnimationRotationX(item, position, null, 400, onItemDeleteListener);
    }

    /*
     * 上下抖动动画，用于提醒用户去点击 rotation:转动角度,
     */
    public static void shakeUp(View view, float scaleMax, float rotation) {
        getShakeUp(view, scaleMax, rotation).start();

    }

    public static ObjectAnimator getShakeUp(View view, float scaleMax, float rotation) {
        // Keyframe是一个时间/值对，用于定义在某个时刻动画的状态
        // 在不同时间段的X轴0.8-1.1的缩放
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(
                "scaleX", Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(0.1f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.2f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.3f, scaleMax),
                Keyframe.ofFloat(0.4f, scaleMax),
                Keyframe.ofFloat(0.5f, scaleMax),
                Keyframe.ofFloat(0.6f, scaleMax),
                Keyframe.ofFloat(0.7f, scaleMax),
                Keyframe.ofFloat(0.8f, scaleMax),
                Keyframe.ofFloat(0.9f, scaleMax), Keyframe.ofFloat(1f, 1f));
        // 在不同时间段的Y轴0.8-1.1的缩放
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(
                "scaleY", Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(0.1f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.2f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.3f, scaleMax),
                Keyframe.ofFloat(0.4f, scaleMax),
                Keyframe.ofFloat(0.5f, scaleMax),
                Keyframe.ofFloat(0.6f, scaleMax),
                Keyframe.ofFloat(0.7f, scaleMax),
                Keyframe.ofFloat(0.8f, scaleMax),
                Keyframe.ofFloat(0.9f, scaleMax), Keyframe.ofFloat(1f, 1f));

        // 在不同时间段的旋转 旋转角度 = 0.3*抖动系数
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
                "rotation", Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, rotation),
                Keyframe.ofFloat(0.2f, -rotation),
                Keyframe.ofFloat(0.3f, rotation),
                Keyframe.ofFloat(0.4f, -rotation),
                Keyframe.ofFloat(0.5f, rotation),
                Keyframe.ofFloat(0.6f, -rotation),
                Keyframe.ofFloat(0.7f, rotation),
                Keyframe.ofFloat(0.8f, -rotation),
                Keyframe.ofFloat(0.9f, rotation), Keyframe.ofFloat(1f, 0f));
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, pvhScaleX, pvhScaleY, pvhRotation).setDuration(1000);
        return objectAnimator;
    }

    /*
     * 左右抖动动画，用于表单验证失败 translation:平移距离, 0.85, 6
     */
    public static void shakeLeft(View view, float scaleMax, float translation) {
        translation = DimensUtils.dip2px(view.getContext(), translation);
        // Keyframe是一个时间/值对，用于定义在某个时刻动画的状态
        // 在不同时间段的X轴0.8-1.1的缩放
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(
                "scaleX", Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(0.1f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.2f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.3f, scaleMax),
                Keyframe.ofFloat(0.4f, scaleMax),
                Keyframe.ofFloat(0.5f, scaleMax),
                Keyframe.ofFloat(0.6f, scaleMax),
                Keyframe.ofFloat(0.7f, scaleMax),
                Keyframe.ofFloat(0.8f, scaleMax),
                Keyframe.ofFloat(0.9f, scaleMax), Keyframe.ofFloat(1f, 1f));
        // 在不同时间段的Y轴0.8-1.1的缩放
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(
                "scaleY", Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(0.1f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.2f, scaleMax - 0.3f),
                Keyframe.ofFloat(0.3f, scaleMax),
                Keyframe.ofFloat(0.4f, scaleMax),
                Keyframe.ofFloat(0.5f, scaleMax),
                Keyframe.ofFloat(0.6f, scaleMax),
                Keyframe.ofFloat(0.7f, scaleMax),
                Keyframe.ofFloat(0.8f, scaleMax),
                Keyframe.ofFloat(0.9f, scaleMax), Keyframe.ofFloat(1f, 1f));

        // 在不同时间
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
                "translationX", Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, translation),
                Keyframe.ofFloat(0.2f, -translation),
                Keyframe.ofFloat(0.3f, translation),
                Keyframe.ofFloat(0.4f, -translation),
                Keyframe.ofFloat(0.5f, translation),
                Keyframe.ofFloat(0.6f, -translation),
                Keyframe.ofFloat(0.7f, translation),
                Keyframe.ofFloat(0.8f, -translation),
                Keyframe.ofFloat(0.9f, translation), Keyframe.ofFloat(1f, 0f));
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, pvhScaleX, pvhScaleY, pvhRotation).setDuration(1000);
        objectAnimator.start();

    }

    /**
     * 缩放动画，先放大，后缩小，   用于提醒用户状态的变化
     */
    public static void scaleAnim(View view, float scaleMax, float scaleMin, int duration) {

        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("scaleX", 1, scaleMax, 1, scaleMin, 1);
        PropertyValuesHolder scY = PropertyValuesHolder.ofFloat("scaleY", 1, scaleMax, 1, scaleMin, 1);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, scx, scY).setDuration(duration);
        objectAnimator.start();


    }

    public static void scaleAnim(View view) {
        scaleAnim(view, 1.3f, 0.5f, 800);
    }


    /**
     * **
     * 创建时间: 2016/9/2 16:33
     * <p>
     * 方法功能：Y轴翻转
     */

    public static void rotationAnim(View view, float start, float end, int duration) {

        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("rotationY", start, end);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, scx).setDuration(duration);
        objectAnimator.start();


    }

    /**
     * **
     * 创建时间: 2016/9/2 16:33
     * <p>
     * 方法功能：X轴翻转
     */

    public static void rotationAnimY(View view, float start, float end, int duration) {


        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("rotation", start, end);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scx).setDuration(duration);
        objectAnimator.start();


    }

    public static void rotationAnim(View view) {
        rotationAnim(view, 180, 360, 800);
    }


    /**
     * **
     * 创建时间: 2016/10/12 10:01
     * <p>
     * 方法功能：改变textVIew 的字体动画
     */
    public static void updateTextSize(final TextView textView, float fromSize, float toSize) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromSize, toSize);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue);
            }
        });
        animator.start();
    }

    /**
     * **
     * 创建时间: 2016/10/12 10:02
     * <p>
     * 方法功能：更新view的透明度
     */

    public static void updateAlpha(final View view, float fromValue, float toValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromValue, toValue);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                view.setAlpha(animatedValue);
            }
        });
        animator.start();
    }

    /**
     * **
     * 创建时间: 2016/10/12 10:02
     * <p>
     * 方法功能：更新textView的字体颜色
     */

    public static void updateTextColor(final TextView textView, @ColorInt int fromColor,
                                       @ColorInt int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    /**
     * **
     * 创建时间: 2016/10/12 10:03
     * <p>
     * 方法功能：更新背景色
     */

    public static void updateViewBackgroundColor(final View view, @ColorInt int fromColor,
                                                 @ColorInt int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }








    /**
     * 说明:按压动画
     * 360621904@qq.com 杨阳 2017/3/4  16:30
     */
    public static void pressAnimationListener(final View view, final OnClickListenerCallback callback) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scaleAnim(view, 0.9f, 1f, 500, null);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    scaleAnim(view, 1f, 0.9f, 500, callback);
                }

                return false;
            }
        });

        scaleAnim(view, 1f, 0.9f, 500, callback);

    }


    public static void pressAnimationListener(final View view) {
        pressAnimationListener(view, null);

    }


    /**
     * 缩放动画
     *
     * @param view
     * @param scaleMax 结束比例
     * @param scaleMin 开始比例
     * @param duration 时间
     */
    public static void scaleAnim(final View view, float scaleMax, float scaleMin, int duration, final OnClickListenerCallback callback) {

        if (callback != null) {
            view.setEnabled(false);
        }

        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("scaleX", scaleMin, scaleMax);
        PropertyValuesHolder scY = PropertyValuesHolder.ofFloat("scaleY", scaleMin, scaleMax);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, scx, scY).setDuration(duration);


        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null) {
                    view.setEnabled(true);
                    callback.clickCallback(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


        objectAnimator.start();


    }

    /**
     * 点击进入的回调
     */
    public interface OnClickListenerCallback {
        void clickCallback(boolean isOnClick);
    }


}
