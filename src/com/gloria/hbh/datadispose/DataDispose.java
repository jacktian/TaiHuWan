package com.gloria.hbh.datadispose;

import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.data.forum.WeatherInfo;
import com.gloria.hbh.util.HttpUtils;
import com.google.gson.JsonObject;

/**
 * 文 件 名 : DataDispose 创 建 人： gejian 日 期：2012-8-7 修 改 人：gejian 日 期：2012-8-7 描
 * 述：数据处理类
 */
public class DataDispose {

	/*
	 * 天气信息
	 */
	public static WeatherInfo getWeatherInfo(String code, boolean isRefresh) {
		JsonObject jsonObject = null;
		WeatherInfo weatherInfo = null;
		jsonObject = HttpUtils.getJsonObject(BaseConfig.requestWeatherInfoUrl(code), isRefresh);
		// weatherInfo = WeatherInfo.getWeatherInfoFromJson(jsonObject);
		weatherInfo = new WeatherInfo(jsonObject);
		return weatherInfo;
	}

}