package com.magcomm.ishare.ui.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.utils.Debug;
import com.magcomm.sharelibrary.utils.glide.GlideRoundTransform;
import com.magcomm.sharelibrary.views.SettingsItem;
import com.magcomm.sharelibrary.views.TopBar;
import com.magcomm.sharelibrary.widget.dialog.BaseDialog;

import utils.SelectPhotoUtils;

/**
 * 作者:Created by yezesong on 16-3-8:39
 * 邮箱: yezesong@magcomm.cn
 */
public class UserInfoActivity extends BaseActivity {
    private static final String TAG = UserInfoActivity.class.getSimpleName();
    private static final int ITEM_SEX_MALE = 0;
    private static final int ITEM_SEX_FEMALE = 1;
    public static final int FLAG_EDIT_NICKNAME = 0x022;

    private TopBar mTopBar;
    private RelativeLayout mInfoHead;
    private ImageView mInfoHeadPic;
    private SettingsItem mInfoId;
    private SettingsItem mInfoName;
    private SettingsItem mInfoSex;
    private SettingsItem mInfoSettings;

    private SelectPhotoUtils mSelectPhotoUtils;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_userinfo);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        // 初始化上传头像工具
        mSelectPhotoUtils = new SelectPhotoUtils(this);
        mSelectPhotoUtils.setCallBack(mSelectPhotoCallBack);

        mTopBar = bindView(R.id.topbar);
        mInfoHead = bindView(R.id.info_head);
        mInfoHeadPic = bindView(R.id.info_head_pic);
        mInfoId = bindView(R.id.info_sharid);
        mInfoName = bindView(R.id.info_name);
        mInfoSex = bindView(R.id.info_sex);
        mInfoSettings = bindView(R.id.info_settings);

        mTopBar.setOnTopBarListener(this);
        mInfoHead.setOnClickListener(this);
        mInfoName.setOnClickListener(this);
        mInfoSex.setOnClickListener(this);
        mInfoSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_settings:
                Log.i(TAG, " itemClick is called and settings");
                startActivity(new Intent(UserInfoActivity.this, SettingsActivity.class));
                break;
            case R.id.info_name:
                Intent intent = new Intent(UserInfoActivity.this, UserNickNameActivity.class);
                intent.putExtra("nickname", mInfoName.getResult());
                startActivityForResult(intent, FLAG_EDIT_NICKNAME);
                break;
            case R.id.info_sex:
                BaseDialog.Builder builder = new BaseDialog.Builder(this, R.style.Dialog_SexSelect);
                builder.setTitle(R.string.select_sex_title)
                        .setItems(R.array.select_sex, mDialogClickListener)
                        .setNegativeButton(R.string.menu_cancel, null)
                        .isShowFromBottom(true)
                        .create()
                        .show();

                break;
            case R.id.info_head:
                mSelectPhotoUtils.select(SelectPhotoUtils.MODE_CROP, 1, 1);
                break;
        }
    }


    private DialogInterface.OnClickListener mDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case ITEM_SEX_MALE:
                    mInfoSex.setResult(R.string.txt_sex_male);
                    Debug.log(" male ");
                    break;
                case ITEM_SEX_FEMALE:
                    mInfoSex.setResult(R.string.txt_sex_female);
                    Debug.log(" female ");
                    break;
            }
        }
    };

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        BaseDialog.Builder dialog = new BaseDialog.Builder(this);
        dialog.setTitle(R.string.notice_title)
                .setMessage(R.string.notice_message)
                .setCancelable(false)
                .setNegativeButton(R.string.notice_no, null)
                .setPositiveButton(R.string.notice_yes, null)
                .create().show();
    }

    private SelectPhotoUtils.CallBack mSelectPhotoCallBack = new SelectPhotoUtils.CallBack() {
        @Override
        public void onResult(Uri url) {
            Glide.with(UserInfoActivity.this)
                    .load(url)
                    .transform(new GlideRoundTransform(UserInfoActivity.this))
                    .into(mInfoHeadPic);

            //mInfoHeadPic.setTag(R.id.tag_1, url.getPath());
        }

        @Override
        public void updateNick(String nickName) {
            mInfoName.setResult(nickName);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSelectPhotoUtils.onActivityResult(requestCode, resultCode, data);
    }
}
