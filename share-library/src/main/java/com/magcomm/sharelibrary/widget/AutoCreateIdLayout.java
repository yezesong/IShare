package com.magcomm.sharelibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 作者:Created by yezesong on 16-3-10:19
 * 邮箱: yezesong@magcomm.cn
 */
public class AutoCreateIdLayout extends LinearLayout {
    private static final String TAG = AutoCreateIdLayout.class.getSimpleName();
    private int mViewNumID;

    public AutoCreateIdLayout(Context context) {
        this(context, null);
    }

    public AutoCreateIdLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoCreateIdLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        mViewNumID = getChildCount();
        Log.i(TAG, "id = " + mViewNumID);
        child.setId(mViewNumID);
        super.addView(child);
    }
}
