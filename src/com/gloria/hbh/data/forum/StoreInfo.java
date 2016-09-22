package com.gloria.hbh.data.forum;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * ¡„ €µÍ–≈œ¢
 */
public class StoreInfo {
	String address;
	String name;
	String telephone;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public static StoreInfo getStoreInfo(HashMap<String, Object> root) {
		if(root == null){
			return null;
		}
		StoreInfo storeInfo = new StoreInfo();
		storeInfo.setName((String)root.get("name"));
		storeInfo.setAddress((String)root.get("address"));
		storeInfo.setTelephone((String)root.get("telephone"));
		return storeInfo;
	}
	
}
