package com.gloria.hbh.data.forum;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;

/**
 * 鏂?浠?鍚?: WeatherItem 鍒?寤?浜猴細 gloria 鏃? 鏈燂細2013-4-7 淇?鏀?浜猴細gloria 鏃?
 * 鏈燂細2013-4-7 鎻? 杩帮細 澶╂皵淇℃伅瀛愰」
 */
public class WeatherItem {

	private String temp; // 褰撳墠鏃ユ湡鏄?8鏃ラ偅杩欑涓?釜鐨勬俯搴︿负19鏃ュ噷鏅ㄥ埌19鏃ヤ腑鍗堟槸鐨勬俯搴︼紝涓嬮潰浠ユ绫绘帹
	private String tempF; // 鍗庢皬娓╁害 鍚屼笂
	private String weather; // 鍚屾俯搴︿竴鏍蜂篃鏄?9鏃ュ噷鏅ㄤ篃鍙互璇存垚鏄?8鏃?3:59:59绉?/涓嬮潰绫绘帹
	private String img_s; // 瀵瑰簲鐨勬樉绀哄浘鐗囩紪鍙?
	private String img_e; // 瀵瑰簲鐨勬樉绀哄浘鐗囩紪鍙?

	private String img_title_s; // 18鏃ュ闂?
	private String img_title_e; // 19鏃ョ櫧澶?

	private String wind; // 涓?ぉ鐨勯鍔?
	private String fl; // 椋庡姏
	private String st;

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTempF() {
		return tempF;
	}

	public void setTempF(String tempF) {
		this.tempF = tempF;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getImg_S() {
		return img_s;
	}

	public void setImg_S(String img_s) {
		this.img_s = img_s;
	}

	public String getImg_E() {
		return img_e;
	}

	public void setImg_E(String img_e) {
		this.img_e = img_e;
	}

	public String getImg_Title_S() {
		return img_title_s;
	}

	public void setImg_Title_S(String img_title_s) {
		this.img_title_s = img_title_s;
	}

	public String getImg_Title_E() {
		return img_title_e;
	}

	public void setImg_Title_E(String img_title_e) {
		this.img_title_e = img_title_e;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	/*
	 * 鏋勯?鍑芥暟
	 */
	public WeatherItem() {
		super();
	}

	public WeatherItem(JsonObject jsonObject) {
		super();
		if (jsonObject == null) {
			return;
		}
		temp = JsonMethed.getJsonString(jsonObject.get("temp"));
		tempF = JsonMethed.getJsonString(jsonObject.get("tempF"));
		weather = JsonMethed.getJsonString(jsonObject.get("weather"));
		img_s = JsonMethed.getJsonString(jsonObject.get("img_s"));
		img_e = JsonMethed.getJsonString(jsonObject.get("img_e"));
		img_title_s = JsonMethed.getJsonString(jsonObject.get("img_title_s"));
		img_title_e = JsonMethed.getJsonString(jsonObject.get("img_title_e"));
		wind = JsonMethed.getJsonString(jsonObject.get("wind"));
		fl = JsonMethed.getJsonString(jsonObject.get("fl"));
		st = JsonMethed.getJsonString(jsonObject.get("st"));
	}
}
