package com.sky_wf.chinachat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.base.BaseActivity;
import com.sky_wf.chinachat.chat.entity.LoginExceptionHandle;
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.manager.ChatManager;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.SharedUtils;
import com.sky_wf.chinachat.utils.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * @Date : 2018/5/7
 * @Author : WF
 * @Description :Splash
 */
public class SplashActivity extends BaseActivity implements CallBakcListener
{
    @BindView(R.id.img_start)
    ImageView imgStart;
    private int runCount = 0;
    private boolean isLogin;
    private Context mContext;
    private final int MSG_LOGIN = 0;
    private final int MSG_MAIN = 1;
    private final String TAG = "SplashActivity";
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_LOGIN:
                    gtLogin();
                    break;
                case MSG_MAIN:
                    gtChat();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Debugger.d(TAG, ">>onCreate<<");
        mContext = this;
        ChatManager.getInstance().setCallBackListener(this);
        runCount = SharedUtils.getInstance(mContext).getInt(Constansts.RUN_COUNT);
        if (runCount == 0)
        {
            // 处理引导逻辑

        } else
        {
            SharedUtils.getInstance(mContext).putInt(Constansts.RUN_COUNT, ++runCount);
        }

        isLogin = SharedUtils.getInstance(mContext).getBoolean(Constansts.LoginState);
        if (isLogin)
        {
            handleLogin();
        } else
        {
            mHandler.sendEmptyMessageDelayed(MSG_LOGIN, 300);
        }
    }

    @Override
    protected void initTitle()
    {

    }

    @Override
    protected void setListener()
    {

    }

    private void handleLogin()
    {
        String phone = SharedUtils.getInstance(mContext).getString(Constansts.PHONE);
        String pwd = SharedUtils.getInstance(mContext).getString(Constansts.PWD);
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd))
        {
            if (isNetAvaliable())
            {
                ChatManager.getInstance().login(mContext, phone, pwd);
            } else
            {
                Utils.showLongToast(imgStart, getString(R.string.net_error));
            }
        } else
        {
            SharedUtils.getInstance(mContext).remove(Constansts.LoginState);
            mHandler.sendEmptyMessageDelayed(MSG_LOGIN, 300);
        }
    }

    @Override
    public void onSuccess()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Utils.showLongToast(imgStart, getString(R.string.login_sucess));
                Observable.timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>()
                {
                    @Override
                    public void call(Long aLong)
                    {
                        gtChat();
                    }
                });
            }
        });
    }

    private void gtChat()
    {
        startTargetActivity(new MainActivity());
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        finish();
    }

    private void gtLogin()
    {
        startTargetActivity(new LoginActivity());
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        finish();
    }

    @Override
    public void onFailed(final Exception e)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                LoginExceptionHandle.handleErrorMsg(imgStart, e);
                mHandler.sendEmptyMessageDelayed(MSG_LOGIN, 1000);
            }
        });
    }
}
