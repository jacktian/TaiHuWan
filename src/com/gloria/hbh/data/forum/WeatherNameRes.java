package com.gloria.hbh.data.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.main.R;

/**
 * 鏂?浠?鍚?: WeatherNameRes 鍒?寤?浜猴細 gloria 鏃? 鏈燂細2013-4-9 淇?鏀?浜猴細gloria 鏃?
 * 鏈燂細2013-4-9 鎻? 杩帮細 澶╂皵鎻忚堪瀵瑰簲鐨勫ぉ姘斿浘鐗囪祫婧?
 */
public class WeatherNameRes {

	public static String DAYICON = "dayicon";
	public static String NIGHTICON = "nighticon";
	public static String NAME = "name";
	public static String TEXT = "text";

	private static WeatherNameRes instance = null;
	// private Context mContext;
	// private static Pattern mPattern;
	// private static String[] mResArrayName;
	private static String[] mResArrayText;
	private static Map<String, Integer> mResToDayIcons;
	private static Map<String, Integer> mResToNightIcons;

	private List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();

	/*
	 * 鍗曚緥妯″紡涓幏鍙栧敮涓?殑ExitApplication瀹炰緥
	 */
	public static WeatherNameRes getInstance() {
		if (null == instance) {
			instance = new WeatherNameRes();
		}
		return instance;
	}

	public WeatherNameRes() {
		// mResArrayName = BaseApplication.getInstance().getApplicationContext()
		// .getResources().getStringArray(Smily.DEFAULT_SMILY_NAMES);
		mResArrayText = BaseApplication.getInstance().getApplicationContext().getResources()
				.getStringArray(Weather.DEFAULT_WEATHER_TEXT);

		mResToDayIcons = buileResToDayDrawableMap();
		// mPattern = buildPattern();
		data = buileMap();
	}

	private HashMap<String, Integer> buileResToDayDrawableMap() {
		if (mResArrayText.length != Weather.DEFAULT_WEATHER_DAY_ICONS.length) {
			throw new IllegalStateException("length is Illegal");
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < mResArrayText.length; i++) {
			map.put(mResArrayText[i], Weather.DEFAULT_WEATHER_DAY_ICONS[i]);
		}
		return map;
	}

	// private Pattern buildPattern(){
	// StringBuilder sb = new StringBuilder();
	// sb.append('(');
	// for(int i=0;i<mResArrayText.length;i++){
	// sb.append(Pattern.quote(mResArrayText[i]));
	// sb.append('|');
	// }
	// sb.replace(sb.length() -1, sb.length(), ")");
	// return Pattern.compile(sb.toString());
	// }

	private List<Map<String, ?>> buileMap() {
		List<Map<String, ?>> listMap = new ArrayList<Map<String, ?>>();
		for (int i = 0; i < mResArrayText.length; i++) {
			HashMap<String, Object> entry = new HashMap<String, Object>();
			entry.put(DAYICON, Weather.DEFAULT_WEATHER_DAY_ICONS[i]);
			// entry.put(NAME, mResArrayName[i]);i
			entry.put(TEXT, mResArrayText[i]);
			listMap.add(entry);
		}
		return listMap;
	}

	static class Weather {
		public static final int DEFAULT_WEATHER_TEXT = R.array.default_weather_texts;
		private static final int[] DEFAULT_WEATHER_DAY_ICONS = { R.drawable.weather_sunny, R.drawable.weather_cloudy,
				R.drawable.weather_overcast, R.drawable.weather_rainy, R.drawable.weather_snow, };
	}

	public List<Map<String, ?>> getData() {
		return data;
	}

	// public CharSequence compileStringToDisply(CharSequence text){
	// SpannableStringBuilder sb = new SpannableStringBuilder(text);
	//
	// Matcher m = mPattern.matcher(text);
	//
	// while(m.find()){
	// int resId = mResToIcons.get(m.group());
	// sb.setSpan(new
	// ImageSpan(BaseApplication.getInstance().getApplicationContext(),resId),
	// m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	// }
	// return sb;
	// }

	public int getDrawableResByText(CharSequence text) {
		int resId = 0;
		try {
			resId = mResToDayIcons.get(text);
		} catch (Exception e) {
			resId = R.drawable.weather_cloudy;
		}
		return resId;
	}

}
