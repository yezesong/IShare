package com.magcomm.ishare.ui.fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.magcomm.ishare.R;
import com.magcomm.ishare.bean.User;
import com.magcomm.ishare.http.RequestListener;
import com.magcomm.ishare.http.ShareHttp;
import com.magcomm.ishare.ui.activitys.MainActivity;
import com.magcomm.ishare.ui.activitys.SignUpActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.utils.ShareUtils;
import com.magcomm.sharelibrary.views.TextEditView;
import com.magcomm.sharelibrary.views.TimerButton;
import com.magcomm.sharelibrary.views.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Url;

/**
 * 作者:Created by yezesong on 16-2-27:40
 * 邮箱: yezesong@magcomm.cn
 */
public class FragmentSignUpHome extends BaseFragment implements TextEditView.EditInputChanger {
    private static final String TAG = "FragmentSignUpHome";
    private TopBar mTopBar;
    private TimerButton mTimerButton;
    private TextView mVoice;
    private TextView mTVDeal;
    private TextEditView mTxtEditPhoneNumber;
    private TextEditView mTxtEditVeri;
    private Button mBtnNext;
    private SignUpActivity mActivity;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mActivity = (SignUpActivity) getActivity();
        return inflater.inflate(R.layout.fragment_signup_home, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Debug.log("onDestroy is called");
        mTimerButton.onDestroy();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        mTopBar = bindView(R.id.topbar);
        mTimerButton = bindView(R.id.btn_timer);
        mTVDeal = bindView(R.id.tv_deal);
        mTxtEditPhoneNumber = bindView(R.id.txt_edit_phonenumber);
        mTxtEditVeri = bindView(R.id.txt_edit_verfi);
        mBtnNext = bindView(R.id.btn_sign_home_next);

        mVoice = bindView(R.id.tv_voice);
        mVoice.setOnClickListener(this);
        mTopBar.setOnTopBarListener(this);
        mTxtEditPhoneNumber.setTextChanged(this);
        mTxtEditVeri.setTextChanged(this);
        //mTimerButton.onCreate();
        mTimerButton.setEnabled(false);
        mTimerButton.setTextAfter("秒后重发").setTextBefore("获取验证码");//.setLenght(5 * 1000);
        mTimerButton.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mTVDeal.setOnClickListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s) {
        if (!ShareUtils.isMobileNO(mTxtEditPhoneNumber.getContent())) {
            mTimerButton.setEnabled(false);
            mBtnNext.setEnabled(false);
        } else {
            mTimerButton.setEnabled(true);
            boolean enable1 = mTxtEditPhoneNumber.getContent().length() > 0;
            boolean enable2 = mTxtEditVeri.getContent().length() > 0;
            if (enable1 && enable2) {
                mBtnNext.setEnabled(true);
            } else {
                mBtnNext.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_timer:
                getVerfiCode();
                break;
            case R.id.tv_voice:
                getVoiceCode();
                break;
        }
        super.onClick(v);
    }

    /**
     * 获取短信验证码
     */
    private void getVerfiCode() {
        if (mTxtEditPhoneNumber.getContent() == null) {
            return;
        }
        ShareHttp.getJsonRequest("verfi", getVeriCode(), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Debug.log("onSuccess and reponse = " + response.toString());
                //startActivity(mActivity.this, MainActivity.class);
                //finish();
            }

            @Override
            public void onFailed(VolleyError error) {
                Debug.log("onFailed and error = " + error.toString());
                mVoice.setEnabled(true);
            }
        });
    }

    /**
     * 获取短信验证码失败时，使用语音验证码
     */

    private void getVoiceCode() {
        Debug.log("getVoice  = " + getVoice());
        if (mTxtEditPhoneNumber.getContent() == null) {
            return;
        }
        ShareHttp.getJsonRequest("voice", getVoice(), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Debug.log("onSuccess and reponse = " + response.toString());

            }

            @Override
            public void onFailed(VolleyError error) {
                Debug.log("onFailed and error = " + error.toString());
            }
        });
    }

    private String getVoice() {
        return "http://192.168.0.68:8080/oauth2/oauth2/getvoice?phone="
                + mTxtEditPhoneNumber.getContent() + "&appsecret=fbed1d1b4b1449daa4bc49397cbe2350";
    }

    private String getVeriCode() {
        return "http://192.168.0.68:8080/oauth2/oauth2/getsms?phone="
                + mTxtEditPhoneNumber.getContent() + "&appsecret=fbed1d1b4b1449daa4bc49397cbe2350";
    }

    @Override
    public void onLeftClick(View v) {
        mActivity.finish();
    }
}
