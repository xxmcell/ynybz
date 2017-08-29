package com.honganjk.ynybzbizfood.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;



/**
 * @author Phil
 */
public class CircleImageView extends ImageView {
    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private final RectF mDrawableRect = new RectF();
    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float mDrawableRadius;
    private boolean mReady;
    private boolean mSetupPending;
    private final Paint mFlagBackgroundPaint = new Paint();
    private final TextPaint mFlagTextPaint = new TextPaint();
    private String mFlagText;
    private boolean mShowFlag = false;
    private Rect mFlagTextBounds = new Rect();

    Shader mSweepGradient = null;

    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // TypedArray a = context.obtainStyledAttributes(attrs,
        // R.styleable.CircleImageView, defStyle, 0);
        //
        // mBorderWidth =
        // a.getDimensionPixelSize(R.styleable.CircleImageView_border_width,
        // DEFAULT_BORDER_WIDTH);
        // mBorderColor = a.getColor(R.styleable.CircleImageView_border_color,
        // DEFAULT_BORDER_COLOR);
        //
        // a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format(
                    "ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException(
                    "adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,
                mBitmapPaint);
        float a = (float)(3 + Math.cos((float) (Math.PI * 5 / 18)))
                * getHeight() / 4 + mFlagTextBounds.height() / 3;
        if (mShowFlag && mFlagText != null) {
            mFlagTextPaint.getTextBounds(mFlagText, 0, mFlagText.length(),
                    mFlagTextBounds);
            canvas.drawText(mFlagText, getWidth() / 2, a,
                    mFlagTextPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
                        COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mDrawableRect.set(0, 0, getWidth(), getWidth());
        mDrawableRadius = Math.min(mDrawableRect.height() / 2,
                mDrawableRect.width() / 2);

        mFlagBackgroundPaint.setColor(Color.BLACK & 0x66FFFFFF);
        mFlagBackgroundPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        mFlagTextPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);
        mFlagTextPaint.setTextAlign(Align.CENTER);
        mFlagTextPaint.setColor(Color.WHITE);
        mFlagTextPaint
                .setTextSize(getResources().getDisplayMetrics().density * 18);

        mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2,
                new int[]{Color.rgb(255, 255, 255), Color.rgb(1, 209, 255)},
                null);

        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()
                * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
        }

        mShaderMatrix.setScale(scale, scale);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    public void setShowFlag(boolean show) {
        mShowFlag = show;
        invalidate();
    }

    public void setFlagText(String text) {
        mFlagText = text;
        invalidate();
    }
}