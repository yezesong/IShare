package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.config.AppConfig;
import com.magcomm.sharelibrary.utils.Debug;

/**
 * 作者:Created by yezesong on 16-3-21:06
 * 邮箱: yezesong@magcomm.cn
 */
public class ShareTitleLayout extends LinearLayout {
    private int mTopBarHeight;
    private Context mContext;

    public ShareTitleLayout(Context context) {
        this(context, null);
    }

    public ShareTitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mTopBarHeight = getStatusBarHeight() + getTopBarHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setFitsSystemWindows(true);
        }
        setBackgroundResource(R.color.topbar_background);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, mTopBarHeight);
    }

    private int getTopBarHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.topbar_default_height);
    }

    private int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        if (AppConfig.DEBUG) Debug.log("Status height:" + height);
        return height;
    }
}
