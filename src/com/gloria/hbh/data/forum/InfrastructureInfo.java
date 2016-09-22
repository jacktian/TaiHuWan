package com.gloria.hbh.data.forum;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.AssetManager;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.handle.PListParser;
import com.google.gson.JsonParseException;

/*
 * 公共设施信息
 */
public class InfrastructureInfo {
	
	private static InfrastructureInfo instance;   
	
	ArrayList<String> clinic;
	ArrayList<String> food;
	ArrayList<String> park;
	ArrayList<String> store;
	ArrayList<String> ticket;
	ArrayList<String> toilet;
	
	public ArrayList<String> getClinic() {
		if(clinic == null){
			clinic = new ArrayList<String>(1);
		}
		return clinic;
	}

	public void setClinic(ArrayList<String> clinic) {
		this.clinic = clinic;
	}

	public ArrayList<String> getFood() {
		if(food == null){
			food = new ArrayList<String>(1);
		}
		return food;
	}

	public void setFood(ArrayList<String> food) {
		this.food = food;
	}

	public ArrayList<String> getPark() {
		if(park == null){
			park = new ArrayList<String>(1);
		}
		return park;
	}

	public void setPark(ArrayList<String> park) {
		this.park = park;
	}

	public ArrayList<String> getStore() {
		if(store == null){
			store = new ArrayList<String>(1);
		}
		return store;
	}

	public void setStore(ArrayList<String> store) {
		this.store = store;
	}

	public ArrayList<String> getTicket() {
		if(ticket == null){
			ticket = new ArrayList<String>(1);
		}
		return ticket;
	}

	public void setTicket(ArrayList<String> ticket) {
		this.ticket = ticket;
	}

	public ArrayList<String> getToilet() {
		if(toilet == null){
			toilet = new ArrayList<String>(1);
		}
		return toilet;
	}

	public void setToilet(ArrayList<String> toilet) {
		this.toilet = toilet;
	}

	@SuppressWarnings("unchecked")
	public static InfrastructureInfo getInstance()   { 
    	if(null == instance){   
    		instance = new InfrastructureInfo(); 
    		//读取(本地)文件，同时Check配置文件，下载保存。（保存图片）
    		InputStream is = null;
    		try{
    			AssetManager assetManager = BaseApplication.getInstance().getApplicationContext().getAssets();
    			is = assetManager.open("MapData.plist");
    			PListParser parser = new PListParser(is);
    			getInfo((HashMap<String, Object>) parser.root);
    		}catch (JsonParseException e) {
    		}catch (Exception e) {
    		}
    	}   
    	return instance;    
    }
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getList(ArrayList<Object> root) {
		if(root == null){
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < root.size(); i++){
			HashMap<String, Object> items = (HashMap<String, Object>) root.get(i);
			list.add(((String) items.get("point")).trim().replace("{", "").replace("}", ""));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private static void getInfo(HashMap<String, Object> root) {
		getInstance().setClinic(getList((ArrayList<Object>)root.get("clinic")));
		getInstance().setFood(getList((ArrayList<Object>)root.get("food")));
		getInstance().setPark(getList((ArrayList<Object>)root.get("park")));
		getInstance().setStore(getList((ArrayList<Object>)root.get("store")));
		getInstance().setTicket(getList((ArrayList<Object>)root.get("ticket")));
		getInstance().setToilet(getList((ArrayList<Object>)root.get("toilet")));
	}

}
