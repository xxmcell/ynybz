package com.honganjk.ynybzbizfood.widget.photoBrowse;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.animator.DepthPageTransformer;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;

import java.util.List;


/**
 * @author LinJ
 * @ClassName: PhotoViewPager
 * @Description: 自定义viewpager  优化了事件拦截
 * @date 2015-1-9 下午5:33:33
 */
public class PhotoViewPager extends ViewPager {
    public final static String TAG = "AlbumViewPager";
    private Context context;
    TextView numberTv;

    /**
     * 当前子控件是否处理拖动状态
     */
    private boolean mChildIsBeingDragged = false;

    /**
     * 界面单击事件 用以显示和隐藏菜单栏
     */
    private View.OnClickListener onClickListener;
    private AdapterView.OnItemLongClickListener onLongClickListener;


    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setPageTransformer(true, new DepthPageTransformer());
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (numberTv != null && getAdapter() != null) {

                    if (getAdapter().getCount() > 1) {

                        numberTv.setText(position + 1 + " / " + getAdapter().getCount());
                        numberTv.setVisibility(VISIBLE);

                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (numberTv != null) {
                                    numberTv.setVisibility(GONE);
                                }
                            }
                        }, 3000);
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setNumberTv(TextView numberTv) {
        this.numberTv = numberTv;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }


    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public interface OnPlayVideoListener {
        void onPlay(String path);
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<String> paths;//大图地址 如果为网络图片 则为大图url

        public ViewPagerAdapter(List<String> paths) {
            this.paths = paths;
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, final int position) {
            //注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
            //因为直接加到viewGroup下会导致返回的view为viewGroup
            final PhotoView imageView = new PhotoView(context);
            imageView.setBackgroundColor(getResources().getColor(R.color.translucent));
            imageView.enable();
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(view);
                    }
                }
            });
            String path = paths.get(position);
            GlideUtils.show(imageView, path, new GlideOptions.Builder().bulider());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewGroup.addView(imageView);
            imageView.setTag(position);

            //实现长按事件
            imageView.setLongClickable(true);
            imageView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onLongClickListener != null) {
                        onLongClickListener.onItemLongClick(null, imageView, position, -1);
                    }
                    return false;
                }


            });
            return imageView;
        }


        @Override
        public int getItemPosition(Object object) {
            //在notifyDataSetChanged时返回None，重新绘制
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int arg1, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }


}