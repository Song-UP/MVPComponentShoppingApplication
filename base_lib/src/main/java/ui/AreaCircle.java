package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.lib.R;

import utils.Utils;

/**
 * Created by XH on 2016/8/20.
 */
public class AreaCircle extends View {
    private Paint backgroundPaint;
    private int color;
    private String text = "";
    private int textColor=Color.parseColor("#ffffff");
    private int textStrokeColor=Color.parseColor("#000000");
    private boolean isShowStroke=false;
    private Paint textPaint;
    private Paint textStrokePaint;
    private Context context;

    public AreaCircle(Context context) {
        this(context, null);
    }

    public AreaCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AreaCircle(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context =context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AreaCircle, defStyleAttr, 0);
        color = a.getColor(R.styleable.AreaCircle_cicle_color, Color.parseColor("#000000"));
        a.recycle();

        initPaint();
    }

    private void initPaint() {
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(color);
        this.backgroundPaint.setStyle(Paint.Style.FILL); //绘制空心圆
        this.backgroundPaint.setAntiAlias(true); //消除锯齿

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textStrokePaint = new Paint();
        textStrokePaint.setAntiAlias(true);
        textStrokePaint.setColor(textStrokeColor);
        textStrokePaint.setTextAlign(Paint.Align.CENTER);
        textStrokePaint.setStrokeWidth(Utils.dip2px(context,1)); //设置描边宽度
        textStrokePaint.setStyle(Paint.Style.STROKE);
    }

    public void setText(String c){
        text = c;
        invalidate();
    }

    public void setBackground(int color){
        this.color = color;
        backgroundPaint.setColor(color);
        invalidate();
    }

    public void setTextColor(String textColor){
        setTextColor(Color.parseColor(textColor));
    }
    public void setTextColor(int textColor){
        this.textColor=textColor;
        textPaint.setColor(this.textColor);
        invalidate();
    }

    /**
     * 设置描边颜色
     * @param textColor
     */
    public void setTextStrokeColor(String textColor){
        setTextColor(Color.parseColor(textColor));
    }
    /**
     * 设置描边颜色
     * @param textColor
     */
    public void setTextStrokeColor(int textColor){
        this.textStrokeColor=textColor;
        textStrokePaint.setColor(textStrokeColor);
        invalidate();
    }

    /**
     * 是否显示描边
     */
    public void setIsShowStroke(boolean isShowStroke){
        this.isShowStroke =isShowStroke;
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth()/2;
        int radius = getWidth()/2;//圆半径
        textPaint.setTextSize(getWidth()*2/3);
        textStrokePaint.setTextSize((getWidth()*2/3));

        canvas.drawCircle(center, center, radius, this.backgroundPaint);

        int xPos = (getWidth() / 2);
        int yPos = (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        if (isShowStroke) {
            canvas.drawText(text, xPos, yPos, textStrokePaint);
        }
        canvas.drawText(text, xPos, yPos, textPaint);

        super.onDraw(canvas);
    }

    public String getText() {
        return text;
    }
}
