package com.gloria.hbh.util;

import com.gloria.hbh.application.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
* 文 件 名 : PreferencesUtils
* 创 建 人： gejian
* 日     期：2012-8-7
* 修 改 人：gejian
* 日    期：2012-8-7
* 描    述：保存在内存的存储方法类
*/
public class PreferencesUtils {

    public static void setStringPreferences(String preference, String key, String value){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getApplicationContext().getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static String getStringPreferences(String preference, String key, String defaultValue){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getApplicationContext().getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
    
    public static void setBooleanPreferences(String preference, String key, boolean value){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getApplicationContext().getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    
    public static boolean getBooleanPreferences(String preference, String key, boolean defaultValue){
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getApplicationContext().getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }
}
