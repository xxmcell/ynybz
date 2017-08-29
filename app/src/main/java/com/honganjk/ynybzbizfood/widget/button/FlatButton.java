package com.honganjk.ynybzbizfood.widget.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.ui.DrawableUtils;


public class FlatButton extends android.support.v7.widget.AppCompatTextView {

    private StateListDrawable mNormalDrawable;
    //圆角
    private float cornerRadius;
    //边框宽度
    private float strokeWidth;
    //边框颜色
    private int strokeColor;


    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public FlatButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlatButton(Context context) {
        this(context, null);

    }

    private void init(Context context, AttributeSet attrs) {
        mNormalDrawable = new StateListDrawable();
        if (attrs != null) {
            initAttributes(context, attrs);
        }
        setBackgroundCompat(mNormalDrawable);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (attr == null)
            return;
        try {
            float defValue = DimensUtils.dip2px(getContext(), 2);
            cornerRadius = attr.getDimension(R.styleable.FlatButton_cornerRadius, defValue);
            strokeColor = attr.getColor(R.styleable.FlatButton_strokeColor, strokeColor);
            strokeWidth = attr.getDimension(R.styleable.FlatButton_strokeWidth, strokeWidth);
            mNormalDrawable.addState(new int[]{android.R.attr.state_pressed},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{android.R.attr.state_focused},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{-android.R.attr.state_enabled},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{android.R.attr.state_selected},
                    createPressedDrawable(attr));
            mNormalDrawable.addState(new int[]{}, createNormalDrawable(attr));

            //文字的颜色
            int cp = getColor(R.color.translucent);
            int colorPressed = attr.getColor(R.styleable.FlatButton_colorPressedText, cp);

            int bn = getColor(R.color.translucent);
            int colorNormal = attr.getColor(R.styleable.FlatButton_colorNormalText, bn);
            if (cp != colorPressed) {
                setTextColor(DrawableUtils.getColorStateList(bn != colorNormal ? colorNormal : getCurrentTextColor(), colorPressed));
            }

        } finally {
            attr.recycle();
        }
    }

    private LayerDrawable createNormalDrawable(TypedArray attr) {
        int cp = getColor(R.color.translucent);
        int colorPressed = attr.getColor(R.styleable.FlatButton_colorPressed, cp);

        int bn = getColor(R.color.translucent);
        int colorNormal = attr.getColor(R.styleable.FlatButton_colorNormal, bn);

        //默认的颜色为透明，那么第一层的颜色也为透明
        if (colorNormal == 0) {
            colorPressed = 0;
        }
        //第一层
        GradientDrawable drawableTop = new GradientDrawable();
        drawableTop.setCornerRadius(getCornerRadius());
        drawableTop.setShape(GradientDrawable.RECTANGLE);
        drawableTop.setColor(colorPressed);
        drawableTop.setStroke((int) strokeWidth, strokeColor);

        //第二层
        GradientDrawable drawableBottom = new GradientDrawable();
        drawableBottom.setCornerRadius(getCornerRadius());
        drawableBottom.setShape(GradientDrawable.RECTANGLE);

        drawableBottom.setColor(colorNormal);

        LayerDrawable layerDrawable = new LayerDrawable(new GradientDrawable[]{drawableTop, drawableBottom});

        layerDrawable.setLayerInset(1, 0, 0, DimensUtils.dip2px(getContext(), 1), DimensUtils.dip2px(getContext(), 0));
        return layerDrawable;
    }

    private Drawable createPressedDrawable(TypedArray attr) {
        GradientDrawable drawablePressed = new GradientDrawable();
        drawablePressed.setCornerRadius(getCornerRadius());
        int cp = getColor(R.color.gray_ee);
        int colorPressed = attr.getColor(R.styleable.FlatButton_colorPressed, cp);
        drawablePressed.setColor(colorPressed);
        drawablePressed.setStroke((int) strokeWidth, strokeColor);
        return drawablePressed;
    }

    protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    protected float getDimension(int id) {
        return getResources().getDimension(id);
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public StateListDrawable getNormalDrawable() {
        return mNormalDrawable;
    }


    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     *
     * @param drawable
     */
    public void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }
}
