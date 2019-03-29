package com.myLoan.br.tools.phonestatus;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.myLoan.br.MyApplication;
import com.myLoan.br.bean.PackInfoBean;
import com.myLoan.br.tools.DebugLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class PackageManagerUtil {
    //版本名
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    public static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = MyApplication.getMyApplication().getPackageManager();
            pi = pm.getPackageInfo(MyApplication.getMyApplication().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return getPackageInfo(context).packageName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static List<PackInfoBean> getAppPackageInfo(Context context, boolean allowAcessPackageStatus) {
        UsageStatsManager usageStatsManager;
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = new ArrayList<ActivityManager.RunningTaskInfo>();
        Map<String, UsageStats> usageStatusMap = new HashMap<String, UsageStats>();
        List<PackInfoBean> list = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> ListPackInfo = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usageStatsManager != null) {
                long now = System.currentTimeMillis();
                usageStatusMap = usageStatsManager.queryAndAggregateUsageStats(now - 60 * 1000, now);
                for (Map.Entry<String, UsageStats> u : usageStatusMap.entrySet()) {
                    DebugLog.i("wang", "==UsageStats==" + u.getKey());
                }
            }

        } else {
            runningTaskInfoList = activityManager.getRunningTasks(Integer.MAX_VALUE);
        }
       /* HashMap<String, ActivityManager.RunningAppProcessInfo> appProcessInfoHashMap = new HashMap<>();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessInfoList) {
            String processName = appProcess.processName;
            DebugLog.i("wang", "===processName==" + processName);
            String[] pkgList = appProcess.pkgList;
            for (int i = 0; i < pkgList.length; i++) {
                String pkgName = pkgList[i];
                appProcessInfoHashMap.put(pkgName, appProcess);
            }
        }*/
        for (PackageInfo info : ListPackInfo) {
            ApplicationInfo appInfo = info.applicationInfo;
            //去除系统应用
            if (!filterApp(appInfo)) {
                continue;
            }
            //拿到应用程序的图标
            Drawable icon = appInfo.loadIcon(packageManager);
            //拿到应用程序的程序名
            String appName = appInfo.loadLabel(packageManager).toString();
            //拿到应用程序的包名
            String packageName = appInfo.packageName;
            //拿到应用程序apk路径
            String apkePath = appInfo.sourceDir;
            //应用装时间
            long firstInstallTime = info.firstInstallTime;
            //应用最后一次更新时间
            long lastUpdateTime = info.lastUpdateTime;

            //获取应用程序启动意图
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
//            DebugLog.i("wang", "==package==" + packageName + "====" + appName);
            PackInfoBean appItem = new PackInfoBean();
            appItem.setPackName(packageName);
            appItem.setAppName(appName);
            appItem.setAppInstallFirstTime(firstInstallTime + "");
            appItem.setAppInstallTime(lastUpdateTime + "");
            if (allowAcessPackageStatus) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (usageStatusMap != null) {
                        if (usageStatusMap.containsKey(packageName)) {
                            appItem.setIsRunning("1");
                        } else {
                            appItem.setIsRunning("0");
                        }
                    }
                } else {
                    //info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
                    for (ActivityManager.RunningTaskInfo taskInfo : runningTaskInfoList) {
                        if (taskInfo.topActivity.getPackageName().equals(context.getPackageName()) && taskInfo.baseActivity.getPackageName().equals(context.getPackageName())) {
                            appItem.setIsRunning("1");
                            break;
                        } else {
                            appItem.setIsRunning("0");
                            break;
                        }
                    }
                }

            } else {
                appItem.setIsRunning("3");
            }

            list.add(appItem);
        }

        return list;
    }


    /**
     * 判断某一个应用程序是不是系统的应用程序，
     * 如果是返回true，否则返回false。
     */
    public static boolean filterApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //判断是不是系统应用
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
