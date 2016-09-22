package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.PreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * ��̳������Ϣ
 * @author gejian
 * 2013-1-23
 */
public class ForumInfo {
	
	public class PhoneTypeConstants {
		public static final String PhoneType_IPHONE = "1"; //iphone
		public static final String PhoneType_ANDROID = "2"; //android
		public static final String PhoneType_WP = "3"; //WP
	}
	
	public class FeedBackTypeConstants {
		public static final String FeedBackType_CRASH = "1"; //��������
		public static final String FeedBackType_SUGGESTION = "2"; //�������
	}
	
	String forum_type = ""; //��̳������
	String phone_type = PhoneTypeConstants.PhoneType_ANDROID; //�ֻ�����  1��iphone  2����׿   3��WP
	String back_type = FeedBackTypeConstants.FeedBackType_CRASH; //�������� 1��������Ϣ�ύ  2���������
	String company_id = ""; //��̳������˾��id��
	String host_url = "";  //��̳��վ��url��ַ
	String api_url = "";   //api�ӿ�λ����վ��url��ַ
	String min_post_word_count = ""; //��̳�����������ַ���
	FunctionInfo functionInfo = null; //��̳��֧�ֵ��ڲ�����
	
	public String getForumType() {
		return forum_type;
	}

	public void setForumType(String forum_type) {
		this.forum_type = forum_type;
	}

	public String getCompanyId() {
		return company_id;
	}

	public void setCompanyId(String company_id) {
		this.company_id = company_id;
	}

	public String getHostUrl() {
		return host_url;
	}

	public void setHostUrl(String host_url) {
		this.host_url = host_url;
	}

	public String getApiUrl() {
		return api_url;
	}

	public void setApiUrl(String api_url) {
		this.api_url = api_url;
	}

	public String getMinPostWordCount() {
		return min_post_word_count;
	}

	public void setMinPostWordCount(String min_post_word_count) {
		this.min_post_word_count = min_post_word_count;
	}

	public void setFunctionInfo(FunctionInfo functionInfo) {
		this.functionInfo = functionInfo;
	}

	@SuppressWarnings("unchecked")
	public static ForumInfo getForumInfo(HashMap<String, Object> root) {
		// TODO Auto-generated method stub
		if(root == null){
			return null;
		}
		ForumInfo forumInfo = new ForumInfo();
		forumInfo.setApiUrl((String)root.get("api_url"));
		forumInfo.setCompanyId((String)root.get("company_id"));
		forumInfo.setForumType((String)root.get("forum_type"));
		forumInfo.setHostUrl((String)root.get("host_url"));
		forumInfo.setMinPostWordCount((String)root.get("min_post_word_count"));
		forumInfo.setFunctionInfo(FunctionInfo.getFunctionInfo((HashMap<String, Object>)root.get("function_support")));
		return forumInfo;
	}

}
