package com.wag.gamebox.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角ImageVIew
 */

public class FilletImageView extends ImageView {
    public FilletImageView(Context context) {
        super(context);
    }
    public FilletImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FilletImageView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}