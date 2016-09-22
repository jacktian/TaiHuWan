package com.gloria.hbh.adapter;

import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalBitmap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloria.hbh.main.R; 



public class ImgGalleryAdapter extends Adapter_Base{
	

  	 List<String> imgList;
  	 private FinalBitmap fb; 
	public ImgGalleryAdapter( List<String> imgList,Activity instanceActivity){
		this.imgList=imgList; 
		  fb=FinalBitmap.create(instanceActivity);
		          fb.configLoadingImage(R.drawable.defalut_img_small);
	        fb.configLoadfailImage(R.drawable.defalut_img_small);
	}
	
	public int getCount(){
		return imgList==null?0:imgList.size();
	}
	
	public String  getItem(int position)
	{
		return imgList.get(position);
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		final ViewClass view ;
		if (convertView == null){
			convertView = LayoutInflater.from(_context).inflate(R.layout.gallery_row_img, null);
			view = new ViewClass();  
	        view.item_img = (ImageView)convertView.findViewById(R.id.item_img);
	        view.item_text = (TextView)convertView.findViewById(R.id.item_text);
	        convertView.setTag(view);  
		} else{  
	          view =(ViewClass) convertView.getTag();  
		}
		
		//加载图片
		if(imgList.get(position)!= null && !imgList.get(position).equals("")){
		    fb.display(view.item_img,imgList.get(position));
		}
		view.item_text.setText((position+1)+"/"+getCount());
		
		return convertView;
	 }
	

	
	//定义组件的类：  
	private class ViewClass{  
		TextView item_text;  
//		EllipsizingTextView  item_text;
		ImageView item_img;
	} 
}
