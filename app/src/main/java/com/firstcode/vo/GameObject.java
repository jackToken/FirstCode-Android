package com.firstcode.vo;

/**
 * Created by wangjinliang on 2016/3/21.
 */
public class GameObject {
    private String gameName;
    private String apkUrl;
    private String picUrl;
    private String packageName;
    private String uid;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "gameName='" + gameName + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", packageName='" + packageName + '\'' +
                ", uid='" + uid + '\'' +
                '}' + "\n";
    }
}
