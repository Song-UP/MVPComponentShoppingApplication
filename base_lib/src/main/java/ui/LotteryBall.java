package ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.lib.R;
import utils.Utils;

public class LotteryBall extends View {
    private Paint paint;
    private String text = "";
    private boolean chosen = false;
    Bitmap bm;
    Bitmap resizedBitmap;
    Bitmap bm1;
    private String odds;

    public LotteryBall(Context context) {
        this(context, null);
    }

    public LotteryBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryBall(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);

        this.paint = new Paint();
        bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.buy_ball);
        bm1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ball_none);
    }

    public void setText(String c){
        text = c;
    }

    public String getText(){
        return text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(chosen){
            resizedBitmap = Bitmap.createScaledBitmap(bm, getWidth(), getWidth(), true);
            this.paint.setAntiAlias(true); //消除锯齿
            canvas.drawBitmap(resizedBitmap, 0, 0, paint);
            resizedBitmap.recycle();
            resizedBitmap = null;
            Paint paint1 = new Paint();
            paint1.setColor(getResources().getColor(R.color.bg_white));
            paint1.setTextSize(Utils.dip2px(getContext().getApplicationContext(), 15));
            paint1.setTextAlign(Paint.Align.CENTER);
            int xPos = (getWidth() / 2);
            int yPos = (int) ((getWidth() / 2) - ((paint1.descent() + paint1.ascent()) / 2)) ;
            canvas.drawText(text, xPos, yPos, paint1);

            if(odds != null){
                Paint paint2 = new Paint();
                paint2.setColor(Color.parseColor("#f45300"));
                paint2.setTextSize(Utils.dip2px(getContext().getApplicationContext(), 12));
                paint2.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(odds, xPos, yPos + Utils.dip2px(getContext().getApplicationContext(), 25), paint2);
            }
        } else {
            resizedBitmap = Bitmap.createScaledBitmap(bm1, getWidth(), getWidth(), true);
            this.paint.setAntiAlias(true); //消除锯齿
            canvas.drawBitmap(resizedBitmap, 0, 0, paint);
            resizedBitmap.recycle();
            resizedBitmap = null;
            Paint paint1 = new Paint();
            paint1.setColor(Color.parseColor("#661dac"));
            paint1.setTextSize(Utils.dip2px(getContext().getApplicationContext(), 15));
            paint1.setTextAlign(Paint.Align.CENTER);
            int xPos = (getWidth() / 2);
            int yPos = (int) ((getWidth() / 2) - ((paint1.descent() + paint1.ascent()) / 2)) ;
            canvas.drawText(text, xPos, yPos, paint1);

            if(odds != null){
                Paint paint2 = new Paint();
                paint2.setColor(Color.parseColor("#666666"));
                paint2.setTextSize(Utils.dip2px(getContext().getApplicationContext(), 12));
                paint2.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(odds, xPos, yPos + Utils.dip2px(getContext().getApplicationContext(), 25), paint2);
            }
        }

        super.onDraw(canvas);
    }

    public void setSelect(){
        chosen = true;

        invalidate();
    }

    public void setUnSelect(){
        chosen = false;

        invalidate();
    }

    public void setOdds(String o){
        odds = o;

        requestLayout();
        invalidate();
    }

    public boolean getSelect(){
        return chosen;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(odds == null){
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec + Utils.dip2px(getContext().getApplicationContext(), 25));
        }
    }
}