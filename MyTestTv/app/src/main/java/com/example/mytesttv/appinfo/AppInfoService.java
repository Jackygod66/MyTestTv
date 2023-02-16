package com.example.mytesttv.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class AppInfoService {
    @Deprecated
    private Context context;

    @Deprecated
    public AppInfoService(Context context) {
        this.context = context;
    }

    @Deprecated
    public List<AppInfo> getAppInfo() {
        List<AppInfo> appInfoList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        //TODO 获取appInfoList逻辑
        return appInfoList;
    }

    public static List<AppInfo> getAppInfo(PackageManager pm) {
        List<AppInfo> appInfoList = new ArrayList<>();
        if (pm == null) return appInfoList;
        //创建要返回的集合对象
        List<AppInfo> appInfos = new ArrayList<>();
        //获取手机中含有launch的应用
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        //遍历所有的应用集合
        for(ResolveInfo info : resolveInfos){

            AppInfo appInfo = new AppInfo();

            //获取应用程序的图标
            Drawable appIcon = info.loadIcon(pm);
            appInfo.setAppIcon(appIcon);

            //获取应用的名称
            String appName = info.loadLabel(pm).toString();
            appInfo.setAppName(appName);

            //获取应用的包名
            String packageName = info.activityInfo.packageName;
            appInfo.setPackageName(packageName);
            try {
                //获取应用的版本号
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                String appVersion = packageInfo.versionName;
                appInfo.setAppVersion(appVersion);

                //判断应用程序是否是用户程序
                boolean isUserApp = filterApp(packageInfo.applicationInfo);
                appInfo.setUserApp(isUserApp);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            appInfos.add(appInfo);
        }
        return appInfos;
    }

    //判断应用程序是否是用户程序
    public static boolean filterApp(ApplicationInfo info) {
        //原来是系统应用，用户手动升级
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //用户自己安装的应用程序
        } else return (info.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
    }
}