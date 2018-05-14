package com.sky_wf.chinachat.utils;

import android.util.Log;



/**
@Date : 2015年2月9日
@Author : Zhan Yu
@Description : 打印信息
 */
public class Debugger
{
	private final static String LOG_TAG = "ChinaChat";
    
	/**
	 * false表示开发阶段，使用Log.i/d/v/w/e()，true表示最终发布，使用SkyLogger.i/d/v/w/e()
	 */
    private final static boolean isRelease = true;
    
    private static final int LOG_LEVEL_INFO = 1;
    private static final int LOG_LEVEL_DEBUG = 2;
    private static final int LOG_LEVEL_WARNING = 3;
    private static final int LOG_LEVEL_ERROR = 4;
    private static final int LOG_LEVEL_PREVIOUS = 5;
    
    public static void i(String info)
    {
        print(LOG_LEVEL_INFO, LOG_TAG, info);
    }
    
    public static void d(String info)
    {
        print(LOG_LEVEL_DEBUG, LOG_TAG, info);
    }
    
    public static void w(String info)
    {
        print(LOG_LEVEL_WARNING, LOG_TAG, info);
    }
    
    public static void e(String info)
    {
        print(LOG_LEVEL_ERROR, LOG_TAG, info);
    }

    /**
     * @param TAG 一级标签默认为MarketShow，TAG为二级子标签
     */
    public static void i(String TAG, String info)
    {
        print(LOG_LEVEL_INFO, LOG_TAG, "<"+ TAG +">, "+ info);
    }

    /**
     * @param TAG 一级标签默认为MarketShow，TAG为二级子标签
     */
    public static void d(String TAG, String info)
    {
        print(LOG_LEVEL_DEBUG, LOG_TAG, "<"+ TAG +">, "+ info);
    }

    /**
     * @param TAG 一级标签默认为MarketShow，TAG为二级子标签
     */
    public static void w(String TAG, String info)
    {
        print(LOG_LEVEL_WARNING, LOG_TAG, "<"+ TAG +">, "+ info);
    }

    /**
     * @param TAG 一级标签默认为MarketShow，TAG为二级子标签
     */
    public static void e(String TAG, String info)
    {
        print(LOG_LEVEL_ERROR, LOG_TAG, "<"+ TAG +">, "+ info);
    }
    
    public static void p(String info)
    {
        print(LOG_LEVEL_PREVIOUS, LOG_TAG, info);
    }
   
    // 0 - dalvik.system.VMStack.getThreadStackTrace()
    // 1 - java.lang.Thread.getStackTrace()
    // 2 - Debugger.print()
    // 3 - Debugger.i/d/w/e();
    // 4 - the line call Debugger.i/d/w/e();
    private static void print(int mode, String TAG, String info)
    {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace(); 
        StringBuffer sb = new StringBuffer();
    	if(mode == LOG_LEVEL_PREVIOUS && ste.length > 5)
    	{
            sb.append(info);
            sb.append(" ||| ");
            sb.setLength(0);
            sb.append(ste[5].getClassName());
            sb.append("$");
            sb.append(ste[5].getMethodName());
            sb.append("()@");
            sb.append(ste[5].getLineNumber());
    	} else
    	{
            sb.append(info);
            sb.append(" ||| ");
            sb.append(ste[4].getClassName());
            sb.append("$");
            sb.append(ste[4].getMethodName());
            sb.append("()@");
            sb.append(ste[4].getLineNumber());
    	}
        if(isRelease)
        {
        	printOnRelease(mode, TAG, sb.toString());
        } else
        {
        	printOnDebug(mode, TAG, sb.toString());
        }
    }   
    
    private static void printOnDebug(int mode, String TAG, String info)
    {
        switch (mode)
        {
            case LOG_LEVEL_INFO:   
                Log.i(TAG, info);
                break;
            case LOG_LEVEL_DEBUG:
            	Log.d(TAG, info);
                break;
            case LOG_LEVEL_WARNING:
            	Log.w(TAG, info);
                break;
            case LOG_LEVEL_ERROR:
            	Log.e(TAG, info);
                break;
            case LOG_LEVEL_PREVIOUS:
            	Log.d(TAG, info);
            	break;
            default:
                break;
        }
    }
    
    private static void printOnRelease(int mode, String TAG, String info)
    {
        switch (mode)
        {
            case LOG_LEVEL_INFO:   
                Log.i(TAG, info);
                break;
            case LOG_LEVEL_DEBUG:
                Log.d(TAG, info);
                break;
            case LOG_LEVEL_WARNING:
                Log.w(TAG, info);
                break;
            case LOG_LEVEL_ERROR:
                Log.e(TAG, info);
                break;
            case LOG_LEVEL_PREVIOUS:
                Log.d(TAG, info);
            	break;
            default:
                break;
        }   
    }
}