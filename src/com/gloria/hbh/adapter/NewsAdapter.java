package com.gloria.hbh.adapter;

import java.util.List;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseImageView;
import com.parse.ParseObject;


public class NewsAdapter extends Adapter_Base{
	
	List<ParseObject> listParseObject;
	
	public NewsAdapter(List<ParseObject> listParseObject,ImageLoader imageLoader){
		this.listParseObject = listParseObject;
		this.imageLoader = imageLoader; 
	}

	public int getCount(){
		return listParseObject.size();
	}
	
	public ParseObject getItem(int position){
		return listParseObject.get(position);
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
			convertView = LayoutInflater.from(this._context).inflate(R.layout.listview_news, null);
			item.imageView = (ParseImageView) convertView.findViewById(R.id.icon);
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
		
		ParseObject parseObject = getItem(position);
		
		item.imageView.setPlaceholder(_context.getResources().getDrawable(R.drawable.ic_launcher));
		item.imageView.setVisibility(View.GONE);
		if(parseObject.getParseFile("imageFile") != null){
			item.imageView.setVisibility(View.VISIBLE);
			item.imageView.setImageURI(Uri.parse(parseObject.getParseFile("imageFile").getUrl()));
			imageLoader.displayImage(parseObject.getParseFile("imageFile").getUrl(), item.imageView, options);
		}
		  
		item.title.setText(parseObject.getString("title"));
		item.subtitle.setText(parseObject.getString("description"));
		return convertView;
	}
	
	public class ViewItem {
		ParseImageView imageView;
		TextView title;
		TextView subtitle;
	}
}
