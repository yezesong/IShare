package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.utils.ShareUtils;
import com.magcomm.sharelibrary.views.TextEditView;

/**
 * 作者:Created by yezesong on 16-3-21:52
 * 邮箱: yezesong@magcomm.cn
 */
public class UserNickNameActivity extends BaseActivity implements TextEditView.EditInputChanger {
    private static final int RECODE_NICK_NAME = 0x004;

    private TextEditView mNickName;
    private String mNickStr;
    private TextView mCancel, mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        init();
    }

    private void init() {
        mNickStr = getIntent().getStringExtra("nickname");
        mNickName = bindView(R.id.txt_edit_nick);
        mNickName.setTextChanged(this);
        mNickName.setTitle(mNickStr);
        mNickName.setClearVisible(true);

        mCancel = bindView(R.id.title_cancel);
        mCancel.setOnClickListener(this);
        mSave = bindView(R.id.title_modify);
        mSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_cancel:
                setResult(ShareUtils.RESULT_CODE_CANCEL);
                finish();
                break;
            case R.id.title_modify:
                Intent intent = new Intent(UserNickNameActivity.this, UserInfoActivity.class);
                intent.putExtra("nickname", mNickName.getContent().toString());
                setResult(RECODE_NICK_NAME, intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(ShareUtils.RESULT_CODE_CANCEL);
        super.onBackPressed();
    }

    @Override
    public void onTextChanged(CharSequence s) {
        Debug.log(" onTextChanged is called and s = " + s.toString());
        String nakeName = s.toString();
        if (!TextUtils.equals(nakeName, mNickStr) && nakeName != null && !TextUtils.equals(nakeName, "")) {
            mSave.setEnabled(true);
        } else {
            mSave.setEnabled(false);
        }
    }
}
