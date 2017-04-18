package wave.maktub.maktubwave;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dinhdv on 3/3/2017.
 */

public class MaktubView extends View {
    private Paint mPaint;
    float mRadius = 6, mRadiusOne = 8, mRadiusTwo = 8, mRadiusThree = 10;
    float mCorner = 0, mCornerOne = 90, mCornerTwo = 180, mCornerThree = 270;
    float mDirection = (float) 0.7, mDirectionOne = (float) 0.8, mDirectionTwo = (float) 0.6, mDirectionThree = (float) 0.5;
    float mUp = 5, mUpOne = 6, mUpTwo = 2, mUpThree = 7;
    float mDown = 12, mDownOne = 3, mDownTwo = 7, mDownThree = 2;
    //Do cao cua song con
    // Dao dong tu   0 ~ 5
    // Tay doi phu thuoc vao am luong cua bai hat
    float mMetter = 1, mMetterThree = 1, mMetterTwo = 1, mMetterOne = 1;
    float dMetter = 1;
    private float currentLinePointY = 0;
    private boolean mDrawPaint1 = true;

    public MaktubView(Context context) {
        super(context);
        initView(context);
    }

    public MaktubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MaktubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if (mPaint == null) {
            mPaint = new Paint(Paint.DITHER_FLAG);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(4f);
        mPaint.setAntiAlias(true);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (width > 1000) {
            mUp = 15;
            mDown = 8;
            mUpThree = 12;
            mDownThree = 7;
            mUpTwo = 8;
            mDownTwo = 5;
            mUpOne = 15;
            mDownOne = 5;
        }
    }

    public void setMetter(float metter) {
        mMetter = mMetterOne = mMetterThree = mMetterTwo = metter;
    }

    public float getMetter() {
        return mMetter;
    }

    public void setUpWave(float values) {
        mUp = values;
    }

    public void setDownWave(float values) {
        mDown = values;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentLinePointY = (float) (h / 3.8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWaveView(canvas);
        mCorner += 0.03;
        mCornerOne += 0.04;
        mCornerTwo += 0.05;
        mCornerThree += 0.06;
        mDrawPaint1 = !mDrawPaint1;
        invalidate();
        dMetter--;
        if (dMetter == 0)
            dMetter = 5;
    }

    private void drawWaveView(Canvas canvas) {
        drawWaveView(canvas, mPaint, Color.WHITE, 60, mRadius, mCorner, mDirection, mUp, mDown, 15);
        drawWaveView(canvas, mPaint, Color.WHITE, 40, mRadiusOne, mCornerOne, mDirectionOne, mUpOne, mDownOne, 9);
        drawWaveView(canvas, mPaint, Color.WHITE, 15, mRadiusTwo, mCornerTwo, mDirectionTwo, mUpTwo, mDownTwo, 11);
        drawWaveView(canvas, mPaint, Color.WHITE, 5, mRadiusThree, mCornerThree, mDirectionThree, mUpThree, mDownThree, 12);
    }

    private void drawWaveView(Canvas canvas, Paint paint, int color, int alpha, float radius, float corner, float direction, float up, float down, int valuesSmall) {
        // View One.
        paint.setColor(color);
        paint.setAlpha(alpha);

        Path path = new Path();
        float y = currentLinePointY;
        float mWidth = canvas.getWidth();
        boolean first = true;
        // Small wave
        for (float x = 0; x <= mWidth; x++) {
            float dy = mMetter * (float) Math.sin(valuesSmall * x / mWidth * Math.PI * direction - 4 * corner / Math.PI);
            float dx = 0;
            if (x > mWidth * 0.1 && x < mWidth * 0.9) {
                dx = x - mWidth * (float) 0.1;
                dx /= mWidth * 0.8;
                if (dx > 0.5) {
                    dx = 1 - dx;
                }
                dx *= 2;
            }
            y = radius * (float) Math.sin(3 * x / mWidth * Math.PI * direction - 4 * corner / Math.PI) * up + currentLinePointY + dy * dx * dMetter;
            if (first) {
                first = false;
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        y += currentLinePointY;
        // main wave.
        for (float x = mWidth; x >= 0; x--) {
            y = radius * (float) Math.sin(3 * x / mWidth * Math.PI * direction - 4 * corner / Math.PI) * down + currentLinePointY * 2;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, paint);
    }
}