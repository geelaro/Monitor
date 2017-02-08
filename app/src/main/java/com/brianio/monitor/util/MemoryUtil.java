package com.brianio.monitor.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;
import android.os.Debug.MemoryInfo;

import com.brianio.monitor.App;
import com.brianio.monitor.Constants;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryUtil {
    /**
     * getTotalPss,5.0及以上系统使用开源com.jaredrummler.android_process，5.0以下使用系统API
     *
     * @param processNameList
     * @return
     */
    public static HashMap<String, Long> getTotalPss(ArrayList<String> processNameList) {
        //
        HashMap<String, Long> resultMap = new HashMap<String, Long>();
        ActivityManager ActivityMag = (ActivityManager) App.getAppContent().getSystemService(Context.ACTIVITY_SERVICE);
        // 运行的进程

        if (Build.VERSION.SDK_INT >= 21) {
            List<AndroidAppProcess> list = com.jaredrummler.android.processes.AndroidProcesses.getRunningAppProcesses();
            if (list != null) {
                for (AndroidAppProcess processInfo : list) {
                    if (processNameList.contains(processInfo.name)) {
                        int pid = processInfo.pid;
                        MemoryInfo[] memoryInfos = ActivityMag.getProcessMemoryInfo(new int[]{pid});

                        int totalPss = memoryInfos[0].getTotalPss();
                        resultMap.put(processInfo.name, new Long(totalPss));
                    }
                }
            }

        } else {
            List<RunningAppProcessInfo> list = ActivityMag.getRunningAppProcesses();
            if (list != null) {
                for (RunningAppProcessInfo processInfo : list) {
                    if (Constants.PROCESS_NAME_LIST.contains(processInfo.processName)) {
                        int pid = processInfo.pid;
                        MemoryInfo[] meminfos = ActivityMag.getProcessMemoryInfo(new int[]{pid});

                        int totalPss = meminfos[0].getTotalPss();
                        resultMap.put(processInfo.processName, new Long(totalPss));
                    }
                }
            }
        }
        return resultMap;
    }

    public static long getTotalPss(String processName) {
        ActivityManager activityMag = (ActivityManager) App.getAppContent().getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 21) {
            List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses();
            if (list != null) {
                for (AndroidAppProcess processInfo : list) {
                    if (processInfo.name.equals(processName)) {
                        int pid = processInfo.pid;

                        MemoryInfo[] memoryInfos = activityMag.getProcessMemoryInfo(new int[]{pid});
                        int totalPss = memoryInfos[0].getTotalPss();
                        return totalPss;
                    }
                }
            }
        } else {
            List<RunningAppProcessInfo> list = activityMag.getRunningAppProcesses();
            if (list != null) {
                for (RunningAppProcessInfo processInfo : list) {
                    if (processInfo.processName.equals(processName)) {
                        int pid = processInfo.pid;

                        MemoryInfo[] memoryInfos = activityMag.getProcessMemoryInfo(new int[]{pid});
                        int totalPss = memoryInfos[0].getTotalPss();
                        return totalPss;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * 可用内存
     */
    public static long getAvailableMemory() {
        ActivityManager activityMag = (ActivityManager) App.getAppContent().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();
        activityMag.getMemoryInfo(mInfo);

        return mInfo.availMem;
    }
}
