package com.example.mytesttv.appinfo;

import android.graphics.drawable.Drawable;

public class AppInfo {
    //图标
    private Drawable appIcon;
    //应用名称
    private String appName;
    //应用版本号
    private String appVersion;
    //应用包名
    private String packageName;
    //是否是用户app
    private boolean isUserApp;

    public AppInfo(){}

    public AppInfo(Drawable app_icon, String app_name, String app_version,
                   String packagename) {
        this.appIcon = app_icon;
        this.appName = app_name;
        this.appVersion = app_version;
        this.packageName = packagename;
    }

    public AppInfo(Drawable app_icon, String app_name, String app_version,
                   String packagename, boolean isUserApp) {
        this.appIcon = app_icon;
        this.appName = app_name;
        this.appVersion = app_version;
        this.packageName = packagename;
        this.isUserApp = isUserApp;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public void setUserApp(boolean isUserApp) {
        this.isUserApp = isUserApp;
    }

    @Override
    public String toString() {
        return "AppInfo [app_icon=" + appIcon + ", app_name=" + appName
                + ", app_version=" + appVersion + ", packagename="
                + packageName + ", isUserApp=" + isUserApp + "]";
    }
}