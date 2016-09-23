package com.gloria.hbh.cachemanager;

import java.io.File;
import java.io.IOException;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.util.FileUtils;
import com.gloria.hbh.util.NetworkUtils;

public class ConfigCache {
	private static final String TAG = ConfigCache.class.getName();

	public static final int CONFIG_CACHE_MOBILE_TIMEOUT = 5 * 60 * 1000; // 1
																			// minute
	public static final int CONFIG_CACHE_WIFI_TIMEOUT = 5 * 60 * 1000; // 1
																		// minute

	public static String getUrlCache(String url) {
		if (url == null) {
			return null;
		}

		String result = null;
		int NETWORKTYPE = NetworkUtils.getNetworkState();
		String filePath = SetUrlToFilePath(BaseConstants.CACHE_FILETYPE_FILE, url);
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			long expiredTime = System.currentTimeMillis() - file.lastModified();
			// Log.d(TAG, file.getAbsolutePath() + " expiredTime:" +
			// expiredTime/60000 + "min");
			// 1. in case the system time is incorrect (the time is turn back
			// long ago)
			// 2. when the network is invalid, you can only read the cache
			if (NETWORKTYPE != NetworkUtils.NETWORK_NONE && expiredTime < 0) {
				return null;
			}
			if (NETWORKTYPE == NetworkUtils.NETWORK_WIFI && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
				return null;
			} else if (NETWORKTYPE == NetworkUtils.NETWORK_MOBILE && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
				return null;
			}
			try {
				result = FileUtils.readTextFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String SetUrlToFilePath(int fileType, String url) {
		String filePath = "";
		switch (fileType) {
		case BaseConstants.CACHE_FILETYPE_FILE:
			filePath = BaseConstants.CACHE_FILE_PATH;
			break;
		default:
			break;
		}
		filePath += url.replaceAll("[/|&|?|:|%]+", "_") + ".dat";
		return filePath;
	}

	public static void setUrlCache(String data, String url) {
		String filePath = SetUrlToFilePath(BaseConstants.CACHE_FILETYPE_FILE, url);
		File file = new File(filePath);
		try {
			// 创建缓存数据到磁盘，就是创建文件
			FileUtils.writeTextFile(file, data);
		} catch (IOException e) {
			// Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
			e.printStackTrace();
		}
	}
}
