package com.myLoan.br.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.myLoan.br.BuildConfig;
import com.myLoan.br.Constant;
import com.myLoan.br.MyApplication;
import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.bean.CallBackBean;
import com.myLoan.br.bean.CamaraParam;
import com.myLoan.br.bean.DetectLivenessRequest;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.PackInfoBean;
import com.myLoan.br.bean.UserDeviceDetailVO;
import com.myLoan.br.bean.UserPhoneAddCodeBO;
import com.myLoan.br.bean.UserPhoneAppDTOS;
import com.myLoan.br.bean.UserPhoneBO;
import com.myLoan.br.bean.UserPhoneCallDTO;
import com.myLoan.br.bean.UserPhoneContactDTO;
import com.myLoan.br.bean.UserPhoneContactDTOH5;
import com.myLoan.br.bean.UserPhoneContactDTOS;
import com.myLoan.br.bean.UserPhoneContactMessH5;
import com.myLoan.br.bean.UserPhoneSmsDTO;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.GonsonUtil;
import com.myLoan.br.http.GsonUtil;
import com.myLoan.br.listener.contract.DeviceView;
import com.myLoan.br.listener.contract.FaceIdContract;
import com.myLoan.br.presenter.DetectLivenessPresenter;
import com.myLoan.br.presenter.DeviceFingurePresenter;
import com.myLoan.br.presenter.DevicePresenter;
import com.myLoan.br.presenter.LocationPresener;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.RootManager;
import com.myLoan.br.tools.file.DebugMessage;
import com.myLoan.br.tools.file.FileUtils;
import com.myLoan.br.tools.file.ImageUtil;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.tools.loginstate.LoginState;
import com.myLoan.br.tools.map.MyLocation;
import com.myLoan.br.tools.math.DESCoder;
import com.myLoan.br.tools.math.MD5Util;
import com.myLoan.br.tools.phonestatus.CameraUtils;
import com.myLoan.br.tools.phonestatus.ContactUtil;
import com.myLoan.br.tools.phonestatus.DeviceUtil;
import com.myLoan.br.tools.phonestatus.NetWorkUtil;
import com.myLoan.br.tools.phonestatus.PackageManagerUtil;
import com.myLoan.br.tools.view.DensityUtil;
import com.myLoan.br.tools.view.ForbidDoubleClickTools;
import com.myLoan.br.tools.view.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mmkv.MMKV;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import cn.fraudmetrix.octopus.livenesssdk.OctopusLivenessCallback;
import cn.fraudmetrix.octopus.livenesssdk.OctopusLivenessInfo;
import cn.fraudmetrix.octopus.livenesssdk.OctopusLivenessManager;
import cn.fraudmetrix.octopus.moboxclippicture.OctopusOcrCallBack;
import cn.fraudmetrix.octopus.moboxclippicture.OctopusOcrManager;
import cn.fraudmetrix.octopus.moboxclippicture.bean.OctopusOcrCallBackData;
import cn.fraudmetrix.octopus.moboxclippicture.bean.OctopusParam;
import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;
import cn.tongdun.android.shell.inter.FMCallback;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.myLoan.br.tools.phonestatus.LocationUtil.openGPS;


public class WebViewActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener, DeviceView, FaceIdContract {
    public static final String MMKV_CAMERA_CAMERAFRONTNUMBER = "cameraFrontNumber";//手机后摄像头像素
    public static final String MMKV_CAMERA_CAMERAREARNUMBER = "cameraRearNumber";//手机后摄像头像素
    public static final String MMKV_CAMERA_CAMERAREARCAMERAPIXEL = "cameraRearCameraPixel";//手机后摄像头像素
    public static final String MMKV_CAMERA_CAMERAFRONTCAMERAPIXEL = "cameraFrontCameraPixel";//手机前摄像头像素
    private final static int FILECHOOSER_RESULTCODE = 1;
    private final static int FILECHOOSER_RESULTCODE_ARRAY = 2;                      //编辑图片
    public BridgeWebView webView;
    private int misOver = 0;
    int lastHeight;
    int lastVisibleHeight;
    private String urlPath;
    private RxPermissions rxPermissions;
    private String path_icon;
    private String tempName; //临时照片文件路径
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private String imageFullPath = null;    //图片地址
    private CallBackFunction mCallback;
    private CallBackFunction mCallback1, mCallback2, mCallback3, mCallback4, mCallback5, mCallback6, mCallback7, mCallback8;
    private DevicePresenter mDevicePresenter;
    private DeviceFingurePresenter deviceFingurePresenter;
    private LocationPresener locationPresener;
    private DetectLivenessPresenter detectLivenessPresenter;
    String userAgent = "";
    private boolean onePermissionOK;
    private boolean twoPermissionOk;
    private boolean oneCountPermissionOK;
    private boolean twoCountPermissionOk;
    private boolean hardwareOnePermissionOk;
    private boolean hardwaretwoPermissionOk;
    private boolean hardwarethreePermissionOk;
    private List<Integer> listBack = new ArrayList<>();
    private List<Integer> listFont = new ArrayList<>();
    CamaraParam camaraParam = new CamaraParam();

    private Uri uri;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        webView = findViewById(R.id.webview_content);
        webView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        WebSettings webSettings = webView.getSettings();//获得webSettings设置对象
        userAgent = webSettings.getUserAgentString();
        //设置支持h5的localStorage属性
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        //下面3行是WebView支持JS并能够和JS代码间进行交互的设置
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setJavaScriptEnabled(true);//设置支持javascript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//可以自动打开窗口
        webSettings.setSupportZoom(true);//设置是否支持变焦(缩放)
        webSettings.setBuiltInZoomControls(true);//设置显示缩放按钮
        webView.getSettings().setUseWideViewPort(true);//扩大比例的缩放
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
        webView.getSettings().setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
        webSettings.setPluginState(WebSettings.PluginState.ON);//这里是支持flash的相关设置
        webView.getSettings().setSupportMultipleWindows(true);
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebChromeClient(new MyWebChromeClient()); //辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        webView.setDownloadListener(new MyWebViewDownLoadListener());

        initJsListener();
        initListener();
        urlPath = getIntent().getStringExtra("urlPath");
        syncCookie(urlPath, "android");
        webView.loadUrl(urlPath);
        webView = findViewById(R.id.webview_content);
    }

    @Override
    public void intData() {
        path_icon = FileUtils.getRootDir(WebViewActivity.this);
        rxPermissions = new RxPermissions(this);
    }

    private void initJsListener() {
    }

    public void initListener() {

        webView.setPageLoadFinshListener(new BridgeWebViewClient.PageLoadFinshListener() {
            @Override
            public void pageStart() {
                showProgessDialog();
            }

            @Override
            public void pageFinish() {
                hiddenProgessDialog();
            }

            @Override
            public void pageError() {
                hiddenProgessDialog();
            }
        });

        webView.registerHandler("getUserLoginInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LoginBean.DataBean loginBean;
                if (LoginContext.getLoginContext().getState() instanceof LoginState) {
                    loginBean = LoginContext.getLoginContext().getUser(WebViewActivity.this);
                    loginBean.setDeviceId(DeviceUtil.getUniqueID());
                    loginBean.setAppsflyId("ddddddd");
                    String clientId = PushManager.getInstance().getClientid(MyApplication.mContext);
                    loginBean.setClientId(clientId != null ? clientId : "");
                    loginBean.setLatitude(String.valueOf(MyLocation.getInstance().latitude));
                    loginBean.setLongitude(String.valueOf(MyLocation.getInstance().longitude));
                    loginBean.setVest(String.valueOf(Constant.vest));
                    loginBean.setDeviceType(DeviceUtil.getDeviceName());
                    loginBean.setAppVersion(PackageManagerUtil.getVersionName());
                    DebugLog.i("wang", "====getUserLoginInfo===" + GsonUtil.buildGosn().toJson(loginBean));
                    function.onCallBack(GsonUtil.buildGosn().toJson(loginBean));
                } else {
                    function.onCallBack("{}");
                }

            }
        });
        webView.registerHandler("goBack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
        webView.registerHandler("openCamera", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback = function;
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean granted) {
                        super.onNext(granted);
                        if (granted) {
                            setGetCameraPix();
                            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            path_icon = FileUtils.getRootDir(WebViewActivity.this);
                            tempName = path_icon + System.currentTimeMillis() + "_temp.jpg";
                            ContentValues contentValues = new ContentValues(1);
                            contentValues.put(MediaStore.Images.Media.DATA, tempName);
                            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(cameraintent, PHOTO_REQUEST_CAMERA);
                        } else {
                            CamaraParam camaraParam = new CamaraParam();
                            camaraParam.setCode(Constant.DENY_PERMISSION);
                            function.onCallBack(GsonUtil.buildGosn().toJson(camaraParam));
                        }
                    }
                });
            }
        });
        webView.registerHandler("openOCR", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback = function;
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean granted) {
                        super.onNext(granted);
                        if (granted) {
                            setGetCameraPix();
                            OctopusParam param = new OctopusParam();
                            OctopusOcrManager.getInstance().getPhoto(WebViewActivity.this, param, new OctopusOcrCallBack() {
                                @Override
                                public void onCallBack(OctopusOcrCallBackData data) {
                                    Bitmap bitmap = data.mIdcardBitmap;//拍照返回  的图片
                                    int code = data.code;//拍照返回的code码
                                    String taskid = data.taskId;//此次拍照生成的taskid
                                    Log.d("codecode", code + "|||" + taskid);
                                    if (code == 0) {
                                        saveImage(bitmap, taskid);
                                    } else {
                                        CamaraParam camaraParam = new CamaraParam();
                                        camaraParam.setCode(code);
                                        mCallback.onCallBack(GonsonUtil.buildGosn().toJson(camaraParam));
                                    }
                                }
                            });
                        } else {
                            CamaraParam camaraParam = new CamaraParam();
                            camaraParam.setCode(Constant.DENY_PERMISSION);
                            function.onCallBack(GsonUtil.buildGosn().toJson(camaraParam));
                        }
                    }
                });
            }
        });
        webView.registerHandler("goHome", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });

        webView.registerHandler("goLogin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        webView.registerHandler("forgetLoginPw", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(WebViewActivity.this, SetLoginPasswdActivity.class);
                intent.putExtra("phoneNum", LoginContext.getLoginContext().getUser(WebViewActivity.this).getPhoneNumber());
                intent.putExtra("area", Constant.area);
                intent.putExtra("type", Constant.VETIFYCODE_TYPE_RESET_LOGIN);
                startActivity(intent);
            }
        });
        webView.registerHandler("openGallary", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback = function;
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new CusumeObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            super.onNext(aBoolean);

                            if (aBoolean) {
                                //选择相册图片
                                Intent photointent = null;
                                if (Build.VERSION.SDK_INT >= 19) {
                                    photointent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    photointent.setType("image/*");
                                } else {
                                    photointent = new Intent(Intent.ACTION_GET_CONTENT);
                                    photointent.addCategory(Intent.CATEGORY_OPENABLE);
                                    photointent.setType("image/*");
                                }

                                tempName = path_icon + System.currentTimeMillis() + "_temp.jpg";
                                startActivityForResult(photointent, PHOTO_REQUEST_GALLERY);
                            } else {
                                CamaraParam camaraParam = new CamaraParam();
                                camaraParam.setCode(Constant.REQUEST_CANCEL);
                                function.onCallBack(GonsonUtil.buildGosn().toJson(camaraParam));
                            }
                        }
                    });
                } else {
                    //选择相册图片
                    Intent photointent = null;
                    if (Build.VERSION.SDK_INT >= 19) {
                        photointent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        photointent.setType("image/*");
                    } else {
                        photointent = new Intent(Intent.ACTION_GET_CONTENT);
                        photointent.addCategory(Intent.CATEGORY_OPENABLE);
                        photointent.setType("image/*");
                    }
                    path_icon = FileUtils.getRootDir(WebViewActivity.this);
                    tempName = path_icon + System.currentTimeMillis() + "_temp.jpg";
                    startActivityForResult(photointent, PHOTO_REQUEST_GALLERY);
                }
            }
        });

        webView.registerHandler("getContact", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback = function;
                Log.i("wang==getContact====", "handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    rxPermissions.request(Manifest.permission.READ_CONTACTS).subscribe(new CusumeObserver<Boolean>() {
                        @Override
                        public void onNext(Boolean aBoolean) {
                            super.onNext(aBoolean);

                            if (aBoolean) {
                                List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
                                List<UserPhoneContactDTOH5> listContactH5 = new ArrayList<>();
                                for (int i = 0; i < listContact.size(); i++) {
                                    UserPhoneContactDTOH5 userPhoneContactDTOH5 = new UserPhoneContactDTOH5();
                                    userPhoneContactDTOH5.setContactName(listContact.get(i).getContactName());
                                    userPhoneContactDTOH5.setContactPhones(listContact.get(i).getContactPhones());
                                    listContactH5.add(userPhoneContactDTOH5);
                                }
                                UserPhoneContactMessH5 userPhoneContactMessH5 = new UserPhoneContactMessH5();
                                userPhoneContactMessH5.setUserPhoneContactDTOH5s(listContactH5);
                                userPhoneContactMessH5.setCode(1);
                                Log.i("wang==getContact====", GonsonUtil.buildGosn().toJson(userPhoneContactMessH5));
                                function.onCallBack(GonsonUtil.buildGosn().toJson(userPhoneContactMessH5));
                            } else {
                                Log.i("wang==getContact====", "REJECT");
                                UserPhoneContactMessH5 userPhoneContactMessH5 = new UserPhoneContactMessH5();
                                userPhoneContactMessH5.setUserPhoneContactDTOH5s(new ArrayList<UserPhoneContactDTOH5>());
                                userPhoneContactMessH5.setCode(0);
                                function.onCallBack(GonsonUtil.buildGosn().toJson(userPhoneContactMessH5));
                            }
                        }
                    });
                } else {
                    List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
                    List<UserPhoneContactDTOH5> listContactH5 = new ArrayList<>();
                    for (int i = 0; i < listContact.size(); i++) {
                        UserPhoneContactDTOH5 userPhoneContactDTOH5 = new UserPhoneContactDTOH5();
                        userPhoneContactDTOH5.setContactName(listContact.get(i).getContactName());
                        userPhoneContactDTOH5.setContactPhones(listContact.get(i).getContactPhones());
                        listContactH5.add(userPhoneContactDTOH5);
                    }
                    UserPhoneContactMessH5 userPhoneContactMessH5 = new UserPhoneContactMessH5();
                    userPhoneContactMessH5.setUserPhoneContactDTOH5s(listContactH5);
                    userPhoneContactMessH5.setCode(1);
                    Log.i("wang==getContact====", GonsonUtil.buildGosn().toJson(userPhoneContactMessH5));
                    function.onCallBack(GonsonUtil.buildGosn().toJson(userPhoneContactMessH5));
                }
            }
        });

        webView.registerHandler("InstalledApp", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback1 = function;
                Log.i("wang==InstalledApp====", "handler");
                //安装过的app信息
                List<PackInfoBean> packInfoList = PackageManagerUtil.getAppPackageInfo(mContext, true);
                UserPhoneAppDTOS userPhoneAppDTOS = new UserPhoneAppDTOS();
                userPhoneAppDTOS.setList(packInfoList);
                String userId = "";
                if (LoginContext.getLoginContext().getState() instanceof LoginState
                        && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                    userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                }
                userPhoneAppDTOS.setUserId(userId);
                Gson gson = new Gson();
                String entry = DESCoder.encryData(gson.toJson(userPhoneAppDTOS));
                Log.i("wang==u===", gson.toJson(userPhoneAppDTOS));
                mDevicePresenter.getInstalledApkMess(entry);
            }

        });

        webView.registerHandler("contactMess", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback2 = function;
                Log.i("wang==contactMess====", "handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //申请的权限全部允许,定位请求
                                List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
                                UserPhoneContactDTOS userPhoneContactDTOS = new UserPhoneContactDTOS();
                                userPhoneContactDTOS.setList(listContact);
                                String userId = "";
                                if (LoginContext.getLoginContext().getState() instanceof LoginState
                                        && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                                    userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                                }
                                userPhoneContactDTOS.setUserId(userId);
                                Gson gson = new Gson();
                                String entry = DESCoder.encryData(gson.toJson(userPhoneContactDTOS));
                                Log.i("wang==u===", gson.toJson(userPhoneContactDTOS));
                                mDevicePresenter.getContact(entry);
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                mCallback.onCallBack(getFailPermissionDeny());
                            }
                        }
                    });
                } else {
                    List<UserPhoneContactDTO> listContact = ContactUtil.readContactMessage(mContext);
                    UserPhoneContactDTOS userPhoneContactDTOS = new UserPhoneContactDTOS();
                    userPhoneContactDTOS.setList(listContact);
                    String userId = "";
                    if (LoginContext.getLoginContext().getState() instanceof LoginState
                            && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                        userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                    }
                    userPhoneContactDTOS.setUserId(userId);
                    Gson gson = new Gson();
                    String entry = DESCoder.encryData(gson.toJson(userPhoneContactDTOS));
                    Log.i("wang==u===", gson.toJson(userPhoneContactDTOS));
                    mDevicePresenter.getContact(entry);
                }
            }

        });

        webView.registerHandler("getHardwareMess", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                openGPS(mContext);
                mCallback8 = function;
                issendPhone = false;
                Log.i("wang=====", "getHardwareMess handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                hardwareOnePermissionOk = true;
                                initphone();
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                mCallback.onCallBack(getFailPermissionDeny());
                            }
                        }
                    });
                } else {
                    hardwareOnePermissionOk = true;
                    initphone();
                }

                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                hardwaretwoPermissionOk = true;
                                initphone();
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                mCallback.onCallBack(getFailPermissionDeny());
                            }
                        }
                    });
                } else {
                    hardwaretwoPermissionOk = true;
                    initphone();
                }

                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                hardwarethreePermissionOk = true;
                                initphone();
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                mCallback.onCallBack(getFailPermissionDeny());
                            }
                        }
                    });
                } else {
                    hardwarethreePermissionOk = true;
                    initphone();
                }

            }


        });
        webView.registerHandler("getCall", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback3 = function;
                Log.i("wang==getCall====", "handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_CALL_LOG).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //申请的权限全部允许,定位请求
                                List<UserPhoneCallDTO.UserPhoneCallBO> listBeanList = ContactUtil.readCallLog(mContext);
//                        mDevicePresenter.getContact();
                                UserPhoneCallDTO userPhoneCallDTO = new UserPhoneCallDTO();
                                String userId = "";
                                if (LoginContext.getLoginContext().getState() instanceof LoginState
                                        && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                                    userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                                }
                                userPhoneCallDTO.setUserId(userId);
                                userPhoneCallDTO.setList(listBeanList);
                                Gson gson = new Gson();
                                String entry = DESCoder.encryData(gson.toJson(userPhoneCallDTO));
                                Log.i("wang==u===", gson.toJson(userPhoneCallDTO));
                                mDevicePresenter.getCall(entry);
                            } else {
                                //只要有一个权限被拒绝，就会执行0
                                mCallback.onCallBack(getFailPermissionDeny());
                            }
                        }
                    });
                } else {
                    List<UserPhoneCallDTO.UserPhoneCallBO> listBeanList = ContactUtil.readCallLog(mContext);
                    UserPhoneCallDTO userPhoneCallDTO = new UserPhoneCallDTO();
                    String userId = "";
                    if (LoginContext.getLoginContext().getState() instanceof LoginState
                            && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                        userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                    }
                    userPhoneCallDTO.setUserId(userId);
                    userPhoneCallDTO.setList(listBeanList);
                    Gson gson = new Gson();
                    String entry = DESCoder.encryData(gson.toJson(userPhoneCallDTO));
                    Log.i("wang==u===", gson.toJson(userPhoneCallDTO));
                    mDevicePresenter.getCall(entry);
                }
            }

        });

        webView.registerHandler("getSms", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback4 = function;
                Log.i("wang==getSms====", "handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //申请的权限全部允许,定位请求
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
                                Log.i("wang==u===", gson.toJson(userPhoneSmsDTO));
                                mDevicePresenter.getSms(entry);
                            } else {
                                //只要有一个权限被拒绝，就会执行
                                mCallback.onCallBack(getFail(1, ""));
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
                    Log.i("wang==u===", gson.toJson(userPhoneSmsDTO));
                    mDevicePresenter.getSms(entry);
                }
            }

        });
        webView.registerHandler("getDviceDetail", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback5 = function;
                Log.i("wang====", "getDviceDetail  handler");
                getDeviceDetail();
            }
        });

        webView.registerHandler("getNewCode", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback6 = function;
                isSendInfo = false;
                Log.i("wang====", "getNewCode  handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_CALL_LOG).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                onePermissionOK = true;
                                sendInfo();
                            } else {
                                mCallback.onCallBack(getFailPermissionDeny());
                            }

                        }
                    });

                } else {
                    onePermissionOK = true;
                    sendInfo();
                }

                //读取第一条短信

                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                twoPermissionOk = true;
                                sendInfo();
                            } else {
                                mCallback.onCallBack(getFailPermissionDeny());
                            }

                        }
                    });

                } else {
                    twoPermissionOk = true;
                    sendInfo();

                }
            }

        });
        webView.registerHandler("uploadfaceNow", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback = function;
                goFaceId();
            }
        });

        webView.registerHandler("getCount", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                mCallback7 = function;
                isSendCount = false;
                Log.i("wang====", "getCount  handler");
                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_CALL_LOG).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                oneCountPermissionOK = true;
                                setCount();
                            } else {
                                mCallback.onCallBack(getFailPermissionDeny());
                            }

                        }
                    });

                } else {
                    oneCountPermissionOK = true;
                    setCount();
                }

                //读取第一条短信

                if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
                    rePermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                twoCountPermissionOk = true;
                                setCount();
                            } else {
                                mCallback.onCallBack(getFailPermissionDeny());
                            }

                        }
                    });

                } else {
                    twoCountPermissionOk = true;
                    setCount();

                }
            }

        });

        webView.registerHandler("copy", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!TextUtils.isEmpty(data)) {
                    if (ForbidDoubleClickTools.fastDouble(WebViewActivity.this)) {
                        return;
                    }
                    ClipboardManager cm = (ClipboardManager) WebViewActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(data);
                    ToastUtil.showToast(getString(R.string.cut_conent));
                } else {
                    //    ToastUtil.showToast(getString(R.string.cut_conent_failture));
                }
            }
        });
    }


    @Override
    public void onGlobalLayout() {
        Rect visible = new Rect();
        Rect size = new Rect();
        webView.getWindowVisibleDisplayFrame(visible);
        webView.getHitRect(size);

        int height = size.bottom - size.top;
        int visibleHeight = visible.bottom - visible.top;
        if (height == lastHeight && lastVisibleHeight == visibleHeight) return;

        lastHeight = height;
        lastVisibleHeight = visibleHeight;
        String js = String.format("javascript:heightChange(%1$d , %2$d)", height, visibleHeight);
        webView.loadUrl(js);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void getInatalledApkSuccess() {

        mCallback1.onCallBack(getSuccess());
    }

    @Override
    public void getInatalledApkFail(int code, String message) {
        mCallback1.onCallBack(getFail(code, message));
    }

    @Override
    public void getContactSuccess() {
        mCallback2.onCallBack(getSuccess());
    }

    @Override
    public void getContactFail(int code, String message) {
        Log.i("wang8888888888888", "getContactFail");
        mCallback2.onCallBack(getFail(code, message));
    }

    @Override
    public void getCallSuccess() {
        Log.i("wang8888888888888", "getCallSuccess");
        mCallback3.onCallBack(getSuccess());
    }

    @Override
    public void getCallFail(int code, String message) {
        Log.i("wang8888888888888", "getCallFail");
        mCallback3.onCallBack(getFail(code, message));
    }

    @Override
    public void getSmsSuccess() {
        Log.i("wang8888888888888", "getSmsSuccess");
        mCallback4.onCallBack(getSuccess());
    }

    @Override
    public void getSmsFail(int code, String message) {
        Log.i("wang8888888888888", "getSmsFail");
        mCallback4.onCallBack(getFail(code, message));
    }

    @Override
    public void getDeviceDetailSuccess() {
        Log.i("wang8888888888888", "getDeviceDetailSuccess");
        mCallback5.onCallBack(getSuccess());
    }

    @Override
    public void getDeviceDetailFail(int code, String message) {
        Log.i("wang8888888888888", "getDeviceDetailFail");
        mCallback5.onCallBack(getFail(code, message));
    }

    @Override
    public void getNewCodeSuccess() {
        Log.i("wang8888888888888", "getNewCodeSuccess");
        mCallback6.onCallBack(getSuccess());
    }

    @Override
    public void getNewCodeFail(int code, String message) {
        Log.i("wang8888888888888", "getNewCodeFail");
        mCallback6.onCallBack(getFail(code, message));
    }

    @Override
    public void getCountSuccess() {
        Log.i("wang8888888888888", "getCountSuccess");
        mCallback7.onCallBack(getSuccess());
    }

    @Override
    public void getCountFail(int code, String message) {
        Log.i("wang8888888888888", "getCountFail");
        mCallback7.onCallBack(getFail(code, message));
    }

    @Override
    public void getHardWareMessSuccess() {
        Log.i("wang8888888888888", "getHardWareMessSuccess");
        mCallback8.onCallBack(getSuccess());
    }

    @Override
    public void getHardWareMessFail(int code, String message) {
        Log.i("wang8888888888888", "getHardWareMessFail");
        mCallback8.onCallBack(getFail(code, message));
    }

    private boolean issendPhone = false;

    private void initphone() {
        if (hardwaretwoPermissionOk && hardwareOnePermissionOk && hardwarethreePermissionOk && !issendPhone) {
            getLocation();
            MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);

            //保存手机硬件信息
            UserPhoneBO userPhoneBO = new UserPhoneBO();
            String userId = "";
            if (LoginContext.getLoginContext().getState() instanceof LoginState
                    && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
            }
            userPhoneBO.setUserId(userId);
            UserPhoneBO.UserPhoneAddressBO userPhoneAddressBO = new UserPhoneBO.UserPhoneAddressBO();
            UserPhoneBO.UserPhoneBssidBO userPhoneBssidBO = new UserPhoneBO.UserPhoneBssidBO();
            final UserPhoneBO.UserPhoneHardwareBO userPhoneHardwareBO = new UserPhoneBO.UserPhoneHardwareBO();
            UserPhoneBO.UserPhoneIpBO userPhoneIpBO = new UserPhoneBO.UserPhoneIpBO();
            UserPhoneBO.UserPhoneVersionBO userPhoneVersionBO = new UserPhoneBO.UserPhoneVersionBO();

            //ssid
            userPhoneBssidBO.setBssid(DeviceUtil.Wifi.getBSSID(mContext));

            userPhoneHardwareBO.setUserAgent(userAgent);
            userPhoneHardwareBO.setPhoneNumber(DeviceUtil.getTelPhone(mContext));
            userPhoneHardwareBO.setPhoneIccd(DeviceUtil.getICCID(mContext));
            userPhoneHardwareBO.setPhoneImei(DeviceUtil.getIMEI());
            userPhoneHardwareBO.setPhoneImsi(DeviceUtil.getIMSI(mContext));
            userPhoneHardwareBO.setPhoneUuid(DeviceUtil.getUUID(mContext));
            userPhoneHardwareBO.setPhoneMeid(DeviceUtil.getMEID());
            userPhoneHardwareBO.setEquipmentModel(DeviceUtil.getDeviceName());
            userPhoneHardwareBO.setCpu(DeviceUtil.getAndroidCpuApi());
            userPhoneHardwareBO.setSystemVersion(Build.VERSION.SDK);
            userPhoneHardwareBO.setMotherboard(DeviceUtil.getAndroidBorder());
            userPhoneHardwareBO.setRoot(RootManager.isDeviceRooted());
            userPhoneHardwareBO.setBootTime(DeviceUtil.getOpenTime() + "");
            userPhoneHardwareBO.setBatteryPower(DeviceUtil.getBatteryLevel(mContext) + "");
            userPhoneHardwareBO.setScreenSize(DeviceUtil.getSize(mContext) + "");
            userPhoneHardwareBO.setScreenResolutionDpi(DensityUtil.getDensity(mContext) + "");
            userPhoneHardwareBO.setScreenResolution(DensityUtil.getDisplayWidth(mContext) + "*" + DensityUtil.getDisplayHeight(mContext));
            userPhoneHardwareBO.setAffiliatedTerminal("3");
            int cameraFrontNumber = mMkv.decodeInt(MMKV_CAMERA_CAMERAFRONTNUMBER, 0);
            int cameraRearNumber = mMkv.decodeInt(MMKV_CAMERA_CAMERAREARNUMBER, 0);
            String cameraRearCameraPixel = mMkv.decodeString(MMKV_CAMERA_CAMERAREARCAMERAPIXEL, "");
            String cameraFrontCameraPixel = mMkv.decodeString(MMKV_CAMERA_CAMERAFRONTCAMERAPIXEL, "");

            userPhoneHardwareBO.setCameraFrontNumber(cameraFrontNumber + "");
            userPhoneHardwareBO.setCameraRearNumber(cameraRearNumber + "");
            userPhoneHardwareBO.setCameraFrontCameraPixel(cameraFrontCameraPixel);
            userPhoneHardwareBO.setCameraRearCameraPixel(cameraRearCameraPixel);

            DebugLog.i("wang", "===cameraFrontNumber + cameraRearNumber + cameraRearCameraPixel + cameraFrontCameraPixel==" + cameraFrontNumber + cameraRearNumber + cameraRearCameraPixel + cameraFrontCameraPixel);
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


            DebugLog.i("wang", "====click====longitude=" + MyLocation.getInstance().longitude + "===latitude===" + MyLocation.getInstance().latitude);
            userPhoneAddressBO.setLongitude(MyLocation.getInstance().longitude + "");
            userPhoneAddressBO.setLatitude(MyLocation.getInstance().latitude + "");

            userPhoneBO.setUserPhoneAddressBO(userPhoneAddressBO);
            userPhoneBO.setUserPhoneBssidBO(userPhoneBssidBO);
            userPhoneBO.setUserPhoneHardwareBO(userPhoneHardwareBO);
            userPhoneBO.setUserPhoneIpBO(userPhoneIpBO);
            userPhoneBO.setUserPhoneVersionBO(userPhoneVersionBO);

            Gson gson1 = new Gson();
            String entry = DESCoder.encryData(gson1.toJson(userPhoneBO));
            Log.i("wang==u===", gson1.toJson(userPhoneBO));
            issendPhone = true;
            mDevicePresenter.hardWareMess(entry);

        }

    }

    private void getLocation() {
        locationPresener.onCreateGPS();
    }

    private boolean isSendInfo = false;

    private void sendInfo() {
        if (onePermissionOK && twoPermissionOk && !isSendInfo) {
            UserPhoneSmsDTO.UserPhoneSmsBO sms = ContactUtil.readFirstSms(mContext);
            String earliestSms = sms.getSmsTime();
            UserPhoneCallDTO.UserPhoneCallBO callBO = ContactUtil.readFirstReadCallLog(mContext);
            String earliestCall = callBO.getCallStartTime();
            String clipStr = DeviceUtil.getClip(mContext);
            UserPhoneAddCodeBO userPhoneAddCodeBO = new UserPhoneAddCodeBO();
            String userId = "";
            if (LoginContext.getLoginContext().getState() instanceof LoginState
                    && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
            }
            userPhoneAddCodeBO.setUserId(userId);
            userPhoneAddCodeBO.setClipboard(clipStr);
            userPhoneAddCodeBO.setEarliestCall(earliestCall);
            userPhoneAddCodeBO.setEarliestSms(earliestSms);
            Gson gson = new Gson();
            isSendInfo = true;
            Log.i("wang==u===", gson.toJson(userPhoneAddCodeBO));
            String entry = DESCoder.encryData(gson.toJson(userPhoneAddCodeBO));
            mDevicePresenter.getNewCode(entry);
        }

    }

    private boolean isSendCount = false;

    private void setCount() {
        if (oneCountPermissionOK && twoCountPermissionOk && !isSendCount) {
            mDevicePresenter.getMessAndCallCount(ContactUtil.readSmsSize(mContext) + "", ContactUtil.getCallLogSize(mContext) + "");
            isSendCount = true;
        }
    }

    private String getSuccess() {
        CallBackBean callBackBean = new CallBackBean();
        callBackBean.setCode(0);
        callBackBean.setMessage("success");
        Gson gson = new Gson();
        Log.i("wang==========", gson.toJson(callBackBean));
        return gson.toJson(callBackBean);
    }

    private String getFail(int code, String message) {
        CallBackBean callBackBean = new CallBackBean();
        callBackBean.setCode(1);
        callBackBean.setMessage(message);
        Gson gson = new Gson();
        Log.i("wang==========", gson.toJson(callBackBean));
        return gson.toJson(callBackBean);
    }

    private String getFailPermissionDeny() {
        CallBackBean callBackBean = new CallBackBean();
        callBackBean.setCode(1);
        callBackBean.setMessage("no permission");
        Gson gson = new Gson();
        Log.i("wang==========", gson.toJson(callBackBean));
        return gson.toJson(callBackBean);
    }

    @Override
    public void faceidSendSuccess() {
        mCallback.onCallBack("success");
    }

    @Override
    public void faceidFail(int s) {
        mCallback.onCallBack(getResources().getString(s));
    }


    private class MyWebChromeClient extends WebChromeClient {
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE_ARRAY);
            return true;
        }

        //
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String mtitle) {
            super.onReceivedTitle(view, mtitle);
//            setTitle(mtitle);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //     webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
            //    webView.loadUrl("javascript:historyBack()");
            if (misOver == 1) {
//                Constant.REFRESH_ORDER = 1;
                Intent intent = new Intent(WebViewActivity.this, MainActivityKotlin.class);
                //      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                setResult(RESULT_OK);
                WebViewActivity.this.finish();
            }
        }
        return true;
    }


    public void syncCookie(String url, String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, "PSClient=" + cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        TextUtils.isEmpty(newCookie);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PHOTO_REQUEST_CAMERA) {//取消裁剪功能，暂时不用
            if (resultCode == Activity.RESULT_OK) {
                //    startPhotoZoom(uri);
                getImageToView(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    ToastUtil.showToast(getResources().getString(R.string.select_file_error));
                    return;
                }

                Uri selectedImage = data.getData();
                if (selectedImage == null) {
                    ToastUtil.showToast(getResources().getString(R.string.select_file_error));
                    return;
                } else {
                    getImageToView(data.getData());
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            //webView.loadUrl("javascript:notifyImg('" + "cancelCamera" + "')");
            if (mCallback != null) {
                CamaraParam param = new CamaraParam();
                param.setCode(Constant.REQUEST_CANCEL);
                mCallback.onCallBack(GsonUtil.buildGosn().toJson(param));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /* 保存裁剪之后的图片数据
     * @param picdata
     */
    private void getImageToView(Uri uri) {
        Bitmap photo;
        try {
            photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            saveImage(photo, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveImage(Bitmap bm, final String taskId) {
        try {
            imageFullPath = path_icon + "/1headurl_" + "_temp" + System.currentTimeMillis() + ".jpg";//保存头像在本地
            Log.d("imageFullPathdd", imageFullPath);
            ImageUtil.saveBitmap(bm, imageFullPath);
            String size = FileUtils.getFileSizeByUrl(imageFullPath);
            camaraParam.setPictureTrueSize(size);
            camaraParam.setPictureUri(imageFullPath);
            if (tempName != null && new File(tempName).exists()) {
                new File(tempName).delete();
            }
            final File uploadFile = new File(imageFullPath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
                try {
                    uploadFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Luban.with(this).load(uploadFile).ignoreBy(100).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    DebugLog.i("wang", "====onStart==");
                }

                @Override
                public void onSuccess(File file) {
                    uploadFile.delete();
                    DebugLog.i("wang", "====file==" + file.getAbsolutePath());
                    ByteArrayOutputStream jpeg_data = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    String resolutionCap = bitmap.getWidth() + "*" + bitmap.getHeight();
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, jpeg_data)) {
                        byte[] code = jpeg_data.toByteArray();
                        byte[] output = Base64.encode(code, Base64.NO_WRAP);
                        String js_out = new String(output);
                        int PhotoresolutionCap = bitmap.getWidth() / bitmap.getHeight();
                        camaraParam.setPhotoResolutionRatio(PhotoresolutionCap + "");
                        camaraParam.setResolutionCap(resolutionCap);
                        camaraParam.setCode(Constant.CALL_BACK_SUESSCODE);
                        camaraParam.setImageBase64(js_out);
                        camaraParam.setTaskId(taskId);
                        DebugLog.i("wang", "===camaraParam====" + GonsonUtil.buildGosn().toJson(camaraParam));
                        mCallback.onCallBack(GonsonUtil.buildGosn().toJson(camaraParam));
                    }
                    DebugLog.i("wang", "===size====" + file.getAbsolutePath());
                }

                @Override
                public void onError(Throwable e) {
                    DebugLog.i("wang", "====error==" + e.toString());
                }
            }).launch();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
        }
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        mDevicePresenter = new DevicePresenter();
        locationPresener = new LocationPresener();
        deviceFingurePresenter = new DeviceFingurePresenter();
        detectLivenessPresenter = new DetectLivenessPresenter();
        BasePresenter[] presenters = {mDevicePresenter, locationPresener, detectLivenessPresenter, deviceFingurePresenter};
        return presenters;
    }

    private Disposable disposable;

    //人脸识别
    public void goFaceId() {
        rxPermissions.requestEach(Manifest.permission.CAMERA).subscribe(new CusumeObserver<Permission>() {
            @Override
            public void onNext(Permission permission) {
                super.onNext(permission);
                if (permission.granted) {
                    OctopusLivenessManager.getInstance().detectLiveness(WebViewActivity.this, new OctopusLivenessCallback() {
                        @Override
                        public void onCallback(int i, OctopusLivenessInfo info) {
                            if (i == 0) {
                                Log.d("verificationPackage", info.verificationPackage);
                                Log.d("verificationPackageFull", info.verificationPackageFull);
                                Log.d("verificatidddd", info.verificationPackageWithFanpaiFull);
//                                DebugMessage.put("Package", info.verificationPackage);
//                                DebugMessage.put("PackageFull", info.verificationPackageFull);
//                                DebugMessage.put("PackageWithFanpaiFull", info.verificationPackageWithFanpaiFull);
                                DetectLivenessRequest detectLivenessRequest = new DetectLivenessRequest();
                                detectLivenessRequest.setVerificationPackage(info.verificationPackage);
                                detectLivenessRequest.setVerificationPackageFull(info.verificationPackageFull);
                                detectLivenessRequest.setVerificationPackageWithFanpaiFull(info.verificationPackageWithFanpaiFull);
                                detectLivenessPresenter.update(detectLivenessRequest);
                            }

                        }
                    });
                } else {
                    mCallback.onCallBack("failure");
                }
            }
        });
    }

    private void getDeviceDetail() {
        final UserDeviceDetailVO userDeviceDetailVO = new UserDeviceDetailVO();
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
        Log.d("blackboxblackbox", "1111");
        if (ActivityCompat.checkSelfPermission(MyApplication.mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("blackboxblackbox", "2222");
            RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
            Disposable disposable = rePermissions.request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        userDeviceDetailVO.setImei(DeviceUtil.getIMEI());//权限
                        Gson gson = new Gson();
                        Log.i("wang==u===", gson.toJson(userDeviceDetailVO));
                        mDevicePresenter.getDeviceDetail(userDeviceDetailVO);
                        getDeviceTongdun();
                    } else {
                        //只要有一个权限被拒绝，就会执行
                        mCallback.onCallBack(getFailPermissionDeny());
                    }
                }
            });
        } else {
            userDeviceDetailVO.setImei(DeviceUtil.getIMEI());//权限
            Gson gson = new Gson();
            Log.i("wang==u===", gson.toJson(userDeviceDetailVO));
            mDevicePresenter.getDeviceDetail(userDeviceDetailVO);

            RxPermissions rePermissions = new RxPermissions(WebViewActivity.this);
            Disposable disposable = rePermissions.request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        getDeviceTongdun();
                    } else {
                        mCallback.onCallBack(getFailPermissionDeny());
                    }
                }
            });

        }
    }

    private void getDeviceTongdun() {
        Map<String, Object> options = new HashMap<>();
        options.put(FMAgent.OPTION_DOMAIN, "https://idfptest.tongdun.net");
        options.put(FMAgent.OPTION_BLACKBOX_MAXSIZE, 5 * 1024);
        try {
            FMAgent.initWithCallback(WebViewActivity.this, FMAgent.ENV_PRODUCTION, options, new FMCallback() {
                @Override
                public void onEvent(String blackbox) {
                    deviceFingurePresenter.sendDeviceFingure(blackbox);
                }
            });
        } catch (FMException e) {
            e.printStackTrace();
        }
    }

    public void setGetCameraPix() {
        DebugLog.i("wang", "====开始获取像素=====");
        listBack = CameraUtils.getBackCamera();
        listFont = CameraUtils.getFontCamera();
        List<CamaraParam.CameraRear> listCameraRear = new ArrayList<>();
        List<CamaraParam.CameraFront> listCameraFront = new ArrayList<>();


        if (listFont != null && listFont.size() > 0) {
            for (int i = 0; i < listFont.size(); i++) {
                CamaraParam.CameraFront camaraFront = new CamaraParam.CameraFront();
                camaraFront.setCameraId(listFont.get(i));
                camaraFront.setCameraFrontCameraPixel(CameraUtils.getCameraPixels(listFont.get(i)));
                listCameraFront.add(camaraFront);
            }
        }
        if (listBack != null && listBack.size() > 0) {
            for (int i = 0; i < listBack.size(); i++) {
                CamaraParam.CameraRear cameraRear = new CamaraParam.CameraRear();
                cameraRear.setCameraId(listBack.get(i));
                cameraRear.setCameraRearCameraPixel(CameraUtils.getCameraPixels(listBack.get(i)));
                listCameraRear.add(cameraRear);
            }
        }

        camaraParam.setCameraRearList(listCameraRear);
        camaraParam.setCameraFrontList(listCameraFront);
        camaraParam.setCameraFrontNumber(listFont.size());
        camaraParam.setCameraRearNumber(listBack.size());

        MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);
        mMkv.encode(MMKV_CAMERA_CAMERAFRONTNUMBER, listFont.size());
        mMkv.encode(MMKV_CAMERA_CAMERAREARNUMBER, listBack.size());
        mMkv.encode(MMKV_CAMERA_CAMERAREARCAMERAPIXEL, listCameraRear.get(0).getCameraRearCameraPixel());
        mMkv.encode(MMKV_CAMERA_CAMERAFRONTCAMERAPIXEL, listCameraFront.get(0).getCameraFrontCameraPixel());
    }

}
