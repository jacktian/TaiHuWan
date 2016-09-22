package com.gloria.hbh.data.app;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.gloria.hbh.application.BaseApplication;

/*
 * 手机设备的应用常量
 */
public class DeviceInfo {
	
	private static DeviceInfo instance;   
	
	String mobilemodel = "";  //手机型号
	String mobileversion = ""; //手机系统版本号
	String mobilenumber = "";  //手机号码
	String crashinfo = "";  //手机崩溃信息
	
	public static DeviceInfo getInstance()   { 
    	if(null == instance){   
    		instance = new DeviceInfo(); 
    		TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    		getInstance().setMobilemodel((android.os.Build.MANUFACTURER +"_"+android.os.Build.MODEL).replaceAll(" ", ""));
    		getInstance().setMobileversion("Android"+android.os.Build.VERSION.RELEASE);
    		if(tm.getLine1Number() != null && !tm.getLine1Number().equals("null")){
    			getInstance().setMobilenumber(tm.getLine1Number());
    	  	}
    	}   
    	return instance;    
    }  
	
	//获取手机的IMIE号码
    public static  String  getImieNumber() {
    	TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    	String deviceId = tm.getDeviceId();
    	return deviceId;
    }
	
	public String getMobilemodel() {
		return mobilemodel;
	}

	public void setMobilemodel(String mobilemodel) {
		this.mobilemodel = mobilemodel;
	}

	public String getMobileversion() {
		return mobileversion;
	}

	public void setMobileversion(String mobileversion) {
		this.mobileversion = mobileversion;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getCrashinfo() {
		return crashinfo;
	}

	public void setCrashinfo(String crashinfo) {
		this.crashinfo = crashinfo;
	}
}
