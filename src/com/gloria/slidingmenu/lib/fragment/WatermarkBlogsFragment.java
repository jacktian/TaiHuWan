package com.gloria.slidingmenu.lib.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.gloria.hbh.adapter.ImgGalleryAdapter;
import com.gloria.hbh.camera.CameraPreview;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.MyGallery;
import com.gloria.hbh.myview.PageIndicatorView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 水印游记页面
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("static-access")
public class WatermarkBlogsFragment extends BaseFragment {

	// private static final String TAB_DATA = "tab_data";
	// private static final String TAB_ADAPTER = "tab_adapter";

	String text = "咨讯";

	List<HashMap<String, String>> list;

	Activity_Main mMain = null;
	private FrameLayout mFrameLayout = null;

	MyGallery gallery;
	PageIndicatorView view_page;
	ArrayList<String> imgLists;
	ImgGalleryAdapter imgGalleryAdapter;

	ProgressDialog pdialog;
	boolean isRefresh = true;

	int isSelectPos = 0;
	Activity insActivity;

	public WatermarkBlogsFragment() {
	}

	public WatermarkBlogsFragment(String text) {
		this.text = text;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		insActivity = activity;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		mMain = (Activity_Main) getActivity();
		mFrameLayout = (FrameLayout) mMain.findViewById(R.id.content_main);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_imggallery, null);

		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
		titlebar_menu = (Button) view.findViewById(R.id.titlebar_menu);
		titlebar_left = (Button) view.findViewById(R.id.titlebar_camera);
		titlebar_menu.setVisibility(View.VISIBLE);
		titlebar_left.setVisibility(View.VISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_name.setText(text);
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_left.setBackgroundResource(R.drawable.titlebtn_bg_camera);

		initData();
		setListener();

		gallery = (MyGallery) view.findViewById(R.id.gallery_img);

		view_page = (PageIndicatorView) view.findViewById(R.id.view_page);

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				isSelectPos = position;
				view_page.setCurrentPage(position);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		imgGalleryAdapter = new ImgGalleryAdapter(imgLists, insActivity);
		gallery.setAdapter(imgGalleryAdapter);

		view_page.setTotalPage(getImgLists().size());
		return view;
	}

	/*
	 * 初始图片数据
	 */
	private void initData() {
		File file = new File(BaseConstants.CACHE_SAVE_IMG_PATH);
		if (file.exists()) {
			imgLists = getImagePathList(file);
		}
	}

	public ArrayList<String> getImgLists() {
		if (imgLists == null) {
			imgLists = new ArrayList<String>(1);
		}
		return imgLists;
	}

	public void setImgLists(ArrayList<String> imgLists) {
		this.imgLists = imgLists;
	}

	/**
	 * 获取图片地址列表
	 * 
	 * @param file
	 * @return
	 */
	private static ArrayList<String> getImagePathList(File file) {
		ArrayList<String> list = new ArrayList<String>();
		File[] files = file.listFiles();
		for (File f : files) {
			list.add("file://" + f.getAbsolutePath());
		}
		Collections.reverse(list);
		return list;
	}

	private void setListener() {
		titlebar_menu.setOnClickListener(onClickListener);
		titlebar_left.setOnClickListener(onClickListener);
		titlebar_name.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_menu:
				if (mMain.getSlidingMenu().isSecondaryMenuShowing()) {
					mMain.getSlidingMenu().toggle();
				} else {
					mMain.getSlidingMenu().showSecondaryMenu();
				}
				break;
			case R.id.titlebar_camera:
				goToPhoto();
				break;
			default:
				break;
			}
		}
	};

	public void onResume() {
		super.onResume();
		File file = new File(BaseConstants.CACHE_SAVE_IMG_PATH);
		getImgLists().clear();
		if (file.exists()) {
			imgLists = getImagePathList(file);
			view_page.setTotalPage(getImgLists().size());
		}
		imgGalleryAdapter = new ImgGalleryAdapter(imgLists, insActivity);
		gallery.setAdapter(imgGalleryAdapter);
	}

	/*
	 * 拍照页面
	 */
	protected void goToPhoto() {
		Intent i = new Intent(mMain, CameraPreview.class);
		startActivityForResult(i, 1);
	}
}