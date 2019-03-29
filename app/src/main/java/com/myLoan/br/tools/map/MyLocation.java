package com.myLoan.br.tools.map;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyLocation {
    public double latitude = 0;
    public double longitude = 0;
    public long updateTime;//最后更新时间，用于做精确度择优
    public float accuracy;
    private static MyLocation myLocation;

    MyLocation() {
    }

    public MyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static MyLocation getInstance() {
        if (myLocation == null) myLocation = new MyLocation();
        return myLocation;
    }
}
