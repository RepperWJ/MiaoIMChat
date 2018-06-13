package com.sky_wf.chinachat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * @Date : 2018/5/31
 * @Author : WF
 * @Description :
 */
public class AppUtils
{

    /**获取缓存路径，如果SD卡存在则缓存在SD卡上，如果不存则缓存在手机内存上
     * @param context
     * @return
     */
    public static String getCacheDir(Context context)
    {
        String cacheDir;
        if (context.getExternalCacheDir() != null && isExistSDCard())
        {
            cacheDir = context.getExternalCacheDir().toString();
        } else
        {
            cacheDir = context.getCacheDir().toString();
        }
        return cacheDir;
    }

    /**判断是否存在SD卡
     * @return
     */
    public static boolean isExistSDCard()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isNetWorkConnected(Context context)
    {
        if(null!=context)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetWorkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(null!=mNetWorkInfo)
            {
                return mNetWorkInfo.isAvailable();
            }
        }
        return false;
    }


}
