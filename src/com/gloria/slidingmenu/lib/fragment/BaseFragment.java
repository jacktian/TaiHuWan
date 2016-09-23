package com.gloria.slidingmenu.lib.fragment;

import com.baidu.mobstat.StatService;
import com.gloria.hbh.myview.LoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseFragment extends Fragment {

	protected LinearLayout titlebar;
	protected Button titlebar_left, titlebar_right, titlebar_menu;
	protected TextView titlebar_name;

	LoadingDialog pdialog;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public void onResume() {
		super.onResume();
		/**
		 * Fragment页面起始 (注意： 如果有继承的父Fragment中已经添加了该调用，那么子Fragment中务必不能添加）
		 */
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		/**
		 * Fragment 页面结束（注意：如果有继承的父Fragment中已经添加了该调用，那么子Fragment中务必不能添加）
		 */
		StatService.onPause(this);
	}

}
