package com.honganjk.ynybzbizfood.mode.javabean.base;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.honganjk.ynybzbizfood.utils.http.download.DownloadManager;
import com.honganjk.ynybzbizfood.utils.http.download.DownloadTask;
import com.honganjk.ynybzbizfood.utils.http.download.DownloadTaskListener;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.widget.dialog.DialogProgress;

import java.io.File;

/**
 * 类说明:下载类
 * Created by 阳  2016/12/9  1:54
 */

public class VersionUpdataB {

    DialogProgress dialogProgress;

    public void installAPK(final Activity activity, String url) {

        try {
            DownloadManager downloadManager = DownloadManager.getInstance();
            downloadManager.addDownloadTask(new DownloadTask.Builder()
                            .setUrl(url)
                            .setListener(new DownloadTaskListener() {
                                @Override
                                public void onDownloading(DownloadTask downloadTask, long completedSize, long totalSize, double percent) {
    //                        LogUtils.e(completedSize + "\t" + totalSize + "\t");

                                    if (dialogProgress == null) {
                                        dialogProgress = new DialogProgress(activity);
                                    }
                                    dialogProgress.setTitleText("下载中...");
                                    dialogProgress.setProgress((int) percent);
                                    LogUtils.e(completedSize + "\t" + totalSize + "\t" + "\t\t" + (int) percent);

                                    dialogProgress.show();
                                }

                                @Override
                                public void onPause(DownloadTask downloadTask, long completedSize, long totalSize, double percent) {

                                }

                                @Override
                                public void onCancel(DownloadTask downloadTask) {

                                }

                                @Override
                                public void onDownloadSuccess(DownloadTask downloadTask, File file) {

                                    if (dialogProgress != null) {
                                        dialogProgress.dismiss();
                                    }
                                    installApk(file, activity);
                                }

                                @Override
                                public void onError(DownloadTask downloadTask, int errorCode) {

                                }
                            }).build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装apk
     *
     * @param file
     * @param activity
     */
    protected void installApk(File file, Activity activity) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivityForResult(intent, 990);
    }

}
