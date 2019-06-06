package com.song.songup.shoppingapp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.MainAcModule;
import com.song.songup.shoppingapp.mvp.contract.MainAcContract;

import com.jess.arms.di.scope.ActivityScope;
import com.song.songup.shoppingapp.mvp.ui.activity.MainAcActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/01/2019 14:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MainAcModule.class, dependencies = AppComponent.class)
public interface MainAcComponent {
    void inject(MainAcActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainAcComponent.Builder view(MainAcContract.View view);

        MainAcComponent.Builder appComponent(AppComponent appComponent);

        MainAcComponent build();
    }
}