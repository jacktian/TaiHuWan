package com.gloria.hbh.adapter;

import java.util.ArrayList;

import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.main.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HandlineListsAdapter extends Adapter_Base {
	ArrayList<HandlinesBasicInfo> handlinesBasicInfos;

	public HandlineListsAdapter(ArrayList<HandlinesBasicInfo> handlinesBasicInfos, ImageLoader imageLoader) {
		this.handlinesBasicInfos = handlinesBasicInfos;
		this.imageLoader = imageLoader;
	}

	public int getCount() {
		return handlinesBasicInfos.size();
	}

	public Object getItem(int position) {
		return handlinesBasicInfos.get(position);
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
			convertView = LayoutInflater.from(this._context).inflate(R.layout.listview_handlineslist, null);
			item.item_img = (ImageView) convertView.findViewById(R.id.item_image);
			item.item_title = (TextView) convertView.findViewById(R.id.item_title);
			item.item_content = (TextView) convertView.findViewById(R.id.item_content);
			convertView.setTag(item);
		}
		item.item_title.setText(Html.fromHtml(handlinesBasicInfos.get(position).getTitle()));
		if (lastviewsTidList != null && lastviewsTidList.contains(handlinesBasicInfos.get(position).getTid())) {
			item.item_title.setText(Html.fromHtml(
					"<b><font color=#999999>" + handlinesBasicInfos.get(position).getTitle() + "</font></b>"));
		}
		item.item_content.setText(handlinesBasicInfos.get(position).getDescrip());
		item.item_content.setMinLines(2);
		item.item_content.setMaxLines(2);
		item.item_img.setVisibility(View.VISIBLE);
		if (handlinesBasicInfos.get(position) != null && handlinesBasicInfos.get(position).getImg() != null
				&& !handlinesBasicInfos.get(position).getImg().equals("")) {
			// String img_url =
			// BaseConfig.requestImageUrl(ScreenUtils.getInstance().getWidth()/4,
			// ScreenUtils.getInstance().getWidth()*3/(4*4),
			// handlinesBasicInfos.get(position).getImg(),
			// ImgScaleTypeConstants.IMGTYPE_INYERCEPT);
			String img_url = handlinesBasicInfos.get(position).getImg();
			imageLoader.displayImage(img_url, item.item_img, options);
		}
		return convertView;
	}

	private class ViewItem {
		ImageView item_img;
		TextView item_title;
		TextView item_content;
	}
}