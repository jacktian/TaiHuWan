package com.gloria.slidingmenu.lib.fragment;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gloria.hbh.data.app.LeftSideData;
import com.gloria.hbh.data.forum.ExhibitionInfo;
import com.gloria.hbh.main.Activity_ExhibitionInfo;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.ForumToast;
import com.parse.ParseObject;

/*
 * 左侧菜单
 */
@SuppressLint("ValidFragment")
public class LeftMenu extends BaseFragment{
	
	private FrameLayout mFrameLayout = null;
	private Activity_Main mActivity = null;
	private View contextView = null;
	private List<Map<String,Object>> listitems;
	private Map<String,Object> listitem;
	
	ExpandableListView expandableListView;
	LeftMenuAdapter leftMenuAdapter;
	
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	
	public LeftMenu(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (Activity_Main) getActivity();
		mFrameLayout = (FrameLayout) mActivity.findViewById(R.id.content_main);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		contextView = inflater.inflate(R.layout.expandable_list_menu, container, false);
		expandableListView=(ExpandableListView)contextView.findViewById(R.id.expandablelistview_menu);
		
		setView();
		return contextView;
	}
		
	private void setView() {
		leftMenuAdapter = new LeftMenuAdapter(mActivity);
		expandableListView.setAdapter(leftMenuAdapter);
		expandableListView.setBackgroundResource(R.drawable.bg_transparent);
		expandableListView.setSelector(getResources().getDrawable(R.drawable.bg_transparent));
		expandableListView.setGroupIndicator(null);
		expandableListView.setChildIndicator(null);
		
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				ForumToast.show(leftMenuAdapter.getGroup(groupPosition).getTitle());
				return false;
			}
		});
		
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition, final int childPosition, long id) {
				// TODO Auto-generated method stub
				leftMenuAdapter.setSelectPos(childPosition);
				leftMenuAdapter.notifyDataSetChanged();
				mActivity.getSlidingMenu().toggle();
				
				RightMenu.rightMenuAdapter.setSelectPos(1);
				RightMenu.rightMenuAdapter.notifyDataSetChanged();
				fragmentManager = mActivity.getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				
				Activity_Main.menu_style = 10;
				Activity_Main.radioGroup.setVisibility(View.GONE);
				String text = RightMenu.mResArrayTextRight[1];
				
				if(leftMenuAdapter.getGroup(groupPosition).getTitle().equals("辅展区")){
					Intent intent = new Intent();
					intent.setClass(mActivity, Activity_ExhibitionInfo.class);
					intent.putExtra("name", leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getTitle());
					intent.putExtra("detail", leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getDetail());
					intent.putStringArrayListExtra("images", leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getImages());
					startActivity(intent);
					return false;
				}
				
				int delayTime = 0;
				
				MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(text);
				if(mapFragment == null){
					mapFragment = new MapFragment("地图导览");
					delayTime = 5*1000;
				}
				fragmentTransaction.replace(R.id.content_main,mapFragment,text);
				fragmentTransaction.addToBackStack(text);
				fragmentTransaction.commit();
				fragmentManager.executePendingTransactions();
				
				final MapFragment map = mapFragment;
				new Handler().postDelayed(new Runnable(){   
				    public void run() { 
				    	int viewid = leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getId();
						String location = leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getPoint();
						map.mapMoveToScreenXY(viewid,leftMenuAdapter.getGroup(groupPosition).getSublist().get(childPosition).getTitle(),location);
				    }   
				}, delayTime); 
				return false;
			}
		});
	}
	
	public void onResume() {
		super.onResume();
		if(leftMenuAdapter != null){
			leftMenuAdapter.notifyDataSetChanged();
		}
	}
	
	//栏目列表的适配器
	public class LeftMenuAdapter extends BaseExpandableListAdapter {
		Context context;
		//初始选择
		int selectPos = 0;
		public LeftMenuAdapter(Context context){
			this.context=context;
		}
		
		public int getSelectPos() {
			return selectPos;
		}

		public void setSelectPos(int selectPos) {
			this.selectPos = selectPos;
		}

		public ExhibitionInfo getChild(int groupPosition, int childPosition) {
			return LeftSideData.getInstance().getList().get(groupPosition).getSublist().get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder;			
			if(convertView == null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mActivity).inflate(R.layout.listview_menu_item, null);
				holder.nImageView = (TextView) convertView.findViewById(R.id.item_image_meun);
				holder.nTextView = (TextView) convertView.findViewById(R.id.item_content_meun);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.nImageView.setVisibility(View.INVISIBLE);
			holder.nTextView.setText(getChild(groupPosition, childPosition).getTitle());
			holder.nTextView.setTextColor(getResources().getColor(R.color.white));
			holder.nTextView.setTextSize(16);
			return convertView;
		}

		public int getChildrenCount(int groupPosition) {
			return LeftSideData.getInstance().getList().get(groupPosition).getSublist().size();
		}

		public ExhibitionInfo getGroup(int groupPosition) {
			return LeftSideData.getInstance().getList().get(groupPosition);
		}

		public int getGroupCount() {
			return LeftSideData.getInstance().getList().size();
		}

		public long getGroupId(int groupPosition) {
			return 0;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			ViewHolder holder;			
			if(convertView == null){
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mActivity).inflate(R.layout.listview_menu_item, null);
				holder.nImageView = (TextView) convertView.findViewById(R.id.item_image_meun);
				holder.nTextView = (TextView) convertView.findViewById(R.id.item_content_meun);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.nTextView.setText(getGroup(groupPosition).getTitle());
			holder.nTextView.setTextColor(getResources().getColor(R.color.white));
			holder.nTextView.setTextSize(18);
			
			return convertView;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
	
	class ViewHolder{
		TextView nImageView;
		TextView nTextView;
		ImageView nImageView1;
		ListView listview;
	}
	
	class MapMoveRequestTask extends AsyncTask<String, Void,String> { 
    	List<ParseObject>  list;
  	  	protected void onPostExecute(String result) {
			return;
		}
	  
  	  	protected void onPreExecute() {
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		return "";
	  	}
    }
}