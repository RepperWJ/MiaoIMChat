package com.sky_wf.chinachat.chat.entity;

import android.view.View;

import com.hyphenate.EMError;
import com.hyphenate.exceptions.HyphenateException;
import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.Utils;

import javax.security.auth.login.LoginException;

import cn.bmob.v3.exception.BmobException;

/**
 * @Date : 2018/6/4
 * @Author : WF
 * @Description :登录注册处理类
 */
public class LoginExceptionHandle
{

    /*
    **注册
     */
    //环信平台
    // 用户已经存在
    public static final int USER_EXISTS = 203;
    // 登录
    // 用户名不存在
    public static final int USER_NOT_EXIST = 204;
    // 用户账号或者密码错误
    public static final int INPUT_ERROR = 202;

    public static final int USER_ALREADY_ONLINE = 200;

    /*
    **Bmob后台
     */
    //用户已经够存在
    public static final int BMOB_USER_EXISTS = 202;

    public static final int BMOB_PWD_OR_PHONE_ERROR = 101;




    // 处理注册错误
    public static void handleErrorMsg(View view, Exception e)
    {
        int errorCode = 0;
        if (e instanceof HyphenateException)
        {
            HyphenateException exception = (HyphenateException) e;
            errorCode = exception.getErrorCode();
        } else if (e instanceof SmsLoginException)
        {
            SmsLoginException exception = (SmsLoginException) e;
            errorCode = exception.getErrorCode();
        }else if(e instanceof BmobException)
        {
            handleBmobError(view, (BmobException) e);
            Debugger.d("------------------------"+((BmobException) e).getErrorCode());
        }

        switch (errorCode)
        {
            case USER_EXISTS:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_user_exist));
                break;
            case USER_NOT_EXIST:
                Utils.showLongToast(view,
                        MyApplication.res.getString(R.string.error_user_not_exist));
                break;
            case INPUT_ERROR:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_login_info));
                break;

            case EMError.USER_REMOVED:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_account_removed));
                break;
            case EMError.USER_LOGIN_TOO_MANY_DEVICES:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_login_too_many_device));
                break;
            case EMError.USER_ALREADY_LOGIN:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_login_aleardy_login));
                break;

        }

    }

    private static void handleBmobError(View view,BmobException e)
    {
        switch (e.getErrorCode())
        {
            case BMOB_USER_EXISTS:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_user_exist));
                break;
            case BMOB_PWD_OR_PHONE_ERROR:
                Utils.showLongToast(view, MyApplication.res.getString(R.string.error_login_info));
                break;
        }
    }
}
