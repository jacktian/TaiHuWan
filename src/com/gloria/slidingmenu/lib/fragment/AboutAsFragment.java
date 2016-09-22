package com.gloria.slidingmenu.lib.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;

/*
 * 关于我们页面
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("static-access")
public class AboutAsFragment extends BaseFragment{
	
	TextView info;
	String text = "咨讯";
	
    Activity_Main mMain = null;
    private FrameLayout mFrameLayout = null;
    
    public AboutAsFragment() {
    }

    public AboutAsFragment(String text) {
        this.text = text;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        
        mMain = (Activity_Main) getActivity();
        mFrameLayout = (FrameLayout) mMain.findViewById(R.id.content_main);
    }
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aboutas, null);
        
        setView(view);
	 	setListener();
    	
        return view;
    }

	private void setView(View view) {
		titlebar = (LinearLayout)view.findViewById(R.id.titlebar);
		titlebar_name = (TextView)view.findViewById(R.id.titlebar_name);
		titlebar_name.setText(getString(R.string.aboutas));
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_left = (Button)view.findViewById(R.id.titlebar_left);
		titlebar_menu = (Button)view.findViewById(R.id.titlebar_menu);
	 	titlebar_left.setVisibility(View.INVISIBLE);
	 	titlebar_menu.setVisibility(View.VISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	
	 	info = (TextView)view.findViewById(R.id.item_info);
	 	info.setText("江苏甲子网络科技有限公司成立于2008年，获江苏省发展和改革委员会的批准，" +
	 			"在常州市武进太湖湾度假区内规划建设江苏数字文化产业振兴基地，" +
	 			"项目一期将重点建设其中的“一园”即环球数字嬉戏谷动漫游戏文化主题公园；" +
	 			"“一网”CCJOY中国数字线上互动娱乐门户网， 真正达成了线上线下一体的娱乐形式。" +
	 			"公司作为中国互动娱乐领域的生力军，始终致力互联网和线下资源“虚实互动”娱乐新体验，" +
	 			"同时努力促进数字文化生活，通过互联网为用户提供多元化的娱乐服务，为大众创造更愉悦的线上线下互动体验。" +
	 			"公司旗下共有4个研发团队共120余人；拥有成功研发经验；以“嬉戏”为主题切入点，" +
	 			"自主研发端游产品《中华龙塔》《密传2》；页游产品《嬉戏幻想》《嬉戏飞车》《上古传奇》等；" +
	 			"手机游戏《嬉游记》将于2013年强势推出，产品成功销往港、澳、台、东南亚等地，现均已上线运营。");
	}

	private void setListener(){
    	titlebar_menu.setOnClickListener(onClickListener);
    	titlebar_left.setOnClickListener(onClickListener);
		titlebar_name.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_menu:
				if(mMain.getSlidingMenu().isSecondaryMenuShowing()){
					mMain.getSlidingMenu().toggle();
				}else{
					mMain.getSlidingMenu().showSecondaryMenu();
				}
				break;
			case R.id.titlebar_back:
				break;
			default:
				break;
			}
		}		
	};
	
}