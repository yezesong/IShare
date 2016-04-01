package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by yezesong on 16-2-23.
 */
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MarqueeTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //setFocusable(true);
        //setFocusableInTouchMode(true);
        setGravity(Gravity.CENTER);
        // -1 get from setMarqueeRepeatLimit Note
        // or get from framework attr
        setMarqueeRepeatLimit(-1);
        setHorizontallyScrolling(true);
        setSingleLine(true);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) {
            super.onWindowFocusChanged(focused);
        }
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
