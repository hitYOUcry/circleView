package com.example.nemoqjzhang.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by nemoqjzhang on 2017/11/16 21:24.
 * https://github.com/Ulez/DropIndicator
 */

public class CircleView extends View {
    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path mPath;
    private Paint mPaint;

    private Point mStartPoint;
    private Point mEndPoint;

    private Point mCtrlPoint1;
    private Point mCtrlPoint2;
    private Point mCtrlPoint3;
    private Point mCtrlPoint4;

    private float radius = 60;
    private float startX = 100;
    private float startY = 200;

    private float endX = 500;
    private float endY = 200;

    private static final float STAGE1 = 0.2f;
    private static final float STAGE2 = 0.8f;
    private static final float STAGE3 = 0.9f;
    private static final float STAGE4 = 1.0f;

    private float mScaleLenX = radius * 1;
    private float mScaleLenY = radius * 0.4f;

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mStartPoint = new Point();
        mEndPoint = new Point();
        mCtrlPoint1 = new Point();
        mCtrlPoint2 = new Point();
        mCtrlPoint3 = new Point();
        mCtrlPoint4 = new Point();

        mPath.reset();
        mInterpolatedTime = 0;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mInterpolatedTime <= STAGE3 && mInterpolatedTime >= STAGE1) {
            mStartPoint.x = (mInterpolatedTime - STAGE1) / (STAGE3 - STAGE1) * (endX - startX) + startX;
            mStartPoint.y = startY;
            mPaint.setColor(Color.GRAY);
        } else if(mInterpolatedTime < STAGE1){
            mStartPoint.x = startX;
            mStartPoint.y = startY;
            mPaint.setColor(Color.parseColor("#B04285F4"));
        } else {
            mStartPoint.x = endX;
            mStartPoint.y = endY;
            mPaint.setColor(Color.parseColor("#B0EA4335"));
        }

        mEndPoint.x = mStartPoint.x;
        mEndPoint.y = mStartPoint.y + 2 * radius;

        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);
        if (mInterpolatedTime <= STAGE1) {
            mCtrlPoint1.x = mStartPoint.x + radius * 4 / 3 + mInterpolatedTime / STAGE1 * mScaleLenX;
            mCtrlPoint1.y = mStartPoint.y + mInterpolatedTime / STAGE1 * mScaleLenY ;

            mCtrlPoint2.x = mEndPoint.x + radius * 4 / 3 + mInterpolatedTime / STAGE1 * mScaleLenX;
            mCtrlPoint2.y = mEndPoint.y - mInterpolatedTime / STAGE1 * mScaleLenY;

            mCtrlPoint3.x = mStartPoint.x - radius * 4 / 3;
            mCtrlPoint3.y = mStartPoint.y;

            mCtrlPoint4.x = mEndPoint.x - radius * 4 / 3;
            mCtrlPoint4.y = mEndPoint.y;

        } else if (mInterpolatedTime <= STAGE2) {

            mCtrlPoint1.x = mStartPoint.x + radius * 4 / 3 + (1- (mInterpolatedTime - STAGE1) / (STAGE2 - STAGE1)) * mScaleLenX;
            mCtrlPoint1.y = mStartPoint.y +  (1 - (mInterpolatedTime - STAGE1) / (STAGE2 - STAGE1)) * mScaleLenY;

            mCtrlPoint2.x =mCtrlPoint1.x;
            mCtrlPoint2.y = mEndPoint.y - (1 - (mInterpolatedTime - STAGE1) / (STAGE2 - STAGE1)) * mScaleLenY;

            mCtrlPoint3.x = mStartPoint.x - radius * 4 / 3 - (mInterpolatedTime - STAGE1) / (STAGE2 - STAGE1) * mScaleLenX;
            mCtrlPoint3.y = mStartPoint.y + (mInterpolatedTime-STAGE1) / (STAGE2 - STAGE1) * mScaleLenY ;

            mCtrlPoint4.x = mEndPoint.x - radius * 4 / 3 - (mInterpolatedTime - STAGE1) / (STAGE2 - STAGE1) * mScaleLenX;
            mCtrlPoint4.y = mEndPoint.y - (mInterpolatedTime-STAGE1) / (STAGE2 - STAGE1) * mScaleLenY ;

        } else if (mInterpolatedTime <= STAGE3) {

            mCtrlPoint1.x = mStartPoint.x + radius * 4 / 3;
            mCtrlPoint1.y = mStartPoint.y;

            mCtrlPoint2.x = mCtrlPoint1.x;
            mCtrlPoint2.y = mEndPoint.y;


            mCtrlPoint3.x = mStartPoint.x - radius * 4 / 3 - mScaleLenX * (1 - 2.1f * (mInterpolatedTime - STAGE2) / (STAGE3 - STAGE2));
            mCtrlPoint3.y = mStartPoint.y + mScaleLenY * (1 - (mInterpolatedTime - STAGE2) / (STAGE3 - STAGE2));

            mCtrlPoint4.x = mCtrlPoint3.x;
            mCtrlPoint4.y = mEndPoint.y - mScaleLenY * (1 - (mInterpolatedTime - STAGE2) / (STAGE3 - STAGE2));

        } else if (mInterpolatedTime <= STAGE4) {
            mCtrlPoint1.x = mStartPoint.x + radius * 4 / 3;
            mCtrlPoint1.y = mStartPoint.y;

            mCtrlPoint2.x = mEndPoint.x + radius * 4 / 3;
            mCtrlPoint2.y = mEndPoint.y;

            mCtrlPoint3.x = mStartPoint.x - radius * 4 / 3 - mScaleLenX * (1 - 2.1f) * (1 - (mInterpolatedTime - STAGE3) / (STAGE4 - STAGE3));
            mCtrlPoint3.y = mStartPoint.y;

            mCtrlPoint4.x = mCtrlPoint3.x;
            mCtrlPoint4.y = mEndPoint.y;


        }
        Log.i("nemo1", "ctrl1.x = " + mCtrlPoint1.x + ", time = " + mInterpolatedTime);
        Log.i("nemo2", "ctrl2.x = " + mCtrlPoint2.x + ", time = " + mInterpolatedTime);
        Log.i("nemo3", "ctrl3.x = " + mCtrlPoint3.x + ", time = " + mInterpolatedTime);
        Log.i("nemo4", "ctrl4.x = " + mCtrlPoint4.x + ", time = " + mInterpolatedTime);
        Log.i("nemo", "start.x = " + mStartPoint.x + ", time = " + mInterpolatedTime);

        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.cubicTo(mCtrlPoint1.x, mCtrlPoint1.y, mCtrlPoint2.x, mCtrlPoint2.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.cubicTo(mCtrlPoint3.x, mCtrlPoint3.y, mCtrlPoint4.x, mCtrlPoint4.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawCircle(startX, startY + radius, radius, mPaint);
        canvas.drawCircle(endX, endY + radius, radius, mPaint);
    }
    public void setProgress(float progress){
        mInterpolatedTime = progress;
        invalidate();
    }

    private float mInterpolatedTime;

    private class MoveAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }


    public void startAnimation() {
        mPath.reset();
        mInterpolatedTime = 0;
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        MoveAnimation anim = new MoveAnimation();
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(anim);
    }

    static class Point {
        public float x;
        public float y;

        public Point(float xx, float yy) {
            x = xx;
            y = yy;
        }

        public Point() {

        }
    }
}
