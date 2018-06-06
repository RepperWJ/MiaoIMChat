package com.sky_wf.chinachat.net.entity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.sky_wf.chinachat.net.exception.NetExceptionHandle;
import com.sky_wf.chinachat.utils.AppUtils;

import rx.Subscriber;

/**
 * @Date :  2018/6/1
 * @Author : WF
 * @Description :
 */
public class BaseSubScriber<T> extends Subscriber<T> {
    private Context context;

    public BaseSubScriber(Context context)
    {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!AppUtils.isNetWorkConnected(context))
        {

            Toast.makeText(context,"当前网络不可用，请检查网络连接情况",Toast.LENGTH_SHORT).show();
            onCompleted();
            return;
        }
        //显示进度条
    }

    @Override
    public void onCompleted() {
        //关闭进度条
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d("wftt","onError>>"+throwable.getMessage());
        NetExceptionHandle.ResponeThrowable responeThrowable = NetExceptionHandle.handleException(throwable);
        Log.d("wftt","NetExceptionHandle>>"+responeThrowable.message);


    }

    @Override
    public void onNext(T t) {

    }

}
