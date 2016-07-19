package com.brianio.monitor.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.brianio.monitor.R;
import com.brianio.monitor.service.CoreService;


/**
 * Created by Administrator on 2016/7/15.
 */
public class FloatWindowBigView extends LinearLayout {
    //BigFloatWindow's Width
    public static int viewWidth;
    //BigFloatWindow's Height
    public static int viewHeight;
    //Button
    private Button close;
    private Button back;

    public FloatWindowBigView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_big,this);
        View view=findViewById(R.id.big_float_window);
        viewWidth=view.getLayoutParams().width;
        viewHeight=view.getLayoutParams().height;

        close=(Button)findViewById(R.id.close_float);
        back=(Button)findViewById(R.id.back);

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭时，关闭悬浮窗并停止service
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.removeSmallWindow(context);
                Intent intent=new Intent(getContext(), CoreService.class);
                context.stopService(intent);

            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击返回时，移除大悬浮窗，创建小悬浮窗
                MyWindowManager.removeBigWindow(context);
                MyWindowManager.createSmallWindow(context);
            }
        });
    }
}

