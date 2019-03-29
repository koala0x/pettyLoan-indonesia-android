package com.myLoan.br.bean;

import java.util.List;

public class CamaraParam {
    private int code;
    private String imageBase64;
    private String pictureTrueSize;
    private String pictureUri;
    private String resolutionCap;
    private String photoResolutionRatio;
    private int cameraFrontNumber;
    private int cameraRearNumber;
    private String taskId;
    private List<CameraRear> CameraRearList;
    private List<CameraFront> CameraFrontList;


    public String getPhotoResolutionRatio() {
        return photoResolutionRatio;
    }

    public void setPhotoResolutionRatio(String photoResolutionRatio) {
        this.photoResolutionRatio = photoResolutionRatio;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }


    public String getPictureTrueSize() {
        return pictureTrueSize;
    }

    public void setPictureTrueSize(String pictureTrueSize) {
        this.pictureTrueSize = pictureTrueSize;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public String getResolutionCap() {
        return resolutionCap;
    }

    public void setResolutionCap(String resolutionCap) {
        this.resolutionCap = resolutionCap;
    }

    public int getCameraFrontNumber() {
        return cameraFrontNumber;
    }

    public void setCameraFrontNumber(int cameraFrontNumber) {
        this.cameraFrontNumber = cameraFrontNumber;
    }

    public int getCameraRearNumber() {
        return cameraRearNumber;
    }

    public void setCameraRearNumber(int cameraRearNumber) {
        this.cameraRearNumber = cameraRearNumber;
    }

    public List<CameraRear> getCameraRearList() {
        return CameraRearList;
    }

    public void setCameraRearList(List<CameraRear> cameraRearList) {
        CameraRearList = cameraRearList;
    }

    public List<CameraFront> getCameraFrontList() {
        return CameraFrontList;
    }

    public void setCameraFrontList(List<CameraFront> cameraFrontList) {
        CameraFrontList = cameraFrontList;
    }

    public static class CameraRear {
        private int cameraId;
        private String cameraRearCameraPixel;

        public int getCameraId() {
            return cameraId;
        }

        public void setCameraId(int cameraId) {
            this.cameraId = cameraId;
        }

        public String getCameraRearCameraPixel() {
            return cameraRearCameraPixel;
        }

        public void setCameraRearCameraPixel(String cameraRearCameraPixel) {
            this.cameraRearCameraPixel = cameraRearCameraPixel;
        }
    }

    public static class CameraFront {
        private int cameraId;
        private String cameraFrontCameraPixel;

        public int getCameraId() {
            return cameraId;
        }

        public void setCameraId(int cameraId) {
            this.cameraId = cameraId;
        }

        public String getCameraFrontCameraPixel() {
            return cameraFrontCameraPixel;
        }

        public void setCameraFrontCameraPixel(String cameraFrontCameraPixel) {
            this.cameraFrontCameraPixel = cameraFrontCameraPixel;
        }
    }
}
