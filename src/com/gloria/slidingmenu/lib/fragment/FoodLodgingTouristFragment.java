package com.gloria.slidingmenu.lib.fragment;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.DistanceUtil;
import com.gloria.hbh.adapter.FoodAdapter;
import com.gloria.hbh.adapter.HandlineListsAdapter;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.data.app.RecommondSightspots;
import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.main.Activity_Detail;
import com.gloria.hbh.main.Activity_FoodLodgingDetail;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.util.Methods;
import com.gloria.pulltorefresh.library.PullToRefreshBase;
import com.gloria.pulltorefresh.library.PullToRefreshBase.Mode;
import com.gloria.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.gloria.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.gloria.pulltorefresh.library.PullToRefreshListView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
 * 餐饮住宿页面
 */
@SuppressLint("ValidFragment")

@SuppressWarnings("static-access")
public class FoodLodgingTouristFragment extends BaseFragment {

	// private static final String TAB_DATA = "tab_data";
	// private static final String TAB_ADAPTER = "tab_adapter";

	String text = "咨讯";
//
//	Activity_Main mMain = null;
//	private FrameLayout mFrameLayout = null;
//
//	// 底部视图
//	LinearLayout footView;
//
//	// 定位相关
//	LocationClient mLocClient;
//	LocationData locData = null;
//	public MyLocationListenner myListener = new MyLocationListenner();
//	GeoPoint localGeoPoint;
//
//	boolean isFirstSearch = true;
//	private MKSearch foodSearch = null; // 搜索模块，也可去掉地图模块独立使用
//	private int load_Index_food = 1;
//	// boolean isGetFood = false;
//
//	private MKSearch lodgingSearch = null; // 搜索模块，也可去掉地图模块独立使用
//	private int load_Index_lodging = 1;
//	// boolean isGetlodging = false;
//
//	ArrayList<HandlinesBasicInfo> foodlists;
//	List<ParseObject> foodlistParseObject;
//	FoodAdapter foodAdapter;
//
//	ArrayList<HandlinesBasicInfo> lodginglists;
//	List<ParseObject> lodginglistParseObject;
//	FoodAdapter lodgingAdapter;
//
//	ArrayList<HandlinesBasicInfo> touristlists;
//	HandlineListsAdapter touristAdapter;
//
//	PullToRefreshListView listview;
//	ListView actualListView;
//
//	RadioGroup radio;
//	RadioButton btn_food, btn_lodging, btn_tourist;
//
//	ProgressDialog pdialog;
//	// int tabIndex = 0;
//	boolean isRefresh = true;
//	boolean isFirstShow = true;
//
	public FoodLodgingTouristFragment() {
	}

	public FoodLodgingTouristFragment(String text) {
		this.text = text;
	}
//
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setRetainInstance(true);
//
//		mMain = (Activity_Main) getActivity();
//		mFrameLayout = (FrameLayout) mMain.findViewById(R.id.content_main);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.activity_foodlodgingtourist, null);
//		footView = (LinearLayout) inflater.inflate(R.layout.foot_food, null);
//		setView(view);
//		setListener();
//		initLocation();
//
//		btn_food.setChecked(true);
//		changBtnBg(btn_food);
//		if (btn_food.isChecked()) {
//			if (getFoodList().size() != 0 && foodAdapter != null) {
//				actualListView.setAdapter(foodAdapter);
//			} else {
//				new FoodRequestTask().execute();
//			}
//		}
//		return view;
//	}
//
//	private void initLocation() {
//		// 定位初始化
//		mLocClient = new LocationClient(mMain);
//		mLocClient.setAK(BaseApplication.strKey);
//		locData = new LocationData();
//		mLocClient.registerLocationListener(myListener);
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);// 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setScanSpan(1000);
//		mLocClient.setLocOption(option);
//		mLocClient.start();
//	}
//
//	private void setView(View view) {
//		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
//		titlebar.setVisibility(View.VISIBLE);
//		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
//		titlebar_menu = (Button) view.findViewById(R.id.titlebar_menu);
//		titlebar_left = (Button) view.findViewById(R.id.titlebar_left);
//		titlebar_menu.setVisibility(View.VISIBLE);
//		titlebar_left.setVisibility(View.INVISIBLE);
//		titlebar_name.setVisibility(View.VISIBLE);
//		titlebar_name.setText("吃住游");
//		titlebar_name.setTextColor(Color.BLACK);
//
//		radio = (RadioGroup) view.findViewById(R.id.radio);
//		btn_food = (RadioButton) view.findViewById(R.id.btn_food);
//		btn_food.setChecked(true);
//		btn_lodging = (RadioButton) view.findViewById(R.id.btn_lodging);
//		btn_tourist = (RadioButton) view.findViewById(R.id.btn_tourist);
//
//		listview = (PullToRefreshListView) view.findViewById(R.id.listView);
//		listview.setMode(Mode.PULL_FROM_START);
//		actualListView = listview.getRefreshableView();
//
//		// 注册菜单
//		registerForContextMenu(actualListView);
//		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(com.gloria.hbh.util.TextUtils.getCurrentTime());
//				goToRefresh();
//			}
//		});
//
//		listview.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//			public void onLastItemVisible() {
//				goToLoadingData();
//			}
//		});
//
//		listview.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				position = position - 1;
//				setItemClickOfList(position);
//			}
//		});
//
//		// 初始化搜索模块，注册搜索事件监听
//		foodSearch = new MKSearch();
//		foodSearch.init(BaseApplication.getInstance().mBMapManager, new MKSearchListener() {
//			// 在此处理详情页结果
//			@Override
//			public void onGetPoiDetailSearchResult(int type, int error) {
//				if (error != 0) {
//					ForumToast.show("抱歉，未找到结果");
//				} else {
//					ForumToast.show("成功，查看详情页面");
//				}
//			}
//
//			/**
//			 * 在此处理poi搜索结果
//			 */
//			public void onGetPoiResult(MKPoiResult res, int type, int error) {
//				if (pdialog != null && pdialog.isShowing()) {
//					pdialog.dismiss();
//				}
//				// 错误号可参考MKEvent中的定义
//				if (error != 0 || res == null) {
//					ForumToast.show("抱歉，未找到结果");
//					return;
//				}
//				// 将地图移动到第一个POI中心点
//				if (res.getCurrentNumPois() > 0) {
//					if (btn_food.isChecked()) {
//						for (MKPoiInfo info : res.getAllPoi()) {
//							if (info.pt != null) {
//								// isGetFood = true;
//								HandlinesBasicInfo handlinesBasicInfo = new HandlinesBasicInfo();
//								handlinesBasicInfo.setTitle(info.name);
//								handlinesBasicInfo.setDescrip(info.address);
//								handlinesBasicInfo.setUrl(info.phoneNum);
//								handlinesBasicInfo.setAuthorId("0");
//								handlinesBasicInfo.setFid(String.valueOf(info.pt.getLatitudeE6()));
//								handlinesBasicInfo.setTid(String.valueOf(info.pt.getLongitudeE6()));
//								double distance = DistanceUtil.getDistance(info.pt, localGeoPoint);
//								handlinesBasicInfo.setAuthor(String.valueOf((int) distance) + "米");
//								getFoodList().add(handlinesBasicInfo);
//							}
//						}
//						if (foodAdapter == null) {
//							foodAdapter = new FoodAdapter(getFoodList(), imageLoader);
//						}
//						foodAdapter.notifyDataSetChanged();
//					} else if (btn_lodging.isChecked()) {
//						for (MKPoiInfo info : res.getAllPoi()) {
//							if (info.pt != null) {
//								// isGetFood = true;
//								HandlinesBasicInfo handlinesBasicInfo = new HandlinesBasicInfo();
//								handlinesBasicInfo.setTitle(info.name);
//								handlinesBasicInfo.setDescrip(info.address);
//								handlinesBasicInfo.setUrl(info.phoneNum);
//								handlinesBasicInfo.setAuthorId("0");
//								handlinesBasicInfo.setFid(String.valueOf(info.pt.getLatitudeE6()));
//								handlinesBasicInfo.setTid(String.valueOf(info.pt.getLongitudeE6()));
//								double distance = DistanceUtil.getDistance(info.pt, localGeoPoint);
//								handlinesBasicInfo.setAuthor(String.valueOf((int) distance) + "米");
//								getLodgingList().add(handlinesBasicInfo);
//							}
//						}
//						if (lodgingAdapter == null) {
//							lodgingAdapter = new FoodAdapter(getLodgingList(), imageLoader);
//						}
//						lodgingAdapter.notifyDataSetChanged();
//					}
//				} else if (res.getCityListNum() > 0) {
//					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
//					String strInfo = "在";
//					for (int i = 0; i < res.getCityListNum(); i++) {
//						strInfo += res.getCityListInfo(i).city;
//						strInfo += ",";
//					}
//					strInfo += "找到结果";
//					ForumToast.show(strInfo);
//				}
//			}
//
//			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
//			}
//
//			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
//			}
//
//			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
//			}
//
//			public void onGetAddrResult(MKAddrInfo res, int error) {
//			}
//
//			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
//			}
//
//			/**
//			 * 更新建议列表
//			 */
//			@Override
//			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
//			}
//
//			@Override
//			public void onGetShareUrlResult(MKShareUrlResult result, int type, int error) {
//			}
//		});
//
//		// lodgingSearch = new MKSearch();
//		// lodgingSearch.init(BaseApplication.getInstance().mBMapManager, new
//		// MKSearchListener(){
//		// //在此处理详情页结果
//		// @Override
//		// public void onGetPoiDetailSearchResult(int type, int error) {
//		// if (error != 0) {
//		// ForumToast.show("抱歉，未找到结果");
//		// }
//		// else {
//		// ForumToast.show("成功，查看详情页面");
//		// }
//		// }
//		// /**
//		// * 在此处理poi搜索结果
//		// */
//		// public void onGetPoiResult(MKPoiResult res, int type, int error) {
//		// // 错误号可参考MKEvent中的定义
//		// if (error != 0 || res == null) {
//		// ForumToast.show("抱歉，未找到结果");
//		// return;
//		// }
//		// // 将地图移动到第一个POI中心点
//		// if (res.getCurrentNumPois() > 0) {
//		// for( MKPoiInfo info : res.getAllPoi() ){
//		// if ( info.pt != null ){
//		//// isGetlodging = true;
//		// HandlinesBasicInfo handlinesBasicInfo = new HandlinesBasicInfo();
//		// handlinesBasicInfo.setTitle(info.name);
//		// handlinesBasicInfo.setDescrip(info.address);
//		// getLodgingList().add(handlinesBasicInfo);
//		// }
//		// }
//		// lodgingAdapter.notifyDataSetChanged();
//		// } else if (res.getCityListNum() > 0) {
//		// //当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
//		// String strInfo = "在";
//		// for (int i = 0; i < res.getCityListNum(); i++) {
//		// strInfo += res.getCityListInfo(i).city;
//		// strInfo += ",";
//		// }
//		// strInfo += "找到结果";
//		// ForumToast.show(strInfo);
//		// }
//		// }
//		// public void onGetDrivingRouteResult(MKDrivingRouteResult res,
//		// int error) {
//		// }
//		// public void onGetTransitRouteResult(MKTransitRouteResult res,
//		// int error) {
//		// }
//		// public void onGetWalkingRouteResult(MKWalkingRouteResult res,
//		// int error) {
//		// }
//		// public void onGetAddrResult(MKAddrInfo res, int error) {
//		// }
//		// public void onGetBusDetailResult(MKBusLineResult result, int iError)
//		// {
//		// }
//		// /**
//		// * 更新建议列表
//		// */
//		// @Override
//		// public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
//		//
//		// }
//		// @Override
//		// public void onGetShareUrlResult(MKShareUrlResult result, int type,
//		// int error) {
//		// // TODO Auto-generated method stub
//		//
//		// }
//		// });
//	}
//
//	private void setListener() {
//		titlebar_menu.setOnClickListener(onClickListener);
//		titlebar_left.setOnClickListener(onClickListener);
//		titlebar_name.setOnClickListener(onClickListener);
//		btn_food.setOnClickListener(onClickListener);
//		btn_lodging.setOnClickListener(onClickListener);
//		btn_tourist.setOnClickListener(onClickListener);
//	}
//
//	private OnClickListener onClickListener = new OnClickListener() {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.titlebar_menu:
//				if (mMain.getSlidingMenu().isSecondaryMenuShowing()) {
//					mMain.getSlidingMenu().toggle();
//				} else {
//					mMain.getSlidingMenu().showSecondaryMenu();
//				}
//				break;
//			case R.id.titlebar_left:
//				break;
//			case R.id.btn_food:
//				changBtnBg(btn_food);
//				if (localGeoPoint != null) {
//					foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//					// if(btn_food.isChecked()){
//					// foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//					// }else if(btn_lodging.isChecked()){
//					// lodgingSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//					// }
//				}
//				if (foodAdapter != null) {
//					actualListView.setAdapter(foodAdapter);
//				} else {
//					new FoodRequestTask().execute();
//				}
//				break;
//			case R.id.btn_lodging:
//				changBtnBg(btn_lodging);
//				if (localGeoPoint != null) {
//					foodSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//					// if(btn_food.isChecked()){
//					// foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//					// }else if(btn_lodging.isChecked()){
//					// lodgingSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//					// }
//				}
//				if (lodgingAdapter != null) {
//					actualListView.setAdapter(lodgingAdapter);
//				} else {
//					new LodgingRequestTask().execute();
//				}
//				break;
//			case R.id.btn_tourist:
//				changBtnBg(btn_tourist);
//				if (touristAdapter != null) {
//					actualListView.setAdapter(touristAdapter);
//				} else {
//					new TouristRequestTask().execute();
//				}
//				break;
//			default:
//				break;
//			}
//		}
//
//	};
//
//	// private OnCheckedChangeListener onCheckedChangeListener = new
//	// OnCheckedChangeListener() {
//	// public void onCheckedChanged(CompoundButton buttonView, boolean
//	// isChecked) {
//	// if(buttonView.getId() == btn_food.getId()){
//	// if(foodAdapter != null){
//	// actualListView.setAdapter(foodAdapter);
//	// }else{
//	// new FoodRequestTask().execute();
//	// }
//	// }else if(buttonView.getId() == btn_lodging.getId()){
//	// if(lodgingAdapter != null){
//	// actualListView.setAdapter(lodgingAdapter);
//	// }else{
//	// new LodgingRequestTask().execute();
//	// }
//	// }else if(buttonView.getId() == btn_tourist.getId()){
//	// if(touristAdapter != null){
//	// actualListView.setAdapter(touristAdapter);
//	// }else{
//	// //TODO
//	//// new LodgingRequestTask().execute();
//	// }
//	// }
//	// }
//	// };
//
//	protected void changBtnBg(RadioButton radioButton) {
//		switch (radioButton.getId()) {
//		case R.id.btn_food:
//			btn_food.setBackgroundResource(R.drawable.icon_food_sel);
//			btn_lodging.setBackgroundResource(R.drawable.icon_lodging);
//			btn_tourist.setBackgroundResource(R.drawable.icon_tourist);
//			break;
//		case R.id.btn_lodging:
//			btn_food.setBackgroundResource(R.drawable.icon_food);
//			btn_lodging.setBackgroundResource(R.drawable.icon_lodging_sel);
//			btn_tourist.setBackgroundResource(R.drawable.icon_tourist);
//			break;
//		case R.id.btn_tourist:
//			btn_food.setBackgroundResource(R.drawable.icon_food);
//			btn_lodging.setBackgroundResource(R.drawable.icon_lodging);
//			btn_tourist.setBackgroundResource(R.drawable.icon_tourist_sel);
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	protected List<ParseObject> getFoodListParseObject() {
//		if (foodlistParseObject == null) {
//			foodlistParseObject = new ArrayList<ParseObject>(1);
//		}
//		return foodlistParseObject;
//	}
//
//	protected List<HandlinesBasicInfo> getFoodList() {
//		if (foodlists == null) {
//			foodlists = new ArrayList<HandlinesBasicInfo>(1);
//		}
//		return foodlists;
//	}
//
//	protected List<ParseObject> getLodgingListParseObject() {
//		if (lodginglistParseObject == null) {
//			lodginglistParseObject = new ArrayList<ParseObject>(1);
//		}
//		return lodginglistParseObject;
//	}
//
//	protected List<HandlinesBasicInfo> getLodgingList() {
//		if (lodginglists == null) {
//			lodginglists = new ArrayList<HandlinesBasicInfo>(1);
//		}
//		return lodginglists;
//	}
//
//	protected ArrayList<HandlinesBasicInfo> getTouristList() {
//		if (touristlists == null) {
//			touristlists = new ArrayList<HandlinesBasicInfo>(1);
//		}
//		return touristlists;
//	}
//
//	/*
//	 * 加载更多数据
//	 */
//	@SuppressWarnings("static-access")
//	protected void goToLoadingData() {
//		if (pdialog == null) {
//			pdialog = new ProgressDialog(mMain);
//			pdialog.setMessage(getString(R.string.msg_loading));
//			pdialog.setCancelable(BaseConstants.isCancelable);
//		}
//		if (btn_food.isChecked()) {
//			if (!pdialog.isShowing()) {
//				pdialog.show();
//			}
//			foodSearch.goToPoiPage(++load_Index_food);
//		} else if (btn_lodging.isChecked()) {
//			if (!pdialog.isShowing()) {
//				pdialog.show();
//			}
//			foodSearch.goToPoiPage(++load_Index_lodging);
//		}
//	}
//
//	/*
//	 * 列表点击事件
//	 */
//	protected void setItemClickOfList(int position) {
//		Intent intent = new Intent();
//		if (btn_food.isChecked()) {
//			if (getFoodList().get(position).getAuthorId() != null
//					&& getFoodList().get(position).getAuthorId().equals("1")) {
//				return;
//			}
//			intent.setClass(mMain, Activity_FoodLodgingDetail.class);
//			intent.putExtra("title", getFoodList().get(position).getTitle());
//			intent.putExtra("adr", getFoodList().get(position).getDescrip());
//			intent.putExtra("phonenum", getFoodList().get(position).getUrl());
//			intent.putExtra("distance", getFoodList().get(position).getAuthor());
//			intent.putExtra("latitude", getFoodList().get(position).getFid());
//			intent.putExtra("longitude", getFoodList().get(position).getTid());
//			startActivity(intent);
//		} else if (btn_lodging.isChecked()) {
//			if (getLodgingList().get(position).getAuthorId() != null
//					&& getLodgingList().get(position).getAuthorId().equals("1")) {
//				return;
//			}
//			intent.setClass(mMain, Activity_FoodLodgingDetail.class);
//			intent.putExtra("title", getLodgingList().get(position).getTitle());
//			intent.putExtra("adr", getLodgingList().get(position).getDescrip());
//			intent.putExtra("phonenum", getLodgingList().get(position).getUrl());
//			intent.putExtra("distance", getLodgingList().get(position).getAuthor());
//			intent.putExtra("latitude", getLodgingList().get(position).getFid());
//			intent.putExtra("longitude", getLodgingList().get(position).getTid());
//			startActivity(intent);
//		} else if (btn_tourist.isChecked()) {
//
//			intent.setClass(mMain, Activity_Detail.class);
//			intent.putExtra("text", getTouristList().get(position).getTitle());
//			intent.putExtra("title", "100");
//			intent.putExtra("description", getTouristList().get(position).getDescrip());
//			intent.putExtra("imageUrl", getTouristList().get(position).getImg());
//			intent.putExtra("date", "");
//			intent.putExtra("type", "");
//			startActivity(intent);
//		}
//	}
//
//	public void onPause() {
//		super.onPause();
//		// 保存listview位置
//		// Plist.getInstance().getTabData().get(text).setFirstpos(actualListView.getFirstVisiblePosition());
//	}
//
//	public void onResume() {
//		super.onResume();
//	}
//
//	public void onDestroy() {
//		mLocClient.stop();
//		super.onDestroy();
//	}
//
//	public void setListView() {
//		if (btn_food.isChecked()) {
//			foodAdapter = new FoodAdapter(getFoodList(), imageLoader);
//			actualListView.setAdapter(foodAdapter);
//		} else if (btn_lodging.isChecked()) {
//			lodgingAdapter = new FoodAdapter(getLodgingList(), imageLoader);
//			actualListView.setAdapter(lodgingAdapter);
//		} else if (btn_tourist.isChecked()) {
//			touristAdapter = new HandlineListsAdapter(getTouristList(), imageLoader);
//			actualListView.setAdapter(touristAdapter);
//		}
//	}
//
//	public void goToRefresh() {
//		isRefresh = true;
//		if (btn_food.isChecked()) {
//			load_Index_food = 1;
//			foodAdapter = null;
//			getFoodList().clear();
//			new FoodRequestTask().execute();
//		} else if (btn_lodging.isChecked()) {
//			load_Index_lodging = 1;
//			lodgingAdapter = null;
//			getLodgingList().clear();
//			new LodgingRequestTask().execute();
//		}
//	}
//
//	class FoodRequestTask extends AsyncTask<String, Void, List<ParseObject>> {
//		List<ParseObject> list;
//
//		protected void onPostExecute(List<ParseObject> result) {
//			listview.onRefreshComplete();
//			if (Methods.disposeDataException(result)) {
//				return;
//			}
//
//			if (result.size() == 0) {
//				ForumToast.show(getString(R.string.hint_NoData));
//				actualListView.setAdapter(null);
//				return;
//			}
//			// 设置列表视图
//			setListView();
//			pdialog.cancel();
//			return;
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (pdialog == null) {
//				pdialog = new ProgressDialog(mMain);
//				pdialog.setMessage(getString(R.string.msg_loading));
//				pdialog.setCancelable(BaseConstants.isCancelable);
//			}
//			pdialog.show();
//		}
//
//		protected List<ParseObject> doInBackground(String... params) {
//			ParseQuery foodlistsQuery = new ParseQuery("RecommendRuralDish");// News
//			foodlistsQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
//			foodlistsQuery.setMaxCacheAge(10 * 1024);
//			foodlistsQuery.orderByDescending("date");
//			foodlistsQuery.setLimit(20);
//
//			try {
//				foodlistParseObject = foodlistsQuery.find();
//				for (int i = getFoodListParseObject().size() - 1; i >= 0; i--) {
//					ParseObject parseObject = getFoodListParseObject().get(i);
//					HandlinesBasicInfo handlinesInfo = new HandlinesBasicInfo();
//					handlinesInfo.setTitle(parseObject.getString("title"));
//					handlinesInfo.setDescrip(parseObject.getString("subtitle"));
//					handlinesInfo.setUrl(parseObject.getString("phoneNumber"));
//					handlinesInfo.setAuthorId("1");
//					// getFoodList().add(0,handlinesInfo);
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//			if (localGeoPoint != null && isFirstSearch) {
//				isFirstSearch = false;
//				foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//				// if(btn_food.isChecked()){
//				// foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//				// }else if(btn_lodging.isChecked()){
//				// lodgingSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//				// }
//			}
//			return foodlistParseObject;
//		}
//	}
//
//	class LodgingRequestTask extends AsyncTask<String, Void, List<ParseObject>> {
//		List<ParseObject> list;
//
//		protected void onPostExecute(List<ParseObject> result) {
//			listview.onRefreshComplete();
//			if (Methods.disposeDataException(result)) {
//				return;
//			}
//			// 设置列表视图
//			setListView();
//			pdialog.cancel();
//			return;
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (pdialog == null) {
//				pdialog = new ProgressDialog(mMain);
//				pdialog.setMessage(getString(R.string.msg_loading));
//				pdialog.setCancelable(BaseConstants.isCancelable);
//			}
//			pdialog.show();
//		}
//
//		protected List<ParseObject> doInBackground(String... params) {
//			ParseQuery hotellistsQuery = new ParseQuery("RecommendHotel");// News
//			hotellistsQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
//			hotellistsQuery.setMaxCacheAge(10 * 1024);
//			hotellistsQuery.orderByDescending("date");
//			hotellistsQuery.setLimit(20);
//
//			try {
//				lodginglistParseObject = hotellistsQuery.find();
//				for (int i = getLodgingListParseObject().size() - 1; i >= 0; i--) {
//					ParseObject parseObject = getLodgingListParseObject().get(i);
//					HandlinesBasicInfo handlinesInfo = new HandlinesBasicInfo();
//					handlinesInfo.setTitle(parseObject.getString("title"));
//					handlinesInfo.setDescrip(parseObject.getString("subtitle"));
//					handlinesInfo.setUrl(parseObject.getString("phoneNumber"));
//					handlinesInfo.setAuthorId("1");
//					// handlinesInfo.setImg(parseObject.getParseFile("imageFile").getUrl());
//					// handlinesInfo.setAuthor(parseObject.getString("subtitle"));
//					// getLodgingList().add(handlinesInfo);
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			if (localGeoPoint != null) {
//				foodSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//				// if(btn_food.isChecked()){
//				// foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//				// }else if(btn_lodging.isChecked()){
//				// lodgingSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//				// }
//			}
//
//			return lodginglistParseObject;
//		}
//	}
//
//	class TouristRequestTask extends AsyncTask<String, Void, ArrayList<HandlinesBasicInfo>> {
//		protected void onPostExecute(ArrayList<HandlinesBasicInfo> result) {
//			pdialog.cancel();
//			listview.onRefreshComplete();
//			if (Methods.disposeDataException(result)) {
//				return;
//			}
//			// 设置列表视图
//			setListView();
//			return;
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (pdialog == null) {
//				pdialog = new ProgressDialog(mMain);
//				pdialog.setMessage(getString(R.string.msg_loading));
//				pdialog.setCancelable(BaseConstants.isCancelable);
//			}
//			pdialog.show();
//		}
//
//		protected ArrayList<HandlinesBasicInfo> doInBackground(String... params) {
//			touristlists = RecommondSightspots.getInstance().getList();
//			return touristlists;
//		}
//	}
//
//	/**
//	 * 定位SDK监听函数
//	 */
//	public class MyLocationListenner implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
//
//			locData.latitude = location.getLatitude();
//			locData.longitude = location.getLongitude();
//			// 如果不显示定位精度圈，将accuracy赋值为0即可
//			locData.accuracy = location.getRadius();
//			locData.direction = location.getDerect();
//			localGeoPoint = new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6));
//			if (localGeoPoint != null && isFirstSearch) {
//				foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//				isFirstSearch = false;
//				// if(btn_food.isChecked()){
//				// foodSearch.poiSearchNearBy("餐厅", localGeoPoint, 5000);
//				// }else if(btn_lodging.isChecked()){
//				// lodgingSearch.poiSearchNearBy("酒店", localGeoPoint, 5000);
//				// }
//				mLocClient.stop();
//			}
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//			if (poiLocation == null) {
//				return;
//			}
//		}
//	}
}