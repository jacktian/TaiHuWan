package com.gloria.hbh.data.forum;

import java.util.ArrayList;
import java.util.List;

import com.gloria.hbh.data.forum.BaseData;
import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.parse.ParseObject;

public class HandlinesInfo extends BaseData{
	
	private ArrayList<HandlinesBasicInfo> handlineslist;
	private ArrayList<HandlinesBasicInfo> handlinesimg;
	
	public ArrayList<HandlinesBasicInfo> getHandlinesImg() {
		if(handlinesimg == null){
			handlinesimg = new ArrayList<HandlinesBasicInfo>();
		}
		return handlinesimg;
	}
	
	public ArrayList<HandlinesBasicInfo> getHandlinesList() {
		if(handlineslist == null){
			handlineslist = new ArrayList<HandlinesBasicInfo>(1);
		}
		return handlineslist;
	}
	
	public static HandlinesInfo getHandlinesInfoFromJsonObject(JsonObject jsonObject_imgs,JsonObject jsonObject_lists) {
		if(jsonObject_imgs == null || jsonObject_lists == null){
			return null;
		}
		HandlinesInfo handlinesInfo = new HandlinesInfo();
		try {
			JsonArray jsonArray_page = JsonMethed.getJsonArray(jsonObject_lists.get("page"));
			if(jsonArray_page != null){
				getPageFromJson(jsonArray_page);
			}
			JsonArray jsonArray_imgs_list = JsonMethed.getJsonArray(jsonObject_imgs.get("list"));
			JsonArray jsonArray_lists_list = JsonMethed.getJsonArray(jsonObject_lists.get("list"));
			if(jsonArray_imgs_list != null){
				for(int i = 0; i < jsonArray_imgs_list.size(); i++){
					JsonObject jsonObject_imgs_list = JsonMethed.getJsonObject(jsonArray_imgs_list.get(i));
					handlinesInfo.getHandlinesImg().add(HandlinesBasicInfo.getHandlinesBasicInfoFromJsonObject(jsonObject_imgs_list));
				}
			}
			if(jsonArray_lists_list != null){
				for(int i = 0; i < jsonArray_lists_list.size(); i++){
					JsonObject jsonObject_lists_list = JsonMethed.getJsonObject(jsonArray_lists_list.get(i));
					handlinesInfo.getHandlinesList().add(HandlinesBasicInfo.getHandlinesBasicInfoFromJsonObject(jsonObject_lists_list));
				}
			}
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return handlinesInfo;
	}

	public static ArrayList<HandlinesBasicInfo> getHandlinesInfoFromParseObject(
			List<ParseObject> images) {
		ArrayList<HandlinesBasicInfo> imgs = new ArrayList<HandlinesBasicInfo>(1);
		if(images != null){
			for(int i = 0; i < images.size(); i++){
				ParseObject parseObject = images.get(i);
				HandlinesBasicInfo handlinesInfo = new HandlinesBasicInfo();
				handlinesInfo.setTitle(parseObject.getString("title"));
				handlinesInfo.setDescrip(parseObject.getString("description"));
				handlinesInfo.setImg(parseObject.getParseFile("imageFile").getUrl());
				handlinesInfo.setAuthor(parseObject.getString("date"));
				imgs.add(handlinesInfo);
			}
		}
		return imgs;
	}
}