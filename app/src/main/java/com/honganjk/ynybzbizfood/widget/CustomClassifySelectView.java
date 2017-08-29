package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/28 14:02
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：分类选择控件
 * <p>
 * 使用方法：
 * <com.hanbang.lshm.widget.CustomClassifySelectView
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:layout_marginTop="@dimen/dp_10"
 * android:baselineAligned="false"
 * app:CSFLBackgroundColorDefault="@color/white"
 * app:CSFLBackgroundColorSelect="@color/white"
 * app:CSFLClassify="ffff|aaaa|llll|wq vb456456|4545456|456 "
 * app:CSFLMarginSize="@dimen/dp_5"
 * app:CSFLPaddingSize="@dimen/dp_10"
 * app:CSFLRoundSize="@dimen/dp_1"
 * app:CSFLTextColorSelect="@color/main_color"
 * app:CSFLStrokeColorDefault="@color/gray_99"
 * app:CSFLStrokeColor="@color/main_color"
 * app:CSFLTextColor="@color/gray_33"
 * app:CSFLTextSize="@dimen/text_xxxm"
 * app:flexDirection="row"
 * app:flexWrap="wrap"
 * app:justifyContent="flex_start">
 * <p>
 * </com.hanbang.lshm.widget.CustomClassifySelectView>
 * <p>
 * <p>
 * CSFLClassify
 * CSFLTextSize
 * CSFLMarginSize
 * CSFLPaddingSize
 * CSFLBackgroundColorDefault
 * CSFLBackgroundColorSelect
 * CSFLTextColor
 * CSFLRoundSize
 */

public class CustomClassifySelectView extends FlexboxLayout implements View.OnClickListener {
    private Context mContext;
    //回调
    private OnClickCallback onClickCallback;
    //字体大小
    private float textSize = 12;
    //字体的颜色
    private int textColor = 0xff1f1a17;
    //字体的颜色-选择时
    private int textColorSelect = 0xff000000;
    //默认的背景
    private int backgroundColorDefault = 0xffeeeeee;
    // 边框颜色
    private int strokeColor = 0xffffaf00;
    // 边框颜色-默认
    private int strokeColorDefault = 0xffbbbbbb;
    //选择时的背景
    private int backgroundColorSelect = 0xffffaf00;
    //圆角半径
    private float roundSize = 3;
    //边框线大小
    private float strokeSize = 0.5f;
    //边距
    private int marginSize = 10;
    //内部边距
    private int paddingSize = 10;


    public CustomClassifySelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomClassifySelectView);
        String str = typedArray.getString(R.styleable.CustomClassifySelectView_CSFLClassify);
        textSize = typedArray.getDimension(R.styleable.CustomClassifySelectView_CSFLTextSize, textSize);
        backgroundColorDefault = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLBackgroundColorDefault, backgroundColorDefault);
        backgroundColorSelect = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLBackgroundColorSelect, backgroundColorSelect);
        strokeColor = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLStrokeColor, strokeColor);
        textColor = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLTextColor, textColor);
        textColorSelect = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLTextColorSelect, textColorSelect);
        strokeColorDefault = typedArray.getColor(R.styleable.CustomClassifySelectView_CSFLStrokeColorDefault, strokeColorDefault);
        roundSize = typedArray.getDimension(R.styleable.CustomClassifySelectView_CSFLRoundSize, roundSize);
        marginSize = (int) typedArray.getDimension(R.styleable.CustomClassifySelectView_CSFLMarginSize, marginSize);
        paddingSize = (int) typedArray.getDimension(R.styleable.CustomClassifySelectView_CSFLPaddingSize, paddingSize);
        strokeSize = typedArray.getDimension(R.styleable.CustomClassifySelectView_CSFLStrokeSize, strokeSize);


        //根据名称添加view
        if (!TextUtils.isEmpty(str) && str.contains("|")) {
            String[] content = str.split("\\|");
            addViews(content);
        }


    }

    private void addViews(String[] content) {
        for (int i = 0; i < content.length; i++) {

            //名称
            LayoutParams ll_1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_1.leftMargin = marginSize;
            ll_1.setMargins(marginSize,marginSize,marginSize,marginSize);

            final TextView name = new TextView(mContext);
            name.setText(content[i]);
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            name.setGravity(Gravity.CENTER);
            name.setLayoutParams(ll_1);
            name.setPadding(paddingSize, paddingSize / 2, paddingSize, paddingSize / 2);
            name.setTag(content[i]);
            name.setTextColor(textColor);
            name.setOnClickListener(this);
            //选中时
            if (i == 0) {
                name.setBackgroundDrawable(getGradientDrawable(backgroundColorSelect, strokeColor, roundSize, strokeSize));
                name.setTextColor(textColorSelect);
                //默认状态
            } else {
                name.setBackgroundDrawable(getGradientDrawable(backgroundColorDefault, strokeColorDefault, roundSize, strokeSize));
            }
            addView(name);
        }

    }


    @Override
    public void onClick(View view) {

        for (int i = 0; i < getChildCount(); i++) {
            //选中时
            if (getChildAt(i).getTag().equals(view.getTag())) {
                view.setBackgroundDrawable(getGradientDrawable(backgroundColorSelect, strokeColor, roundSize, strokeSize));
                ((TextView) view).setTextColor(textColorSelect);
                if (onClickCallback != null) {
                    onClickCallback.onClick(view.getTag().toString());

                }

                //默认状态
            } else {
                getChildAt(i).setBackgroundDrawable(getGradientDrawable(backgroundColorDefault, strokeColorDefault, roundSize, strokeSize));
                ((TextView) getChildAt(i)).setTextColor(textColor);
            }
        }
    }


    /**
     * 重新设置标题
     *
     * @param title
     */
    public void setTitle(String... title) {
        if (title == null || title.length == 0) {
            return;
        }

        this.removeAllViews();
        addViews(title);
    }

    /**
     * 添加标题
     *
     * @param title
     */
    public void addTitle(String... title) {
        if (title == null || title.length == 0) {
            return;
        }
        addViews(title);
    }


    public interface OnClickCallback {
        void onClick(String content);

    }


    public void setOnClickCallback(OnClickCallback callback) {
        onClickCallback = callback;
    }

    /**
     * @param fillColor   内部填充颜色
     * @param strokeColor 边框颜色
     * @param roundRadius 圆角半径
     * @param strokeWidth 边框宽度
     * @return
     */
    public GradientDrawable getGradientDrawable(int fillColor, int strokeColor, float roundRadius, float strokeWidth) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setStroke(DimensUtils.dip2px(mContext, strokeWidth), strokeColor);
        drawable.setCornerRadius(DimensUtils.dip2px(mContext, roundRadius));
        return drawable;
    }


}
