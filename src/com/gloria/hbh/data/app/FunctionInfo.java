package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.PreferencesUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * 论坛的支持的内部功能
 * 
 * @author gejian 2013-1-23
 */
public class FunctionInfo {
	boolean autologin = true; // true ：自动登录
	boolean isCreatShortCut = false; // true ：添加桌面图标 false:不添加桌面图标
	boolean isSendChanle = false; // true ：支持渠道分析 false:不支持
	boolean isSupportDataAnalysis = false; // true ：添加数据分析
	boolean isSupportPushInfo = false; // true ：支持推送
	boolean isSupportSinaShare = false; // true ：支持推送
	boolean isSupportBaiDuInput = false; // true ：支持百度输入

	WeiBoShareInfo weiBoShareInfo = null;

	// int postsShowMode = 0; // 0：查看所有内容 1：只看楼主
	// int textSizeMode = 0; //0:小（14sp） 1：中(16sp) 2：大(18sp) 3：超大(20sp)
	// int perpage = 20; // 帖子每页显示的条数
	// boolean isShowImageWithoutWIFI = false; // false:不设置非WIFI下禁止下图
	// true:非WIFI下禁止下图
	// boolean pushInfoMode = false; //在支持推送的情况下 true:默认推送 false:默认不推送
	// boolean isShareToSina = false; //支持新浪分享 false:不分享

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
		if (isSupportDataAnalysis == null) {
			return;
		}
		if (isSupportDataAnalysis.equalsIgnoreCase("YES")) {
			this.isSupportDataAnalysis = true;
		} else if (isSupportDataAnalysis.equalsIgnoreCase("NO")) {
			this.isSupportDataAnalysis = false;
		}
	}

	public boolean isSupportPushInfo() {
		return isSupportPushInfo;
	}

	public void setSupportPushInfo(String isSupportPushInfo) {
		if (isSupportPushInfo == null) {
			return;
		}
		if (isSupportPushInfo.equalsIgnoreCase("YES")) {
			this.isSupportPushInfo = true;
		} else if (isSupportPushInfo.equalsIgnoreCase("NO")) {
			this.isSupportPushInfo = false;
		}
	}

	public boolean isSupportSinaShare() {
		return isSupportSinaShare;
	}

	public void setSupportSinaShare(String isSupportSinaShare) {
		if (isSupportSinaShare == null) {
			return;
		}
		if (isSupportSinaShare.equalsIgnoreCase("YES")) {
			this.isSupportSinaShare = true;
		} else if (isSupportSinaShare.equalsIgnoreCase("NO")) {
			this.isSupportSinaShare = false;
		}
	}

	public boolean isSupportBaiDuInput() {
		return isSupportBaiDuInput;
	}

	public void setSupportBaiDuInput(String isSupportBaiDuInput) {
		if (isSupportBaiDuInput == null) {
			return;
		}
		if (isSupportBaiDuInput.equalsIgnoreCase("YES")) {
			this.isSupportBaiDuInput = true;
		} else if (isSupportBaiDuInput.equalsIgnoreCase("NO")) {
			this.isSupportBaiDuInput = false;
		}
	}

	public WeiBoShareInfo getWeiBoShareInfo() {
		if (weiBoShareInfo == null) {
			// 读取本地的配置数据
			String jsonString_lastLogin = PreferencesUtils.getStringPreferences(BaseConstants.WeiBoShare_NAME,
					BaseConstants.SharedPreferences_WeiBoShareInfo, null);
			if (jsonString_lastLogin != null && !jsonString_lastLogin.equals("")) {
				JsonObject jsonObject = JsonMethed.getJsonObject(JsonMethed.getJsonElement(jsonString_lastLogin));
				weiBoShareInfo = WeiBoShareInfo.getFromJsonObject(jsonObject);
			}
			// else{
			// weiBoShareInfo = new WeiBoShareInfo();
			// Gson gson=new Gson();
			// String gsonString=gson.toJson(weiBoShareInfo);
			// //保存配置
			// PreferencesUtils.setStringPreferences(BaseConstants.WeiBoShare_NAME,
			// BaseConstants.SharedPreferences_WeiBoShareInfo, gsonString);
			// }
		}
		return weiBoShareInfo;
	}

	public void setWeiBoShareInfo(WeiBoShareInfo weiBoShareInfo) {
		this.weiBoShareInfo = weiBoShareInfo;
	}

	public static FunctionInfo getFromJsonObject(JsonObject jsonObject) {
		FunctionInfo functionInfo = new FunctionInfo();
		try {
			functionInfo.setAutologin(JsonMethed.getJsonBoolean(jsonObject.get("autologin")));
			functionInfo.setCreatShortCut(JsonMethed.getJsonBoolean(jsonObject.get("isCreatShortCut")));
			functionInfo.setSendChanle(JsonMethed.getJsonBoolean(jsonObject.get("isSendChanle")));
			functionInfo.setSupportDataAnalysis(JsonMethed.getJsonString(jsonObject.get("isSupportDataAnalysis")));
			functionInfo.setSupportPushInfo(JsonMethed.getJsonString(jsonObject.get("isSupportPushInfo")));
			functionInfo.setSupportSinaShare(JsonMethed.getJsonString(jsonObject.get("isSupportSinaShare")));
		} catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return functionInfo;
	}

	@SuppressWarnings("unchecked")
	public static FunctionInfo getFunctionInfo(HashMap<String, Object> root) {
		if (root == null) {
			return null;
		}
		FunctionInfo functionInfo = new FunctionInfo();
		functionInfo.setSupportSinaShare((String) root.get("share"));
		functionInfo.setSupportPushInfo((String) root.get("push"));
		functionInfo.setSupportDataAnalysis((String) root.get("statistics"));
		functionInfo.setSupportBaiDuInput((String) root.get("baidu_input"));
		functionInfo
				.setWeiBoShareInfo(WeiBoShareInfo.getWeiBoShareInfo((HashMap<String, Object>) root.get("weibo_share")));
		return functionInfo;
	}

}
