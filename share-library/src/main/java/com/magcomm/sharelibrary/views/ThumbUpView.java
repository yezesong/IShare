package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.widget.TextView;

import com.magcomm.sharelibrary.R;

/**
 * 作者:Created by yezesong on 16-3-24:43
 * 邮箱: yezesong@magcomm.cn
 */
public class ThumbUpView extends TextView {

    private static final int DEFAULT_TEXTSIZE = 15;

    public ThumbUpView(Context context) {
        super(context);
        setTextColor(getResources().getColorStateList(R.color.tv_get_verifi_bg));
        setTextSize(DEFAULT_TEXTSIZE);
        setEnabled(true);
        setClickable(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
