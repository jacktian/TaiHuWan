package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.data.forum.ImageInfo;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.PreferencesUtils;
import com.google.gson.JsonObject;

/**
 * app������Ϣ
 * @author gejian
 * 2013-1-23
 */
public class AppInfo {

	// Ӧ�ó���ĳ���
	String version_check_url = "";  //�汾���url��ַ
	ImageInfo launch_image = null; // ����ͼƬ�������������飩

	AppInfo() {
		launch_image = new ImageInfo();
	}

	public String getVersionCheckUrl() {
		return version_check_url;
	}

	public void setVersionCheckUrl(String version_check_url) {
		if(version_check_url == null){
			return;
		}
		this.version_check_url = version_check_url;
	}

	public ImageInfo getLaunchImage() {
		return launch_image;
	}

	public void setLaunchImage(ImageInfo launch_image) {
		this.launch_image = launch_image;
	}

	@SuppressWarnings("unchecked")
	public static AppInfo getAppInfo(HashMap<String, Object> root){
		if(root == null){
			return null;
		}
    	AppInfo appInfo = new AppInfo();
    	appInfo.setVersionCheckUrl((String)root.get("version_check_url"));
    	//�жϱ����Ƿ��������ļ��������ȡ����
		String jsonString_startPic = "";
		jsonString_startPic =  PreferencesUtils.getStringPreferences(
	  			BaseConstants.Settings_NAME, BaseConstants.SharedPreferences_StartPic, null);
		if(jsonString_startPic == null || jsonString_startPic.equals("")){
			appInfo.setLaunchImage(ImageInfo.getImageInfo((HashMap<String, Object>)root.get("launch_image")));
		}else{
			JsonObject jsonObject= JsonMethed.getJsonObject(JsonMethed.getJsonElement(jsonString_startPic));
			appInfo.setLaunchImage(ImageInfo.getImageInfoFromJson(jsonObject));
		}
		return appInfo;
	}
}
