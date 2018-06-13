package com.sky_wf.chinachat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date : 2018/6/4
 * @Author : WF
 * @Description :
 */
public class Utils
{

    /**
     * 验证手机号
     * 
     * @param mobile
     * @return
     */
    public static boolean isMobileNomber(String mobile)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static void showLongToast(View view, String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShortToast(View view, String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str);
        if (match.matches() == false)
        {
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * 判断是否有网络
     * 
     * @param context
     * @return
     */
    public static boolean isNetAvaliable(Context context)
    {
        if (context.checkCallingOrSelfPermission(
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        } else
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == connectivityManager)
            {
                return false;
            }
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null == info || !info.isAvailable())
            {
                return false;
            }

        }
        return true;
    }

    /**
     * 获取复制内容
     * 
     * @return
     */
    public static String getCopyText()
    {
        // Android3.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            ClipboardManager clipboardManager = (ClipboardManager) MyApplication.context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (null == clipboardManager || !clipboardManager.hasPrimaryClip())
            {
                return "";
            } else
            {
                ClipData clipData = clipboardManager.getPrimaryClip();
                if (clipData == null || clipData.getItemCount() <= 0)
                {
                    return "";
                }
                return clipData.getItemAt(0).getText().toString();
            }

        } else
        {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) MyApplication.context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if(null == clipboardManager || !clipboardManager.hasText())
            {
                return "";
            }
            return clipboardManager.getText().toString();
        }

    }

    public static void finish(Activity activity)
    {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
    }



}
