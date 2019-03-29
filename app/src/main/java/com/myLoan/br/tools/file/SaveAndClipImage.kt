package com.myLoan.br.tools.file

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.core.app.ActivityCompat.startActivityForResult
import com.myLoan.br.activity.MainActivityKotlin
import com.myLoan.br.presenter.UserPresenter
import com.myLoan.br.tools.DebugLog
import com.myLoan.br.tools.math.MD5Util
import com.myLoan.br.tools.view.DensityUtil
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.io.FileNotFoundException

class SaveAndClipImage {
    private var tempName: String? = null                //临时照片文件路径
    private var uri: Uri? = null
    private var uritempFile: Uri? = null
    private var imageFullPath: String? = null    //图片地址

    fun openCamera(activity: Activity) {
        val path_icon = activity.filesDir.absolutePath
        imageFullPath = path_icon + MD5Util.md5("headurl_" + "_temp") + System.currentTimeMillis() + ".jpg"//保存头像在本地

        val cameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val path_icon = FileUtils.getRootDir(activity)
            tempName = path_icon + System.currentTimeMillis() + "_temp.jpg"
            /*这段代码不兼容7.0以上android系统
                uri = Uri.fromFile(new File(tempName));
            */
            val contentValues = ContentValues(1)
            contentValues.put(MediaStore.Images.Media.DATA, tempName)
            uri = activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            activity.startActivityForResult(cameraintent, MainActivityKotlin.PHOTO_REQUEST_CAMERA)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    public fun startPhotoZoom(uri: Uri? = this.uri, isRotate: Boolean = true, context: Activity) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 设置裁剪
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", DensityUtil.dip2px(context, 150f))
        intent.putExtra("outputY", DensityUtil.dip2px(context, 150f))
        intent.putExtra("scale", true)//黑边
        intent.putExtra("scaleUpIfNeeded", true)//黑边
        /*这段代码不兼容7.0以上android系统
        intent.putExtra("return-data", true);
		startActivityForResult(intent, 4);*/
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().path + "/" + System.currentTimeMillis() + "small.jpg")
        if (!TextUtils.isEmpty(tempName) && isRotate) {
            val degree = ImageUtil.readPictureDegree(tempName)
            if (!(degree == 0 || degree == 360)) {
                val newBitmap = ImageUtil.rotateBitmapByDegree(ImageUtil.getBitmap(tempName), degree)
                ImageUtil.saveBitmap(newBitmap, tempName)
            }
        }
        DebugLog.i("wang", "====" + tempName!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        context.startActivityForResult(intent, 4)
    }

    fun getImageToView(data: Intent?, activity: Activity, userPresenter: UserPresenter) {
        val photo: Bitmap
        try {
            photo = BitmapFactory.decodeStream(activity.contentResolver.openInputStream(uritempFile!!))
            try {
                ImageUtil.saveBitmap(photo, imageFullPath)
                if (tempName != null && File(tempName).exists()) {
                    File(tempName).delete()
                }
                val uploadFile = File(imageFullPath)
                Luban.with(activity).load(uploadFile).ignoreBy(100).setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        DebugLog.i("wang", "====onStart==")
                    }

                    override fun onSuccess(file: File) {
                        DebugLog.i("wang", "===size====" + file.absolutePath)
                        userPresenter!!.uploadHead(file)
                    }

                    override fun onError(e: Throwable) {
                        DebugLog.i("wang", "====error==$e")
                    }
                }).launch()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (photo != null && !photo.isRecycled) {
                    photo.recycle()
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}