package com.gloria.hbh.adapter;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.data.forum.ImageInfo.ImgScaleTypeConstants;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.PageIndicatorView;
import com.gloria.hbh.util.NetworkUtils;
import com.gloria.hbh.util.ScreenUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HeadlineImgsAdapter extends Adapter_Base{
	
	ArrayList<HandlinesBasicInfo> handlinesBasicInfos ;
	PageIndicatorView view_page;
	
	public HeadlineImgsAdapter(ArrayList<HandlinesBasicInfo> handlinesBasicInfos,ImageLoader imageLoader){
		this.handlinesBasicInfos = handlinesBasicInfos;
		this.imageLoader = imageLoader;
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.defalut_img_big)
		.showImageForEmptyUri(R.drawable.defalut_img_big)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	public int getCount(){
		return handlinesBasicInfos.size();
	}
	
	public Object getItem(int position){
		return handlinesBasicInfos.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		final ViewItem item;
		if (convertView != null) {
			item =(ViewItem) convertView.getTag();  
		} else {
			item = new ViewItem();
			convertView = LayoutInflater.from(this._context).inflate(R.layout.gallery_row_headline, null);
			item.item_img = (ImageView) convertView.findViewById(R.id.home_img_big);
			item.item_title = (TextView) convertView.findViewById(R.id.home_img_big_text);
			convertView.setTag(item); 
		}
		convertView.requestFocus();
		convertView.requestFocusFromTouch();
		
		item.item_title.setText(Html.fromHtml("   "+ handlinesBasicInfos.get(position).getTitle()));
		item.item_img.setVisibility(View.GONE);
		if(handlinesBasicInfos.get(position)!= null && handlinesBasicInfos.get(position).getImg()!= null
				&& !handlinesBasicInfos.get(position).getImg().equals("")){
			
//			String img_url = BaseConfig.requestImageUrl(ScreenUtils.getInstance().getWidth(), ScreenUtils.getInstance().getWidth()*3/4, 
//					handlinesBasicInfos.get(position).getImg(), ImgScaleTypeConstants.IMGTYPE_SCALE);
			item.item_img.setVisibility(View.VISIBLE);
			String img_url = handlinesBasicInfos.get(position).getImg();
			imageLoader.displayImage(img_url, item.item_img, options);
		}
		return convertView;
	}
	
	private class ViewItem {
		ImageView item_img;
		TextView item_title;
	}
}