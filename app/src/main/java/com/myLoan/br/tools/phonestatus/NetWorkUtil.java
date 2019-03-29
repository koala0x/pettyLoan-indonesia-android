package com.myLoan.br.tools.phonestatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.myLoan.br.MyApplication;
import com.myLoan.br.tools.DebugLog;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/13 0011.
 */

public class NetWorkUtil {

    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getMyApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空
        return null != networkInfo && networkInfo.isAvailable();
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected() {
        if (MyApplication.getMyApplication() != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) MyApplication.getMyApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected() {
        if (MyApplication.getMyApplication() != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) MyApplication.getMyApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 获取ip
     *
     * @param context
     * @return
     */
    public static HashMap<String, String> getIp(Context context) {
        final HashMap<String, String> map = new HashMap<String, String>();
        if (isNetworkConnected()) {
            if (isWifiConnected()) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) {
                    DebugLog.i("wang", "=======" + wifiInfo.getIpAddress());
                    DebugLog.i("wang", "===wifi===" + intIP2StringIP(wifiInfo.getIpAddress()));
                    String ip = intIP2StringIP(wifiInfo.getIpAddress());
                    map.put("ipType", "wifi");
                    map.put("ip", ip);
                    return map;
                }
            } else {
           /*         Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                              e.onNext("1");
                        }
                    }).observeOn(Schedulers.io()).subscribe(new CusumeObserver<String>(){
                        @Override
                        public void onNext(@NonNull String s) {
                            super.onNext(s);*/
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            final InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                                try {
                                    DebugLog.i("wang", "===4G==" + inetAddress.getHostName() + "===" + inetAddress.getHostAddress().toString());
                                    String ip = inetAddress.getHostAddress().toString();
                                    map.put("ipType", "mobile");
                                    map.put("ip", ip);
                                    return map;
                                } catch (Exception e) {
                                    map.put("ipType", "mobile");
                                    map.put("ip", "");
                                    return map;
                                }

                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                   /*     }
                    });*/

            }
            DebugLog.i("wang", "ip无法获取");
            return null;
        }
        DebugLog.i("wang", "无网络连接，ip无法获取");
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
