package com.song.songup.shoppingapp.di.module.main;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCAdapter;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCViewHold;
import me.jessyan.armscomponent.commonsdk.utils.count_down.CountDownManager;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.contract.main.MainHomeAContract;
import com.song.songup.shoppingapp.mvp.model.been.main.Winger;
import com.song.songup.shoppingapp.mvp.model.main.MainHomeAModel;


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
@Module
public abstract class MainHomeAModule {

    @Binds
    abstract MainHomeAContract.Model bindMainHomeModel(MainHomeAModel model);

//    @ActivityScope
//    @Provides
//    static List<DailyListBean.StoriesBean> provideList(){
//        return new ArrayList<>();
//    }

    @FragmentScope
    @Provides
    static BaseRCAdapter<Winger> provideZhihuHomeAdapter(MainHomeAContract.View mainHomeView){
        BaseRCAdapter beanBaseAdapter = new BaseRCAdapter<Winger>(mainHomeView.getActivity(), R.layout.listview_activity, null) {
            @Override
            public void onBindView(BaseRCViewHold holder, Winger data) {
//                Observable.just(data.getTitle())
//                        .subscribe(s -> mName.setText(s));
            }
        };
        return beanBaseAdapter;
    }


    @FragmentScope
    @Provides
    static  CountDownManager.CountDownModel provideCountDownModel(MainHomeAContract.View mainHomeView){
        CountDownManager.CountDownModel countDownModel = new CountDownManager.CountDownModel() {
            @Override
            public void onExecute() {
                if (mainHomeView.getActivity() == null)
                    return;
                mainHomeView.getActivity().runOnUiThread(() -> {
                    mainHomeView.changeSwitchviewWinggerByTime();
                    mainHomeView.changeRyActivityByTime();});
            }
        };
        return countDownModel;
    }






}