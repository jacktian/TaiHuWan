package com.gloria.slidingmenu.lib.fragment;

import com.gloria.hbh.main.Activity_Main;
import com.gloria.hbh.main.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 票务页面
 */
@SuppressLint("ValidFragment")
public class TicketFragment extends BaseFragment {

	String text = "咨讯";

	Activity_Main mMain = null;
	private FrameLayout mFrameLayout = null;

	ImageView OfficialBooking, OfficialAdvice, ChinaPost, ChinaBank, ChangZhouGuoLv, ChangZhouChunQiu, GuangDaGuoLv,
			MingDuGuolv, ChangZhouFengHuang, GuangDianGuoLv;

	public TicketFragment(String text) {
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
		View view = inflater.inflate(R.layout.activity_ticket, null);

		setView(view);
		setListener();

		return view;
	}

	private void setView(View view) {
		titlebar = (LinearLayout) view.findViewById(R.id.titlebar);
		titlebar.setVisibility(View.VISIBLE);
		titlebar_name = (TextView) view.findViewById(R.id.titlebar_name);
		// titlebar_left = (Button)view.findViewById(R.id.titlebar_left);
		// titlebar_menu = (Button)view.findViewById(R.id.titlebar_menu);
		// titlebar_menu.setVisibility(View.INVISIBLE);
		// titlebar_left.setVisibility(View.INVISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		titlebar_name.setText("票务购买");
		titlebar_name.setTextColor(Color.BLACK);

		// OfficialBooking = (ImageView)view.findViewById(R.id.OfficialBooking);
		// OfficialAdvice = (ImageView)view.findViewById(R.id.OfficialAdvice);
		// ChinaPost = (ImageView)view.findViewById(R.id.ChinaPost);
		// ChinaBank = (ImageView)view.findViewById(R.id.ChinaBank);
		// ChangZhouGuoLv = (ImageView)view.findViewById(R.id.ChangZhouGuoLv);
		// ChangZhouChunQiu =
		// (ImageView)view.findViewById(R.id.ChangZhouChunQiu);
		// GuangDaGuoLv = (ImageView)view.findViewById(R.id.GuangDaGuoLv);
		// MingDuGuolv = (ImageView)view.findViewById(R.id.MingDuGuolv);
		// ChangZhouFengHuang =
		// (ImageView)view.findViewById(R.id.ChangZhouFengHuang);
		// GuangDianGuoLv = (ImageView)view.findViewById(R.id.GuangDianGuoLv);
	}

	private void setListener() {
		// titlebar_menu.setOnClickListener(onClickListener);
		// titlebar_left.setOnClickListener(onClickListener);
		// titlebar_name.setOnClickListener(onClickListener);
		//
		// OfficialBooking.setOnClickListener(onClickListener);
		// OfficialAdvice.setOnClickListener(onClickListener);
		// ChinaPost.setOnClickListener(onClickListener);
		// ChinaBank.setOnClickListener(onClickListener);
		// ChangZhouGuoLv.setOnClickListener(onClickListener);
		// ChangZhouChunQiu.setOnClickListener(onClickListener);
		// GuangDaGuoLv.setOnClickListener(onClickListener);
		// MingDuGuolv.setOnClickListener(onClickListener);
		// ChangZhouFengHuang.setOnClickListener(onClickListener);
		// GuangDianGuoLv.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			String text_dialog = "";
			switch (v.getId()) {
			case R.id.titlebar_menu:
				mMain.getSlidingMenu().toggle();
				break;
			case R.id.titlebar_right:
				break;
			// case R.id.OfficialBooking:
			// text_dialog = "tel:051986313216";
			// goToDialog(text_dialog);
			// break;
			// case R.id.OfficialAdvice:
			// text_dialog = "tel:051986050588";
			// goToDialog(text_dialog);
			// break;
			// case R.id.ChinaPost:
			// text_dialog = "tel:051988128185";
			// goToDialog(text_dialog);
			// break;
			// case R.id.ChinaBank:
			// text_dialog = "tel:051988118905";
			// goToDialog(text_dialog);
			// break;
			// case R.id.ChangZhouGuoLv:
			// text_dialog = "tel:051988881618";
			// goToDialog(text_dialog);
			// break;
			// case R.id.ChangZhouChunQiu:
			// text_dialog = "tel:051988888114";
			// goToDialog(text_dialog);
			// break;
			// case R.id.GuangDaGuoLv:
			// text_dialog = "tel:051988888100";
			// goToDialog(text_dialog);
			// break;
			// case R.id.MingDuGuolv:
			// text_dialog =
			// "tel:0519-86561998,0519-86562998,0519-86561998,0519-86562998";
			// goToDialog(text_dialog);
			// break;
			// case R.id.ChangZhouFengHuang:
			// text_dialog = "tel:051986318556";
			// goToDialog(text_dialog);
			// break;
			// case R.id.GuangDianGuoLv:
			// text_dialog = "tel:051986559191";
			// goToDialog(text_dialog);
			// break;
			}
		}
	};

	/*
	 * 拨打电话
	 */
	protected void goToDialog(String text) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(text));
		startActivity(intent);
	}
}
