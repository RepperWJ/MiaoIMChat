package com.sky_wf.chinachat.chat.manager;

import android.content.Context;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.sky_wf.chinachat.chat.entity.LoginExceptionHandle;
import com.sky_wf.chinachat.chat.entity.SmsLoginException;
import com.sky_wf.chinachat.chat.entity.User;
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.listener.SendCodeListener;
import com.sky_wf.chinachat.utils.BuildConfig;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.SharedUtils;
import com.sky_wf.chinachat.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
    private CallBakcListener listener;
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

    public void setCallBackListener(CallBakcListener listener)
    {
        this.listener = listener;
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
        BmobSMS.verifySmsCode(phone, code, new UpdateListener()
        {
            @Override
            public void done(BmobException e)
            {
                if (null != e)
                {
                    Debugger.d(TAG, "<<verifySmsCode>>---send code success");
                    User user = new User();
                    user.setUsername(phone);
                    user.setPassword(pwd);
                    user.signUp(new SaveListener<String>()
                    {
                        @Override
                        public void done(String s, BmobException e)
                        {
                            if (e == null || e.getErrorCode() == 9015)
                            {
                                Debugger.d(TAG, "Bmob create acount sucess>>" + s);
                                Observable.create(new Observable.OnSubscribe<String>()
                                {
                                    @Override
                                    public void call(final Subscriber<? super String> subscriber)
                                    {

                                        try
                                        {
                                            EMClient.getInstance().createAccount(phone, pwd);
                                            subscriber.onNext("REGISTER SUCESS");
                                        } catch (HyphenateException e)
                                        {
                                            e.printStackTrace();
                                            listener.onFailed(e);
                                            // subscriber.onError(e);
                                        }
                                    }
                                }).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
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
                                                SharedUtils.getInstance(context).putString("phone",
                                                        phone);
                                                SharedUtils.getInstance(context).putString("pwd",
                                                        pwd);
                                            }
                                        });
                            } else
                            {
                                listener.onFailed(e);
                                Debugger.d(TAG, "Bmob create acount failed>>" + e.getErrorCode()
                                        + "-----" + e.getMessage());
                            }
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

            BmobSMS.requestSMSCode(phone, BuildConfig.SMS_template, new QueryListener<Integer>()
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

    // 登录Bmob后台
    public void login(final Context context, final String phone, final String pwd)
    {
        User user = new User();
        user.setUsername(phone);
        user.setPassword(pwd);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(null==e)
                {
                    Debugger.d(TAG, "登录Bmob后台服务器成功！");
                    loginChatServer(context,phone,pwd);
                }else {
                    Debugger.d(TAG, "登录Bmob后台服务器失败！"+e.getMessage()+e.getErrorCode());
                    listener.onFailed(e);
                }
            }
        });

    }

    /**登录环信服务器
     * @param context
     * @param phone
     * @param pwd
     */
    private void loginChatServer(final Context context, final String phone, final String pwd)
    {
        EMClient.getInstance().login(phone, pwd, new EMCallBack()
        {// 回调
            @Override
            public void onSuccess()
            {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Debugger.d(TAG, "登录环信聊天服务器成功！");
                listener.onSuccess();
                SharedUtils.getInstance(context).putString("phone", phone);
                SharedUtils.getInstance(context).putString("pwd", pwd);
            }

            @Override
            public void onProgress(int progress, String status)
            {

            }

            @Override
            public void onError(int code, String message)
            {
                Debugger.d(TAG, "登录环信聊天服务器失败！" + code + ">>" + message);
                listener.onFailed(new SmsLoginException(code, message));
            }
        });
    }

    /**
     * 获取用户最近所有会话（包括单聊和群聊）
     * 
     * @return
     */
    public List<EMConversation> loadConversationWithRecentChat()
    {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager()
                .getAllConversations();
        List<EMConversation> list = new ArrayList<>();
        for (EMConversation conversation : conversations.values())
        {
            if (conversation.getAllMessages().size() != 0)
            {
                list.add(conversation);
            }
        }
        Debugger.d(TAG, "<<loadConversationWithRecentChat---当前用户的会话总数>>" + list.size());
        // 按照时间对会话进行排序
        sortConversationByLastChatTime(list);
        Debugger.d(TAG, "<<loadConversationWithRecentChat---排序后当前用户的会话总数>>" + list.size());
        return list;

    }

    private void sortConversationByLastChatTime(List<EMConversation> list)
    {
        Collections.sort(list, new Comparator<EMConversation>()
        {
            @Override
            public int compare(EMConversation o1, EMConversation o2)
            {
                EMMessage o1LastMsg = o1.getLastMessage();
                EMMessage o2LastMsg = o2.getLastMessage();
                if (o2LastMsg.getMsgTime() == o1LastMsg.getMsgTime())
                {
                    return 0;
                } else if (o2LastMsg.getMsgTime() > o1LastMsg.getMsgTime())
                {
                    return 1;
                } else
                {
                    return -1;
                }
            }
        });
    }

    /**更新用户昵称
     * @param nickname
     */
    public void updateNickName(String nickname)
    {
        if(null!=getCurrentUser())
        {
            User user = getCurrentUser();
            user.setNickName(nickname);
            user.update(new UpdateListener() {
                @Override
                public void done(BmobException  e) {
                    if(null==e)
                    {
                        listener.onSuccess();
                       Debugger.d(TAG,"updateNickName  success");
                    } else {
                        listener.onFailed(e);
                        Debugger.d(TAG,"updateNickName failed>>"+e.getErrorCode()+"--"+e.getMessage());
                    }
                }
            });
        }

    }

    /**获取当前登录用户
     * @return
     */
    public User getCurrentUser()
    {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null)
        {
            return user;
        }
        Debugger.d(TAG,"getCurrentUse is"+user);
        return null;
    }

}
