package com.gloria.hbh.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 活动节目表
 */
public class Activity_HuoDongProgram extends Activity_Base {

	ImageView image;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huodongprogram);

		setView();
		setListener();
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText("活动节目表");
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_back = (Button) findViewById(R.id.titlebar_back);
		titlebar_right = (Button) findViewById(R.id.titlebar_right);
		titlebar_back.setVisibility(View.VISIBLE);
		titlebar_right.setVisibility(View.INVISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);

		// image = (ImageView)findViewById(R.id.image);
	}

	/*
	 * 监听
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
