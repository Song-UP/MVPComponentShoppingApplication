package glideHelp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

import ui.GlideCircleTransform;

/**
 * Created by ASUS on 2017/7/31.
 */

public class GlideHelper {
    public static void loadImage(Context context, String url, ImageView image, int defaults){
        Glide.with(context).load(url).placeholder(defaults).dontAnimate().into(image);
    }

    public static void loadImage(Context context, String url, ImageView image){
        Glide.with(context).load(url).into(image);
    }

    public static void loadImage(Fragment fragment, String url, ImageView image){
        Glide.with(fragment).load(url).into(image);
    }

    public static void loadRoundImage(Fragment fragment, String url, ImageView image, int defaults){
        Glide.with(fragment).load(url).transform(new GlideCircleTransform(fragment.getContext())).placeholder(defaults).dontAnimate().into(image);
    }

    public static void loadRoundImage(Context context, String url, ImageView image){
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(image);
    }

    public static void loadImageWithoutCache(Context context, String url, ImageView image){
        Glide.with(context).load(url).signature(new StringSignature(""+System.currentTimeMillis())).skipMemoryCache(true).into(image);
    }
}
