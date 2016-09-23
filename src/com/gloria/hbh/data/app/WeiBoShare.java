package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/*
 * 微博分享的数据
 */
public class WeiBoShare {
	String key = ""; // oauth授权的Key
	String secret = ""; //// oauth授权的Secret

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public static WeiBoShare getWeiBoShare(HashMap<String, Object> root) {
		if (root == null) {
			return null;
		}
		WeiBoShare weiBoShare = new WeiBoShare();
		weiBoShare.setKey((String) root.get("key"));
		weiBoShare.setSecret((String) root.get("secret"));
		return weiBoShare;
	}

	public static WeiBoShare getFromJsonObject(JsonObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		WeiBoShare weiBoShare = new WeiBoShare();
		try {
			weiBoShare.setKey(JsonMethed.getJsonString(jsonObject.get("key")));
			weiBoShare.setSecret(JsonMethed.getJsonString(jsonObject.get("secret")));
		} catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return weiBoShare;
	}
}
