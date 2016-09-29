package com.gloria.hbh.main;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.gloria.hbh.map.DrivingRouteOverlay;
import com.gloria.hbh.map.TransitRouteOverlay;
import com.gloria.hbh.map.WalkingRouteOverlay;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_RoutePlan extends Activity_Base implements OnGetRoutePlanResultListener {
	private MapView mMapView;
	Button titlebar_drive, titlebar_bus, titlebar_walk;
	
    private BaiduMap mBaiduMap = null;
    //默认城市经纬度
    private static final LatLng GEO_DEFAULT_CITY = new LatLng(31.79, 119.95);
    //定位坐标点
    private Double locLatitude = null;
    private Double locLongitude = null;
    // 定位相关
    private LocationClient mLocClient;
    private boolean isFirstLoc = true; // 是否首次定位
    private MyLocationListener locListener = new MyLocationListener();
    private String qLocation = "";//检索中心点
    //定位频率
    private static final Integer LOC_TIME = 1000;//毫秒
    private Marker locMarker;
    //路径规划相关
    private RoutePlanSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用
    private RouteLine routeLine = null;
    private WalkingRouteOverlay walkingRouteOverlay = null;
    private DrivingRouteOverlay drivingRouteOverlay = null;
    private TransitRouteOverlay transitRouteOverlay=null;

	// 浏览路线节点相关
	int nodeIndex = -2;// 节点索引,供浏览节点时使用
	boolean useDefaultIcon = false;
	int searchType = -1;// 记录搜索的类型，区分驾车/步行和公交

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routeplan);

		setView();
		setListener();
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText("导航");
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_back = (Button) findViewById(R.id.titlebar_back);
		titlebar_drive = (Button) findViewById(R.id.titlebar_drive);
		titlebar_bus = (Button) findViewById(R.id.titlebar_bus);
		titlebar_walk = (Button) findViewById(R.id.titlebar_walk);
		titlebar_back.setVisibility(View.VISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		initMap();
	}

	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_drive.setOnClickListener(onClickListener);
		titlebar_bus.setOnClickListener(onClickListener);
		titlebar_walk.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			   if (null != walkingRouteOverlay) {
		            walkingRouteOverlay.removeFromMap();
		            walkingRouteOverlay = null;
		        }
			   if (null != drivingRouteOverlay) {
				   drivingRouteOverlay.removeFromMap();
				   drivingRouteOverlay = null;
		        }
			   if (null != transitRouteOverlay) {
				   transitRouteOverlay.removeFromMap();
				   transitRouteOverlay = null;
		        }
		        //点击检索点显示信息
		        LatLng stLocation = new LatLng(locLatitude, locLongitude);//定位坐标点
		        LatLng endLocation = new LatLng(31.5103478579,120.0590861451);//目的坐标点
		        // 设置起终点信息
		        PlanNode stNode = PlanNode.withLocation(stLocation);
		        PlanNode endNode = PlanNode.withLocation(endLocation);
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			case R.id.titlebar_drive:
				 mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(endNode));
				break;
			case R.id.titlebar_bus:
				 mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode).city("常州").to(endNode));
				break;
			case R.id.titlebar_walk:
			        //步行路线规划
			        mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(endNode));
				break;
			default:
				break;
			}
		}
	};

	private void initMap() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.showScaleControl(true);
		mMapView.showZoomControls(false);
	    mBaiduMap = mMapView.getMap();
	    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(15));
	    //设置默认显示城市
        MapStatusUpdate msu_cz = MapStatusUpdateFactory.newLatLng(GEO_DEFAULT_CITY);
        mBaiduMap.setMapStatus(msu_cz);

		// 定位初始化
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(locListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); //返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(LOC_TIME);//设置发起定位请求的间隔时间
        mLocClient.setLocOption(option);
        mLocClient.start();
        
        // 初始化路径规划模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
	}

    //定位监听器
    private class MyLocationListener implements BDLocationListener {
        // map view 销毁后不在处理新接收的位置
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (null == bdLocation || null == mMapView) {
                return;
            }
            if (null != locMarker) {
                locMarker.remove();
                locMarker = null;
            }
            MarkerOptions locMO = new MarkerOptions().position(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));//动画速度
            locMarker = (Marker) (mBaiduMap.addOverlay(locMO));//定位图标

            //获取当前位置坐标
            locLatitude = bdLocation.getLatitude();
            locLongitude = bdLocation.getLongitude();
            qLocation = locLongitude + "," + locLatitude;

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		  // 退出时销毁定位
        mLocClient.stop();
        //关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
		super.onDestroy();
	}

	@Override
	public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
		
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
		   if (null == drivingRouteResult || SearchResult.ERRORNO.NO_ERROR != drivingRouteResult.error) {
	            Toast.makeText(Activity_RoutePlan.this, "未找到规划路线!", Toast.LENGTH_SHORT).show();
	        }
	        if (SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR == drivingRouteResult.error) {
	            return;
	        }
	        if (SearchResult.ERRORNO.NO_ERROR == drivingRouteResult.error) {
	            routeLine = drivingRouteResult.getRouteLines().get(0);
	            drivingRouteOverlay = new MyDrivingingRouteOverlay(mBaiduMap);
	            drivingRouteOverlay.setData((DrivingRouteLine) routeLine);
	            drivingRouteOverlay.addToMap();
	            drivingRouteOverlay.zoomToSpan();
	        }
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
		   if (null == transitRouteResult || SearchResult.ERRORNO.NO_ERROR != transitRouteResult.error) {
	            Toast.makeText(Activity_RoutePlan.this, "未找到规划路线!", Toast.LENGTH_SHORT).show();
	        }
	        if (SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR == transitRouteResult.error) {
	            return;
	        }
	        if (SearchResult.ERRORNO.NO_ERROR == transitRouteResult.error) {
	            routeLine = transitRouteResult.getRouteLines().get(0);
	            transitRouteOverlay = new MyTransitRouteOverlay(mBaiduMap);
	            transitRouteOverlay.setData((TransitRouteLine) routeLine);
	            transitRouteOverlay.addToMap();
	            transitRouteOverlay.zoomToSpan();
	        }
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
		   if (null == walkingRouteResult || SearchResult.ERRORNO.NO_ERROR != walkingRouteResult.error) {
	            Toast.makeText(Activity_RoutePlan.this, "未找到规划路线!", Toast.LENGTH_SHORT).show();
	        }
	        if (SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR == walkingRouteResult.error) {
	            return;
	        }
	        if (SearchResult.ERRORNO.NO_ERROR == walkingRouteResult.error) {
	            routeLine = walkingRouteResult.getRouteLines().get(0);
	            walkingRouteOverlay = new MyWalkingRouteOverlay(mBaiduMap);
	            walkingRouteOverlay.setData((WalkingRouteLine) routeLine);
	            walkingRouteOverlay.addToMap();
	            walkingRouteOverlay.zoomToSpan();
	        }
	}

    // 定制RouteOverly
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }
    
    // 定制RouteOverly
    private class MyDrivingingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }
    
    private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }
}