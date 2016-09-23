package com.gloria.hbh.data.forum;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gloria.hbh.datadispose.JsonMethed;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WeatherInfo {

	String city;
	String cityid;
	String temp1;
	String temp2;
	String weather;
	String img1;
	String img2;
	String ptime;

	public WeatherInfo() {
	}

	public WeatherInfo(JsonObject jsonObject) {
		super();
		if (jsonObject == null) {
			return;
		}
		try {
			JsonObject jsonObject_weatherinfo = JsonMethed.getJsonObject(jsonObject.get("weatherinfo"));
			if (jsonObject_weatherinfo != null) {
				city = JsonMethed.getJsonString(jsonObject_weatherinfo.get("city"));
				cityid = JsonMethed.getJsonString(jsonObject_weatherinfo.get("cityid"));
				temp1 = JsonMethed.getJsonString(jsonObject_weatherinfo.get("temp1"));
				temp2 = JsonMethed.getJsonString(jsonObject_weatherinfo.get("temp2"));
				weather = JsonMethed.getJsonString(jsonObject_weatherinfo.get("weather"));
				img1 = JsonMethed.getJsonString(jsonObject_weatherinfo.get("img1"));
				img2 = JsonMethed.getJsonString(jsonObject_weatherinfo.get("img2"));
				ptime = JsonMethed.getJsonString(jsonObject_weatherinfo.get("ptime"));
			}
		} catch (JsonParseException e) {
		} catch (Exception e) {
		}
	}

	public static WeatherInfo getWeatherInfoFromJson(JsonObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		WeatherInfo weatherInfo = new WeatherInfo();
		try {
			JsonObject jsonObject_weatherinfo = JsonMethed.getJsonObject(jsonObject.get("weatherinfo"));
			if (jsonObject_weatherinfo != null) {
				weatherInfo.setCity(JsonMethed.getJsonString(jsonObject_weatherinfo.get("city")));
				weatherInfo.setCityId(JsonMethed.getJsonString(jsonObject_weatherinfo.get("cityid")));
				weatherInfo.setTemp1(JsonMethed.getJsonString(jsonObject_weatherinfo.get("temp1")));
				weatherInfo.setTemp2(JsonMethed.getJsonString(jsonObject_weatherinfo.get("temp2")));
				weatherInfo.setWeather(JsonMethed.getJsonString(jsonObject_weatherinfo.get("weather")));
				weatherInfo.setImg1(JsonMethed.getJsonString(jsonObject_weatherinfo.get("img1")));
				weatherInfo.setImg2(JsonMethed.getJsonString(jsonObject_weatherinfo.get("img2")));
				weatherInfo.setPtime(JsonMethed.getJsonString(jsonObject_weatherinfo.get("ptime")));
			}
		} catch (JsonParseException e) {
		} catch (Exception e) {
		}
		return weatherInfo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return cityid;
	}

	public void setCityId(String cityid) {
		this.cityid = cityid;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getPtime() {
		return ptime;
	}

	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

}
