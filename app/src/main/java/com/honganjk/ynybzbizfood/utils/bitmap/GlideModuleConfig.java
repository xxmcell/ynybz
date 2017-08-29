package com.honganjk.ynybzbizfood.utils.bitmap;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.honganjk.ynybzbizfood.R;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-07-11  10:36
 * <p>
 * 配置清单中添加
 * <!--**********************************Glide******************************************* -->
 * <meta-data
 * android:name="com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig"
 * android:value="GlideModule" />
 */
public class GlideModuleConfig implements GlideModule {
    //加载失败的图片
    public static int errorPicture = R.mipmap.fail_picture;
    //加载中的图片
    public static int placeholderPicture = R.mipmap.fail_picture;
    //动画的时间
    public static int crossFade = 100;


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //获取内存计算器
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        //获取Glide默认内存缓存大小
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //获取Glide默认图片池大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //将数值修改为之前的1.1倍
        int myMemoryCacheSize = (int) (1.1 * defaultMemoryCacheSize);
        int myBitmapPoolSize = (int) (1.1 * defaultBitmapPoolSize);

        //修改默认值 配置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(myMemoryCacheSize));
        //配置图片池大小
        builder.setBitmapPool(new LruBitmapPool(myBitmapPoolSize));
        //自定义图片质量
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);


//            builder.setDiskCache();//自定义磁盘缓存
//            builder.setDiskCacheService();//自定义本地缓存的线程池
//            builder.setResizeService();//自定义核心处理的线程池


    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //TO
    }
}
