package com.magcomm.sharelibrary.widget;

/**
 * 作者:Created by yezesong on 16-3-29:58
 * 邮箱: yezesong@magcomm.cn
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * 添加显示、隐藏动画
 */
public class AnimLayout extends LinearLayout {
    public AnimLayout(Context context) {
        this(context, null);
    }

    public AnimLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showSelf() {
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAction.setDuration(300);
        startAnimation(showAction);
        setVisibility(View.VISIBLE);
    }

    public void hideSelf() {
        TranslateAnimation hideAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        hideAction.setDuration(300);
        startAnimation(hideAction);

        setVisibility(View.GONE);
    }
}
