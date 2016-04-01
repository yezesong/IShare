package com.magcomm.ishare.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.activitys.ResetPasswordActivity;
import com.magcomm.sharelibrary.BaseFragment;

/**
 * 作者:Created by yezesong on 16-3-11:31
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentResetComplement extends BaseFragment {
    private ResetPasswordActivity mActivity;
    private Button mCompleteBt;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (ResetPasswordActivity) getActivity();
        return inflater.inflate(R.layout.fragment_reset_pw_complete, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mCompleteBt = bindView(R.id.btn_reset_complete);
        mCompleteBt.setOnClickListener(this);
    }
}
