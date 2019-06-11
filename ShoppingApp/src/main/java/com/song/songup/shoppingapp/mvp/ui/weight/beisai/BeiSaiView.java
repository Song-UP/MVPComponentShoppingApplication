package com.song.songup.shoppingapp.mvp.ui.weight.beisai;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/8 15:40
 * 修改备注：
 */
public class BeiSaiView extends View {

//    onePaht onePaht;
    CircleDraw circleDraw;
    public BeiSaiView(Context context) {
        this(context,null);
    }

    public BeiSaiView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BeiSaiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        onePaht = new onePaht();

        circleDraw = new CircleDraw();
    }

    public void initPain(){
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        onePaht.onSizechange(w, h, oldw, oldh);

        circleDraw.onSizeChange(w, h, oldw, oldh);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        onePaht.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        onePaht.onDraw(canvas);

        circleDraw.onDraw(canvas);
    }

    /**********一阶贝塞尔曲线***********/
    public class onePaht{
        private Paint mPaint;
        private int centerX, centerY;

        private PointF start, end, control;

        public onePaht() {
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(8);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setTextSize(60);

            start = new PointF(0,0);
            end = new PointF(0,0);
            control = new PointF(0,0);
        }

        public void onSizechange(int w, int h, int oldw, int oldh){
            centerX = w/2;
            centerY = h/2;

            // 初始化数据点和控制点的位置
            start.x = centerX-200;
            start.y = centerY;
            end.x = centerX+200;
            end.y = centerY;
            control.x = centerX;
            control.y = centerY-100;
        }

        public boolean onTouchEvent(MotionEvent event){
            control.x =event.getX();
            control.y = event.getY();
            invalidate();
            return true;
        }

        public void  onDraw(Canvas canvas){
            //绘制数据点，控制点
            mPaint.setColor(Color.GRAY);
            mPaint.setStrokeWidth(20);
            canvas.drawPoint(start.x,start.y,mPaint);
            canvas.drawPoint(end.x,end.y,mPaint);
            canvas.drawPoint(control.x,control.y, mPaint);

            //绘制辅助线
            mPaint.setStrokeWidth(4);
            canvas.drawLine(start.x,start.y,control.x,control.y,mPaint);
            canvas.drawLine(end.x,end.y,control.x,control.y,mPaint);

            //绘制贝塞尔曲线
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(8);

            Path path = new Path();
            path.moveTo(start.x,start.y);
            path.quadTo(control.x, control.y, end.x, end.y);

            canvas.drawPath(path,mPaint);
        }

    }

    public class CircleDraw{

        Paint mPaint;
        private PointF centerPointF;//中心点
        private float r = 200;
//        (4/3)*tan(pi/(2n))。
//        用来控制贝塞尔曲线
        float c = 0.552284749831f;
        float length = c*r;


        List<PointF> sparseArray = new ArrayList<>();

        public CircleDraw() {
            Paint paint = new Paint();

            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(8);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setTextSize(60);

        }

        PointF topP1,topP2,topP3;
        PointF leftP1,leftP2,leftP3;
        PointF rightP1,rightP2,rightP3;
        PointF bottomP1,bottomP2,bottomP3;

        public void onSizeChange(int w, int h, int oldw, int oldh){
            topP1 = new PointF();
            topP2 = new PointF();
            topP3 = new PointF();

            leftP1 = new PointF();
            leftP2 = new PointF();
            leftP3 = new PointF();

            bottomP1 = new PointF();
            bottomP2 = new PointF();
            bottomP3 = new PointF();

            rightP1 = new PointF();
            rightP2 = new PointF();
            rightP3 = new PointF();

            centerPointF = new PointF();
            centerPointF.set(w/2,h/2);
            topP2.set(centerPointF.x,centerPointF.y-r);
            rightP2.set(centerPointF.x+r,centerPointF.y);
            bottomP2.set(centerPointF.x,centerPointF.y+r);
            leftP2.set(centerPointF.x-r,centerPointF.y);

            topP1.set(topP2.x-length,topP2.y);
            topP3.set(topP2.x+length,topP2.y);

            rightP1.set(rightP2.x,rightP2.y-length);
            rightP3.set(rightP2.x,rightP2.y+length);

            bottomP1.set(bottomP2.x+length,bottomP2.y);
            bottomP3.set(bottomP2.x-length,bottomP2.y);

            leftP1.set(leftP2.x,leftP2.y+length);
            leftP3.set(leftP2.x,leftP2.y-length);

            sparseArray.add(topP1);
            sparseArray.add(topP2);
            sparseArray.add(topP3);

            sparseArray.add(rightP1);
            sparseArray.add(rightP2);
            sparseArray.add(rightP3);
//
            sparseArray.add(bottomP1);
            sparseArray.add(bottomP2);
            sparseArray.add(bottomP3);

            sparseArray.add(leftP1);
            sparseArray.add(leftP2);
            sparseArray.add(leftP3);
        }

        Path path = new Path();
        private void onDraw(Canvas canvas){
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(10);

            topP2.y += 100;

            bottomP1.y -=30;
            bottomP3.y -= 30;

            //辅助点
            for (int i = 0;i <sparseArray.size(); i++){
                canvas.drawPoint(sparseArray.get(i).x, sparseArray.get(i).y,mPaint);
            }
            //辅助线
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(5);
            for (int i = 0;i <sparseArray.size();){
                path.moveTo(sparseArray.get(i).x,sparseArray.get(i).y);
                i+=1;
                path.lineTo(sparseArray.get(i).x,sparseArray.get(i).y);
                i+=1;
                path.lineTo(sparseArray.get(i).x, sparseArray.get(i).y);
                i+=1;
                canvas.drawPath(path,mPaint);
            }


//            三阶贝塞尔曲线
            mPaint.setColor(Color.RED);
            for (int i = 0;i <sparseArray.size();){
                path.moveTo(sparseArray.get(getFinal(i+1)).x,sparseArray.get(getFinal(i+1)).y);
                path.cubicTo(sparseArray.get(getFinal(i+2)).x, sparseArray.get(getFinal(i+2)).y
                                ,sparseArray.get(getFinal(i+3)).x,sparseArray.get(getFinal(i+3)).y
                                ,sparseArray.get(getFinal(i+4)).x,sparseArray.get(getFinal(i+4)).y);
                canvas.drawPath(path,mPaint);
                i+=3;
            }









        }
        public int getFinal(int i){
            int finalNum = i;
            i = finalNum<sparseArray.size()?finalNum:(finalNum - sparseArray.size());
            return i;
        }
    }



}

