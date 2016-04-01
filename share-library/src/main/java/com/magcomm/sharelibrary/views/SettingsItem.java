package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magcomm.sharelibrary.R;

/**
 * 作者:Created by yezesong on 16-3-9:44
 * 邮箱: yezesong@magcomm.cn
 */
public class SettingsItem extends ViewGroup {
    private Context mContext;
    private TextView mTitleView, mResultView;
    private ImageView mTipView;
    private String mTitle, mResult;
    private int mMargin;
    private boolean mClickable;
    private int mTipVisible;

    private int mHeight;

    public SettingsItem(Context context) {
        this(context, null);
    }

    public SettingsItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingsItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem, defStyleAttr, 0);
        mTitle = ta.getString(R.styleable.SettingsItem_tp_title);
        mResult = ta.getString(R.styleable.SettingsItem_content_str);
        mMargin = ta.getDimensionPixelOffset(R.styleable.SettingsItem_settings_item_margin, getDefaultMargin());
        mClickable = ta.getBoolean(R.styleable.SettingsItem_title_clickable, true);
        mTipVisible = ta.getInt(R.styleable.SettingsItem_tip_visible, View.VISIBLE);
        ta.recycle();

        mHeight = getImtesHeight();
    }

    private int getImtesHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.settings_item_height);
    }

    private int getDefaultMargin() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.settings_default_margin);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        mTitleView.layout(mMargin, (mHeight - mTitleView.getMeasuredHeight()) / 2, mMargin + mTitleView.getMeasuredWidth(),
                (mHeight + mTitleView.getMeasuredHeight()) / 2);

        if (View.GONE != mTipVisible) {
            mResultView.layout(width - mResultView.getMeasuredWidth() - mTipView.getMeasuredWidth() - mMargin * 3 / 2,
                    (mHeight - mResultView.getMeasuredHeight()) / 2, width - mTipView.getMeasuredWidth() - mMargin * 3 / 2,
                    (mHeight + mResultView.getMeasuredHeight()) / 2);
            mTipView.layout(width - mMargin - mTipView.getMeasuredWidth(), 0, width - mMargin, mHeight);
        } else {
            mResultView.layout(width - mResultView.getMeasuredWidth() - mMargin,
                    (mHeight - mResultView.getMeasuredHeight()) / 2, width - mMargin,
                    (mHeight + mResultView.getMeasuredHeight()) / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        mTitleView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.UNSPECIFIED));

        mResultView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.UNSPECIFIED));

        mTipView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.UNSPECIFIED));
        setMeasuredDimension(width, mHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addTitle();
        addResult();
        addTipIcon();
        setClickable(mClickable);
    }

    /**
     * 返回中间“返回值”
     *
     * @return
     */
    public String getResult() {
        if (mResultView != null) {
            return mResultView.getText().toString();
        }
        return "";
    }

    /**
     * 设置中间“返回值”
     *
     * @param result
     */
    public void setResult(String result) {
        if (mResultView != null) {
            mResultView.setText(result);
        }
    }

    /**
     * 设置中间“返回值”
     *
     * @param id
     */
    public void setResult(int id) {
        if (mResultView != null) {
            mResultView.setText(id);
        }
    }

    private void addTitle() {
        if (mTitleView == null) {
            mTitleView = new TextView(mContext);
            if (mTitle != null) {
                mTitleView.setText(mTitle);
                mTitleView.setGravity(Gravity.CENTER);
                mTitleView.setTextColor(mContext.getResources().getColor(R.color.color_light_gray));
                mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            }
            addView(mTitleView);
        }
    }

    private void addResult() {
        if (mResultView == null) {
            mResultView = new TextView(mContext);
            if (mResult != null) {
                mResultView.setText(mResult);
                mResultView.setGravity(Gravity.CENTER);
                mResultView.setTextColor(mContext.getResources().getColor(R.color.color_light_gray));
                mResultView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            }
            addView(mResultView);
        }
    }

    private void addTipIcon() {
        if (mTipView == null) {
            mTipView = new ImageView(mContext);
            mTipView.setImageResource(R.mipmap.ic_tip);
            mTipView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            addView(mTipView);
        }
    }
}
