package com.myLoan.br.tools.phonestatus;

import android.hardware.Camera;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class CameraUtils {
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_NONE = 2;
    //
    public static List<Integer> getFontCamera(){
        int numberOfCameras = Camera.getNumberOfCameras();
        List<Integer> fontNumList=new ArrayList<Integer>();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                Log.i("wang","===back=="+i);
                fontNumList.add(i);
//                return fontNumList;
            }
        }
        return fontNumList;
    }
    //
    public static List<Integer> getBackCamera(){
        int numberOfCameras = Camera.getNumberOfCameras();
        List<Integer> fontNumList=new ArrayList<Integer>();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_BACK) {
                Log.i("wang","===font=="+i);
                fontNumList.add(i);
//                return fontNumList;
            }
        }
        return fontNumList;
    }

    public static String getCameraPixels(int paramInt)
    {
        Log.i("wang","==camare=="+paramInt);
        Camera localCamera = Camera.open(paramInt);
        Camera.Parameters localParameters = localCamera.getParameters();
        localParameters.set("camera-id", 1);
        List<Camera.Size> localList = localParameters.getSupportedPictureSizes();
        if (localList != null)
        {
            int heights[] = new int[localList.size()];
            int widths[] = new int[localList.size()];
            for (int i = 0; i < localList.size(); i++)
            {
                Camera.Size size = (Camera.Size) localList.get(i);
                int sizehieght = size.height;
                int sizewidth = size.width;
                heights[i] = sizehieght;
                widths[i] =sizewidth;
            }
            int pixels = getMaxNumber(heights) * getMaxNumber(widths);
            localCamera.release();
            return String.valueOf(pixels / 10000);

        }
        else return "0";
    }
    public static int getMaxNumber(int[] paramArray)
    {
        int temp = paramArray[0];
        for(int i = 0;i<paramArray.length;i++)
        {
            if(temp < paramArray[i])
            {
                temp = paramArray[i];
            }
        }
        return temp;
    }
}
