package com.gloria.hbh.constant;

import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.data.forum.ImageInfo.ImgScaleTypeConstants;

/**
 * 应用的基本配置
 * 
 * @author gejian
 *
 */
public class BaseConfig {

	// public static int perpage =
	// Integer.valueOf(Plist.getInstance().getForum().getSetting_PAGE_COUNT().getValue());
	// //每页显示条数
	public static int perpage = 20;

	public static void resetPerpage(int perpage) {
		BaseConfig.perpage = perpage;
	}

	public static int getPerpage() {
		return perpage;
	}

	/*
	 * 设置每页显示条数
	 */
	public void setPerPag(int _perpage) {
		perpage = 20;
	}

	/*
	 * 正式网站地址
	 */
	public static String url = Plist.getInstance().getForum().getForumInfo().getHostUrl(); // 正式站

	/*
	 * 网站API地址
	 */
	public static String urlString = Plist.getInstance().getForum().getForumInfo().getApiUrl(); // 正式站

	/*
	 * 检查版本更新
	 */
	public static String checkupdate = "http://go.glavesoft.com/t/android_update.txt";

	/*
	 * 获取缩略图（width，height）
	 */
	public static String requestImageUrl(int width, int height, String url, int type) { // type:缩放类型
		String result = "";

		// if(!url.contains("/")){
		// return url;
		// }
		// String fileName = url.substring(url.lastIndexOf('/') + 1);
		// if(!fileName.contains(".")){
		// return url;
		// }
		// String Suffix = fileName.substring(fileName.lastIndexOf('.'));
		// if(Suffix.equalsIgnoreCase(".gif")){
		// return url;
		// }

		switch (type) {
		case ImgScaleTypeConstants.IMGTYPE_NORMAL:
			result = url;
			break;
		case ImgScaleTypeConstants.IMGTYPE_INYERCEPT:
			result = urlString + "phone_image.php?width=" + width + "&height=" + height + "&scale=0&path=" + url;
			break;
		case ImgScaleTypeConstants.IMGTYPE_SCALE:
			result = urlString + "phone_image.php?width=" + width + "&height=" + height + "&scale=1&path=" + url;
			break;
		default:
			result = urlString + "phone_image.php?width=" + width + "&height=" + height + "&scale=1&path=" + url;
			break;
		}

		// result = url;
		return result;
	}

	/*
	 * 某个城市天气信息
	 */
	public static String requestWeatherInfoUrl(String code) {
		String url = "http://www.weather.com.cn/data/cityinfo/" + code + ".html";
		return url;
	}

}
