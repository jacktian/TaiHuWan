package com.gloria.hbh.data.forum;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

/**
 * 基本数据结构
 * @author gejian
 *
 */
public class BaseData {
	
	private int result = 0; //0:不成功 1: 成功（默认）
	private String message = "OK";//错误时返回错误原因,成功返回"OK" 
	
	private int firstpos = 0; //listview上次显示的第一个Item
	
	private static Page page; //页码
	
	public static Page getPage() {
		if(page == null){
			page = new Page();
		}
		return page;
	}

	public int getFirstpos() {
		return firstpos;
	}

	public void setFirstpos(int firstpos) {
		this.firstpos = firstpos;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	String webString="";//服务器返回原始信息
	
	public String getWebString(){
		return "服务器返回："+webString;
	}
	
	public void setWebString(String str){
		webString=str;
	}
	
	public static Page getPageFromJson(JsonArray jsonArray){
		if(jsonArray != null){
			try{
			}catch (JsonParseException e) {
			} catch (Exception e) {
			}
			
			getPage().setPerPage(JsonMethed.getJsonInt(jsonArray.get(0)));
			getPage().setCurPage(JsonMethed.getJsonInt(jsonArray.get(1)));
			getPage().setTopic(JsonMethed.getJsonInt(jsonArray.get(2)));
		}
		return getPage();
	}
}
