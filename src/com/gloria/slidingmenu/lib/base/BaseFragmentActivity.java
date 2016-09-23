package com.gloria.slidingmenu.lib.base;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseFragmentActivity extends FragmentActivity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected LinearLayout titlebar;
	protected Button titlebar_left, titlebar_right;
	protected TextView titlebar_name;
	protected ImageButton webnav_left, webnav_right;

	String tmp = "";

	private boolean instanceStateSaved;

	ProgressDialog pdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}

	// public void finish(){
	// super.finish();
	// overridePendingTransition(R.anim.fragment_slide_left_enter,
	// R.anim.fragment_slide_right_exit);
	// }
	//
	// public void defaultFinish(){
	// super.finish();
	// }
}
