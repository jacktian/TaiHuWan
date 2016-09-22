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
* �� �� �� : FileUtils
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-28
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-28
* ��    �����ļ�������
*/
public class FileUtils {
	
//	private static String TAG = "FileUtils";
//	private static boolean DEBUG = false; //�Ƿ�Debug
//    private static final int BUFFER = 8192;
    
    //�Ƿ���SD��
    public static boolean isHasSDCard(){
    	boolean  isHasSDCard = false;
    	if (Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) {
    		isHasSDCard = true;
    	}
    	return isHasSDCard;
    }
    
    /*
     * ��ȡ�ı��ļ�
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
     * ��ȡ�ı��ļ�
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
     * д���ı��ļ�
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
     * д���ı��ļ�
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
     * @param fromFile �����Ƶ��ļ�  
     * @param toFile ���Ƶ�Ŀ¼�ļ�  
     * @param rewrite �Ƿ����´����ļ�  
     *   
     * <p>�ļ��ĸ��Ʋ�������  
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
            //�ر����롢�����  
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
     * �ݹ����ļ��д�С
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
     * ɾ���ļ�������������ļ�
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
    				delFile(files[i]);   //ɾ���������ļ����ļ���
    				files[i].delete();	//ɾ���Ը���
    			}
    			else {
    				files[i].delete();	//�ļ�ֱ��ɾ��
    			}
    		}
    	}
	}
    
    /*
     * ͼƬѹ��
     * @param file :��ѹ��Դ�ļ�
     * @param size ��ѹ����С
     * @param path ��ѹ��
     */
    public static File  compressImg(File file,long size,String path) throws IOException{
		OutputStream os = null;
		Bitmap btm = null;
		if(isHasSDCard()){
			if (!file.exists() || file.length() < size) // ���ļ������ڣ�����С��size���򲻽���ѹ��
				return file;
			
			try{
				File tmpfile=new File(path,"tmp.jpg");
				
				while(file.length() > size){		
					// ��ͼƬ����ѹ�������ڶ�ȡ�Ĺ����н���ѹ���������ǰ�ͼƬ�������ڴ��ٽ���ѹ��
					BitmapFactory.Options newOpts = new BitmapFactory.Options();
					// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
					newOpts.inSampleSize = 2;
					// inJustDecodeBounds��Ϊfalse��ʾ��ͼƬ�����ڴ���
					newOpts.inJustDecodeBounds = false;
					// ���ô�С�����һ���ǲ�׼ȷ�ģ�����inSampleSize��Ϊ׼���������������ȴ��������
					btm = BitmapFactory.decodeFile(file.getAbsolutePath(), newOpts);
						
					// �����ļ������
					if(btm==null){
						return file;
					}
					tmpfile=new File(path,"tmp.jpg");
					os = new FileOutputStream(tmpfile);
					// �洢
					btm.compress(CompressFormat.JPEG, 100, os);
					// �ر���
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
	 * zip��ѹ
	 * 
	 * AssetManager assetManager = getAssets();
	 * // ��Ҫ��ѹ�Ķ���
	 * InputStream dataSource = assetManager.open("ShiningTrip.zip");
	 *  //    �{�ý�ѹ�ķ���
	 *  ZipUtil.unzip(dataSource, android.os.Environment.getExternalStorageDirectory()  + "");
	 */
	public static void unzip(InputStream zipFileName, String outputDirectory) {
		try {
			ZipInputStream in = new ZipInputStream(zipFileName);
	        // ��ȡZipInputStream�е�ZipEntry��Ŀ��һ��zip�ļ��п��ܰ������ZipEntry��
	        // ��getNextEntry�����ķ���ֵΪnull�������ZipInputStream��û����һ��ZipEntry��
	        // ��������ȡ��ɣ�
	        ZipEntry entry = in.getNextEntry();
	        while (entry != null) {

	        	// ������zip���ļ���ΪĿ¼���ĸ�Ŀ¼
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
		            // ��ȡ��һ��ZipEntry
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
	
	//ѹ���ļ�������ͼƬ�� --->����Ҫ�ж��Ƿ�Ϊnull
	public static File  compressImg(File file) throws IOException{
		String orientation = "";
		int angle = 0;
		Bitmap mBitmap = null;
		Bitmap tmpBitmap = null;
		OutputStream os = null;
		Bitmap btm = null;
		
		// �ļ�������
		if (!file.exists()) {
			return null;
		}
		
		String filepath = file.getAbsolutePath();
		orientation = ImageUtils.getOrientation(filepath);
		
		// ���ļ�С��ָ����С���򲻽���ѹ��
		if (file.length() > BaseConstants.FILE_IMAGE_MAXSIZE){
			try{
				String path= BaseConstants.CACHE_IMG_TEMP_PATH;
				File tmpfile=new File(path);
				if(!tmpfile.exists()){
					tmpfile.mkdirs();
				}
				tmpfile=new File(path,"tmp.jpg");
					
				while(file.length() > BaseConstants.FILE_IMAGE_MAXSIZE){		
//					// ��ͼƬ����ѹ�������ڶ�ȡ�Ĺ����н���ѹ���������ǰ�ͼƬ�������ڴ��ٽ���ѹ��
//					BitmapFactory.Options newOpts = new BitmapFactory.Options();
//					// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
//					//newOpts.inSampleSize = 2;
//					// inJustDecodeBounds��Ϊfalse��ʾ��ͼƬ�����ڴ���
//					newOpts.inJustDecodeBounds = true;
//					// ���ô�С�����һ���ǲ�׼ȷ�ģ�����inSampleSize��Ϊ׼���������������ȴ��������
//					Bitmap btm_tmp = BitmapFactory.decodeFile(file.getAbsolutePath(), newOpts);
//					int xScale = newOpts.outWidth / BaseConfig.screenUtils.getWidth();
//					int yScale = newOpts.outHeight / BaseConfig.screenUtils.getWidth();
//					newOpts.inSampleSize = xScale > yScale?xScale:yScale;
					
					BitmapFactory.Options opts = new BitmapFactory.Options();
					// ���ŵı����������Ǻ��Ѱ�׼���ı����������ŵģ�Ŀǰ��ֻ����ֻ��ͨ��inSampleSize���������ţ���ֵ�������ŵı�����SDK�н�����ֵ��2��ָ��ֵ
					opts.inSampleSize = 2;
					// inJustDecodeBounds��Ϊfalse��ʾ��ͼƬ�����ڴ���
					opts.inJustDecodeBounds = false;
					// ���ô�С�����һ���ǲ�׼ȷ�ģ�����inSampleSize��Ϊ׼���������������ȴ��������
					btm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
							
					// �����ļ������
					if(btm==null){
						return file;
					}
						
					tmpfile=new File(path,"tmp.jpg");
					os = new FileOutputStream(tmpfile);
					// �洢
					btm.compress(CompressFormat.JPEG, 100, os);
					// �ر���
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
//            imageDispose.mBitmapRefs.add(new SoftReference<Bitmap>(mBitmap));     //�˴�����ArrayList  
//            tmpBitmap = Bitmap.createBitmap(mBitmap,0,0, mBitmap.getWidth(), mBitmap.getHeight(),matrix, true);  
//            imageDispose.mBitmapRefs.add(new SoftReference<Bitmap>(tmpBitmap));     //�˴�����ArrayList  
//            saveBitmap(tmpBitmap, filepath);
//		}
		return file;
	}
		
	/**
	 * �ж��ļ��Ƿ�ΪͼƬ�ļ�(GIF,PNG,JPG)
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
	
	// ɾ��numDays֮ǰ���ļ���
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
