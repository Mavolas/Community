package com.ziruk.oa.communitymodule.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

	/**
	 * 计算图片缩放的指数（以2的指数的倒数被进行放缩）
	 * (1 -> decodes full size; 2 -> decodes 1/4th size; 4 -> decode 1/16th size)
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels 最大像素；宽×高
	 * @return
	 */
	protected static int computeSampleSize(BitmapFactory.Options options,
	        int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength,
	            maxNumOfPixels);

	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;  // roundedSize = roundedSize * 2;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }

	    return roundedSize;
	}

	/**
	 * compute Initial Sample Size
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	protected static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength,
			int maxNumOfPixels) {

		//原始宽
	    double w = options.outWidth;
	    //原始高
	    double h = options.outHeight;

	    // 像素倍数的平方根，通过像素来推算缩放倍数
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : 
	    	(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	    
	    // 图像最小边长倍数，通过原图像边长与缩放后最短边推算倍数
	    int upperBound = (minSideLength == -1) ? 128 : 
	    	(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

	    if (upperBound < lowerBound) {
	        // return the larger one when there is no overlapping zone.
	        return lowerBound;
	    }

	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	        return 1;
	    } else if (minSideLength == -1) {
	        return lowerBound;
	    } else {
	        return upperBound;
	    }
	}

	/**
	 * get Bitmap
	 *
	 * @param imgFile
	 * @param width 目标文件宽像素
	 * @param heigth 目标文件高像素
	 * @return
	 */
	public static Bitmap tryGetBitmap(String imgFile, int width,
	        int heigth) {
	    if (imgFile == null || imgFile.length() == 0)
	        return null;

	    int minSideLength = Math.min(width, heigth);
//	    int maxNumOfPixels = width * heigth;
	    
	    try {
	        @SuppressWarnings("resource")
			FileDescriptor fd = new FileInputStream(imgFile).getFD();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        // BitmapFactory.decodeFile(imgFile, options);
	        BitmapFactory.decodeFileDescriptor(fd, null, options);

	        options.inSampleSize = computeSampleSize(options, minSideLength, -1);//不通过像素缩放
	                //maxNumOfPixels);
	        try {
	            // 这里一定要将其设置回false，因为之前我们将其设置成了true
	            // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
	            options.inJustDecodeBounds = false;

	            Bitmap bmp = BitmapFactory.decodeFile(imgFile, options);
	            return bmp == null ? null : bmp;
	        } catch (OutOfMemoryError err) {
	            return null;
	        }
	    } catch (Exception e) {
	        return null;
	    }
	}

	public static Bitmap tryGetBitmap(InputStream inputStream, int width,
	        int heigth) {

	    int minSideLength = Math.min(width, heigth);
//	    int maxNumOfPixels = width * heigth;
	    
	    try {
	    	
	    	ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
	    	byte[] buffer = new byte[1024]; 
	    	int byteread = 0;
	    	while( (byteread =inputStream.read(buffer)) != -1){
	    		outStream.write(buffer, 0, byteread );                
	    	}
	    	outStream.close();
	    	
	    	byte[] data = outStream.toByteArray();
	    	
	    	BitmapFactory.Options options = new BitmapFactory.Options();
	    	options.inJustDecodeBounds = true;
	    	BitmapFactory.decodeByteArray(data, 0, data.length,options);
	    	
//			FileDescriptor fd = new FileInputStream(bis).getFD();
//	        BitmapFactory.Options options = new BitmapFactory.Options();
//	        options.inJustDecodeBounds = true;
//	        // BitmapFactory.decodeFile(imgFile, options);

	        options.inSampleSize = computeSampleSize(options, minSideLength, -1);//不通过像素缩放
	                //maxNumOfPixels);
	        try {
	            // 这里一定要将其设置回false，因为之前我们将其设置成了true
	            // 设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
	            options.inJustDecodeBounds = false;

//	            options.inPreferredConfig = Bitmap.Config.RGB_565;
	            
	            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length,options);
	            return bmp == null ? null : bmp;
	        } catch (OutOfMemoryError err) {
	            return null;
	        }
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	/**
	 * 保存Bitma文件
	 * @param bitmap
	 * @return
	 * @throws IOException
	 */
	public static File saveMyBitmap(Bitmap bitmap, String path) throws IOException {

//	     String saveDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//	     saveDir += "/DCIM";
//	     File dir = new File(saveDir);  
//	     if (!dir.exists()) {  
//	         dir.mkdir();  
//	     }  
//	     saveDir += "/zrk";  
//	     dir = new File(saveDir);  
//	     if (!dir.exists()) {  
//	         dir.mkdir();  
//	     }  
		
		String fileName = StringUtils.substringBeforeLast(path, ".") + ".png";
		File f = new File(fileName);
		f.createNewFile();
		FileOutputStream fOut = null;		
		fOut = new FileOutputStream(f);
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
		fOut.flush();
		fOut.close();
		
		return f;
	}
}
