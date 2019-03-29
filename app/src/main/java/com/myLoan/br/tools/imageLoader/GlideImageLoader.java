package com.myLoan.br.tools.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Administrator on 2018/6/21 0021.
 */

public class GlideImageLoader implements IImageLoader {
    private Context context;

    @Override
    public void initialize(Context context) {
        this.context = context;
    }

    @Override
    public void loadUrlImage(ImageView view, String url) {
        Glide.with(context).load(url).into(view);
    }

    @Override
    public void loadUrlImage(String url, final IImageResult result) {
       /* Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                result.onResult(resource);
            }
        });*/
    }

    @Override
    public void loadFileImage(ImageView view, String path) {
        Glide.with(context).load(new File(path)).into(view);
    }

    @Override
    public void loadDrawbleImage(ImageView view, int resId) {
        Glide.with(context).load(resId).into(view);
    }

    @Override
    public void loadDownImage(String url, IDownloadResult result) {

    }

    @Override
    public void loadAssetImage(ImageView view, String fileName) {
        Glide.with(context).load("file:///android_asset/" + fileName);
    }
}
