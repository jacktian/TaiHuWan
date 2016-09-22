package com.gloria.slidingmenu.lib.fragment;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.gloria.hbh.myview.LoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseFragment extends Fragment{

	protected LinearLayout titlebar;
	protected Button titlebar_left,titlebar_right,titlebar_menu;
	protected TextView titlebar_name;
	
	LoadingDialog pdialog;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	public void onResume() {
		super.onResume();
		/**
		 * Fragmentҳ����ʼ (ע�⣺ ����м̳еĸ�Fragment���Ѿ�����˸õ��ã���ô��Fragment����ز�����ӣ�
		 */
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		/**
		 *Fragment ҳ�������ע�⣺����м̳еĸ�Fragment���Ѿ�����˸õ��ã���ô��Fragment����ز�����ӣ�
		 */
		StatService.onPause(this);
	}
	
}
