package com.song.songup.shoppingapp.di.component.main;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.main.MainHomeAModule;
import com.song.songup.shoppingapp.mvp.contract.main.MainHomeAContract;

import com.jess.arms.di.scope.FragmentScope;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainHomeAFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MainHomeAModule.class, dependencies = AppComponent.class)
public interface MainHomeAComponent {
    void inject(MainHomeAFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainHomeAComponent.Builder view(MainHomeAContract.View view);

        MainHomeAComponent.Builder appComponent(AppComponent appComponent);

        MainHomeAComponent build();
    }
}