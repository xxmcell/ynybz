package com.honganjk.ynybzbizfood.utils.ui.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;


/**
 * 说明：
 * 作者　　: 杨阳
 * 创建时间: 2016/11/5 17:26
 */

public class RadiusBackgroundDrawable extends Drawable {
    //圆角
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;
    //边界
    private int left;
    private int top;
    private int right;
    private int bottom;
    //填充色
    private int color;
    //边框位置（内或外）
    private final boolean isStroke;
    //边框大小
    private int strokeWidth = 0;
    //边框颜色
    private int strokeColor;

    private final Paint paint;
    private Path path;

    /**
     * 分别设置角的大小，与背景色
     *
     * @param topLeftRadius
     * @param topRightRadius
     * @param bottomLeftRadius
     * @param bottomRightRadius
     * @param isStroke
     * @param color
     */
    public RadiusBackgroundDrawable(int topLeftRadius, int topRightRadius, int bottomLeftRadius,
                                    int bottomRightRadius, boolean isStroke, int color) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.isStroke = isStroke;
        this.color = color;
    }

    /**
     * 4个角统一小大小的构造，，与背景色
     *
     * @param radius
     * @param isStroke
     * @param color
     */
    public RadiusBackgroundDrawable(int radius, boolean isStroke, int color) {
        this.topLeftRadius = this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.isStroke = isStroke;
        this.color = color;
    }

    /**
     * 设置边框大小
     *
     * @param width
     */
    public void setStrokeWidth(int width) {
        strokeWidth = width;
        setBounds(left, top, right, bottom);
    }

    /**
     * 设置边框色
     *
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    /**
     * 设置填充色
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 设置设置圆角大小
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.topLeftRadius = this.topRightRadius = this.bottomLeftRadius = this.bottomRightRadius = radius;
    }

    /**
     * 分别设置四个角的大小
     *
     * @param topLeftRadius
     * @param topRightRadius
     * @param bottomLeftRadius
     * @param bottomRightRadius
     */
    public void setRadiuses(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        if (isStroke) {
            int halfStrokeWidth = strokeWidth / 2;
            left += halfStrokeWidth;
            top += halfStrokeWidth;
            right -= halfStrokeWidth;
            bottom -= halfStrokeWidth;
        }

        path = new Path();

        path.moveTo(left + topLeftRadius, top);
        path.lineTo(right - topRightRadius, top);
        path.arcTo(new RectF(right - topRightRadius * 2, top, right, top + topRightRadius * 2), -90, 90);
        path.lineTo(right, bottom - bottomRightRadius);
        path.arcTo(new RectF(right - bottomRightRadius * 2, bottom - bottomRightRadius * 2, right, bottom), 0, 90);
        path.lineTo(left + bottomLeftRadius, bottom);
        path.arcTo(new RectF(left, bottom - bottomLeftRadius * 2, left + bottomLeftRadius * 2, bottom), 90, 90);
        path.lineTo(left, top + topLeftRadius);
        path.arcTo(new RectF(left, top, left + topLeftRadius * 2, top + topLeftRadius * 2), 180, 90);
        path.close();
    }


    @Override
    public void draw(Canvas canvas) {
        if (color != 0) {
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }

        if (strokeWidth > 0) {
            paint.setColor(strokeColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.MITER);
            paint.setStrokeWidth(strokeWidth);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


}
