package com.gloria.hbh.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.util.BMapUtil;

public class Activity_RoutePlan extends Activity_Base {

	// ��λ���
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	
	//��λͼ��
	locationOverlay myLocationOverlay = null;
	//��������ͼ��
	private PopupOverlay   pop  = null;//��������ͼ�㣬����ڵ�ʱʹ��
	private TextView  popupText = null;//����view
	private View viewCache = null;
	
	//��ͼ��أ�ʹ�ü̳�MapView��MyLocationMapViewĿ������дtouch�¼�ʵ�����ݴ���
	//���������touch�¼���������̳У�ֱ��ʹ��MapView����
	MyLocationMapView mMapView = null;	// ��ͼView
	private MapController mMapController = null;

	boolean isRequest = false;//�Ƿ��ֶ���������λ
	boolean isFirstLoc = true;//�Ƿ��״ζ�λ
	
	Button titlebar_drive = null,titlebar_bus = null,titlebar_walk = null;
	//���·�߽ڵ����
	int nodeIndex = -2;//�ڵ�����,������ڵ�ʱʹ��
	MKRoute route = null;//����ݳ�/����·�����ݵı�����������ڵ�ʱʹ��
	TransitOverlay transitOverlay = null;//���湫��·��ͼ�����ݵı�����������ڵ�ʱʹ��
	RouteOverlay routeOverlay = null; 
	boolean useDefaultIcon = false;
	int searchType = -1;//��¼���������ͣ����ּݳ�/���к͹���	
	//�������
	MKSearch mSearch = null;	// ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeplan);
       
        setView();
        setListener();
    }
    
	private void setView() {
    	titlebar = (LinearLayout)findViewById(R.id.titlebar);
    	titlebar_name = (TextView)findViewById(R.id.titlebar_name);
		titlebar_name.setText("����");
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_back = (Button)findViewById(R.id.titlebar_back);
		titlebar_drive = (Button)findViewById(R.id.titlebar_drive);
		titlebar_bus = (Button)findViewById(R.id.titlebar_bus);
		titlebar_walk = (Button)findViewById(R.id.titlebar_walk);
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
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			case R.id.titlebar_drive:
				SearchButtonProcess(titlebar_drive);
				break;
			case R.id.titlebar_bus:
				SearchButtonProcess(titlebar_bus);
				break;
			case R.id.titlebar_walk:
				SearchButtonProcess(titlebar_walk);
				break;
			default:
				break;
			}
		}
	};
	
	private void initMap() {
		//��ͼ��ʼ��
        mMapView = (MyLocationMapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
      //���� ��������ͼ��
        createPaopao();
        
        //��λ��ʼ��
        mLocClient = new LocationClient(this);
        mLocClient.setAK(BaseApplication.strKey);
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//��gps
        option.setCoorType("bd09ll");     //������������
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //��λͼ���ʼ��
		myLocationOverlay = new locationOverlay(mMapView);
		//���ö�λ����
	    myLocationOverlay.setData(locData);
	    //��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//�޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
		
		// ��ʼ������ģ�飬ע���¼�����
        mSearch = new MKSearch();
        mSearch.init(BaseApplication.getInstance().mBMapManager, new MKSearchListener(){

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					mMapView.getOverlays().clear();
					ForumToast.show("��Ǹ��δ�ҵ����");
					return;
				}
			
				searchType = 0;
			    routeOverlay = new RouteOverlay(Activity_RoutePlan.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    //�������ͼ��
			    mMapView.getOverlays().clear();
			    //���·��ͼ��
			    mMapView.getOverlays().add(routeOverlay);
			    //ִ��ˢ��ʹ��Ч
			    mMapView.refresh();
			    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			    //�ƶ���ͼ�����
			    mMapView.getController().animateTo(res.getStart().pt);
			    //��·�����ݱ����ȫ�ֱ���
			    route = res.getPlan(0).getRoute(0);
			    //����·�߽ڵ��������ڵ����ʱʹ��
			    nodeIndex = -1;
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					mMapView.getOverlays().clear();
					ForumToast.show("��Ǹ��δ�ҵ����");
					return;
				}
				
				searchType = 1;
				transitOverlay = new TransitOverlay (Activity_RoutePlan.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
			    transitOverlay.setData(res.getPlan(0));
			  //�������ͼ��
			    mMapView.getOverlays().clear();
			  //���·��ͼ��
			    mMapView.getOverlays().add(transitOverlay);
			  //ִ��ˢ��ʹ��Ч
			    mMapView.refresh();
			    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			    mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());
			  //�ƶ���ͼ�����
			    mMapView.getController().animateTo(res.getStart().pt);
			  //����·�߽ڵ��������ڵ����ʱʹ��
			    nodeIndex = 0;
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				//�����յ������壬��Ҫѡ�����ĳ����б���ַ�б�
				if (error == MKEvent.ERROR_ROUTE_ADDR){
					//�������е�ַ
//					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
//					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
//					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
//					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
					return;
				}
				if (error != 0 || res == null) {
					mMapView.getOverlays().clear();
					ForumToast.show("��Ǹ��δ�ҵ����");
					return;
				}

				searchType = 2;
				routeOverlay = new RouteOverlay(Activity_RoutePlan.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				//�������ͼ��
			    mMapView.getOverlays().clear();
			  //���·��ͼ��
			    mMapView.getOverlays().add(routeOverlay);
			  //ִ��ˢ��ʹ��Ч
			    mMapView.refresh();
			    // ʹ��zoomToSpan()���ŵ�ͼ��ʹ·������ȫ��ʾ�ڵ�ͼ��
			    mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			  //�ƶ���ͼ�����
			    mMapView.getController().animateTo(res.getStart().pt);
			    //��·�����ݱ����ȫ�ֱ���
			    route = res.getPlan(0).getRoute(0);
			    //����·�߽ڵ��������ڵ����ʱʹ��
			    nodeIndex = -1;
			    
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetPoiDetailSearchResult(int type, int iError) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub
				
			}
        });
	}
	
//	/**
//	 * �ڵ����ʾ��
//	 * @param v
//	 */
//	public void nodeClick(View v){
//		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
//        popupText =(TextView) viewCache.findViewById(R.id.textcache);
//		if (searchType == 0 || searchType == 2){
//			//�ݳ�������ʹ�õ����ݽṹ��ͬ���������Ϊ�ݳ����У��ڵ����������ͬ
//			if (nodeIndex < -1 || route == null || nodeIndex >= route.getNumSteps())
//				return;
//			
//			//��һ���ڵ�
//			if (mBtnPre.equals(v) && nodeIndex > 0){
//				//������
//				nodeIndex--;
//				//�ƶ���ָ������������
//				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
//				//��������
//				popupText.setBackgroundResource(R.drawable.popup);
//				popupText.setText(route.getStep(nodeIndex).getContent());
//				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//						route.getStep(nodeIndex).getPoint(),
//						5);
//			}
//			//��һ���ڵ�
//			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps()-1)){
//				//������
//				nodeIndex++;
//				//�ƶ���ָ������������
//				mMapView.getController().animateTo(route.getStep(nodeIndex).getPoint());
//				//��������
//				popupText.setBackgroundResource(R.drawable.popup);
//				popupText.setText(route.getStep(nodeIndex).getContent());
//				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//						route.getStep(nodeIndex).getPoint(),
//						5);
//			}
//		}
//		if (searchType == 1){
//			//��������ʹ�õ����ݽṹ��������ͬ����˵�������ڵ����
//			if (nodeIndex < -1 || transitOverlay == null || nodeIndex >= transitOverlay.getAllItem().size())
//				return;
//			
//			//��һ���ڵ�
//			if (mBtnPre.equals(v) && nodeIndex > 1){
//				//������
//				nodeIndex--;
//				//�ƶ���ָ������������
//				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
//				//��������
//				popupText.setBackgroundResource(R.drawable.popup);
//				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
//				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//						transitOverlay.getItem(nodeIndex).getPoint(),
//						5);
//			}
//			//��һ���ڵ�
//			if (mBtnNext.equals(v) && nodeIndex < (transitOverlay.getAllItem().size()-2)){
//				//������
//				nodeIndex++;
//				//�ƶ���ָ������������
//				mMapView.getController().animateTo(transitOverlay.getItem(nodeIndex).getPoint());
//				//��������
//				popupText.setBackgroundResource(R.drawable.popup);
//				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
//				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
//						transitOverlay.getItem(nodeIndex).getPoint(),
//						5);
//			}
//		}
//	}

	/**
	 * ����·�߹滮����ʾ��
	 * @param v
	 */
	public void SearchButtonProcess(View v) {
		//��������ڵ��·������
		route = null;
		routeOverlay = null;
		transitOverlay = null; 
		// ����������ť��Ӧ
//		EditText editSt = (EditText)findViewById(R.id.start);
//		EditText editEn = (EditText)findViewById(R.id.end);
		
		// ������յ��name���и�ֵ��Ҳ����ֱ�Ӷ����긳ֵ����ֵ�����򽫸��������������
		MKPlanNode stNode = new MKPlanNode();
//		stNode.name = editSt.getText().toString();
		stNode.pt = new GeoPoint((int)(locData.latitude * 1e6), (int)(locData.longitude * 1e6));
		MKPlanNode enNode = new MKPlanNode();
		double latitude_d = 31.5103478579f;
		double longitude_d = 120.0590861451f;
		enNode.pt = new GeoPoint((int)(latitude_d * 1e6), (int)(longitude_d * 1e6));

		// ʵ��ʹ�����������յ���н�����ȷ���趨
		if (titlebar_drive.equals(v)) {
			mSearch.drivingSearch("�ҵ�λ��", stNode, "̫������Ϸ��", enNode);
		} else if (titlebar_bus.equals(v)) {
			mSearch.transitSearch("�ҵ�λ��", stNode, enNode);
		} else if (titlebar_walk.equals(v)) {
			mSearch.walkingSearch("�ҵ�λ��", stNode, "̫������Ϸ��", enNode);
		} 
	}
    
	/**
     * �ֶ�����һ�ζ�λ����
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        ForumToast.show("���ڶ�λ����");
    }
    /**
     * �޸�λ��ͼ��
     * @param marker
     */
    public void modifyLocationOverlayIcon(Drawable marker){
    	//������markerΪnullʱ��ʹ��Ĭ��ͼ�����
    	myLocationOverlay.setMarker(marker);
    	//�޸�ͼ�㣬��Ҫˢ��MapView��Ч
    	mMapView.refresh();
    }
    /**
	 * ������������ͼ��
	 */
	public void createPaopao(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        //���ݵ����Ӧ�ص�
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
        MyLocationMapView.pop = pop;
	}
	/**
     * ��λSDK��������
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //�������ʾ��λ����Ȧ����accuracy��ֵΪ0����
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            //���¶�λ����
            myLocationOverlay.setData(locData);
            //����ͼ������ִ��ˢ�º���Ч
            mMapView.refresh();
            //���ֶ�����������״ζ�λʱ���ƶ�����λ��
            if (isRequest || isFirstLoc){
            	//�ƶ���ͼ����λ��
            	Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;
                myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
            }
            //�״ζ�λ���
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    //�̳�MyLocationOverlay��дdispatchTapʵ�ֵ������
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  			// TODO Auto-generated constructor stub
  		}
  		@Override
  		protected boolean dispatchTap() {
  			// TODO Auto-generated method stub
  			//�������¼�,��������
  			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("�ҵ�λ��");
			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
					8);
  			return true;
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
    	//�˳�ʱ���ٶ�λ
        if (mLocClient != null)
            mLocClient.stop();
        if (mMapView != null)
            mMapView.destroy();
        
        super.onDestroy();
    }
    
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
/**
 * �̳�MapView��дonTouchEventʵ�����ݴ������
 * @author hejin
 *
 */
class MyLocationMapView extends MapView{
	static PopupOverlay   pop  = null;//��������ͼ�㣬���ͼ��ʹ��
	public MyLocationMapView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyLocationMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			//��������
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}
}