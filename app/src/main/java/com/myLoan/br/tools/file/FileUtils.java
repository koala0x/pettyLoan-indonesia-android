package com.myLoan.br.tools.file;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.myLoan.br.tools.math.MD5Util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.UUID;

public class FileUtils {

    /**
     * 本地与我们应用程序相关文件存放的根目录
     */
    private static final String ROOT_DIR_PATH = "/MonneyManger";

    /**
     * 下载图片文件存放的目录
     */
    private static final String IMAGE_DOWNLOAD_IMAGES_PATH = "/Download/Images/";

    /**
     * 获取下载图片文件存放的目录
     *
     * @param context Context
     * @return SDCard卡或者手机内存的根路径
     */
    public static String getImageDownloadDir(Context context) {
        return getRootDir(context) + IMAGE_DOWNLOAD_IMAGES_PATH;
    }

    /**
     * 获取SDCard卡或者手机内存的根路径（优先获取SDCard卡的根路径）
     *
     * @param context Context
     * @return SDCard卡或者手机内存的根路径
     */
    public static String getRootDir(Context context) {
        String rootDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + ROOT_DIR_PATH;
        } else {
            rootDir = context.getApplicationContext().getCacheDir().getAbsolutePath() + ROOT_DIR_PATH;
        }
        return rootDir;
    }

    /**
     * 清楚下载的临时图片
     *
     * @return
     */
    public static boolean clearDownTmp(Context context) {
        String imageRootDir = getImageDownloadDir(context);
        File dir = new File(imageRootDir);
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                f.delete();
            }
            return true;
        }
        return false;
    }

    /**
     * 随机生成一个文件，用于存放下载的图片
     *
     * @param context Context
     * @return
     */
    public static String getImageDownloadPath(Context context) {
        String imageRootDir = getImageDownloadDir(context);
        File dir = new File(imageRootDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + ".jpg";
        return dir + File.separator + fileName;
    }

    /**
     * 获取下载文件在本地存放的路径
     *
     * @param context
     * @param url
     * @return
     */
    public static String getImageDownloadPath(Context context, String url) {
        if (url.startsWith("/")) {
            return url;
        }

        String fileName = getFileName(url);
        String imageRootDir = getImageDownloadDir(context);
        File dir = new File(imageRootDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir + File.separator + MD5Util.md5(fileName) + ".temp";
    }

    /**
     * 根据url获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        String fileName = url.substring(url.lastIndexOf(File.separator) + 1);
//        MLog.i("fileName = " + fileName);
        return fileName;
    }

    /**
     * 检查本地是否存在某个文件
     *
     * @param filePath
     * @return
     */
    public static boolean exists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static String getFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.0");
        String fileSizeString;
        if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

   /* public static HashMap<Integer, Double> getNetFileSizeDescription(long size) {
        //  StringBuffer bytes = new StringBuffer();
        HashMap<Integer,Double> map=new HashMap<>();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            map.put(Constant.G_TYPE,i);
            //  bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            map.put(Constant.M_TYPE,i);
            //     bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            map.put(Constant.K_TYPE,i);
            //    bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                //         bytes.append("0B");
                map.put(Constant.B_TYPE,0.00);
            } else {
                //            bytes.append((int) size).append("B");
                map.put(Constant.B_TYPE, (double) size);
            }
        }
        return map;
    }*/
   public static String getFileSizeByUrl(Uri uri) {
       File file = null;
       try {
           file = new File(new URI(uri.toString()));
           if (file.exists() && file.isFile()) {
               return getPrintSize(file.length());
           }

       } catch (URISyntaxException e) {
           e.printStackTrace();
       }
       return "0";
   }

    public static String getFileSizeByUrl(String fil) {
        File file = new File(fil);
        if (file.exists() && file.isFile()) {
            return getPrintSize(file.length());
        }
        return "0";
    }
    public static String getPrintSize(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();

    }
}
