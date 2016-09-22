package com.gloria.hbh.datadispose;

import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.data.forum.WeatherInfo;
import com.gloria.hbh.util.HttpUtils;
import com.google.gson.JsonObject;


/**
* �� �� �� : DataDispose
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-7
* ��    �������ݴ�����
*/
public class DataDispose {

	/*
	 *������Ϣ
	 */
	public static WeatherInfo getWeatherInfo(String code,boolean isRefresh) {
		JsonObject jsonObject = null;
		WeatherInfo weatherInfo = null;
		jsonObject = HttpUtils.getJsonObject(BaseConfig.requestWeatherInfoUrl(code),isRefresh);
//		weatherInfo = WeatherInfo.getWeatherInfoFromJson(jsonObject);
		weatherInfo = new WeatherInfo(jsonObject);
		return weatherInfo;
	}

}