package com.gloria.hbh.main;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gloria.hbh.adapter.FranchiseShopsAdapter;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.data.app.RetailStores;
import com.gloria.hbh.data.forum.StoreInfo;
import com.gloria.hbh.util.Methods;
import com.gloria.pulltorefresh.library.PullToRefreshBase;
import com.gloria.pulltorefresh.library.PullToRefreshBase.Mode;
import com.gloria.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.gloria.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.gloria.pulltorefresh.library.PullToRefreshListView;

/*
 * 特许零售店页面
 */
public class Activity_FranchiseShops extends Activity_Base{
	
	PullToRefreshListView listview;
	ListView actualListView;
	ArrayList<StoreInfo> list;
	FranchiseShopsAdapter franchiseShopsAdapter;
	
	ProgressDialog pdialog;
	boolean isRefresh = true;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        
		setView();
		setListener();
		
		new InfoRequestTask().execute();
	}

	private void setView() {
		titlebar = (LinearLayout)findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
    	titlebar_name = (TextView)findViewById(R.id.titlebar_name);
		titlebar_name.setText("特许零售店");
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_back = (Button)findViewById(R.id.titlebar_back);
	 	titlebar_right = (Button)findViewById(R.id.titlebar_right);
	 	titlebar_back.setVisibility(View.VISIBLE);
	 	titlebar_right.setVisibility(View.INVISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	
	 	listview = (PullToRefreshListView)findViewById(R.id.listView);   
		listview.setMode(Mode.PULL_FROM_START);
		actualListView = listview.getRefreshableView();
		actualListView.setBackgroundResource(R.drawable.bg_main);
		actualListView.setCacheColorHint(getResources().getColor(R.color.transparent));
		
		//注册菜单
		registerForContextMenu(actualListView);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(com.gloria.hbh.util.TextUtils.getCurrentTime());
				goToRefresh();
			}
		});

		listview.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			public void onLastItemVisible() {
//				goToLoadingData();
			}
		});
	}
	
	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_right.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			case R.id.titlebar_right:
				break;
			default:
				break;
			}
		}
	};
	
	public void goToRefresh() {  
    	isRefresh = true;
    	new InfoRequestTask().execute();
    }  
	
	public void setListView(ArrayList<StoreInfo> result) {
		franchiseShopsAdapter = new FranchiseShopsAdapter(list);
		actualListView.setAdapter(franchiseShopsAdapter);
	}
    
    class InfoRequestTask extends AsyncTask<String, Void,ArrayList<StoreInfo>> { 
  	  	protected void onPostExecute(ArrayList<StoreInfo> result) {
  	  		pdialog.cancel();
  	  		listview.onRefreshComplete();
			if(Methods.disposeDataException(result)){
				return;
			}
			//设置列表视图
			setListView(result);
			return;
		}
	  
  	  protected void onPreExecute() {
			super.onPreExecute();
			if(pdialog == null){
				pdialog = new ProgressDialog(Activity_FranchiseShops.this);
				pdialog.setMessage(getString(R.string.msg_loading));
				pdialog.setCancelable(BaseConstants.isCancelable);
			}
			pdialog.show();			
		}
		
		protected ArrayList<StoreInfo> doInBackground(String... params) {
			list = RetailStores.getInstance().getList();
	  		return list;
	  	}
    }
}
