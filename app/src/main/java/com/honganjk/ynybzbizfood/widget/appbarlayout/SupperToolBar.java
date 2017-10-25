package com.honganjk.ynybzbizfood.widget.appbarlayout;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;

/**
 * Created by Administrator on 2016/8/26.
 */

public class SupperToolBar extends Toolbar {
    private TextView titleView;

    public SupperToolBar(Context context) {
        this(context, null);
    }

    public SupperToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SupperToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(null);
        setSubtitle(null);
        addTitleView();
        titleView.setText(title);
    }

    /**
     * 获取titleView
     *
     * @return
     */
    public TextView getTitleView() {
        return titleView;
    }


    public void setTitleAlpha(@FloatRange(from = 0.0, to = 1.0) float res) {
        titleView.setAlpha(res);
    }

    public void setTitleColor(@ColorRes int res) {
        addTitleView();
        titleView.setTextColor(getResources().getColor(res));
    }

    public void setTitleSize(@DimenRes int res) {
        addTitleView();
        titleView.setTextColor(DimensUtils.px2sp(getContext(), getResources().getDimension(res)));
    }


    private void addTitleView() {
        if (titleView == null) {
            titleView = new TextView(getContext());
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(getResources().getColor(R.color.white));
            titleView.setTextSize(DimensUtils.px2sp(getContext(), getResources().getDimension(R.dimen.text_h)));
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            //lp.setMargins(0, 0, (int) (getResources().getDimension(R.dimen.dp_6)), 0);
            titleView.setLayoutParams(lp);

            addView(titleView);
        }
    }

    public MenuItem addAction(int postion, String title) {
        MenuItem menuItem = getMenu().add(0, postion, 0, title);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titleView.getLayoutParams();
//        layoutParams.setMargins(0, 0, (int) (layoutParams.rightMargin + getResources().getDimension(R.dimen.dp_24)), 0);
        return menuItem;
    }

    /**
     * SHOW_AS_ACTION_ALWAYS //总是作为Action项显示
     * SHOW_AS_ACTION_IF_ROOM //空间足够时显示
     * SHOW_AS_ACTION_NEVER //永远不作为Action项显示
     * SHOW_AS_ACTION_WITH_TEXT //显示Action项的文字部分
     *
     * @param postion
     * @param title
     * @param icon
     * @return
     */
    public MenuItem addActions(int postion, String title, int icon) {

        MenuItem menuItem = getMenu().add(0, postion, 0, title);
        menuItem.setIcon(icon);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titleView.getLayoutParams();
//        layoutParams.setMargins(0, 0, (int) (layoutParams.rightMargin + getResources().getDimension(R.dimen.dp_24)), 0);
        return menuItem;
    }


    public void setBack(final Activity context) {
        setNavigationIcon(R.drawable.material_arrwos_white_left);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    public void setBack(final Activity context, int drawableId) {
        setNavigationIcon(drawableId);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    public void setBack(final OnClickListener listener) {
        setNavigationIcon(R.drawable.material_arrwos_white_left);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
            }
        });
    }


    public static class LayoutParams extends Toolbar.LayoutParams {

        private int mViewType;

        public LayoutParams(@NonNull Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            mViewType = 2;
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
        }

        public LayoutParams(int gravity) {
            this(WRAP_CONTENT, MATCH_PARENT, gravity);
        }

        public LayoutParams(Toolbar.LayoutParams source) {
            super(source);

        }

        public LayoutParams(ActionBar.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            // ActionBar.LayoutParams doesn't have a MarginLayoutParams constructor.
            // Fake it here and copy over the relevant data.
            copyMarginsFromCompat(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        void copyMarginsFromCompat(MarginLayoutParams source) {
            this.leftMargin = source.leftMargin;
            this.topMargin = source.topMargin;
            this.rightMargin = source.rightMargin;
            this.bottomMargin = source.bottomMargin;
        }
    }
}
