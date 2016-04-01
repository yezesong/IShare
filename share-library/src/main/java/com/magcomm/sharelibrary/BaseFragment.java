package com.magcomm.sharelibrary;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.magcomm.sharelibrary.views.TopBar;

import java.lang.ref.SoftReference;

/**
 * 作者:Created by yezesong on 16-3-11:18
 * 邮箱: yezesong@magcomm.cn
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, TopBar.onTopBarListener {

    public static final int WHICH_MSG = 0X37211;

    protected View fragmentRootView;
    private ThreadDataCallBack callback;
    private BaseFragmentHandle threadHandle = new BaseFragmentHandle(this);

    /**
     * 一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack {
        void onSuccess();
    }

    private static class BaseFragmentHandle extends Handler {
        private final SoftReference<BaseFragment> mOuterInstance;

        BaseFragmentHandle(BaseFragment outer) {
            mOuterInstance = new SoftReference<>(outer);
        }

        // 当线程中初始化的数据初始化完成后，调用回调方法
        @Override
        public void handleMessage(android.os.Message msg) {
            BaseFragment fragment = mOuterInstance.get();
            if (msg.what == WHICH_MSG && fragment != null) {
                fragment.callback.onSuccess();
            }
        }
    }

    protected abstract View inflaterView(LayoutInflater inflater,
                                         ViewGroup container, Bundle bundle);

    /**
     * initialization widget, you should look like parentView.findviewbyid(id);
     * call method
     *
     * @param parentView 根View
     */
    protected void initWidget(View parentView) {
    }

    /**
     * initialization data
     */
    protected void initData() {
    }

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    public void onChange() {
    }

    /**
     * initialization data. And this method run in background thread, so you
     * shouldn't change ui<br>
     * on initializated, will call threadDataInited();
     */
    protected void initDataFromThread() {
        callback = new ThreadDataCallBack() {
            @Override
            public void onSuccess() {
                threadDataInited();
            }
        };
    }

    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited() {
    }

    @Override
    public void onClick(View v) {
        if (getActivity() instanceof BaseFragmentListener) {
            ((BaseFragmentListener) getActivity()).fragmentClick(v);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRootView = inflaterView(inflater, container, savedInstanceState);
        fragmentRootView.setTag(getClass().getSimpleName());

        initData();
        initWidget(fragmentRootView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initDataFromThread();
                threadHandle.sendEmptyMessage(WHICH_MSG);
            }
        }).start();
        return fragmentRootView;
    }

    protected <T extends View> T bindView(int id) {
        return (T) fragmentRootView.findViewById(id);
    }

    protected <T extends View> T bindView(int id, boolean click) {
        T view = (T) fragmentRootView.findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    public interface BaseFragmentListener {
        void fragmentClick(View view);
    }

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

    }

    @Override
    public void onTitleClick(View v) {

    }
}
