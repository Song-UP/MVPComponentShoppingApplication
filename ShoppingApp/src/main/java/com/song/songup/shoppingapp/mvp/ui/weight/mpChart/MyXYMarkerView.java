package com.song.songup.shoppingapp.mvp.ui.weight.mpChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.song.songup.shoppingapp.R;

public class MyXYMarkerView extends MarkerView {
    public static final int ARROW_SIZE = 15; // 箭头的大小
    public static final int HORIENT_SPACE = 0; // 箭头的大小
    private static final float STOKE_WIDTH = 0;//这里对于stroke_width的宽度也要做一定偏移
    private final TextView tvContent;
    private int index; //当前点击的那个
    private int curColor = Color.parseColor("#ffb90b");

    public MyXYMarkerView(Context context) {
        super(context, R.layout.layout_makerview);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    private float highlightX;
    private float highlightY;

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        index = highlight.getDataSetIndex();//这个方法用于获得折线是哪根
        tvContent.setText((int) e.getY() + "");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
//        if (getChartView() == null)
//            return super.getOffsetForDrawingAtPoint(posX,posY);
//
//        MPPointF offset = getOffset();
//        Chart chart = getChartView();
//        float width = getWidth();
//        float height = getHeight();
//
//        highlightX = posX;
//        highlightY = posY;
//
//// posY \posX 指的是markerView左上角点在图表上面的位置
////处理Y方向
//        if (posY <= height + ARROW_SIZE) {// 如果点y坐标小于markerView的高度，如果不处理会超出上边界，处理了之后这时候箭头是向上的，我们需要把图标下移一个箭头的大小
//            offset.y = ARROW_SIZE;
//        } else {//否则属于正常情况，因为我们默认是箭头朝下，然后正常偏移就是，需要向上偏移markerView高度和arrow size，再加一个stroke的宽度，因为你需要看到对话框的上面的边框
//            offset.y = -height - ARROW_SIZE - STOKE_WIDTH; // 40 arrow height   5 stroke width
//        }
////处理X方向，分为3种情况，1、在图表左边 2、在图表中间 3、在图表右边
//        if (posX > chart.getWidth() - width) {//如果超过右边界，则向左偏移markerView的宽度
//            offset.x = -width-HORIENT_SPACE;
//        } else {//默认情况，不偏移（因为是点是在左上角）
//            offset.x = 0+HORIENT_SPACE;
////            if (posX > width / 2) {//如果大于markerView的一半，说明箭头在中间，所以向右偏移一半宽度
////                offset.x = -(width / 2);
////            }
//        }
//        return offset;

        highlightX = posX;
        highlightY = posY;
        return new MPPointF(-(getWidth() / 2), -getHeight()-ARROW_SIZE);
    }


    Paint paint = new Paint();
    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas,posX,posY);

        paint.setColor(curColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        canvas.drawCircle(highlightX,highlightY,12,paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(highlightX,highlightY,4,paint);
    }

    public void setCurColor(int curColor) {
        this.curColor = curColor;
    }
}