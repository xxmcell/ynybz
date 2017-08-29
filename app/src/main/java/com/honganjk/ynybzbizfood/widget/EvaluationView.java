package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.honganjk.ynybzbizfood.R;

/**
 * Created by Administrator on 2017/3/11.
 */

public class EvaluationView extends View {

    private Bitmap mFcousbitmap;
    private Bitmap mNormalbitmap;
    //自定义属性
    private int maxEvalution = 5;
    private int mWidthSize;
    private int mFocusNum;

    public EvaluationView(Context context) {
        this(context, null);
    }

    public EvaluationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaluationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EvaluationView);
        maxEvalution = a.getInt(R.styleable.EvaluationView_maxEvalution, 5);
        int norresId = a.getResourceId(R.styleable.EvaluationView_normalfive_stars, R.mipmap.bigger_grade_normal);
        int fcousResId = a.getResourceId(R.styleable.EvaluationView_focumalfive_stars, R.mipmap.bigger_grade_fcous);

        //拿到bitmap
        mFcousbitmap = BitmapFactory.decodeResource(getResources(), fcousResId);
        mNormalbitmap = BitmapFactory.decodeResource(getResources(), norresId);
        //拿到属性集合

    }

    public void setmFocusNum(int num){
        mFocusNum = num;
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量

        mWidthSize = mFcousbitmap.getWidth();
        int heightSize = mFcousbitmap.getHeight();
        //拿到
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            mWidthSize = maxEvalution * mWidthSize;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize + getTop();
        }
        setMeasuredDimension(maxEvalution * mWidthSize, heightSize + getTop());
    }

    /**
     * 绘制 几个
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < maxEvalution; x++) { //绘制第一星星 （0*星星的宽度.0）  第二个（1*星星的宽度.0） 第三个（2*星星的宽度，0）
            if (x < mFocusNum) {
                canvas.drawBitmap(mFcousbitmap, x * mWidthSize, getPaddingTop(), null);
            } else {

                canvas.drawBitmap(mNormalbitmap, x * mWidthSize, getPaddingTop(), null);
            }
        }

    }

    private boolean touchEnable = true;

    public void setTouchEnable(boolean enable){
        touchEnable = enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(!touchEnable){
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int downX = (int) event.getX();
                if (downX < 0) {
                    mFocusNum = 0;
                } else {
                    mFocusNum = (downX / mWidthSize) + 1;

                }
                break;

        }
        postInvalidate();
        return true;
    }

    public int getEvaluation() {
        return mFocusNum;
    }

}
