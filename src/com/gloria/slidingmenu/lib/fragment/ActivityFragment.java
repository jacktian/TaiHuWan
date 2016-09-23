package com.gloria.slidingmenu.lib.fragment;

import java.util.ArrayList;
import java.util.List;

import com.gloria.hbh.adapter.activitybaseadapter;
import com.gloria.hbh.constant.GsonTools;
import com.gloria.hbh.data.app.activitybean;
import com.gloria.hbh.datadispose.Datamanage;
import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.PullListView;
import com.gloria.hbh.myview.PullListView.IXListViewListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityFragment extends BaseFragment implements IXListViewListener {
	String text = "活动";
	Activity_Main mMain = null;
	private FrameLayout mFrameLayout = null;
	TextView head_text1, head_text2;
	PullListView pulllistview;
	private List<activitybean> arrayList = new ArrayList<activitybean>();
	private Datamanage datamanage;
	private Activity instance;
	private activitybaseadapter newsadapter;
	public static String str = "", wea = "";
	private View messageLayout;

	public ActivityFragment() {
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		instance = activity;
	}

	public ActivityFragment(String text) {
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
		if (messageLayout == null) {
			messageLayout = inflater.inflate(R.layout.xmlinfro, null);
			setView(messageLayout);
			setListener();
			pulllistview.setpullstate();
			datamanage = new Datamanage(instance);
			pulllistview.setXListViewListener(this);
			try {
				arrayList = datamanage.findAll(activitybean.class);
				newsadapter = new activitybaseadapter(instance, arrayList);
				pulllistview.setAdapter(newsadapter);
			} catch (Exception e) {
			}
			if (isOpenNetWork()) {
				pulllistview.mHeaderView.setVisiableHeight(pxToDIP(70));
				pulllistview.mHeaderView.setState(2);
				onRefresh1();
			}
		}
		ViewGroup parent = (ViewGroup) messageLayout.getParent();
		if (parent != null)
			parent.removeView(messageLayout);
		return messageLayout;
	}

	protected int pxToDIP(int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
				instance.getResources().getDisplayMetrics());
	}

	protected boolean isOpenNetWork() {
		ConnectivityManager connManager = (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		{
			return false;
		}
	}

	private void setView(View view) {
		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
		titlebar_left = (Button) view.findViewById(R.id.titlebar_left);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar.setBackgroundResource(R.drawable.top_img);
		titlebar_name.setText("活动");
		titlebar_name.setTextColor(Color.BLACK);
		pulllistview = (PullListView) view.findViewById(R.id.listView);
	}

	private void setListener() {
	}

	protected void stoplistview() {
		pulllistview.mPullRefreshing = true;
		pulllistview.stopRefresh();
		pulllistview.stopLoadMore();
	}

	public void onRefresh1() {
		if (isOpenNetWork()) {
			HttpUtils http1 = new HttpUtils();
			http1.send(HttpRequest.HttpMethod.GET, "http://112.21.190.22/taihuwan/wapNewsList.action?id=9",
					new RequestCallBack<String>() {
						public void onLoading(long total, long current, boolean isUploading) {
						}

						public void onSuccess(ResponseInfo<String> responseInfo) {
							stoplistview();
							List<activitybean> testlist = new ArrayList<activitybean>();
							try {
								testlist = GsonTools.newsinforad(responseInfo.result);
								if (testlist.size() > 0) {
									arrayList = testlist;
									newsadapter = new activitybaseadapter(instance, arrayList);
									pulllistview.setAdapter(newsadapter);
									try {
										datamanage.deleteAll(activitybean.class);
										for (activitybean activitybean : testlist) {
											datamanage.save(activitybean);
										}
									} catch (Exception e) {
									}
								}
							} catch (Exception e) {
							}
						}

						public void onStart() {
						}

						public void onFailure(HttpException error, String msg) {
							stoplistview();
							Toast.makeText(instance, "获取列表失败", Toast.LENGTH_SHORT).show();
						}
					});
		} else {
			stoplistview();
			Toast.makeText(instance, "网络异常", Toast.LENGTH_SHORT).show();
		}
	}

	public void onLoadMore() {
	}
}
