package me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.ThirdViewUtil;

import me.jessyan.armscomponent.commonsdk.imgaEngine.config.CommonImageConfigImpl;

/**
 * Created by wusong on 2018/1/16.
 *
 */

public class BaseRCViewHold extends RecyclerView.ViewHolder {
    SparseArray<View> viewSpare;
    View itemView;
    Context context;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public static BaseRCViewHold get(Context context, View itemView){
        return new BaseRCViewHold(context, itemView);
    }

    public static BaseRCViewHold get(Context context, int itemId, ViewGroup viewGroup){
        View itemView = LayoutInflater.from(context).inflate(itemId, viewGroup,false);
        return new BaseRCViewHold(context, itemView);
    }

    public BaseRCViewHold(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
//        if (ThirdViewUtil.isUseAutolayout()) AutoUtils.autoSize(itemView);//适配
        viewSpare = new SparseArray<>();
    }




    public View getContentView(){
        return this.itemView;
    }

    public <T extends View> T getView(int viewId){
        View view = viewSpare.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            viewSpare.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 点击事件监听
     * @param viewId
     * @param onClickListener
     * @return
     */

    public BaseRCViewHold setOnClickLister(int viewId , View.OnClickListener onClickListener){
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 图片
     */
    public BaseRCViewHold setImageResource(int viewId, int iconId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(iconId);
        return this;
    }
    /**
     * 图片
     */
    public BaseRCViewHold setImageUrl(int viewId, String url){
        ImageView imageView = getView(viewId);
        //itemView 的 Context 就是 Activity, Glide 会自动处理并和该 Activity 的生命周期绑定
        getmImageLoader().loadImage(imageView.getContext(),
                CommonImageConfigImpl
                        .builder()
                        .url(url)
                        .imageView(imageView)
                        .build());
        return this;
    }

    /**
     * 文字
     */

    public BaseRCViewHold setText(int viewId, int strId){
        setText(viewId, context.getString(strId));
        return this;
    }
    public BaseRCViewHold setText(int viewId, String str){
        TextView textView = getView(viewId);
        textView.setText(str);
        return this;
    }

    public AppComponent getmAppComponent() {
        if (mAppComponent == null)
            mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        return mAppComponent;
    }

    public ImageLoader getmImageLoader() {
        if (mImageLoader == null)
            mImageLoader = getmAppComponent().imageLoader();
        return mImageLoader;
    }

    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    protected void onRelease() {

    }



}
