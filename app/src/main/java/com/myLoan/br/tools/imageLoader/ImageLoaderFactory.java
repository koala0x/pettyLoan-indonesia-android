package com.myLoan.br.tools.imageLoader;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class ImageLoaderFactory {
    static GlideImageLoader glideImageLoader = new GlideImageLoader();

    /**
     * 创建 frcso Imageloader
     *
     * @return
     */
    public static IImageLoader createImageLoader() {
        return glideImageLoader;
    }

}
