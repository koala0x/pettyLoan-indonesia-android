package com.megvii.livenesslib;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DebugMessage {

    public static void put(String name, String s) {
        try {
            String fileAdd = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name + ".txt";
            FileOutputStream outStream = new FileOutputStream(fileAdd, true);
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            writer.write(s);
            writer.flush();
            writer.close();//记得关闭

            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createFileWithByte(byte[] bytes, String fileName) {
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File appFiles = new File(Environment.getExternalStorageDirectory(),
                "/app/");
        if (!appFiles.exists()) {
            appFiles.mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory(),
                "/app/" + fileName);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

}
