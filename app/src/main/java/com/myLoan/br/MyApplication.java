package com.myLoan.br;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;
import com.myLoan.br.base.MMkvInfo;
import com.myLoan.br.tools.imageLoader.ImageLoaderFactory;
import com.myLoan.br.service.PushIntentService;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.view.FontUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.fraudmetrix.octopus.moboxclippicture.OctopusOcrManager;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {
    public static Application mContext;
    public static List<Activity> activitielists = new ArrayList<>();

    public void onCreate() {
        super.onCreate();
        mContext = this;
        DebugLog.i("=oncreate===" + mContext);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);
        FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, "fonts/HKGrotesk-Medium.ttf");
        ImageLoaderFactory.createImageLoader().initialize(this);
        PushManager.getInstance().initialize(this, PushService.class);
        PushManager.getInstance().registerPushIntentService(this, PushIntentService.class);
        initMMKV();

        OctopusOcrManager.getInstance().init(this,"ps2_id_test","b36e51763f684b9db3cbac4a1287e16e");
        initLocation();

    }

    private void initLocation() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());


    }

/*    private void initFacebook() {
        FacebookSdk.setApplicationId("303600783815649");
        FacebookSdk.sdkInitialize(mContext);
    }*/


    private void initMMKV() {
        MMKV.initialize(this);
        MMkvInfo.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Application getMyApplication() {
        return mContext;
    }

    /**
     * 获取userId
     *
     * @return
     */
    public static int getUserId() {
        //   return 100113;
        return 997;
    }

    public static void regitActivity(Activity activity) {
        if (activitielists != null) {
            if (activitielists.contains(activity)) {
                activitielists.remove(activity);
            }
            activitielists.add(activity);
        }
    }

    public static void unRegitActivity(Activity activity) {
        if (activitielists != null) {
            activitielists.remove(activity);
        }
    }

    public static void clearAllTaskActivty() {
        Iterator iterator = activitielists.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            iterator.remove();
            activity.finish();

        }
    }



}
