package com.sky_wf.chinachat.chat.listener;

import com.hyphenate.exceptions.HyphenateException;

/**
 * @Date : 2018/6/4
 * @Author : WF
 * @Description :
 */
public interface LoginRegStateListener
{
    void onSuccess();

    void onFailed(Exception e);
}
