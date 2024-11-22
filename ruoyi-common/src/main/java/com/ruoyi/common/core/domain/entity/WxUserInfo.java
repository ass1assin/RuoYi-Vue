package com.ruoyi.common.core.domain.entity;

public class WxUserInfo {
    private String userName;

    private String password;

    private String openid;

    private String phoneNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "WxUserInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", openid='" + openid + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
