package com.gloria.hbh.data.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.AssetManager;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.forum.StoreInfo;
import com.gloria.hbh.handle.PListParser;
import com.google.gson.JsonParseException;

public class RetailStores {
	private static RetailStores instance;
	private ArrayList<StoreInfo> list;
	
	public ArrayList<StoreInfo> getList() {
		if(list == null){
			list = new ArrayList<StoreInfo>(1);
		}
		return list;
	}

	public void setList(ArrayList<StoreInfo> list) {
		this.list = list;
	} 
	
	@SuppressWarnings("unchecked")
	public static RetailStores getInstance()   { 
    	if(null == instance){   
    		instance = new RetailStores(); 
    		//读取(本地)文件，同时Check配置文件，下载保存。（保存图片）
    		InputStream is = null;
    		try{
    			AssetManager assetManager = BaseApplication.getInstance().getApplicationContext().getAssets();
    			is = assetManager.open("RetailStores.plist");
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
			getInstance().getList().add(StoreInfo.getStoreInfo(info));
		}
	}
}
