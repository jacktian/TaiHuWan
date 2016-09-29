package com.gloria.hbh.application;

import java.util.LinkedList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.sharecookies.ShareCookie;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.yolanda.nohttp.NoHttp;

import android.app.Activity;
import android.app.Application;
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
		NoHttp.initialize(this);
		JPushInterface.init(this); // 初始化 JPush
	    SDKInitializer.initialize(this);  
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
