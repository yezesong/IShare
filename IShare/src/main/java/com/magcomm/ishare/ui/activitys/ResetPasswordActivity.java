package com.magcomm.ishare.ui.activitys;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.magcomm.ishare.R;
import com.magcomm.ishare.ui.fragments.FragmentResetComplement;
import com.magcomm.ishare.ui.fragments.FragmentResetPwHome;
import com.magcomm.ishare.ui.fragments.FragmentRestPwVerfi;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.BaseFragment;

/**
 * 作者:Created by yezesong on 16-3-10:57
 * 邮箱: yezesong@magcomm.cn
 */
public class ResetPasswordActivity extends BaseActivity implements BaseFragment.BaseFragmentListener {
    private FragmentResetPwHome mFragmentHome;
    private FragmentRestPwVerfi mFragmentRestPwVerfi;
    private FragmentResetComplement mCompleteFragment;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_reset_password_one);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        if (savedInstanceState == null) {
            mFragmentHome = new FragmentResetPwHome();
            changeFragment(mFragmentHome);
        }
    }

    @Override
    public void fragmentClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_home_next:
                if (mFragmentRestPwVerfi == null) {
                    mFragmentRestPwVerfi = new FragmentRestPwVerfi();
                }
                changeFragment(mFragmentRestPwVerfi, FLAG_CHANGE_FRAGMENT_ADD, true);
                break;
            case R.id.btn_reset_veri_next:
                if (mCompleteFragment == null) {
                    mCompleteFragment = new FragmentResetComplement();
                }
                changeFragment(mCompleteFragment, FLAG_CHANGE_FRAGMENT_REPLACE, true);
                break;

            case R.id.btn_reset_complete:
                Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //切换Fragment
    private void changeFragment(BaseFragment targetFragment) {
        this.changeFragment(targetFragment, FLAG_CHANGE_FRAGMENT_DEFAULT);
    }

    private void changeFragment(BaseFragment targetFragment, int flag) {
        this.changeFragment(targetFragment, flag, FLAG_BACK_FRAGMENT_DEFAULT);
    }

    private void changeFragment(BaseFragment targetFragment, int flag, boolean back) {
        super.changeFragment(R.id.reset_pw_content, targetFragment, flag, back);
    }
}
