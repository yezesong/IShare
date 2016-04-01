package com.magcomm.sharelibrary.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.magcomm.sharelibrary.LibApp;
import com.magcomm.sharelibrary.R;
import com.magcomm.sharelibrary.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yezesong on 16-2-25.
 * <p/>
 * 倒计时按钮
 */
public class TimerButton extends Button implements View.OnClickListener {
    private static final String TAG = TimerButton.class.getSimpleName();
    private long LENGTH = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String TEXTAFTER = "秒后重发";
    private String TEXTBEFORE = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private long mTime;
    /**
     * 计时的Handle的 what选项
     */
    private static final int HANDLER_TIME = 1000;
    private static final int RESET_TIME = 1001;
    private Context mContext;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLER_TIME:
                    TimerButton.this.setText(mTime / 1000 + TEXTAFTER);
                    mTime -= 1000;
                    if (mTime < 0) {
                        TimerButton.this.setEnabled(true);
                        TimerButton.this.setText(TEXTBEFORE);
                        clearTimer();
                    }
                    break;
                case RESET_TIME:
                    mHandler.removeMessages(HANDLER_TIME);
                    TimerButton.this.setEnabled(true);
                    TimerButton.this.setText(TEXTBEFORE);
                    clearTimer();
                    break;
            }
        }
    };

    private void clearTimer() {
        TimerButton.this.setTextColor(getResources().getColor(R.color.color_light_green));
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        //mHandler.removeMessages(HANDLER_TIME);
        mHandler.sendEmptyMessage(RESET_TIME);
        System.gc();
    }

    private void initTimer() {
        mTime = LENGTH;
        mTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                Log.e(TAG, mTime / 1000 + "");
                TimerButton.this.setTextColor(getResources().getColor(R.color.timer_button_text_color));
                mHandler.sendEmptyMessage(HANDLER_TIME);
            }
        };
    }

    public TimerButton(Context context) {
        this(context, null);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null) {
            initTimer();
            this.setText(mTime / 1000 + TEXTAFTER);
            this.setEnabled(false);
            mTimer.schedule(mTimerTask, 0, 1000);
            mOnclickListener.onClick(v);
            return;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimerButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (LibApp.map == null) {
            LibApp.map = new HashMap<String, Long>();
        }
        LibApp.map.put(TIME, mTime);
        LibApp.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.e(TAG, "onDestroy");
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate() {
        Log.e(TAG, LibApp.map + "");
        if (LibApp.map == null) {
            return;
        }
        if (LibApp.map.size() <= 0) {// 这里表示没有上次未完成的计时
            return;
        }
        long time = System.currentTimeMillis() - LibApp.map.get(CTIME)
                - LibApp.map.get(TIME);
        LibApp.map.clear();
        if (time > 0) {
            return;
        } else {
            initTimer();
            this.mTime = Math.abs(time);
            mTimer.schedule(mTimerTask, 0, 1000);
            this.setText(time + TEXTAFTER);
            this.setEnabled(false);
        }
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimerButton setTextAfter(String text) {
        this.TEXTAFTER = text;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimerButton setTextBefore(String text) {
        this.TEXTBEFORE = text;
        this.setText(TEXTBEFORE);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param length 时间 默认毫秒
     * @return
     */
    public TimerButton setLenght(long length) {
        this.LENGTH = length;
        return this;
    }

    public void setRestTimer() {
        mHandler.sendEmptyMessage(RESET_TIME);
    }
}
