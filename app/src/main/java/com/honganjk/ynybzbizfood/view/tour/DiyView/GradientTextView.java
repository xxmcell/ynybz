package com.honganjk.ynybzbizfood.view.tour.DiyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-11-15.
 */

public class GradientTextView extends TextView {
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();

    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{leftColor, RightColor},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

    private int leftColor = Color.GRAY;

    private int RightColor = Color.GRAY;

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
    }

    public void setRightColor(int rightColor) {
        RightColor = rightColor;
    }
}
