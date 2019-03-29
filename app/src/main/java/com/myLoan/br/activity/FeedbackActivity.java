package com.myLoan.br.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myLoan.br.R;
import com.myLoan.br.base.BaseActivity;
import com.myLoan.br.base.BasePresenter;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.listener.IFeedbackView;
import com.myLoan.br.presenter.HelpCenterPresenter;
import com.myLoan.br.tools.file.FileUtils;
import com.myLoan.br.tools.file.ImageUtil;
import com.myLoan.br.tools.math.MD5Util;
import com.myLoan.br.tools.view.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FeedbackActivity extends BaseActivity implements IFeedbackView {
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private static final int PHOTO_REQUEST_GALLERY_ADD = 3;
    private static final int PHOTO_REQUEST_GALLERY_PIC1 = 4;
    private static final int PHOTO_REQUEST_GALLERY_PIC2 = 5;
    private static final int PHOTO_REQUEST_GALLERY_PIC3 = 6;
    private static final int PHOTO_REQUEST_GALLERY_PIC4 = 7;
    private static final int PHOTO_REQUEST_GALLERY_PIC5 = 8;
    private static final int PHOTO_REQUEST_GALLERY_PIC6 = 9;
    ImageView image01;
    ImageView image02;
    ImageView image03;
    ImageView image04;
    ImageView image05;
    ImageView image06;
    private HelpCenterPresenter helpCenterPresenter;

    EditText edittext;
    TextView tvLimit;
    TextView tvAdd;

    private RxPermissions rxPermissions;
    private Uri uritempFile;
    List<File> photoFiles;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initListener() {
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvLimit.setText(edittext.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void initView() {
        image01 = findViewById(R.id.image_01);
        image02 = findViewById(R.id.image_02);
        image03 = findViewById(R.id.image_03);
        image04 = findViewById(R.id.image_04);
        image05 = findViewById(R.id.image_05);
        image06 = findViewById(R.id.image_06);
        edittext = findViewById(R.id.edittext);
        tvLimit = findViewById(R.id.tv_limit);
        tvAdd = findViewById(R.id.tv_add);
        image01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        image02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        image03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        image04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        image05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        image06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        tvLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked(v);
            }
        });
        setTitle(getResources().getString(R.string.feedback));
    }

    @Override
    public void intData() {
        rxPermissions = new RxPermissions(this);
        photoFiles = new ArrayList<>();
    }

    @Override
    protected BasePresenter[] oncreatePresenter() {
        helpCenterPresenter = new HelpCenterPresenter();
        BasePresenter prestener[] = {helpCenterPresenter};
        return prestener;
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_add:
                getGalley(PHOTO_REQUEST_GALLERY_ADD);
                break;
            case R.id.tv_option:
//                photoFiles.add()
                helpCenterPresenter.feedback("11111", "22222", "wwww", photoFiles);
                break;
            case R.id.image_01:
                getGalley(PHOTO_REQUEST_GALLERY_PIC1);
                break;
            case R.id.image_02:
                getGalley(PHOTO_REQUEST_GALLERY_PIC2);
                break;
            case R.id.image_03:
                getGalley(PHOTO_REQUEST_GALLERY_PIC3);
                break;
            case R.id.image_04:
                getGalley(PHOTO_REQUEST_GALLERY_PIC4);
                break;
            case R.id.image_05:
                getGalley(PHOTO_REQUEST_GALLERY_PIC5);
                break;
            case R.id.image_06:
                getGalley(PHOTO_REQUEST_GALLERY_PIC6);
                break;
            case R.id.linear_add2:
                getGalley(PHOTO_REQUEST_GALLERY_ADD);
                break;


        }
    }

    private void getGalley(final int requst) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new CusumeObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
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
                    startActivityForResult(photointent, requst);
                } else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_GALLERY_ADD:

                    break;
                case PHOTO_REQUEST_GALLERY_PIC1:
                    break;
                case PHOTO_REQUEST_GALLERY_PIC2:
                    break;
                case PHOTO_REQUEST_GALLERY_PIC3:
                    break;
                case PHOTO_REQUEST_GALLERY_PIC4:
                    break;
                case PHOTO_REQUEST_GALLERY_PIC5:
                    break;
                case PHOTO_REQUEST_GALLERY_PIC6:
                    break;


            }

            if (data == null) {
                ToastUtil.showToast(getResources().getString(R.string.select_file_error));
                return;
            }

            Uri selectedImageUri = data.getData();
            if (selectedImageUri == null) {
                ToastUtil.showToast(getResources().getString(R.string.select_file_error));
                return;
            } else {
//                    startPhotoZoom(data.getData(), false);
                try {

//
                    displayPicture(selectedImageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setPic(Intent data, ImageView imageView) {

    }

    private String imageFullPath = null;    //图片地址

    private void displayPicture(Uri uri) {
        Bitmap photo;
        try {
            String path_icon = FileUtils.getRootDir(FeedbackActivity.this);
            photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            imageFullPath = path_icon + MD5Util.md5("helpcenterurl_" + "_temp") + System.currentTimeMillis() + ".jpg";//保存在本地

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ImageUtil.saveBitmapOther(bitmap, imageFullPath, 50);
            File uploadFile = new File(imageFullPath);
            photoFiles.add(uploadFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void feedbackSuccess() {

    }

    @Override
    public void feedbackFail(String massage) {

    }

}

/*
 * 裁剪图片方法实现
 * @param uri
 */
//    public void startPhotoZoom(Uri uri, boolean isRotate) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", DensityUtil.dip2px(this, 150));
//        intent.putExtra("outputY", DensityUtil.dip2px(this, 150));
//        intent.putExtra("scale", true);//黑边
//        intent.putExtra("scaleUpIfNeeded", true);//黑边
//        /*这段代码不兼容7.0以上android系统
//        intent.putExtra("return-data", true);
//		startActivityForResult(intent, 4);*/
//        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "small.jpg");
//        if (!TextUtils.isEmpty(tempName) && isRotate) {
//            int degree = ImageUtil.readPictureDegree(tempName);
//            if (!(degree == 0 || degree == 360)) {
//                Bitmap newBitmap = ImageUtil.rotateBitmapByDegree(ImageUtil.getBitmap(tempName), degree);
//                ImageUtil.saveBitmap(newBitmap, tempName);
//            }
//        }
//        DebugLog.i("wang", "====" + tempName);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        startActivityForResult(intent, 4);
//    }
