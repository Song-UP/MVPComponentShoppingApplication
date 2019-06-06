package com.example.sonic.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MatchSeekBar extends ProgressBar{
    protected Paint mPaint;
    protected int mRealWidth;
    private int w;
    public MatchSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);                       //设置画笔为无锯齿


        int oldRadius=getHeight()/2;
        int radius=(int)(oldRadius-1.5);
        w=mRealWidth-oldRadius*2;


        float radio = getProgress() * 1.0f / getMax();
        float progressPosX = (int) (w * radio);


        canvas.translate(getPaddingLeft()+oldRadius, getHeight() / 2);
        /**
                  * 先画一个白色的圆
                  */
        Paint mPaint2=new Paint();
        mPaint2.setColor(Color.WHITE);
        canvas.drawCircle(progressPosX,0,radius,mPaint2);
        /**
                  * 再画一个圆环
                  * 这里要注意的是圆与圆环的半径都被减去1.5，这是为啥呢，
                  * 因为这里半径用到控件高度的一半，而圆环的的宽度是3，
                  * 那么圆环就会有1.5被画到控件外面去
                  *
                  */
        mPaint.setColor(Color.parseColor("#18BBB6"));                    //设置画笔颜色
        mPaint.setStrokeWidth((float) 3.0);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(progressPosX,0,(int)radius,mPaint);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
//        Log.d("dake.xuan", "onSizeChanged: "+mRealWidth);
    }
}