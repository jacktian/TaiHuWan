package com.gloria.hbh.main;

import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.Symbol;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.gloria.hbh.data.app.SubTabInfo.SubTabInfoTypeConstants;
import com.gloria.hbh.util.BMapUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 内容详细页面
 */
public class Activity_FoodLodgingDetail extends Activity_Base {

	// 地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	MyLocationMapView mMapView = null; // 地图View
	private MapController mMapController = null;

	// 定位图层
	locationOverlay myLocationOverlay = null;
	// 弹出泡泡图层
	private PopupOverlay pop = null;// 弹出泡泡图层，浏览节点时使用
	private TextView popupText = null;// 泡泡view
	private View viewCache = null;

	TextView text_title;
	TextView text_adr;
	TextView text_phonenum;
	TextView text_distance;

	String title = "";
	String adr = "";
	String phonenum = "";
	String distance = "";
	String latitude = "";
	String longitude = "";
	GeoPoint geoPoint;
	int type = SubTabInfoTypeConstants.SUBCATE_COMMON;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foodlodgingdetail);

		initData();
		setView();
		initMap();
		setListener();
	}

	private void initData() {
		title = getIntent().getStringExtra("title");
		adr = getIntent().getStringExtra("adr");
		phonenum = getIntent().getStringExtra("phonenum");
		distance = getIntent().getStringExtra("distance");
		latitude = getIntent().getStringExtra("latitude");
		longitude = getIntent().getStringExtra("longitude");
		geoPoint = new GeoPoint(Integer.valueOf(latitude), Integer.valueOf(longitude));
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText(title);
		titlebar_back = (Button) findViewById(R.id.titlebar_back);
		titlebar_menu = (Button) findViewById(R.id.titlebar_menu);
		titlebar_back.setVisibility(View.VISIBLE);
		titlebar_menu.setVisibility(View.INVISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_menu.setBackgroundResource(R.drawable.titlebtn_bg_share);

		text_title = (TextView) findViewById(R.id.title);
		text_title.setText(title);
		text_adr = (TextView) findViewById(R.id.adr);
		text_adr.setText(adr);
		text_phonenum = (TextView) findViewById(R.id.phonenum);
		text_phonenum.setText(phonenum);
		text_distance = (TextView) findViewById(R.id.distance);
		text_distance.setText(distance);
	}

	private void initMap() {
		// 地图初始化
		mMapView = (MyLocationMapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(20);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
		// 创建 弹出泡泡图层
		createPaopao();

		// GraphicsOverlay graphicsOverlay = new GraphicsOverlay(mMapView);
		// mMapView.getOverlays().add(graphicsOverlay);
		// //添加点
		// graphicsOverlay.setData(drawPoint(Integer.valueOf(latitude),Integer.valueOf(longitude)));
		//
		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		LocationData locData = new LocationData();
		locData.latitude = (double) geoPoint.getLatitudeE6() / 1E6;
		locData.longitude = (double) geoPoint.getLongitudeE6() / 1E6;
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 执行地图刷新使生效
		mMapController.animateTo(geoPoint);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setText("  " + title + "  ");
		pop.showPopup(BMapUtil.getBitmapFromView(popupText), geoPoint, 8);
		mMapView.refresh();
	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// 处理点击事件,弹出泡泡
			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText(title);
			pop.showPopup(BMapUtil.getBitmapFromView(popupText), geoPoint, 8);
			return true;
		}

	}

	/**
	 * 绘制单点，该点状态不随地图状态变化而变化
	 * 
	 * @return 点对象
	 */
	public Graphic drawPoint(int lat, int lon) {
		// double mLat = 39.98923;
		// double mLon = 116.397428;
		// int lat = (int) (mLat*1E6);
		// int lon = (int) (mLon*1E6);
		GeoPoint pt1 = new GeoPoint(lat, lon);

		// 构建点
		Geometry pointGeometry = new Geometry();
		// 设置坐标
		pointGeometry.setPoint(pt1, 10);
		// 设定样式
		Symbol pointSymbol = new Symbol();
		Symbol.Color pointColor = pointSymbol.new Color();
		pointColor.red = 0;
		pointColor.green = 126;
		pointColor.blue = 255;
		pointColor.alpha = 255;
		pointSymbol.setPointSymbol(pointColor);
		// 生成Graphic对象
		Graphic pointGraphic = new Graphic(pointGeometry, pointSymbol);
		return pointGraphic;
	}

	/**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao() {
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		// 泡泡点击响应回调
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
		};
		pop = new PopupOverlay(mMapView, popListener);
		MyLocationMapView.pop = pop;
	}

	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_menu.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			case R.id.titlebar_menu:
				// goToShare();
				break;
			default:
				break;
			}
		}
	};

}
