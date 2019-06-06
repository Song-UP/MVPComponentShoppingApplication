package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by ASUS on 2017/9/2.
 */

public class NoTouchListView extends ListView{
    public NoTouchListView(Context context) {
        super(context);
    }

    public NoTouchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
