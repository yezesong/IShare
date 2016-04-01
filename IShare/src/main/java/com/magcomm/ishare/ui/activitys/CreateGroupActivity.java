package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.views.TextEditView;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-10:52
 * 邮箱: yezesong@magcomm.cn
 */
public class CreateGroupActivity extends BaseActivity implements TextEditView.EditInputChanger {

    private TopBar mTopBar;
    private TextEditView mGroupName;
    private Button mBtnCreate;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_create_group);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_group:
                Intent intent = new Intent();
                intent.putExtra(RESULT_CREATE, mGroupName.getContent().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mTopBar = bindView(R.id.topbar);
        mGroupName = bindView(R.id.create_group_edit);
        mBtnCreate = bindView(R.id.btn_create_group);

        mTopBar.setOnTopBarListener(this);
        mBtnCreate.setOnClickListener(this);
        mGroupName.setTextChanged(this);

    }

    @Override
    public void onTextChanged(CharSequence s) {
        if (mGroupName.getContent().length() > 0) {
            mBtnCreate.setEnabled(true);
        } else {
            mBtnCreate.setEnabled(false);
        }
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }
}
