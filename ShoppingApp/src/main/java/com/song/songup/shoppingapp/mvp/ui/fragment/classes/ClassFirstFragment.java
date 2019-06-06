package com.song.songup.shoppingapp.mvp.ui.fragment.classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.di.component.classes.DaggerClassFirstComponent;
import com.song.songup.shoppingapp.mvp.contract.classes.ClassFirstContract;
import com.song.songup.shoppingapp.mvp.model.been.classes.ClassesBeen;
import com.song.songup.shoppingapp.mvp.presenter.classes.ClassFirstPresenter;
import com.song.songup.shoppingapp.mvp.ui.adapter.ClassesFirstAdapter;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.utils.count_down.CountDownManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class ClassFirstFragment extends MyBaseFragment<ClassFirstPresenter> implements ClassFirstContract.View {

    List<ClassesBeen> classesBeenList = new ArrayList<>();
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    ClassesFirstAdapter classesFirstAdapter;

    private CountDownManager.CountDownModel countDownModel = new CountDownManager.CountDownModel() {
        @Override
        public void onExecute() {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(() -> {
                if (classesFirstAdapter == null)
                    return;
                classesFirstAdapter.refreshTime();
            });
        }
    };

    public static ClassFirstFragment newInstance() {
        ClassFirstFragment fragment = new ClassFirstFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerClassFirstComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_first, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        for (int i = 0; i < 100; i++) {
            classesBeenList.add(new ClassesBeen("标题" + i, "1", 10 + i * 10));
        }

        initRecylceView();
        CountDownManager.asInstance().register(countDownModel);

    }

    public void initRecylceView(){
        classesFirstAdapter = new ClassesFirstAdapter(getContext(), classesBeenList);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(classesFirstAdapter);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroyView() {
        CountDownManager.asInstance().unRegister(countDownModel);
        super.onDestroyView();
    }
}
