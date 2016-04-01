package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.config.AppConfig;

/**
 * Created by yezesong on 16-2-23.
 */
public class ImgTextView extends LinearLayout {
    private static final String TAG = ImgTextView.class.getSimpleName();

    private Context mContext;
    private int mPadding;
    private Drawable mImageDrawable;
    private String mTextContent;
    private int mTextSize;
    private ColorStateList mTitleColor;

    private ImageView mImageView;
    private MarqueeTextView mTxtView;
    private boolean mClickable;

    public ImgTextView(Context context) {
        this(context, null);
    }

    public ImgTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImgTextView, defStyleAttr, 0);
        mPadding = ta.getDimensionPixelSize(R.styleable.ImgTextView_content_padding, getDefaultPadding());
        mImageDrawable = ta.getDrawable(R.styleable.ImgTextView_imgsrc);
        mTextContent = ta.getString(R.styleable.ImgTextView_content_str);
        mTextSize = ta.getDimensionPixelSize(R.styleable.ImgTextView_content_size, getDefaultTextSize());
        mTitleColor = ta.getColorStateList(R.styleable.ImgTextView_content_color);
        mClickable = ta.getBoolean(R.styleable.ImgTextView_clickable, false);
        if (mTitleColor == null) {
            mTitleColor = ColorStateList.valueOf(Color.BLACK);
        }
        ta.recycle();
    }

    private int getDefaultPadding() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.content_default_padding);
    }

    private int getDefaultTextSize() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.content_text_default_size);
    }

    private void getImageView() {
        if (mImageView == null) {
            mImageView = new ImageView(mContext);
            mImageView.setScaleType(ImageView.ScaleType.CENTER);
            if (mImageDrawable != null) {
                mImageView.setImageDrawable(mImageDrawable);
            } else {
                mImageView.setImageResource(R.mipmap.logo);
            }
            if (AppConfig.DEBUG)
                Log.i(TAG, "getImageView is called and mImageView = " + mImageView);
            addView(mImageView);
        }
    }

    private void getContentText() {
        if (mTxtView == null) {
            mTxtView = new MarqueeTextView(mContext);
            mTxtView.setTextSize(mTextSize);
            mTxtView.setTextColor(mTitleColor);
            if (mTextContent != null) {
                mTxtView.setText(mTextContent);
            } else {
                mTxtView.setText(R.string.app_name);
            }
            addView(mTxtView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int width = getMeasuredWidth();
        int imgLeft = (width - mImageView.getMeasuredWidth()) / 2;
        int txtLeft = (width - mTxtView.getMeasuredWidth()) / 2;
        int txtTop = mPadding * 2 + mImageView.getMeasuredHeight();

        Log.i(TAG, "onLayout is called and width = " + width + " and imgLeft = " + imgLeft + " and txtLeft = " + txtLeft);
        mImageView.layout(imgLeft, mPadding / 3, width + imgLeft / 2, mImageView.getMeasuredHeight() + mPadding / 3);
        mTxtView.layout(txtLeft / 2, txtTop - mPadding / 2, width - txtLeft / 2, txtTop + mTxtView.getMeasuredHeight() - mPadding / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        //mImageView.measure(measureSpec, measureSpec);
        //mTxtView.measure(measureSpec, measureSpec);

        int width = Math.max(mImageView.getMeasuredWidth(), mTxtView.getMeasuredWidth());
        int height = mImageView.getMeasuredHeight() + mPadding * 2 + mTxtView.getMeasuredHeight();
        if (AppConfig.DEBUG) Log.i(TAG, "width = " + width + " and height = " + height);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        getImageView();
        getContentText();
        setClickable(mClickable);
        setEnabled(mClickable);
    }
}
