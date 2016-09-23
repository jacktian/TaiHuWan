package com.gloria.hbh.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.cloudsdk.BaiduException;
import com.baidu.cloudsdk.IBaiduListener;
import com.baidu.cloudsdk.social.core.MediaType;
import com.baidu.cloudsdk.social.oauth.SocialConfig;
import com.baidu.cloudsdk.social.share.ShareContent;
import com.baidu.cloudsdk.social.share.SocialShare;
import com.baidu.cloudsdk.social.share.SocialShare.UIWidgetStyle;
import com.baidu.cloudsdk.social.share.SocialShareConfig;
import com.gloria.hbh.data.app.SubTabInfo.SubTabInfoTypeConstants;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.util.TextUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

/*
 * 内容详细页面
 */
public class Activity_Detail extends Activity_Base {

	ImageView image;
	TextView text_title;
	TextView text_subtitle;
	TextView text_content;

	String text = "";
	String title = "";
	String description = "";
	String date = "";
	String imageUrl = "";
	int type = SubTabInfoTypeConstants.SUBCATE_COMMON;

	String content = "";
	private String clientID;

	private ShareContent mImageContent;
	private DefaultListener mDefaultListener;
	private AlertDialog mAlertDialog;
	final Handler handler = new Handler();
	private FinalBitmap fb;
	DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.defalut_img_small)
			.showImageForEmptyUri(R.drawable.defalut_img_small).cacheInMemory().cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565).build();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		fb = FinalBitmap.create(this);
		fb.configLoadingImage(R.drawable.defalut_img_small);
		fb.configLoadfailImage(R.drawable.defalut_img_small);
		initData();
		setView();
		setListener();
	}

	private void initData() {
		type = getIntent().getIntExtra("type", SubTabInfoTypeConstants.SUBCATE_COMMON);

		text = getIntent().getStringExtra("text");
		title = getIntent().getStringExtra("title");
		date = getIntent().getStringExtra("date");
		description = getIntent().getStringExtra("description");
		imageUrl = getIntent().getStringExtra("imageUrl");
	}

	private void setView() {
		titlebar = (LinearLayout) findViewById(R.id.titlebar);
		titlebar_name = (TextView) findViewById(R.id.titlebar_name);
		titlebar_name.setText(text);
		titlebar_name.setTextColor(Color.BLACK);
		titlebar_back = (Button) findViewById(R.id.titlebar_back);
		titlebar_menu = (Button) findViewById(R.id.titlebar_menu);
		titlebar_back.setVisibility(View.VISIBLE);
		// titlebar_menu.setVisibility(View.VISIBLE);
		titlebar_name.setVisibility(View.VISIBLE);
		// titlebar_menu.setBackgroundResource(R.drawable.titlebtn_bg_share);
		text_title = (TextView) findViewById(R.id.title);
		text_title.setText(title);
		image = (ImageView) findViewById(R.id.image);
		image.setVisibility(View.GONE);
		if (imageUrl != null && !imageUrl.equals("")) {

			try {
				image.setVisibility(View.VISIBLE);
				if (title.equals("100")) {
					text_title.setText("");
					image.setBackgroundDrawable(new BitmapDrawable(
							(BitmapFactory.decodeStream(getResources().getAssets().open(imageUrl.substring(9))))));
				} else {
					fb.display(image, imageUrl);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// imageLoader.displayImage(imageUrl, image, options);
		}

		text_subtitle = (TextView) findViewById(R.id.subtitle);
		text_subtitle.setText(date);
		text_content = (TextView) findViewById(R.id.content);

		CharSequence charSequence = Html.fromHtml(description);
		text_content.setText(charSequence);
		text_content.setMovementMethod(LinkMovementMethod.getInstance());

		if (type == SubTabInfoTypeConstants.SUBCATE_ACTIVITY || type == SubTabInfoTypeConstants.SUBCATE_TOURIST) {
			text_title.setVisibility(View.GONE);
			text_subtitle.setVisibility(View.GONE);
		}
	}

	private void setListener() {
		titlebar_back.setOnClickListener(onClickListener);
		titlebar_menu.setOnClickListener(onClickListener);
		mDefaultListener = new DefaultListener();
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titlebar_back:
				finish();
				break;
			case R.id.titlebar_menu:
				goToShare();
				break;
			default:
				break;
			}
		}
	};

	// 分享
	protected void goToShare() {
		clientID = SocialConfig.getInstance(this).getClientId(MediaType.BAIDU);

		List<MediaType> mediaTypes = new ArrayList<MediaType>(1);
		mediaTypes.add(MediaType.SINAWEIBO);
		mediaTypes.add(MediaType.QQWEIBO);
		// mediaTypes.add(MediaType.WEIXIN);
		// mediaTypes.add(MediaType.WEIXIN_TIMELINE);
		mediaTypes.add(MediaType.EMAIL);
		mediaTypes.add(MediaType.SMS);
		mediaTypes.add(MediaType.QQFRIEND);
		SocialShareConfig.getInstance(this).setSupportedMediaTypes(mediaTypes);

		if (imageUrl != null && !imageUrl.equals("")) {
			mImageContent = new ShareContent(title, TextUtils.getWeiBoContent(description), "http://www.hbhsc.net/",
					Uri.parse(imageUrl));
			mImageContent.saveImageDataIfNecessary();
		} else {
			mImageContent = new ShareContent(title, TextUtils.getWeiBoContent(description), "http://www.hbhsc.net/");
		}

		SocialShare.getInstance(this, clientID).show(getWindow().getDecorView(), mImageContent, UIWidgetStyle.DEFAULT,
				mDefaultListener);
	}

	private ImageSize getImageSizeScaleTo(ImageView imageView) {
		DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

		LayoutParams params = imageView.getLayoutParams();
		int width = params.width; // Get layout width parameter
		if (width <= 0)
			width = displayMetrics.widthPixels;

		int height = params.height; // Get layout height parameter
		if (height <= 0)
			height = displayMetrics.heightPixels;

		return new ImageSize(width, height);
	}

	private void showAlert(final int rId, final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (!isFinishing()) {
					if (mAlertDialog == null) {
						mAlertDialog = new android.app.AlertDialog.Builder(Activity_Detail.this)
								.setPositiveButton(R.string.ok, null).create();
						mAlertDialog.setTitle(rId);
						mAlertDialog.setIcon(android.R.drawable.ic_dialog_info);
						mAlertDialog.setMessage(msg);
						mAlertDialog.show();
					} else if (mAlertDialog != null && (!mAlertDialog.isShowing())) {
						mAlertDialog.setTitle(rId);
						mAlertDialog.setMessage(msg);
						mAlertDialog.show();
					}
				}
			}
		});
	}

	private class DefaultListener implements IBaiduListener {

		public void onCancel() {
			ForumToast.show(getString(R.string.share_canceled));
		}

		public void onError(BaiduException ex) {
			showAlert(R.string.share_failed, ex.getMessage());
			ForumToast.show(getString(R.string.share_failed));
		}

		public void onComplete() {
			ForumToast.show(getString(R.string.share_completed));
		}

		public void onComplete(JSONObject arg0) {
			ForumToast.show(getString(R.string.share_completed));
		}

		public void onComplete(JSONArray arg0) {
			ForumToast.show(getString(R.string.share_completed));
		}

	}
}
