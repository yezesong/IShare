package com.magcomm.sharelibrary.utils;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

import com.magcomm.sharelibrary.config.AppConfig;

public class SoundMeter implements MediaRecorder.OnErrorListener {
    static final private double EMA_FILTER = 0.6;

    private MediaRecorder mRecorder = null;
    private double mEMA = 0.0;

    public interface IHintRecoding {
        void hitRecording();
    }

    private IHintRecoding mIHintRecoding;

    public void setIHintRecoding(IHintRecoding hintRecoding) {
        mIHintRecoding = hintRecoding;
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        Debug.log(" onError is called and what = " + what + " and extra = " + extra);
        try {
            if (mr != null)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
            stop();
            if (mIHintRecoding != null) {
                mIHintRecoding.hitRecording();
            }
        }
        return;
    }

    private static class LazyHolder {
        private static final SoundMeter INSTANCE = new SoundMeter();
    }

    private SoundMeter() {
    }

    public static final SoundMeter getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void start(String name) {

        if (!Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }

        try {
            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
                mRecorder.setOnErrorListener(this);
            } else {
                mRecorder.reset();
            }
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //mRecorder.setOutputFile(android.os.Environment.getExternalStorageDirectory() + "/" + name);
            mRecorder.setOutputFile(AppConfig.audioPath + name);
            mRecorder.prepare();
            mRecorder.start();
            mEMA = 0.0;
        } catch (IllegalStateException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Handle Exception when call the function of MediaRecorder
     */
    private void handleException(boolean isDeleteSample, Exception exception) {
        Debug.log("<handleException> the exception is: " + exception);
        exception.printStackTrace();
        if (mRecorder != null) {
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public void stop() {
        try {
            if (mRecorder != null)
                mRecorder.stop();
        } catch (RuntimeException exception) {
            /** modified for stop recording failed. */
            handleException(false, exception);
            //mListener.onError(this, ErrorHandle.ERROR_RECORDING_FAILED);
            Debug.log("<stopRecording> recorder illegalstate exception in recorder.stop()");
        }
        if (null != mRecorder) {
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
        }

        /*if (mRecorder != null) {
            mRecorder.setOnErrorListener(null);
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void pause() {
        if (mRecorder != null) {
            mRecorder.stop();
        }
    }

    public void start() {
        if (mRecorder != null) {
            mRecorder.start();
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return (mRecorder.getMaxAmplitude() / 2700.0);
        else
            return 0;

    }

    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}
