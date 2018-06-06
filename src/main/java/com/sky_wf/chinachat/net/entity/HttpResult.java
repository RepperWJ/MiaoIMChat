package com.sky_wf.chinachat.net.entity;

import com.sky_wf.chinachat.net.NetConstans;

/**
 * @Date :  2018/6/1
 * @Author : WF
 * @Description :
 */
public class HttpResult<T> {
    private int code;
    private String desc;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucess()
    {
        return code==NetConstans.SUCCESS;
    }

    public boolean isEmpty()
    {
        return code == NetConstans.EMPTY;
    }

    public boolean isNoMore()
    {
        return code == NetConstans.NOMORE;
    }
}
