package com.gloria.hbh.constant;

import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.data.forum.ImageInfo.ImgScaleTypeConstants;

/**
 * Ӧ�õĻ�������
 * @author gejian
 *
 */
public class BaseConfig {

	//public static int perpage = Integer.valueOf(Plist.getInstance().getForum().getSetting_PAGE_COUNT().getValue()); //ÿҳ��ʾ����
	public static int perpage=20;
	public static void resetPerpage(int perpage) {
		BaseConfig.perpage = perpage;
	}
	
	public static int getPerpage() {
		return perpage;
	}

	/*
	 * ����ÿҳ��ʾ����
	 */
	public void setPerPag(int _perpage){
		perpage = 20;
	}

	/*
	 * ��ʽ��վ��ַ 
	 */
	public static String url = Plist.getInstance().getForum().getForumInfo().getHostUrl(); //��ʽվ
	
	/*
	 * ��վAPI��ַ
	 */
	public static String urlString = Plist.getInstance().getForum().getForumInfo().getApiUrl(); //��ʽվ

	/*
	 * ���汾����
	 */
	public static String checkupdate="http://go.glavesoft.com/t/android_update.txt";
	
	/*
	 * ��ȡ����ͼ��width��height��
	 */
	public static String requestImageUrl(int width,int height,String url,int type){ //type:��������
		String result = "";
		
//		if(!url.contains("/")){
//			return url;
//		}
//		String fileName = url.substring(url.lastIndexOf('/') + 1);
//		if(!fileName.contains(".")){
//			return url;
//		}
//		String Suffix = fileName.substring(fileName.lastIndexOf('.'));
//		if(Suffix.equalsIgnoreCase(".gif")){
//			return url;
//		}
		
		switch (type) {
		case ImgScaleTypeConstants.IMGTYPE_NORMAL:
			result = url;
			break;
		case ImgScaleTypeConstants.IMGTYPE_INYERCEPT:
			result = urlString+"phone_image.php?width="+width+"&height="+height+"&scale=0&path="+url;
			break;
		case ImgScaleTypeConstants.IMGTYPE_SCALE:
			result = urlString+"phone_image.php?width="+width+"&height="+height+"&scale=1&path="+url;
			break;
		default:
			result = urlString+"phone_image.php?width="+width+"&height="+height+"&scale=1&path="+url;
			break;
		}
		
//		result = url;
		return result;
	}

	/*
	 * ĳ������������Ϣ
	 */
	public static String requestWeatherInfoUrl(String code)
	{
		String url = "http://www.weather.com.cn/data/cityinfo/" + code + ".html";
		return url;
	}
	
}
