package com.song.songup.shoppingapp.di.component.main;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.main.MainClassModule;
import com.song.songup.shoppingapp.mvp.contract.main.MainClassContract;

import com.jess.arms.di.scope.FragmentScope;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainClassFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/16/2019 19:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MainClassModule.class, dependencies = AppComponent.class)
public interface MainClassComponent {
    void inject(MainClassFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainClassComponent.Builder view(MainClassContract.View view);

        MainClassComponent.Builder appComponent(AppComponent appComponent);

        MainClassComponent build();
    }
}