package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.magcomm.ishare.R;
import com.magcomm.ishare.bean.User;
import com.magcomm.ishare.http.RequestListener;
import com.magcomm.ishare.http.ShareHttp;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.widget.SelectMenus;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.Url;

/**
 * Created by yezesong on 16-2-23.
 */
public class LoginActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private SelectMenus mSelectMenu;
    private Button mBtnLogin;
    private EditText mEditUserName;
    private EditText mEditPassword;
    private CheckBox mCBCheck;
    private CheckBox mCkVisible;

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                doLogin();
                break;
            case R.id.tv_signup://注册
                startActivity(createStartIntent(LoginActivity.this, SignUpActivity.class, FLAG_SIGN_UP));
                break;
            case R.id.tv_forget://忘记密码
                startActivity(LoginActivity.this, ResetPasswordActivity.class);
                break;
            case R.id.tv_message_login://短信验证登录
                showSelectMenus();
                break;
        }
    }

    private void doLogin() {
        startActivity(LoginActivity.this, MainActivity.class);
        finish();

        /*ShareHttp.postJsonRequest("login", Url.REGISTER_URL, getParams(), new RequestListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Debug.log("onSuccess and reponse = " + response.toString());
                startActivity(LoginActivity.this, MainActivity.class);
                finish();
            }

            @Override
            public void onFailed(VolleyError error) {
                Debug.log("onFailed and error = " + error.toString());
            }
        });*/
    }

    private Map<String, String> getParams() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put(User.Key.PHONE, mEditUserName.getText().toString());
        parmas.put(User.Key.PASS_WORD, mEditPassword.getText().toString());
        parmas.put(User.Key.VERIFY_CODE, "0000");
        return parmas;
    }

    private void showSelectMenus() {
        mSelectMenu = new SelectMenus(LoginActivity.this);
        mSelectMenu.addButton(R.string.menu_phone_number);
        mSelectMenu.addButton(R.string.menu_more);
        mSelectMenu.setOnClickListener(new SelectMenus.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.string.menu_phone_number:
                        startActivity(createStartIntent(LoginActivity.this, SignUpActivity.class, FLAG_LOGIN_BY_PHONE));
                        //startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                        break;
                    case R.string.menu_more:
                        startActivity(createStartIntent(LoginActivity.this, SignUpActivity.class, FLAG_LOGIN_BY_OTHER));
                        break;
                }
            }
        });
        mSelectMenu.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean enable1 = mEditUserName.getText().length() > 0;
        boolean enable2 = mEditPassword.getText().length() > 0;
        if (enable1 && enable2) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        //mHHandler = new HHandler(this);
        mBtnLogin = bindView(R.id.btn_login);
        mEditUserName = bindView(R.id.edt_user_name);
        mEditPassword = bindView(R.id.edt_pass_word);
        mCBCheck = bindView(R.id.cb_save);
        mCkVisible = bindView(R.id.ck_visible);

        mEditUserName.addTextChangedListener(this);
        mEditPassword.addTextChangedListener(this);
        mCkVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEditPassword.setSelection(mEditPassword.getText().length());
                if (isChecked) {
                    //显示密码
                    mEditPassword.setInputType(0x90);
                } else {
                    //隐藏密码
                    mEditPassword.setInputType(0x81);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setRootView() {
        //空实现，取消沉浸式效果
    }
}
