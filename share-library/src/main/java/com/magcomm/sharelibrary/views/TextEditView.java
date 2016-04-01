package com.magcomm.sharelibrary.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.utils.Debug;

/**
 * Created by yezesong on 16-2-24.
 */
public class TextEditView extends LinearLayout implements View.OnClickListener {
    private TextView mLabel;
    private EditText mInputTxt;
    private String mLabelStr;
    private String mHint;
    private Context mContext;
    private int mCotentSize;
    private Button mClearButton;
    private Drawable mButtonDrawable;
    private int mInputType;
    private int mLength;
    private int mLabelWidth;
    private ColorStateList mEditTextColor;

    private EditInputChanger mEditInputChanger;

    public TextEditView(Context context) {
        this(context, null);
    }

    public TextEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextEditView, defStyleAttr, 0);
        mLabelStr = ta.getString(R.styleable.TextEditView_txt_edit_label);
        mHint = ta.getString(R.styleable.TextEditView_txt_edit_hint);
        mInputType = ta.getInt(R.styleable.TextEditView_edittext_inputtype, 0);
        mLength = ta.getInt(R.styleable.TextEditView_txt_content_length, 0);
        mLabelWidth = ta.getDimensionPixelSize(R.styleable.TextEditView_txt_label_width, getDefaultLableWith());
        mEditTextColor = ta.getColorStateList(R.styleable.TextEditView_txt_edit_textcolor);
        ta.recycle();

        if (mLabelStr == null) {
            mLabelStr = getResources().getString(R.string.edit_label_default);
        }

        if (mHint == null) {
            mHint = getResources().getString(R.string.app_name);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mButtonDrawable = getResources().getDrawable(R.drawable.edit_clear, null);
        } else {
            mButtonDrawable = getResources().getDrawable(R.drawable.edit_clear);
        }

        mCotentSize = getDefaultTextSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        LinearLayout.LayoutParams lpLabel = (LinearLayout.LayoutParams) mLabel.getLayoutParams();
        lpLabel.width = mLabelWidth;
        mLabel.setLayoutParams(lpLabel);

        LinearLayout.LayoutParams lpEdit = (LinearLayout.LayoutParams) mInputTxt.getLayoutParams();
        lpEdit.width = 0;
        lpEdit.height = getMeasuredHeight();
        lpEdit.weight = 1;
        mInputTxt.setLayoutParams(lpEdit);

        LinearLayout.LayoutParams lpBt = (LinearLayout.LayoutParams) mClearButton.getLayoutParams();
        lpBt.width = 85;
        lpBt.height = getMeasuredHeight();
        mClearButton.setLayoutParams(lpBt);
    }

    private int getDefaultLableWith() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.default_label_width);
    }

    private int getDefaultTextSize() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.edit_default_size);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        getLabel();
        getEditText();
        getClearEdit();
    }

    public void setLabel(String lable) {
        if (mLabel != null) {
            mLabel.setText(lable);
        }
    }

    public void setTitle(int id) {
        setTitle(getResources().getString(id));
    }

    public void setTitle(String title) {
        if (mInputTxt != null) {
            mInputTxt.setText(title);
            mInputTxt.setSelection(title.length());
        }
    }


    private void getLabel() {
        if (mLabel == null) {
            mLabel = new TextView(mContext);
            mLabel.setText(mLabelStr);
            mLabel.setTextSize(mCotentSize);
            mLabel.setIncludeFontPadding(false);
            mLabel.setGravity(Gravity.CENTER);
            mLabel.setTranslationX(30);
            mLabel.setSingleLine();
            mLabel.setTextColor(getResources().getColor(R.color.color_light_gray));
            addView(mLabel);
        }
    }

    private void getEditText() {
        if (mInputTxt == null) {
            mInputTxt = new EditText(mContext);
            mInputTxt.setTextSize(mCotentSize);
            mInputTxt.setHint(mHint);
            mInputTxt.setTranslationX(50);
            mInputTxt.setSingleLine();
            mInputTxt.setPadding(0, 0, 55, 0);
            mInputTxt.setTag(this);
            if (mLength > 0) {
                mInputTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mLength)});
            }
            mInputTxt.setInputType(mInputType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mInputTxt.setBackground(null);
            }
            if (mEditTextColor == null) {
                mInputTxt.setTextColor(getResources().getColor(R.color.edit_input_default_color));
            } else {
                mInputTxt.setTextColor(mEditTextColor);
            }
            mInputTxt.setHintTextColor(getResources().getColor(R.color.edit_hint_default_color));
            addView(mInputTxt);
        }
    }

    private void getClearEdit() {
        mClearButton = new Button(mContext);
        mClearButton.setCompoundDrawablesWithIntrinsicBounds(null, null, mButtonDrawable, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mClearButton.setBackground(null);
        }
        mClearButton.setOnClickListener(this);
        mClearButton.setVisibility(View.GONE);
        addView(mClearButton);
    }

    private TextWatcher myWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            Debug.log(" s. " + s.toString().trim().length());
            if (s.toString().trim().length() > mLength - 1) {
                String error1 = getResources().getString(R.string.length_to_long);
                String error = String.format(error1, mLabelStr, mLength);
                // mInputTxt.setError(error);
            }

            if (mEditInputChanger != null) {
                mEditInputChanger.onTextChanged(s);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            if (s != null && !s.toString().equals("")) {
                if (mClearButton != null) {
                    mClearButton.setVisibility(View.VISIBLE);
                }
            } else {
                if (mClearButton != null) {
                    mClearButton.setVisibility(View.GONE);
                }
            }
        }
    };

    public void setClearVisible(boolean visible) {
        mClearButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mInputTxt != null) {
            mInputTxt.setText("");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInputTxt != null)
            mInputTxt.addTextChangedListener(myWatcher);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInputTxt != null)
            mInputTxt.removeTextChangedListener(myWatcher);
    }

    public void setTextChanged(EditInputChanger editInputChanger) {
        this.mEditInputChanger = editInputChanger;
    }

    public interface EditInputChanger {
        void onTextChanged(CharSequence s);
    }

    public String getContent() {
        if (mInputTxt != null) {
            return mInputTxt.getText().toString();
        }
        return "";
    }
}
