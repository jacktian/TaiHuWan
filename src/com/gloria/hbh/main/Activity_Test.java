package com.gloria.hbh.main;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class Activity_Test  extends Activity_Base {

	private WebView webview;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview); 
        
        webview = (WebView)findViewById(R.id.webview);
        setWebView();
	}
	
	 private  String getURl() {
		 String url = "";
		 
		 url = "<html><body background=file:///android_asset/bg_main.png topmargin=30 leftmargin=30 bottommargin=30 rightmargin=30>" 
				 + "<img src='file:///android_asset/bg_main_house.jpg'/>"
				 + "<table cellspacing=10 cellpadding=0 bgcolor=#B2FFFFFF width=100% >"
				 + "<tr >" 
				 + "<td background='file:///android_asset/section_bg.png'>�����������������ſ�</td>"
				 + "</tr>" 
				 + "<tr >" 
				 + "<td width=100%>�й����ܲ����ᣨ��ơ������ᡱ����ʼ��1987�꣬ÿ����ٰ�һ�Σ����ҹ���ģ���Ӱ�����Ĺ��Ҽ� ����ʢ�ᣬ����Ϊ�й����ܽ�ġ�����ƥ�ˡ����������ڱ������Ϻ������ݡ��ɶ��ȵسɹ��ٰ��߽죬��ģ ��Ӱ�첻�ϼ�������Ź��ʻ��ķ���չ���ڰ˽��й����ܲ����Ὣ��2013��9�£������С����ʻ�԰���С���������ˮ�ǡ������Ľ���ʡ������������������š�ʱ �䣺2013��9��28�ա�10��27���� �⣺�Ҹ��񻨶�һ�� �� �����¼������쵥λ���й�����Э�ᣬ����ʡ���������а쵥λ������ʡ��ľЭ�ᣬ����ʡ��������������ʵʩ��λ������ʡ�������������������</td>"
				 + "</tr>" 
				 + "</table>"
				 + "</body</html>";
		 
		 return url;
	 }
	
	private void setWebView() {
		WebSettings  webSettings = webview.getSettings();
		webSettings.setSavePassword(false);
		//���ñ�������
		webSettings.setSaveFormData(true);
		webSettings.setSupportZoom(true);
		//�����ȡ��ʽ
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		webview.setFocusable(true);
		webview.requestFocus();
		
		final String mimeType = "text/html";
		final String encoding = "UTF-8";
		webview.loadDataWithBaseURL("", getURl(), mimeType, encoding, "");
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.setBackgroundResource(R.drawable.bg_main);
 }
}
