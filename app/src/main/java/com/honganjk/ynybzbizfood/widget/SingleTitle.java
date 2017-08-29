package com.honganjk.ynybzbizfood.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
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
import com.honganjk.ynybzbizfood.utils.ui.drawable.RadiusBackgroundDrawable;

import java.util.ArrayList;


/**
 * 作者　　: 杨阳
 * 创建时间: 2016/10/18 12:02
 * 邮箱　　：360621904@qq.com
 * <p>
 * 功能介绍：标题选择
 * <p>
 * 属性设置
 * <p>
 * STRondSize       -->>字体大小
 * STTextColor      -->>字体与背景颜色
 * STTitle          -->>标题,中间要用“|”分开
 * STRondSize       -->>圆角
 * STStrokeSize     -->>边框线
 * <p>
 * 使用例子
 * <p>
 * <com.hanbang.lixinghang.widget.SingleTitle xmlns:stit="http://schemas.android.com/apk/res-auto"
 * android:id="@+id/SingleTitle"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:layout_marginBottom="@dimen/dp_5"
 * android:layout_marginLeft="@dimen/dp_30"
 * android:layout_marginRight="@dimen/dp_30"
 * android:layout_marginTop="@dimen/dp_5"
 * stit:STRondSize="@dimen/dp_2"
 * stit:STStrokeSize="@dimen/dp_1"
 * stit:STTextColor="@color/main_color"
 * stit:STTextSize="@dimen/text_xxxm"
 * stit:STTitle="六西格码|企业认证" />
 */

public class SingleTitle extends LinearLayout implements OnClickListener {
    //view 容器
    private ArrayList<TextView> textViews = new ArrayList<>();
    private Context context;
    private OnClickCallback onClickCallback = null;
    int textColor = 0xffff6700;
    float textSize = 12;
    int backgroundColor = 0xffffffff;
    //圆角半径
    float roundSize = 3;
    //边框线大小
    float strokeSize = 1;

    public SingleTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SingleTitle);
        textSize = typedArray.getDimension(R.styleable.SingleTitle_STTextSize, textSize);
        textColor = typedArray.getColor(R.styleable.SingleTitle_STTextColor, textColor);
        roundSize = typedArray.getDimension(R.styleable.SingleTitle_STRondSize, roundSize);
        strokeSize = typedArray.getDimension(R.styleable.SingleTitle_STStrokeSize, strokeSize);
        //设置背景
        this.setBackgroundDrawable(getGradientDrawable(context, backgroundColor, textColor, roundSize, strokeSize));
        String str = typedArray.getString(R.styleable.SingleTitle_STTitle);
        //根据名称添加view
        if (!TextUtils.isEmpty(str) && str.contains("|")) {
            String[] content = str.split("\\|");
            addViews(content);
        }
        typedArray.recycle();
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
        textViews.clear();
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

    /**
     * 添加View
     *
     * @param content
     */
    private void addViews(String[] content) {
        for (int i = 0; i < content.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setGravity(Gravity.CENTER);
            ll.setLayoutParams(params);

            //名称
            LinearLayout.LayoutParams ll_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1);
            final TextView name = new TextView(context);
            name.setText(content[i]);
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            name.setGravity(Gravity.CENTER);
            name.setLayoutParams(ll_1);
            name.setPadding(0, DimensUtils.dip2px(context, 5), 0, DimensUtils.dip2px(context, 5));
            name.setTag(content[i]);
            name.setTextColor(textColor);
            ll.addView(name, ll_1);
            name.setOnClickListener(this);
            textViews.add(name);

            //分界线
            if (content.length - 1 != i) {
                LinearLayout.LayoutParams ll_2 = new LinearLayout.LayoutParams(1, LayoutParams.MATCH_PARENT);
                View boundary = new TextView(context);
                boundary.setLayoutParams(ll_2);
                boundary.setBackgroundColor(textColor);
                ll.addView(boundary, ll_2);
            }
            //默认第一个item为选中
            if (i == 0) {
                name.setTextColor(backgroundColor);
//                name.setBackgroundDrawable(getGradientDrawable(context, textColor, textColor, roundSize, strokeSize));
                name.setBackgroundDrawable(setBackground(roundSize, 0, roundSize, 0));
            }
            addView(ll);
        }
    }

    /**
     * 购物车是否有东西
     */
    boolean f = true;

    public void setClickEnable(boolean enable) {
        f = enable;
    }


    @Override
    public void onClick(View v) {

        if (!f) {

            for (int i = 0; i < textViews.size(); i++) {
                if (textViews.get(i).getTag() == v.getTag()) {
                    if (onClickCallback != null) {
                        onClickCallback.onErrorMsg(i);
                    }
                }
            }

            return;
        }

        for (int i = 0; i < textViews.size(); i++) {
            if (textViews.get(i).getTag() == v.getTag()) {

                if (onClickCallback != null) {
                    onClickCallback.onClick(v.getTag().toString(), i);
                }

                textViews.get(i).setTextColor(backgroundColor);
                //第一个的背景色
                if (i == 0) {
                    textViews.get(i).setBackgroundDrawable(setBackground(roundSize, 0, roundSize, 0));
                    continue;

                    //最后一个的背景色
                } else if (i == textViews.size() - 1) {
                    textViews.get(i).setBackgroundDrawable(setBackground(0, roundSize, 0, roundSize));
                    continue;

                    //其它item的背景色
                } else {
                    textViews.get(i).setBackgroundDrawable(getGradientDrawable(context, textColor, textColor, 0, strokeSize));
                    textViews.get(i).setBackgroundColor(textColor);
                    continue;
                }
            }
            //不是点击的控件颜色全部设置为透明
            textViews.get(i).setBackgroundColor(0x00000000);
            textViews.get(i).setTextColor(textColor);
        }
    }


    /**
     * 返回设置的标题
     */
    public interface OnClickCallback {
        void onClick(String content, int position);

        void onErrorMsg(int position);
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

    public RadiusBackgroundDrawable setBackground(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        RadiusBackgroundDrawable h = new RadiusBackgroundDrawable(
                DimensUtils.dip2px(context, (int) topLeftRadius),
                DimensUtils.dip2px(context, (int) topRightRadius),
                DimensUtils.dip2px(context, (int) bottomLeftRadius),
                DimensUtils.dip2px(context, (int) bottomRightRadius),
                true, textColor);
        return h;
    }
}
