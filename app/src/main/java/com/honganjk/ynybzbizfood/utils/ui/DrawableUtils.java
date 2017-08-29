package com.honganjk.ynybzbizfood.utils.ui;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;


/**
 * **
 * 创建时间: 16:34 Administrator
 *
 * <p>
 * 功能介绍：Drawable 常用的状态变化
 */


public class DrawableUtils {

    /**
     * 获取ColorStateList实例
     * 列如 ：对TextView设置不同状态时其文字颜色。
     */

    public static ColorStateList getColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static ColorStateList getColorStateList(int normal, int pressed) {
        int[] colors = new int[]{pressed, pressed, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{-android.R.attr.state_enabled};
        states[2] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static ColorStateList getColorSelect(int normal, int select) {
        int[] colors = new int[]{select, select, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * int strokeWidth = 5; // 3dp 边框宽度
     * int roundRadius = 15; // 8dp 圆角半径
     * int strokeColor = Color.parseColor("#2E3135");//边框颜色
     * int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
     *
     * @return
     */
    public static GradientDrawable getGradientDrawable(int fillColor, int strokeColor, int roundRadius, int strokeWidth) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(MyApplication.myApp.getResources().getColor(fillColor));
        if (strokeColor > 0) {
            drawable.setStroke(DimensUtils.dip2px(MyApplication.myApp, strokeWidth), MyApplication.myApp.getResources().getColor(strokeColor));
        }
        drawable.setCornerRadius(DimensUtils.dip2px(MyApplication.myApp, roundRadius));
        return drawable;
    }

    /**
     * 获取StateListDrawable实例
     *
     * @param idNormal    默认的资源id
     * @param idPressed   按下的资源id
     * @param isEnabledId 获取焦点的资源id
     */
    public static StateListDrawable getStateListDrawable(int idNormal, int idPressed, int isEnabledId) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : MyApplication.myApp.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : MyApplication.myApp.getResources().getDrawable(idPressed);
        Drawable enabled = isEnabledId == -1 ? null : MyApplication.myApp.getResources().getDrawable(isEnabledId);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{-android.R.attr.state_enabled}, enabled);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, enabled);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }


    /**
     * 获取StateListDrawable实例
     */
    public static StateListDrawable getStateSelectDrawable(@DrawableRes int selectRes, @DrawableRes int defaultRes) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = defaultRes == -1 ? null : MyApplication.myApp.getResources().getDrawable(defaultRes);
        Drawable select = selectRes == -1 ? null : MyApplication.myApp.getResources().getDrawable(selectRes);

        bg.addState(new int[]{android.R.attr.state_selected}, select);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 获取StateListDrawable实例
     * 之定义资源，例如形状，边框，颜色
     *
     * @param colorPressed 按下的颜色
     * @param colorNormal  默认的颜色
     * @param cornerRadius 圆角半径  DP
     * @param strokeWidth  边框宽度 dP
     * @param strokeColor  边框颜色
     * @return
     */
    public static StateListDrawable getStateListDrawable(int colorNormal, int colorPressed, int cornerRadius, int strokeWidth, int strokeColor) {

        Resources resources = MyApplication.myApp.getResources();

        StateListDrawable bg = new StateListDrawable();
        Drawable pressed = createDrawable(resources.getColor(colorPressed), cornerRadius, strokeWidth, resources.getColor(strokeColor));
        LayerDrawable normal = createDrawable(resources.getColor(colorPressed), resources.getColor(colorNormal), cornerRadius, strokeWidth, resources.getColor(strokeColor));
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{-android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 更具指定的参数获取LayerDrawable, 双层展示, 一般用于默认状态下的Drawable
     *
     * @param colorPressed 按下的颜色
     * @param colorNormal  默认的颜色
     * @param cornerRadius 圆角半径
     * @param strokeWidth  边框宽度
     * @param strokeColor  边框颜色
     * @return
     */

    private static LayerDrawable createDrawable(int colorPressed, int colorNormal, int cornerRadius, int strokeWidth, int strokeColor) {
        //默认的颜色为透明，那么第一层的颜色也为透明
        if (colorNormal == MyApplication.myApp.getResources().getColor(R.color.translucent)) {
            colorPressed = MyApplication.myApp.getResources().getColor(R.color.translucent);
        }

        //第一层
        GradientDrawable drawableTop = new GradientDrawable();
        drawableTop.setCornerRadius(cornerRadius);
        drawableTop.setShape(GradientDrawable.RECTANGLE);

        drawableTop.setColor(colorPressed);
        drawableTop.setStroke(strokeWidth, strokeColor);

        //第二层
        GradientDrawable drawableBottom = new GradientDrawable();
        drawableBottom.setCornerRadius(cornerRadius);
        drawableBottom.setShape(GradientDrawable.RECTANGLE);
        drawableBottom.setColor(colorNormal);

        LayerDrawable layerDrawable = new LayerDrawable(new GradientDrawable[]{drawableTop, drawableBottom});
        //对某个内部的资源manage
        layerDrawable.setLayerInset(1, 0, 0, DimensUtils.dip2px(MyApplication.myApp, 1), DimensUtils.dip2px(MyApplication.myApp, 2));
        return layerDrawable;
    }

    /**
     * 更具指定的参数获取Drawable， 单层显示   一般用于按下状态下的Drawable
     *
     * @param colorPressed 按下的颜色
     * @param cornerRadius 圆角半径
     * @param strokeWidth  边框宽度
     * @param strokeColor  边框颜色
     * @return
     */
    private static Drawable createDrawable(int colorPressed, int cornerRadius, int strokeWidth, int strokeColor) {
        GradientDrawable drawablePressed = new GradientDrawable();
        drawablePressed.setCornerRadius(cornerRadius);
        drawablePressed.setColor(colorPressed);
        drawablePressed.setStroke(strokeWidth, strokeColor);
        return drawablePressed;
    }


    public static void setBackgroundCompat(View view, Drawable drawable) {
        int pL = view.getPaddingLeft();
        int pT = view.getPaddingTop();
        int pR = view.getPaddingRight();
        int pB = view.getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(pL, pT, pR, pB);
    }
}
