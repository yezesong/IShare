package com.magcomm.ishare.ui.activitys;

import android.os.Bundle;
import android.view.View;

import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.views.TopBar;

/**
 * 作者:Created by yezesong on 16-3-10:55
 * 邮箱: yezesong@magcomm.cn
 */
public class AddGroupActivity extends BaseActivity {
    private TopBar mTopBar;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_add_group);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mTopBar = bindView(R.id.topbar);
        mTopBar.setOnTopBarListener(this);
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }
}
