package com.honganjk.ynybzbizfood.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.honganjk.ynybzbizfood.code.MyApplication;
import com.honganjk.ynybzbizfood.utils.http.httpquest.HttpLocalUtils;
import com.honganjk.ynybzbizfood.utils.other.FileUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;
import static com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig.crossFade;
import static com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig.errorPicture;
import static com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig.placeholderPicture;

/**
 * 说明:Glide 图片加载工具类
 * 作者： 杨阳; 创建于：  2017-07-11  11:03
 */
@SuppressWarnings("ConfusingArgumentToVarargsMethod")
public class GlideUtils2 {

    /**
     * @param url                   可以是网络路径，也可以是图片文件
     * @param view                  imageView
     * @param crossFade             动画的时
     * @param bitmapTransformations 修剪类型
     */
    public static void show(String url, ImageView view, int crossFade, Transformation<Bitmap> bitmapTransformations) {
        try {
            DrawableTypeRequest drawableTypeRequest = Glide.with(view.getContext()).load(url);
            drawableTypeRequest
                    .error(errorPicture)
                    .placeholder(placeholderPicture)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            if (crossFade != -1) {
                drawableTypeRequest.crossFade(crossFade);
            } else {
                drawableTypeRequest.dontAnimate();
            }
            if (bitmapTransformations != null ) {
                drawableTypeRequest.bitmapTransform(bitmapTransformations);
            }
            drawableTypeRequest.into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通显示
     */
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public static void show(String url, ImageView view) {
        show(url, view, crossFade,  null);
    }


    /**
     * 指定动画时间
     */
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public static void show(String url, ImageView view, int crossFade) {
        show(url, view, crossFade,null);
    }


    /**
     * 不带动画
     */
    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public static void showDontAnimate(String url, ImageView view) {
        show(url, view, -1, null);
    }

    /**
     * 圆形
     */
    public static void showCircle(String url, ImageView view) {
        show(url, view, crossFade, new CropCircleTransformation(view.getContext()));
    }

    /**
     * 圆角
     */
    public static void showRound(String url, ImageView view, int round) {
        show(url, view, crossFade, new RoundedCornersTransformation(view.getContext(), round, round));
    }


    public static void showBitmap(ImageView imageView, Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            Glide.with(myApp)
                    .load(bytes)
                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 下载
     *
     * @param url
     * @param downloadCallbacl
     */
    public static void downloadOnly(final String url, final OnDownloadCallbacl downloadCallbacl) {

        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = null;
                try {
                    file = Glide.with(myApp).load(HttpLocalUtils.getHttpFileUrl(url))
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    if (file != null) {
                        String newPath = MyApplication.appSDFilePath;
                        newPath = newPath + File.separator + StringUtils.getUrlToFileName(url);
                        FileUtils.copyFile(file.getPath(), newPath);
                        file = new File(newPath);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(file);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        downloadCallbacl.onCall(file);
                    }
                });
    }


    public static interface OnDownloadCallbacl {
        void onCall(File file);
    }
}
