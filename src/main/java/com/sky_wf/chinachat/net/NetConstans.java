package com.sky_wf.chinachat.net;

/**
 * @Date : 2018/6/1
 * @Author : WF
 * @Description :网络常量
 */
public class NetConstans
{
    public  static final String BASE_URL = "http://ip.taobao.com/";

//        public  static final String BASE_URL = "http://www.zhuangbi.info/";
    public  static final int READ_TIME_OUT = 20;
    public  static final int WRITE_TIME_OUT = 20;
    public  static final int CONNECTED_TIME_OUT = 15;

    //定义缓存
    public static final String CACHE_FILE_NAME = "httpCache";
    public static final int maxCacheTime = 60;
    public static final int maxCacheSize = 1024*1024*50;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 没有数据
     */
    public static final int EMPTY = -3;
    /**
     * 没有更多数据
     */
    public static final int NOMORE= -4;

    /**
     * 网络中断，请检查您的网络状态
     */
    public static final int NETERROR= -1000;
    /**
     * 未知错误
     */
    public static final int UNKONWERROR= -1001;

}
