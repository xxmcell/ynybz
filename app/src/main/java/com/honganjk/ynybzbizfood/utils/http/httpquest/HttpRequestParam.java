package com.honganjk.ynybzbizfood.utils.http.httpquest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * **
 * 创建时间:2016/10/14　15:03
 *
 * <p>
 * 功能介绍： 请求参数
 */

public class HttpRequestParam {
    Map<String, RequestBody> partMap = new HashMap<>();
    Map<String, String> mapString = new HashMap<>();


    public void addAction(String valuse) {
        addParam("action", valuse);
    }


    public void addParam(String key, String valuse) {
        if (!isEmpty(key) && !isEmpty(valuse)) {
            mapString.put(key, valuse);
        }
    }

    public void addParam(String key, int valuse) {
        addParam(key, String.valueOf(valuse));
    }

    public void addParam(String key, double valuse) {
        addParam(key, String.valueOf(valuse));
    }

    public void addParam(String key, long valuse) {
        addParam(key, String.valueOf(valuse));
    }

    public void addParam(String key, File file) {
        FileParam param = new FileParam(key, file);
        if (param.exists())
            partMap.put(param.getMapKey(), param.getFileRequestBody());
    }

    public void addParamFile(String key, String filePath) {
        addParam(key, new File(filePath));
    }

    public void addParam(String key, List<File> files) {

        List<FileParam> params = FileParam.getFileToFileParam(key, files);
        for (FileParam param : params) {
            if (param.exists())
                partMap.put(param.getMapKey(), param.getFileRequestBody());
        }
    }

    /**
     * 压缩文件路径参数
     *
     * @param key
     * @param files
     */
    public void addParamFileParam(String key, List<FileParam> files) {

        for (FileParam param : files) {
            if (param.exists())
                partMap.put(param.getMapKey(), param.getFileRequestBody());
        }
    }


    public void addParamFile(String key, List<String> filePaths) {
        List<FileParam> params = FileParam.getStringToFileParam(key, filePaths);
        for (FileParam param : params) {
            if (param.exists())
                partMap.put(param.getMapKey(), param.getFileRequestBody());
        }
    }

    public Map<String, RequestBody> getPartMap() {
        for (Map.Entry<String, String> entry : mapString.entrySet()) {
            partMap.put(entry.getKey(), RequestBodyUtils.getStringRequestBody(entry.getValue()));
        }
        return partMap;
    }

    public Map<String, String> getStringMap() {
        return mapString;
    }


    /**
     * 是否正常的字符串
     *
     * @return
     */
    public boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

}
