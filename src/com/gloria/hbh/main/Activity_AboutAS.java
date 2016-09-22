package com.gloria.hbh.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_AboutAS extends Activity_Base{
	
	TextView info;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutas);
		
		setView();
		setListener();
	}
	
	private void setView(){
		titlebar = (LinearLayout)findViewById(R.id.titlebar);
		titlebar_name = (TextView)findViewById(R.id.titlebar_name);
		titlebar_name.setText(getString(R.string.aboutas));
		titlebar_back = (Button)findViewById(R.id.titlebar_back);
	 	titlebar_right = (Button)findViewById(R.id.titlebar_right);
	 	titlebar_back.setVisibility(View.VISIBLE);
	 	titlebar_right.setVisibility(View.INVISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	
	 	info = (TextView)findViewById(R.id.item_info);
	 	info.setText("���ռ�������Ƽ����޹�˾������2008�꣬����ʡ��չ�͸ĸ�ίԱ�����׼��" +
	 			"�ڳ��������̫����ȼ����ڹ滮���轭�������Ļ���ҵ���˻��أ�" +
	 			"��Ŀһ�ڽ��ص㽨�����еġ�һ԰��������������Ϸ�ȶ�����Ϸ�Ļ����⹫԰��" +
	 			"��һ����CCJOY�й��������ϻ��������Ż����� �����������������һ���������ʽ��" +
	 			"��˾��Ϊ�й����������������������ʼ��������������������Դ����ʵ���������������飬" +
	 			"ͬʱŬ���ٽ������Ļ����ͨ��������Ϊ�û��ṩ��Ԫ�������ַ���Ϊ���ڴ�������õ��������»������顣" +
	 			"��˾���¹���4���з��Ŷӹ�120���ˣ�ӵ�гɹ��з����飻�ԡ���Ϸ��Ϊ��������㣬" +
	 			"�����з����β�Ʒ���л����������ܴ�2����ҳ�β�Ʒ����Ϸ���롷����Ϸ�ɳ������ϹŴ��桷�ȣ�" +
	 			"�ֻ���Ϸ�����μǡ�����2013��ǿ���Ƴ�����Ʒ�ɹ������ۡ��ġ�̨�������ǵȵأ��־���������Ӫ��");
	}
	
	/*
	 * ����
	 */
	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_right.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			default:
				break;
			}
		}
	};
}
