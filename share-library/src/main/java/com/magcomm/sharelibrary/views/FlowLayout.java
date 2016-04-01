package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.magcomm.sharelibrary.R;

/**
 * 作者:Created by yezesong on 16-3-24:07
 * 邮箱: yezesong@magcomm.cn
 */
public class FlowLayout extends ViewGroup implements View.OnClickListener {
    private float mVerticalSpacing; //每个item纵向间距
    private float mHorizontalSpacing; //每个item横向间距
    private Context mContext;

    public FlowLayout(Context context) {
        super(context);
    }

    /**
     * 批量添加点赞
     *
     * @param texts
     */
    public void addThumbViews(String[] texts) {
        for (String text : texts) {
            addThumbView(text);
        }
    }

    public void addThumbView(String text) {
        ThumbUpView view = new ThumbUpView(mContext);
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            view.setText(text);
            view.setOnClickListener(this);
            setTag(view);
        }
        addView(view);
    }


    public void delThumbViews(String[] texts) {
        for (String text : texts) {
            delThumbView(text);
        }
    }

    public void delThumbView(String text) {
        ThumbUpView view = (ThumbUpView) getTag();
        if (TextUtils.equals(text, view.getText())) {
            removeView(view);
        }
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mVerticalSpacing = ta.getDimensionPixelOffset(R.styleable.FlowLayout_verticalspace, getDefaultVerticalSpace());
        mHorizontalSpacing = ta.getDimensionPixelOffset(R.styleable.FlowLayout_horizontalspace, getDefaultHorizontalSpace());
        ta.recycle();
    }

    public void setHorizontalSpacing(float pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(float pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    private int getDefaultVerticalSpace() {
        return getResources().getDimensionPixelOffset(R.dimen.default_vertical_space);
    }

    private int getDefaultHorizontalSpace() {
        return getResources().getDimensionPixelOffset(R.dimen.default_horizontal_space);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //通过计算每一个子控件的高度，得到自己的高度
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > selfWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(selfWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        //根据子控件的宽高，计算子控件应该出现的位置。
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ThumbUpView) {
            if (mListener != null) {
                mListener.onViewClick((ThumbUpView) v);
            }
        }
    }

    private onViewClickListener mListener;

    public void setListener(onViewClickListener listener) {
        mListener = listener;
    }

    public interface onViewClickListener {
        void onViewClick(ThumbUpView view);
    }
}
