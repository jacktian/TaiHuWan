package com.gloria.hbh.myview;

import com.gloria.hbh.application.BaseApplication;

import android.content.Context;
import android.widget.Toast;

/**
* 文 件 名 : ForumToast
* 创 建 人： gejian
* 日     期：2012-8-28
* 修 改 人：gejian
* 日    期：2012-8-28
* 描    述：Toast显示
*/
public class ForumToast {

	public static void show(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void show(String text){
		Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
}
