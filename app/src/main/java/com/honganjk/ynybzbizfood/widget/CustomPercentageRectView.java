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


/**
 * 说明：CustomPercentageRectView(自定义的长方形百分比进度)
 * 作者　　: 杨阳
 * 创建时间: 2016/11/23 14:20
 * <p>
 * 使用方法
 * <com.hanbang.lixinghang.widget.CustomPercentageRectView
 * android:id="@+id/myViewC"
 * android:layout_width="match_parent"
 * android:layout_height="30dp"
 * android:layout_margin="@dimen/dp_20"
 * app:CPRVCompleteColor="@color/green"
 * app:CPRVProgress="0.8"
 * app:CPRVResidueColor="@color/main_color"
 * app:CPRVStrokeColor="@color/gray_33"
 * app:CPRVStrokeWidth="@dimen/dp_2" />
 */

public class CustomPercentageRectView extends View {
    //边框颜色
    public int strokeColor = 0xff676767;
    //剩余颜色
    public int residueColor = 0xffffaf00;
    //完成颜色
    public int completeColor = 0xFF22a91c;
    //边线
    public float strokeWidth = 4f;
    //进度比例
    public float progress = 0.5f;
    //进度的值
    float progressValue = 0;
    //定义创建画笔
    Paint paint = new Paint();

    public CustomPercentageRectView(Context context) {
        super(context);
    }

    public CustomPercentageRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomPercentageRectView);
        strokeColor = ta.getColor(R.styleable.CustomPercentageRectView_CPRVStrokeColor, strokeColor);
        residueColor = ta.getColor(R.styleable.CustomPercentageRectView_CPRVResidueColor, residueColor);
        completeColor = ta.getColor(R.styleable.CustomPercentageRectView_CPRVCompleteColor, completeColor);
        strokeWidth = ta.getDimension(R.styleable.CustomPercentageRectView_CPRVStrokeWidth, strokeWidth);
        progress = ta.getFloat(R.styleable.CustomPercentageRectView_CPRVProgress, progress);


    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //边框颜色
        paint.setColor(strokeColor);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paint);
        //剩余的颜色
        paint.setColor(residueColor);
        canvas.drawRect(new RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth), paint);

        //进度的颜色
        paint.setColor(completeColor);
        canvas.drawRect(new RectF(strokeWidth, strokeWidth, progressValue, getHeight() - strokeWidth), paint);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        progressValue = getWidth() * progress - strokeWidth;
    }

    /**
     * 设置进度
     *
     * @paraa 0-1 （1为100%）
     */
    public void setProgress(float progress) {
        this.progress = progress;
        animation();
    }

    /**
     * 增加动画
     */
    private void animation() {
        progressValue = getWidth() * progress - strokeWidth;
        ValueAnimator vA = ValueAnimator.ofInt(0, (int) progressValue);
        vA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressValue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        vA.setDuration(2000);
        vA.start();
    }
}
