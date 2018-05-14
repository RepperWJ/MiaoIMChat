package com.sky_wf.chinachat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.SharedUtils;

/**
 * @Date : 2018/5/7
 * @Author : WF
 * @Description :引导页
 */
public class SplashActivity extends BaseActivity
{
    private int runCount = 0;
    private boolean isLogin;
    private Context mContext;
    private final String RUN_COUNT = "RUN_COUNT";
    private final int MSG_LOGIN = 0;
    private final int MSG_MAIN = 1;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_LOGIN:
                    startTargetActivity(new LoginActivity());
                    overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                    finish();
                    break;
                case MSG_MAIN:
                    startTargetActivity(new MainActivity());
                    overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mContext = this;
        runCount = SharedUtils
                .getInstance(mContext)
                .getInt(RUN_COUNT);
        if (runCount == 0)
        {
            // 处理引导逻辑

        } else
        {
            SharedUtils.getInstance(mContext).putInt(RUN_COUNT, ++runCount);
        }

        isLogin = SharedUtils
                .getInstance(mContext)
                .getBoolean(Constansts.LoginState);
        if (isLogin)
        {
            handleLogin();
        } else
        {
            mHandler.sendEmptyMessageDelayed(MSG_LOGIN,5000);
        }
    }

    private void handleLogin()
    {
        String name = SharedUtils
                .getInstance(mContext)
                .getString(Constansts.User_ID);
        String pwd = SharedUtils
                .getInstance(mContext)
                .getString(Constansts.Pwd);
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd))
        {
            //这里仍需处理我们自己的服务器登录问题
            LoginChatSever(name, pwd);
        } else
        {
            SharedUtils
                    .getInstance(mContext)
                    .remove(Constansts.LoginState);
            mHandler.sendEmptyMessageDelayed(MSG_LOGIN, 300);
        }
    }

    /**
     * 登录环信SDK服务器
     */
    private void LoginChatSever(String name, String pwd)
    {

    }
}
