package com.song.songup.shoppingapp.di.component.classes;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.song.songup.shoppingapp.di.module.classes.ClassFirstModule;
import com.song.songup.shoppingapp.mvp.contract.classes.ClassFirstContract;

import com.jess.arms.di.scope.FragmentScope;
import com.song.songup.shoppingapp.mvp.ui.fragment.classes.ClassFirstFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2019 19:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ClassFirstModule.class, dependencies = AppComponent.class)
public interface ClassFirstComponent {
    void inject(ClassFirstFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ClassFirstComponent.Builder view(ClassFirstContract.View view);

        ClassFirstComponent.Builder appComponent(AppComponent appComponent);

        ClassFirstComponent build();
    }
}