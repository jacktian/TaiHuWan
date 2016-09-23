package com.gloria.slidingmenu.lib.fragment;

import java.util.ArrayList;
import java.util.List;

import com.gloria.hbh.data.app.LeftSideData;
import com.gloria.hbh.data.forum.ExhibitionInfo;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.map.MapObjectContainer;
import com.gloria.hbh.map.MapObjectModel;
import com.gloria.hbh.map.TextPopup;
import com.gloria.hbh.staellitemenu.view.SatelliteMenu;
import com.gloria.hbh.staellitemenu.view.SatelliteMenu.SateliteClickedListener;
import com.gloria.hbh.staellitemenu.view.SatelliteMenuItem;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.config.MapGraphicsConfig;
import com.ls.widgets.map.config.OfflineMapConfig;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.events.ObjectTouchEvent;
import com.ls.widgets.map.interfaces.Layer;
import com.ls.widgets.map.interfaces.MapEventsListener;
import com.ls.widgets.map.interfaces.OnMapScrollListener;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapObject;
import com.ls.widgets.map.utils.PivotFactory;
import com.ls.widgets.map.utils.PivotFactory.PivotPosition;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 * 地图导览页面
 */
@SuppressLint("ValidFragment")
public class MapFragment extends BaseFragment implements MapEventsListener, OnMapTouchListener {
	String text = "";
	Activity_Main mMain = null;
	// private FrameLayout mFrameLayout = null;
	Button titlebar_menu;
	MapObjectModel objectModel;
	private static final String TAG = "BrowseMapActivity";
	private static final Integer LAYER_BASIC_ID = -1; // 基本
	private static final Integer LAYER_CLINIC_ID = 0; // 诊所
	private static final Integer LAYER_PACK_ID = 1; // 停车场
	private static final Integer LAYER_TOILET_ID = 2; // 厕所
	private static final Integer LAYER_STORE_ID = 3; // 商店
	private static final Integer LAYER_FOOD_ID = 4; // 餐饮
	private static final Integer LAYER_TICKET_ID = 5; // 票务
	Layer layer_BASIC;
	Layer layer_CLINIC;
	Layer layer_PACK;
	// Layer layer_TOILET;
	Layer layer_STORE;
	// Layer layer_FOOD;
	Layer layer_TICKET;
	// private static final Integer LAYER2_ID = 1;
	private static final int MAP_ID = 23;
	private int nextObjectId;
	private int pinHeight;
	private MapObjectContainer model;
	private MapWidget map;
	private TextPopup mapObjectInfoPopup;
	SatelliteMenuItem item_clinic, item_pack, item_store, item_ticket;
	private int currentPoint;
	static RelativeLayout layout;
	// LocationClient mLocationClient = null;
	// BDLocationListener myListener = null;
	SatelliteMenu menu;
	SatelliteMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6;
	int distance_point = 35;

	public MapFragment() {
	}

	public MapFragment(String text) {
		this.text = text;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mMain = (Activity_Main) getActivity();
		// mFrameLayout = (FrameLayout) mMain.findViewById(R.id.content_main);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_map, null);
		setView(view);
		setMapView(view, savedInstanceState);
		setListener();
		mapMoveToScreenXY(0, text, LeftSideData.getInstance().getList().get(0).getSublist().get(0).getPoint());
		return view;
	}

	private void setView(View view) {
		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
		titlebar_left = (Button) view.findViewById(R.id.titlebar_left);
		titlebar_menu = (Button) view.findViewById(R.id.titlebar_menu);
		titlebar_menu.setVisibility(View.VISIBLE);
		// titlebar_left.setVisibility(View.VISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_name.setText(text);
		titlebar_name.setTextColor(Color.BLACK);
		// titlebar_left.setBackgroundResource(R.drawable.titlebtn_bg_location);
		menu = (SatelliteMenu) view.findViewById(R.id.menu);
		menu.setSatelliteDistance(320);
		// menu.setExpandDuration(500);
		menu.setTotalSpacingDegree(90);
		initMenuItem();
		menu.setOnItemClickedListener(new SateliteClickedListener() {
			public void eventOccured(int id) {
				Log.i("sat", "Clicked on " + id);
				// ForumToast.show(String.valueOf(id));
				layer_CLINIC.setVisible(false);
				// layer_FOOD.setVisible(false);
				layer_PACK.setVisible(false);
				layer_STORE.setVisible(false);
				layer_TICKET.setVisible(false);
				// layer_BASIC.setVisible(false);
				switch (id) {
				case 1:
					layer_CLINIC.setVisible(true);
					break;
				// case 2:
				// layer_FOOD.setVisible(true);
				// break;
				case 3:
					layer_PACK.setVisible(true);
					break;
				case 4:
					layer_STORE.setVisible(true);
					break;
				case 5:
					layer_TICKET.setVisible(true);
					break;
				// case 6:
				// layer_TOILET.setVisible(true);
				// break;
				default:
					break;
				}
				map.invalidate();
			}
		});
	}

	private void initMenuItem() {
		List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
		item_clinic = new SatelliteMenuItem(1, R.drawable.ic_clinic);
		// item_food = new SatelliteMenuItem(2, R.drawable.ic_food);
		item_pack = new SatelliteMenuItem(3, R.drawable.ic_ticket);
		item_store = new SatelliteMenuItem(4, R.drawable.ic_store);
		item_ticket = new SatelliteMenuItem(5, R.drawable.ic_pack);
		// item_toilet = new SatelliteMenuItem(6, R.drawable.ic_toilet);
		items.add(item_clinic);
		items.add(item_pack);
		// items.add(item_toilet);
		items.add(item_store);
		// items.add(item_food);
		items.add(item_ticket);
		menu.addItems(items);
	}

	private void setMapView(View view, Bundle savedInstanceState) {
		layout = (RelativeLayout) view.findViewById(R.id.rootLayout);
		nextObjectId = 0;
		model = new MapObjectContainer();
		initMap(savedInstanceState);
		initBasicLocationPoints();
		initMapObjects();
		initMapListeners();
		map.centerMap();
	}

	private void setListener() {
		titlebar_menu.setOnClickListener(onClickListener);
		// titlebar_left.setOnClickListener(onClickListener);
		titlebar_name.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_menu:
				if (mMain.getSlidingMenu().isSecondaryMenuShowing()) {
					mMain.getSlidingMenu().toggle();
				} else {
					mMain.getSlidingMenu().showSecondaryMenu();
				}
				break;
			// case R.id.titlebar_left:
			// mMain.getSlidingMenu().toggle();
			// break;
			default:
				break;
			}
		}
	};

	public void onResume() {
		super.onResume();
		currentPoint = 0;
		if (map != null && model != null) {
			Location location = model.getObjectById(currentPoint).getLocation();
			if (location != null) {
				map.scrollMapTo(location);
			}
		}
	}

	public void mapMoveToScreenXY(int id, String text, String str_location) {
		int x = Integer.valueOf(str_location.substring(0, str_location.indexOf(",")).trim());
		int y = Integer.valueOf(str_location.substring(str_location.indexOf(",") + 1).trim()) - distance_point;
		map.scrollMapTo(new Point(x, y));
		// map.setScale(9f);
		showLocationsPopup(id, xToScreenCoords(x), yToScreenCoords(y), text);
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		map.saveState(outState);
	}

	private void initBasicLocationPoints() {
		// layer_BASIC = map.getLayerById(LAYER_BASIC_ID);
		// MapObjectModel objectModel ;
		// int count = 0;
		// for (int i=0; i<LeftSideData.getInstance().getList().size(); i++) {
		// ExhibitionInfo exhibitionInfo
		// =LeftSideData.getInstance().getList().get(i);
		// for (int j=0; j< exhibitionInfo.getSublist().size(); j++) {
		// ExhibitionInfo subexhibitionInfo =
		// exhibitionInfo.getSublist().get(j);
		// if(subexhibitionInfo.getPoint() != null &&
		// !subexhibitionInfo.getPoint().equals("")){
		// String[] location = subexhibitionInfo.getPoint().split(",");
		// int id = subexhibitionInfo.getId();
		// objectModel = new MapObjectModel(id,
		// Integer.valueOf(location[0].trim()),
		// Integer.valueOf(location[1].trim())-distance_point,
		// subexhibitionInfo.getTitle());
		// addNotScalableMapObject(objectModel,
		// layer_BASIC,R.drawable.pinviewspot);
		// model.addObject(objectModel);
		// count ++;
		// }
		// }
		// }
		// currentPoint = 0;
		// layer_BASIC.setVisible(true);
	}

	// private Location getNextLocationPoint()
	// {
	// if (currentPoint < points.length-1) {
	// currentPoint += 1;
	// } else {
	// currentPoint = 0;
	// }
	//
	// return points[currentPoint];
	// }
	private void initMap(Bundle savedInstanceState) {
		// In order to display the map on the screen you will need
		// to initialize widget and place it into layout.
		map = new MapWidget(savedInstanceState, mMain, "map", // root name of
																// the
																// map under
																// assets
																// folder.
				12); // initial zoom level
		map.setMaxZoomLevel(13);
		// map.setMinimumWidth(2500);
		// map.setMinimumWidth(1125);
		map.setMinZoomLevel(11);
		map.setScale(1.0f);
		map.setId(MAP_ID);
		OfflineMapConfig config = map.getConfig();
		config.setZoomBtnsVisible(false); // Sets embedded zoom buttons visible
		config.setPinchZoomEnabled(true); // Sets pinch gesture to zoom
		config.setFlingEnabled(true); // Sets inertial scrolling of the map
		config.setMapCenteringEnabled(true);
		// // Configuration of GPS receiver
		// GPSConfig gpsConfig = config.getGpsConfig();
		// gpsConfig.setPassiveMode(false);
		// gpsConfig.setGPSUpdateInterval(500, 5);
		// Configuration of position marker
		MapGraphicsConfig graphicsConfig = config.getGraphicsConfig();
		graphicsConfig.setAccuracyAreaColor(0x550000FF); // Blue with
															// transparency
		graphicsConfig.setAccuracyAreaBorderColor(Color.TRANSPARENT); // Blue
																		// without
																		// transparency
		// Adding the map to the layout
		layout.addView(map, 0);
		layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
		map.createLayer(LAYER_BASIC_ID);
		map.createLayer(LAYER_CLINIC_ID);
		map.createLayer(LAYER_FOOD_ID);
		map.createLayer(LAYER_PACK_ID);
		map.createLayer(LAYER_STORE_ID);
		map.createLayer(LAYER_TICKET_ID);
		map.createLayer(LAYER_TOILET_ID);
	}

	private void initMapObjects() {
		mapObjectInfoPopup = new TextPopup(mMain, layout);
		layer_CLINIC = map.getLayerById(LAYER_CLINIC_ID);
		layer_PACK = map.getLayerById(LAYER_PACK_ID);
		layer_STORE = map.getLayerById(LAYER_STORE_ID);
		layer_TICKET = map.getLayerById(LAYER_TICKET_ID);
		for (int i = 0; i < LeftSideData.getInstance().getList().size(); i++) {
			ExhibitionInfo exhibitionInfo = LeftSideData.getInstance().getList().get(i);
			for (int j = 0; j < exhibitionInfo.getSublist().size(); j++) {
				ExhibitionInfo subexhibitionInfo = exhibitionInfo.getSublist().get(j);
				if (subexhibitionInfo.getPoint() != null && !subexhibitionInfo.getPoint().equals("")
						&& subexhibitionInfo.getLevel() == 2) {
					String[] location = subexhibitionInfo.getPoint().split(",");
					int id = subexhibitionInfo.getId();
					objectModel = new MapObjectModel(id, Integer.valueOf(location[0].trim()),
							Integer.valueOf(location[1].trim()) - distance_point, subexhibitionInfo.getTitle());
					addNotScalableMapObject(objectModel, layer_CLINIC, R.drawable.loation_icon);
					model.addObject(objectModel);
					layer_CLINIC.setVisible(false);
				}
				if (subexhibitionInfo.getPoint() != null && !subexhibitionInfo.getPoint().equals("")
						&& subexhibitionInfo.getLevel() == 3) {
					String[] location = subexhibitionInfo.getPoint().split(",");
					int id = subexhibitionInfo.getId();
					objectModel = new MapObjectModel(id, Integer.valueOf(location[0].trim()),
							Integer.valueOf(location[1].trim()) - distance_point, subexhibitionInfo.getTitle());
					addNotScalableMapObject(objectModel, layer_PACK, R.drawable.loation_icon);
					model.addObject(objectModel);
					layer_PACK.setVisible(false);
				}
				if (subexhibitionInfo.getPoint() != null && !subexhibitionInfo.getPoint().equals("")
						&& subexhibitionInfo.getLevel() == 4) {
					String[] location = subexhibitionInfo.getPoint().split(",");
					int id = subexhibitionInfo.getId();
					objectModel = new MapObjectModel(id, Integer.valueOf(location[0].trim()),
							Integer.valueOf(location[1].trim()) - distance_point, subexhibitionInfo.getTitle());
					addNotScalableMapObject(objectModel, layer_STORE, R.drawable.loation_icon);
					model.addObject(objectModel);
					layer_STORE.setVisible(false);
				}
				if (subexhibitionInfo.getPoint() != null && !subexhibitionInfo.getPoint().equals("")
						&& subexhibitionInfo.getLevel() == 1) {
					String[] location = subexhibitionInfo.getPoint().split(",");
					int id = subexhibitionInfo.getId();
					objectModel = new MapObjectModel(id, Integer.valueOf(location[0].trim()),
							Integer.valueOf(location[1].trim()) - distance_point, subexhibitionInfo.getTitle());
					addNotScalableMapObject(objectModel, layer_TICKET, R.drawable.loation_icon);
					model.addObject(objectModel);
					layer_TICKET.setVisible(false);
				}
			}
		}
		//
		//
		//
		// layer_PACK = map.getLayerById(LAYER_PACK_ID);
		// for (int i=0; i<InfrastructureInfo.getInstance().getPark().size();
		// i++) {
		// String[] location =
		// InfrastructureInfo.getInstance().getPark().get(i).split(",");
		// int id = Integer.valueOf(2000+String.valueOf(i));
		// objectModel = new MapObjectModel(id,
		// Integer.valueOf(location[0].trim()),
		// Integer.valueOf(location[1].trim())-distance_point, "停车场");
		// addNotScalableMapObject(objectModel, layer_PACK,R.drawable.pinpark);
		// // model.addObject(objectModel);
		// }
		//
		// layer_PACK.setVisible(false);
		//
		// layer_STORE = map.getLayerById(LAYER_STORE_ID);
		// for (int i=0; i<InfrastructureInfo.getInstance().getStore().size();
		// i++) {
		// String[] location =
		// InfrastructureInfo.getInstance().getStore().get(i).split(",");
		// int id = Integer.valueOf(2000+String.valueOf(i));
		// objectModel = new MapObjectModel(id,
		// Integer.valueOf(location[0].trim()),
		// Integer.valueOf(location[1].trim())-distance_point, "商店");
		// addNotScalableMapObject(objectModel,
		// layer_STORE,R.drawable.pinstore);
		// // model.addObject(objectModel);
		// }
		//
		// layer_STORE.setVisible(false);
		//
		// layer_TICKET = map.getLayerById(LAYER_TICKET_ID);
		// for (int i=0; i<InfrastructureInfo.getInstance().getTicket().size();
		// i++) {
		// String[] location =
		// InfrastructureInfo.getInstance().getTicket().get(i).split(",");
		// int id = Integer.valueOf(2000+String.valueOf(i));
		// objectModel = new MapObjectModel(id,
		// Integer.valueOf(location[0].trim()),
		// Integer.valueOf(location[1].trim())-distance_point, "票务");
		// addNotScalableMapObject(objectModel,
		// layer_TICKET,R.drawable.pinticket);
		// // model.addObject(objectModel);
		// }
		//
		// layer_TICKET.setVisible(false);
		// layer_TOILET= map.getLayerById(LAYER_TOILET_ID);
		// for (int i=0; i<InfrastructureInfo.getInstance().getToilet().size();
		// i++) {
		// String[] location =
		// InfrastructureInfo.getInstance().getToilet().get(i).split(",");
		// int id = Integer.valueOf(2000+String.valueOf(i));
		// objectModel = new MapObjectModel(id,
		// Integer.valueOf(location[0].trim()),
		// Integer.valueOf(location[1].trim())-distance_point, "厕所");
		// addNotScalableMapObject(objectModel,
		// layer_TOILET,R.drawable.pintoilet);
		// // model.addObject(objectModel);
		// }
		//
		// layer_TOILET.setVisible(false);
	}

	private void addNotScalableMapObject(int x, int y, Layer layer, int res) {
		// Getting the drawable of the map object
		Drawable drawable = getResources().getDrawable(res);
		pinHeight = drawable.getIntrinsicHeight();
		// Creating the map object
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), // id,
																			// will
																			// be
																			// passed
																			// to
																			// the
																			// listener
																			// when
																			// user
																			// clicks
																			// on
																			// it
				drawable, new Point(x, y), // coordinates in original map
											// coordinate
											// system.
				// Pivot point of center of the drawable in the drawable's
				// coordinate
				// system.
				PivotFactory.createPivotPoint(drawable, PivotPosition.PIVOT_CENTER), true, // This
																							// object
																							// will
																							// be
																							// passed
																							// to
																							// the
																							// listener
				false); // is not scalable. It will have the same size on each
						// zoom
						// level
		// Adding object to layer
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addNotScalableMapObject(MapObjectModel objectModel, Layer layer, int res) {
		if (objectModel.getLocation() != null) {
			addNotScalableMapObject(objectModel.getLocation(), layer, res);
		} else {
			addNotScalableMapObject(objectModel.getX(), objectModel.getY(), layer, res);
		}
	}

	private void addNotScalableMapObject(Location location, Layer layer, int res) {
		if (location == null)
			return;
		// Getting the drawable of the map object
		Drawable drawable = getResources().getDrawable(res);
		// Creating the map object
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), // id,
																			// will
																			// be
																			// passed
																			// to
																			// the
																			// listener
																			// when
																			// user
																			// clicks
																			// on
																			// it
				drawable, new Point(0, 0), // coordinates in original map
											// coordinate
											// system.
				// Pivot point of center of the drawable in the drawable's
				// coordinate
				// system.
				PivotFactory.createPivotPoint(drawable, PivotPosition.PIVOT_CENTER), true, // This
																							// object
																							// will
																							// be
																							// passed
																							// to
																							// the
																							// listener
				true); // is not scalable. It will have the same size on each
						// zoom level
		layer.addMapObject(object1);
		// Will crash if you try to move before adding to the layer.
		object1.moveTo(location);
		nextObjectId += 1;
	}

	private void addScalableMapObject(int x, int y, Layer layer, int res) {
		Drawable drawable = getResources().getDrawable(res);
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void initMapListeners() {
		// In order to receive MapObject touch events we need to set listener
		map.setOnMapTouchListener(this);
		// In order to receive pre and post zoom events we need to set
		// MapEventsListener
		map.addMapEventsListener(this);
		// In order to receive map scroll events we set OnMapScrollListener
		map.setOnMapScrolledListener(new OnMapScrollListener() {
			public void onScrolledEvent(MapWidget v, MapScrolledEvent event) {
				handleOnMapScroll(v, event);
			}
		});
	}

	private void handleOnMapScroll(MapWidget v, MapScrolledEvent event) {
		// When user scrolls the map we receive scroll events
		// This is useful when need to move some object together with the map
		int dx = event.getDX(); // Number of pixels that user has scrolled
								// horizontally
		int dy = event.getDY(); // Number of pixels that user has scrolled
								// vertically
		if (mapObjectInfoPopup.isVisible()) {
			mapObjectInfoPopup.moveBy(dx, dy);
		}
	}

	@Override
	public void onPostZoomIn() {
		Log.i(TAG, "onPostZoomIn()" + map.getScale());
	}

	@Override
	public void onPostZoomOut() {
		Log.i(TAG, "onPostZoomOut()" + map.getScale());
	}

	@Override
	public void onPreZoomIn() {
		Log.i(TAG, "onPreZoomIn()" + map.getScale());
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
	}

	@Override
	public void onPreZoomOut() {
		Log.i(TAG, "onPreZoomOut()" + map.getScale());
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
	}

	// * On map touch listener implemetnation *//
	public void onTouch(MapWidget v, MapTouchedEvent event) {
		// Get touched object events from the MapTouchEvent
		ArrayList<ObjectTouchEvent> touchedObjs = event.getTouchedObjectIds();
		if (touchedObjs.size() > 0) {
			int xInMapCoords = event.getMapX();
			int yInMapCoords = event.getMapY();
			int xInScreenCoords = event.getScreenX();
			int yInScreenCoords = event.getScreenY();
			ObjectTouchEvent objectTouchEvent = event.getTouchedObjectIds().get(0);
			// Due to a bug this is not actually the layer id, but index of the
			// layer in layers array.
			// Will be fixed in the next release.
			long layerId = objectTouchEvent.getLayerId();
			Integer objectId = (Integer) objectTouchEvent.getObjectId();
			// User has touched one or more map object
			// We will take the first one to show in the toast message.
			String message = "You touched the object with id: " + objectId + " on layer: " + layerId + " mapX: "
					+ xInMapCoords + " mapY: " + yInMapCoords + " screenX: " + xInScreenCoords + " screenY: "
					+ yInScreenCoords;
			Log.d(TAG, message);
			MapObjectModel objectModel = model.getObjectById(objectId.intValue());
			if (objectModel != null) {
				// This is a case when we want to show popup info exactly above
				// the pin image
				// float density = getResources().getDisplayMetrics().density;
				// int imgHeight = (int) (pinHeight / density / 2);
				// Calculating position of popup on the screen
				// int x = xToScreenCoords(objectModel.getX());
				// int y = yToScreenCoords(objectModel.getY()) - imgHeight;
				// Show it
				// showLocationsPopup(x, y, objectModel.getCaption());
				showLocationsPopup(objectId, xInScreenCoords, yInScreenCoords, objectModel.getCaption());
			}
			// else {
			// // This is a case when we want to show popup where the user has
			// touched.
			// showLocationsPopup(objectId, xInScreenCoords, yInScreenCoords,
			// "Shows where user touched");
			// }
			// Hint: If user touched more than one object you can show the
			// dialog in which ask
			// the user to select concrete object
		} else {
			if (mapObjectInfoPopup != null) {
				mapObjectInfoPopup.hide();
			}
		}
	}

	private void showLocationsPopup(final int objectId, int x, int y, String text) {
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
		// ((TextPopup) mapObjectInfoPopup).setIcon((BitmapDrawable)
		// mMain.getResources().getDrawable(R.drawable.map_popup_arrow));
		((TextPopup) mapObjectInfoPopup).setText(text);
		// mapObjectInfoPopup.setOnClickListener(new OnTouchListener()
		// {
		// public boolean onTouch(View v, MotionEvent event)
		// {
		// if (event.getAction() == MotionEvent.ACTION_UP)
		// {
		// if (mapObjectInfoPopup != null)
		// {
		// mapObjectInfoPopup.hide();
		// //位置点的点击事件
		// //TODO
		// ExhibitionInfo exhibitionInfo =
		// LeftSideData.getInstance().getExhibitionInfoById(objectId);
		// if(exhibitionInfo != null){
		// Intent intent = new Intent();
		// intent.setClass(mMain, Activity_ExhibitionInfo.class);
		// intent.putExtra("name", exhibitionInfo.getTitle());
		// intent.putExtra("detail", exhibitionInfo.getDetail());
		// intent.putStringArrayListExtra("images", exhibitionInfo.getImages());
		// startActivity(intent);
		// }
		// }
		// }
		// return false;
		// }
		// });
		((TextPopup) mapObjectInfoPopup).show(layout, x, y);
	}

	/***
	 * Transforms coordinate in map coordinate system to screen coordinate
	 * system
	 * 
	 * @param mapCoord
	 *            - X in map coordinate in pixels.
	 * @return X coordinate in screen coordinates. You can use this value to
	 *         display any object on the screen.
	 */
	private int xToScreenCoords(int mapCoord) {
		return (int) (mapCoord * map.getScale() - map.getScrollX());
	}

	private int yToScreenCoords(int mapCoord) {
		return (int) (mapCoord * map.getScale() - map.getScrollY());
	}
}
