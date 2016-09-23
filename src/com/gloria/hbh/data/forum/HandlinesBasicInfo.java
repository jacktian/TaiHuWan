package com.gloria.hbh.data.forum;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;

public class HandlinesBasicInfo {

	// private ArrayList<String> imgurl;
	private String author = "";
	private String authorid = "";
	private String img = "";
	private String url = "";
	private String title = "";
	private String tid = "";
	private String fid = "";
	private String descrip = "";

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorId() {
		return authorid;
	}

	public void setAuthorId(String authorid) {
		this.authorid = authorid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public static HandlinesBasicInfo getHandlinesBasicInfoFromJsonObject(JsonObject jsonObject) {
		HandlinesBasicInfo handlinesBasicInfo = new HandlinesBasicInfo();

		try {
			handlinesBasicInfo.setAuthor(JsonMethed.getJsonString(jsonObject.get("author")));
			handlinesBasicInfo.setImg(JsonMethed.getJsonString(jsonObject.get("imgurl")));
			handlinesBasicInfo.setTitle(JsonMethed.getJsonString(jsonObject.get("title")));
			handlinesBasicInfo.setUrl(JsonMethed.getJsonString(jsonObject.get("url")));
			handlinesBasicInfo.setTid(JsonMethed.getJsonString(jsonObject.get("tid")));
			handlinesBasicInfo.setDescrip(JsonMethed.getJsonString(jsonObject.get("descrip")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return handlinesBasicInfo;
	}
}
