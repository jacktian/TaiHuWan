package com.gloria.hbh.data.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.AssetManager;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.data.forum.StoreInfo;
import com.gloria.hbh.handle.PListParser;
import com.google.gson.JsonParseException;

public class RecommondSightspots {
	private static RecommondSightspots instance;
	private ArrayList<HandlinesBasicInfo> list;
	
	public ArrayList<HandlinesBasicInfo> getList() {
		if(list == null){
			list = new ArrayList<HandlinesBasicInfo>(1);
		}
		return list;
	}

	public void setList(ArrayList<HandlinesBasicInfo> list) {
		this.list = list;
	} 
	
	@SuppressWarnings("unchecked")
	public static RecommondSightspots getInstance()   { 
    	if(null == instance){   
    		instance = new RecommondSightspots(); 
    		//��ȡ(����)�ļ���ͬʱCheck�����ļ������ر��档������ͼƬ��
    		InputStream is = null;
    		try{
    			AssetManager assetManager = BaseApplication.getInstance().getApplicationContext().getAssets();
    			is = assetManager.open("RecommondSightspots.plist");
    			PListParser parser = new PListParser(is);
    			getInfo((ArrayList<Object>) parser.root);
    		}catch (JsonParseException e) {
    		}catch (Exception e) {
    		}
    	}   
    	return instance;    
    }
	
	private static void getInfo(ArrayList<Object> root) {
		if(root == null){
			return;
		}
		for(int i = 0; i < root.size(); i++){
			HashMap<String, Object> info = (HashMap<String, Object>) root.get(i);
			HandlinesBasicInfo handlinesBasicInfo = new HandlinesBasicInfo();
			handlinesBasicInfo.setTitle((String)info.get("title"));
			handlinesBasicInfo.setDescrip((String)info.get("subtitle"));
			handlinesBasicInfo.setImg("assets://" + (String)info.get("image"));
			getInstance().getList().add(handlinesBasicInfo);
		}
	}
}
