package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.PreferencesUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * ��̳��֧�ֵ��ڲ�����
 * @author gejian
 * 2013-1-23
 */
public class FunctionInfo {
	boolean autologin = true;  //true ���Զ���¼
	boolean isCreatShortCut = false;  //true ���������ͼ��  false:���������ͼ��
	boolean isSendChanle = false;  //true ��֧����������  false:��֧��
	boolean isSupportDataAnalysis = false;  //true ��������ݷ���  
	boolean isSupportPushInfo = false;  //true ��֧������
	boolean isSupportSinaShare = false;  //true ��֧������
	boolean isSupportBaiDuInput = false;  //true ��֧�ְٶ�����
	
	WeiBoShareInfo weiBoShareInfo = null;
	
//	int postsShowMode = 0; // 0���鿴��������   1��ֻ��¥��
//	int textSizeMode = 0;  //0:С��14sp��  1����(16sp)  2����(18sp) 3������(20sp)
//	int perpage = 20;	// ����ÿҳ��ʾ������
//	boolean isShowImageWithoutWIFI = false;  // false:�����÷�WIFI�½�ֹ��ͼ   true:��WIFI�½�ֹ��ͼ
//	boolean pushInfoMode = false; //��֧�����͵������   true:Ĭ������ false:Ĭ�ϲ�����    
//	boolean isShareToSina = false; //֧�����˷���  false:������
	
	public boolean isAutologin() {
		return autologin;
	}
	public void setAutologin(boolean autologin) {
		this.autologin = autologin;
	}
	public boolean isCreatShortCut() {
		return isCreatShortCut;
	}
	public void setCreatShortCut(boolean isCreatShortCut) {
		this.isCreatShortCut = isCreatShortCut;
	}
	public boolean isSendChanle() {
		return isSendChanle;
	}
	public void setSendChanle(boolean isSendChanle) {
		this.isSendChanle = isSendChanle;
	}
	public boolean isSupportDataAnalysis() {
		return isSupportDataAnalysis;
	}
	public void setSupportDataAnalysis(String isSupportDataAnalysis) {
		if(isSupportDataAnalysis == null){
			return;
		}
		if(isSupportDataAnalysis.equalsIgnoreCase("YES")){
			this.isSupportDataAnalysis = true;
		}else if(isSupportDataAnalysis.equalsIgnoreCase("NO")){
			this.isSupportDataAnalysis = false;
		}
	}
	public boolean isSupportPushInfo() {
		return isSupportPushInfo;
	}
	public void setSupportPushInfo(String isSupportPushInfo) {
		if(isSupportPushInfo == null){
			return;
		}
		if(isSupportPushInfo.equalsIgnoreCase("YES")){
			this.isSupportPushInfo = true;
		}else if(isSupportPushInfo.equalsIgnoreCase("NO")){
			this.isSupportPushInfo = false;
		}
	}
	public boolean isSupportSinaShare() {
		return isSupportSinaShare;
	}
	public void setSupportSinaShare(String isSupportSinaShare) {
		if(isSupportSinaShare == null){
			return;
		}
		if(isSupportSinaShare.equalsIgnoreCase("YES")){
			this.isSupportSinaShare = true;
		}else if(isSupportSinaShare.equalsIgnoreCase("NO")){
			this.isSupportSinaShare = false;
		}
	}
	public boolean isSupportBaiDuInput() {
		return isSupportBaiDuInput;
	}
	public void setSupportBaiDuInput(String isSupportBaiDuInput) {
		if(isSupportBaiDuInput == null){
			return;
		}
		if(isSupportBaiDuInput.equalsIgnoreCase("YES")){
			this.isSupportBaiDuInput = true;
		}else if(isSupportBaiDuInput.equalsIgnoreCase("NO")){
			this.isSupportBaiDuInput = false;
		}
	}
	
	public WeiBoShareInfo getWeiBoShareInfo() {
		if(weiBoShareInfo == null){
			//��ȡ���ص���������
    		String jsonString_lastLogin =  PreferencesUtils.getStringPreferences(
		  			BaseConstants.WeiBoShare_NAME, BaseConstants.SharedPreferences_WeiBoShareInfo, null);
			if(jsonString_lastLogin!=null&&!jsonString_lastLogin.equals("")){
				JsonObject jsonObject= JsonMethed.getJsonObject(JsonMethed.getJsonElement(jsonString_lastLogin));
				weiBoShareInfo = WeiBoShareInfo.getFromJsonObject(jsonObject);
			}
//			else{
//				weiBoShareInfo = new WeiBoShareInfo();
//				Gson gson=new Gson();
//				String gsonString=gson.toJson(weiBoShareInfo);
//				//��������
//				PreferencesUtils.setStringPreferences(BaseConstants.WeiBoShare_NAME, BaseConstants.SharedPreferences_WeiBoShareInfo, gsonString);
//			}
		}
		return weiBoShareInfo;
	}

	public void setWeiBoShareInfo(WeiBoShareInfo weiBoShareInfo) {
		this.weiBoShareInfo = weiBoShareInfo;
	}
	
	public static FunctionInfo getFromJsonObject(JsonObject jsonObject){
		FunctionInfo functionInfo = new FunctionInfo();
		try {
			functionInfo.setAutologin(JsonMethed.getJsonBoolean(jsonObject.get("autologin")));
			functionInfo.setCreatShortCut(JsonMethed.getJsonBoolean(jsonObject.get("isCreatShortCut")));
			functionInfo.setSendChanle(JsonMethed.getJsonBoolean(jsonObject.get("isSendChanle")));
			functionInfo.setSupportDataAnalysis(JsonMethed.getJsonString(jsonObject.get("isSupportDataAnalysis")));
			functionInfo.setSupportPushInfo(JsonMethed.getJsonString(jsonObject.get("isSupportPushInfo")));
			functionInfo.setSupportSinaShare(JsonMethed.getJsonString(jsonObject.get("isSupportSinaShare")));
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return functionInfo;
	}

	@SuppressWarnings("unchecked")
	public static FunctionInfo getFunctionInfo(HashMap<String, Object> root) {
		if(root == null){
			return null;
		}
		FunctionInfo functionInfo = new FunctionInfo();
		functionInfo.setSupportSinaShare((String)root.get("share"));
		functionInfo.setSupportPushInfo((String)root.get("push"));
		functionInfo.setSupportDataAnalysis((String)root.get("statistics"));
		functionInfo.setSupportBaiDuInput((String)root.get("baidu_input"));
		functionInfo.setWeiBoShareInfo(WeiBoShareInfo.getWeiBoShareInfo((HashMap<String, Object>)root.get("weibo_share")));
		return functionInfo;
	}

}
