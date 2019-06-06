package library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import utils.Utils;

/**
 * Created by jameson on 8/30/16.
 */
public class CardScaleHelper {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private float mScale = 0.7f; // 两边视图scale
    private int mOnePageWidth; // 滑动一页的距离
    private int mCurrentItemPos;
    private int mCurrentItemOffset;
    private CardLinearSnapHelper mLinearSnapHelper = new CardLinearSnapHelper();
    private ScrollListener scrollListener;
    public interface ScrollListener{
        void onScrollStateChange(int newState);
    }
    public void setScrollListener(ScrollListener scrollListener){
        this.scrollListener = scrollListener;
    }

    public void attachToRecyclerView(final RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    mLinearSnapHelper.mNoNeedToScroll = mCurrentItemOffset == 0 || mCurrentItemOffset == getDestItemOffset(mRecyclerView.getAdapter().getItemCount() - 1);
//                } else {
//                    mLinearSnapHelper.mNoNeedToScroll = false;
//                }

                if(scrollListener != null){
                    scrollListener.onScrollStateChange(newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);// dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑

                if(dx != 0){
                    mCurrentItemOffset += dx;
                    computeCurrentItemPos();
                    onScrolledChangedCallback();
                }
            }
        });

        initWidth();
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mOnePageWidth = Utils.dip2px(mContext, 310);
                onScrolledChangedCallback();
            }
        });
    }

    private int getDestItemOffset(int destPos) {
        return mOnePageWidth * destPos;
    }

    /**
     * 计算mCurrentItemOffset
     */
    private void computeCurrentItemPos() {
        if (mOnePageWidth <= 0) return;
        boolean pageChanged = false;
        // 滑动超过一页说明已翻页
        if (Math.abs(mCurrentItemOffset - mCurrentItemPos * mOnePageWidth) >= mOnePageWidth) {
            pageChanged = true;
        }
        if (pageChanged) {
            mCurrentItemPos = mCurrentItemOffset / mOnePageWidth;
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {
        int offset = mCurrentItemOffset - mCurrentItemPos * mOnePageWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);

        View leftView = null;
        View currentView;
        View rightView = null;
        if (mCurrentItemPos > 0) {
            leftView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
        if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
            rightView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
        }

        if (leftView != null) {
            leftView.setScaleY((1 - mScale) * percent + mScale);
        }
        if (currentView != null) {
            currentView.setScaleY((mScale - 1) * percent + 1);
        }
        if (rightView != null) {
            rightView.setScaleY((1 - mScale) * percent + mScale);
        }
    }
}