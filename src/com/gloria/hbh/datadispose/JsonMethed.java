package com.gloria.hbh.datadispose;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
* �� �� �� : JsonMethed
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-7
* ��    ����Json���ݴ�����
*/
public class JsonMethed {
	
	public static JsonElement getJsonElement(String jsonString)
	{
		try{
			JsonElement jsonElement = new JsonParser().parse(jsonString);
			if(jsonElement != null && !jsonElement.isJsonNull()){
				return jsonElement;
			}
		}catch (JsonParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonArray getJsonArray(JsonElement jsonElement)
	{
		if(jsonElement != null &&!jsonElement.isJsonNull()&&jsonElement.isJsonArray()&& jsonElement.getAsJsonArray().size()!=0){
			return jsonElement.getAsJsonArray();
		}
		return null;
	}
	
	public static JsonObject getJsonObject(JsonElement jsonElement)
	{
		if(jsonElement != null && !jsonElement.isJsonNull()&&jsonElement.isJsonObject()){
			return jsonElement.getAsJsonObject();
		}
		return null;
	}
	
	public static String getJsonString(JsonElement jsonElement)
	{
		if(jsonElement != null && !jsonElement.isJsonNull()&&jsonElement.isJsonPrimitive()){
			return jsonElement.getAsString();
		}
		return ""; //Ĭ��""
	}
	
	@SuppressWarnings("null")
	public static Integer getJsonInt(JsonElement jsonElement)
	{
		if(jsonElement != null && !jsonElement.isJsonNull()&&jsonElement.isJsonPrimitive()){
			boolean isInt = true;
			try{
				int num = jsonElement.getAsInt();
				return num;
			}catch (JsonParseException e) {
				isInt = false;
				
			}catch (Exception e) {
			}
			if(!isInt){
				try{
					String str = jsonElement.getAsString();
					if(str != null || !str.equals("null") || !str.equals("") ){
						return Integer.valueOf(str);
					}
				}catch (JsonParseException e) {
				}catch (Exception e) {
				}
			}
		}
		return 0; //Ĭ��==0
	}
	
	public static Boolean getJsonBoolean(JsonElement jsonElement)
	{
		if(jsonElement != null && !jsonElement.isJsonNull()&&jsonElement.isJsonPrimitive()){
			return jsonElement.getAsBoolean();
		}
		return false;  //Ĭ��false
	}
}
