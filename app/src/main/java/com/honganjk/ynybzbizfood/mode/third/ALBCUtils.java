package com.honganjk.ynybzbizfood.mode.third;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.media.MediaService;
import com.alibaba.sdk.android.media.upload.UploadListener;
import com.alibaba.sdk.android.media.upload.UploadOptions;
import com.alibaba.sdk.android.media.upload.UploadTask;
import com.alibaba.sdk.android.media.utils.FailReason;
import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.enumeration.ALBCPath;
import com.honganjk.ynybzbizfood.utils.http.compress.Luban;
import com.honganjk.ynybzbizfood.utils.http.compress.OnCompressListener;
import com.honganjk.ynybzbizfood.utils.http.httpquest.FileParam;
import com.honganjk.ynybzbizfood.utils.other.LogUtils;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;
import com.honganjk.ynybzbizfood.widget.dialog.LoadDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * 说明:阿里百川工具类
 * 360621904@qq.com 杨阳 2017/3/7  16:12
 */
public abstract class ALBCUtils {
    private LoadDialog loadDialog;

    /**
     * @param activity
     * @param filePath 网络文件路径
     * @param path     文件路径
     */
    public ALBCUtils(final BaseActivity activity, ALBCPath filePath, String path) {
        uploadImage(activity, filePath, path);

    }

    private void uploadImage(final BaseActivity activity, ALBCPath filePath, String path) {
        final MediaService mediaService = AlibabaSDK.getService(MediaService.class);
        final UploadListener listener = new UploadListener() {

            @Override
            public void onUploading(UploadTask uploadTask) {
                LogUtils.e("---上传中---已上传大小：" + uploadTask.getCurrent() + " 总文件大小：" + uploadTask.getTotal());
            }

            @Override
            public void onUploadFailed(UploadTask uploadTask, FailReason failReason) {
                LogUtils.e("---上传失败---");
                dialogDismiss();
                activity.showErrorMessage("上传失败");
            }

            @Override
            public void onUploadComplete(UploadTask uploadTask) {
                LogUtils.e("---上传成功---URL:" + uploadTask.getResult().url);
                dialogDismiss();
//                activity.showInforSnackbar("上传成功");
                succeed(uploadTask.getResult().url);
            }

            @Override
            public void onUploadCancelled(UploadTask uploadTask) {
                LogUtils.e("---上传取消---");
                dialogDismiss();
                activity.showWarningMessage("上传取消");
            }
        };
        try {
            //带上传选项
            final UploadOptions options = new UploadOptions.Builder().dir(filePath.getValuse()).aliases(new Date().getTime() + ".jpg").build();
            File file = new File(path);
            if (file.exists()) {
                loadDialog = new LoadDialog(activity);
                loadDialog.setContent(StringUtils.dataFilter("上传中...", activity.getResources().getString(R.string.loadding)));
                loadDialog.setCancelable(false);
                loadDialog.show();

                ArrayList<File> files = new ArrayList<>();
                files.add(file);
                //压缩图片
                ArrayList<FileParam> fileParam = new ArrayList<>();
                fileParam.addAll(FileParam.getFileToFileParam("imgs_url", files));
                new Luban().load(fileParam).setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(ArrayList<FileParam> files) {
                        try {
                            if (files.size() > 0) {
                                mediaService.upload(files.get(0).getFile(), "hajk", options, listener);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onLoading(int progress, long total) {


                    }
                }).launch();

//                mediaService.upload(file1, "hajk", options, listener);
            } else {
                activity.showErrorMessage("文件件不存在请重新选择");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("阿里百川报错");
        }
    }

    public abstract void succeed(String url);


    private void dialogDismiss() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
    }


}
