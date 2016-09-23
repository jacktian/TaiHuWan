package com.gloria.hbh.adapter;

import java.util.ArrayList;

import com.gloria.hbh.main.R;
import com.gloria.hbh.util.ImageUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AttachmentAdapter extends Adapter_Base {
	private ArrayList<String> imgList;

	public AttachmentAdapter(ArrayList<String> imgList) {
		this.imgList = imgList;
	}

	public int getCount() {
		return imgList == null ? 0 : imgList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewItem item;
		if (convertView != null) {
			item = (ViewItem) convertView.getTag();
		} else {
			item = new ViewItem();
			convertView = LayoutInflater.from(_context).inflate(R.layout.gallery_row_attachment, null);
			item.img = (ImageView) convertView.findViewById(R.id.item_img);
			item.del = (ImageView) convertView.findViewById(R.id.item_del);
			convertView.setTag(item);
		}

		if (imgList.get(position) != null && !imgList.get(position).equals("")) {
			if (imgList.size() <= 3) {
				item.img.setImageBitmap(ImageUtils.getBitmapByPath(imgList.get(position)));
			} else {
				// ForumToast.show("最多只能发三张图片");
				// imgList.remove(3);
			}
		}

		item.del.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				imgList.remove(position);
				notifyDataSetChanged();
			}
		});

		item.img.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				imgList.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	public class ViewItem {
		ImageView img;
		ImageView del;
	}

	public ArrayList<String> getImgLists() {
		return imgList;
	}
}
