package com.gloria.hbh.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gloria.hbh.adapter.ImgGalleryAdapter;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.myview.MyGallery;
import com.gloria.hbh.myview.PageIndicatorView;
import com.gloria.hbh.util.ImageUtils;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 附件图片列表
 */
public class Activity_AttachmentList extends Activity_Base {

	MyGallery gallery;
	PageIndicatorView view_page;
	ArrayList<String> imgLists;
	ImgGalleryAdapter imgGalleryAdapter;

	String fileSrc = "";
	String fileDir = "";
	String imgSrc = "";
	String fileName = "";
	int isSelectPos = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imggallery);
		BaseApplication.getInstance().addActivity(this);

		initData();
		setViews();
		setListener();

		imgGalleryAdapter = new ImgGalleryAdapter(imgLists, this);
		gallery.setAdapter(imgGalleryAdapter);
	}

	private void initData() {
		imgLists = getIntent().getStringArrayListExtra("imgLists");
	}

	public ArrayList<String> getImgLists() {
		if (imgLists == null) {
			imgLists = new ArrayList<String>(1);
		}
		return imgLists;
	}

	private void setViews() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText(getString(R.string.main_tab_5));
		titlebar_left = (Button) findViewById(R.id.titlebar_left);
		titlebar_right = (Button) findViewById(R.id.titlebar_right);
		titlebar_left.setVisibility(View.VISIBLE);
		titlebar_right.setVisibility(View.VISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		// 设置title样式
		titlebar_name.setText("浏览图片");
		titlebar_left.setText(getString(R.string.back));
		titlebar_right.setText(getString(R.string.download));

		gallery = (MyGallery) findViewById(R.id.gallery_img);

		view_page = (PageIndicatorView) findViewById(R.id.view_page);

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				isSelectPos = position;
				view_page.setCurrentPage(position);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void setListener() {
		titlebar_left.setOnClickListener(onClickListener);
		titlebar_right.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_left:
				finish();
				break;
			case R.id.titlebar_right:
				imgSrc = "";
				imgSrc = imgLists.get(isSelectPos);
				goToDownload(imgSrc);
				break;
			default:
				break;
			}
		}
	};

	/*
	 * 下载图片
	 */
	protected void goToDownload(String imgSrc) {
		fileName = "";
		fileDir = "";
		// imgSrc = "";
		// imgSrc = imgLists.get(isSelectPos);
		fileDir = BaseConstants.CACHE_SAVE_IMG_PATH;
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/") + 1).replaceAll("_", "");
		fileDir = fileDir + fileName;
		ImageSize targetSize = getImageSizeScaleTo(gallery);
		String memoryCacheKey = MemoryCacheUtil.generateKey(imgSrc, targetSize);
		if (imageLoader.getMemoryCache().get(memoryCacheKey) != null) {
			File dir = new File(fileDir);
			if (!dir.getParentFile().exists()) {
				dir.getParentFile().mkdirs();
			}
			if (dir.exists()) {
				ForumToast.show("图片已在相册中！");
				return;
			}
			try {
				if (ImageUtils.saveBitmap(imageLoader.getMemoryCache().get(memoryCacheKey), fileDir, false)) {
					ForumToast.show("成功下载到相册！");
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + fileDir)));
				} else {
					ForumToast.show("保存图片失败！");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private ImageSize getImageSizeScaleTo(Gallery gallery) {
		DisplayMetrics displayMetrics = gallery.getContext().getResources().getDisplayMetrics();

		LayoutParams params = gallery.getLayoutParams();
		int width = params.width; // Get layout width parameter
		if (width <= 0)
			width = displayMetrics.widthPixels;

		int height = params.height; // Get layout height parameter
		if (height <= 0)
			height = displayMetrics.heightPixels;

		return new ImageSize(width, height);
	}
}
