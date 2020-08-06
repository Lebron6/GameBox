package com.wag.gamebox.tools;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;

public class BitmapHelper {
    public BitmapHelper() {
    }

    public static ImageOptions getBitmapUtils() {
        ImageOptions options = (new ImageOptions.Builder()).setIgnoreGif(false).setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        return options;
    }
}