package com.honganjk.ynybzbizfood.utils.http.compress;

import com.honganjk.ynybzbizfood.utils.http.httpquest.FileParam;

import java.util.ArrayList;

public interface OnCompressListener {

    /**
     * Fired when the compression is started, override to handle in your own code
     */
    void onStart();

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    void onSuccess(ArrayList<FileParam> files);

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    void onError(Throwable e);

    /**
     * @param progress 第几张
     * @param total    总张数
     */
    void onLoading(int progress, long total);
}
