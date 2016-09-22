package com.gloria.hbh.main;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
 
 
 
 

import com.baidu.mobstat.StatService;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.app.TabInfo;
import com.gloria.slidingmenu.lib.SlidingMenu;
import com.gloria.slidingmenu.lib.app.SlidingFragmentActivity;
import com.gloria.slidingmenu.lib.fragment.ActivityFragment;
import com.gloria.slidingmenu.lib.fragment.InformationFragment;
import com.gloria.slidingmenu.lib.fragment.LeftMenu;
import com.gloria.slidingmenu.lib.fragment.MallFragment;
import com.gloria.slidingmenu.lib.fragment.MapFragment;
import com.gloria.slidingmenu.lib.fragment.RightMenu;
import com.gloria.slidingmenu.lib.fragment.TicketFragment;
import com.gloria.slidingmenu.lib.fragment.TrafficFragment;
import com.parse.Parse;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class Activity_Main extends SlidingFragmentActivity {
	
	FragmentTransaction fragmentTransaction;
	 
	protected LinearLayout titlebar,layout;
	protected Button titlebar_left,titlebar_right;
	protected TextView titlebar_name;
	
	public static RadioGroup radioGroup;
	RadioButton tab_radiobtn_1,tab_radiobtn_2,tab_radiobtn_3,
				tab_radiobtn_4,tab_radiobtn_5;
	 
	private static final int DIALOG_EXIT = 0;
	private ArrayList<TabInfo> tabInfos;
	String img_path = "";
	String orientation="";
	protected final int PICTYPE_CAMERA = 10; // 相机取图
	protected final int PICTYPE_LIB = 20; // 手机相册取图
	boolean isSaveCookies=true;
	boolean isRefresh = true;
	ProgressDialog pdialog;
	public static int menu_selectPos = 0;
	public static String fid = "351";
	
	public static int menu_style = 0; //菜单样式 
	
	int type = 0; 
	public final static int TYPE_INFO = 0; 
	public final static int TYPE_MAP = 1; 
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);
        
		initData();
        //左边栏     	  
        setView();   
        if(type == TYPE_INFO){
        	getInformationFragment();
        }else{
        	getMapFragment();
        }
    }
    
    private void initData() {
    	type = getIntent().getIntExtra("type", TYPE_INFO);
    	if(type == TYPE_INFO){
        	menu_selectPos = 0;
        }else{
        	menu_selectPos = 1;
        }
    	
    	String applicationId = "jx7XTM6gS1YGBcTX82kPbwZRNTuhRhvRYq6tQOM9";
		String clientKey = "4z6JnvbHbOX8Rc70kryVhSxmgqpR9Mjp2MCZHVbs";
		Parse.initialize(this, applicationId, clientKey);
		
//		String applicationId = "c2bcZnrowbIprG6rhWyuTBk1NhoWIt66rHefmZ7A";
//		String clientKey = "NRC7SzCxeY8poAcep8ny9g4qoRbFXDoWU18s9Qey";
//		Parse.initialize(this, applicationId, clientKey);
	}

	private void setView(){
    	 initSlidingMenu();
    	setMenuView();
    	
    	radioGroup = (RadioGroup)findViewById(R.id.main_radio);
    	tab_radiobtn_1 = (RadioButton)findViewById(R.id.tab_radiobtn_1);
    	tab_radiobtn_2 = (RadioButton)findViewById(R.id.tab_radiobtn_2);
    	tab_radiobtn_3 = (RadioButton)findViewById(R.id.tab_radiobtn_3);
    	tab_radiobtn_4 = (RadioButton)findViewById(R.id.tab_radiobtn_4);
    	tab_radiobtn_5 = (RadioButton)findViewById(R.id.tab_radiobtn_5);
    	
    	tab_radiobtn_1.setOnCheckedChangeListener(onCheckedChangeListener);
    	tab_radiobtn_2.setOnCheckedChangeListener(onCheckedChangeListener);
    	tab_radiobtn_3.setOnCheckedChangeListener(onCheckedChangeListener);
    	tab_radiobtn_4.setOnCheckedChangeListener(onCheckedChangeListener);
    	tab_radiobtn_5.setOnCheckedChangeListener(onCheckedChangeListener);
    }
    
    OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked){
				switch (buttonView.getId()) {
				case R.id.tab_radiobtn_1:
					menu_style = 0;
					getInformationFragment();
					tab_radiobtn_1.setChecked(true);
					break;
				case R.id.tab_radiobtn_2:
					menu_style = 1;
					getActivityFragment();
					tab_radiobtn_2.setChecked(true);
					break;
				case R.id.tab_radiobtn_3:
					menu_style = 2;
					getMallFragment();
					tab_radiobtn_3.setChecked(true);
					break;
				case R.id.tab_radiobtn_4:
					menu_style = 3;
					getTicketFragment();
					tab_radiobtn_4.setChecked(true);
					break;
				case R.id.tab_radiobtn_5:
					menu_style = 4;
					getTrafficFragment();
					tab_radiobtn_5.setChecked(true);
					break;
				default:
					break;
				}
				//setMenuStyle(menu_style);
			}
		}
	};
    	
	/*
	 * 设置菜单模式
	 */
	public void setMenuStyle(int i) {
		switch (i) {
		case 0:
			 getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			 getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			break;
		case 1:
		case 2:
		case 3:
		case 4:
			 getSlidingMenu().setMode(SlidingMenu.RIGHT);
			 getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;
		case 10:
			getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			break;
		case 20:
			break;
		case 30:
			break;
		case 40:
			break;
		case 50:
			break;
		case 60:
			getSlidingMenu().setMode(SlidingMenu.RIGHT);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;
		default:
			break;
		}
	}
	
	//花博资讯
	private void getMapFragment(){
		String text = "地图导览";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				mapFragment == null ? new MapFragment("地图导览"):mapFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}
	
	//花博资讯
	private void getInformationFragment(){
		String text = RightMenu.mResArrayTextRight[0]+"_资讯";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		InformationFragment informationFragment = (InformationFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				informationFragment == null ? new InformationFragment("资讯"):informationFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}

	//活动
	private void getActivityFragment(){
		String text = RightMenu.mResArrayTextRight[0]+"_活动";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		ActivityFragment activityFragment = (ActivityFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				activityFragment == null ? new ActivityFragment("活动"):activityFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}
	
	//商城
	private void getMallFragment(){
		String text = RightMenu.mResArrayTextRight[0]+"_商城";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		MallFragment mallFragment = (MallFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				mallFragment == null ? new MallFragment("商城"):mallFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}
	
	//票务
	private void getTicketFragment(){
		String text = RightMenu.mResArrayTextRight[0]+"_票务";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		TicketFragment ticketFragment = (TicketFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				ticketFragment == null ? new TicketFragment("票务"):ticketFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}
	
	//交通
	private void getTrafficFragment(){
		String text = RightMenu.mResArrayTextRight[0]+"_交通";
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		TrafficFragment trafficFragment = (TrafficFragment) fragmentManager.findFragmentByTag(text);
		fragmentTransaction.replace(R.id.content_main,
				trafficFragment == null ? new TrafficFragment("交通"):trafficFragment,text);
		fragmentTransaction.addToBackStack(text);
		fragmentTransaction.commit();
	}
	
	/*
	 * 初始菜单
	 */
	private void initSlidingMenu() {
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidth(50);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(80);
        sm.setFadeDegree(0.35f);
        //设置slding menu的几种手势模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding menu
        //TOUCHMODE_NONE 自然是不能通过手势打开啦
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }
	
	public void onResume() {  
		super.onResume();
		//setMenuStyle(menu_style);
		/**
		 * Fragment页面起始 (注意： 如果有继承的父Fragment中已经添加了该调用，那么子Fragment中务必不能添加）
		 */
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		/**
		 *Fragment 页面结束（注意：如果有继承的父Fragment中已经添加了该调用，那么子Fragment中务必不能添加）
		 */
		StatService.onPause(this);
	}
    
    /*
     * 设置菜单视图
     */
	private void setMenuView() {
////		setBehindContentView(R.layout.frame_menu_left);
////	    getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
////	    getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
////	    getSlidingMenu().setBehindWidthRes(R.dimen.left_menu_width);
////	    getSlidingMenu().setMenu(R.layout.frame_menu_left);
////	    getSlidingMenu().setSecondaryMenu(R.layout.frame_menu_right);
//		 SlidingMenu _SlidingMenu=new SlidingMenu(this);
//		//_SlidingMenu = getSlidingMenu(); // 由于Activity继承自SlidingFragmentActivity,所以直接获取当前的侧边栏菜单
//
//		_SlidingMenu.setMode(SlidingMenu.RIGHT); // 设置侧边栏菜单为左右模式
//	 
//		_SlidingMenu.setSecondaryShadowDrawable(R.drawable.shadow); // 设置左菜单的阴影
//		_SlidingMenu.setShadowWidth(10); // 设置阴影宽度
//		_SlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 设置侧边栏菜单触摸模式为全屏模式
//
//		setBehindContentView(R.layout.frame_menu_left); // 设置左菜单的默认VIEW
//		RightMenu leftMenu = new RightMenu();
//		getSupportFragmentManager().beginTransaction().replace(R.id.menu_left, leftMenu).commit();
//	        
////	 
////		_SlidingMenu.setSecondaryMenu(R.layout.frame_menu_right); // 设置右菜单默认VIEW
////		_SlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright); // 设置右菜单阴影
////		_SlidingMenu.setRightBehindWidthRes(R.dimen.right_menu_width); // 设置右菜单的宽度,该值为右菜单展开的宽度
////	 
////        RightMenu rightMenu = new RightMenu();
////        getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, rightMenu).commit();
		
		 setBehindContentView(R.layout.frame_menu_left);
	    getSlidingMenu().setMode(SlidingMenu.LEFT);
	    getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	    
	   // getSlidingMenu().setMenu(R.layout.frame_menu_left);
	    getSlidingMenu().setSecondaryMenu(R.layout.frame_menu_right);
	    
	    RightMenu leftMenu = new RightMenu();
	    getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, leftMenu).commit();
	    
	    //LeftMenu lef=new LeftMenu();
	   // getSupportFragmentManager().beginTransaction().replace(R.id.menu_right, lef).commit();
	   
	}
	
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_EXIT) {
			return new AlertDialog.Builder(this).setTitle(
				R.string.hint_Msg).setMessage(R.string.isExitAPP)
				.setPositiveButton(R.string.ok,exitListener)
				.setNegativeButton(R.string.cancel, null).create();
		}
		return super.onCreateDialog(id);
	}
		
	DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			BaseApplication.getInstance().exit(); 
		}
	};
	
	@Override 
	public boolean dispatchKeyEvent(KeyEvent event) { 
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){			
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0){
				showDialog(DIALOG_EXIT);
				return true; 
			}
	     }
		 return super.dispatchKeyEvent(event);
	} 

}
