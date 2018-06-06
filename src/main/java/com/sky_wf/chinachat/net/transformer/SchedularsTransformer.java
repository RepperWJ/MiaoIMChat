package com.sky_wf.chinachat.net.transformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Date : 2018/6/1
 * @Author : WF
 * @Description :定义线程调度器
 */
public class SchedularsTransformer implements Observable.Transformer
{
    @Override
    public Object call(Object o)
    {
        return ((Observable) o)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
