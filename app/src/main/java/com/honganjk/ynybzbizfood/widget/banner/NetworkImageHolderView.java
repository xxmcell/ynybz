package com.honganjk.ynybzbizfood.widget.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.mode.javabean.base.BannerAdData;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideOptions;
import com.honganjk.ynybzbizfood.utils.bitmap.GlideUtils;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpLocalUtils;
import com.honganjk.ynybzbizfood.widget.banner.holder.Holder;


/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public abstract class NetworkImageHolderView<T extends BannerAdData> implements Holder<T> {
    private ImageView imageView;
    int width = 0;
    int height = 0;

    @Override
    public View createView(ConvenientBanner banner, Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width = banner.getWidth(), height = banner.getHeight()));
        imageView.setMinimumWidth(width);
        imageView.setMinimumHeight(height);
        imageView.setId(R.id.switchRoot);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, final T data) {

        GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(data.getImg_url()), new GlideOptions.Builder()
                .setWidth(width).setHeight(height).bulider());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicklistener(imageView, position, data);
            }
        });

    }

    public View getView() {
        return imageView;
    }

    public abstract void onItemClicklistener(View item, int position, T data);

}
