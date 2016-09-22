package com.gloria.hbh.constant;

import java.io.File;

import android.os.Environment;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.main.R;

public class BaseConstants {
	
	public static final String PUSH_TITLE = "֪ͨ";
	
	public static String Save_RootFile = BaseApplication.getInstance()
			.getString(R.string.app_name);   //Ӧ�õĴ�ŵ��ļ���
	
	public static String Settings_NAME = "com.gloria."+Save_RootFile+".settings";  //�����е�����
	public static String Function_NAME = "com.gloria."+Save_RootFile+".function";  //���͵ȹ�������
	public static String WeiBoShare_NAME = "com.gloria."+Save_RootFile+".weiboshare";  //΢������������
	
	public static String AccountManager_NAME = "com.gloria."+Save_RootFile+".accountmanager";
	public static String UPDATE_NAME = "com.gloria."+Save_RootFile+".update"; //listView��ˢ��ʱ��
	public static String LastViews_NAME = "com.gloria."+Save_RootFile+".lastviews";  
	public static String PushInfoServiceName = "com.gloria."+Save_RootFile+".pushinfo.Push_Service"; 
	public static String Settings_Auther = "com.gloria."+Save_RootFile+".auther";
	
	//SharedPreferences��key
	public static final String SharedPreferences_isInstallShortCut = "isInstallShortCut";    //�Ƿ��������ͼ���key����
	public static final String SharedPreferences_isRegisterPhone = "isRegisterPhone";    //�Ƿ�ע�����͵�key����
	public static final String SharedPreferences_isFirstOpenSoft = "isFirstOpenSoft";//�Ƿ��һ�δ������key����
	public static final String SharedPreferences_LastViews = "LastViews";    //��������key����
	public static final String SharedPreferences_LastLogin = "LastLogin";    //�Զ���¼��key����
	public static final String SharedPreferences_LoginInfo= "LoginInfo";  //��¼���û�����������ʺ��б�� key���� 
	public static final String SharedPreferences_WeiBoShareInfo = "WeiBoShareInfo";  //����� key���� 
	public static final String SharedPreferences_Settings = "Settings";    //Ӧ�ø�������õ�key����
	public static final String SharedPreferences_Settings_Default = "Settings_default";    //Ӧ�ø����Ĭ�����õ�key����
	public static final String SharedPreferences_StartPic = "StartPic";    //����ҳ�����õ�key����
	
	//Ӧ�õĴ洢SD�����ļ��е�ַ
	public static String CACHE_FILE_PATH =  Environment.getExternalStorageDirectory()+File.separator+Save_RootFile+File.separator+"cache/filecache/";
	public static String CACHE_IMG_PATH =  Environment.getExternalStorageDirectory() + File.separator+Save_RootFile+File.separator+"cache/imgcache/";		
	public static String CACHE_IMG_TEMP_PATH =  Environment.getExternalStorageDirectory()+ File.separator+Save_RootFile+File.separator+"cache/uploadimg/";
	public static String CACHE_SAVE_IMG_PATH = Environment.getExternalStorageDirectory() + File.separator+Save_RootFile+File.separator+"Imgs/";  //��������·��
	public static String CACHE_FACE_IMG_PATH = Environment.getExternalStorageDirectory() + File.separator+Save_RootFile+File.separator+"cache/face/";
	public static String NOMEDIA_PATH =Environment.getExternalStorageDirectory().getPath()+File.separator+Save_RootFile+File.separator+"cache/.nomedia/";

	public static String FACE_IMG_CONTAIN_PATH = "images/post/smile/";
	public static int DEFAULT_FACE_WIDTH = 80;
	public static int DEFAULT_AVATER_WIDTH = 80;
	public static int DEFAULT_LOG_AVATER_WIDTH = 150;

	//�ļ��洢����
	public static final int CACHE_FILETYPE_FILE = 0;        //�ļ�
	public static final int CACHE_FILETYPE_FACE_IMG = 1;    //����
	public static final int CACHE_FILETYPE_SAVE_IMG = 2;    //�������ͼƬ
	public static final int CACHE_FILETYPE_IMG = 3;         //һ��ͼƬ
	public static final int CACHE_FILETYPE_IMG_SCALESMALL = 4;         //��СͼƬ
	public static final int CACHE_FILETYPE_IMG_SCALEBIG = 5;         //�Ŵ�ͼƬ
	
	//��������
	public static final int TimeoutConnection = 10*1000;    //���ӳ�ʱʱ��
	public static final int TimeoutSocket = 10*1000;    //Socket��ʱʱ��
	
	public static final int Time_DELAY = 30*1000;    //�Ƴٿ�ʼʱ��
	public static final int Time_PERIOD = 30*1000;    //ѭ������ʱ��
	
	public static final int StatusCode_OK = 200;    //StatusCode 200����

	//�����б�����
	public static final int PostList_All = 0; //Ĭ��ȫ��
	public static final int PostList_Top = 1;  //�ö�
	public static final int PostList_Digest = 2; //����
	public static final int ColumnList = 3; //��Ŀ�б�  �Ȱ�����
	
	public static final String TabType_More_ID = "-100";  //����tab��FID
	
	public static final String TAG_NAVLIST = "navList"; //�����б�
	public static final String TAG_MORELIST = "moreList"; //�����б�
	public static final String TAG_PostListType = "PostListType"; //Ĭ��ȫ��
	
	public static final int TAG_ADAPTER_COLUMNLIST = 0; //��Ŀ�б� 

	public static final int Count_SubTabType = 6; //����Ŀ¼��tab����
	public static final int COUNT_HeadLine = 5; //ͷ����ͼ�ĸ���
	
//	public static final long FILE_IMAGE_MAXSIZE = 500*1024; //ͼƬ�ϴ��ļ�������С
	public static final long FILE_IMAGE_MAXSIZE = 200*1024; //ͼƬ�ϴ��ļ�������С
	
	//Activity��ResultCode
	public static int RESULT_TEXTSIZE = 10; //�ֺ����õķ���Code
	public static int RESULT_PAGECOUNT = 40; //ÿҳ�����������õķ���Code
	public static int RESULT_THEMESHIN = 50; //Ƥ�����õķ���Code
	public static int RESULT_COMMENT_DEFAULT = 20; //����  ����Ĭ��
	public static int RESULT_COMMENT_PARENT = 30; //����  ����@ĳ��
	//QQ��Ȩ�ķ���
	public static int RESULT_QQ = 40; //���� QQ��Ȩ��Ϣ
	
	//ͼƬѹ����ʽ
	public static int IMGTYPE_INYERCEPT = 0;  //����
	public static int IMGTYPE_SCALE = 1;  //��ȡ
	public static int IMGTYPE_HOME = 2;  //��ҳ�Ŵ�
	public static int IMGTYPE_NORMAL = 3;  //ԭͼ

	//Բ�Ǵ���
	public static final float DEFAULT_ROUNDED = (float) 10.0;
	public static final float DEFAULT_AVATER = (float) 30.0;

	public static final boolean isCancelable = true;

	//��������ͼ
	public static int[] newFunctionHintPics = {};
}
