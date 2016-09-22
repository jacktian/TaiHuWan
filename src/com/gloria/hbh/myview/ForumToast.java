package com.gloria.hbh.myview;

import com.gloria.hbh.application.BaseApplication;

import android.content.Context;
import android.widget.Toast;

/**
* �� �� �� : ForumToast
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-28
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-28
* ��    ����Toast��ʾ
*/
public class ForumToast {

	public static void show(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void show(String text){
		Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
}
