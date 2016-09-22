package com.gloria.hbh.data.forum;

import java.io.File;
import java.util.HashMap;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.util.FileUtils;
import com.google.gson.JsonObject;

public class ImageInfo {
	
	public class ImgScaleTypeConstants {
		//图片缩放类型
		public static final int IMGTYPE_INYERCEPT = 0;  //截取
		public static final int IMGTYPE_SCALE = 1;  //缩放
		public static final int IMGTYPE_NORMAL = 2;  //原图
		public static final int IMGTYPE_HOME = 1;  //首页放大
	}
	
	static int IMGTYPE_NORMAL = 0;
	static int IMGTYPE_FACE = 1;
	private String code;
	private String path;
	private int imgtype = IMGTYPE_NORMAL; //0：表情图片   1:一般图片  
	private String url; //网络URL取图的地址
	private String miniUrl; //网络URL取图的地址
	public String getMiniurl() {
//		if(miniurl != null && !miniurl.contains("http://")){
//			miniurl = BaseConfig.url + miniurl;
//		}
		return miniUrl;
	}

	public void setMiniurl(String miniurl) {
		this.miniUrl = miniurl;
	}

	private String motify_date = "0"; //图片修改时间
		
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
		//URL类型
		if(url.contains(BaseConstants.FACE_IMG_CONTAIN_PATH)){
			imgtype = IMGTYPE_FACE;
		}
		imgtype = IMGTYPE_NORMAL;
	}
	
	/*
	 * 获取图片保存地址
	 * 不为空时，表示图片存在；
	 */
	public String  getImgSavePath(){
		String img_save_path = "";
		String fileDir = ""; 		// 路径名
		String fileName="";    	//文件名
		final String filePath;		//文件绝对路径
		
		//判断是表情，还是一般的图片，从而从不同的路径判断图片是否存在
		//TODO 。。。。。。。。。。。
		fileName = url.substring(url.lastIndexOf('/') + 1);
		fileName += url.replaceAll("[/|&|?|:|%]+", "_");
		
		fileDir = BaseConstants.CACHE_IMG_PATH;		
		filePath=fileDir+fileName;
		if(FileUtils.isHasSDCard()){
			//文件存在
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
