/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonsdk.core;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import me.jessyan.armscomponent.commonsdk.BuildConfig;
import me.jessyan.armscomponent.commonsdk.core.gsonAdapte.CustomizeGsonConverterFactory;
import me.jessyan.armscomponent.commonsdk.http.Api;
import me.jessyan.armscomponent.commonsdk.http.SSLSocketClient;
import me.jessyan.armscomponent.commonsdk.imgaEngine.Strategy.CommonGlideImageLoaderStrategy;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import timber.log.Timber;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.3">ConfigModule wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalConfiguration implements ConfigModule {
    private final int DEFAULT_TIMEOUT = 30;//超时设置

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
                        builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
                        builder.followRedirects(true); //是否运行客户端重定向，这个后续配置客户端重定向。如果取消，将被重定向。
                        builder.retryOnConnectionFailure(true);//连接失败时，是否以多重方式重连
                        //拦截http请求，添加指定的head
//                        builder.interceptors().add(header);

                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数,甚至您可以替换系统配置好的okhttp对象
                    retrofitBuilder.addConverterFactory(CustomizeGsonConverterFactory.create());
                    //比如使用fastjson替代gson,本例中给，设置了可以转换为取得string字符串

                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    /***********************完整的配置*****************/
//    默认的 GsonConverterFactory 不能解析，就自定义一个 GsonConverterFactory 解析，不会自定义的话就百度一样，不一定只有 FastJson 才能解析某种格式
//    ConverterFactory 不会冲突，他是个集合，Retrofit 会将需要解析的数据的传递给集合里的每个 ConverterFactory，
//    直到某个 ConverterFactory 解析成功为止，如果某个 ConverterFactory 解析不了这个数据，返回 null 即可，
//    传递给下一个 ConverterFactor
    //使用 builder 可以为框架配置一些配置信息
//       builder.baseurl(Api.APP_DOMAIN)
//            .gsonConfiguration((context12, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
//        gsonBuilder
//                .serializeNulls()//支持序列化null的参数
//                .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
//    })
//            .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数,甚至您可以替换系统配置好的okhttp对象
////                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
//    })
//            .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置Okhttp的参数
//        okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
//    }).rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
//        rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
//    }

/********************* 为请求头添加内容  *******************/
//    Interceptor headers = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//                    /*HttpUrl newUrl = original.url().newBuilder().host(BuildConfig.DOMAIN_URL).build();
//                    Request completeRequest = original.newBuilder().url(newUrl).build();*/
//            Request.Builder requestBuilder = original.newBuilder()
//                    /*.header("Connection", "keep-alive")
//                    .header("Connection", "close")
//                    .header("Host", BuildConfig.DOMAIN_URL)*/
//                    .header("auth-token", HeaderVerifyUtils.getVerifyCode2())
//                    .header("User-Agent", Common.UserAgentString == null ? "" : Common.UserAgentString)
//                    .header("COMPANY_CODE", BuildConfig.COMPANY_CODE);
//            Request request = requestBuilder.build();
//            Response response = chain.proceed(request);
////                    if(response.header("BSID") != null || response.header("bsid") != null){
////                        //表示客户端现在访问的地址是正确的
////                    } else {//表示客户端访问的地址不对了，需要切换域名
//////                        String nextUrl = UserInfo.getNextURL();
//////                        if(nextUrl != null && !nextUrl.isEmpty()){
//////                            UserInfo.setLocalUrl(nextUrl);
//////                            RetrofitBase.resetRetrofit();
//////                        }
////
////                        ChangeIP.checkIPs(new ChangeIP.AfterIPAction() {
////                            @Override
////                            public void doAction() {
////                                RetrofitBase.resetRetrofit();
////                            }
////                        });
////                    }
//            return response;
//        }
//    };



    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {

            @Override
            public void attachBaseContext(@NonNull Context base) {
            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }
                ARouter.init(application); // 尽可能早,推荐在Application中初始化
            }

            @Override
            public void onTerminate(@NonNull Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
