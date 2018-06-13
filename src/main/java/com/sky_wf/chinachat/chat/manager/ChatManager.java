package com.sky_wf.chinachat.chat.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.sky_wf.chinachat.MyApplication;
import com.sky_wf.chinachat.R;
import com.sky_wf.chinachat.activity.ChatActivity;
import com.sky_wf.chinachat.chat.entity.SmsLoginException;
import com.sky_wf.chinachat.chat.entity.User;
import com.sky_wf.chinachat.chat.entity.UserGroupInfo;
import com.sky_wf.chinachat.chat.listener.CallBakcListener;
import com.sky_wf.chinachat.chat.listener.QueryCallBackListener;
import com.sky_wf.chinachat.chat.listener.SendCodeListener;
import com.sky_wf.chinachat.chat.utils.ChatConstants;
import com.sky_wf.chinachat.utils.BuildConfig;
import com.sky_wf.chinachat.utils.Constansts;
import com.sky_wf.chinachat.utils.Debugger;
import com.sky_wf.chinachat.utils.NetUtils;
import com.sky_wf.chinachat.utils.SharedUtils;
import com.sky_wf.chinachat.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    private QueryCallBackListener<User> queryUserListener;

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

    public void setQueryUserListener(QueryCallBackListener<User> queryUserListener)
    {
        this.queryUserListener = queryUserListener;
    }

    /**
     * Register
     * 
     * @param context
     * @param view
     * @param phone
     * @param pwd
     * @param code
     */
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
                                                SharedUtils.getInstance(context)
                                                        .putString(Constansts.PHONE, phone);
                                                SharedUtils.getInstance(context)
                                                        .putString(Constansts.PWD, pwd);
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

    /**
     * Send SMS code
     * 
     * @param context
     * @param phone
     */
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

    /**
     * Login
     * 
     * @param context
     * @param phone
     * @param pwd
     */
    public void login(final Context context, final String phone, final String pwd)
    {
        User user = new User();
        user.setUsername(phone);
        user.setPassword(pwd);
        user.login(new SaveListener<User>()
        {
            @Override
            public void done(User user, BmobException e)
            {
                if (null == e)
                {
                    Debugger.d(TAG, "登录Bmob后台服务器成功！");
                    loginChatServer(context, phone, pwd);
                } else
                {
                    Debugger.d(TAG, "登录Bmob后台服务器失败！" + e.getMessage() + e.getErrorCode());
                    listener.onFailed(e);
                }
            }
        });

    }

    /**
     * Login 环信Server
     * 
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
                SharedUtils.getInstance(context).putString(Constansts.PHONE, phone);
                SharedUtils.getInstance(context).putString(Constansts.PWD, pwd);
                SharedUtils.getInstance(context).putBoolean(Constansts.LoginState, true);
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
     * load recent Conversation 获取用户最近所有会话（包括单聊和群聊）
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

    /**
     * Sort Conversations by time 会话列表排序
     * 
     * @param list
     */
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

    /**
     * update users info 更新用户昵称
     * 
     * @param nickname
     */
    public void updateNickName(String nickname)
    {
        if (null != getCurrentUser())
        {
            User user = getCurrentUser();
            user.setNickName(nickname);
            user.update(new UpdateListener()
            {
                @Override
                public void done(BmobException e)
                {
                    if (null == e)
                    {
                        listener.onSuccess();
                        Debugger.d(TAG, "updateNickName  success");
                    } else
                    {
                        listener.onFailed(e);
                        Debugger.d(TAG, "updateNickName failed>>" + e.getErrorCode() + "--"
                                + e.getMessage());
                    }
                }
            });
        }

    }

    /**
     * get current user 获取当前登录用户
     * 
     * @return
     */
    public User getCurrentUser()
    {
        User user = BmobUser.getCurrentUser(User.class);
        if (user != null)
        {
            return user;
        }
        Debugger.d(TAG, "getCurrentUse is" + user);
        return null;
    }

    /**
     * 网络异常
     * 
     * @param context
     */
    public void openSetNetWork(Context context)
    {
        if (null != context)
        {
            NetUtils.openSetNetWork(context);
        }
        Debugger.d(TAG, "<<openSetNetWork>>" + context);
    }

    /**
     * query single user info 查询单个用户信息
     * 
     * @param username
     * @return
     */
    public void QueryUser(String username)
    {

        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<User>()
        {
            @Override
            public void done(List<User> list, BmobException e)
            {
                if (null == e)
                {
                    if (list.size() > 0)
                    {
                        Observable.from(list).take(1).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<User>()
                                {
                                    @Override
                                    public void onCompleted()
                                    {

                                    }

                                    @Override
                                    public void onError(Throwable throwable)
                                    {
                                        queryUserListener.onFailed();
                                    }

                                    @Override
                                    public void onNext(User user)
                                    {
                                        queryUserListener.onSucess(user);
                                        Debugger.d("wftt", "queryUser[0]>>>>" + user + "nickname"
                                                + user.getNickName());
                                    }
                                });
                    }
                } else
                {
                    Debugger.d(TAG,
                            "QueryUser failed>>" + e.getErrorCode() + "--" + e.getMessage());
                }
            }
        });

    }

    /**
     * 获取当前某一会话的未读消息总数
     * 
     * @param conversation
     * @return
     */
    public int getUnReadMsgCount(EMConversation conversation)
    {
        if (null != conversation)
        {
            return conversation.getUnreadMsgCount();
        }
        return 0;
    }

    /**
     * 获取当前会话的消息总数
     * 
     * @param conversation
     * @return
     */
    public int getMsgCount(EMConversation conversation)
    {
        if (null != conversation)
        {
            return conversation.getAllMsgCount();
        }
        return 0;
    }

    /**
     * 获取最近的消息
     * 
     * @param message
     * @param context
     * @return
     */
    public String getLastMessageDigest(EMMessage message, Context context)
    {
        String digest = "";
        switch (message.getType())
        {
            case LOCATION:
                break;
            case IMAGE:
                digest = MyApplication.res.getString(R.string.picture);
                break;
            case TXT:
                if (!message.getBooleanAttribute(Constansts.MESSAGE_ATTR_IS_VOICE_CALL, false))
                {
                    EMTextMessageBody textMessageBody = (EMTextMessageBody) message.getBody();
                    digest = textMessageBody.getMessage();
                }
                break;
            case VIDEO:
                digest = MyApplication.res.getString(R.string.video);
                break;
            case VOICE:
                digest = MyApplication.res.getString(R.string.voice);
                break;
            case CMD:
                break;
            case FILE:
                digest = MyApplication.res.getString(R.string.file);
                break;
            default:
                return "";

        }
        return digest;
    }

    public void updateConversationInfo(List<EMConversation> list)
    {
        for (EMConversation conversation : list)
        {
            if (conversation.isGroup())
            {
                if (!isqueryByID(new UserGroupInfo(), conversation.conversationId()))
                {

                }
            } else
            {
                if (!isqueryByID(new User(), conversation.conversationId()))
                {
                    // 用户信息不存在的情况可以忽略
                }

            }
        }
    }

    private <T> boolean isqueryByID(T t, String usename)
    {
        final boolean[] isQuery = { false };
        BmobQuery<T> query = new BmobQuery<>();
        query.getObject(usename, new QueryListener<T>()
        {
            @Override
            public void done(T user, BmobException e)
            {
                if (e == null)
                {
                    isQuery[0] = true;
                }
            }
        });
        return isQuery[0];
    }

    private void updateUserInfo(EMConversation conversation)
    {

    }

    public void gtChat(Context context, EMConversation conversation)
    {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constansts.User_ID, conversation.conversationId());
        if (conversation.isGroup())
        {
            EMGroup group = EMClient.getInstance().groupManager()
                    .getGroup(conversation.conversationId());
            intent.putExtra(Constansts.TYPE, ChatConstants.CHAT_GROUP);
            intent.putExtra(Constansts.USERNAME, group.getGroupName());
            intent.putExtra(Constansts.GROUP_NUMBER, group.getMemberCount());
        } else
        {

            intent.putExtra(Constansts.TYPE, ChatConstants.CHAT_SINGLE);
            Log.d("wftt", "usemap -->>" + MyApplication.userMap.get(conversation.conversationId()));
            Log.d("wftt", "usemap getNickName -->>"
                    + MyApplication.userMap.get(conversation.conversationId()).getNickName());
            if (MyApplication.userMap.get(conversation.conversationId()) != null)
            {
                intent.putExtra(Constansts.USERNAME,
                        MyApplication.userMap.get(conversation.conversationId()).getNickName());
            }
        }
        context.startActivity(intent);
    }

}
