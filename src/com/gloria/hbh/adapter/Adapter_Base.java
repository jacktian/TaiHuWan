package com.gloria.hbh.adapter;

import java.util.ArrayList;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.forum.PostInfo;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.Methods;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @author gejian
 *
 */
/**
 * 文 件 名 : PostListAdapter 创 建 人： gejian 日 期：2013-2-5 修 改 人：gejian 日 期：2013-2-5
 * 描 述：基类的适配器
 */
public class Adapter_Base extends BaseAdapter {

	Context _context;
	ArrayList<PostInfo> lastviewsList;
	ArrayList<String> lastviewsTidList;
	DisplayImageOptions options;
	DisplayImageOptions options_icon;
	DisplayImageOptions options_big;
	ImageLoader imageLoader;

	public Adapter_Base() {
		// super();
		_context = BaseApplication.getInstance().getApplicationContext();
		// lastviewsList = Methods.getLastViewsList();
		// lastviewsTidList = Methods.getLastViewsTidList(lastviewsList);
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.defalut_img_small)
				.showImageForEmptyUri(R.drawable.defalut_img_small).cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		options_icon = new DisplayImageOptions.Builder().showStubImage(R.drawable.portrait)
				.showImageForEmptyUri(R.drawable.portrait).cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		options_big = new DisplayImageOptions.Builder().showStubImage(R.drawable.defalut_img_big)
				.showImageForEmptyUri(R.drawable.defalut_img_big).cacheInMemory().cacheOnDisc()
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return convertView;
	}

	public int getCount() {
		return 0;
	}

	public Object getItem(int position) {
		return null;
	}

	public ArrayList<PostInfo> getLastviewsList() {
		return lastviewsList;
	}

	public void setLastviewsList(ArrayList<PostInfo> lastviewsList) {
		this.lastviewsList = lastviewsList;
		setLastviewsTidList(Methods.getLastViewsTidList(lastviewsList));
	}

	public ArrayList<String> getLastviewsTidList() {
		return lastviewsTidList;
	}

	public void setLastviewsTidList(ArrayList<String> lastviewsTidList) {
		this.lastviewsTidList = lastviewsTidList;
	}
}
