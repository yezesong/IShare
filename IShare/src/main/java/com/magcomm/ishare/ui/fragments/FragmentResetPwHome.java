package com.magcomm.ishare.ui.fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.activitys.ResetPasswordActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.views.TextEditView;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-10:32
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentResetPwHome extends BaseFragment {
    private ResetPasswordActivity mActivity;

    private TopBar mTopBar;
    private TextEditView mResetEdit;
    private Button mRestBt;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (ResetPasswordActivity) getActivity();
        return inflater.inflate(R.layout.fragment_reset_pw, container, false);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mTopBar = bindView(R.id.topbar);
        mResetEdit = bindView(R.id.reset_pw_edit);
        mRestBt = bindView(R.id.btn_reset_home_next);
        mTopBar.setOnTopBarListener(this);
        mRestBt.setOnClickListener(this);
    }

    @Override
    public void onLeftClick(View v) {
        mActivity.finish();
    }
}
