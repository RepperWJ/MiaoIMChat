package com.sky_wf.chinachat.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

/**
 * @Date :  2018/6/11
 * @Author : WF
 * @Description :Net Util
 */
public class NetUtils {
    public static void openSetNetWork(Context context)
    {
        if(Build.VERSION.SDK_INT > 10)
        {
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }else{
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
    }
}
