package com.gloria.slidingmenu.lib.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.gloria.hbh.adapter.NewNewsAdapter;
import com.gloria.hbh.constant.GsonTools;
import com.gloria.hbh.data.app.informationbean;
import com.gloria.hbh.datadispose.Datamanage;
import com.gloria.hbh.main.Activity_Detail;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.PullListView;
import com.gloria.hbh.myview.PullListView.IXListViewListener;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class InformationFragment extends BaseFragment implements IXListViewListener
{
    String text="咨讯";
    Activity_Main mMain=null;
    private FrameLayout mFrameLayout=null;
    TextView head_text1,head_text2;
    PullListView pulllistview;
    private List<informationbean> arrayList=new ArrayList<informationbean>();
    private Datamanage datamanage;
    private Activity instance;
    private NewNewsAdapter newsadapter;
    public static String str="",wea="";
    private View messageLayout;
    
    public InformationFragment()
    {
    }
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        instance=activity;
    }
    public InformationFragment(String text)
    {
        this.text=text;
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMain=(Activity_Main)getActivity();
        mFrameLayout=(FrameLayout)mMain.findViewById(R.id.content_main);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        if(messageLayout == null)
        {
            messageLayout=inflater.inflate(R.layout.xmlinfro,null);
            setView(messageLayout);
            setListener();
            pulllistview.setpullstate();
            datamanage=new Datamanage(instance);
            pulllistview.setXListViewListener(this);
            try
            {
                arrayList=datamanage.findAll(informationbean.class);
                newsadapter=new NewNewsAdapter(instance,arrayList);
                pulllistview.setAdapter(newsadapter);
            }
            catch(Exception e)
            {
            }
            if(isOpenNetWork())
            {
                pulllistview.mHeaderView.setVisiableHeight(pxToDIP(70));
                pulllistview.mHeaderView.setState(2);
                onRefresh1();
            }
        }
        ViewGroup parent=(ViewGroup)messageLayout.getParent();
        if(parent != null)
            parent.removeView(messageLayout);
        return messageLayout;
    }
    protected int pxToDIP(int px)
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,instance.getResources().getDisplayMetrics());
    }
    protected boolean isOpenNetWork()
    {
        ConnectivityManager connManager=(ConnectivityManager)instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null)
        {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        {
            return false;
        }
    }
    private void setView(View view)
    {
        titlebar=(LinearLayout)view.findViewById(R.id.titlebar);
        titlebar.setVisibility(View.VISIBLE);
        titlebar_name=(TextView)view.findViewById(R.id.titlebar_name);
        titlebar_left=(Button)view.findViewById(R.id.titlebar_left);
        titlebar_menu=(Button)view.findViewById(R.id.titlebar_menu);
        titlebar_menu.setVisibility(View.VISIBLE);
        titlebar_name.setVisibility(View.VISIBLE);
        titlebar.setBackgroundResource(R.drawable.top_img);
        titlebar_name.setText("热点资讯");
        titlebar_name.setTextColor(Color.BLACK);
        pulllistview=(PullListView)view.findViewById(R.id.listView);
    }
    private void setListener()
    {
        titlebar_menu.setOnClickListener(onClickListener);
        titlebar_name.setOnClickListener(onClickListener);
    }
    
    private OnClickListener onClickListener=new OnClickListener(){
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.titlebar_menu:
                    if(mMain.getSlidingMenu().isSecondaryMenuShowing())
                    {
                        mMain.getSlidingMenu().toggle();
                    }
                    else
                    {
                        mMain.getSlidingMenu().showSecondaryMenu();
                    }
                break;
                default:
                break;
            }
        }
    };
    
    protected void stoplistview()
    {
        pulllistview.mPullRefreshing=true;
        pulllistview.stopRefresh();
        pulllistview.stopLoadMore();
    }
    public void onRefresh1()
    {
        if(isOpenNetWork())
        {
            HttpUtils httpUtils=new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET,"http://api.map.baidu.com/telematics/v3/weather?location=常州&output=json&ak=Pd4BIcwYA8Q5p4jgYK2smGrmYqXpd7IG&mcode=49:8A:3C:48:11:C8:89:B8:3E:61:BA:1B:03:C2:54:92:3E:FF:A0:D4;com.gloria.hbh.main",new RequestCallBack<String>(){
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    try
                    {
                        JSONObject jsonObject=new JSONObject(responseInfo.result);
                        if("success".equals(jsonObject.getString("status"))){
                        	JSONArray jsonArray=jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("weather_data");
                        	JSONObject weather=jsonArray.getJSONObject(0);
                            str=weather.getString("temperature");
                            wea=weather.getString("weather");
                            newsadapter=new NewNewsAdapter(instance,arrayList);
                            pulllistview.setAdapter(newsadapter);
                        }else{
                            Toast.makeText(instance,"获取天气失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(JSONException e)
                    {
                    	e.printStackTrace();
                    }
                }
                public void onFailure(HttpException error,String msg)
                {
                }
            });
            HttpUtils http1=new HttpUtils();
            http1.send(HttpRequest.HttpMethod.GET,"http://112.21.190.22/taihuwan/wapNewsList.action",new RequestCallBack<String>(){
                public void onLoading(long total,long current,boolean isUploading)
                {
                }
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    stoplistview();
                    List<informationbean> testlist=new ArrayList<informationbean>();
                    try
                    {
                        testlist=GsonTools.newsinfor(responseInfo.result);
                        if(testlist.size() > 0)
                        {
                            arrayList=testlist;
                            newsadapter=new NewNewsAdapter(instance,arrayList);
                            pulllistview.setAdapter(newsadapter);
                            try
                            {
                                datamanage.deleteAll(informationbean.class);
                                for(informationbean informationbean:testlist)
                                {
                                    datamanage.save(informationbean);
                                }
                            }
                            catch(Exception e)
                            {
                            }
                        }
                    }
                    catch(Exception e)
                    {
                    }
                }
                public void onStart()
                {
                }
                public void onFailure(HttpException error,String msg)
                {
                    stoplistview();
                    Toast.makeText(instance,"获取列表失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            stoplistview();
            Toast.makeText(instance,"网络异常",Toast.LENGTH_SHORT).show();
        }
    }
    public void onLoadMore()
    {
    }
}
