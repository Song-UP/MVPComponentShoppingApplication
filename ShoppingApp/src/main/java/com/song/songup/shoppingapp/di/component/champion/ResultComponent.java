package com.song.songup.shoppingapp.di.component.champion;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.champion.ResultModule;
import com.song.songup.shoppingapp.mvp.contract.champion.ResultContract;

import com.jess.arms.di.scope.ActivityScope;
import com.song.songup.shoppingapp.mvp.ui.activity.champion.ResultActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/24/2019 14:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResultModule.class, dependencies = AppComponent.class)
public interface ResultComponent {
    void inject(ResultActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResultComponent.Builder view(ResultContract.View view);

        ResultComponent.Builder appComponent(AppComponent appComponent);

        ResultComponent build();
    }
}