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
 * �����ٿ�ҳ��
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("static-access")
public class EncyclopediaFragment extends BaseFragment{
	
	String text = "��Ѷ";
	
    Activity_Main mMain = null;
    private FrameLayout mFrameLayout = null;
    
    TextView text_overview,text_logo_1,text_logo_2,text_emblem_1,text_emblem_2,
    			text_zhnr_1,text_zhnr_2,text_zhxs_1,text_zhxs_2,
    			text_zzx_1,text_zzx_2,text_fzx_1,text_fzx_2;
    
    WebView webview;
    private String  url = "";    //webview���ص���ַ
    
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
	 	//titlebar_name.setText("�����ٿ�");
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
	 	
	 	String overview = "�й����ܲ����ᣨ��ơ������ᡱ����ʼ��1987�꣬ÿ����ٰ�һ�Σ�" +
	 						"���ҹ���ģ���Ӱ�����Ĺ��Ҽ� ����ʢ�ᣬ����Ϊ�й����ܽ�ġ�����ƥ�ˡ���" +
	 						"�������ڱ������Ϻ������ݡ��ɶ��ȵسɹ��ٰ��߽죬��ģ ��Ӱ�첻�ϼ�������Ź��ʻ��ķ���չ��" +
	 						"�ڰ˽��й����ܲ����Ὣ��2013��9�£������С����ʻ�԰���С���������ˮ�ǡ������Ľ���ʡ������������������š�" +
	 						"<br>ʱ �䣺2013��9��28�ա�10��27��<br>�� �⣺�Ҹ��񻨶�һ�� <br>�� �����¼���<br>" +
	 						"���쵥λ���й�����Э�ᣬ����ʡ��������<br>�а쵥λ������ʡ��ľЭ�ᣬ����ʡ��������������<br>ʵʩ��λ������ʡ�������������������";
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
		 	
			text_logo_1.setText("��������У����ﲿ�������֡�8���ı��Σ�����Ԫ���ں��˳����л������¼��������");
			text_logo_2.setText("���ͳǡ��������ӡ��������������չ�ݵȵط�Ԫ�أ���ǿ�ҵĳ��ݵ�����ɫ��");
			
			text_emblem_1.setText("������ȡ�����ͺ͡����������������ͺ͡������г��ᡢ������ϵ��������������������");
			text_emblem_2.setText("����������������������");
			
			text_zhnr_1.setText("����չʾ���л�����Ҷ���軨�ȸ��໨�ܣ���Ҷֲ��辰�����ӡ���ľ");
			text_zhnr_2.setText("�����򣻸ɻ�����������װ��ֲ����ʡ����ʣ��貧�����ܡ�����԰�չ��ߡ���ʩ����ز�Ʒ������ʯ�ģ��������㡢�棻԰�־�����Ƶȡ�");
			
			text_zhxs_1.setText("չ��������չ��������չ������ػ��������ɡ���������չ�����ۺϹ�");
			text_zhxs_2.setText("��ר��ݣ�����չ����ֲ���쾰Ϊ������ػ������Ļʽ����Ļʽ���佱��ʽ�ȡ���չ���滮������ռ�����3000Ķ��������չ��������չ�����󲿷֡�");
			
			text_zzx_1.setText("�滮������ռ�����3000Ķ���ܷ�Ϊ����չ��������չ�����󲿷֡�");
			text_zzx_2.setText("������չ�����ۺϹݣ��������Լ7��ƽ���ף��ǵڰ˽��й����ܲ������չ�����ڲ�չ����չ�ݣ���Ϊʡ����չ�����۰�̨չ������ҵչ����ר�⻨����Ƽ�չ����ʡ����չ��ԭ������ʡ����������ֱϽ�к������л���Э��Ϊ��λͳһ��֯��չ���۰�̨չ������ҵչ���ɲ�չ��λ���ʦ��չ�� ����ݣ�����Ȼ�ݡ��Ƽ��ݡ���������ɡ�������չ����ԭ������ֲ���쾰Ϊ�����ɸ�ʡ������չ��������չ����ר�⻨��չ������ҵչ����ɡ�");
			
			text_fzx_1.setText("��ҪΪ��һ����԰������һ�������������й����ܽ��׻᳡������Ϫ��ľ�г�����");
			text_fzx_2.setText("���滮������800Ķ��ģ��������ȫ�����Ļ��ܼ�����ʲ�Ӫ���������ģ�����԰��ָ���ϻ�����ҵ԰����ޱ԰��õ��԰������԰���辰԰��");
			
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
		 	
		 	text_logo_1.setText("��������У����ﲿ�������֡�8���ı��Σ�����Ԫ���ں��˳����л������¼�����������ͳǡ�����");
			text_logo_2.setText("���ӡ��������������չ�ݵȵط�Ԫ�أ���ǿ�ҵĳ��ݵ�����ɫ��");
			
			text_emblem_1.setText("������ȡ�����ͺ͡����������������ͺ͡������г��ᡢ������ϵ�����������������������������");
			text_emblem_2.setText("�������������");
			
			text_zhnr_1.setText("����չʾ���л�����Ҷ���軨�ȸ��໨�ܣ���Ҷֲ��辰�����ӡ���ľ�����򣻸�");
			text_zhnr_2.setText("������������װ��ֲ����ʡ����ʣ��貧�����ܡ�����԰�չ��ߡ���ʩ����ز�Ʒ������ʯ�ģ��������㡢�棻԰�־�����Ƶȡ�");
			
			text_zhxs_1.setText("չ��������չ��������չ������ػ��������ɡ���������չ�����ۺϹݺ�ר��ݡ�");
			text_zhxs_2.setText("����չ����ֲ���쾰Ϊ������ػ������Ļʽ����Ļʽ���佱��ʽ�ȡ���չ���滮������ռ�����3000Ķ��������չ��������չ�����󲿷֡�");
			
			text_zzx_1.setText("�滮������ռ�����3000Ķ��������չ��������չ�����󲿷֡�");
			text_zzx_2.setText("������չ�����ۺϹݣ��������Լ7��ƽ���ף��ǵڰ˽��й����ܲ������չ�����ڲ�չ����չ�ݣ���Ϊʡ����չ�����۰�̨չ������ҵչ����ר�⻨����Ƽ�չ����ʡ����չ��ԭ������ʡ����������ֱϽ�к������л���Э��Ϊ��λͳһ��֯��չ���۰�̨չ������ҵչ���ɲ�չ��λ���ʦ��չ�� ����ݣ�����Ȼ�ݡ��Ƽ��ݡ���������ɡ�������չ����ԭ������ֲ���쾰Ϊ�����ɸ�ʡ������չ��������չ����ר�⻨��չ������ҵչ����ɡ�");
			
			text_fzx_1.setText("��ҪΪ��һ����԰������һ�������������й����ܽ��׻᳡������Ϫ��ľ�г����г��滮����");
			text_fzx_2.setText("��800Ķ��ģ��������ȫ�����Ļ��ܼ�����ʲ�Ӫ���������ģ�����԰��ָ���ϻ�����ҵ԰����ޱ԰��õ��԰������԰���辰԰��");
			
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
		 	
		 	text_logo_1.setText("��������У����ﲿ�������֡�8���ı��Σ�����Ԫ���ں��˳����л������¼�����������ͳǡ�������");
			text_logo_2.setText("�ӡ��������������չ�ݵȵط�Ԫ�أ���ǿ�ҵĳ��ݵ�����ɫ��");
			
			text_emblem_1.setText("������ȡ�����ͺ͡����������������ͺ͡������г��ᡢ������ϵ�����������������������������������");
			text_emblem_2.setText("�������");
			
			text_zhnr_1.setText("����չʾ���л�����Ҷ���軨�ȸ��໨�ܣ���Ҷֲ��辰�����ӡ���ľ�����򣻸ɻ�������");
			text_zhnr_2.setText("����װ��ֲ����ʡ����ʣ��貧�����ܡ�����԰�չ��ߡ���ʩ����ز�Ʒ������ʯ�ģ��������㡢�棻԰�־�����Ƶȡ�");
			
			text_zhxs_1.setText("չ��������չ��������չ������ػ��������ɡ���������չ�����ۺϹݺ�ר��ݡ�");
			text_zhxs_2.setText("����չ����ֲ���쾰Ϊ������ػ������Ļʽ����Ļʽ���佱��ʽ�ȡ���չ���滮������ռ�����3000Ķ��������չ��������չ�����󲿷֡�");
			
			text_zzx_1.setText("�滮������ռ�����3000Ķ���ܷ�Ϊ����չ��������չ�����󲿷֡�");
			text_zzx_2.setText("������չ�����ۺϹݣ��������Լ7��ƽ���ף��ǵڰ˽��й����ܲ������չ�����ڲ�չ����չ�ݣ���Ϊʡ����չ�����۰�̨չ������ҵչ����ר�⻨����Ƽ�չ����ʡ����չ��ԭ������ʡ����������ֱϽ�к������л���Э��Ϊ��λͳһ��֯��չ���۰�̨չ������ҵչ���ɲ�չ��λ���ʦ��չ�� ����ݣ�����Ȼ�ݡ��Ƽ��ݡ���������ɡ�������չ����ԭ������ֲ���쾰Ϊ�����ɸ�ʡ������չ��������չ����ר�⻨��չ������ҵչ����ɡ�");
			
			text_fzx_1.setText("��ҪΪ��һ����԰������һ�������������й����ܽ��׻᳡������Ϫ��ľ�г����г��滮������800Ķ");
			text_fzx_2.setText("��ģ��������ȫ�����Ļ��ܼ�����ʲ�Ӫ���������ģ�����԰��ָ���ϻ�����ҵ԰����ޱ԰��õ��԰������԰���辰԰��");
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