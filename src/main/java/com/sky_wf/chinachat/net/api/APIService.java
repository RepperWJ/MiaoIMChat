package com.sky_wf.chinachat.net.api;


import com.sky_wf.chinachat.net.entity.HttpResult;
import com.sky_wf.chinachat.net.entity.bean.IpBean;
import com.sky_wf.chinachat.net.entity.bean.ItemBean;
import com.sky_wf.chinachat.net.entity.bean.Pbean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import rx.Observable;

/**
 * @Date :  2018/5/31
 * @Author : WF
 * @Description :定义Retrofit网络接口
 */
public interface APIService {

    @GET("{url}")
    Observable<ResponseBody> getIp(@Path ("{url}") String url,@Query("ip")String ip);

    @GET("/search")
    Observable<ResponseBody> getIps(@Query("ip")String ip);

    @GET("service/getIpInfo.php")
    Observable<HttpResult<IpBean>> getIp(@Query("ip") String ip);

    @GET("search")
    Observable<List<ItemBean>> getItem(@Query("q") String p );


}
