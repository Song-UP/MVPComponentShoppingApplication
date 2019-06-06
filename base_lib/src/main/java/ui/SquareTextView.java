package ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ASUS on 2017/8/11.
 */

public class SquareTextView extends AutoResizeTextView {

    public SquareTextView(Context context) {
        super(context);
    }

    public SquareTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
