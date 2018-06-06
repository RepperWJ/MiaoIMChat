package com.sky_wf.chinachat.net;

import android.content.Context;

import com.sky_wf.chinachat.BuildConfig;
import com.sky_wf.chinachat.net.api.APIService;
import com.sky_wf.chinachat.net.interceptor.HttpCacheInterceptor;
import com.sky_wf.chinachat.net.interceptor.HttpLoggingInterceptor;
import com.sky_wf.chinachat.utils.AppUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Date : 2018/5/31
 * @Author : WF
 * @Description :Retrofit工具类
 */
public class RetrofitClient
{
    public APIService apiService;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private Context mContext;
    private HttpCacheInterceptor cacheInterceptor;

    public void init(Context context)
    {
        mContext = context;
        initInterceptor();
        initOkhttp();
        initRetrofit();
        if(apiService==null)
        {
            apiService = retrofit.create(APIService.class);
        }
 
    }

    private void initInterceptor()
    {
        cacheInterceptor = new HttpCacheInterceptor(mContext);
        cacheInterceptor.setUseCache(true);
    }

    private void initOkhttp()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG)
        {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        File cacheFile = new File(AppUtils.getCacheDir(mContext), NetConstans.CACHE_FILE_NAME);
        Cache cache = new Cache(cacheFile, NetConstans.maxCacheSize);

//        builder.cache(cache);
//        builder.addInterceptor(new CacheInterceptor());
//        builder.addNetworkInterceptor(new CacheInterceptor(mContext));
        builder.connectTimeout(NetConstans.CONNECTED_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(NetConstans.READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(NetConstans.WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);//设置失败重连
        okHttpClient = builder.build();

    }

    private void initRetrofit()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(NetConstans.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
