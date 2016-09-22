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
 * 图片处理方式
 * @author gejian
 * 2013-1-24
 */
public class ImageUtils {
	
	/*
     * 获取压缩的图片
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
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
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
     * 获取放大的图片
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
	    // 取得想要缩放的matrix参数
	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
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
     * 保存Bitmap图片
     */
    public static boolean saveBitmap(Bitmap bitmap,String path) throws IOException{
    	return saveBitmap(bitmap,path,true);
    }
    
    /*
     * 保存Bitmap图片
     * isRecycle 是否释放
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
        	//释放bitmap
			if(isRecycle && bitmap != null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
        }
        return isSave;
    }

    /*
     * 图片地址是否存在
     */
    public static String IsImageFileExist(String url,final int width,final int height,int scale) {
//		boolean IsImageFileExist = false;
		String result = "";	
		String fileDir = ""; 		// 路径名
		String fileName="";    	//文件名
		String filePath = "";		//文件绝对路径
		String newurlString = "";  
		if(url != null && !url.equals("")){   //图片地址为空时
			newurlString = BaseConfig.requestImageUrl(width,height,url,scale);
			fileName+=newurlString.replaceAll("[/|&|?|:|%]+", "_");
			
			fileDir = BaseConstants.CACHE_IMG_PATH;
			filePath=fileDir+fileName;
			
			if(FileUtils.isHasSDCard()){
				File file = new File(filePath);
				//文件存在
				if (file.exists()){
//					IsImageFileExist = true;
					result = filePath;
				}
			}
		}
		return result;
	}
    
    /*
     * 图片地址是否存在
     */
    public static String IsSaveImageFileExist(String url,final int width,final int height,int scale) {
//		boolean IsImageFileExist = false;
		String result = "";	
		String fileDir = ""; 		// 路径名
		String fileName="";    	//文件名
		String filePath = "";		//文件绝对路径
		if(url != null && !url.equals("")){   //图片地址为空时
			fileName = url.substring(url.lastIndexOf('/') + 1);
			fileDir = BaseConstants.CACHE_SAVE_IMG_PATH;
			filePath = fileDir + fileName;
			
			if(FileUtils.isHasSDCard()){
				File file = new File(filePath);
				//文件存在
				if (file.exists()){
//					IsImageFileExist = true;
					result = filePath;
				}
			}
		}
		return result;
	}
    
    /*
     * 由URI的到绝对路径
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
	 * 获取相机拍照的图片地址
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
        picPathString = srcPath;  //临时图片存储地址
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
	 * 获取Bitmap通过Path
	 */
	public static Bitmap getBitmapByPath(String path) { 
    	Bitmap bitmap = null;
    	if(FileUtils.isHasSDCard()){
			File file = new File(path);
			if (file.exists()){
				if(FileUtils.isImage(path)){
					//如果内存大于要显示的图片时，才显示
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
//						    options.inSampleSize = 2;   //width，hight设为原来的四分之一 
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
	 * 获取Drawable通过Path
	 */
	@SuppressWarnings("deprecation")
	public static Drawable getDrawableByPath(String path) { 
    	Bitmap bitmap = null;
    	Drawable drawable = null;
    	if(FileUtils.isHasSDCard()){
			File file = new File(path);
			if (file.exists()){
				if(FileUtils.isImage(path)){
					//如果内存大于要显示的图片时，才显示
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
//						    options.inSampleSize = 2;   //width，hight设为原来的四分之一 
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
	 * 获取圆角Bitmap通过Path
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
	* 图片圆角
	* ＠param bitmap
	* ＠return
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
     * 获取图片的旋转
     */
    public static String getOrientation(Uri mImageCaptureUri) {  
    	  
        // 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值  
        // 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看  
    	String orientation = "";
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();  
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找  
        if (cursor != null) {  
            cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了  
            orientation = cursor.getString(cursor  
                    .getColumnIndex("orientation"));// 获取旋转的角度  
            cursor.close();  
        } 
        return orientation;
    } 
    
    /*
     * 获取图片的旋转
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
	    	if (tag == ExifInterface.ORIENTATION_ROTATE_90) {//如果是旋转地图片则先旋转
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
