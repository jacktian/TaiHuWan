package com.gloria.hbh.application;

import java.util.LinkedList;
import java.util.List;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.myview.ForumToast;
import com.gloria.hbh.sharecookies.ShareCookie;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import cn.jpush.android.api.JPushInterface;

//@ReportsCrashes(formKey = "",
//		customReportContent = { 
//			ReportField.BRAND,
//			ReportField.APP_VERSION_NAME, 
//			ReportField.ANDROID_VERSION,
//			ReportField.PHONE_MODEL,
//			ReportField.LOGCAT
//		},
//		mode = ReportingInteractionMode.TOAST,
//		resToastText = R.string.crash_toast_text)

//@ReportsCrashes(
//		formKey = "",
//		mailTo = "gejian311@sina.com",
//		customReportContent = { 
//		ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE, 
//		ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, 
//		ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
//		mode = ReportingInteractionMode.TOAST,
//		forceCloseDialogAfterToast = false,
//		resToastText = R.string.crash_toast_text)

/**
 * 文 件 名 : BaseApplication 创 建 人： gejian 日 期：2012-9-7 修 改 人：gejian 日 期：2012-9-7
 * 描 述：Application的自定义
 */

public class BaseApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static BaseApplication instance;

	public boolean m_bKeyRight = true;
	public BMapManager mBMapManager = null;
	public static final String strKey = "Ppuv5aL6Ohv7NxATZlGLtUQI";
	// public static final String strKey = "4481390def8086e7da9b1c778119a2e6";

	// private final static int CWJ_HEAP_SIZE =20* 1024* 1024 ;
	// private final static float floatTARGET_HEAP_UTILIZATION = 0.75f;

	public void onCreate() {
		// ACRA.init(this);
		// MySender mySender=new MySender();
		// ErrorReporter.getInstance().setReportSender(mySender);
		// ErrorReporter.getInstance().setReportSender(new
		// IOReportSender(this));
		// VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		// //设置最小heap内存为10MB大小。当然对于内存吃紧来说还可以通过手动干涉GC去处理
		// VMRuntime.getRuntime().setTargetHeapUtilization(floatTARGET_HEAP_UTILIZATION);
		super.onCreate();
		instance = this;
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
		initEngineManager(this);
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2).memoryCacheSize(2 * 1024 * 1024) // 2
																							// Mb
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.imageDownloader(new BaseImageDownloader(getApplicationContext()))
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() // Not
																				// necessary
																				// in
																				// common
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		Plist.getInstance();

		// 设置AppKey以及ppChannel
		StatService.setAppKey("7e54332dfd");// appkey必须在mtj网站上注册生成，该设置也可以在AndroidManifest.xml中填写
		StatService.setAppChannel("Baidu Market");// appChannel是应用的发布渠道，不需要在mtj网站上注册，直接填写就可以
		StatService.setSessionTimeOut(30);// 测试时，可以使用1秒钟session过期，这样不断的启动退出会产生大量日志。
		// setOn也可以在AndroidManifest.xml文件中填写，BaiduMobAd_EXCEPTION_LOG
		StatService.setOn(this, StatService.EXCEPTION_LOG);
		/*
		 * 设置启动时日志发送延时的秒数<br/> 单位为秒，大小为0s到30s之间<br/>
		 * 注：请在StatService.setSendLogStrategy之前调用，否则设置不起作用
		 * 
		 * 如果设置的是发送策略是启动时发送，那么这个参数就会在发送前检查您设置的这个参数，表示延迟多少S发送。<br/>
		 * 这个参数的设置暂时只支持代码加入， 在您的首个启动的Activity中的onCreate函数中使用就可以。<br/>
		 */
		StatService.setLogSenderDelayed(10);
		/*
		 * 用于设置日志发送策略<br /> 嵌入位置：Activity的onCreate()函数中 <br />
		 * 
		 * 调用方式：StatService.setSendLogStrategy(this,SendStrategyEnum.
		 * SET_TIME_INTERVAL, 1, false); 第二个参数可选： SendStrategyEnum.APP_START
		 * SendStrategyEnum.ONCE_A_DAY SendStrategyEnum.SET_TIME_INTERVAL 第三个参数：
		 * 这个参数在第二个参数选择SendStrategyEnum.SET_TIME_INTERVAL时生效、
		 * 取值。为1-24之间的整数,即1<=rtime_interval<=24，以小时为单位 第四个参数：
		 * 表示是否仅支持wifi下日志发送，若为true，表示仅在wifi环境下发送日志；若为false，表示可以在任何联网环境下发送日志
		 */
		StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			ForumToast.show("BMapManager  初始化错误!");
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				ForumToast.show("您的网络出错啦！");
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				ForumToast.show("输入正确的检索条件！");
			}
		}

		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				// ForumToast.show("请输入正确的授权Key！");
				getInstance().m_bKeyRight = false;
			}
		}
	}

	/*
	 * 单例模式中获取唯一的ExitApplication实例
	 */
	public static BaseApplication getInstance() {
		if (null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// Activity结束finish
	public void exitForLog() {
		ShareCookie.clearCookie();
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	// 遍历所有Activity并finish
	public void exit() {
		// 退出
		ShareCookie.clearCookie();
		for (Activity activity : activityList) {
			activity.finish();
		}
		// android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
		// new LogoutRequestTask().execute();
	}
}
