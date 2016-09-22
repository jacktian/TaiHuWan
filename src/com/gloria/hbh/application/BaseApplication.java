package com.gloria.hbh.application;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import cn.jpush.android.api.JPushInterface;

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
* �� �� �� : BaseApplication
* �� �� �ˣ� gejian
* ��     �ڣ�2012-9-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-9-7
* ��    ����Application���Զ���
*/

public   class BaseApplication extends Application {   
	  
	private List<Activity> activityList = new LinkedList<Activity>();   
    private static BaseApplication instance;   
    
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
    public static final String strKey = "Ppuv5aL6Ohv7NxATZlGLtUQI";
//    public static final String strKey = "4481390def8086e7da9b1c778119a2e6";

 //	private final static int CWJ_HEAP_SIZE =20* 1024* 1024 ;
//	private final static float floatTARGET_HEAP_UTILIZATION = 0.75f;  
    
	public void onCreate() {
//    	ACRA.init(this);  
//		MySender mySender=new MySender();
//		ErrorReporter.getInstance().setReportSender(mySender);
//		ErrorReporter.getInstance().setReportSender(new IOReportSender(this));
//		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); //������Сheap�ڴ�Ϊ10MB��С����Ȼ�����ڴ�Խ���˵������ͨ���ֶ�����GCȥ����
//		VMRuntime.getRuntime().setTargetHeapUtilization(floatTARGET_HEAP_UTILIZATION); 
		super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true); 	//���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);     		// ��ʼ�� JPush
        initEngineManager(this);
        // This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.imageDownloader(new BaseImageDownloader(getApplicationContext()))
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.enableLogging() // Not necessary in common
		.build();
		
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		Plist.getInstance();
		
		// ����AppKey�Լ�ppChannel
     	StatService.setAppKey("7e54332dfd");//appkey������mtj��վ��ע�����ɣ�������Ҳ������AndroidManifest.xml����д
     	StatService.setAppChannel("Baidu Market");//appChannel��Ӧ�õķ�������������Ҫ��mtj��վ��ע�ᣬֱ����д�Ϳ���
     	StatService.setSessionTimeOut(30);//����ʱ������ʹ��1����session���ڣ��������ϵ������˳������������־��
     	// setOnҲ������AndroidManifest.xml�ļ�����д��BaiduMobAd_EXCEPTION_LOG
     	StatService.setOn(this,StatService.EXCEPTION_LOG);
     	/*
 		 * ��������ʱ��־������ʱ������<br/> ��λΪ�룬��СΪ0s��30s֮��<br/>
 		 * ע������StatService.setSendLogStrategy֮ǰ���ã��������ò�������
 		 * 
 		 * ������õ��Ƿ��Ͳ���������ʱ���ͣ���ô��������ͻ��ڷ���ǰ��������õ������������ʾ�ӳٶ���S���͡�<br/>
 		 * ���������������ʱֻ֧�ִ�����룬 �������׸�������Activity�е�onCreate������ʹ�þͿ��ԡ�<br/>
 		 */
     	StatService.setLogSenderDelayed(10);
     	/*
 		 * ����������־���Ͳ���<br /> Ƕ��λ�ã�Activity��onCreate()������ <br />
 		 * 
 		 * ���÷�ʽ��StatService.setSendLogStrategy(this,SendStrategyEnum.
 		 * SET_TIME_INTERVAL, 1, false);
 		 * �ڶ���������ѡ�� SendStrategyEnum.APP_START SendStrategyEnum.ONCE_A_DAY
 		 * SendStrategyEnum.SET_TIME_INTERVAL 
 		 * ������������
 		 * ��������ڵڶ�������ѡ��SendStrategyEnum.SET_TIME_INTERVALʱ��Ч��
 		 * ȡֵ��Ϊ1-24֮�������,��1<=rtime_interval<=24����СʱΪ��λ
 		 * ���ĸ�������
 		 * ��ʾ�Ƿ��֧��wifi����־���ͣ���Ϊtrue����ʾ����wifi�����·�����־����Ϊfalse����ʾ�������κ����������·�����־
 		 */
     	StatService.setSendLogStrategy(this,SendStrategyEnum.APP_START, 1,false);
    }
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
        	ForumToast.show("BMapManager  ��ʼ������!");
        }
	}
	
	// �����¼���������������ͨ�������������Ȩ��֤�����
    public static class MyGeneralListener implements MKGeneralListener {
        
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
            	ForumToast.show("���������������");
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
            	ForumToast.show( "������ȷ�ļ���������");
            }
        }

        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //��ȨKey����
//            	ForumToast.show("��������ȷ����ȨKey��");
            	getInstance().m_bKeyRight = false;
            }
        }
    }
	
    /*
     * ����ģʽ�л�ȡΨһ��ExitApplicationʵ��   
     */
    public static BaseApplication getInstance()   {   
    	if(null == instance){   
    		instance = new BaseApplication();   
    	}   
    	return instance;    
    }  
    
    //���Activity��������   
    public void addActivity(Activity activity)   {   
    	activityList.add(activity); 
    }  
    
	//Activity����finish   
    public void exitForLog()   { 
    	ShareCookie.clearCookie();
    	for(Activity activity:activityList)   { 
    		activity.finish();   
    	} 
    }
    
    //��������Activity��finish   
    public void exit()   { 
    	//�˳�
		ShareCookie.clearCookie();
		for(Activity activity:activityList)   { 
    		activity.finish();   
    	} 
//		android.os.Process.killProcess(android.os.Process.myPid());    
    	System.exit(0); 
//    	new LogoutRequestTask().execute();
    }  
}  

