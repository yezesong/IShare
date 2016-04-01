package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.views.SettingsItem;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-9:59
 * 邮箱: yezesong@magcomm.cn
 */
public class GroupManagActivity extends BaseActivity {
    private static final int REQUEST_CODE_GROUP_NAME = 0x101;
    private TopBar mTopBar;
    private SettingsItem mGroupName, mPermission;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_group_manager);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mTopBar = bindView(R.id.topbar);
        mTopBar.setOnTopBarListener(this);

        mGroupName = bindView(R.id.group_name);
        mGroupName.setOnClickListener(this);
        mPermission = bindView(R.id.goup_permissions);
        mPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.group_name:
                Intent intent = new Intent(GroupManagActivity.this, GoupManagerNameActivity.class);
                intent.putExtra("groupname", mGroupName.getResult().toString());
                startActivityForResult(intent, REQUEST_CODE_GROUP_NAME);
                break;
            case R.id.goup_permissions:
                break;
        }
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GROUP_NAME:
                if (data != null) {
                    mGroupName.setResult(data.getStringExtra("groupname"));
                }
                break;
        }
    }
}
