package com.brianio.monitor.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brianio.monitor.R;

import java.lang.reflect.Field;
/**
 * Created by Administrator on 2016/7/15.
 */
public class FloatWindowSmallView extends LinearLayout {
    private final static String TAG="SmallFloatWindow";
    //Width
    public static int viewWidth;
    //Height
    public static int viewHeight;
    //StausbarHeight
    private static int statusBarHeight;
    //用于更新小悬浮窗的位置
    private WindowManager windowManager;
    //小悬浮窗的参数
    private WindowManager.LayoutParams mParams;
    //记录当前手指在屏幕上的横坐标
    private float xInScreen;
    //
    private float yInScreen;
    //
    private float xDownInScreen;
    //
    private float yDownInScreen;
    //记录按下时在小悬浮窗的view的横坐标
    private float xInView;
    //
    private float yInView;

    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small,this);
        View view=findViewById(R.id.small_float_window);
        viewWidth=view.getLayoutParams().width;
        viewHeight=view.getLayoutParams().height;

        TextView percentView=(TextView) findViewById(R.id.percent);
        //TODO
        percentView.setText(MyWindowManager.getUsedMemory(context));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView=event.getX();
                yInView=event.getY();

                xDownInScreen=event.getRawX();
                yDownInScreen=event.getRawY()-getStatusBarHeight();

                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen=event.getRawX();
                yInScreen=event.getRawY()-getStatusBarHeight();
                //手指移动时候更新ball的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                //
                if((xDownInScreen==xInScreen)&&(yDownInScreen==yInScreen)){
                    openBigWindow();
                }
                break;
            default:
                break;
        }
        return true;
    }
    //将小悬浮球的位置传入，用于更新位置
    public void setParams(WindowManager.LayoutParams params){
        mParams=params;
    }
    //更新悬浮球的位置
    public void updateViewPosition(){
        mParams.x=(int)(xInScreen-xInView);
        mParams.y=(int)(yInScreen-yInView);
        windowManager.updateViewLayout(this,mParams);
        Log.d(TAG, "updateViewPosition() executed");
    }
    //打开大悬浮窗
    public void openBigWindow(){
        MyWindowManager.removeSmallWindow(getContext());
        MyWindowManager.createBigWindow(getContext());
    }


    //获取状态栏的高度，返回像素值
    private int getStatusBarHeight(){
        if(statusBarHeight==0){
            try{
                Class<?> c=Class.forName("com.android.internal.R$dimen");
                Object o=c.newInstance();
                Field field=c.getField("status_bar_height");
                int x=(Integer) field.get(o);
                statusBarHeight=getResources().getDimensionPixelOffset(x);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
