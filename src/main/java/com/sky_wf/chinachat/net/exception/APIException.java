package com.sky_wf.chinachat.net.exception;

/**
 * @Date :  2018/6/1
 * @Author : WF
 * @Description :
 */
public class APIException extends RuntimeException {
    private int code;
    private String message;

    public APIException(int code,String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
