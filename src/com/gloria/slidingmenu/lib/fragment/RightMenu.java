package com.gloria.slidingmenu.lib.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.ForumToast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

/*
 * 右侧菜单
 */
@SuppressLint("ValidFragment")
public class RightMenu extends BaseFragment{
	
//	private int tab_index;
	
	private static final String NAME = "name";
	private static final String IMAGE = "image";
	
	private FrameLayout mFrameLayout = null;
	private Activity_Main mActivity = null;
	private View contextView = null;
	private List<Map<String,Object>> listitems;
	private Map<String,Object> listitem;
	
	public static String[] mResArrayTextRight = BaseApplication.getInstance().getApplicationContext()
	.getResources().getStringArray(R.array.rightmenu);
	
	int[] img_s=new int[]{R.drawable.icon_home,R.drawable.icon_map,R.drawable.icon_food_drink,
			R.drawable.icon_camera,R.drawable.icon_about,R.drawable.icon_update};
	int[] img_n=new int[]{R.drawable.icon_home,R.drawable.icon_map,R.drawable.icon_food_drink,
			R.drawable.icon_camera,R.drawable.icon_about,R.drawable.icon_update};
	
	ListView listview;
	public static RightMenuAdapter rightMenuAdapter;
	
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	
    boolean isUpdate =false;
    private final int DIALOG_NONETWORK = 0; // 提示无网络
	private final int DIALOG_VERSIONUPDATE = 1; // 提示版本更新
	String apkUrl;
	int code;
	String version;
	
	public RightMenu() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (Activity_Main) getActivity();
		mFrameLayout = (FrameLayout) mActivity.findViewById(R.id.content_main);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		contextView = inflater.inflate(R.layout.list_menu, container, false);
		listview=(ListView)contextView.findViewById(R.id.listview_menu);
		
		setView();
		return contextView;
	}
		
	private void setView() {
		listitems = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i < mResArrayTextRight.length;i++){
			listitem = new HashMap<String,Object>();
			listitem.put(IMAGE, img_n[i]);
			listitem.put(NAME,mResArrayTextRight[i]);
			listitems.add(listitem);
		}
		rightMenuAdapter = new RightMenuAdapter(mActivity);
		rightMenuAdapter.setSelectPos(Activity_Main.menu_selectPos);
		listview.setAdapter(rightMenuAdapter);
		listview.setBackgroundResource(R.drawable.bg_transparent);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				rightMenuAdapter.setSelectPos(position);
				rightMenuAdapter.notifyDataSetChanged();
				mFrameLayout.setVisibility(View.VISIBLE);
				
				fragmentManager = mActivity.getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				Intent intent = new Intent();
				
				String text = rightMenuAdapter.getItem(position);
				
				Activity_Main.radioGroup.setVisibility(View.GONE);
				switch (position) {
				case 0:
					Activity_Main.menu_style = 0;
					Activity_Main.radioGroup.setVisibility(View.VISIBLE);
					text = text +"_资讯";
					InformationFragment informationFragment = (InformationFragment) fragmentManager.findFragmentByTag(text);
					fragmentTransaction.replace(R.id.content_main,
							informationFragment == null ? new InformationFragment("资讯"):informationFragment,text);
					break;
				case 1:
					Activity_Main.menu_style = 10;
					MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(text);
					fragmentTransaction.replace(R.id.content_main,
							mapFragment == null ? new MapFragment("地图导览"):mapFragment,text);
					break;
				case 2:
					Activity_Main.menu_style = 20;
					FoodLodgingTouristFragment foodLodgingTouristFragment = (FoodLodgingTouristFragment) fragmentManager.findFragmentByTag(text);
					fragmentTransaction.replace(R.id.content_main,
							foodLodgingTouristFragment == null ? new FoodLodgingTouristFragment("餐饮"):foodLodgingTouristFragment,text);
					break;
				case 3:
					Activity_Main.menu_style = 30;
					WatermarkBlogsFragment watermarkBlogsFragment = (WatermarkBlogsFragment) fragmentManager.findFragmentByTag(text);
					fragmentTransaction.replace(R.id.content_main,
							watermarkBlogsFragment == null ? new WatermarkBlogsFragment("水印游记"):watermarkBlogsFragment,text);
					break;
				
				case 4:
					Activity_Main.menu_style = 50;
					Activity_Main.menu_style = 40;
					AboutAsFragment aboutAsFragment = (AboutAsFragment) fragmentManager.findFragmentByTag(text);
					fragmentTransaction.replace(R.id.content_main,
							aboutAsFragment == null ? new AboutAsFragment("关于我们"):aboutAsFragment,text);
					break;
				case 5:
					Activity_Main.menu_style = 60;
					new CheckUpdateRequestTask().execute();
					return;
				default:
					break;
				}
				
				fragmentTransaction.addToBackStack(text);
				fragmentTransaction.commit();
				
				mActivity.getSlidingMenu().toggle();
			}
		});
	}
	
	/**
	 * 异步加载
	 */
	class CheckUpdateRequestTask extends AsyncTask<Void, Void, Boolean> {     //异步请求
		@SuppressWarnings("deprecation")
		ParseQuery updateQuery;
		protected void onPostExecute(Boolean result) {
			if(!result){ 
				ForumToast.show(getString(R.string.theLastestVersion));
				return;
			}
			//自动更新，跳转到电子市场
			showDialog(DIALOG_VERSIONUPDATE);
		}

		protected Boolean doInBackground(Void... params) {	
			boolean isUpdate = false; //false:没更新
			updateQuery = new ParseQuery("AndroidUpdate");//News
			updateQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
			updateQuery.setMaxCacheAge(10*1024);
			
			try {
				List<ParseObject> object = updateQuery.find();
				if(object != null && object.size() != 0){
					apkUrl = (String) object.get(0).get("url");
					version = (String) object.get(0).get("version");
					int code = object.get(0).getInt("code");
					PackageManager pm = mActivity.getPackageManager();  
			    	PackageInfo pi;
			    	pi = pm.getPackageInfo(mActivity.getPackageName(), 0);
			    	if(version != null && (code > pi.versionCode) && apkUrl != null && !apkUrl.equals("")) {
			    		isUpdate =  true;
			    	}
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}catch (ParseException e) {
				e.printStackTrace();
			}
		    return isUpdate;
		}	
	}
	
    public void showDialog(int id) {
    	switch (id) {
		case DIALOG_NONETWORK:
			new AlertDialog.Builder(mActivity).setTitle(R.string.hint_Msg)
					.setMessage(R.string.msg_isSetNetwork).setPositiveButton(R.string.ok,networkListener)
					.setNegativeButton(R.string.cancel,networkListener).create().show();
		case DIALOG_VERSIONUPDATE:
			new AlertDialog.Builder(mActivity).setTitle(R.string.hint_Msg)
					.setMessage(R.string.msg_isUpdateVersion).setPositiveButton(R.string.ok,gotoUpdateListener)
					.setNegativeButton(R.string.cancel,null).create().show();
		}
	}

	//对话框监听事件
	DialogInterface.OnClickListener networkListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));  //打开设置网络的界面
		}
	};
	DialogInterface.OnClickListener gotoUpdateListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl));  
            startActivity(intent);
		}
	};
	
	public void onResume() {
		super.onResume();
		if(rightMenuAdapter != null){
			rightMenuAdapter.notifyDataSetChanged();
		}
	}
	
	//栏目列表的适配器
	public class RightMenuAdapter extends BaseAdapter {
		Context context;
		//初始选择
		int selectPos = 0;
		public RightMenuAdapter(Context context){
			this.context=context;
		}
		
		public int getSelectPos() {
			return selectPos;
		}

		public void setSelectPos(int selectPos) {
			this.selectPos = selectPos;
		}

		public int getCount() {
			return mResArrayTextRight.length;
		}

		public String getItem(int position) {
			return mResArrayTextRight[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public final View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;			
			 if(convertView == null){
				 holder = new ViewHolder();
				 convertView = LayoutInflater.from(mActivity).inflate(R.layout.listview_menu_item, null);
				 holder.nImageView = (TextView) convertView.findViewById(R.id.item_image_meun);
				 holder.nTextView = (TextView) convertView.findViewById(R.id.item_content_meun);
				 holder.nImageView.setVisibility(View.VISIBLE);
				 convertView.setTag(holder);
			 }else{
				 holder = (ViewHolder) convertView.getTag();
			 }
			 holder.nImageView.setBackgroundResource(img_n[position]);
			 holder.nTextView.setText(getItem(position));
			 holder.nTextView.setTextColor(getResources().getColor(R.color.white));
			 convertView.setBackgroundResource(R.drawable.bg_transparent);
			 if(selectPos == position){
				 holder.nTextView.setTextColor(getResources().getColor(R.color.black));
				 convertView.setBackgroundResource(R.drawable.bg_side_item);
			 }
			 return convertView; 
		}
		class ViewHolder{
			TextView nImageView;
			TextView nTextView;
			ImageView nImageView1;
			ListView listview;
		}
	}
}