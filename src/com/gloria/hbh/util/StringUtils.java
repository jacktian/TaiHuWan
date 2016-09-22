package com.gloria.hbh.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;


/**
* 鏂?浠?鍚?: StringUtils
* 鍒?寤?浜猴細 gejian
* 鏃?    鏈燂細2012-8-28
* 淇?鏀?浜猴細gejian
* 鏃?   鏈燂細2012-8-28
* 鎻?   杩帮細缃戠粶鑾峰彇鐨凷tring鐨勫悇绉嶇姸鎬佺殑澶勭悊鏂规硶绫?
*/
public class StringUtils {
	
	/** 
	 * 把输入流转换成字符数组 
	 * @param inputStream   输入流 
	 * @return  字符数组 
	 * @throws Exception 
	 */  
	public static byte[] readStream(InputStream inputStream) throws Exception {  
		ByteArrayOutputStream bout = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len = 0;  
		while ((len = inputStream.read(buffer)) != -1) {  
			bout.write(buffer, 0, len);  
		}  
		bout.close();  
		inputStream.close();  
		  
		return bout.toByteArray();  
	} 

    public static String replaceUrlWithPlus(String url) {
    	if (url != null) {
//            return url.replaceAll("http://(.)*?/", "").replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
            return url.replaceAll("[/|&|?|:|%]+", "_");
        }
        return null;
    }
    
//    public static String SetUrlToFilePath(int fileType, String url) {
//    	String filePath = "";
//    	switch (fileType) {
//		case BaseConstants.CACHE_FILETYPE_FILE:
//			filePath = BaseConstants.CACHE_FILE_PATH;
//			break;
//
//		default:
//			break;
//		}
//		filePath += url.replaceAll("[/|&|?|:|%]+", "_")+".dat";
//        return filePath;
//    }
    
    /*
	 * MD5加密（32位）
	 */
	public static String toMD5(String str) {
		MessageDigest messageDigest = null;  
        try {  
             messageDigest = MessageDigest.getInstance("MD5");  
             messageDigest.reset();  
             messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
             System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
             e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                 md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                 md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
//        return md5StrBuff.toString();  
        return md5StrBuff.substring(0,3).toString().toUpperCase(); 
	}
	
	//首页tab字体换行大小
    public static SpannableString changeFontSize(String tmpString,int size_big,int size_small){
        SpannableString ss=new SpannableString(tmpString); 
        ss.setSpan(new AbsoluteSizeSpan(size_big), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(size_small), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        if(ScreenUtils.getInstance().getWidth()==480){
//        	 ss.setSpan(new AbsoluteSizeSpan(21), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//             ss.setSpan(new AbsoluteSizeSpan(10), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }else if(GlobalSource.screenWidth==320){
//        	 ss.setSpan(new AbsoluteSizeSpan(14), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//             ss.setSpan(new AbsoluteSizeSpan(8), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }else if(GlobalSource.screenWidth==240){
//        	ss.setSpan(new AbsoluteSizeSpan(12), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new AbsoluteSizeSpan(6), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
		return ss;
    }
    
}
