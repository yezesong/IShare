package com.magcomm.sharelibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.utils.DensityUtils;

import android.view.WindowManager.LayoutParams;

/**
 * Created by yezesong on 16-2-25.
 */
public class SelectMenus extends Dialog implements View.OnClickListener {
    private final int TEXT_SIZE = 20;
    private final LinearLayout mContent;
    private final Button mCancel;
    OnClickListener mListener;

    public SelectMenus(Context context) {
        super(context);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.select_menu, null);
        mContent = (LinearLayout) v.findViewById(R.id.content);
        mCancel = (Button) v.findViewById(R.id.menu_cancel);
        mCancel.setOnClickListener(this);

        addContentView(v, getLayoutParams());
    }

    private LayoutParams getLayoutParams() {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.width = DensityUtils.getScreenW(getContext());//ShareUtils.getScreenSize(getContext())[0];
        return lp;
    }

    public void addButton(int id) {
        addButton(id, R.drawable.select_menu_item_bg);
    }

    public void addButton(int id, int backgroud) {
        addButton(id, backgroud, mContent);
    }

    public void addButton(int id, int backgroud, LinearLayout root) {
        final int h = getContext().getResources().getDimensionPixelOffset(R.dimen.select_menu_item_height);
        Button bu = new Button(getContext());
        bu.setText(id);
        bu.setTextSize(TEXT_SIZE);
        bu.setTextColor(getContext().getResources().getColor(R.color.color_light_gray));
        bu.setId(id);
        bu.setBackgroundResource(backgroud);
        bu.setMinHeight(h);
        bu.setOnClickListener(this);
        root.addView(bu);
    }

    public Button getItemById(int id) {
        for (int i = 0; i < mContent.getChildCount(); i++) {
            if (id == mContent.getChildAt(i).getId()) {
                return (Button) mContent.getChildAt(i);
            }
        }
        return null;
    }

    public void addButton(String name) {
        addButton(name, R.drawable.select_menu_item_bg);
    }

    public void addButton(String name, int backgroud) {
        addButton(name, backgroud, mContent);
    }

    public void addButton(String name, int backgroud, LinearLayout root) {
        final int h = getContext().getResources().getDimensionPixelOffset(R.dimen.select_menu_item_height);
        Button bu = new Button(getContext());
        bu.setText(name);
        bu.setTextSize(TEXT_SIZE);
        bu.setTextColor(getContext().getResources().getColor(R.color.color_light_gray));
        bu.setTag(name);
        bu.setBackgroundResource(backgroud);
        bu.setMinHeight(h);
        bu.setOnClickListener(this);
        root.addView(bu);
    }

    public Button getButtonById(String tag) {
        for (int i = 0; i < mContent.getChildCount(); i++) {
            Button child = (Button) mContent.getChildAt(i);
            String _tag = (String) child.getTag();
            if (TextUtils.equals(tag, _tag)) {
                return child;
            }
        }
        return null;
    }

    public void addButton(int id, String text, int backgroud, LinearLayout root) {
        final int h = getContext().getResources().getDimensionPixelOffset(R.dimen.select_menu_item_height);
        Button bu = new Button(getContext());
        bu.setId(id);
        bu.setText(text);
        bu.setTextSize(TEXT_SIZE);
        bu.setTextColor(getContext().getResources().getColor(R.color.color_light_gray));
        bu.setTag(text);
        bu.setBackgroundResource(backgroud);
        bu.setMinHeight(h);
        bu.setOnClickListener(this);
        root.addView(bu);
    }

    public void addGroupButtons(int[] ids) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.select_menu_group_root, null);
        for (int i = 0; i < ids.length; i++) {
            /*if (i == 0) {
                addButton(ids[i], R.drawable.care_menu_item_top_bg, root);
            } else if (i == ids.length - 1) {
                addButton(ids[i], R.drawable.care_menu_item_bottom_bg, root);
            } else {
                addButton(ids[i], R.drawable.care_menu_item_middle_bg, root);
            }*/
            addButton(ids[i], R.drawable.select_menu_item_bg, root);
        }
        mContent.addView(root);
    }

    public void addGroupButtons(String[] ids) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.select_menu_group_root, null);
        for (int i = 0; i < ids.length; i++) {
            /*if (i == 0) {
                addButton(ids[i], R.drawable.care_menu_item_top_bg, root);
            } else if (i == ids.length - 1) {
                addButton(ids[i], R.drawable.select_menu_item_bg, root);
            } else {
                addButton(ids[i], R.drawable.care_menu_item_middle_bg, root);
            }*/
            addButton(ids[i], R.drawable.select_menu_item_bg, root);
        }
        mContent.addView(root);
    }


    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        setCanceledOnTouchOutside(false);
        window.setAttributes(lp);
//        window.setBackgroundDrawable(null);

        window.setBackgroundDrawableResource(R.drawable.dialogback);
        window.setWindowAnimations(R.style.SelectMenuAnim);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onClick(View v);
    }

    @Override
    public void onClick(View v) {
        /*if (v.getId() == R.id.menu_cancel) {
            this.dismiss();
            return;
        }*/
        this.dismiss();
        if (mListener != null) {
            mListener.onClick(v);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && isShowing()) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}
