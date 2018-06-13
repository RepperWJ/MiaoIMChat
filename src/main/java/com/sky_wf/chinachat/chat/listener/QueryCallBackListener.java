package com.sky_wf.chinachat.chat.listener;

/**
 * @Date : 2018/6/12
 * @Author : WF
 * @Description :Query info
 */
public interface QueryCallBackListener<T>
{
    void onSucess(T t);

    void onFailed();
}
