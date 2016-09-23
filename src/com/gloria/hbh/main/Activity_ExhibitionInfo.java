package com.gloria.hbh.main;

import java.util.ArrayList;

import com.gloria.hbh.adapter.HeadImgsAdapter;
import com.gloria.hbh.data.app.SubTabInfo.SubTabInfoTypeConstants;
import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.myview.MyGallery;
import com.gloria.hbh.myview.PageIndicatorView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 展会内容页面
 */
public class Activity_ExhibitionInfo extends Activity_Base {

	ImageView image;
	TextView text_content;
	TextView text_title;

	String name = "";
	String detail = "";
	ArrayList<String> images;
	int type = SubTabInfoTypeConstants.SUBCATE_ACTIVITY;

	MyGallery gallery;
	PageIndicatorView view_page;
	ArrayList<HandlinesBasicInfo> handlinesBasicInfos;
	HeadImgsAdapter headImgsAdapter;

	String content = "";

	DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.defalut_img_big)
			.showImageForEmptyUri(R.drawable.defalut_img_big).cacheInMemory().cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565).build();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition);

		initData();
		setView();
		setListener();
	}

	private void initData() {
		type = getIntent().getIntExtra("type", SubTabInfoTypeConstants.SUBCATE_ACTIVITY);

		name = getIntent().getStringExtra("name");
		detail = getIntent().getStringExtra("detail");
		images = getIntent().getStringArrayListExtra("images");
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText(name);
		titlebar_back = (Button) findViewById(R.id.titlebar_back);
		titlebar_right = (Button) findViewById(R.id.titlebar_right);
		titlebar_back.setVisibility(View.VISIBLE);
		titlebar_right.setVisibility(View.INVISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_right.setBackgroundResource(R.drawable.titlebtn_bg_share);

		text_title = (TextView) findViewById(R.id.text_title);
		text_title.setText("　" + name);
		view_page = (PageIndicatorView) findViewById(R.id.view_page);
		gallery = (MyGallery) findViewById(R.id.head_gallery);
		gallery.setFocusable(true);
		gallery.setFocusableInTouchMode(true);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				view_page.setCurrentPage(position);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		if (images != null && images.size() != 0) {
			headImgsAdapter = new HeadImgsAdapter(images, imageLoader);
			gallery.setAdapter(headImgsAdapter);
			view_page.setTotalPage(images.size());
		}

		text_content = (TextView) findViewById(R.id.content);
		text_content.setText(" " + detail);
	}

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
			case R.id.titlebar_right:
				goToShare();
				break;
			default:
				break;
			}
		}
	};

	// 分享
	protected void goToShare() {
		ForumToast.show("分享待实现！");
	}
}
