package com.sky_wf.chinachat.chat.manager;

import android.content.Context;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.sky_wf.chinachat.chat.entity.SmsLoginException;
import com.sky_wf.chinachat.chat.listener.LoginRegStateListener;
import com.sky_wf.chinachat.chat.listener.SendCodeListener;
import com.sky_wf.chinachat.utils.BuildConfig;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.SharedUtils;
import com.sky_wf.chinachat.utils.Utils;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Date : 2018/6/4
 * @Author : WF
 * @Description :IM Manager
 */
public class ChatManager
{
    private static ChatManager manager = null;
    private final String TAG = "ChatManager";
    private LoginRegStateListener listener;
    private SendCodeListener codeListener;


    private ChatManager()
    {
    }

    public static ChatManager getInstance()
    {
        if (null == manager)
        {
            manager = new ChatManager();
        }
        return manager;

    }

    public void setRegisterListener(LoginRegStateListener loginRegStateListener)
    {
        this.listener = loginRegStateListener;
    }

    public void setLoginListener(LoginRegStateListener loginListener)
    {
        this.listener = loginListener;
    }

    public void setCodeListener(SendCodeListener codeListener)
    {
        this.codeListener = codeListener;
    }

    // 注册用户
    public void createACount(final Context context, final View view, final String phone,
                             final String pwd, final String code)
    {
        Debugger.d(TAG, "<<createACount>>");
        BmobSMS.verifySmsCode(context, phone, code, new VerifySMSCodeListener()
        {
            @Override
            public void done(BmobException e)
            {
                if (null == e)
                {
                    Observable.create(new Observable.OnSubscribe<String>()
                    {
                        @Override
                        public void call(Subscriber<? super String> subscriber)
                        {
                            try
                            {
                                EMClient.getInstance().createAccount(phone, pwd);

                                subscriber.onNext("REGISTER SUCESS");
                            } catch (HyphenateException e)
                            {
                                e.printStackTrace();
                                listener.onFailed(e);
                                subscriber.onError(e);
                            }
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<String>()
                            {
                                @Override
                                public void onCompleted()
                                {

                                }

                                @Override
                                public void onError(Throwable throwable)
                                {

                                }

                                @Override
                                public void onNext(String s)
                                {
                                    Debugger.d(TAG, s);
                                    Utils.showLongToast(view, "注册成功！");
                                    listener.onSuccess();
                                    SharedUtils.getInstance(context).putString("phone",phone);
                                    SharedUtils.getInstance(context).putString("pwd",pwd);
                                }
                            });
                } else
                {
                    Debugger.d(TAG, "<<verifySmsCode>>code is error" + e.getErrorCode() + "----"
                            + e.getMessage());
                    Utils.showShortToast(view, "验证码有误，请重新输入！");
                }
            }
        });

    }

    // 发送验证码
    public void sendSmsCode(Context context, String phone)
    {
        if (phone != null)
        {

            BmobSMS.requestSMSCode(context, phone, BuildConfig.SMS_template,
                    new RequestSMSCodeListener()
                    {
                        @Override
                        public void done(Integer integer, BmobException e)
                        {
                            if (null == e)
                            {
                                codeListener.onSendCodeSucess();
                            } else
                            {
                                codeListener.onSendCodeFailed();
                            }
                        }
                    });
        }
    }

    public void login(final Context context, final String phone, final String pwd)
    {
        EMClient.getInstance().login(phone, pwd, new EMCallBack()
        {// 回调
            @Override
            public void onSuccess()
            {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Debugger.d(TAG, "登录聊天服务器成功！");
                listener.onSuccess();
                SharedUtils.getInstance(context).putString("phone",phone);
                SharedUtils.getInstance(context).putString("pwd",pwd);
            }

            @Override
            public void onProgress(int progress, String status)
            {

            }

            @Override
            public void onError(int code, String message)
            {
                Debugger.d(TAG, "登录聊天服务器失败！" + code + ">>" + message);
                listener.onFailed(new SmsLoginException(code, message));
            }
        });
    }

}
