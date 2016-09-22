package com.gloria.hbh.adapter;

import java.util.List;
import net.tsz.afinal.FinalBitmap;
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
import com.gloria.hbh.data.app.informationbean;
import com.gloria.hbh.main.Activity_Detail;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.ScreenUtils;
import com.gloria.slidingmenu.lib.fragment.InformationFragment;

public class NewNewsAdapter extends BaseAdapter
{
    private List<informationbean> arraylist;
    private Activity instanceActivity;
    private FinalBitmap fb;
    private View mTopView; 
    public NewNewsAdapter(Activity instanceActivity,List<informationbean> arrayList2)
    {
        super();
        fb=FinalBitmap.create(instanceActivity);
        this.instanceActivity=instanceActivity;
        this.arraylist=arrayList2;
        fb.configLoadingImage(R.drawable.defalut_img_small);
        fb.configLoadfailImage(R.drawable.defalut_img_small);
    }
    public int getCount()
    {
        return arraylist.size() + 1;
    }
    public Object getItem(int position)
    {
        return position;
    }
    public long getItemId(int position)
    {
        if(position == 0)
            return 0;
        else
            return position - 1;
    }
    public int getItemViewType(int position)
    {
        return position > 0?0:1;
    }
    public int getViewTypeCount()
    {
        return 2;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        final ViewItem item;
        if(position == 0)
            return getTopView(convertView);
        else
        {
            if(convertView != null)
            {
                item=(ViewItem)convertView.getTag();
            }
            else
            {
                item=new ViewItem();
                convertView=LayoutInflater.from(instanceActivity).inflate(R.layout.newlistviewnews,null);
                item.layout=(LinearLayout)convertView.findViewById(R.id.layour);
                item.imageView=(ImageView)convertView.findViewById(R.id.icon);
                item.title=(TextView)convertView.findViewById(R.id.title);
                item.subtitle=(TextView)convertView.findViewById(R.id.subtitle);
                switch(ScreenUtils.getInstance().getWidth())
                {
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
            try
            {
                 
                
                final informationbean informationbean=arraylist.get(position - 1);
             
                item.layout.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        try
                        { 
                            Intent intent=new Intent();
                            intent.setClass(instanceActivity,Activity_Detail.class);
                            intent.putExtra("text","热点资讯内容");
                            intent.putExtra("title",informationbean.getTitle());
                            intent.putExtra("description",informationbean.getContent());
                            String imageUrl="";
                            if(informationbean.getImg().length() > 0)
                            {
                                try
                                {
                                    imageUrl=informationbean.getImg();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            intent.putExtra("imageUrl",imageUrl);
                            intent.putExtra("date","");
                            intent.putExtra("type","");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            instanceActivity.startActivity(intent);
                        }
                        catch(Exception e)
                        {
                        }  
                    }
                });
                item.subtitle.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        try
                        { 
                            Intent intent=new Intent();
                            intent.setClass(instanceActivity,Activity_Detail.class);
                            intent.putExtra("text","热点资讯内容");
                            intent.putExtra("title",informationbean.getTitle());
                            intent.putExtra("description",informationbean.getContent());
                            String imageUrl="";
                            if(informationbean.getImg().length() > 0)
                            {
                                try
                                {
                                    imageUrl=informationbean.getImg();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            intent.putExtra("imageUrl",imageUrl);
                            intent.putExtra("date","");
                            intent.putExtra("type","");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            instanceActivity.startActivity(intent);
                        }
                        catch(Exception e)
                        {
                        }  
                    }
                });
                fb.display(item.imageView,informationbean.getImg());
                item.title.setText(informationbean.getTitle());
                
                CharSequence charSequence=Html.fromHtml(informationbean.getContent());
                item.subtitle.setText(charSequence);
                item.subtitle.setMovementMethod(LinkMovementMethod.getInstance());
                if(informationbean.getImg().length()>10)
                    item.imageView.setVisibility(View.VISIBLE);
                else  
                    item.imageView.setVisibility(View.GONE); 
                 
            }
            catch(Exception e)
            {
            }
            return convertView;
        }
    }
    private View getTopView(View convertView)
    {
        try
        {
            mTopView=LayoutInflater.from(instanceActivity).inflate(R.layout.head_weather,null);
            
            TextView    head_image=(TextView)mTopView.findViewById(R.id.head_image); 
            
            fb.display(head_image,"http://112.21.190.22/taihuwan/img/main_house.jpg");
            
            String weather=InformationFragment.wea;
            ImageView    head_img=(ImageView)mTopView.findViewById(R.id.head_img); 
            if(weather.contains("转"))
            {
                weather=weather.substring(weather.indexOf("转") + 1);
            }
            if(weather.contains("晴"))
            {
                weather="晴";
                head_img.setBackgroundResource(R.drawable.weather_sunny);
            }
            else
                if(weather.contains("多云"))
                {
                    weather="多云";
                    head_img.setBackgroundResource(R.drawable.weather_cloudy);
                }
                else
                    if(weather.contains("阴"))
                    {
                        weather="阴";
                        head_img.setBackgroundResource(R.drawable.weather_overcast);
                    }
                    else
                        if(weather.contains("雨"))
                        {
                            weather="雨";
                            head_img.setBackgroundResource(R.drawable.weather_rainy);
                        }
                        else
                            if(weather.contains("雪"))
                            {
                                weather="雪";
                                head_img.setBackgroundResource(R.drawable.weather_snow);
                            }
            
            TextView  head_text1=(TextView)mTopView.findViewById(R.id.head_text1); 
         
            head_text1.setText(InformationFragment.str);  
           
            
        }
        catch(Exception e)
        {
        }
        return mTopView;
    }
    
    private class ViewItem
    {
        LinearLayout layout;
        ImageView imageView;
        TextView title;
        TextView subtitle;
    }
}
