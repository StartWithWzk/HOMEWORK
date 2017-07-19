package com.example.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by 小吉哥哥 on 2017/7/19.
 */

public class RoundBimageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mInput;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();


    public RoundBimageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap input = getBitmap(getDrawable());
        if (input != null) {
            int inputMinSize = Math.min(getWidth(), getHeight());
            float dstWidth = inputMinSize;
            float dstHeight = inputMinSize;
            if (mShader == null || !input.equals(mInput)) {
                mInput = input;
                mShader = new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }
            if (mShader != null) {
                mMatrix.setScale(dstWidth / mInput.getWidth(), dstHeight / mInput.getHeight());
                mShader.setLocalMatrix(mMatrix);
            }
            mPaint.setShader(mShader);
            float radius = inputMinSize / 2.0f;
            canvas.drawCircle(radius, radius, radius, mPaint);
        } else {
            super.onDraw(canvas);

        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }

    }
}
