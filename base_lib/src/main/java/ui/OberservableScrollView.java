package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ASUS on 2017/9/5.
 */

public class OberservableScrollView extends ScrollView {
    private boolean enableScrolling = true;

    public boolean isEnableScrolling() {
        return enableScrolling;
    }

    public void setEnableScrolling(boolean enableScrolling) {
        this.enableScrolling = enableScrolling;
    }

    public interface ScrollViewListener {
        void onScrollChanged(OberservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener scrollViewListener = null;

    public OberservableScrollView(Context context) {
        super(context);
    }

    public OberservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OberservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        if(enableScrolling){
            super.onScrollChanged(x, y, oldx, oldy);
            if (scrollViewListener != null) {
                scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
            }
        }
    }
}
