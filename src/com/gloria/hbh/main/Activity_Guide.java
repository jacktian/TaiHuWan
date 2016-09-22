package com.gloria.hbh.main;

import com.gloria.hbh.util.ScreenUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_Guide  extends Activity_Base {

	private LinearLayout startView;  
    private TranslateAnimation animation; 
    
    TextView text_enter;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_guide);
      //  goToInfoFrame();
        text_enter = (TextView) findViewById(R.id.text_enter);
        text_enter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				new AlertDialog.Builder(Activity_Guide.this)
//						.setMessage("地图导览，无忧畅游！")
//						.setPositiveButton("太湖咨询",infoListener)
//						.setNegativeButton("地图导览", mapListener).create().show();
				 goToInfoFrame();
			}
		});
        
        startView=(LinearLayout)findViewById(R.id.startView);  
//        animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, 
//        		Animation.RELATIVE_TO_PARENT, -1.0f,   Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);  
        float xScale = (float)4881/ScreenUtils.getInstance().getWidth()-1;
        animation=new TranslateAnimation(0, 0, 
        		Animation.RELATIVE_TO_PARENT, -xScale,   Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);  
        
        animation.setDuration(15*1000);  
        animation.setRepeatCount(3);//设置重复次数 
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setAnimationListener(new AnimationListener() {
        	public void onAnimationStart(Animation animation) {
        	}
        	public void onAnimationRepeat(Animation animation) {
        	}
        	public void onAnimationEnd(Animation animation) {
        		startView.startAnimation(animation); 
        	}
        });
        startView.startAnimation(animation); 
    }  
    
    //对话框监听事件
   	DialogInterface.OnClickListener infoListener = new DialogInterface.OnClickListener() {
   		public void onClick(DialogInterface dialog, int which) {
   			goToInfoFrame();
   		}
   	};
   	
    //对话框监听事件
   	DialogInterface.OnClickListener mapListener = new DialogInterface.OnClickListener() {
   		public void onClick(DialogInterface dialog, int which) {
   			goToMapFrame();
   		}
   	};
   	
	protected void goToInfoFrame() {
		Intent intent = new Intent();
		intent.setClass(Activity_Guide.this, Activity_Main.class);
		intent.putExtra("type", Activity_Main.TYPE_INFO);
		startActivity(intent);
	}
	
	protected void goToMapFrame() {
		Intent intent = new Intent();
		intent.setClass(Activity_Guide.this, Activity_Main.class);
		intent.putExtra("type", Activity_Main.TYPE_MAP);
		startActivity(intent);
	}
}  
