package com.gloria.hbh.data.app;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * 更新文件数据结构
 * 
 * @author gejian
 * @since 2013-1-21
 */
public class UpdateInfo {
	String verName = ""; // 版本名称
	int verCode = 0; // 版本号
	int lastVerCode = 0; // 最新版本号
	String lastVerName = ""; // 最新版本名称
	String apkUrl = ""; // apk更新地址
	String apiHost = ""; // apk应用的域名
	String updateContent = ""; // 更新内容

	public String getUpdateContent() {
		return updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		if (verCode == null || verCode.equals("") || verCode.equals("null")) {
			this.verCode = 0;
			return;
		}
		this.verCode = Integer.valueOf(verCode);
	}

	public int getLastVerCode() {
		return lastVerCode;
	}

	public void setLastVerCode(int lastVerCode) {
		this.lastVerCode = lastVerCode;
	}

	public String getLastVerName() {
		return lastVerName;
	}

	public void setLastVerName(String lastVerName) {
		this.lastVerName = lastVerName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getApiHost() {
		return apiHost;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public static UpdateInfo getFromJsonObject(JsonObject jsonObject) {
		UpdateInfo updateInfo = null;
		if (jsonObject == null) {
			return updateInfo;
		}
		updateInfo = new UpdateInfo();
		try {
			updateInfo.setVerName(JsonMethed.getJsonString(jsonObject.get("verName")));
			updateInfo.setVerCode(JsonMethed.getJsonString(jsonObject.get("verCode")));
			updateInfo.setLastVerCode(JsonMethed.getJsonInt(jsonObject.get("lastVerCode")));
			updateInfo.setApkUrl(JsonMethed.getJsonString(jsonObject.get("apkUrl")));
			updateInfo.setApiHost(JsonMethed.getJsonString(jsonObject.get("apiHost")));
			updateInfo.setUpdateContent(JsonMethed.getJsonString(jsonObject.get("updateContent")));
		} catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return updateInfo;
	}
}
