package com.magcomm.ishare.ui.fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.activitys.ResetPasswordActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.views.TimerButton;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-10:48
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentRestPwVerfi extends BaseFragment {
    private ResetPasswordActivity mActivity;

    private TopBar mTopBar;
    private TextView mTxtNotice;
    private TimerButton mTimerButton;
    private Button mNextBt;
    String notice;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (ResetPasswordActivity) getActivity();
        return inflater.inflate(R.layout.fragment_reset_pw_verfi, container, false);
    }

    @Override
    protected void initData() {
        super.initData();
        notice = getString(R.string.txt_reset_label) + "\n\n" + "152****1534";
        mTxtNotice = bindView(R.id.reset_tv_notice);
        mTimerButton = bindView(R.id.reset_pw_timer);
        mNextBt = bindView(R.id.btn_reset_veri_next);

        SpannableStringBuilder ssb = new SpannableStringBuilder(notice);
        ssb.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.color_light_green)),
                5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtNotice.setText(ssb);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        //mTimerButton.onCreate();
        mTimerButton.setTextAfter("秒后重发").setTextBefore("获取验证码");//.setLenght(5 * 1000);
        mTimerButton.setOnClickListener(this);
        mNextBt.setOnClickListener(this);
        mTopBar = bindView(R.id.topbar);
        mTopBar.setOnTopBarListener(this);
    }

    @Override
    public void onLeftClick(View v) {
        
    }
}
