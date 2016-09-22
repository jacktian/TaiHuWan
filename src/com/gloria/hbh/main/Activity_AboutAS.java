package com.gloria.hbh.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_AboutAS extends Activity_Base{
	
	TextView info;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutas);
		
		setView();
		setListener();
	}
	
	private void setView(){
		titlebar = (LinearLayout)findViewById(R.id.titlebar);
		titlebar_name = (TextView)findViewById(R.id.titlebar_name);
		titlebar_name.setText(getString(R.string.aboutas));
		titlebar_back = (Button)findViewById(R.id.titlebar_back);
	 	titlebar_right = (Button)findViewById(R.id.titlebar_right);
	 	titlebar_back.setVisibility(View.VISIBLE);
	 	titlebar_right.setVisibility(View.INVISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	
	 	info = (TextView)findViewById(R.id.item_info);
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
	
	/*
	 * 监听
	 */
	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_right.setOnClickListener(onClickListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			default:
				break;
			}
		}
	};
}
