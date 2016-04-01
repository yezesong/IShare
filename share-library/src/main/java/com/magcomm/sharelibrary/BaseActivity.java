package com.magcomm.sharelibrary;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.utils.DensityUtils;
import com.magcomm.sharelibrary.views.TopBar;

import java.lang.ref.SoftReference;

/**
 * 作者:Created by yezesong on 16-2-27:16
 * 邮箱: yezesong@magcomm.cn
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener, TopBar.onTopBarListener {
    public static final int WHICH_MSG = 0X37210;
    protected static final String EXTRA_PREFS_SHOW_FRAGMENT = "extra_prefs_show_fragment";
    /**
     * 注册标志
     */
    protected static final int FLAG_SIGN_UP = 0x001;
    /**
     * 登录方式：手机号登录
     */
    protected static final int FLAG_LOGIN_BY_PHONE = 0x002;
    /**
     * 登录方式：微信、QQ等其它方式
     */
    protected static final int FLAG_LOGIN_BY_OTHER = 0x003;
    protected static final String EXTRA_GROUP_PREFS = "extra_group_prefs";
    /**
     * 分别是创建和加入圈的返回值
     */
    protected static final int REQUEST_CODE_CREATE_GROUP = 0x004;
    protected static final int REQUEST_CODE_ADD_GROUP = 0x005;
    protected static final String RESULT_CREATE = "result_create";
    protected static final String RESULT_ADD = "result_";
    protected BaseFragment mCurrentFragment;

    /**
     * 提交fragment时的模式
     */
    protected static final int FLAG_CHANGE_FRAGMENT_ADD = 0x001;
    protected static final int FLAG_CHANGE_FRAGMENT_REPLACE = 0x002;
    protected static final int FLAG_CHANGE_FRAGMENT_REMOVE = 0x004;
    protected static final int FLAG_CHANGE_FRAGMENT_HIDE = 0x006;
    protected static final int FLAG_CHANGE_FRAGMENT_SHOW = 0x008;
    protected static final int FLAG_CHANGE_FRAGMENT_DEFAULT = FLAG_CHANGE_FRAGMENT_ADD;
    protected static final boolean FLAG_BACK_FRAGMENT_DEFAULT = false;

    public Activity aty;

    private ThreadDataCallBack callback;
    private BaseActivityHandle threadHandle = new BaseActivityHandle(this);

    /**
     * 一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack {
        void onSuccess();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id, boolean click) {
        T view = (T) findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    private static class BaseActivityHandle extends Handler {
        private final SoftReference<BaseActivity> mOuterInstance;

        BaseActivityHandle(BaseActivity outer) {
            mOuterInstance = new SoftReference<>(outer);
        }

        // 当线程中初始化的数据初始化完成后，调用回调方法
        @Override
        public void handleMessage(android.os.Message msg) {
            BaseActivity aty = mOuterInstance.get();
            if (msg.what == WHICH_MSG && aty != null) {
                aty.callback.onSuccess();
            }
        }
    }

    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited() {

    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 在线程中初始化数据，注意不能在这里执行UI操作
     */

    public void initDataFromThread() {
        callback = new ThreadDataCallBack() {
            @Override
            public void onSuccess() {
                threadDataInited();
            }
        };
    }

    protected void initWidget(Bundle savedInstanceState) {
    }

    /**
     * 仅仅是为了代码整洁点
     */
    private void initializer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initDataFromThread();
                threadHandle.sendEmptyMessage(WHICH_MSG);
            }
        }).start();
    }

    /**
     * 打印Log接口
     *
     * @param tag
     * @param text
     */
    protected void log(String tag, String text) {
        Log.i(tag, text);
    }

    protected void log(String text) {
        log(aty.getClass(), text);
    }

    protected void log(Class cl, String text) {
        Log.i(cl.getSimpleName(), text);
    }

    /***************************************************************************
     * print Activity callback methods
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aty = this;
        setRootView();
        initializer();
        initWidget(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void setRootView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //yuan tong qin add
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.topbar_background));
            window.setNavigationBarColor(getResources().getColor(R.color.topbar_background));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        LibApp.screenH = DensityUtils.getScreenH(aty);
        LibApp.screenW = DensityUtils.getScreenW(aty);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback = null;
        threadHandle = null;
        mCurrentFragment = null;
        aty = null;
    }

    protected Intent createStartIntent(Activity from, Class ctx, int flag) {
        return new Intent(from, ctx)
                .putExtra(EXTRA_PREFS_SHOW_FRAGMENT, flag);
    }

    protected void startActivity(Activity from, Class ctx) {
        startActivity(new Intent(from, ctx));
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    protected void changeFragment(int resView, BaseFragment targetFragment) {
        changeFragment(resView, targetFragment, FLAG_CHANGE_FRAGMENT_DEFAULT);
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     * @param flag           targetFragment 执行操作的类型
     */
    protected void changeFragment(int resView, BaseFragment targetFragment, int flag) {
        changeFragment(resView, targetFragment, flag, FLAG_BACK_FRAGMENT_DEFAULT);
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     * @param flag           targetFragment 执行操作的类型
     */
    protected void changeFragment(int resView, BaseFragment targetFragment, int flag, boolean back) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (targetFragment.equals(mCurrentFragment)) {
            return;
        }
        switch (flag) {
            case FLAG_CHANGE_FRAGMENT_ADD:
            case FLAG_CHANGE_FRAGMENT_SHOW:
                if (!targetFragment.isAdded()) {
                    fragmentTransaction.add(resView, targetFragment, targetFragment.getClass()
                            .getName());
                }
                if (targetFragment.isHidden()) {
                    fragmentTransaction.show(targetFragment);
                    targetFragment.onChange();
                }
                if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
                    fragmentTransaction.hide(mCurrentFragment);
                }
                break;
            case FLAG_CHANGE_FRAGMENT_REMOVE:
                if (targetFragment.isAdded()) {
                    fragmentTransaction.remove(targetFragment);
                }

                break;
            case FLAG_CHANGE_FRAGMENT_HIDE:
                fragmentTransaction.hide(targetFragment);
                break;
            case FLAG_CHANGE_FRAGMENT_REPLACE:
                fragmentTransaction.replace(resView, targetFragment);
                break;
        }
        if (back) {
            fragmentTransaction.addToBackStack(targetFragment.getClass().getName());
        }
        mCurrentFragment = targetFragment;
        fragmentTransaction.commit();
    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    @Override
    public void onRightClick(View v) {

    }

    @Override
    public void onTitleClick(View v) {

    }
}
