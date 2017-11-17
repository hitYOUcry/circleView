package com.example.nemoqjzhang.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by nemoqjzhang on 2017/11/16 21:24.
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

    private float radius = 50;
    private float startX = 100;
    private float startY = 600;

    private float endX = 900;
    private float endY = 600;



    private void init(){
        mPath = new Path();
        mPaint = new Paint();
        mStartPoint = new Point();
        mEndPoint = new Point();
        mCtrlPoint1 = new Point();
        mCtrlPoint2 = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mStartPoint.x = mInnterpolatedTime*(endX - startX) + startX;
        mStartPoint.y = mInnterpolatedTime*(endY - startY) + startY;

        mEndPoint.x =  mStartPoint.x;
        mEndPoint.y =  mStartPoint.y + 2 * radius;


        mPath.reset();
        mCtrlPoint1.x = mStartPoint.x + radius * 4/3;
        mCtrlPoint1.y = mStartPoint.y;
        mCtrlPoint2.x = mCtrlPoint1.x;
        mCtrlPoint2.y = mEndPoint.y;

        mPath.moveTo(mStartPoint.x,mStartPoint.y);
        mPath.cubicTo(mCtrlPoint1.x,mCtrlPoint1.y,mCtrlPoint2.x,mCtrlPoint2.y,mEndPoint.x,mEndPoint.y);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(mStartPoint.x,mStartPoint.y);
        mCtrlPoint1.x = mStartPoint.x - radius * 4/3;
        mCtrlPoint2.x = mCtrlPoint1.x;
        mPath.cubicTo(mCtrlPoint1.x,mCtrlPoint1.y,mCtrlPoint2.x,mCtrlPoint2.y,mEndPoint.x,mEndPoint.y);
        canvas.drawPath(mPath,mPaint);


    }


    private float mInnterpolatedTime;
    private class MoveAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInnterpolatedTime = interpolatedTime;
            invalidate();
        }
    }


    public void startAnimation(){
        mPath.reset();
        mInnterpolatedTime = 0;
        MoveAnimation anim = new MoveAnimation();
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(anim);
    }

    static class Point{
        public float x;
        public float y;
        public Point(float xx,float yy){
            x = xx;
            y = yy;
        }

        public Point(){

        }
    }
}
