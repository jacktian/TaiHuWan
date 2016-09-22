package com.gloria.slidingmenu.lib.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;

/*
 * ��������ҳ��
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("static-access")
public class AboutAsFragment extends BaseFragment{
	
	TextView info;
	String text = "��Ѷ";
	
    Activity_Main mMain = null;
    private FrameLayout mFrameLayout = null;
    
    public AboutAsFragment() {
    }

    public AboutAsFragment(String text) {
        this.text = text;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        
        mMain = (Activity_Main) getActivity();
        mFrameLayout = (FrameLayout) mMain.findViewById(R.id.content_main);
    }
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aboutas, null);
        
        setView(view);
	 	setListener();
    	
        return view;
    }

	private void setView(View view) {
		titlebar = (LinearLayout)view.findViewById(R.id.titlebar);
		titlebar_name = (TextView)view.findViewById(R.id.titlebar_name);
		titlebar_name.setText(getString(R.string.aboutas));
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_left = (Button)view.findViewById(R.id.titlebar_left);
		titlebar_menu = (Button)view.findViewById(R.id.titlebar_menu);
	 	titlebar_left.setVisibility(View.INVISIBLE);
	 	titlebar_menu.setVisibility(View.VISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	
	 	info = (TextView)view.findViewById(R.id.item_info);
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

	private void setListener(){
    	titlebar_menu.setOnClickListener(onClickListener);
    	titlebar_left.setOnClickListener(onClickListener);
		titlebar_name.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_menu:
				if(mMain.getSlidingMenu().isSecondaryMenuShowing()){
					mMain.getSlidingMenu().toggle();
				}else{
					mMain.getSlidingMenu().showSecondaryMenu();
				}
				break;
			case R.id.titlebar_back:
				break;
			default:
				break;
			}
		}		
	};
	
}