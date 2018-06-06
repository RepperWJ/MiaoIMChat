package com.sky_wf.chinachat.chat.entity;

/**
 * @Date : 2018/6/5
 * @Author : WF
 * @Description :Bmob登录异常处理
 */
public class SmsLoginException extends Exception
{
    private int errorCode;
    private String msg;

    public SmsLoginException(int errorCode, String msg)
    {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
