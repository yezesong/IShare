package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.fragments.FragmentLoginByPhone;
import com.magcomm.ishare.ui.fragments.FragmentSignUpComplement;
import com.magcomm.ishare.ui.fragments.FragmentSignUpHome;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.BaseFragment;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.views.TopBar;

public class SignUpActivity extends BaseActivity implements BaseFragment.BaseFragmentListener {
    private FragmentSignUpHome mFragmentSignHome;
    private FragmentSignUpComplement mFragmentSignComplete;
    private FragmentLoginByPhone mFragmentLoginByPhone;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_signup_home);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        if (savedInstanceState == null) {
            mFragmentSignHome = new FragmentSignUpHome();
            mFragmentLoginByPhone = new FragmentLoginByPhone();
            switch (getIntent().getIntExtra(EXTRA_PREFS_SHOW_FRAGMENT, FLAG_SIGN_UP)) {
                case FLAG_SIGN_UP://注册
                    changeFragment(R.id.main_content, mFragmentSignHome);
                    break;
                case FLAG_LOGIN_BY_PHONE://手机号登录
                    changeFragment(R.id.main_content, mFragmentLoginByPhone);
                    break;
                case FLAG_LOGIN_BY_OTHER://其它第三方登录接口

                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void fragmentClick(View view) {
        switch (view.getId()) {
            //普通注册
            case R.id.btn_sign_home_next:
                if (mFragmentSignComplete == null) {
                    mFragmentSignComplete = new FragmentSignUpComplement();
                }
                changeFragment(R.id.main_content, mFragmentSignComplete, FLAG_CHANGE_FRAGMENT_ADD, true);
                break;
            //注册成功并跳转至主界面
            case R.id.btn_sign_complete:
                Intent signIntent = new Intent(SignUpActivity.this, MainActivity.class);
                signIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signIntent);
                SignUpActivity.this.finish();
                break;
            //使用手机号登录
            case R.id.btn_login_byphone:

                break;
        }
    }
}
