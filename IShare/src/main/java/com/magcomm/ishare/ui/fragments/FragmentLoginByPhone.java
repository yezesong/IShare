package com.magcomm.ishare.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.activitys.SignUpActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.views.TimerButton;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-1:37
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentLoginByPhone extends BaseFragment {

    private TopBar mTopBar;
    private TimerButton mTimerButton;
    private Button mLoginByPhone;
    private SignUpActivity mActivity;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimerButton.onDestroy();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mTopBar = bindView(R.id.topbar);
        mTimerButton = bindView(R.id.btn_timer);
        //mTimerButton.onCreate();
        mTimerButton.setTextAfter("秒后重发").setTextBefore("获取验证码");//.setLenght(5 * 1000);

        mTopBar.setOnTopBarListener(this);
        mTimerButton.setOnClickListener(this);
        mLoginByPhone = bindView(R.id.btn_login_byphone);
        mLoginByPhone.setOnClickListener(this);
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (SignUpActivity) getActivity();
        return inflater.inflate(R.layout.fragment_login_byphone, container, false);
    }

    @Override
    public void onLeftClick(View v) {
        mActivity.finish();
    }
}
