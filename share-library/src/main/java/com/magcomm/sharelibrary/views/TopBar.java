package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.config.AppConfig;

/**
 * Created by yezesong on 16-2-24.
 */
public class TopBar extends ViewGroup implements View.OnClickListener {
    private static final String TAG = "TopBar";
    private ColorStateList mTitleColor;
    private int mTitleSize;
    private String mTitleStr;
    private int mTopBarHeight;
    private Button mLeftBtn, mRightButton;
    private ImageView mLeftImage;
    /**
     * 左边和右边是否可见
     */
    private int mLeftVisibility, mRightVisibility;
    private int mLeftTypeIcon;
    private MarqueeTextView mTitle;
    private onTopBarListener mTopBarListener;
    private Context mContext;
    private int mPadding;
    private int mStatusBarHeight;
    private boolean mClickable;
    private Drawable mLeftDrawable, mRightDrawable;
    private LinearLayout mTitleContent;
    private String mLeftStr;
    private boolean isKITKAT_LOLLIPOP = false;
    boolean ishidestatusbar = false;

    public void setOnTopBarListener(onTopBarListener listener) {
        mTopBarListener = listener;
    }

    public interface onTopBarListener {
        void onLeftClick(View v);

        void onRightClick(View v);

        void onTitleClick(View v);
    }

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar, defStyleAttr, 0);
        mTitleColor = ta.getColorStateList(R.styleable.TopBar_tp_titleColor);
        if (mTitleColor == null) {
            mTitleColor = ColorStateList.valueOf(Color.WHITE);
        }
        ishidestatusbar = ta.getBoolean(R.styleable.TopBar_isHideStatusBar,false);
        mTitleSize = ta.getDimensionPixelSize(R.styleable.TopBar_tp_textSize, getDefaultTextSize());
        mTitleStr = ta.getString(R.styleable.TopBar_tp_title);
        mLeftStr = ta.getString(R.styleable.TopBar_tp_left_text);
        mLeftVisibility = ta.getInt(R.styleable.TopBar_left_visible, View.VISIBLE);
        mRightVisibility = ta.getInt(R.styleable.TopBar_right_visible, View.GONE);
        mLeftDrawable = ta.getDrawable(R.styleable.TopBar_tp_left_icon);
        mRightDrawable = ta.getDrawable(R.styleable.TopBar_tp_right_icon);
        mClickable = ta.getBoolean(R.styleable.TopBar_title_clickable, false);
        mLeftTypeIcon = ta.getInt(R.styleable.TopBar_tp_left_icon_type, 0);
        ta.recycle();

        mStatusBarHeight = getStatusBarHeight();
        // yuan tong qin
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            mTopBarHeight =  getTopBarHeight() + mStatusBarHeight ;
            isKITKAT_LOLLIPOP = true;
        }else{
            mTopBarHeight =  getTopBarHeight();
            isKITKAT_LOLLIPOP = false;
        }
        if(ishidestatusbar){
            mTopBarHeight =  getTopBarHeight();
            isKITKAT_LOLLIPOP = false;
        }


       // mStatusBarHeight ;
        if (mLeftStr != null) {
            mPadding = 0;
        } else {
            mPadding = getDefaultPadding();
        }

        if (mLeftDrawable == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mLeftDrawable = getResources().getDrawable(R.drawable.ic_back, null);
            } else {
                mLeftDrawable = getResources().getDrawable(R.drawable.ic_back);
            }
        }

        if (mRightDrawable == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mRightDrawable = getResources().getDrawable(R.drawable.ic_more, null);
            } else {
                mRightDrawable = getResources().getDrawable(R.drawable.ic_more);
            }
        }
    }

    private int getDefaultPadding() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.topbar_padding);
    }

    private int getDefaultTextSize() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.topbar_title_default_size);
    }

    private int getTopBarHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.topbar_default_height);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.topbar_left_button) {
            if (mTopBarListener != null) {
                mTopBarListener.onLeftClick(v);
            }
        } else if (v.getId() == R.id.topbar_right_button) {
            if (mTopBarListener != null) {
                mTopBarListener.onRightClick(v);
            }
        } else if (v.getId() == R.id.topbar_center) {
            if (mTopBarListener != null) {
                mTopBarListener.onTitleClick(v);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        //yuan tong qin add start
        int top = 0;
        if(isKITKAT_LOLLIPOP){
           top = mStatusBarHeight;
        }else{
             top = 0;// + mPadding;
        }

        int left = mPadding;
        int right = width - mPadding;
        int bottom = mTopBarHeight;
        if (mLeftTypeIcon == 0) {
            mLeftBtn.layout(left, top, left + mLeftBtn.getMeasuredWidth(), bottom);
            mLeftBtn.setVisibility(mLeftVisibility);
        } else {
            mLeftImage.layout(left + mPadding, top + 4, mPadding + left + mLeftImage.getMeasuredWidth(), 4 + top + mLeftImage.getMeasuredWidth());
        }
        mRightButton.layout(right - mRightButton.getMeasuredWidth(), top, right, bottom);
        mRightButton.setVisibility(mRightVisibility);
        right = width - mRightButton.getMeasuredWidth() + 30;
        mTitleContent.layout(right - 60 - mTitle.getMeasuredWidth(), top, right, mTopBarHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //int titleW; //yuan tong qin add
        int titleW = 0;
        int titleH = 0;
        if(isKITKAT_LOLLIPOP){
            if (mLeftTypeIcon == 0) {
                mLeftBtn.measure(MeasureSpec.makeMeasureSpec(mTopBarHeight - mStatusBarHeight, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(mTopBarHeight - mStatusBarHeight, MeasureSpec.UNSPECIFIED));
                //titleW = getMeasuredWidth() - mLeftBtn.getMeasuredWidth() - mRightButton.getMeasuredWidth();
            } else {
                mLeftImage.measure(MeasureSpec.makeMeasureSpec(105, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(105, MeasureSpec.EXACTLY));
                //titleW = getMeasuredWidth() - mLeftImage.getMeasuredWidth() - mRightButton.getMeasuredWidth();
            }
            mRightButton.measure(MeasureSpec.makeMeasureSpec(mTopBarHeight - mStatusBarHeight, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(mTopBarHeight - mStatusBarHeight, MeasureSpec.UNSPECIFIED));

             titleW = getMeasuredWidth() - mRightButton.getMeasuredWidth() * 2;
             titleH = mTopBarHeight - mStatusBarHeight;
        }else{
            //int titleW;
            if (mLeftTypeIcon == 0) {
                mLeftBtn.measure(MeasureSpec.makeMeasureSpec(mTopBarHeight , MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(mTopBarHeight , MeasureSpec.UNSPECIFIED));
                //titleW = getMeasuredWidth() - mLeftBtn.getMeasuredWidth() - mRightButton.getMeasuredWidth();
            } else {
                mLeftImage.measure(MeasureSpec.makeMeasureSpec(105, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(105, MeasureSpec.EXACTLY));
                //titleW = getMeasuredWidth() - mLeftImage.getMeasuredWidth() - mRightButton.getMeasuredWidth();
            }
            mRightButton.measure(MeasureSpec.makeMeasureSpec(mTopBarHeight , MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(mTopBarHeight , MeasureSpec.UNSPECIFIED));

             titleW = getMeasuredWidth() - mRightButton.getMeasuredWidth() * 2;
             titleH = mTopBarHeight ;
        }

       //yuan tong qin add end

        mTitleContent.measure(
                MeasureSpec.makeMeasureSpec(titleW, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(titleH, MeasureSpec.EXACTLY));

        setMeasuredDimension(widthMeasureSpec, mTopBarHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setFitsSystemWindows(true);
        }
        getTitleContent();
        getTitle();
        getLeftView();
        getRightButton();
        setBackgroundResource(R.color.topbar_background);
    }

    public LinearLayout getTitleContent() {
        if (mTitleContent == null) {
            mTitleContent = new LinearLayout(getContext());
            mTitleContent.setGravity(Gravity.CENTER);
            mTitleContent.setOrientation(LinearLayout.VERTICAL);
            mTitleContent.setEnabled(mClickable);
            mTitleContent.setClickable(mClickable);
            mTitleContent.setId(R.id.topbar_center);
            mTitleContent.setOnClickListener(this);
            addView(mTitleContent);
        }
        return mTitleContent;
    }

    public void setTpTitle(int id) {
        if (mTitle != null) {
            mTitle.setText(id);
        }
    }

    public void setTpTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    private void getTitle() {
        getTitleContent();
        if (mTitle == null) {
            mTitle = new MarqueeTextView(mContext);
            mTitle.setText(mTitleStr);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
            mTitle.setIncludeFontPadding(false);
            mTitle.setGravity(Gravity.CENTER);
            //mTitle.setClickable(mClickable);
            //mTitle.setEnabled(mClickable);
            mTitle.setTextColor(getResources().getColor(R.color.ic_back_text_bg));
            mTitleContent.addView(mTitle);
        }
    }

    public ImageView getImageView() {
        if (mLeftImage != null) {
            return mLeftImage;
        }
        return null;
    }

    private void getLeftView() {
        if (mLeftTypeIcon == 0) {
            if (mLeftBtn == null) {
                mLeftBtn = new Button(mContext);
                mLeftBtn.setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, null, null);
                if (mLeftStr != null) {
                    mLeftBtn.setText(mLeftStr);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        mLeftBtn.setCompoundDrawablePadding(12);
                    } else {
                        mLeftBtn.setCompoundDrawablePadding(-10);
                    }
                    mLeftBtn.setTextColor(getResources().getColor(R.color.ic_back_text_bg));
                    mLeftBtn.setTextSize(18);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mLeftBtn.setBackground(null);
                }
                mLeftBtn.setGravity(Gravity.CENTER);
                mLeftBtn.setOnClickListener(this);
                mLeftBtn.setId(R.id.topbar_left_button);
                addView(mLeftBtn);
            }
        } else {
            if (mLeftBtn == null) {
                mLeftImage = new ImageView(mContext);
                //mLeftImage.setImageResource(R.mipmap.ic_person_normal);
                mLeftImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mLeftImage.setBackgroundResource(R.drawable.ic_person_cornor);
                mLeftImage.setPadding(5, 5, 5, 5);
                mLeftImage.setOnClickListener(this);
                mLeftImage.setId(R.id.topbar_left_button);
                addView(mLeftImage);
            }
        }
    }

    private void getRightButton() {
        if (mRightButton == null) {
            mRightButton = new Button(mContext);
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawable, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRightButton.setBackground(null);
            }

            mRightButton.setOnClickListener(this);
            mRightButton.setId(R.id.topbar_right_button);
            addView(mRightButton);
        }
    }

    private int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        if (AppConfig.DEBUG) Log.v(TAG, "Status height:" + height);
        return height;
    }
}
