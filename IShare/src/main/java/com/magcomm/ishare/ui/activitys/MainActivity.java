package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.config.AppConfig;
import com.magcomm.sharelibrary.manager.AnimRFLinearLayoutManager;
import com.magcomm.sharelibrary.utils.DensityUtils;
import com.magcomm.sharelibrary.utils.glide.GlideRoundTransform;
import com.magcomm.sharelibrary.views.AnimRFRecyclerView;
import com.magcomm.sharelibrary.views.FlowLayout;
import com.magcomm.sharelibrary.views.ImgTextView;
import com.magcomm.sharelibrary.views.ThumbUpView;
import com.magcomm.sharelibrary.views.TopBar;
import com.magcomm.sharelibrary.widget.AnimLayout;
import com.magcomm.sharelibrary.widget.MorePopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezesong on 16-2-23.
 */
public class MainActivity extends BaseActivity implements MorePopWindow.ListItemClick {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TopBar mTopBar;
    private ImgTextView mImgViewGroup, mImgViewCode;
    private MorePopWindow mPopWindow;
    private long mLastTime;
    private AnimRFRecyclerView mRecyclerView;
    private AnimLayout mSharePhotoLayout;
    private List<String> datas = new ArrayList<>();
    private Handler mHandler = new Handler();

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mImgViewGroup = bindView(R.id.img_group);
        mImgViewCode = bindView(R.id.img_code);
        mSharePhotoLayout = bindView(R.id.share_photo_layout);
        mRecyclerView = bindView(R.id.content_list);
        mImgViewGroup.setOnClickListener(this);
        mImgViewCode.setOnClickListener(this);
        mPopWindow = new MorePopWindow(MainActivity.this);
        mPopWindow.addItem(R.string.topbar_title_family_photos);
        mPopWindow.setListItemClick(this);
        mRecyclerView.setLayoutManager(new AnimRFLinearLayoutManager(this));
        // 设置头部恢复动画的执行时间，默认500毫秒
        mRecyclerView.setHeaderImageDurationMillis(600);
        // 设置拉伸到最高时头部的透明度，默认0.5f
        mRecyclerView.setHeaderImageMinAlpha(0.6f);
        // 设置适配器
        mRecyclerView.setAdapter(new MyAdapter());

        // 设置刷新和加载更多数据的监听，分别在onRefresh()和onLoadMore()方法中执行刷新和加载更多操作
        mRecyclerView.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener() {
            @Override
            public void setShow() {
                mSharePhotoLayout.showSelf();
            }

            @Override
            public void setHide() {
                mSharePhotoLayout.hideSelf();
            }

            @Override
            public void onRefresh() {
                new Thread(new MyRunnable(true)).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new MyRunnable(false)).start();
            }
        });

        // 刷新
        mRecyclerView.setRefresh(true);
    }

    class MyRunnable implements Runnable {

        boolean isRefresh;

        public MyRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {
                        newData();
                        refreshComplate();
                        // 刷新完成后调用，必须在UI线程中
                        mRecyclerView.refreshComplate();
                    } else {
                        addData();
                        loadMoreComplate();
                        // 加载更多完成后调用，必须在UI线程中
                        mRecyclerView.loadMoreComplate();
                    }
                }
            }, 2000);
        }
    }

    public void refreshComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadMoreComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    private void addData() {
        for (int i = 0; i < 13; i++) {
            datas.add("条目  " + (datas.size() + 1));
        }
    }

    public void newData() {
        datas.clear();
        for (int i = 0; i < 13; i++) {
            datas.add("刷新后条目  " + (datas.size() + 1));
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(MainActivity.this);
            view.setGravity(Gravity.CENTER);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    DensityUtils.dip2px(MainActivity.this, 50)));
            MyViewHolder mViewHolder = new MyViewHolder(view);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.mTextView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选择照片并分享
            case R.id.share_photos:
                startActivity(new Intent(MainActivity.this, SharePhotosActivity.class));
                break;
            //管理此相册
            case R.id.img_group:
                startActivity(new Intent(MainActivity.this, GroupManagActivity.class));
                break;
            //发送邀请码
            case R.id.img_code:
                startActivity(new Intent(MainActivity.this, InvitationActivity.class));
                break;
        }
    }

    @Override
    public void onListItemClick(int position) {
        if (position != 0)
            mPopWindow.removeData(position);
    }

    /**
     * 加入相册
     */
    @Override
    public void addGroup() {
        Intent addGroupIntent = new Intent(MainActivity.this, AddGroupActivity.class);
        startActivityForResult(addGroupIntent, REQUEST_CODE_ADD_GROUP);
    }

    /**
     * 创建相册
     */
    @Override
    public void createGroup() {
        Intent createGroupIntent = new Intent(MainActivity.this, CreateGroupActivity.class);
        startActivityForResult(createGroupIntent, REQUEST_CODE_CREATE_GROUP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_GROUP:
                break;
            case REQUEST_CODE_CREATE_GROUP:
                if (RESULT_OK == resultCode && data != null) {
                    String groupName = data.getStringExtra(RESULT_CREATE);
                    mPopWindow.addItem(groupName);
                }
                break;
        }
    }

    @Override
    public void onLeftClick(View v) {
        startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
    }

    @Override
    public void onRightClick(View v) {
        mPopWindow.showPopupWindow(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //已成功登录，下次可以直接进入
        AppConfig.getInstance().putBoolean(AppConfig.LOGINED, true);

        mTopBar = bindView(R.id.topbar);
        mTopBar.setOnTopBarListener(this);

        Glide.with(this)
                .load("https://www.baidu.com/img/bdlogo.png")
                .transform(new GlideRoundTransform(MainActivity.this))
                .placeholder(R.mipmap.ic_person_normal)
                .into(mTopBar.getImageView());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        long nowtime = System.currentTimeMillis();
        if (nowtime - mLastTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainActivity.this, R.string.click_again_exit, Toast.LENGTH_SHORT).show();
            mLastTime = nowtime;
        }
    }
}
