package com.gloria.hbh.data.forum;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

/**
 * �������ݽṹ
 * @author gejian
 *
 */
public class BaseData {
	
	private int result = 0; //0:���ɹ� 1: �ɹ���Ĭ�ϣ�
	private String message = "OK";//����ʱ���ش���ԭ��,�ɹ�����"OK" 
	
	private int firstpos = 0; //listview�ϴ���ʾ�ĵ�һ��Item
	
	private static Page page; //ҳ��
	
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

	String webString="";//����������ԭʼ��Ϣ
	
	public String getWebString(){
		return "���������أ�"+webString;
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
