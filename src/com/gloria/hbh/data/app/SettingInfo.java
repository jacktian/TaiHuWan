package com.gloria.hbh.data.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
* �� �� �� : SettingsInfo
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-7
* ��    ����������������ݽṹ
*/
public class SettingInfo {
	
	public class SettingTypeConstants {
		//������������͵����
		public static final int SETTING_TOPICS_DEFAULT_AUTHOR   = -1; //����Ĭ��ֻ��¥��
		public static final int SETTING_2G_3G_NOT_SHOW_IMAGE   = -2; //2G/3G���粻��ʾͼƬ
		public static final int SETTING_TOPICS_DETAIL_FONT_SIZE = -3;  //������ϸ�����С
		public static final int SETTING_PAGE_COUNT = -4; //ÿҳ��������
		public static final int SETTING_RECEIVE_NOTIFY = -5; //����ϵͳ֪ͨ
		public static final int SETTING_CLEAR_CACHE = -6; //�������
		public static final int SETTING_THEME_SKIN = -7; //�л�����Ƥ��
		public static final int SETTING_USE_BAIDU_INPUT = -8; //ʹ�ðٶ����뷨
		public static final int SETTING_RESET_SETTING = -9; //�ظ�Ĭ������
		
		public static final int SETTING_NEWBIE_GUIDE = -10; //��������
	}
	
	public class TextSizeTypeConstants {
//		//������������͵����
//		public static final int TextSize_H = -1;  //����  Huge
//		public static final int TextSize_B = -2;  //��
//		public static final int TextSize_M = -3;  //��
//		public static final int TextSize_S = -4;  //С
		
		public static final String TextSize_H = "����";  //����  Huge
		public static final String TextSize_B = "��";  //��
		public static final String TextSize_M = "��";  //��
		public static final String TextSize_S = "С";  //С
	}

	String name = "";
	int type = SettingTypeConstants.SETTING_2G_3G_NOT_SHOW_IMAGE;
	String value = "";
	ArrayList<SettingInfo> settings;
	
	public ArrayList<SettingInfo> getSettings() {
		if(settings == null){
			settings = new ArrayList<SettingInfo>(1);
		}
		return settings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		if(type == 0){
			type = SettingTypeConstants.SETTING_2G_3G_NOT_SHOW_IMAGE;
		}
		return type;
	}

	public void setType(String type) {
		//����
//		if(type.equals("setting_topic_default_author")){
//			this.type = SettingTypeConstants.SETTING_TOPICS_DEFAULT_AUTHOR;
//		}else if(type.equals("setting_2g_3g_not_show_image")){
//			this.type = SettingTypeConstants.SETTING_2G_3G_NOT_SHOW_IMAGE;
//		}else if(type.equals("setting_topic_detail_font_size")){
//			this.type = SettingTypeConstants.SETTING_TOPICS_DETAIL_FONT_SIZE;
//		}else if(type.equals("setting_page_count")){
//			this.type = SettingTypeConstants.SETTING_PAGE_COUNT;
//		}else if(type.equals("setting_receive_notify")){
//			this.type = SettingTypeConstants.SETTING_RECEIVE_NOTIFY;
//		}else if(type.equals("setting_clear_cache")){
//			this.type = SettingTypeConstants.SETTING_CLEAR_CACHE;
//		}else if(type.equals("setting_theme_skin")){
//			this.type = SettingTypeConstants.SETTING_THEME_SKIN;
//		}else if(type.equals("setting_use_baidu_input")){
//			this.type = SettingTypeConstants.SETTING_USE_BAIDU_INPUT;
//		}else if(type.equals("setting_reset_setting")){
//			this.type = SettingTypeConstants.SETTING_RESET_SETTING;
//		}
		if(type.equals("topic_default_author")){
			this.type = SettingTypeConstants.SETTING_TOPICS_DEFAULT_AUTHOR;
		}else if(type.equals("2g_3g_not_show_image")){
			this.type = SettingTypeConstants.SETTING_2G_3G_NOT_SHOW_IMAGE;
		}else if(type.equals("topic_detail_font_size")){
			this.type = SettingTypeConstants.SETTING_TOPICS_DETAIL_FONT_SIZE;
		}else if(type.equals("page_count")){
			this.type = SettingTypeConstants.SETTING_PAGE_COUNT;
		}else if(type.equals("receive_nofity")){
			this.type = SettingTypeConstants.SETTING_RECEIVE_NOTIFY;
		}else if(type.equals("clear_cache")){
			this.type = SettingTypeConstants.SETTING_CLEAR_CACHE;
		}else if(type.equals("theme_skin")){
			this.type = SettingTypeConstants.SETTING_THEME_SKIN;
		}else if(type.equals("baidu_input")){
			this.type = SettingTypeConstants.SETTING_USE_BAIDU_INPUT;
		}else if(type.equals("reset_setting")){
			this.type = SettingTypeConstants.SETTING_RESET_SETTING;
		}else if(type.equals("newbie_guide")){
			this.type = SettingTypeConstants.SETTING_NEWBIE_GUIDE;
		}
	}
	
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		if(value == null || value.equals("null")){
			value = "NO";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static SettingInfo getSettingInfo(HashMap<String, Object> root) {
		if(root == null){
			return null;
		}
		SettingInfo settingsInfo = new SettingInfo();
		settingsInfo.setName((String)root.get("name"));
		settingsInfo.setType((String)root.get("type"));
		settingsInfo.setValue((String)root.get("value"));
		return settingsInfo;
	}
	
	private static SettingInfo getSettingInfoFromJson(JsonObject jsonObject) {
		if(jsonObject == null){
			return null;
		}
		SettingInfo settingsInfo = new SettingInfo();
		settingsInfo.setName(JsonMethed.getJsonString(jsonObject.get("name")));
		settingsInfo.setType(JsonMethed.getJsonInt(jsonObject.get("type")));
		settingsInfo.setValue(JsonMethed.getJsonString(jsonObject.get("value")));
		return settingsInfo;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<SettingInfo> getSettingInfos(ArrayList<Object> root) {
		// TODO Auto-generated method stub
		if(root == null){
			return null;
		}
		ArrayList<SettingInfo> settingsInfos = new ArrayList<SettingInfo>();
		for(int i = 0; i < root.size(); i++){
			if(root.get(i) instanceof HashMap){
				HashMap<String, Object> setting = (HashMap<String, Object>) root.get(i);
				settingsInfos.add(SettingInfo.getSettingInfo(setting));
			}
			else if(root.get(i) instanceof ArrayList){
				ArrayList<Object> setting = (ArrayList<Object>) root.get(i);
				SettingInfo settingInfo = new SettingInfo();
				for(int j = 0; j < setting.size(); j++){
					if(setting.get(j) instanceof HashMap){
						HashMap<String, Object> setting_ = (HashMap<String, Object>) setting.get(j);
						SettingInfo settingInfo2  = SettingInfo.getSettingInfo(setting_);
//						if(settingInfo2.getType() == SettingTypeConstants.SETTING_USE_BAIDU_INPUT){
//							continue;
//						}
						settingInfo.getSettings().add(SettingInfo.getSettingInfo(setting_));
					}
//					else if(setting.get(j) instanceof ArrayList){
//						ArrayList<Object> setting_ = (ArrayList<Object>) setting.get(j);
//					}
				}
				settingsInfos.add(settingInfo);
			}
		}
		return settingsInfos;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<SettingInfo> getSettingInfosFromJson(JsonArray jsonArray) {
		// TODO Auto-generated method stub
		if(jsonArray == null){
			return null;
		}
		ArrayList<SettingInfo> settingsInfos = new ArrayList<SettingInfo>();
		for(int i = 0; i < jsonArray.size(); i++){
			JsonObject jsonObject = JsonMethed.getJsonObject(jsonArray.get(i));
			JsonArray jsonArray2 = JsonMethed.getJsonArray(jsonObject.get("settings"));
			if(jsonArray2 != null){
				SettingInfo settingInfo = new SettingInfo();
				for(int j = 0; j < jsonArray2.size(); j++){
					JsonObject jsonObject2 = JsonMethed.getJsonObject(jsonArray2.get(j));
					if(jsonObject != null){
						settingInfo.getSettings().add(SettingInfo.getSettingInfoFromJson(jsonObject2));
					}
//					else if(setting.get(j) instanceof ArrayList){
//						ArrayList<Object> setting_ = (ArrayList<Object>) setting.get(j);
//					}
				}
				settingsInfos.add(settingInfo);
			}
		}
		return settingsInfos;
	}

}
