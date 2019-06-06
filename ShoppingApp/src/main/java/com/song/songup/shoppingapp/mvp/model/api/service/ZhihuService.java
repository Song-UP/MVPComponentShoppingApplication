/*
 * Copyright 2017 JessYan
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
package com.song.songup.shoppingapp.mvp.model.api.service;

import com.song.songup.shoppingapp.mvp.model.been.Root;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.song.songup.shoppingapp.mvp.model.api.Api.BAIDU_DOMAIN_NAME;
import static com.song.songup.shoppingapp.mvp.model.api.Api.GOOGLE_DOMAIN_NAME;
import static com.song.songup.shoppingapp.mvp.model.api.Api.ZHIHU_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 zhihu 的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface ZhihuService {
    /**
     * 最新日报
     */
    @Headers({DOMAIN_NAME_HEADER + ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/latest")
    Observable<Root> getDailyList();

//    @Headers({DOMAIN_NAME_HEADER + ZHIHU_DOMAIN_NAME,"Content-Type: application/json"})
//    @GET("/api/4/news/latest")
//    Observable<String> getDetailTest();

    @Headers({DOMAIN_NAME_HEADER+BAIDU_DOMAIN_NAME})
    @GET("/s?ie=utf-8&f=3&rsv_bp=1&rsv_idx=1&tn=baidu&wd=mysql&rsv_pq=b55cd71b003da0d0&rsv_t=773bO67yKY3aZ1VwSrofHDfwv4B9H6wR2JI4Uih25wQ8GdrkIPaIils8mTA&rqlang=cn&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug7=001&rsv_sug2=1&rsp=0&rsv_sug9=es_1_1&inputT=17909&rsv_sug4=18505&rsv_sug=6")
    Observable<String> getNetConfitBaidu();

    @Headers({DOMAIN_NAME_HEADER +GOOGLE_DOMAIN_NAME})
    @GET("/webhp?hl=zh-CN&sourceid=cnhp&gws_rd=ssl")
    Observable<String> getNetConfitGoogle();

//    @Headers({DOMAIN_NAME_HEADER+GOOGLE_DOMAIN_NAME})
//    @GET("/admin/public/login.html")
//    Observable<String> getNetConfitGoogle();
}
