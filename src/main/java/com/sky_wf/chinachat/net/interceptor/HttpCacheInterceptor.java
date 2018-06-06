package com.sky_wf.chinachat.net.interceptor;

import android.content.Context;
import android.net.wifi.aware.PublishConfig;
import android.util.Log;

import com.sky_wf.chinachat.net.NetConstans;
import com.sky_wf.chinachat.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Date : 2018/5/31
 * @Author : WF
 * @Description :缓存拦截器
 */
public class HttpCacheInterceptor implements Interceptor
{
    private Context context;
    private boolean isUseCache = false;


    public HttpCacheInterceptor(Context context)
    {
        this.context = context;

    }


    public boolean setUseCache(boolean isUseCache)
    {
        return this.isUseCache = isUseCache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request.Builder builder = chain.request().newBuilder();

        if (!AppUtils.isNetWorkConnected(context) || isUseCache)// 使用缓存
        {
            Log.d("wftt","使用缓存");
            builder.cacheControl(FORCE_CACHE).build();

        } else if (AppUtils.isNetWorkConnected(context) && !isUseCache)// 使用网络
        {
            Log.d("wftt","不使用缓存");
            builder.cacheControl(CacheControl.FORCE_NETWORK).build();
        }

        Response response = chain.proceed(builder.build());
        if (AppUtils.isNetWorkConnected(context))
        {
//            response = response.newBuilder()
//                    .header("Cache-Control", "public,max-age=" + NetConstans.maxCacheTime)
//                    .removeHeader("Pragma").build();
//            request.re
        } else
        {
            //
//            response = response.newBuilder().build();
        }
        return response;
    }

    private CacheControl FORCE_CACHE = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(7,TimeUnit.SECONDS)
            .build();

}
