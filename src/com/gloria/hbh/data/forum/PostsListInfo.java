package com.gloria.hbh.data.forum;

import java.util.ArrayList;

import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PostsListInfo  extends BaseData{

	public ArrayList<PostInfo> postInfos;
	public ArrayList<HandlinesBasicInfo> handlinesimg;
	
	public ArrayList<PostInfo> getPostInfos() {
		if(postInfos == null){
			postInfos = new ArrayList<PostInfo>(1);
		}
		return postInfos;
	}

	public void setPostInfos(ArrayList<PostInfo> postInfos) {
		this.postInfos = postInfos;
	}
	
	public ArrayList<HandlinesBasicInfo> getHandlinesImg() {
		if(handlinesimg == null){
			handlinesimg = new ArrayList<HandlinesBasicInfo>(1);
		}
		return handlinesimg;
	}

	public void setHandlinesImg(ArrayList<HandlinesBasicInfo> handlinesimg) {
		this.handlinesimg = handlinesimg;
	}

	@SuppressWarnings("static-access")
	public static PostsListInfo getPostsListInfoFromJsonObject(int type,JsonObject jsonObject) {
		if(jsonObject == null){
			return null;
		}
		PostsListInfo postsListInfo = new PostsListInfo();
		try {
			JsonArray jsonArray_page = JsonMethed.getJsonArray(jsonObject.get("page"));
			if(jsonArray_page != null){
				getPageFromJson(jsonArray_page);
			}
			
			JsonArray jsonArray_list = JsonMethed.getJsonArray(jsonObject.get("list"));
			if(jsonArray_list != null){
				for(int i = 0; i < jsonArray_list.size(); i++){
					JsonObject jsonObjec_list = JsonMethed.getJsonObject(jsonArray_list.get(i));
					postsListInfo.getPostInfos().add(PostInfo.getPostBasicFromJsonObject(type,jsonObjec_list));
				}
			}
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return postsListInfo;
	}
	
	public static PostsListInfo getPostsListInfoFromJsonObject(JsonObject jsonObject) {
		if(jsonObject == null){
			return null;
		}
		PostsListInfo postsListInfo = new PostsListInfo();
		try {
			JsonArray jsonArray_page = JsonMethed.getJsonArray(jsonObject.get("page"));
			if(jsonArray_page != null){
				getPageFromJson(jsonArray_page);
			}
			
			JsonArray jsonArray_list = JsonMethed.getJsonArray(jsonObject.get("list"));
			if(jsonArray_list != null){
				for(int i = 0; i < jsonArray_list.size(); i++){
					JsonObject jsonObjec_list = JsonMethed.getJsonObject(jsonArray_list.get(i));
					postsListInfo.getPostInfos().add(PostInfo.getPostBasicFromJsonObject(jsonObjec_list));
				}
			}
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return postsListInfo;
	}

	public static PostsListInfo getHandlinesInfoFromJsonObject(JsonObject jsonObject_imgs) {
		if(jsonObject_imgs == null){
			return null;
		}
		PostsListInfo handlinesInfo = new PostsListInfo();
		try {
			JsonArray jsonArray_page = JsonMethed.getJsonArray(jsonObject_imgs.get("page"));
			if(jsonArray_page != null){
				getPageFromJson(jsonArray_page);
			}
			JsonArray jsonArray_imgs_list = JsonMethed.getJsonArray(jsonObject_imgs.get("list"));
			if(jsonArray_imgs_list != null){
				for(int i = 0; i < jsonArray_imgs_list.size(); i++){
					JsonObject jsonObject_imgs_list = JsonMethed.getJsonObject(jsonArray_imgs_list.get(i));
					handlinesInfo.getHandlinesImg().add(HandlinesBasicInfo.getHandlinesBasicInfoFromJsonObject(jsonObject_imgs_list));
				}
			}
		}catch (JsonParseException e) {
		}catch (Exception e) {
		}
		return handlinesInfo;
	}
	
}
