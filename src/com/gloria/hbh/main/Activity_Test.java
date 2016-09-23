package com.gloria.hbh.main;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class Activity_Test extends Activity_Base {

	private WebView webview;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		webview = (WebView) findViewById(R.id.webview);
		setWebView();
	}

	private String getURl() {
		String url = "";

		url = "<html><body background=file:///android_asset/bg_main.png topmargin=30 leftmargin=30 bottommargin=30 rightmargin=30>"
				+ "<img src='file:///android_asset/bg_main_house.jpg'/>"
				+ "<table cellspacing=10 cellpadding=0 bgcolor=#B2FFFFFF width=100% >" + "<tr >"
				+ "<td background='file:///android_asset/section_bg.png'>　　　　　　　　概况</td>" + "</tr>" + "<tr >"
				+ "<td width=100%>中国花卉博览会（简称“花博会”）创始于1987年，每四年举办一次，是我国规模最大、影响最广的国家级 花事盛会，被誉为中国花卉界的“奥林匹克”，至今已在北京、上海、广州、成都等地成功举办七届，规模 、影响不断加深，正朝着国际化的方向发展。第八届中国花卉博览会将于2013年9月，在素有“国际花园城市”、“花都水城”美誉的江苏省常州市武进区精彩绽放。时 间：2013年9月28日—10月27日主 题：幸福像花儿一样 会 花：月季花主办单位：中国花卉协会，江苏省人民政府承办单位：江苏省花木协会，江苏省常州市人民政府实施单位：江苏省常州市武进区人民政府</td>"
				+ "</tr>" + "</table>" + "</body</html>";

		return url;
	}

	private void setWebView() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setSavePassword(false);
		// 设置保存数据
		webSettings.setSaveFormData(true);
		webSettings.setSupportZoom(true);
		// 缓存读取方式
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
