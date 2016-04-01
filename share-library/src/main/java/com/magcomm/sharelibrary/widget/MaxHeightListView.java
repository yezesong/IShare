package com.magcomm.sharelibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 作者:Created by yezesong on 16-3-10:29
 * 邮箱: yezesong@magcomm.cn
 */
public class MaxHeightListView extends ListView {
    private static final int MAX_HEIGHT = 330;

    public MaxHeightListView(Context context) {
        this(context, null);
    }

    public MaxHeightListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        if (height <= MAX_HEIGHT) {
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.UNSPECIFIED));
        } else {
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MAX_HEIGHT,
                    MeasureSpec.UNSPECIFIED));
        }
    }
}
