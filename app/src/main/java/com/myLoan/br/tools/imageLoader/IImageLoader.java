package com.myLoan.br.tools.imageLoader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public interface IImageLoader {
    /**
     * 实例化 imagelaoder
     *
     * @param context
     */
    void initialize(Context context);

    /**
     * 加载 url image
     *
     * @param view
     * @param url
     */
    void loadUrlImage(ImageView view, String url);

    /**
     * 加载 URL image回调bitmap
     *
     * @param url
     * @param result
     */
    void loadUrlImage(String url, IImageResult result);

    /**
     * 加载 文件路径 image
     *
     * @param view
     * @param path
     */
    void loadFileImage(ImageView view, String path);

    /**
     * 加载drwoable image
     *
     * @param view
     * @param path
     */
    void loadDrawbleImage(ImageView view, int resId);

    /**
     * 下载文件 image ，回调文件路劲或文件
     *
     * @param url
     * @param result
     */
    void loadDownImage(String url, IDownloadResult result);

    /**
     * 加载asset下的资源文件
     *
     * @param view
     * @param fileName
     */
    void loadAssetImage(ImageView view, String fileName);

}
