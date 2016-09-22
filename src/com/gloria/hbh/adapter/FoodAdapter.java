package com.gloria.hbh.adapter;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.ForumToast;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author gejian
 *
 */
/**
* 文 件 名 : PostListAdapter
* 创 建 人： gejian
* 日     期：2012-8-9
* 修 改 人：gejian
* 日    期：2012-8-9
* 描    述：具体分类下帖子列表listview的适配器
*/
public class FoodAdapter extends Adapter_Base{
	
	List<HandlinesBasicInfo> list;
	
	public FoodAdapter(List<HandlinesBasicInfo> list,ImageLoader imageLoader){
		this.list = list;
		this.imageLoader = imageLoader;
//		lastviewsList = Methods.getLastViewsList();
//		lastviewsTidList = Methods.getLastViewsTidList(lastviewsList);
	}

	public int getCount(){
		return list.size();
	}
	
	public HandlinesBasicInfo getItem(int position){
		return list.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent){
		final ViewItem item;
		if (convertView != null) {
			item =(ViewItem) convertView.getTag();  
		} else {
			item = new ViewItem();
			convertView = LayoutInflater.from(this._context).inflate(R.layout.listview_food, null);
			item.recommend = (ImageView) convertView.findViewById(R.id.recommend);
			item.dialog = (ImageView) convertView.findViewById(R.id.dialog);
			item.title = (TextView) convertView.findViewById(R.id.title);
			item.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
			item.stars = (TextView) convertView.findViewById(R.id.stars);
			item.distance = (TextView) convertView.findViewById(R.id.distance);
			convertView.setTag(item); 
		}
		
		HandlinesBasicInfo handlinesBasicInfo = getItem(position);
		
		item.title.setText(handlinesBasicInfo.getTitle());
		item.subtitle.setText(handlinesBasicInfo.getDescrip());
		
		item.stars.setVisibility(View.GONE);
		item.distance.setVisibility(View.GONE);
		if(handlinesBasicInfo.getAuthorId() != null && handlinesBasicInfo.getAuthorId().equals("1")){
			item.recommend.setVisibility(View.VISIBLE);
			item.stars.setVisibility(View.VISIBLE);
			item.stars.setBackgroundResource(R.drawable.five_stars);
		}else{
			item.recommend.setVisibility(View.GONE);
			item.distance.setVisibility(View.VISIBLE);
			item.distance.setText(handlinesBasicInfo.getAuthor());
		}
		item.dialog.setVisibility(View.VISIBLE);
		item.dialog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(getItem(position).getUrl() == null 
						|| getItem(position).getUrl().equals("")){
					ForumToast.show("号码为空！");
					return;
				}
				String num = "tel:"+getItem(position).getUrl();
				goToDialog(num);
			}
		});
		return convertView;
	}
	
	private class ViewItem {
		TextView title;
		TextView subtitle;
		TextView stars;
		TextView distance;
		ImageView recommend;
		ImageView dialog;
	}
	
	/*
	 * 拨打电话
	 */
	protected void goToDialog(String text) {
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(text));  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
	}
}
