package com.magcomm.ishare.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.activitys.MainActivity;
import com.magcomm.ishare.ui.activitys.SignUpActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.views.TopBar;


/**
 * 作者:Created by yezesong on 16-2-29:49
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentSignUpComplement extends BaseFragment {

    private TopBar mTopBar;
    private Button mBtnComplete;

    private SignUpActivity mActivity;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (SignUpActivity) getActivity();
        return View.inflate(mActivity, R.layout.fragment_signup_complete, null);
    }

    @Override
    protected void initWidget(View parentView) {
        mBtnComplete = bindView(R.id.btn_sign_complete);
        mBtnComplete.setOnClickListener(this);
    }
}
