package com.gloria.hbh.adapter;

import java.util.ArrayList;

import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.PageIndicatorView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HeadImgsAdapter extends Adapter_Base {

	ArrayList<String> imageList;
	PageIndicatorView view_page;

	public HeadImgsAdapter(ArrayList<String> imageList, ImageLoader imageLoader) {
		this.imageList = imageList;
		this.imageLoader = imageLoader;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.defalut_img_big)
				.showImageForEmptyUri(R.drawable.defalut_img_big).cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public int getCount() {
		return imageList.size();
	}

	public Object getItem(int position) {
		return imageList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewItem item;
		if (convertView != null) {
			item = (ViewItem) convertView.getTag();
		} else {
			item = new ViewItem();
			convertView = LayoutInflater.from(this._context).inflate(R.layout.gallery_row_exhibition, null);
			item.item_img = (ImageView) convertView.findViewById(R.id.home_img_big);
			item.item_title = (TextView) convertView.findViewById(R.id.home_img_big_text);
			item.item_title.setVisibility(View.GONE);
			convertView.setTag(item);
		}
		convertView.requestFocus();
		convertView.requestFocusFromTouch();

		String img_url = "assets://" + imageList.get(position);
		imageLoader.displayImage(img_url, item.item_img, options);
		return convertView;
	}

	private class ViewItem {
		ImageView item_img;
		TextView item_title;
	}
}