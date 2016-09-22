package com.gloria.hbh.data.app;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.gloria.hbh.application.BaseApplication;

/*
 * �ֻ��豸��Ӧ�ó���
 */
public class DeviceInfo {
	
	private static DeviceInfo instance;   
	
	String mobilemodel = "";  //�ֻ��ͺ�
	String mobileversion = ""; //�ֻ�ϵͳ�汾��
	String mobilenumber = "";  //�ֻ�����
	String crashinfo = "";  //�ֻ�������Ϣ
	
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
	
	//��ȡ�ֻ���IMIE����
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
