package com.gloria.hbh.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.gloria.hbh.constant.BaseConstants;

/**
* 文 件 名 : FileUtils
* 创 建 人： gejian
* 日     期：2012-8-28
* 修 改 人：gejian
* 日    期：2012-8-28
* 描    述：文件处理类
*/
public class FileUtils {
	
//	private static String TAG = "FileUtils";
//	private static boolean DEBUG = false; //是否Debug
//    private static final int BUFFER = 8192;
    
    //是否有SD卡
    public static boolean isHasSDCard(){
    	boolean  isHasSDCard = false;
    	if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {
    		isHasSDCard = true;
    	}
    	return isHasSDCard;
    }
    
    /*
     * 读取文本文件
     * param String
     */
    public static String readTextFile(File file) throws IOException {
        String text = null;
        InputStream is = null;
        if(isHasSDCard()){
        	try {
                is = new FileInputStream(file);
                text = readTextInputStream(is);;
            } finally {
                if(is != null) {
                    is.close();
                }
            }
        }
        return text;
    }

    /*
     * 读取文本文件
     * param InputStream
     */
    public static String readTextInputStream(InputStream is) throws IOException {
        StringBuffer strbuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        if(!isHasSDCard()){
        	return null;
        }
        try{
            reader = new BufferedReader(new InputStreamReader(is));
            while((line = reader.readLine()) != null) {
                strbuffer.append(line).append("\r\n");
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return strbuffer.toString();
    }

    /*
     * 写入文本文件
     * param String
     */
	public static void writeTextFile(File file, String str) throws IOException {
        DataOutputStream out = null;
        if(isHasSDCard()){
        	if(!file.getParentFile().exists()){
        		file.getParentFile().mkdirs();
        	}
        	try {
                out = new DataOutputStream(new FileOutputStream(file));
                out.write(str.getBytes());
            } finally {
                if(out != null) {
                    out.close();
                }
            }
        }
    }
	
	 /*
     * 写入文本文件
     * param inStream
     */
	public static void writeTextFile(File file, InputStream inStream) throws IOException {
        FileOutputStream outStream = null;
        if(isHasSDCard()){
        	if(!file.getParentFile().exists()){
        		file.getParentFile().mkdirs();
        	}
        	try {
        		outStream = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = inStream.read(buffer)) != -1)
				{
					outStream.write(buffer, 0, len);
				}
            } finally {
            	if(outStream != null) {
            		outStream.flush();
    				outStream.close();
            	}
            }
        }
    }

	/**  
     *   
     * @param fromFile 被复制的文件  
     * @param toFile 复制的目录文件  
     * @param rewrite 是否重新创建文件  
     *   
     * <p>文件的复制操作方法  
     */  
    public static boolean copyfile(File fromFile, File toFile,Boolean rewrite ){  
          
        if(!fromFile.exists()){  
            return false;  
        }  
          
        if(!fromFile.isFile()){  
            return false;  
        }  
        if(!fromFile.canRead()){  
            return false;  
        }  
        if(!toFile.getParentFile().exists()){  
            toFile.getParentFile().mkdirs();  
        }  
        if(toFile.exists() && rewrite){  
            toFile.delete();  
        }  
          
        try {  
            FileInputStream fosfrom = new FileInputStream(fromFile);  
            FileOutputStream fosto = new FileOutputStream(toFile);  
              
            byte[] bt = new byte[1024];  
            int c;  
            while((c=fosfrom.read(bt)) > 0){  
                fosto.write(bt,0,c);  
            }  
            //关闭输入、输出流  
            fosfrom.close();  
            fosto.close();  
            return true;
              
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return false;
    }  
    
    /*
     * 递归获得文件夹大小
     */
    public static long getFileSize(File file){		
		long size = 0;
		if(isHasSDCard()){
			if(!file.exists())
				return 0;
			
			File [] files=file.listFiles();
			for(int i=0;i<files.length;i++)
			{
				if(files[i].isDirectory())
				{
					size+=getFileSize(files[i]);
				}
				else
				{
					size+=files[i].length();
				}
			} 
		}
		return size;	
	}
    
    /*
     * 删除文件及其下面的子文件
     */
    public static  void delFile(File file){
    	if(isHasSDCard()){
    		if(!file.exists())
    			return;
    		File []files=file.listFiles();
    		for(int i=0;i<files.length;i++)
    		{
    			if(files[i].isDirectory())
    			{
    				delFile(files[i]);   //删除下面子文件或文件夹
    				files[i].delete();	//删除自个儿
    			}
    			else {
    				files[i].delete();	//文件直接删掉
    			}
    		}
    	}
	}
    
    /*
     * 图片压缩
     * @param file :待压缩源文件
     * @param size ：压缩大小
     * @param path ：压缩
     */
    public static File  compressImg(File file,long size,String path) throws IOException{
		OutputStream os = null;
		Bitmap btm = null;
		if(isHasSDCard()){
			if (!file.exists() || file.length() < size) // 若文件不存在，或者小于size，则不进行压缩
				return file;
			
			try{
				File tmpfile=new File(path,"tmp.jpg");
				
				while(file.length() > size){		
					// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
					BitmapFactory.Options newOpts = new BitmapFactory.Options();
					// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
					newOpts.inSampleSize = 2;
					// inJustDecodeBounds设为false表示把图片读进内存中
					newOpts.inJustDecodeBounds = false;
					// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
					btm = BitmapFactory.decodeFile(file.getAbsolutePath(), newOpts);
						
					// 创建文件输出流
					if(btm==null){
						return file;
					}
					tmpfile=new File(path,"tmp.jpg");
					os = new FileOutputStream(tmpfile);
					// 存储
					btm.compress(CompressFormat.JPEG, 100, os);
					// 关闭流
					os.flush();
					os.close();
					if(!btm.isRecycled()){
						btm.recycle();
					}
					file=new File(path+file.getName());
					tmpfile.renameTo(file);
				}
				return file;
			}
			catch (Exception e){
				return file;
			}
		}
		return file;
	}
    
    /*
	 * zip解压
	 * 
	 * AssetManager assetManager = getAssets();
	 * // 需要解压的对象
	 * InputStream dataSource = assetManager.open("ShiningTrip.zip");
	 *  //    調用解压的方法
	 *  ZipUtil.unzip(dataSource, android.os.Environment.getExternalStorageDirectory()  + "");
	 */
	public static void unzip(InputStream zipFileName, String outputDirectory) {
		try {
			ZipInputStream in = new ZipInputStream(zipFileName);
	        // 获取ZipInputStream中的ZipEntry条目，一个zip文件中可能包含多个ZipEntry，
	        // 当getNextEntry方法的返回值为null，则代表ZipInputStream中没有下一个ZipEntry，
	        // 输入流读取完成；
	        ZipEntry entry = in.getNextEntry();
	        while (entry != null) {

	        	// 创建以zip包文件名为目录名的根目录
	        	if(FileUtils.isHasSDCard()){
	        		File file = new File(outputDirectory);
		            file.mkdir();
		            if (entry.isDirectory()) {
		            String name = entry.getName();
		            name = name.substring(0, name.length() - 1);
		              
		            file = new File(outputDirectory + File.separator + name);
		            	file.mkdir();
		            } else {
		            	file = new File(outputDirectory + File.separator + entry.getName());
		                file.createNewFile();
		                FileOutputStream out = new FileOutputStream(file);
		                int b;
		                while ((b = in.read()) != -1) {
		                	out.write(b);
		                }
		                out.close();
		            }
		            // 读取下一个ZipEntry
		            entry = in.getNextEntry();
	        	}
	        }
	        in.close();
	    } catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//压缩文件（例如图片） --->返回要判断是否为null
	public static File  compressImg(File file) throws IOException{
		String orientation = "";
		int angle = 0;
		Bitmap mBitmap = null;
		Bitmap tmpBitmap = null;
		OutputStream os = null;
		Bitmap btm = null;
		
		// 文件不存在
		if (!file.exists()) {
			return null;
		}
		
		String filepath = file.getAbsolutePath();
		orientation = ImageUtils.getOrientation(filepath);
		
		// 若文件小于指定大小，则不进行压缩
		if (file.length() > BaseConstants.FILE_IMAGE_MAXSIZE){
			try{
				String path= BaseConstants.CACHE_IMG_TEMP_PATH;
				File tmpfile=new File(path);
				if(!tmpfile.exists()){
					tmpfile.mkdirs();
				}
				tmpfile=new File(path,"tmp.jpg");
					
				while(file.length() > BaseConstants.FILE_IMAGE_MAXSIZE){		
//					// 对图片进行压缩，是在读取的过程中进行压缩，而不是把图片读进了内存再进行压缩
//					BitmapFactory.Options newOpts = new BitmapFactory.Options();
//					// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
//					//newOpts.inSampleSize = 2;
//					// inJustDecodeBounds设为false表示把图片读进内存中
//					newOpts.inJustDecodeBounds = true;
//					// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
//					Bitmap btm_tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), newOpts);
//					int xScale = newOpts.outWidth / BaseConfig.screenUtils.getWidth();
//					int yScale = newOpts.outHeight / BaseConfig.screenUtils.getWidth();
//					newOpts.inSampleSize = xScale > yScale?xScale:yScale;
					
					BitmapFactory.Options opts = new BitmapFactory.Options();
					// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
					opts.inSampleSize = 2;
					// inJustDecodeBounds设为false表示把图片读进内存中
					opts.inJustDecodeBounds = false;
					// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
					btm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
							
					// 创建文件输出流
					if(btm==null){
						return file;
					}
						
					tmpfile=new File(path,"tmp.jpg");
					os = new FileOutputStream(tmpfile);
					// 存储
					btm.compress(CompressFormat.JPEG, 100, os);
					// 关闭流
					os.flush();
					os.close();
					if(btm!= null && !btm.isRecycled()){
						btm.recycle();
					}
					file=new File(path+file.getName());
					tmpfile.renameTo(file);
				}
			}catch (Exception e)	{
				return null;
			}
		}
//		filepath = file.getAbsolutePath();
//		
//		if(orientation != null && !orientation.equals("") && !orientation.equals("0")){
//			angle = Integer.parseInt(orientation); 
//			Matrix matrix = new Matrix();  
//            matrix.setRotate(angle);  
//            mBitmap = getBitmapByPath(filepath);
//            imageDispose.mBitmapRefs.add(new SoftReference<Bitmap>(mBitmap));     //此处加入ArrayList  
//            tmpBitmap = Bitmap.createBitmap(mBitmap,0,0, mBitmap.getWidth(), mBitmap.getHeight(),matrix, true);  
//            imageDispose.mBitmapRefs.add(new SoftReference<Bitmap>(tmpBitmap));     //此处加入ArrayList  
//            saveBitmap(tmpBitmap, filepath);
//		}
		return file;
	}
		
	/**
	 * 判断文件是否为图片文件(GIF,PNG,JPG)
	 * @param srcFileName
	 * @return
	 */
	public static boolean  isImage(String srcFileName) {
		boolean isImage = false;
		if(!srcFileName.contains(".")){
			return isImage;
		}
		String imagetype = srcFileName.substring(srcFileName.lastIndexOf("."));
		
		if( imagetype.equalsIgnoreCase(".jpg") || imagetype.equalsIgnoreCase(".gif")
			|| imagetype.equalsIgnoreCase(".jpeg") || imagetype.equalsIgnoreCase(".bmp")
			|| imagetype.equalsIgnoreCase(".png")){  
			isImage = true;
		}
     return isImage;  
	}
	
	// 删除numDays之前的文件夹
	public static int clearCacheFolder(File dir, long numDays) {        
	      int deletedFiles = 0;       
	      if (dir!= null && dir.isDirectory()) {           
	          try {              
	              for (File child:dir.listFiles()) {  
	                  if (child.isDirectory()) {            
	                      deletedFiles += clearCacheFolder(child, numDays);        
	                  }  
	                  if (child.lastModified() < numDays) {   
	                      if (child.delete()) {                 
	                          deletedFiles++;         
	                      }  
	                  }  
	              }           
	          } catch(Exception e) {     
	              e.printStackTrace();  
	          }   
	      }     
	      return deletedFiles;   
	  }  
}
