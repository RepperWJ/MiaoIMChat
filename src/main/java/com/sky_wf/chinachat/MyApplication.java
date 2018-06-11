package com.sky_wf.chinachat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.sky_wf.chinachat.net.api.API;
import com.sky_wf.chinachat.utils.BuildConfig;
import com.sky_wf.chinachat.utils.Debugger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;


/**
 * @Date : 2018/6/1
 * @Author : WF
 * @Description :App
 */
public class MyApplication extends Application
{
    public static Context context;
    public static Resources res;
    private final String TAG = "MyApplication";
    private static List<Activity> activityList;

    @Override
    public void onCreate()
    {
        Debugger.d(TAG, "<<--MyApplication-->>onCreate()");
        super.onCreate();
        context = this;
        res = context.getResources();
        activityList = new ArrayList<>();
        API.getInstance().init(this);
        initEaseMob();
        initBmobSms();

    }

    /**
     * 初始化环信SDK
     */
    private void initEaseMob()
    {
        int pid = android.os.Process.myPid();
        String processName = getAppName(pid);
        Debugger.d(TAG,
                "<<--MyApplication-->> ProcessName--" + pid + "--ProcesPid--" + processName);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (null == processName || !processName.equalsIgnoreCase(context.getPackageName()))
        {
            return;
        }
        EMOptions options = new EMOptions();
        // 设置加好友时需要验证，默认为不需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 设置自动下载附件类消息的缩略图，默认为true，这里需要设置options.setAutoTransferMessageAttachments(true);才起作用
        options.setAutoDownloadThumbnail(true);
        EMClient.getInstance().init(context, options);
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 初始化BmobSDK
     */
    private void initBmobSms()
    {
        Bmob.initialize(this, BuildConfig.App_ID);
    }

    @Override
    public void onLowMemory()
    {
        Debugger.d(TAG, "<<--MyApplication-->> onLowMemory()");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level)
    {
        Debugger.d(TAG, "<<--MyApplication-->> onTrimMemory()");
        super.onTrimMemory(level);
    }

    private String getAppName(int pID)
    {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List process = am.getRunningAppProcesses();// 获取当前运行的进程
        Iterator iterable = process.iterator();
        while (iterable.hasNext())
        {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) iterable
                    .next();
            try
            {
                if (info.pid == pID)
                {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e)
            {
                Debugger.d(TAG, "<<--MyApplication-->> Exception>>" + e.toString());
            }
        }
        return processName;
    }

    public static void addAcitivtyList(Activity activity)
    {
        if (null != activity)
        {
            activityList.add(activity);

        }
    }

    public static void exitActivity()
    {
        if(null != activityList)
        for(Activity activity:activityList)
        {
            activity.finish();
        }
    }
}
