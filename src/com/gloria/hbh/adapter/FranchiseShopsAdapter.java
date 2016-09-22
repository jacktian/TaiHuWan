package com.gloria.hbh.adapter;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloria.hbh.data.forum.StoreInfo;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.PageIndicatorView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/*
 * 特许零售店适配器
 */
public class FranchiseShopsAdapter extends Adapter_Base
{
    ArrayList<StoreInfo> list;

    public FranchiseShopsAdapter(ArrayList<StoreInfo> list)
    {
        this.list=list;
    }

    public int getCount()
    {
        return list.size();
    }

    public StoreInfo getItem(int position)
    {
        return list.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position,View convertView,ViewGroup parent)
    {
        final ViewItem item;
        if(convertView!=null)
        {
            item=(ViewItem) convertView.getTag();
        }
        else
        {
            item=new ViewItem();
            convertView=LayoutInflater.from(this._context).inflate(R.layout.listview_franchiseshops,null);
            item.item_img=(ImageView) convertView.findViewById(R.id.icon);
            item.item_title=(TextView) convertView.findViewById(R.id.title);
            item.item_subtitle=(TextView) convertView.findViewById(R.id.subtitle);
            convertView.setTag(item);
        }
        convertView.requestFocus();
        convertView.requestFocusFromTouch();
        item.item_title.setText(getItem(position).getName());
        item.item_subtitle.setText(getItem(position).getAddress());
        //		item.item_img.setBackgroundResource(R.drawable.icon_dialog);
        item.item_img.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String num="tel:"+getItem(position).getTelephone().replace("-","");
                goToDialog(num);
            }
        });
        return convertView;
    }

    private class ViewItem
    {
        ImageView item_img;
        TextView item_title;
        TextView item_subtitle;
        TextView item_divider;
    }

    /*
     * 拨打电话
     */
    protected void goToDialog(String text)
    {
        Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(text));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }
}