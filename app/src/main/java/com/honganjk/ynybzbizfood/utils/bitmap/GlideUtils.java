package com.honganjk.ynybzbizfood.utils.bitmap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
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


/**
 * Created by Administrator on 2016/8/8.
 */

public class GlideUtils {

    public static void init(Application application) {

        //OkHttpClient okHttpClient = (OkHttpClient) HttpManager.getInstance().getRetrofit().callFactory();
        //OkHttpDownLoader okHttpDownloader = new OkHttpDownLoader(okHttpClient);
        Glide.get(application).setMemoryCategory(MemoryCategory.LOW);

    }

    public static void show(ImageView view, String path, GlideOptions picassoOptions) {
        DrawableTypeRequest drawableTypeRequest = getRequestCreator(view.getContext(), path);
        if (picassoOptions.getError() > 0) {
            drawableTypeRequest.error(picassoOptions.getError());
        }
        if (picassoOptions.getPlaceholder() > 0) {
            drawableTypeRequest.placeholder(picassoOptions.getPlaceholder());
        }
        if (picassoOptions.getTransformations() != null && picassoOptions.getTransformations().length > 0) {
            drawableTypeRequest.bitmapTransform(picassoOptions.getTransformations());
        }
        if (picassoOptions.getWidth() > 0 && picassoOptions.getHeight() > 0) {
            drawableTypeRequest.override(picassoOptions.getWidth(), picassoOptions.getHeight());
        }
        drawableTypeRequest.sizeMultiplier(0.8f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
    }

    private static DrawableTypeRequest<String> getRequestCreator(Context context, String path) {

        if (context instanceof Activity) {
            return Glide.with((Activity) context).load(HttpLocalUtils.getHttpFileUrl(path));
        }

        return Glide.with(context).load(HttpLocalUtils.getHttpFileUrl(path));
    }


    /**
     * 给ImageView 设置网络图片（圆形）
     *
     * @param imageView
     * @param path      GlideUtils.show((ImageView) getView(viewId), path, new GlideOptions.Builder().addTransformation(new RoundedCornersTransformation(myApp, round, round)).bulider());
     *                  return this;
     */
    public static void showCircle(View imageView, String path) {
        try {
            GlideUtils.show((ImageView) imageView,
                    path,
                    new GlideOptions.Builder().addTransformation(new CropCircleTransformation(myApp)).bulider());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param imageView
     * @param path
     * @param round     圆角的大小
     */
    public static void setImageBitmapRound(View imageView, String path, int round) {
        try {
            GlideUtils.show((ImageView) imageView, path, new GlideOptions.Builder().addTransformation(new RoundedCornersTransformation(myApp, round, round)).bulider());

        } catch (Exception e) {
            e.printStackTrace();
        }

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
     * 给ImageView 设置网络图片
     *
     * @param imageView
     * @param path
     */
    public static void show(View imageView, String path) {
        try {
            GlideUtils.show((ImageView) imageView,
                    path,
                    new GlideOptions.Builder().bulider());

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
