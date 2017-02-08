package com.brianio.monitor;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

@SuppressLint("Registered")
public class App extends Application {
    public static Context mcContext;

    public static Context getAppContent() {
        return mcContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mcContext = this;
    }
}
