package com.honganjk.ynybzbizfood.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;


/**
 * Created by lgp on 2015/10/5.
 */
public class AnimCheckBox extends View {
    private final String TAG = "AnimCheckBox";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int radius;
    private RectF mRectF = new RectF();
    private RectF mInnerRectF = new RectF();
    private Path mPath = new Path();
    private float mSweepAngle;
    private final double mSin27 = Math.sin(Math.toRadians(27));
    private final double mSin63 = Math.sin(Math.toRadians(63));
    private float mHookStartY;
    private float mBaseLeftHookOffset;
    private float mBaseRightHookOffset;
    private float mEndLeftHookOffset;
    private float mEndRightHookOffset;
    private int size;
    private boolean mChecked = true;
    private float mHookOffset;
    private float mHookSize;
    private int mInnerCircleAlpha = 0XFF;
    private int mStrokeWidth = 2;
    private final int mDuration = 500;

    private String text;
    private float textSize = 16;
    private float textSpace = 5;
    private int textColor = getResources().getColor(R.color.main_color);

    private int mStrokeColor = getResources().getColor(R.color.main_color);
    private int mOutCircleColor = getResources().getColor(R.color.main_color);
    private int mCircleColor = getResources().getColor(R.color.gray_ee);
    private final int defaultSize = 40;
    private OnCheckedChangeListener mOnCheckedChangeListener;


    private boolean isAutoSelect = true;

    public AnimCheckBox(Context context) {
        this(context, null);
    }

    public AnimCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.AnimCheckBox);
            mStrokeWidth = (int) array.getDimension(R.styleable.AnimCheckBox_ab_strokeWidth, dip(mStrokeWidth));
            mStrokeColor = array.getColor(R.styleable.AnimCheckBox_ab_strokeColor, mStrokeColor);
            mCircleColor = array.getColor(R.styleable.AnimCheckBox_ab_circleColor, mCircleColor);
            mOutCircleColor = array.getColor(R.styleable.AnimCheckBox_ab_outColor, mOutCircleColor);
            mChecked = array.getBoolean(R.styleable.AnimCheckBox_ab_isSelect, false);
            isAutoSelect = array.getBoolean(R.styleable.AnimCheckBox_ab_autoSelect, isAutoSelect);
            text = array.getString(R.styleable.AnimCheckBox_ab_text);
            textSize = array.getDimension(R.styleable.AnimCheckBox_ab_textSize, sip((int) textSize));
            textColor = array.getColor(R.styleable.AnimCheckBox_ab_textColor, textColor);
            array.recycle();
            if (mChecked) {
                mInnerCircleAlpha = 0xFF;
                mSweepAngle = 0;
                mHookOffset = mHookSize + mEndLeftHookOffset - mBaseLeftHookOffset;
            } else {
                mInnerCircleAlpha = 0x00;
                mSweepAngle = 360;
                mHookOffset = 0;
            }
        } else {
            mStrokeWidth = dip(mStrokeWidth);
            textSize = sip((int) textSize);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStrokeColor);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAutoSelect) {
                    setChecked(!mChecked);
                    if (mOnCheckedChangeListener != null) {
                        mOnCheckedChangeListener.onChange(AnimCheckBox.this, mChecked);
                    }
                }
            }
        });
    }

    public void setAutoSelect(boolean autoSelect) {
        isAutoSelect = autoSelect;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getLayoutParams().height;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = dip(defaultSize);
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(width < height){
            width =height;
        }
        if (text != null && text.length() != 0) {
            textSpace = height / 4.0f;
            initDrawTextPaint();
            float textLength = mPaint.measureText(text);
            width = (int) (height + textSpace + textLength);
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        size = getHeight() - getPaddingTop() - getPaddingBottom();
        radius = (size - (2 * mStrokeWidth)) / 2;

        mRectF.set(mStrokeWidth + getPaddingLeft(), mStrokeWidth + getPaddingTop(), size - mStrokeWidth + getPaddingRight(), size - mStrokeWidth + getPaddingBottom());
        mInnerRectF.set(mRectF);
        mInnerRectF.inset(mStrokeWidth / 2, mStrokeWidth / 2);
        mHookStartY = (float) (size / 2 - (radius * mSin27 + (radius - radius * mSin63)));
        mBaseLeftHookOffset = (float) (radius * (1 - mSin63)) + mStrokeWidth / 2;
        mBaseRightHookOffset = 0f;
        mEndLeftHookOffset = mBaseLeftHookOffset + (2 * size / 3 - mHookStartY) * 0.33f;
        mEndRightHookOffset = mBaseRightHookOffset + (size / 3 + mHookStartY) * 0.38f;
        mHookSize = size - (mEndLeftHookOffset + mEndRightHookOffset);
        mHookOffset = mChecked ? mHookSize + mEndLeftHookOffset - mBaseLeftHookOffset : 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        drawHook(canvas);

        drwaText(canvas);
    }

    private void drwaText(Canvas canvas) {
        if (text != null && text.length() != 0) {
            initDrawTextPaint();
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();//文字的4根线，baseLine为0，top为负值， bottom为正数
            int baseline = (getHeight() - fontMetrics.top - fontMetrics.bottom) / 2;
            canvas.drawText(text, getPaddingLeft() + size + textSpace, baseline, mPaint);
            LogUtils.e(" getPaddingLeft() = " + getPaddingLeft() + "   size = " + size + "     textSpace=" + textSpace
                    + "   baseline " + baseline);
        }
    }

    private void drawCircle(Canvas canvas) {
        initDrawStrokeCirclePaint();
        canvas.drawArc(mRectF, 202, mSweepAngle, false, mPaint);
        initDrawAlphaStrokeCirclePaint();
        canvas.drawArc(mRectF, 202, mSweepAngle - 360, false, mPaint);
        initDrawInnerCirclePaint();
        canvas.drawArc(mInnerRectF, 0, 360, false, mPaint);
    }

    private void drawHook(Canvas canvas) {
        if (mHookOffset == 0)
            return;
        initDrawHookPaint();

        mPath.reset();
        float offset;
        if (mHookOffset <= (2 * size / 3 - mHookStartY - mBaseLeftHookOffset)) {
            mPath.moveTo(mBaseLeftHookOffset, mBaseLeftHookOffset + mHookStartY);
            mPath.lineTo(mBaseLeftHookOffset + mHookOffset, mBaseLeftHookOffset + mHookStartY + mHookOffset);
        } else if (mHookOffset <= mHookSize) {
            mPath.moveTo(mBaseLeftHookOffset, mBaseLeftHookOffset + mHookStartY);
            mPath.lineTo(2 * size / 3 - mHookStartY, 2 * size / 3);
            mPath.lineTo(mHookOffset + mBaseLeftHookOffset,
                    2 * size / 3 - (mHookOffset - (2 * size / 3 - mHookStartY - mBaseLeftHookOffset)));
        } else {
            offset = mHookOffset - mHookSize;
            mPath.moveTo(mBaseLeftHookOffset + offset, mBaseLeftHookOffset + mHookStartY + offset);
            mPath.lineTo(2 * size / 3 - mHookStartY, 2 * size / 3);
            mPath.lineTo(mHookSize + mBaseLeftHookOffset + offset,
                    2 * size / 3 - (mHookSize - (2 * size / 3 - mHookStartY - mBaseLeftHookOffset) + offset));
        }
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    private void initDrawHookPaint() {
        mPaint.setAlpha(0xFF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStrokeColor);
    }

    private void initDrawStrokeCirclePaint() {
        mPaint.setAlpha(0xFF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mOutCircleColor);
    }

    private void initDrawAlphaStrokeCirclePaint() {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mStrokeColor);
        mPaint.setAlpha(0x60);
    }

    private void initDrawInnerCirclePaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        mPaint.setAlpha(mInnerCircleAlpha);
    }

    private void initDrawTextPaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(textColor);
        mPaint.setAlpha(0xff);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(textSize);
    }

    private void startCheckedAnim() {
        ValueAnimator animator = new ValueAnimator();
        final float hookMaxValue = mHookSize + mEndLeftHookOffset - mBaseLeftHookOffset;
        final float circleMaxFraction = mHookSize / hookMaxValue;
        final float circleMaxValue = 360 / circleMaxFraction;
        animator.setFloatValues(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mHookOffset = fraction * hookMaxValue;
                if (fraction <= circleMaxFraction) {
                    mSweepAngle = (int) ((circleMaxFraction - fraction) * circleMaxValue);
                } else {
                    mSweepAngle = 0.00001f;
                }
                mInnerCircleAlpha = (int) (fraction * 0xFF);
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(mDuration).start();
    }

    private void startUnCheckedAnim() {
        ValueAnimator animator = new ValueAnimator();
        final float hookMaxValue = mHookSize + mEndLeftHookOffset - mBaseLeftHookOffset;
        final float circleMinFraction = (mEndLeftHookOffset - mBaseLeftHookOffset) / hookMaxValue;
        final float circleMaxValue = 360 / (1 - circleMinFraction);
        animator.setFloatValues(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float circleFraction = animation.getAnimatedFraction();
                float fraction = 1 - circleFraction;
                mHookOffset = fraction * hookMaxValue;
                if (circleFraction >= circleMinFraction) {
                    mSweepAngle = (int) ((circleFraction - circleMinFraction) * circleMaxValue);
                } else {
                    mSweepAngle = 0.00001f;
                }
                mInnerCircleAlpha = (int) (fraction * 0xFF);
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(mDuration).start();
    }

    private void startAnim() {
        clearAnimation();
        if (mChecked) {
            startCheckedAnim();
        } else {
            startUnCheckedAnim();
        }
    }


    private int getAlphaColor(int color, int alpha) {
        alpha = alpha < 0 ? 0 : alpha;
        alpha = alpha > 255 ? 255 : alpha;
        return (color & 0x00FFFFFF) | alpha << 24;
    }

    public boolean isChecked() {
        return mChecked;
    }


    /**
     * setChecked with Animation
     *
     * @param checked true if checked, false if unchecked
     */
    public void setChecked(boolean checked) {
        setChecked(checked, true);
    }

    /**
     * @param checked   true if checked, false if unchecked
     * @param animation true with animation,false without animation
     */
    public void setChecked(boolean checked, boolean animation) {
        if (checked == this.mChecked) {
            return;
        }
        this.mChecked = checked;
        if (animation) {
            startAnim();
        } else {
            if (mChecked) {
                mInnerCircleAlpha = 0xFF;
                mSweepAngle = 0;
                mHookOffset = mHookSize + mEndLeftHookOffset - mBaseLeftHookOffset;
            } else {
                mInnerCircleAlpha = 0x00;
                mSweepAngle = 360;
                mHookOffset = 0;
            }
            invalidate();
        }
    }

    private int dip(int dip) {
        return (int) getContext().getResources().getDisplayMetrics().density * dip;
    }

    private int sip(int sip) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;

        return (int) (sip * fontScale + 0.5f);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    /**
     * setOnCheckedChangeListener
     *
     * @param listener the OnCheckedChangeListener listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onChange(AnimCheckBox checkBox, boolean checked);
    }
}
