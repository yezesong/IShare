package com.magcomm.sharelibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.magcomm.sharelibrary.adapter.CommonAdapter;
import com.magcomm.sharelibrary.adapter.ViewHolder;
import com.magcomm.sharelibrary.utils.DensityUtils;

import com.magcomm.sharelibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:Created by yezesong on 16-3-9:48
 * 邮箱: yezesong@magcomm.cn
 */
public class MorePopWindow extends PopupWindow implements View.OnClickListener {

    private final Button mAddButton, mCreateButton;
    private ListView mListView;
    private PopAdapter mAdapter;
    private List<String> mViews = new ArrayList<String>();

    private Activity mContext;
    private ColorStateList mTextColor;

    public MorePopWindow(Activity context) {
        mContext = context;
        LayoutInflater inflater = context.getLayoutInflater();
        View conentView = inflater.inflate(R.layout.popup_menu, null);
        mListView = (ListView) conentView.findViewById(R.id.content);
        mAdapter = new PopAdapter(context, mViews, R.layout.pop_list_item);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListItemClick != null) {
                    mListItemClick.onListItemClick(position);
                    dismiss();
                }
            }
        });

        mAddButton = (Button) conentView.findViewById(R.id.add_group);
        mCreateButton = (Button) conentView.findViewById(R.id.create_group);
        mCreateButton.setOnClickListener(this);
        mAddButton.setOnClickListener(this);

        this.setAnimationStyle(R.style.PopuWindowAnimation);
        int w = DensityUtils.getDialogW(context);
        this.setWidth(w / 2 + 20);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundDrawable(context.getResources().getDrawable(R.drawable.more_pop_bg, null));
        } else {
            setBackgroundDrawable(context.getResources().getDrawable(R.drawable.more_pop_bg));
        }
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setOutsideTouchable(true);
        mTextColor = context.getResources().getColorStateList(R.color.ic_back_text_bg);
        setContentView(conentView);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, -175, 8);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_group) {
            if (mListItemClick != null) {
                mListItemClick.createGroup();
            }
        } else if (v.getId() == R.id.add_group) {
            if (mListItemClick != null) {
                mListItemClick.addGroup();
            }
        }
        dismiss();
    }

    public void addItem(int id) {
        addItem(mContext.getResources().getString(id));
    }

    public void addItem(String item) {
        if (mAdapter != null) {
            mAdapter.addData(item);
        }
    }

    public void removeData(int position) {
        if (mAdapter != null) {
            mAdapter.removeData(position);
        }
    }

    private class PopAdapter extends CommonAdapter<String> {

        public PopAdapter(Context context, List<String> datas, int itemLayoutId) {
            super(context, datas, itemLayoutId);
        }

        public void addData(String item) {
            super.addData(item);
        }

        public void removeData(int position) {
            super.removeData(position);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            helper.setText(R.id.poplistitem, item);
        }
    }

    private ListItemClick mListItemClick;

    public void setListItemClick(ListItemClick listItemClick) {
        mListItemClick = listItemClick;
    }

    public interface ListItemClick {
        void onListItemClick(int position);

        void addGroup();

        void createGroup();
    }
}
