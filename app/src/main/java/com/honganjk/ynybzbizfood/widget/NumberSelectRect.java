package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/28 14:02
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：数量选择控件
 */

public class NumberSelectRect extends LinearLayout {
    private Context context;
    private ImageView subtract;
    private ImageView add;
    private EditText num;
    //选择的数量
    private int selectNum = 1;
    private float textSize = 12;
    private OnClickCallback onClickCallback;



    public NumberSelectRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_number_select_rect, this, true);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberSelectCircle);
        textSize = typedArray.getDimension(R.styleable.NumberSelectCircle_SingleTitleTextSize, textSize);

        //边框取消
        findViewById(R.id.parent).setBackgroundDrawable(getGradientDrawable(context, context.getResources().getColor(R.color.translucent), context.getResources().getColor(R.color.gray), 2, 1));

        subtract = (ImageView) findViewById(R.id.customSubtract);
        num = (EditText) findViewById(R.id.number);
        add = (ImageView) findViewById(R.id.customAdd);
        typedArray.recycle();
        setOnClick();
    }


    public void setOnClick() {
        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectNum == 1) {
                    return;
                }
                num.setText(String.valueOf(--selectNum));

                setCallback(false);
            }
        });

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                num.setText(String.valueOf(++selectNum));
                setCallback(true);
            }
        });

    }


    private void setCallback(boolean addSubtract){
        if (onClickCallback != null) {
            onClickCallback.onClick(addSubtract,selectNum);
        }
    }



    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
        num.setText("" + selectNum);
    }

    /**
     * 回调接口
     */
    public interface OnClickCallback {
        /**
         *
         * @param addSubtract false为减，true为加
         * @param content
         * @return
         */
        boolean onClick(boolean addSubtract,int content);
    }

    /**
     * 设置回调
     *
     * @param callback
     */
    public void setOnClickCallback(OnClickCallback callback) {
        onClickCallback = callback;
    }

    /**
     * @param fillColor   = Color.parseColor("#DFDFE0");//内部填充颜色
     * @param strokeColor = Color.parseColor("#2E3135");//边框颜色
     * @param roundRadius = 15; // 8dp 圆角半径
     * @param strokeWidth = 5; // 3dp 边框宽度
     * @return
     */
    public static GradientDrawable getGradientDrawable(Context context, int fillColor, int strokeColor, float roundRadius, float strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(fillColor);
        drawable.setStroke(DimensUtils.dip2px(context, strokeWidth), strokeColor);
        drawable.setCornerRadius(DimensUtils.dip2px(context, roundRadius));
        return drawable;
    }


}
