package com.gloria.hbh.data.forum;

import java.io.File;
import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.FileUtils;
import com.google.gson.JsonObject;

public class ImageInfo {
	
	public class ImgScaleTypeConstants {
		//ͼƬ��������
		public static final int IMGTYPE_INYERCEPT = 0;  //��ȡ
		public static final int IMGTYPE_SCALE = 1;  //����
		public static final int IMGTYPE_NORMAL = 2;  //ԭͼ
		public static final int IMGTYPE_HOME = 1;  //��ҳ�Ŵ�
	}
	
	static int IMGTYPE_NORMAL = 0;
	static int IMGTYPE_FACE = 1;
	private String code;
	private String path;
	private int imgtype = IMGTYPE_NORMAL; //0������ͼƬ   1:һ��ͼƬ  
	private String url; //����URLȡͼ�ĵ�ַ
	private String miniUrl; //����URLȡͼ�ĵ�ַ
	public String getMiniurl() {
//		if(miniurl != null && !miniurl.contains("http://")){
//			miniurl = BaseConfig.url + miniurl;
//		}
		return miniUrl;
	}

	public void setMiniurl(String miniurl) {
		this.miniUrl = miniurl;
	}

	private String motify_date = "0"; //ͼƬ�޸�ʱ��
		
	public int getImgType() {
		return imgtype;
	}

	public void setImgType(int imgtype) {
		this.imgtype = imgtype;
	}

	public String getMotifyDate() {
		if(motify_date == null || motify_date.equals("")){
			motify_date = "0";
		}
		return motify_date;
	}

	public void setMotifyDate(String motify_date) {
		if(motify_date == null){
			return;
		}
		this.motify_date = motify_date;
	}
	
	public ImageInfo(){
		
	}

	public ImageInfo(String urlString){
		this.path = urlString;
		setTypeByUrl(path);
	}

	public ImageInfo(String code, String path){
		this.code = code;
		this.path = path;
	}

	public String getCode(){
		return code;
	}

	public String getPath(){
		return path;
	}
	
	private void setTypeByUrl(String url) {
		//URL����
		if(url.contains(BaseConstants.FACE_IMG_CONTAIN_PATH)){
			imgtype = IMGTYPE_FACE;
		}
		imgtype = IMGTYPE_NORMAL;
	}
	
	/*
	 * ��ȡͼƬ�����ַ
	 * ��Ϊ��ʱ����ʾͼƬ���ڣ�
	 */
	public String  getImgSavePath(){
		String img_save_path = "";
		String fileDir = ""; 		// ·����
		String fileName="";    	//�ļ���
		final String filePath;		//�ļ�����·��
		
		//�ж��Ǳ��飬����һ���ͼƬ���Ӷ��Ӳ�ͬ��·���ж�ͼƬ�Ƿ����
		//TODO ����������������������
		fileName = url.substring(url.lastIndexOf('/') + 1);
		fileName += url.replaceAll("[/|&|?|:|%]+", "_");
		
		fileDir = BaseConstants.CACHE_IMG_PATH;		
		filePath=fileDir+fileName;
		if(FileUtils.isHasSDCard()){
			//�ļ�����
			File file = new File(filePath);
			if (file.exists()){
				img_save_path = filePath;
			}
		}
		return img_save_path;
	}

	public String getUrl() {
//		if(url != null && !url.equals("")
//				&& !url.contains(BaseConfig.url)){
//			url = BaseConfig.url+url;
//		}
		return url;
	}

	public void setUrl(String url) {
		if(url == null){
			return;
		}
		this.url = url;
	}

	public static ImageInfo getImageInfo(HashMap<String, Object> root) {
		if(root == null){
			return null;
		}
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setUrl((String)root.get("url"));
		imageInfo.setMotifyDate((String)root.get("motify_date"));
		return imageInfo;
	}

	public static ImageInfo getImageInfoFromJson(JsonObject jsonObject) {
		if(jsonObject == null){
			return null;
		}
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setUrl(JsonMethed.getJsonString(jsonObject.get("url")));
		imageInfo.setMiniurl(JsonMethed.getJsonString(jsonObject.get("miniUrl")));
		imageInfo.setMotifyDate(JsonMethed.getJsonString(jsonObject.get("motify_date")));
		return imageInfo;
	}
}
