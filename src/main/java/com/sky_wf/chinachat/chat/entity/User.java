package com.sky_wf.chinachat.chat.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * @Date :  2018/6/6
 * @Author : WF
 * @Description :用户
 */
public class User extends BmobUser {
    private String nickname;//昵称
    private String headUrl;//头像保存地址
    private String signature;//个性签名
    private String sex;//性别
    private String location;//位置信息
    private String birthday;//生日
    private String type;//用户类型 N-正常用户 P-公众账号



    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
