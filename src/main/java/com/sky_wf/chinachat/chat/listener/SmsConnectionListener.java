package com.sky_wf.chinachat.chat.listener;


import com.hyphenate.EMConnectionListener;
import com.sky_wf.chinachat.chat.entity.LoginExceptionHandle;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Date :  2018/6/5
 * @Author : WF
 * @Description :
 */
public class SmsConnectionListener implements EMConnectionListener {
    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(int errorCode) {
        Observable.empty().subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        });
    }
}
