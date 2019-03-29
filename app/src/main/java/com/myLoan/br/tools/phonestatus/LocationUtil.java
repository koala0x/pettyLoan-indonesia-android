package com.myLoan.br.tools.phonestatus;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.myLoan.br.MyApplication;
import com.myLoan.br.tools.map.AlxLocationManager;
import com.myLoan.br.tools.DebugLog;


/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class LocationUtil {
    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    private static boolean  isGetGps=false;

    public static String getLngAndLat(Context context, LocationListener locationListener) {
        double latitude = 0.0;
        double longitude = 0.0;
        isGetGps=false;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
         DebugLog.i("wang","==GPS=="+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationListener != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,locationListener);
           }

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                com.myLoan.br.tools.map.MyLocation.getInstance().latitude=latitude;
                com.myLoan.br.tools.map.MyLocation.getInstance().longitude=longitude;
                if(locationListener!=null){
                    unRegitLocation(context,locationListener);
                    isGetGps=true;
                  // callbackContext.success(new Gson().toJson(map));
                }
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                DebugLog.i("wang","====GPS定位失败====选择基站定位====");
                return getLngAndLatWithNetwork(context,locationListener);
            }
        } else {    //从网络获取经纬度
            return getLngAndLatWithNetwork(context,locationListener);
        }

       // AppStaus.longitude = String.valueOf(longitude);
      //  AppStaus.latitude = String.valueOf(latitude);
        DebugLog.i("wang", "gps===" + "longitude:" + longitude + "||latitude:" + latitude);
        return longitude + "," + latitude;
    }
    public static void unRegitLocation(Context context,LocationListener locationListener){
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(locationListener);

    }
    public static final boolean isGPSOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }

        return false;
    }
        //从网络获取经纬度
    public static String getLngAndLatWithNetwork(Context context, LocationListener locationListener) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationListener != null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0,locationListener);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            com.myLoan.br.tools.map.MyLocation.getInstance().latitude=latitude;
            com.myLoan.br.tools.map.MyLocation.getInstance().longitude=longitude;
            if(locationListener!=null){
                unRegitLocation(context,locationListener);
            }
        } else {
            DebugLog.i("wang","定位失败");
          //  AppStaus.longitude = "0";
          //  AppStaus.latitude = "0";
            AlxLocationManager.onCreateGPS(MyApplication.mContext);
        }

        DebugLog.i("wang", "network==" + "longitude:" + longitude + "||latitude:" + latitude);
        return longitude + "," + latitude;
    }

    /**
     * 强制帮用户打开GPS * @param context
     */
    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 位置信息监听
     */
    public static  class MyLocation implements LocationListener{
        private  Context context;

        public MyLocation(Context context) {
            this.context = context;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
              //  AppStaus.longitude = String.valueOf(location.getLongitude());
               // AppStaus.latitude = String.valueOf(location.getLatitude());
                DebugLog.i("wang", "longitude:" + location.getLongitude() + "||latitude:" +location.getLatitude());
                if(location!=null){
                    isGetGps=true;
                    com.myLoan.br.tools.map.MyLocation.getInstance().longitude=location.getLongitude();
                    com.myLoan.br.tools.map.MyLocation.getInstance().latitude=location.getLatitude();
                }
                LocationUtil.unRegitLocation(context,this);
            }else {
               // AppStaus.longitude="0";
               // AppStaus.latitude="0";
                isGetGps=true;
             /*   DebugLog.i("wang","定位信息获取失败====开启网络定位");
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("longitude","0");
                map.put("latitude","0");
                callbackContext.success();*/
                // LocationUtil.getLngAndLatWithNetwork(PhoneNumActivity.this,this);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
