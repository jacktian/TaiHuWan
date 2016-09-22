package com.gloria.hbh.util;

import android.util.DisplayMetrics;

import com.gloria.hbh.application.BaseApplication;


/**
* 文 件 名 : ScreenUtils
* 创 建 人： gejian
* 日     期：2012-8-7
* 修 改 人：gejian
* 日    期：2012-8-7
* 描    述：获取屏幕分辨率的方法类
*/
public class ScreenUtils {

	private static ScreenUtils instance;
    private static int mWidth =0;
    private static int mHeight=0;
    private static float mscaledDensity;
    private int default_Width=0;
    private int default_Height=0;
    
    public static ScreenUtils getInstance()   {   
    	if(null == instance){   
    		instance = new ScreenUtils();  
    		DisplayMetrics metrics = new DisplayMetrics();
    		metrics = BaseApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics();
    		setScreenUtil(metrics.widthPixels, metrics.heightPixels, metrics.density);  //获取屏幕分辨率
    	}   
    	return instance;    
    }  
    
    public int getDefault_Width() {
    	default_Width = getWidth()*440/480;
		return default_Width;
	}

	public int getDefault_Height() {
    	default_Height = getHeight()*650/800;
		return default_Height;
	}

    public float getScaledDensity() {
		return mscaledDensity;
	}

	public int getWidth() {
		if(mWidth == 0){
			DisplayMetrics metrics = new DisplayMetrics();
    		metrics = BaseApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics();
    		setScreenUtil(metrics.widthPixels, metrics.heightPixels, metrics.density);  //获取屏幕分辨率
		}
        return mWidth;
    }

    public  int getHeight() {
    	if(mHeight == 0){
			DisplayMetrics metrics = new DisplayMetrics();
    		metrics = BaseApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics();
    		setScreenUtil(metrics.widthPixels, metrics.heightPixels, metrics.density);  //获取屏幕分辨率
		}
        return mHeight;
    }

    public static void setScreenUtil(int width,int height,float scaledDensity){
    	mWidth = width;
    	mHeight = height;
    	mscaledDensity = scaledDensity;
    }
   
}
