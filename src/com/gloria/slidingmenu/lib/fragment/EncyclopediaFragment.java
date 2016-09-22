package com.gloria.slidingmenu.lib.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.ScreenUtils;

/*
 * 花博百科页面
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("static-access")
public class EncyclopediaFragment extends BaseFragment{
	
	String text = "咨讯";
	
    Activity_Main mMain = null;
    private FrameLayout mFrameLayout = null;
    
    TextView text_overview,text_logo_1,text_logo_2,text_emblem_1,text_emblem_2,
    			text_zhnr_1,text_zhnr_2,text_zhxs_1,text_zhxs_2,
    			text_zzx_1,text_zzx_2,text_fzx_1,text_fzx_2;
    
    WebView webview;
    private String  url = "";    //webview加载的网址
    
    LinearLayout layout;
    
    public EncyclopediaFragment() {
    }

    public EncyclopediaFragment(String text) {
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
        View view = inflater.inflate(R.layout.activity_encyclopedia, null);
        
        setView(view);
	 	setListener();
    	
        return view;
    }

	private void setView(View view) {
		titlebar = (LinearLayout)view.findViewById(R.id.titlebar);
        titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView)view.findViewById(R.id.titlebar_name);
		titlebar_menu = (Button)view.findViewById(R.id.titlebar_menu);
		titlebar_left = (Button)view.findViewById(R.id.titlebar_left);
	 	titlebar_menu.setVisibility(View.VISIBLE);
	 	titlebar_left.setVisibility(View.INVISIBLE);
	 	titlebar_name.setVisibility(View.VISIBLE);
	 	//titlebar_name.setText("花博百科");
	 	titlebar.setBackgroundResource(R.drawable.top_img);
	 	
	 	text_overview = (TextView)view.findViewById(R.id.text_overview);
	 	text_logo_1 = (TextView)view.findViewById(R.id.text_logo_1);
	 	text_logo_2 = (TextView)view.findViewById(R.id.text_logo_2);
	 	text_emblem_1 = (TextView)view.findViewById(R.id.text_emblem_1);
	 	text_emblem_2 = (TextView)view.findViewById(R.id.text_emblem_2);
	 	text_zhnr_1 = (TextView)view.findViewById(R.id.text_zhnr_1);
	 	text_zhnr_2 = (TextView)view.findViewById(R.id.text_zhnr_2);
	 	text_zhxs_1 = (TextView)view.findViewById(R.id.text_zhxs_1);
	 	text_zhxs_2 = (TextView)view.findViewById(R.id.text_zhxs_2);
	 	text_zzx_1 = (TextView)view.findViewById(R.id.text_zzx_1);
	 	text_zzx_2 = (TextView)view.findViewById(R.id.text_zzx_2);
	 	text_fzx_1 = (TextView)view.findViewById(R.id.text_fzx_1);
	 	text_fzx_2 = (TextView)view.findViewById(R.id.text_fzx_2);
	 	
	 	String overview = "中国花卉博览会（简称“花博会”）创始于1987年，每四年举办一次，" +
	 						"是我国规模最大、影响最广的国家级 花事盛会，被誉为中国花卉界的“奥林匹克”，" +
	 						"至今已在北京、上海、广州、成都等地成功举办七届，规模 、影响不断加深，正朝着国际化的方向发展。" +
	 						"第八届中国花卉博览会将于2013年9月，在素有“国际花园城市”、“花都水城”美誉的江苏省常州市武进区精彩绽放。" +
	 						"<br>时 间：2013年9月28日—10月27日<br>主 题：幸福像花儿一样 <br>会 花：月季花<br>" +
	 						"主办单位：中国花卉协会，江苏省人民政府<br>承办单位：江苏省花木协会，江苏省常州市人民政府<br>实施单位：江苏省常州市武进区人民政府";
	 	text_overview.setText(Html.fromHtml(overview));
	 	
	 	switch (ScreenUtils.getInstance().getWidth()) {
		case 480:
			text_overview.setTextSize(17);
			text_logo_1.setTextSize(17);
		 	text_logo_2.setTextSize(17);
		 	text_emblem_1.setTextSize(17);
		 	text_emblem_2.setTextSize(17);
		 	text_zhnr_1.setTextSize(17);
		 	text_zhnr_2.setTextSize(17);
		 	text_zhxs_1.setTextSize(17);
		 	text_zhxs_2.setTextSize(17);
		 	text_zzx_1.setTextSize(17);
		 	text_zzx_2.setTextSize(17);
		 	text_fzx_1.setTextSize(17);
		 	text_fzx_2.setTextSize(17);
		 	
			text_logo_1.setText("会徽造型中，花蕊部分是数字“8”的变形，整体元素融合了常州市花——月季、武进春");
			text_logo_2.setText("秋淹城“三城三河”、花博会蝶形主展馆等地方元素，有强烈的常州地域特色。");
			
			text_emblem_1.setText("吉祥物取名“和和”、“美美”。“和和”代表和谐社会、和睦关系，“美美”代表美好生");
			text_emblem_2.setText("活、美满人生。紧扣主题理念。");
			
			text_zhnr_1.setText("集中展示鲜切花、切叶、盆花等各类花卉；观叶植物、盆景；种子、苗木");
			text_zhnr_2.setText("、种球；干花、仿生花、装饰植物；花肥、基质；盆钵、几架、器皿；园艺工具、设施等相关产品；观赏石材；观赏鸟、鱼、虫；园林景观设计等。");
			
			text_zhxs_1.setText("展会由室内展区、室外展区、相关活动三部分组成。其中室内展区分综合馆");
			text_zhxs_2.setText("和专题馆，室外展区以植物造景为主，相关活动包括开幕式、闭幕式、颁奖仪式等。主展区规划建设总占地面积3000亩，分室内展区和室外展区两大部分。");
			
			text_zzx_1.setText("规划建设总占地面积3000亩，总分为室内展区和室外展区两大部分。");
			text_zzx_2.setText("【室内展区】综合馆：建筑面积约7万平方米，是第八届中国花卉博览会各展团室内布展的主展馆，分为省区市展区、港澳台展区、企业展区、专题花卉与科技展区。省区市展区原则上以省、自治区、直辖市和深圳市花卉协会为单位统一组织参展，港澳台展区和企业展区由参展单位设计师布展。 主题馆：由自然馆、科技馆、艺术馆组成。【室外展区】原则上以植物造景为主，由各省市室外展区、国际展区、专题花卉展区和企业展区组成。");
			
			text_fzx_1.setText("主要为“一场五园”，“一场”即第六届中国花卉交易会场——夏溪花木市场，市");
			text_fzx_2.setText("场规划扩建至800亩规模，将建成全国最大的花卉及相关资材营销物流中心；“五园”指江南花都产业园、紫薇园、玫瑰园、艺林园、盆景园。");
			
			break;
		case 720:
			text_overview.setTextSize(18);
			text_logo_1.setTextSize(18);
		 	text_logo_2.setTextSize(18);
		 	text_emblem_1.setTextSize(18);
		 	text_emblem_2.setTextSize(18);
		 	text_zhnr_1.setTextSize(18);
		 	text_zhnr_2.setTextSize(18);
		 	text_zhxs_1.setTextSize(18);
		 	text_zhxs_2.setTextSize(18);
		 	text_zzx_1.setTextSize(18);
		 	text_zzx_2.setTextSize(18);
		 	text_fzx_1.setTextSize(18);
		 	text_fzx_2.setTextSize(18);
		 	
		 	text_logo_1.setText("会徽造型中，花蕊部分是数字“8”的变形，整体元素融合了常州市花——月季、武进春秋淹城“三城");
			text_logo_2.setText("三河”、花博会蝶形主展馆等地方元素，有强烈的常州地域特色。");
			
			text_emblem_1.setText("吉祥物取名“和和”、“美美”。“和和”代表和谐社会、和睦关系，“美美”代表美好生活、美满人生");
			text_emblem_2.setText("。紧扣主题理念。");
			
			text_zhnr_1.setText("集中展示鲜切花、切叶、盆花等各类花卉；观叶植物、盆景；种子、苗木、种球；干");
			text_zhnr_2.setText("花、仿生花、装饰植物；花肥、基质；盆钵、几架、器皿；园艺工具、设施等相关产品；观赏石材；观赏鸟、鱼、虫；园林景观设计等。");
			
			text_zhxs_1.setText("展会由室内展区、室外展区、相关活动三部分组成。其中室内展区分综合馆和专题馆。");
			text_zhxs_2.setText("室外展区以植物造景为主，相关活动包括开幕式、闭幕式、颁奖仪式等。主展区规划建设总占地面积3000亩，分室内展区和室外展区两大部分。");
			
			text_zzx_1.setText("规划建设总占地面积3000亩，分室内展区和室外展区两大部分。");
			text_zzx_2.setText("【室内展区】综合馆：建筑面积约7万平方米，是第八届中国花卉博览会各展团室内布展的主展馆，分为省区市展区、港澳台展区、企业展区、专题花卉与科技展区。省区市展区原则上以省、自治区、直辖市和深圳市花卉协会为单位统一组织参展，港澳台展区和企业展区由参展单位设计师布展。 主题馆：由自然馆、科技馆、艺术馆组成。【室外展区】原则上以植物造景为主，由各省市室外展区、国际展区、专题花卉展区和企业展区组成。");
			
			text_fzx_1.setText("主要为“一场五园”，“一场”即第六届中国花卉交易会场——夏溪花木市场，市场规划扩建");
			text_fzx_2.setText("至800亩规模，将建成全国最大的花卉及相关资材营销物流中心；“五园”指江南花都产业园、紫薇园、玫瑰园、艺林园、盆景园。");
			
			break;
		case 768:
			text_overview.setTextSize(18);
			text_logo_1.setTextSize(18);
		 	text_logo_2.setTextSize(18);
		 	text_emblem_1.setTextSize(18);
		 	text_emblem_2.setTextSize(18);
		 	text_zhnr_1.setTextSize(18);
		 	text_zhnr_2.setTextSize(18);
		 	text_zhxs_1.setTextSize(18);
		 	text_zhxs_2.setTextSize(18);
		 	text_zzx_1.setTextSize(18);
		 	text_zzx_2.setTextSize(18);
		 	text_fzx_1.setTextSize(18);
		 	text_fzx_2.setTextSize(18);
		 	
		 	text_logo_1.setText("会徽造型中，花蕊部分是数字“8”的变形，整体元素融合了常州市花——月季、武进春秋淹城“三城三");
			text_logo_2.setText("河”、花博会蝶形主展馆等地方元素，有强烈的常州地域特色。");
			
			text_emblem_1.setText("吉祥物取名“和和”、“美美”。“和和”代表和谐社会、和睦关系，“美美”代表美好生活、美满人生。紧扣");
			text_emblem_2.setText("主题理念。");
			
			text_zhnr_1.setText("集中展示鲜切花、切叶、盆花等各类花卉；观叶植物、盆景；种子、苗木、种球；干花、仿生");
			text_zhnr_2.setText("花、装饰植物；花肥、基质；盆钵、几架、器皿；园艺工具、设施等相关产品；观赏石材；观赏鸟、鱼、虫；园林景观设计等。");
			
			text_zhxs_1.setText("展会由室内展区、室外展区、相关活动三部分组成。其中室内展区分综合馆和专题馆。");
			text_zhxs_2.setText("室外展区以植物造景为主，相关活动包括开幕式、闭幕式、颁奖仪式等。主展区规划建设总占地面积3000亩，分室内展区和室外展区两大部分。");
			
			text_zzx_1.setText("规划建设总占地面积3000亩，总分为室内展区和室外展区两大部分。");
			text_zzx_2.setText("【室内展区】综合馆：建筑面积约7万平方米，是第八届中国花卉博览会各展团室内布展的主展馆，分为省区市展区、港澳台展区、企业展区、专题花卉与科技展区。省区市展区原则上以省、自治区、直辖市和深圳市花卉协会为单位统一组织参展，港澳台展区和企业展区由参展单位设计师布展。 主题馆：由自然馆、科技馆、艺术馆组成。【室外展区】原则上以植物造景为主，由各省市室外展区、国际展区、专题花卉展区和企业展区组成。");
			
			text_fzx_1.setText("主要为“一场五园”，“一场”即第六届中国花卉交易会场——夏溪花木市场，市场规划扩建至800亩");
			text_fzx_2.setText("规模，将建成全国最大的花卉及相关资材营销物流中心；“五园”指江南花都产业园、紫薇园、玫瑰园、艺林园、盆景园。");
			break;
		default:
			break;
		}
	 	
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
			case R.id.titlebar_left:
				break;
			default:
				break;
			}
		}		
	};
	
}