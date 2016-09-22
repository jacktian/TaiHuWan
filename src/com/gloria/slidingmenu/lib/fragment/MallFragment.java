package com.gloria.slidingmenu.lib.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gloria.hbh.main.Activity_FranchiseShops;
import com.gloria.hbh.main.Activity_HuoDongProgram;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;

/*
 * 商城页面
 */
@SuppressLint("ValidFragment")
public class MallFragment extends BaseFragment
{
    String text="咨讯";
    Activity_Main mMain=null;
    private FrameLayout mFrameLayout=null;
    WebView webView;
    String mallUrl="http://ccjoy.tmall.com/";
    
    // String mallUrl ="http://www.baidu.com/";
    public MallFragment(String text)
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
        View view=inflater.inflate(R.layout.activity_webview,null);
        setView(view);
        setListener();
        setWebView();
        return view;
    }
    private void setView(View view)
    {
        titlebar=(LinearLayout)view.findViewById(R.id.titlebar);
        titlebar.setVisibility(View.VISIBLE);
        titlebar_name=(TextView)view.findViewById(R.id.titlebar_name); 
        titlebar_name.setVisibility(View.VISIBLE); 
        titlebar_name.setText("纪念商品");
        titlebar_name.setTextColor(Color.BLACK); 
        webView=(WebView)view.findViewById(R.id.webview);
    }
    private void setWebView()
    {
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webView.loadUrl("http://ccjoy.m.tmall.com/");
        webView.setWebChromeClient(new WebChromeClient(){
            public boolean shouldOverrideUrlLoading(WebView view,String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }
    private void setListener()
    {
    }
    
    private OnClickListener onClickListener=new OnClickListener(){
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.titlebar_menu:
                break;
                case R.id.titlebar_right:
                    goToFranchiseShops();
                break;
            }
        }
    };
    
    /*
     * 进入特许零售店
     */
    protected void goToFranchiseShops()
    {
        Intent intent=new Intent();
        intent.setClass(mMain,Activity_FranchiseShops.class);
        startActivity(intent);
    }
}
