package com.honganjk.ynybzbizfood.utils.other;

import android.content.Intent;
import android.net.Uri;

import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.widget.dialog.DialogProgress;


/**
 * Created by Administrator on 2016/6/20.
 */
public class VersionUpdata {

    public static boolean isStart = false;

    private DialogProgress dialogProgress = null;
    private BaseActivity application;

//    public void stop() {
//        if (cancelable != null && !cancelable.isCancelled()) {
//            cancelable.cancel();
//        }
//        isStart = false;
//    }

    public VersionUpdata(BaseActivity application) {
        this.application = application;
        if (!isStart) {
            start();
        }
    }



    public void start() {
        isStart = true;
//        HttpRequestParams params = new HttpRequestParams("GetNewAppVersion");
//        params.addBodyParameter("Num", BuildConfig.VERSION_CODE);
//        HttpCallBack<SimpleJsonData> callBack = new HttpCallBack<SimpleJsonData>(application) {
//
//            @Override
//            public void onSuccess(SimpleJsonData result) {
//                super.onSuccess(result);
//                if (result.getCode() == 1) {
//                    AppUpdata updata = result.getDataOne(AppUpdata.class);
//                    if (updata.isUPdate()) {
//                        download(updata.getAppPath());
//                    } else {
//                        //不重新升级
//                        isStart = false;
//                    }
//                } else {
//                    isStart = false;
//                }
//            }
//
//            @Override
//            public void onError(Throwable error, boolean isOnCallback) {
//                super.onError(error, isOnCallback);
//                isStart = false;
//            }
//        };
//        x.http().post(params, callBack);
    }

//    public void download(String path) {
//        /**
//         * /Apk/Version/App-release53.apk
//         */
//        RequestParams params = new RequestParams(HttpManager.BASE_URL + path);
//        params.setAutoResume(true);
//        params.setAutoRename(true);
//        params.setSaveFilePath(MyApplication.appSDCachePath + path);
//        params.setCancelFast(true);
////        HttpCallBack<File> callBack = new HttpCallBack<File>(application) {
////            @Override
////            public void onStarted() {
////                super.onStarted();
////                showDialog();
////            }
////
////            @Override
////            public void onError(Throwable error, boolean isOnCallback) {
////                dismiss();
////            }
////
////            @Override
////            public void onFinished() {
////                dismiss();
////            }
////
////            @Override
////            public void onSuccess(File result) {
////                super.onSuccess(result);
////                dismiss();
////                install(result.getPath());
////            }
////
////            @Override
////            public void onLoading(long total, long current, boolean isDownloading) {
////                super.onLoading(total, current, isDownloading);
////                int bai = (int) (current * 1.0 / total * 100.0);
////                if (bai >= 100) {
////                    bai = 100;
////                }
////                dialogProgress.setProgress(bai);
////
////            }
////        };
////        cancelable = x.http().get(params, callBack);
//    }

    private void install(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        application.startActivity(intent);
    }

    private void showDialog() {

        if (dialogProgress == null) {
            dialogProgress = new DialogProgress(application);
        }
        dialogProgress.setTitleText("升级中\n(与其他伙伴保持相同的版本)");
        dialogProgress.show();
    }

    public void dismiss() {
        if (dialogProgress.isShowing())
            dialogProgress.dismiss();
        isStart = false;
    }
}
