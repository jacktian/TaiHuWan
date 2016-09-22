package com.gloria.hbh.datadispose;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
* 文 件 名 : JsonMethed
* 创 建 人： gejian
* 日     期：2012-8-7
* 修 改 人：gejian
* 日    期：2012-8-7
* 描    述：Json数据处理类
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
		return ""; //默认""
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
		return 0; //默认==0
	}
	
	public static Boolean getJsonBoolean(JsonElement jsonElement)
	{
		if(jsonElement != null && !jsonElement.isJsonNull()&&jsonElement.isJsonPrimitive()){
			return jsonElement.getAsBoolean();
		}
		return false;  //默认false
	}
}
