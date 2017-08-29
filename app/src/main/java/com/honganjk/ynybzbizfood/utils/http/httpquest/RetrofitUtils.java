package com.honganjk.ynybzbizfood.utils.http.httpquest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/8/9.
 */

public class RetrofitUtils {
    public static Map<String, RequestBody> getRequestBodys(String... paths) {
        List<FileBody> fileBodies = new ArrayList<>();
        for (String p : paths) {
            File file = new File(p);
            if (file.exists()) {
                fileBodies.add(new FileBody(file.getName(), file));
            }

        }
        return getRequestBodys(fileBodies);
    }

    public static Map<String, RequestBody> getRequestBodys(List<FileBody> fileBodys) {
        Map<String, RequestBody> maps = new HashMap<>();
        for (FileBody fileBody : fileBodys) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), fileBody.getFile());
            maps.put("file\"; filename=\"" + fileBody.getFileName(), body);
        }

        return maps;
    }


    public static class FileBody {
        String fileName;
        File file;

        public FileBody(String fileName, File file) {

            try {
                this.fileName = new String(fileName.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            this.file = file;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }
}
