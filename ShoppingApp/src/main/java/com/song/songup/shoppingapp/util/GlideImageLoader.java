package com.song.songup.shoppingapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.song.songup.shoppingapp.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by ASUS on 2017/9/17.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        //Glide 加载图片简单用法
//            GlideHelper.loadImage(context, path.toString(), imageView);

        Glide.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(R.drawable.banner1).error(R.drawable.banner1))
                .into(imageView);

                //placeholder(R.mipmap.banner).into(imageView);
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//    @Override
//    public ImageView createImageView(Context context) {
//        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//        return simpleDraweeView;
//    }
}
