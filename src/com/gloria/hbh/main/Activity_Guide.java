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
//						.setMessage("��ͼ���������ǳ��Σ�")
//						.setPositiveButton("̫����ѯ",infoListener)
//						.setNegativeButton("��ͼ����", mapListener).create().show();
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
        animation.setRepeatCount(3);//�����ظ����� 
        animation.setRepeatMode(Animation.REVERSE);//���÷�����ִ��
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
    
    //�Ի�������¼�
   	DialogInterface.OnClickListener infoListener = new DialogInterface.OnClickListener() {
   		public void onClick(DialogInterface dialog, int which) {
   			goToInfoFrame();
   		}
   	};
   	
    //�Ի�������¼�
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
