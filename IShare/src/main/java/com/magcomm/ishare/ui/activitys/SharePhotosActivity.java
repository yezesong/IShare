package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.config.AppConfig;
import com.magcomm.sharelibrary.utils.SoundMeter;
import com.magcomm.sharelibrary.utils.ToastUtils;
import com.magcomm.sharelibrary.widget.SelectMenus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 作者:Created by yezesong on 16-3-8:45
 * 邮箱: yezesong@magcomm.cn
 */
public class SharePhotosActivity extends FragmentActivity implements View.OnClickListener, SoundMeter.IHintRecoding {
    public static final String HANDLER_THREAD_NAME = "ShareHandler";
    private SelectMenus mSelectMenu;
    private static final int REQUEST_CODE_PICK_IMAGE = 0x001;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 0x002;
    private boolean mEffectLocation;
    private ImageView mSharePicView;
    private TextView mShareVoice;
    private ShareHandler mShareHandler;// = new Handler();
    private Handler mHandler = new Handler();
    private String mVoiceName;
    private long mStartVoiceT, mEndVoiceT;
    private long mTime;
    private LinearLayout mVoicePop;
    private LinearLayout mPlayDel, mPlay;
    private Button mBtnDel;
    private ImageView mVoiceIcon, mVoiceVolum;
    private TextView mVoiceDes;
    private SoundMeter mSensor;
    private ImageView mPlaySrc;
    private TextView mVoiceTime;
    private AnimationDrawable mAnimationDrawable;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private HandlerThread mHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_photos);
        //showSelectMenus();
        initWidget(savedInstanceState);
    }

    /*@Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_share_photos);
        //showSelectMenus();
    }*/

    private <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

    private void initWidget(Bundle savedInstanceState) {
        //super.initWidget(savedInstanceState);
        //mHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        //mHandlerThread.start();
        //mShareHandler = new ShareHandler(mHandlerThread.getLooper());

        mSharePicView = bindView(R.id.imageview_select_photos);
        mVoicePop = bindView(R.id.voice_popup);
        mVoiceVolum = bindView(R.id.voice_volum);
        mVoiceIcon = bindView(R.id.voice_icon);
        mVoiceDes = bindView(R.id.voice_text);
        mPlayDel = bindView(R.id.play_and_del);
        mBtnDel = bindView(R.id.del_voice);
        mBtnDel.setOnClickListener(this);
        mPlay = bindView(R.id.play_voice_bg);
        mPlay.setOnClickListener(this);

        mSensor = SoundMeter.getInstance();
        mSensor.setIHintRecoding(this);
        mPlaySrc = bindView(R.id.play_voice_src);
        mVoiceTime = bindView(R.id.voice_time);
        mShareVoice = bindView(R.id.share_voice);
    }

    @Override
    public void onClick(View view) {
        //super.onClick(view);
        switch (view.getId()) {
            case R.id.del_voice:
                mShareVoice.setVisibility(View.VISIBLE);
                mPlayDel.setVisibility(View.GONE);
                try {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    File file = new File(AppConfig.audioPath + mVoiceName);
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.play_voice_bg:
                playVoice(AppConfig.audioPath + mVoiceName);
                //sendThreadHandlerMessage(ShareHandler.START_PLAY);
                break;
        }
    }

    private void playVoice(String name) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mPlaySrc.setImageResource(R.drawable.playvoice_anim);
            mAnimationDrawable = (AnimationDrawable) mPlaySrc.getDrawable();
            mAnimationDrawable.start();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(name);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mAnimationDrawable.stop();
                    mPlaySrc.setImageResource(R.mipmap.voice_play_normal);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSelectMenus() {
        mSelectMenu = new SelectMenus(SharePhotosActivity.this);
        mSelectMenu.addButton(R.string.menu_take_photo);
        mSelectMenu.addButton(R.string.menu_select_photo);
        mSelectMenu.setOnClickListener(new SelectMenus.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.string.menu_take_photo:
                        getImageFromCamera();
                        break;
                    case R.string.menu_select_photo:
                        getImageFromAlbum();
                        break;
                    case R.id.menu_cancel:
                        finish();
                        break;
                }
            }
        });
        mSelectMenu.show();
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            finish();
            return;
        }
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                Glide.with(this).load(uri).into(mSharePicView);
            } else {
                finish();
                return;
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(mSharePicView);
            if (uri == null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data");
                    //Glide.with(this).load(photo).into(mSharePicView);
                    mSharePicView.setImageBitmap(photo);
                } else {
                    finish();
                    return;
                }
            }
        }
    }

    public boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int[] location = new int[2];
        mShareVoice.getLocationInWindow(location);
        int posY = location[1];
        int posX = location[0];
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (mPlayDel.getVisibility() == View.VISIBLE) {
                    //mEffectLocation = false;
                    break;
                }
                if (ev.getY() >= posY && ev.getY() <= posY + mShareVoice.getHeight()
                        && ev.getX() > posX && ev.getX() < posX + mShareVoice.getWidth()) {
                    return onTouchEvent(ev);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!Environment.getExternalStorageDirectory().exists()) {
            ToastUtils.show("No SDCard", Toast.LENGTH_LONG);
            return false;
        }

        int[] location = new int[2];
        mShareVoice.getLocationInWindow(location);
        int posY = location[1];
        int posX = location[0];
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPlayDel.getVisibility() == View.VISIBLE) {
                    mEffectLocation = false;
                    break;
                    //return false;
                }
                if (event.getY() >= posY && event.getY() <= posY + mShareVoice.getHeight()
                        && event.getX() > posX && event.getX() < posX + mShareVoice.getWidth()) {
                    mEffectLocation = true;
                    mShareVoice.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
                    mStartVoiceT = System.currentTimeMillis();
                    mVoiceDes.setText(R.string.cancel_moveup);
                    mVoicePop.setVisibility(View.VISIBLE);
                    mVoiceVolum.setVisibility(View.VISIBLE);
                    mVoiceIcon.setVisibility(View.GONE);
                    mVoiceName = mStartVoiceT + ".amr";
                    start(mVoiceName);
                } else {
                    mEffectLocation = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() < posY - 50) {
                    mVoiceVolum.setVisibility(View.GONE);
                    mVoiceIcon.setVisibility(View.VISIBLE);
                    mVoiceIcon.setImageResource(R.drawable.rcd_cancel_icon);
                    mVoiceDes.setText(R.string.moveup_del);
                } else {
                    mVoiceVolum.setVisibility(View.VISIBLE);
                    mVoiceIcon.setVisibility(View.GONE);
                    mVoiceDes.setText(R.string.cancel_moveup);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                mEndVoiceT = System.currentTimeMillis();
                if (event.getY() < posY - 50) {
                    File file = new File(AppConfig.audioPath + mVoiceName);
                    if (file.exists()) {
                        file.delete();
                    }

                    //sendThreadHandlerMessage(ShareHandler.STOP_REOCRD);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mShareVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                            mVoicePop.setVisibility(View.GONE);
                            stop();
                        }
                    }, 400);
                } else if (mEffectLocation) {
                    mTime = (int) ((mEndVoiceT - mStartVoiceT) / 1000);
                    if (1 > mTime) {
                        mVoiceDes.setText(R.string.time_too_short);
                        mVoiceVolum.setVisibility(View.GONE);
                        mVoiceIcon.setVisibility(View.VISIBLE);
                        mVoiceIcon.setImageResource(R.drawable.voice_to_short);
                        //stop();
                        File file = new File(AppConfig.audioPath + mVoiceName);
                        if (file.exists()) {
                            file.delete();
                        }

                        //sendThreadHandlerMessage(ShareHandler.STOP_REOCRD);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mShareVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                                mVoicePop.setVisibility(View.GONE);
                                stop();
                            }
                        }, 300);
                        break;
                    } else {
                        //sendThreadHandlerMessage(ShareHandler.STOP_REOCRD);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mShareVoice.setVisibility(View.GONE);
                                mPlayDel.setVisibility(View.VISIBLE);
                                mPlaySrc.setImageResource(R.mipmap.voice_play_normal);
                                mVoiceTime.setText(mTime + "'");
                                mShareVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                                mVoicePop.setVisibility(View.GONE);
                                stop();
                            }
                        }, 300);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void start(String name) {
        mSensor.start(name);
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        //mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mVoiceVolum.setImageResource(R.drawable.amp0);
        mSensor.stop();
    }

    private static final int POLL_INTERVAL = 300;

    /*private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };*/
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            case 0:
                mVoiceVolum.setImageResource(R.drawable.amp0);
            case 1:
            case 2:
                mVoiceVolum.setImageResource(R.drawable.amp1);
                break;
            case 3:
            case 4:
                mVoiceVolum.setImageResource(R.drawable.amp2);
            case 5:
            case 6:
                mVoiceVolum.setImageResource(R.drawable.amp3);
                break;
            case 7:
            case 8:
                mVoiceVolum.setImageResource(R.drawable.amp4);
                break;
            case 9:
            case 10:
                mVoiceVolum.setImageResource(R.drawable.amp5);
            case 11:
                mVoiceVolum.setImageResource(R.drawable.amp6);
                break;
            default:
                mVoiceVolum.setImageResource(R.drawable.amp7);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void hitRecording() {
        mShareVoice.setVisibility(View.VISIBLE);
        mPlayDel.setVisibility(View.GONE);
        mPlaySrc.setImageResource(R.mipmap.voice_play_normal);
        mShareVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
    }

    private void sendThreadHandlerMessage(int what) {
        mShareHandler.removeCallbacks(mHandlerThread);
        mShareHandler.sendEmptyMessage(what);
    }

    class ShareHandler extends Handler {
        public ShareHandler(Looper looper) {
            super(looper);
        }

        public static final int START_REOCRD = 0;
        public static final int PAUSE_REOCRD = 1;
        public static final int STOP_REOCRD = 2;
        public static final int START_PLAY = 3;
        public static final int PAUSE_PLAY = 4;
        public static final int GOON_PLAY = 5;
        public static final int STOP_PLAY = 6;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_REOCRD:
                    start(mVoiceName);
                    break;
                case PAUSE_REOCRD:
                    stop();
                    break;
                case STOP_REOCRD:
                    break;
                case START_PLAY:
                    playVoice(AppConfig.audioPath + mVoiceName);
                    break;
                case PAUSE_PLAY:
                    break;
                case GOON_PLAY:
                    break;
                case STOP_PLAY:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShareHandler != null) {
            mShareHandler.getLooper().quit();
        }
    }
}
