package com.song.songup.shoppingapp.di.component.main;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.main.MainMeActivityModule;
import com.song.songup.shoppingapp.mvp.contract.main.MainMeActivityContract;

import com.jess.arms.di.scope.FragmentScope;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainMeActivityFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2019 20:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MainMeActivityModule.class, dependencies = AppComponent.class)
public interface MainMeActivityComponent {
    void inject(MainMeActivityFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainMeActivityComponent.Builder view(MainMeActivityContract.View view);

        MainMeActivityComponent.Builder appComponent(AppComponent appComponent);

        MainMeActivityComponent build();
    }
}