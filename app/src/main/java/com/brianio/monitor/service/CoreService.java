package com.brianio.monitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.brianio.monitor.View.MyWindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class CoreService extends Service {
    private final static String TAG = "CoreService";

    private Timer mTimer;

    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new RefreshTask(), 0, 500);//ms
            Log.d(TAG, "onStartCommand: ");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;

        Log.d(TAG, "onDestroy()");
    }

    //
    class RefreshTask extends TimerTask {
        @Override
        public void run() {
            if (!MyWindowManager.isWindowShowing()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                        Log.d(TAG, "run: ");

                    }
                });
            } else if (MyWindowManager.isWindowShowing()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.updateUsedMemory(getApplicationContext());
                    }
                });
            }
        }
    }
}
