package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.lib.R;

/**
 * Created by Administrator on 2015/6/16.
 */
public class LineBlockScrollView extends View {
    int mWidth;
    int mHeight;

    int num = 1;
    float percent = 1.0f;

    int position = 0;
    Paint paint;


    public LineBlockScrollView(Context context) {
        super(context);
    }

    public LineBlockScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineBlockScrollView);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(a.getColor(R.styleable.LineBlockScrollView_LineBlockScrollView_line_color, 0xfff2f218));
        paint.setStyle(Paint.Style.FILL);
        this.num = a.getInteger(R.styleable.LineBlockScrollView_LineBlockScrollView_cell_num, 1);
        this.percent = a.getFloat(R.styleable.LineBlockScrollView_LineBlockScrollView_cellwidth_percent, 1.0f);
        this.position = a.getInteger(R.styleable.LineBlockScrollView_LineBlockScrollView_position, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        setSelectedPosition(position);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mCellWidth = getCellWidth();
        int center = mWidth / num / 2 + position * mWidth / num;
        canvas.drawRect(center - mCellWidth / 2, 0, center + mCellWidth / 2, mHeight, paint);
    }

    private int getCellWidth() {
        return (int) (mWidth / num * percent);
    }

    public void setSelectedPosition(int position) {
        this.position = position;
        invalidate();
    }

    public void setLineColor(int resId) {
        paint.setColor(getResources().getColor(resId));
        invalidate();
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    public void setNum(int num) {
        if (num > 0) {
            this.num = num;
            invalidate();
        }
    }
}
