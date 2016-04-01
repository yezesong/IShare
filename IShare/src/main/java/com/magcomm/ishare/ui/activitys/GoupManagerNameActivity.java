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
 * 作者:Created by yezesong on 16-3-21:36
 * 邮箱: yezesong@magcomm.cn
 */
public class GoupManagerNameActivity extends BaseActivity implements TextEditView.EditInputChanger {
    private static final int RESULT_CODE_MANAGER_NAME = 0x200;
    private TextView mCancel;
    private TextView mSave;
    private TextEditView mGroupName;
    private String mGroupNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_name);
        init();
    }

    private void init() {
        mGroupName = bindView(R.id.txt_edit_group_name);
        mGroupNameStr = getIntent().getStringExtra("groupname");
        mGroupName.setTitle(mGroupNameStr);
        mGroupName.setClearVisible(true);
        mGroupName.setTextChanged(this);
        mCancel = bindView(R.id.title_cancel);
        mCancel.setOnClickListener(this);

        mSave = bindView(R.id.title_modify);
        mSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_modify:
                Intent intent = new Intent(GoupManagerNameActivity.this, GroupManagActivity.class);
                intent.putExtra("groupname", mGroupName.getContent().toString());
                setResult(RESULT_CODE_MANAGER_NAME, intent);
                finish();
                break;
            case R.id.title_cancel:
                setResult(ShareUtils.RESULT_CODE_CANCEL);
                finish();
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence s) {
        String nakeName = s.toString();
        if (!TextUtils.equals(nakeName, mGroupNameStr) && nakeName != null && !TextUtils.equals(nakeName, "")) {
            mSave.setEnabled(true);
        } else {
            mSave.setEnabled(false);
        }
    }
}
