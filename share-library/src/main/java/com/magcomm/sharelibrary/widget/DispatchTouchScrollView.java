package com.magcomm.sharelibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.magcomm.sharelibrary.utils.Debug;

/**
 * 作者:Created by yezesong on 16-4-1:42
 * 邮箱: yezesong@magcomm.cn
 */
public class DispatchTouchScrollView extends ScrollView {
    private boolean mIntercept;

    public DispatchTouchScrollView(Context context) {
        this(context, null);
    }

    public DispatchTouchScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DispatchTouchScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Debug.log(" onInterceptTouchEvent is called ");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //Debug.log(" onTouchEvent is called ");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Debug.log(" dispatchTouchEvent is called ");
        return super.dispatchTouchEvent(ev);
    }
}
