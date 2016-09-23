package com.gloria.slidingmenu.lib.fragment;

import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.Activity_RoutePlan;
import com.gloria.hbh.main.R;

import android.annotation.SuppressLint;
import android.content.Intent;
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

/*
 * 交通页面
 */
@SuppressLint("ValidFragment")
public class TrafficFragment extends BaseFragment {

	String text = "咨讯";

	Activity_Main mMain = null;
	private FrameLayout mFrameLayout = null;

	public TrafficFragment(String text) {
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
		View view = inflater.inflate(R.layout.activity_traffic, null);
		setView(view);
		setListener();

		// if(isFirstShow){
		// isFirstShow = false;
		// }else{
		// onResume();
		// }
		return view;
	}

	private void setView(View view) {
		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
		titlebar_left = (Button) view.findViewById(R.id.titlebar_left);
		titlebar_menu = (Button) view.findViewById(R.id.titlebar_lu);
		titlebar_menu.setVisibility(View.VISIBLE);
		titlebar_left.setVisibility(View.INVISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_name.setText("交通信息");
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_menu.setBackgroundResource(R.drawable.titlebtn_bg);
		titlebar_menu.setPadding(10, 5, 10, 5);
		titlebar_menu.setText("带我去太湖湾");
		titlebar_menu.setTextColor(Color.BLACK);
		titlebar.setBackgroundResource(R.drawable.top_img);
	}

	private void setListener() {
		titlebar_menu.setOnClickListener(onClickListener);
		titlebar_left.setOnClickListener(onClickListener);
		titlebar_name.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_lu:
				goToNavigator();
				break;
			case R.id.titlebar_right:
				break;
			}
		}
	};

	/*
	 * 进入导航页面
	 */
	protected void goToNavigator() {
		Intent intent = new Intent();
		intent.setClass(mMain, Activity_RoutePlan.class);
		startActivity(intent);
	}
}
