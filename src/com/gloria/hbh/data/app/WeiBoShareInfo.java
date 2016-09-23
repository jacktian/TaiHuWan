package com.gloria.hbh.data.app;

import java.util.HashMap;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/*
 * 微博分享的数据列表    sina或QQ列表
 */
public class WeiBoShareInfo {

	WeiBoShare sina = null;
	WeiBoShare tencent = null;

	WeiBoShareInfo() {
		sina = new WeiBoShare();
		tencent = new WeiBoShare();
	}

	public WeiBoShare getSina() {
		return sina;
	}

	public void setSina(WeiBoShare sina) {
		this.sina = sina;
	}

	public WeiBoShare getTencent() {
		return tencent;
	}

	public void setTencent(WeiBoShare tencent) {
		this.tencent = tencent;
	}

	@SuppressWarnings("unchecked")
	public static WeiBoShareInfo getWeiBoShareInfo(HashMap<String, Object> root) {
		if (root == null) {
			return null;
		}
		WeiBoShareInfo weiBoShareInfo = new WeiBoShareInfo();
		weiBoShareInfo.setSina(WeiBoShare.getWeiBoShare((HashMap<String, Object>) root.get("sina")));
		weiBoShareInfo.setTencent(WeiBoShare.getWeiBoShare((HashMap<String, Object>) root.get("tencent")));
		return weiBoShareInfo;
	}

	public static WeiBoShareInfo getFromJsonObject(JsonObject jsonObject) {
		WeiBoShareInfo weiBoShareInfo = null;
		if (jsonObject != null) {
			weiBoShareInfo = new WeiBoShareInfo();
			JsonObject sina = JsonMethed.getJsonObject(jsonObject.get("sina"));
			JsonObject tencent = JsonMethed.getJsonObject(jsonObject.get("tencent"));
			try {
				if (sina != null) {
					weiBoShareInfo.setSina(WeiBoShare.getFromJsonObject(sina));
				}
				if (tencent != null) {
					weiBoShareInfo.setTencent(WeiBoShare.getFromJsonObject(tencent));
				}
			} catch (JsonParseException e) {
			} catch (Exception e) {
			}
		}
		return weiBoShareInfo;
	}
}
