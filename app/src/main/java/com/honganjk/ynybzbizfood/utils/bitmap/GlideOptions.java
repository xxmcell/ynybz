package com.honganjk.ynybzbizfood.utils.bitmap;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import static com.honganjk.ynybzbizfood.code.MyApplication.myApp;
import static com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig.errorPicture;
import static com.honganjk.ynybzbizfood.utils.bitmap.GlideModuleConfig.placeholderPicture;

/**
 * Created by Administrator on 2016/8/4.
 */

public class GlideOptions {


    //加载失败图片
    private int error;
    //加载中的图片
    private int placeholder;
    //图片转换
    private ArrayList<Transformation> transformations;
    int width = 0;
    int height = 0;

    private GlideOptions() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getError() {
        return error;
    }

    public int getPlaceholder() {
        return placeholder;
    }


    public Transformation[] getTransformations() {

        Transformation[] aaa = new Transformation[transformations == null ? 1 : transformations.size() + 1];
        aaa[0] = new CenterCrop(myApp);
        if (transformations != null) {
            for (int i = 0; i < transformations.size(); i++) {
                aaa[i + 1] = transformations.get(i);
            }
        }
        return aaa;
    }

    public static class Builder {
        //加载失败图片
        private int error = errorPicture;
        //加载中的图片
        private int placeholder = placeholderPicture;
        //图片转换
        private ArrayList<Transformation> transformations;
        int width = 0;
        int height = 0;

        public Builder setError(int error) {
            this.error = error;
            return this;
        }

        public Builder setPlaceholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder addTransformation(Transformation transformation) {
            if (transformations == null) {
                transformations = new ArrayList<Transformation>();
            }
            transformations.add(transformation);
            return this;
        }


        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }


        public GlideOptions bulider() {
            GlideOptions options = new GlideOptions();
            options.error = error;
            options.placeholder = placeholder;
            options.transformations = transformations;
            options.width = width;
            options.height = height;
            return options;
        }
    }
}
