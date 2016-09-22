package com.gloria.hbh.data.app;

import java.io.InputStream;
import java.util.HashMap;

import android.content.res.AssetManager;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.handle.PListParser;
import com.gloria.hbh.util.PreferencesUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/*
 * ��̳��Ϣ  
 */
public class Plist {
	
	private static Plist instance;   
	
	public static final String TAB_NAME = "tab_name";
    public static final String TAB_FID = "tab_fid";
    
	String config_version = ""; //�����ļ�Ŀǰ�İ汾��
	
	UserInfo userInfo = null;
	
	AppInfo appInfo = null; //app������Ϣ
	
	Forum forum = null; //��̳��Ϣ
	
    @SuppressWarnings("unchecked")
	public static Plist getInstance()   { 
    	if(null == instance){   
    		instance = new Plist(); 
    		//��ȡ(����)�ļ���ͬʱCheck�����ļ������ر��档������ͼƬ��
    		InputStream is = null;
    		try{
    			AssetManager assetManager = BaseApplication.getInstance().getApplicationContext().getAssets();
    			is = assetManager.open("appconfig.plist");
    			PListParser parser = new PListParser(is);
    			getForumInfo((HashMap<String, Object>) parser.root);
    		}catch (JsonParseException e) {
    		}catch (Exception e) {
    		}
    	}   
    	return instance;    
    }  
    
    public AppInfo getAppInfo() {
    	if(appInfo == null){
    		appInfo = new AppInfo();
    	}
		return appInfo;
	}

	public Forum getForum() {
		if(forum == null){
			forum = new Forum();
		}
		return forum;
	}

    public UserInfo getUserInfo() {
    	if(userInfo == null){
    		//��ȡ����
    		String jsonString_lastLogin =  PreferencesUtils.getStringPreferences(
		  			BaseConstants.AccountManager_NAME, BaseConstants.SharedPreferences_LastLogin, null);
			if(jsonString_lastLogin!=null&&!jsonString_lastLogin.equals("")){
				JsonObject jsonObject= JsonMethed.getJsonObject(JsonMethed.getJsonElement(jsonString_lastLogin));
				userInfo = UserInfo.getFromJsonObject(jsonObject);
				userInfo.setLogin(false);
			}
    	}
		return userInfo;
	}
    
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getConfig_version() {
		return config_version;
	}
    
	public void setConfig_version(String config_version) {
		if(config_version == null){
			return;
		}
		this.config_version = config_version;
	}
    
    @SuppressWarnings("unchecked")
	public static void getForumInfo(HashMap<String, Object> root){
    	getInstance().setConfig_version((String)root.get("config_version"));
    	getInstance().setAppInfo(AppInfo.getAppInfo((HashMap<String, Object>)root.get("app_info")));
    	getInstance().setForum(Forum.getForum((HashMap<String, Object>)root.get("forum")));
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
}
