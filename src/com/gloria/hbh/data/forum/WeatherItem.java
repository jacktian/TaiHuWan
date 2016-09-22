package com.gloria.hbh.data.forum;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;

/**
* �?�?�?: WeatherItem
* �?�?人： gloria
* �?    期：2013-4-7
* �?�?人：gloria
* �?   期：2013-4-7
* �?   述： 天气信息子项
*/
public class WeatherItem {
	
	private String temp;						//当前日期�?8日那这第�?��的温度为19日凌晨到19日中午是的温度，下面以此类推
	private String tempF;						//华氏温度 同上
	private String weather;					//同温度一样也�?9日凌晨也可以说成�?8�?3:59:59�?/下面类推
	private String img_s;						//对应的显示图片编�?
	private String img_e;						//对应的显示图片编�?

	private String img_title_s;			//18日夜�?
	private String img_title_e;			//19日白�?

	private String wind;							//�?��的风�?
	private String fl;								//风力
	private String st	;
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
	 * 构�?函数
	 */
	public WeatherItem(){
		super();
	}
	
	public WeatherItem(JsonObject jsonObject){
		super();
		if(jsonObject == null){
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
