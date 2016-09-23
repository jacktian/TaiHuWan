package com.gloria.hbh.main;

import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.myview.MyGallery;
import com.gloria.hbh.myview.PageIndicatorView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_NewFunctionHint extends Activity_Base {

	PageIndicatorView view_page;
	MyGallery galleryimg;
	String type;
	int curPos = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imggallery);

		initData();
		setView();
		setListener();
	}

	private void initData() {
		type = getIntent().getExtras().getString("type");
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_left = (Button) findViewById(R.id.titlebar_left);
		titlebar_right = (Button) findViewById(R.id.titlebar_right);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);

		galleryimg = (MyGallery) findViewById(R.id.gallery_img);
		view_page = (PageIndicatorView) findViewById(R.id.view_page);
		// galleryimg.setAnimationDuration(100);

		if (type.equals("FromSystemSet")) {
			titlebar.setVisibility(View.VISIBLE);
			titlebar_right.setVisibility(View.INVISIBLE);
			titlebar_name.setText(getString(R.string.guide));
			titlebar_name.setVisibility(View.VISIBLE);
			titlebar_left.setVisibility(View.VISIBLE);
			titlebar_left.setText(getString(R.string.back));
		} else {
			titlebar.setVisibility(View.GONE);
		}

		// galleryimg.setOnItemSelectedListener(new OnItemSelectedListener() {
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// // TODO Auto-generated method stub
		// if(arg2 == BaseConfig.newFunctionHintPics.length-1 &&
		// !type.equals("FromSystemSet")){
		// Intent select_i = new Intent();
		// select_i.setClass(Activity_NewFunctionHint.this, Activity_Log.class);
		// startActivity(select_i);
		// finish();
		// }
		// }
		//
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		//// item.item_gallery.setSelection(1);
		// }
		// });

		galleryimg.setAdapter(new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				final ViewClass view;
				if (convertView == null) {
					convertView = LayoutInflater.from(Activity_NewFunctionHint.this)
							.inflate(R.layout.gallery_row_img_layout, null);
					view = new ViewClass();
					view.item_img = (ImageView) convertView.findViewById(R.id.item_img);
					view.start_btn = (Button) convertView.findViewById(R.id.start_btn);
					convertView.setTag(view);
				} else {
					view = (ViewClass) convertView.getTag();
				}
				view.item_img.setBackgroundResource(BaseConstants.newFunctionHintPics[position]);
				view.start_btn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent select_i = new Intent();
						select_i.setClass(Activity_NewFunctionHint.this, Activity_Guide.class);
						startActivity(select_i);
						finish();
					}
				});

				if ((position == (getCount() - 1)) && type.equals("FromStart")) {
					view.start_btn.setVisibility(View.VISIBLE);
				} else {
					view.start_btn.setVisibility(View.GONE);
				}
				return convertView;
			}

			public long getItemId(int position) {
				return 0;
			}

			public Object getItem(int position) {
				return null;
			}

			public int getCount() {
				return BaseConstants.newFunctionHintPics.length;
			}
		});

		galleryimg.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				view_page.setCurrentPage(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		view_page.setTotalPage(BaseConstants.newFunctionHintPics.length);
		view_page.setCurrentPage(curPos);

	}

	private void setListener() {
		titlebar_left.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_left:
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 定义组件的类：
	private class ViewClass {
		Button start_btn;
		ImageView item_img;
	}
}
