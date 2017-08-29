package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;


/**
 * 说明:收藏按钮
 * 360621904@qq.com 杨阳 2017/4/10  15:49
 * <p>
 * * 属性设置
 * <p>
 * STRondSize       -->>字体大小
 * STTextColor      -->>字体与背景颜色
 * STRondSize       -->>圆角
 * STStrokeSize     -->>边框线
 * <p>
 * 使用例子
 * <p>
 * <p>
 * <com.honganjk.ynybzbizfood.widget.CustomCollectView
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * android:layout_alignParentRight="true"
 * android:layout_below="@+id/collectNumber"
 * android:layout_marginRight="@dimen/dp_20"
 * android:layout_marginTop="@dimen/dp_5"
 * app:CCVIconSize="25"
 * app:CCVRondSize="@dimen/dp_2"
 * app:CCVTextColor="@color/main_red"
 * app:CCVTextSize="@dimen/text_xxm">
 * <p>
 * </com.honganjk.ynybzbizfood.widget.CustomCollectView>
 */
public class CustomCollectView extends LinearLayout implements OnClickListener {
    private Context context;
    private OnClickCallback onClickCallback = null;
    int textColor = 0xffff6700;
    float textSize = 12;
    int backgroundColor = 0x00ffffff;
    //收藏图标的大小
    int iconSize = 30;
    //圆角半径
    float roundSize = 3;
    //边框线大小
    float strokeSize = 1;
    //图标
    View icon;
    //名称
    private TextView name;

    public CustomCollectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCollectView);
        textSize = typedArray.getDimension(R.styleable.CustomCollectView_CCVTextSize, textSize);
        iconSize = typedArray.getInteger(R.styleable.CustomCollectView_CCVIconSize, iconSize);
        textColor = typedArray.getColor(R.styleable.CustomCollectView_CCVTextColor, textColor);
        roundSize = typedArray.getDimension(R.styleable.CustomCollectView_CCVRondSize, roundSize);
        strokeSize = typedArray.getDimension(R.styleable.CustomCollectView_CCVStrokeSize, strokeSize);
        //设置背景
        this.setBackgroundDrawable(getGradientDrawable(context, backgroundColor, textColor, roundSize, strokeSize));
        typedArray.recycle();
        setOnClickListener(this);
        addViews();
    }


    /**
     * 添加View
     */
    private void addViews() {
        setPadding(DimensUtils.dip2px(context, 8), DimensUtils.dip2px(context, 3), DimensUtils.dip2px(context, 8), DimensUtils.dip2px(context, 3));
        setGravity(TEXT_ALIGNMENT_CENTER);
        //图标
        LayoutParams ll = new LayoutParams(DimensUtils.dip2px(getContext(),iconSize), DimensUtils.dip2px(getContext(),iconSize));
        icon = new View(context);
        icon.setBackgroundResource(R.drawable.select_collect);
        icon.setLayoutParams(ll);
        addView(icon);
        //名称
        LayoutParams ll_1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
        name = new TextView(context);
        name.setText("收藏");
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        name.setGravity(Gravity.CENTER);
        name.setLayoutParams(ll_1);
        name.setPadding(DimensUtils.dip2px(context, 5), 0, 0, 0);
        name.setTextColor(textColor);
        addView(name);


    }

    @Override
    public void onClick(View v) {
        if (onClickCallback != null) {
            onClickCallback.onClick(icon.isSelected());
        }
        icon.setSelected(!icon.isSelected());
        setText();
    }


    /**
     * 设置是否收藏
     *
     * @param isCollect
     */
    public void setCollect(boolean isCollect) {
        icon.setSelected(isCollect);
        setText();
    }

    /**
     * 设置显示文本
     */
    private void setText() {
        if (icon.isSelected()) {
            name.setText("已收藏");
        } else {
            name.setText("收藏");
        }
    }

    /**
     * 回调
     */
    public interface OnClickCallback {
        void onClick(boolean isSelect);
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
