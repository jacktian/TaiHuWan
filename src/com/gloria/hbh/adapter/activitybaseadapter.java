package com.gloria.hbh.adapter;

import java.util.List;

import com.gloria.hbh.data.app.activitybean;
import com.gloria.hbh.main.Activity_Detail;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.ScreenUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

public class activitybaseadapter extends BaseAdapter {
	private List<activitybean> arraylist;
	private Activity instanceActivity;
	private FinalBitmap fb;

	public activitybaseadapter(Activity instanceActivity, List<activitybean> arrayList2) {
		super();
		fb = FinalBitmap.create(instanceActivity);
		this.instanceActivity = instanceActivity;
		this.arraylist = arrayList2;
		fb.configLoadingImage(R.drawable.defalut_img_small);
		fb.configLoadfailImage(R.drawable.defalut_img_small);
	}

	public int getCount() {
		return arraylist.size();
	}

	public Object getItem(int position) {
		return position;
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
			convertView = LayoutInflater.from(instanceActivity).inflate(R.layout.newlistviewnews, null);
			item.layout = (LinearLayout) convertView.findViewById(R.id.layour);
			item.imageView = (ImageView) convertView.findViewById(R.id.icon);
			item.title = (TextView) convertView.findViewById(R.id.title);
			item.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
			switch (ScreenUtils.getInstance().getWidth()) {
			case 480:
				item.title.setTextSize(16);
				item.subtitle.setTextSize(14);
				break;
			case 720:
			case 768:
				item.title.setTextSize(18);
				item.subtitle.setTextSize(16);
				break;
			default:
				break;
			}
			convertView.setTag(item);
		}
		try {
			final activitybean informationbean = arraylist.get(position);
			item.layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent();
						intent.setClass(instanceActivity, Activity_Detail.class);
						intent.putExtra("text", "活动资讯内容");
						intent.putExtra("title", informationbean.getTitle());
						intent.putExtra("description", informationbean.getContent());
						String imageUrl = "";
						if (informationbean.getImg().length() > 0) {
							try {
								imageUrl = informationbean.getImg();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						intent.putExtra("imageUrl", imageUrl);
						intent.putExtra("date", "");
						intent.putExtra("type", "");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						instanceActivity.startActivity(intent);
					} catch (Exception e) {
					}
				}
			});
			item.subtitle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent();
						intent.setClass(instanceActivity, Activity_Detail.class);
						intent.putExtra("text", "活动资讯内容");
						intent.putExtra("title", informationbean.getTitle());
						intent.putExtra("description", informationbean.getContent());
						String imageUrl = "";
						if (informationbean.getImg().length() > 0) {
							try {
								imageUrl = informationbean.getImg();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						intent.putExtra("imageUrl", imageUrl);
						intent.putExtra("date", "");
						intent.putExtra("type", "");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						instanceActivity.startActivity(intent);
					} catch (Exception e) {
					}
				}
			});
			fb.display(item.imageView, informationbean.getImg());
			item.title.setText(informationbean.getTitle());
			CharSequence charSequence = Html.fromHtml(informationbean.getContent());
			item.subtitle.setText(charSequence);
			item.subtitle.setMovementMethod(LinkMovementMethod.getInstance());
			if (informationbean.getImg().length() > 10)
				item.imageView.setVisibility(View.VISIBLE);
			else
				item.imageView.setVisibility(View.GONE);
		} catch (Exception e) {
		}
		return convertView;
	}

	private class ViewItem {
		LinearLayout layout;
		ImageView imageView;
		TextView title;
		TextView subtitle;
	}
}
