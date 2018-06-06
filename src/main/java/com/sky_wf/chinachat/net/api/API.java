package com.sky_wf.chinachat.net.api;

import android.util.Log;

import com.sky_wf.chinachat.net.RetrofitClient;

import com.sky_wf.chinachat.net.entity.BaseSubScriber;
import com.sky_wf.chinachat.net.entity.HttpResult;
import com.sky_wf.chinachat.net.entity.bean.IpBean;
import com.sky_wf.chinachat.net.entity.bean.ItemBean;
import com.sky_wf.chinachat.net.entity.bean.Pbean;
import com.sky_wf.chinachat.net.exception.APIException;

import com.sky_wf.chinachat.net.transformer.SchedularsTransformer;

import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * @Date : 2018/6/1
 * @Author : WF
 * @Description :
 */
public class API extends RetrofitClient
{
    public static API api = null;

    private API()
    {

    }

    public static API getInstance()
    {
        if (api == null)
        {
            synchronized (API.class)
            {
                if (api == null)
                {
                    api = new API();
                }
            }
        }
        return api;
    }

    public void getIp(Subscriber<IpBean> subscriber, String ip)
    {
        apiService.getIp(ip)
                .map(new HttpResultFunc<IpBean>())
                .compose(new SchedularsTransformer())
                .subscribe(subscriber);
    }

    public void getP(Subscriber<List<ItemBean>> subscriber, String p)
    {
        apiService.getItem(p)
//                .map(new HttpResultFunc<List<ItemBean>>())
                .compose(new SchedularsTransformer())
                .subscribe(subscriber);
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T>
    {
        @Override
        public T call(HttpResult<T> tHttpResult)
        {
            Log.d("wftt","HttpResultFunc>>>"+tHttpResult.getCode());
            if (!tHttpResult.isSucess())
            {
                throw new APIException(tHttpResult.getCode(), tHttpResult.getDesc());
            }
            return tHttpResult.getData();
        }
    }

}
