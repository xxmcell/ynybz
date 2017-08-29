package com.honganjk.ynybzbizfood.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.honganjk.ynybzbizfood.R;

import java.util.Calendar;


/**
 * 说明：CustomPercentageCircleView ，圆形的百分比
 * 作者　　: 杨阳
 * 创建时间: 2016/11/23 14:09
 * <p>
 * <com.hanbang.lixinghang.widget.CustomPercentageCircleView
 * android:id="@+id/myView"
 * android:layout_width="150dp"
 * android:layout_height="150dp"
 * app:CPCVAngle="310"
 * app:CPCVArcColor="@color/blue"
 * app:CPCVResidueColor="@color/white"
 * app:CPCVStrokeColor="@color/blue"
 * app:CPCVStrokeWidth="3dp" />
 */

public class CustomPercentageCircleView extends View {
    //边框颜色
    public int strokeColor = 0xff3399ff;
    //剩余扇形颜色
    public int residueColor = 0xfffefefe;
    //进度扇形颜色
    public int arcColor = 0xff3399ff;
    //扇形角度
    public float angle = 360f;
    //边线
    public float strokeWidth = 6f;
    //定义创建画笔
    Paint paint = new Paint();

    public CustomPercentageCircleView(Context context) {
        super(context);

    }

    public CustomPercentageCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomPercentageCircleView);
        strokeColor = ta.getColor(R.styleable.CustomPercentageCircleView_CPCVStrokeColor, strokeColor);
        residueColor = ta.getColor(R.styleable.CustomPercentageCircleView_CPCVResidueColor, residueColor);
        arcColor = ta.getColor(R.styleable.CustomPercentageCircleView_CPCVArcColor, arcColor);
        angle = ta.getFloat(R.styleable.CustomPercentageCircleView_CPCVAngle, angle);
        strokeWidth = ta.getDimension(R.styleable.CustomPercentageCircleView_CPCVStrokeWidth, strokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //边框的圆
        paint.setColor(strokeColor);
        canvas.drawArc(new RectF(0, 0, getHeight(), getHeight()), 0, 360, true, paint);
        //背景的圆
        paint.setColor(residueColor);
        canvas.drawArc(new RectF(strokeWidth, strokeWidth, getHeight() - strokeWidth, getHeight() - strokeWidth), 0, 360, true, paint);
        //扇形
        paint.setColor(arcColor);
        canvas.drawArc(new RectF(strokeWidth - 1, strokeWidth - 1, getHeight() - strokeWidth + 1, getHeight() - strokeWidth + 1), 0, angle, true, paint);

    }


    /**
     * 设置度数
     *
     * @param angle 0-360度
     */
    public void setAngle(float angle) {

        animation(angle);
    }

    /**
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    public void setDate(long startDate, long endDate) {
        long nowDate = Calendar.getInstance().getTimeInMillis();
        float percentage = (float) ((endDate - nowDate) / (double)(endDate - startDate));
        if (percentage > 0) {
            animation(360 * percentage);
        } else {
            animation(0);
        }
    }


    /**
     * 增加动画
     *
     * @param angleNum 度数
     */
    private void animation(float angleNum) {

        ValueAnimator vA = ValueAnimator.ofInt(0, (int) (angleNum));
        vA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        vA.setDuration(2000);
        vA.start();
    }

}
