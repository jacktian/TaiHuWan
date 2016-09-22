package com.gloria.hbh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.constant.BaseConstants;

/**
 * ͼƬ����ʽ
 * @author gejian
 * 2013-1-24
 */
public class ImageUtils {
	
	/*
     * ��ȡѹ����ͼƬ
     */
    public static Bitmap getScaleImage(Bitmap bitmap) {
    	if(bitmap == null){
    		return null;
    	}
		int height_bm = bitmap.getHeight();
		int width_bm = bitmap.getWidth();
		float width_bm_new = 0;
		float height_bm_new = 0;
		width_bm_new = width_bm;
		height_bm_new = height_bm;
//		if(width_bm > ScreenUtils.getInstance().getWidth()){
//			width_bm_new = ScreenUtils.getInstance().getWidth();
//			height_bm_new = width_bm_new*height_bm/width_bm;
//			if(height_bm_new > ScreenUtils.getInstance().getWidth()){
//				height_bm_new = ScreenUtils.getInstance().getWidth();
//				width_bm_new = height_bm_new*width_bm/height_bm;
//			}
//		}
		width_bm_new = width_bm/2;
		height_bm_new = height_bm/2;
		float scaleWidth =  width_bm_new/width_bm;
	    float scaleHeight = width_bm_new/height_bm;
	    // ȡ����Ҫ���ŵ�matrix����
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // �õ��µ�ͼƬ
		if(bitmap != null && width_bm >0 && height_bm >0 && width_bm_new>0 && height_bm_new >0){
			Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width_bm, height_bm, matrix,
				      true);
			if(bitmap != null && !bitmap.isRecycled() && bitmap != newbm){
				bitmap.recycle();
			}
			return newbm;
		}
    	return bitmap;
    }
    
    /*
     * ��ȡ�Ŵ��ͼƬ
     */
    public static Bitmap getBigScaleImage(Bitmap bitmap) {
    	if(bitmap == null){
    		return null;
    	}
		int height_bm = bitmap.getHeight();
		int width_bm = bitmap.getWidth();
		float width_bm_new = 0;
		float height_bm_new = 0;
		width_bm_new = width_bm;
		height_bm_new = height_bm;
		if(height_bm < ScreenUtils.getInstance().getHeight()){
			height_bm_new = ScreenUtils.getInstance().getHeight();
			width_bm_new = height_bm_new*width_bm/height_bm;
			if(width_bm_new < ScreenUtils.getInstance().getWidth()){
				width_bm_new = ScreenUtils.getInstance().getWidth();
				height_bm_new = width_bm_new*height_bm/width_bm;
			}
		}
		float scaleWidth =  width_bm_new/width_bm;
	    float scaleHeight = height_bm_new/height_bm;
	    // ȡ����Ҫ���ŵ�matrix����
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // �õ��µ�ͼƬ
		if(bitmap != null && width_bm >0 && height_bm >0 && width_bm_new>0 && height_bm_new >0){
			Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width_bm, height_bm, matrix,
				      true);
			if(bitmap != null && !bitmap.isRecycled() && bitmap != newbm){
				bitmap.recycle();
			}
			return newbm;
		}
    	return bitmap;
	}
    
    /*
     * ����BitmapͼƬ
     */
    public static boolean saveBitmap(Bitmap bitmap,String path) throws IOException{
    	return saveBitmap(bitmap,path,true);
    }
    
    /*
     * ����BitmapͼƬ
     * isRecycle �Ƿ��ͷ�
     */
    public static boolean saveBitmap(Bitmap bitmap,String path,boolean isRecycle) throws IOException{
    	boolean isSave = false;
    	if(bitmap == null){
    		return isSave;
    	}
    	File file = new File(path);
    	if(!file.getParentFile().exists()){
    		file.getParentFile().mkdirs();
    	}
        FileOutputStream out;
        try{
        	out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
            	out.flush();
                out.close();
                isSave = true;
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace(); 
        }finally{
        	//�ͷ�bitmap
			if(isRecycle && bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
        }
        return isSave;
    }

    /*
     * ͼƬ��ַ�Ƿ����
     */
    public static String IsImageFileExist(String url,final int width,final int height,int scale) {
//		boolean IsImageFileExist = false;
		String result = "";	
		String fileDir = ""; 		// ·����
		String fileName="";    	//�ļ���
		String filePath = "";		//�ļ�����·��
		String newurlString = "";  
		if(url != null && !url.equals("")){   //ͼƬ��ַΪ��ʱ
			newurlString = BaseConfig.requestImageUrl(width,height,url,scale);
			fileName+=newurlString.replaceAll("[/|&|?|:|%]+", "_");
			
			fileDir = BaseConstants.CACHE_IMG_PATH;
			filePath=fileDir+fileName;
			
			if(FileUtils.isHasSDCard()){
				File file = new File(filePath);
				//�ļ�����
				if (file.exists()){
//					IsImageFileExist = true;
					result = filePath;
				}
			}
		}
		return result;
	}
    
    /*
     * ͼƬ��ַ�Ƿ����
     */
    public static String IsSaveImageFileExist(String url,final int width,final int height,int scale) {
//		boolean IsImageFileExist = false;
		String result = "";	
		String fileDir = ""; 		// ·����
		String fileName="";    	//�ļ���
		String filePath = "";		//�ļ�����·��
		if(url != null && !url.equals("")){   //ͼƬ��ַΪ��ʱ
			fileName = url.substring(url.lastIndexOf('/') + 1);
			fileDir = BaseConstants.CACHE_SAVE_IMG_PATH;
			filePath = fileDir + fileName;
			
			if(FileUtils.isHasSDCard()){
				File file = new File(filePath);
				//�ļ�����
				if (file.exists()){
//					IsImageFileExist = true;
					result = filePath;
				}
			}
		}
		return result;
	}
    
    /*
     * ��URI�ĵ�����·��
     */
	public static String getAbsoluteImagePath(Activity activity,Uri uri){
        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery( uri,
                        proj,                 // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null);                 // Order-by clause (ascending by name)
        
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();         
        return cursor.getString(column_index);
    }
	
	/*
	 * ��ȡ������յ�ͼƬ��ַ
	 */
	public static String getCameraFileName(String srcPath) { 
    	String picPathString = "";
    	Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));
        String second= String.valueOf(c.get(Calendar.SECOND));
        String name = "IMG_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second;
        picPathString = srcPath;  //��ʱͼƬ�洢��ַ
        if(FileUtils.isHasSDCard()){
        	File destDir = new File(picPathString);
        	if (!destDir.exists())  {
             	destDir.mkdirs();
            }
        }
        picPathString = picPathString+name+".jpg";		
        return picPathString;
    }
	
	/*
	 * ��ȡBitmapͨ��Path
	 */
	public static Bitmap getBitmapByPath(String path) { 
    	Bitmap bitmap = null;
    	if(FileUtils.isHasSDCard()){
			File file = new File(path);
			if (file.exists()){
				if(FileUtils.isImage(path)){
					//����ڴ����Ҫ��ʾ��ͼƬʱ������ʾ
					if(SystemMemoryUtil.getAvailMemory(BaseApplication.getInstance().getApplicationContext()) > file.length()){
//						bitmap=BitmapFactory.decodeFile(path);
						InputStream inputStream = null; 
						try { 
							inputStream = new FileInputStream(path); 
						} catch (FileNotFoundException e) { 
							e.printStackTrace(); 
						} 
//						if(BaseConfig.screenUtils.getWidth()>480){
//							BitmapFactory.Options options=new BitmapFactory.Options(); 
//						    options.inJustDecodeBounds = false; 
//						    options.inSampleSize = 2;   //width��hight��Ϊԭ�����ķ�֮һ 
//							bitmap = BitmapFactory.decodeStream(inputStream,null,options);
//						}else{
//							bitmap = BitmapFactory.decodeStream(inputStream);
//						}
						bitmap = BitmapFactory.decodeStream(inputStream);
					}
				}
			}
    	}
        return bitmap;
    }
	
	/*
	 * ��ȡDrawableͨ��Path
	 */
	@SuppressWarnings("deprecation")
	public static Drawable getDrawableByPath(String path) { 
    	Bitmap bitmap = null;
    	Drawable drawable = null;
    	if(FileUtils.isHasSDCard()){
			File file = new File(path);
			if (file.exists()){
				if(FileUtils.isImage(path)){
					//����ڴ����Ҫ��ʾ��ͼƬʱ������ʾ
					if(SystemMemoryUtil.getAvailMemory(BaseApplication.getInstance().getApplicationContext()) > file.length()){
//						bitmap=BitmapFactory.decodeFile(path);
						InputStream inputStream = null; 
						try { 
							inputStream = new FileInputStream(path); 
						} catch (FileNotFoundException e) { 
							e.printStackTrace(); 
						} 
//						if(BaseConfig.screenUtils.getWidth()>480){
//							BitmapFactory.Options options=new BitmapFactory.Options(); 
//						    options.inJustDecodeBounds = false; 
//						    options.inSampleSize = 2;   //width��hight��Ϊԭ�����ķ�֮һ 
//							bitmap = BitmapFactory.decodeStream(inputStream,null,options);
//						}else{
//							bitmap = BitmapFactory.decodeStream(inputStream);
//						}
						bitmap = BitmapFactory.decodeStream(inputStream);
						drawable = new BitmapDrawable(bitmap);
					}
				}
			}
    	}
        return drawable;
    }
	
	/*
	 * ��ȡԲ��Bitmapͨ��Path
	 */
	public static Bitmap getRoundedCornerBitmapByPath(String path,int res_color,float roundPx) { 
    	Bitmap bitmap = null;
    	Bitmap bitmap_rounded = null;
    	if(FileUtils.isHasSDCard()){
			File file = new File(path);
			if (file.exists()){
				bitmap = getBitmapByPath(path);
				bitmap_rounded = getRoundedCornerBitmap(bitmap,res_color,roundPx);
				if(bitmap_rounded != null){
					if(bitmap != null && !bitmap.isRecycled()){
						bitmap.recycle();
					}
					return bitmap_rounded;
				}
			}
    	}
        return bitmap;
    }
	
	/**
	* ͼƬԲ��
	* ��param bitmap
	* ��return
	*/
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int res_color,float roundPx) {
		final Paint paint = new Paint();
		Canvas canvas = null;
		Bitmap output = null;
		if(bitmap != null){
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			if(roundPx == BaseConstants.DEFAULT_AVATER){
				int f = Math.min(width, height);
				output = Bitmap.createBitmap(f, f, Config.ARGB_8888);
				canvas = new Canvas(output);
				final Rect rect = new Rect(0, 0, f, f);
				final RectF rectF = new RectF(rect);
				paint.setAntiAlias(true);
				canvas.drawARGB(0,0,0,0);
				paint.setColor(res_color);
				canvas.drawRoundRect(rectF, f/2, f/2, paint);
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
				canvas.drawBitmap(bitmap, rect,rect, paint); 
			}else{
				output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
				canvas = new Canvas(output);
				final Rect rect = new Rect(0, 0, width, height);
				final RectF rectF = new RectF(rect);

				paint.setAntiAlias(true);
				canvas.drawARGB(0,0,0,0);
				paint.setColor(res_color);
				canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
				paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
				canvas.drawBitmap(bitmap, rect,rect, paint); 
			}
		}
		return output;
	}
	
	/*
     * ��ȡͼƬ����ת
     */
    public static String getOrientation(Uri mImageCaptureUri) {  
    	  
        // ���������ջ���ѡ��ͼƬÿ��ͼƬ�����������д洢Ҳ�洢�ж�Ӧ��ת�Ƕ�orientationֵ  
        // ����������ȡ��ͼƬ�ǰѽǶ�ֵȡ���Ա�����ȷ����ʾͼƬ,û����תʱ��Ч���ۿ�  
    	String orientation = "";
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();  
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// ����Uri�����ݿ�����  
        if (cursor != null) {  
            cursor.moveToFirst();// ���α��ƶ�����λ����Ϊ�����Uri�ǰ���ID��������Ψһ�Ĳ���Ҫѭ����ָ���һ��������  
            orientation = cursor.getString(cursor  
                    .getColumnIndex("orientation"));// ��ȡ��ת�ĽǶ�  
            cursor.close();  
        } 
        return orientation;
    } 
    
    /*
     * ��ȡͼƬ����ת
     */
    @SuppressLint("NewApi")
	public static String getOrientation(String imgPath) {  
//    	BitmapFactory.Options options = new BitmapFactory.Options();
//    	options.inJustDecodeBounds = true;
//    	Bitmap bmp = BitmapFactory.decodeFile(imgPath, options);
//    	int width = options.outWidth;
//    	int height = options.outHeight;
//    	if(width > height){
//    		return "90";
//    	}
    	
    	ExifInterface exifInterface;
		try {
			exifInterface = new ExifInterface(imgPath);
			int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
	    	if (tag == ExifInterface.ORIENTATION_ROTATE_90) {//�������ת��ͼƬ������ת
	    		return "90";
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "0";
    }

	public static Bitmap getBitmapByDrawable(int res) {
		BitmapDrawable bd = (BitmapDrawable) BaseApplication.getInstance().getResources().getDrawable(res);
		Bitmap bm = bd.getBitmap();
		return bm;
	}

}
