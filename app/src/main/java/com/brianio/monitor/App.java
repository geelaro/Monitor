package com.brianio.monitor;

import android.app.Application;
import android.content.Context;

public class App extends Application{
    public static Context mcContext;
    public static Context getAppContent(){
	return mcContext;
    }
    @Override
    public void onCreate(){
	super.onCreate();
	mcContext=this;
    }
}
