package com.myLoan.br.tools.file;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;


/**
 * @author song
 * @version 创建时间：2012-9-20 上午9:12:11
 * 类说明
 */
public class ImageUtil {
	private final static int MB = 1024 * 1024;
	private final static int CACHE_SIZE = 8 * MB;
	public final static int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
	private final static long OVER_TIME = 36000;
/*	public final static String SD_ROOT_PATH_ICON = Start.SD_ROOT+"icon/";
	public final static String SD_ROOT_PATH_ITEM = Start.SD_ROOT+"item/";*/
	
	public static Bitmap getImageFromUrlOther(URL url){
		InputStream inStream = null;
		try{
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();     
	        conn.setConnectTimeout(5 * 1000);     
	        conn.setRequestMethod("GET");     
	        inStream = conn.getInputStream();     
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){     
	        	byte[] data = readStream(inStream);
	        	if( data !=null){
	        		return BitmapFactory.decodeByteArray(data, 0, data.length);
	        	}    
	        }  
		}catch(Exception  e){
			e.printStackTrace();
		}finally{
			if(inStream != null){
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null; 
	}

	public static BitmapDrawable getImageFromUrl(URL url) {
	
		BitmapDrawable icon = null;
		InputStream inStream = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);     
	        conn.setRequestMethod("GET");
	        
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
	        	inStream = conn.getInputStream();
	        	icon = new BitmapDrawable(inStream);
	        }
			conn.disconnect();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(inStream != null){
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
		return icon;
	
	}
	
	//从网络获取图片， 优化引起内存溢出；  add by lrx 2012-12-27
	public static Bitmap getImageFromUrlScale(URL url){
		Bitmap bitmap = null;  
		InputStream is = null;
		try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5 * 1000);  
            is = conn.getInputStream();  

            BitmapFactory.Options options = new BitmapFactory.Options();
        	
    		options.inPreferredConfig = Bitmap.Config.RGB_565;
    		options.inPurgeable = true; //可被回收
    		options.inInputShareable = true; //设置解码位图的尺寸信息
    		bitmap = BitmapFactory.decodeStream(is, null, options); 
            
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}
	
	public static void CopyStream(InputStream is, OutputStream os) {  
        final int buffer_size = 1024;  
        try {  
            byte[] bytes = new byte[buffer_size];  
            for (;;) {  
                int count = is.read(bytes, 0, buffer_size);  
                if (count == -1)  
                    break;  
                os.write(bytes, 0, count);  
            }  
        } catch (Exception ex) {  
           
        }  
    }  
	

	private static byte[] readStream(InputStream inStream) throws Exception{     
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();     
	    byte[] buffer = new byte[1024];     
	    int len = 0;     
	    while( (len=inStream.read(buffer)) != -1){     
	        outStream.write(buffer, 0, len);     
	    }     
	    outStream.close();     
	    inStream.close();     
	    return outStream.toByteArray();     
	}   


	/**
	 * 初始化相关缓存图片的保存路径(文件夹路径)
	 * @param SD_Path SD卡的路径
	 * @param Rom_Path 非SD卡路径
	 * @return
	 * @throws
	 */
	public static String initImagePath(String SD_Path, String Rom_Path) /*throws ImagePathException*/{
		//System.out.println("SD_Path::"+SD_Path+"====Rom_Path::"+Rom_Path);
		String return_path;
		if( SD_Path.trim().equals("") || Rom_Path.trim().equals("")){
			//throw new ImagePathException("初始化图片的保存文件路径传入的参数有误");
		}
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//System.out.println("有sd卡");
			File f = Environment.getExternalStorageDirectory();
			
			makeDirOnSD(f.getPath(),SD_Path.substring(1,SD_Path.length()-1));
			StringBuffer path = new StringBuffer();
			//path.append(f.getPath()).append("/vgirl/");
			path.append(f.getPath()).append(SD_Path);
			File dir = new File(path.toString());
			if (!dir.exists()) {
				dir.mkdir();
			}
			return_path = path.toString();
		} else {
			//System.out.println("没有SD卡");
			//File dir = new File("/sdcard/.vgirl/");
			File dir = new File(Rom_Path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			//return_path = "/sdcard/.vgirl/";
			//System.out.println("SD_Path:"+SD_Path+",Rom_Path:"+Rom_Path);
			return_path = Rom_Path;
		}
		return return_path;
	}
	
	/**
	 * 初始化相关缓存图片的保存路径(文件夹路径)
	 * @param Rom_Path 本地路径
	 * @return
	 * @throws
	 */
	public static String initImagePath(String Rom_Path) throws Exception{
		
		if(Rom_Path.trim().equals("")){
			throw new Exception("初始化图片的保存文件路径传入的参数有误");
		}
		
		File dir = new File(Rom_Path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		return Rom_Path;
	}

	/**
	 * 创建绝对路径(包含多级) 
	 * @param header 绝对路径的前半部分(已存在) 
	 * @param tail 绝对路径的后半部分(第一个和最后一个字符不能是/，格式：123/258/456)
	 */
	public static void makeDirOnSD(String header, String tail) {
		//System.out.println("header::"+header+"====tail::"+tail);
	    String[] sub = tail.split("/");  
	    File dir = new File(header);  
	    for (int i = 0; i < sub.length; i++) {  
	        if (!dir.exists()) {  
	            dir.mkdir();  
	        }  
	        File dir2 = new File(dir + File.separator + sub[i]);  
	        if (!dir2.exists()) {  
	            dir2.mkdir();  
	        }  
	        dir = dir2;  
	    }  
	}

	/**
	 * 保存图片，以100%的质量保存
	 * @param bitmap 图片资源	
	 * @param picPath 保存路径
	 */
	public static void saveBitmap(Bitmap bitmap, String picPath) {
		//System.out.println("++"+(bitmap==null));
		if( bitmap == null || picPath ==null){
			return;
		}
		//System.out.println("====saveBitmap----"+picPath);
		try{
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//清理目录
				ImageUtil w = new ImageUtil();
				w.removeCache(picPath);
				if(FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSD()){
					return;
				}
			}
			File file = new File(picPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
					out.flush();
					out.close();
				}
				// if ((bitmap != null) && (!bitmap.isRecycled()))
				// bitmap.recycle();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			//System.out.println("保存图片出错");
		}
		
	}

	/**
	 * 保存图片,以quality%的质量保存
	 * @param bitmap
	 * @param picPath
	 */
	public static void saveBitmapOther(Bitmap bitmap, String picPath,int quality) {
		//System.out.println("++"+(bitmap==null));
		if( bitmap == null || picPath ==null){
			return;
		}
		//System.out.println("====saveBitmap----"+picPath);
		try{
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				//清理目录
				ImageUtil w = new ImageUtil();
				w.removeCache(picPath);
				//判断SDCARD上的空间
				//System.out.println("space==="+freeSpaceOnSD());
				if(FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSD()){
					//System.out.println("空间小于10");
					return;
				}
			}
			File file = new File(picPath);
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
					out.flush();
					out.close();
				}
				// if ((bitmap != null) && (!bitmap.isRecycled()))
				// bitmap.recycle();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
			//System.out.println("保存图片出错");
		}
		
	}
	
	/**
	 * 获取图片
	 * @param path 图片的保存路径
	 * @return
	 */
	public static Bitmap getBitmap(String path){
	//	if( path ==null){
	//		return null;
	//	}
	//	File f = new File(path);
	//	if(!f.exists()){
	//		return null;
	//	}
		Bitmap b = null;
		try{
			b = BitmapFactory.decodeFile(path);
		}catch(OutOfMemoryError e)
		{
			System.gc();
			e.printStackTrace();
		} 
		return b;
	}

	/**
	 * 图片加载异常提示框
	 * @param con 上下文
	 * @param isclose 是否关闭当前Activity标志位 true关闭; false不关闭
	 */
	public static void loadImageWrong(final Activity con,final Boolean isclose){
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("提示");
		builder.setMessage("加载图片失败");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {
				
					public void onClick(DialogInterface dialog, int which) {
						if(isclose){
							con.finish();
						}
					}
				});
		builder.create().show();
	}

	/**
	 * 将File转成byte[]
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file){
		if (file == null){
	         return null;
	    }
	     try {
	         FileInputStream stream = new FileInputStream(file);
	         ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
	         byte[] b = new byte[1000];
	         int n;
	         while ((n = stream.read(b)) != -1){
	        	 out.write(b, 0, n);
	         } 
	         stream.close();
	         out.close();
	         return out.toByteArray();
	     }catch (IOException e){
	    	 e.printStackTrace();
	    	 return null;
	     }
	}

	/**
	 * 计算sdcard上的剩余空间
	 * @return
	 */
	public static int freeSpaceOnSD(){
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double)stat.getAvailableBlocks() * (double)stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 删除过期文件
	 * @param dirPath
	 * @param filename
	 */
	public static void removeExpiredCache(String dirPath, String filename){
		if(null == dirPath || null == filename){
			return;
		}
		File file = new File(dirPath, filename);
		if(System.currentTimeMillis() - file.lastModified() > OVER_TIME){
			file.delete();
		}
	}


	/**
	 * 计算存储目录下的文件大小，
	 * 当文件总大小大于规定的cache_size或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
	 * 那么删除40%最近没有被使用的文件
	 * @param dirPath
	 */
	public void removeCache(String dirPath){
		//System.out.println("removeCache");
		
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if(null == files){
			return;
		}
		int dirSize = 0;
		for(int i=0; i<files.length; i++){//未判断多级目录缓存文件
			dirSize += files[i].length();
		}
		if(dirSize > CACHE_SIZE || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSD()){
			int removeFactor = (int)((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifySort());
			
			//clear some file
			for(int i=0; i<removeFactor; i++){
				files[i].delete();
				//System.out.println("removeCache delete file " + files[i].getName());
			}
		}
	}


	class FileLastModifySort implements Comparator<File> {
	
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			}else if(arg0.lastModified() == arg1.lastModified()) {
				return 0;
			}else{
				return -1;      
			}
		}
	
	}
	
	/*
	//使用BitmapFactory.Options的inSampleSize参数来缩放   
    public static Bitmap resizeImage(String path,  
            int width,int height)   
    {  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;//不加载bitmap到内存中   
        //下面两个组合使用
        options.inPurgeable = true; //内在不足可被回收
        options.inInputShareable = true; //设置解码位图的尺寸信息
        BitmapFactory.decodeFile(path,options);   
        int outWidth = options.outWidth;  
        int outHeight = options.outHeight;  
        options.inDither = false;  
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = 1;  
        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0)   
        {  
            //int sampleSize=(outWidth/width+outHeight/height)/2;  
        	int sampleSize = outWidth/width > outHeight/height ? outWidth/width : outHeight/height; //以大的为准
            if(sampleSize < 1){
            	sampleSize = 1;
            }
            options.inSampleSize = sampleSize;  
        }  
      
        options.inJustDecodeBounds = false;
        
		try {
			InputStream is = new FileInputStream(path);
			//decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode
			return BitmapFactory.decodeStream(is, null, options); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
        //return BitmapFactory.decodeFile(path, options);       
    } 
    */
    
    //BitmapFactory.decodeStream  创建位图
    public static Bitmap resizeImage(String path){
    	Bitmap bitmap = null;
    	InputStream is = null;
    	try {
    		
    		BitmapFactory.Options options=new BitmapFactory.Options();
        	is = new FileInputStream(path);
    		//options.inTempStorage = new byte[100 * 1024]; //分配100k缓存
    		options.inPreferredConfig = Bitmap.Config.RGB_565;
    		options.inPurgeable = true; //可被回收
    		options.inInputShareable = true; //设置解码位图的尺寸信息
    		bitmap = BitmapFactory.decodeStream(is, null, options); 
    		
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	
		return bitmap;
    }
    
  //把图片转为正的方向
    public static Bitmap rotaingImage(int angle,Bitmap bitmap){
    	Matrix matrix =  new Matrix();
    	matrix.postRotate(angle);
    	Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizeBitmap;
    }
    /*
     * 读取图片属性：旋转的角度 
     * path 图片绝对路径 
	 * degree旋转的角度
    */
    public static int readPictureDegree(String path){
    	int degree = 0;
    	try {
			ExifInterface exifInterface  =  new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;

			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
		return degree;
    	
    }
    
    //使用Bitmap加Matrix来缩放   
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h)   
    {    
    	int width = bitmap.getWidth();    
    	int height = bitmap.getHeight();    
    	int newWidth = w;    
    	int newHeight = h;    
    	
    	float scaleWidth = ((float) newWidth) / width;    
    	float scaleHeight = ((float) newHeight) / height;    

    	Matrix matrix = new Matrix();    
    	matrix.postScale(scaleWidth, scaleHeight);    
    	// if you want to rotate the Bitmap      
    	// matrix.postRotate(45);      
    	Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,    
    					height, matrix, true);  
    	if(!bitmap.isRecycled()){
    		bitmap.recycle();
    		bitmap = null;
    	}
    	return resizedBitmap;    
    }
    
    //使用Bitmap加Matrix来缩放   [path 图片路径]
    public static Bitmap resizeImage(String path, int w, int h)   
    {    
    	//Bitmap bitmap = resizeImage(path);
    	//Bitmap bitmap = BitmapFactory.decodeFile(path);
    	Bitmap bitmap = decodeFile(path,w,h);
    	int width = bitmap.getWidth();    
    	int height = bitmap.getHeight();    
    	int newWidth = w;    
    	int newHeight = h;    
    	float initScale=(float)0.99;
    	float scaleWidth = ((float) newWidth) / width;    
    	float scaleHeight = ((float) newHeight) / height;
    	
    	if(scaleWidth<1.0||scaleHeight<1.0){
    		initScale = scaleHeight > scaleWidth ? scaleWidth : scaleHeight;
    	}
    	else{
    		return bitmap;
    	}
    	Matrix matrix = new Matrix();    
    	matrix.postScale(initScale, initScale);    
    	// if you want to rotate the Bitmap      
    	// matrix.postRotate(45);
    	
    	Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);    
    	if(!bitmap.isRecycled()){
    		bitmap.recycle();
    		bitmap = null;
    	}
    	return resizedBitmap;    
    }
    
    
    //上传图片
    public static String uploadFile(String sUrl,String upName, String newName, File file){
		String end ="\r\n";
		String twoHyphens ="--";
		String boundary ="*****";
		String res = null;
		try
		{
			URL url =new URL(sUrl);
			HttpURLConnection con=(HttpURLConnection)url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "+ "name=\"" + upName + "\";filename=\"" + newName + "\"" + end);
			ds.writeBytes(end);  
			/* 取得文件的FileInputStream */
			FileInputStream fStream =new FileInputStream(file);
			/* 设置每次写入1024bytes */
			int bufferSize =1024;
			byte[] buffer =new byte[bufferSize];
			int length =-1;
			/* 从文件读取数据至缓冲区 */
			while((length = fStream.read(buffer)) !=-1)
			{
			  /* 将资料写入DataOutputStream中 */
			  ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b =new StringBuffer();
			while( ( ch = is.read() ) !=-1 )
			{
			  b.append( (char)ch );
			}
			/* 将Response显示于Dialog */
			res = b.toString().trim();
			/* 关闭DataOutputStream */
			ds.close();
		} catch(Exception e){
			e.printStackTrace();
		}
      
        return res;
    }
    
    //读本地图片时,防止图片过大，内存溢出
	public static Bitmap readBitMap(int resId,Context con){  
		Bitmap bitmap = null;
		InputStream is = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;   
        opt.inPurgeable = true;  
        opt.inInputShareable = true;  
           //获取资源图片  
        is = con.getResources().openRawResource(resId);  
        bitmap = BitmapFactory.decodeStream(is,null,opt);  
        if(is != null){
        	 try {
     			is.close();
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
        return bitmap; 
    }
	
	public static Bitmap decodeFile(String path,int WIDTH,int HIGHT){
		BitmapFactory.Options o = new BitmapFactory.Options();
		try {
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(path), null, o);

			final int REQUIRED_WIDTH = WIDTH;
			final int REQUIRED_HIGHT = HIGHT;
			int scale = 2;
			while (o.outWidth / scale  >= REQUIRED_WIDTH && o.outHeight / scale >= REQUIRED_HIGHT){
				scale++;
			}
			scale--;
			o.inJustDecodeBounds = false;
			o.inPreferredConfig = Bitmap.Config.RGB_565;
    		o.inPurgeable = true; //可被回收
    		o.inInputShareable = true; //设置解码位图的尺寸信息
			o.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(path), null, o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getBitmapDegree(String path) {
	    int degree = 0;
	    try {
	        // 从指定路径下读取图片，并获取其EXIF信息
	        ExifInterface exifInterface = new ExifInterface(path);
	        // 获取图片的旋转信息
	        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);
	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            degree = 90;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            degree = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            degree = 270;
	            break;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return degree;
	}
	/**
	 * @param degree 旋转角度
	 * @param bitmap 原图片
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
	    Bitmap returnBm = null;
	  
	    // 根据旋转角度，生成旋转矩阵
	    Matrix matrix = new Matrix();
	    matrix.postRotate(degree);
	    try {
	        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
	        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	    } catch (OutOfMemoryError e) {
	    }
	    if (returnBm == null) {
	        returnBm = bm;
	    }
	    if (bm != returnBm) {
	        bm.recycle();
	    }
	    return returnBm;
	}



}
