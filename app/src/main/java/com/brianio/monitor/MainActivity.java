package com.brianio.monitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.brianio.monitor.View.MyWindowManager;
import com.brianio.monitor.service.CoreService;


public class MainActivity extends Activity implements OnClickListener {
    //定义按键
    private Button mBtnStart, mBtnStop;
    private static final String TAG = "MainActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //按键初始化
        mBtnStart = (Button) findViewById(R.id.button_start);
        mBtnStop = (Button) findViewById(R.id.button_stop);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                Intent startIntent = new Intent(this, CoreService.class);
                startService(startIntent);
                Log.d(TAG, "startService() ");
                break;
            case R.id.button_stop:
                MyWindowManager.removeBigWindow(mContext);
                MyWindowManager.removeSmallWindow(mContext);
                Intent stopIntent=new Intent(this,CoreService.class);
                stopService(stopIntent);
                break;
            default:
                break;
        }
    }

}
