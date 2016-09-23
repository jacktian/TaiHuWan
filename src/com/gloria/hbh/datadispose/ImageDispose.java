package com.gloria.hbh.datadispose;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.data.forum.ImageInfo.ImgScaleTypeConstants;
import com.gloria.hbh.main.R;
import com.gloria.hbh.util.FileUtils;
import com.gloria.hbh.util.ImageUtils;
import com.gloria.hbh.util.ScreenUtils;
import com.gloria.hbh.util.SystemMemoryUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDispose {
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 10, 6, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());
	public static HashMap<String, Object> requestingImgUrl = new HashMap<String, Object>(); // 保存请求过的地址

	public static boolean isImgExisted(String urlString) {
		boolean isExist = false;
		String fileDir = ""; // 路径名
		String fileName = ""; // 文件名
		final String filePath; // 文件绝对路径
		fileName = urlString.substring(urlString.lastIndexOf('/') + 1);

		fileDir = BaseConstants.CACHE_IMG_PATH;
		filePath = fileDir + fileName;
		if (FileUtils.isHasSDCard()) {
			File file = new File(filePath);
			if (file.exists()) { // 文件存在
				isExist = true;
			}
		}
		return isExist;
	}

	public static boolean saveImgToPhoto(String urlString) {
		boolean isCopyed = false;
		String fileName = ""; // 文件名
		String fileDir = ""; // 源路径名
		String filePath; // 源文件绝对路径

		String newfileDir = ""; // 目的路径名
		String newfilePath; // 目的文件绝对路径
		fileName = urlString.substring(urlString.lastIndexOf('/') + 1);

		fileDir = BaseConstants.CACHE_IMG_PATH;
		if (FileUtils.isHasSDCard()) {
			filePath = fileDir + fileName;
			File file = new File(filePath);
			if (file.exists()) // 文件存在
			{
				newfileDir = BaseConstants.CACHE_SAVE_IMG_PATH;
				newfilePath = newfileDir + fileName;
				File newfile = new File(newfilePath);
				if (!newfile.getParentFile().exists()) {
					newfile.getParentFile().mkdirs();
				}
				isCopyed = FileUtils.copyfile(file, newfile, true);
			}
		}
		return isCopyed;
	}

	public static Drawable getTextImg(final String urlString, final TextView textView, final String contentString) {
		String fileDir = ""; // 路径名
		String fileName_old = ""; // 文件名
		String fileName_new = ""; // 文件名
		String filePath; // 文件绝对路径
		final String filePath_; // 文件绝对路径
		Drawable drawable;
		String newurlString = "";

		fileName_old = urlString.substring(urlString.lastIndexOf('/') + 1);
		newurlString = BaseConfig.requestImageUrl(BaseConstants.DEFAULT_FACE_WIDTH, BaseConstants.DEFAULT_FACE_WIDTH,
				urlString, ImgScaleTypeConstants.IMGTYPE_NORMAL);
		fileName_new += newurlString.replaceAll("[/|&|?|:|%]+", "_");

		drawable = BaseApplication.getInstance().getApplicationContext().getResources()
				.getDrawable(R.drawable.defaulticon);

		// 表情处理方法
		if (urlString.toLowerCase().contains(BaseConstants.FACE_IMG_CONTAIN_PATH)) {
			String rexString = "/\\w*/\\w*\\.GIF|/\\w*/\\w*\\.gif"; // 正则匹配名
			Pattern pattern = Pattern.compile(rexString);
			Matcher matcher = pattern.matcher(urlString);
			if (matcher.find()) {
				fileDir = matcher.group(); // 获得路径名,fileDir此处做临时变量
			}
			if (fileDir.startsWith("/")) {
				fileDir = fileDir.substring(1, fileDir.length());
			}
			filePath = BaseConstants.CACHE_FACE_IMG_PATH + fileDir;
			fileDir = filePath.replace(fileName_old, "");
			filePath = filePath.replace(fileName_old, fileName_new);
			filePath_ = filePath;
			if (FileUtils.isHasSDCard()) {
				File file = new File(filePath_);
				// 文件存在
				if (file.exists() && FileUtils.isImage(filePath_)) {
					if (SystemMemoryUtil.getAvailMemory(BaseApplication.getInstance().getApplicationContext()) > file
							.length()) {
						// drawable = Drawable.createFromPath(filePath);
						drawable = ImageUtils.getDrawableByPath(filePath_);
						if (drawable != null) {
							drawable.setBounds(0, 0,
									(int) (ScreenUtils.getInstance().getScaledDensity() * drawable.getIntrinsicWidth()),
									(int) (ScreenUtils.getInstance().getScaledDensity()
											* drawable.getIntrinsicHeight()));
						}
					}
					return drawable;
				}

				final Handler handler = new Handler() {
					public void handleMessage(Message message) {
						Boolean state = (Boolean) message.obj;
						if (state && textView != null && contentString != null) {
							textView.setText(Html.fromHtml(contentString, new ImageGetter() {
								public Drawable getDrawable(String source) {
									Drawable drawable = null;
									drawable = ImageDispose.getTextImg(source, textView, contentString);
									return drawable;
								}
							}, null));
						}
					}
				};

				threadPool.execute(new Runnable() {
					public void run() {
						Message message = handler.obtainMessage(0, false);
						if (ImgGetSave(urlString, filePath_, BaseConstants.DEFAULT_FACE_WIDTH,
								BaseConstants.DEFAULT_FACE_WIDTH, ImgScaleTypeConstants.IMGTYPE_NORMAL)) {
							message = handler.obtainMessage(0, true);
						}
						handler.sendMessage(message);
					}
				});
			}
		}
		return drawable;
	}

	public static Boolean ImgGetSave(String urlString, String pathString, int width, int height, int type) {
		URL url_;
		if (!requestingImgUrl.containsKey(urlString)) {
			requestingImgUrl.put(urlString, null);
			try {
				String newurlString = BaseConfig.requestImageUrl(width, height, urlString, type);
				url_ = new URL(newurlString);
				HttpURLConnection conn_ = (HttpURLConnection) url_.openConnection();
				conn_.setConnectTimeout(BaseConstants.TimeoutConnection);
				if (conn_.getResponseCode() == BaseConstants.StatusCode_OK) {
					InputStream inStream = conn_.getInputStream();
					if (FileUtils.isHasSDCard()) {
						// 不进系统相册
						File file = new File(BaseConstants.NOMEDIA_PATH);
						if (!file.exists()) {
							file.mkdirs();
						}
						File tmpfile = new File(pathString + ".dat");
						if (!tmpfile.getParentFile().exists()) {
							tmpfile.getParentFile().mkdirs();
						}
						FileOutputStream outStream = new FileOutputStream(tmpfile);
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = inStream.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);
						}
						outStream.flush();
						outStream.close();
						inStream.close();
						tmpfile.renameTo(new File(pathString));
						return true;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static void loadDrawable(final String urlString, final ImageView imageView, final int defaultDrawable,
			final int width, final int height, final int scale) {
		imageView.setImageResource(defaultDrawable);
		String fileDir = ""; // 路径名
		String fileName = ""; // 文件名
		final String filePath; // 文件绝对路径
		String newurlString = "";

		if (urlString.equals("")) { // 图片地址为空时
			return;
		} else { // 图片地址不为空时
			newurlString = BaseConfig.requestImageUrl(width, height, urlString, scale);

			fileName += newurlString.replaceAll("[/|&|?|:|%]+", "_");

			fileDir = BaseConstants.CACHE_IMG_PATH;
			filePath = fileDir + fileName;

			if (FileUtils.isHasSDCard()) {
				File file = new File(filePath);
				// 文件存在
				if (file.exists()) {
					// if(Metheds.isImage(filePath)){
					// 如果内存大于要显示的图片时，才显示
					if (SystemMemoryUtil.getAvailMemory(BaseApplication.getInstance().getApplicationContext()) > file
							.length()) {
						Bitmap bitmap = BitmapFactory.decodeFile(filePath);
						if (bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
					// }
					return;
				}

				final Handler handler = new Handler() {
					public void handleMessage(Message message) {
						Bitmap bitmap = null;
						Boolean state = (Boolean) message.obj;
						if (state) {
							try {
								bitmap = BitmapFactory.decodeFile(filePath);
							} catch (Exception e) {
							}
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						}
					}
				};

				threadPool.execute(new Runnable() {
					public void run() {
						Message message;
						if (ImgGetSave(urlString, filePath, width, height, scale)) {
							message = handler.obtainMessage(0, true);// inStream);
						} else {
							message = handler.obtainMessage(0, false);
						}
						handler.sendMessage(message);
					}
				});
			}
		}
	}
}