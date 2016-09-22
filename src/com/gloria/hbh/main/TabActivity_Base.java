package com.gloria.hbh.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.gloria.hbh.application.BaseApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressWarnings("deprecation")
public abstract class TabActivity_Base extends TabActivity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected TabHost tabHost; 
	protected LinearLayout titlebar;
	protected Button titlebar_left,titlebar_right;
	protected TextView titlebar_name;
	protected ImageButton webnav_left,webnav_right;
	
	RelativeLayout layout_topbar;
	protected RadioGroup radioGroup;
	HashMap<Integer, RadioButton> radioButtons;
	
	private boolean instanceStateSaved;
	protected boolean isSubTab = false;
	String tmp = "";
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.item_clear_memory_cache:
//				imageLoader.clearMemoryCache();
//				return true;
//			case R.id.item_clear_disc_cache:
//				imageLoader.clearDiscCache();
//				return true;
//			default:
//				return false;
//		}
//	}
	
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 BaseApplication.getInstance().addActivity(this); 
	}
	
	protected void onResume(){
		if(getRadioButtons().size() > 0){
			RadioButton buttonView = getRadioButtons().get(radioGroup.getCheckedRadioButtonId());
			if(buttonView != null){
				Iterator iter = getRadioButtons().entrySet().iterator(); 
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    Integer key = (Integer) entry.getKey(); 
				    RadioButton val = (RadioButton) entry.getValue(); 
				    if(isSubTab){
				    	val.setBackgroundResource(R.drawable.topbar_bg_normal);
				    	val.setTextColor(getResources().getColor(R.color.text_gray));
				    	if(val == buttonView){
				    		val.setBackgroundResource(R.drawable.topbar_btnbg_select);
		    				val.setTextColor(getResources().getColor(R.color.white));
			    		}
				    }else{
				    	val.setBackgroundResource(R.drawable.bg_transparent);
				    	val.setTextColor(getResources().getColor(R.color.transparent));
				    	if(val == buttonView){
				    		val.setBackgroundResource(R.drawable.topbar_btnbg_select);
			    		}
				    }
				}
			}
		}
		super.onResume();
	}
	
	public HashMap<Integer, RadioButton> getRadioButtons() {
		if(radioButtons == null){
			radioButtons = new HashMap<Integer, RadioButton>();
		}
		return radioButtons;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}

	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}
}
