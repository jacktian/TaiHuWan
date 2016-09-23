package com.gloria.hbh.constant;

import java.io.File;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.main.R;

import android.os.Environment;

public class BaseConstants {

	public static final String PUSH_TITLE = "通知";

	public static String Save_RootFile = BaseApplication.getInstance().getString(R.string.app_name); // 应用的存放的文件夹

	public static String Settings_NAME = "com.gloria." + Save_RootFile + ".settings"; // 更多中的配置
	public static String Function_NAME = "com.gloria." + Save_RootFile + ".function"; // 推送等功能配置
	public static String WeiBoShare_NAME = "com.gloria." + Save_RootFile + ".weiboshare"; // 微博分享功能配置

	public static String AccountManager_NAME = "com.gloria." + Save_RootFile + ".accountmanager";
	public static String UPDATE_NAME = "com.gloria." + Save_RootFile + ".update"; // listView的刷新时间
	public static String LastViews_NAME = "com.gloria." + Save_RootFile + ".lastviews";
	public static String PushInfoServiceName = "com.gloria." + Save_RootFile + ".pushinfo.Push_Service";
	public static String Settings_Auther = "com.gloria." + Save_RootFile + ".auther";

	// SharedPreferences的key
	public static final String SharedPreferences_isInstallShortCut = "isInstallShortCut"; // 是否添加桌面图标的key索引
	public static final String SharedPreferences_isRegisterPhone = "isRegisterPhone"; // 是否注册推送的key索引
	public static final String SharedPreferences_isFirstOpenSoft = "isFirstOpenSoft";// 是否第一次打开软件的key索引
	public static final String SharedPreferences_LastViews = "LastViews"; // 最近浏览的key索引
	public static final String SharedPreferences_LastLogin = "LastLogin"; // 自动登录的key索引
	public static final String SharedPreferences_LoginInfo = "LoginInfo"; // 登录的用户名，密码等帐号列表的
																			// key索引
	public static final String SharedPreferences_WeiBoShareInfo = "WeiBoShareInfo"; // 分享的
																					// key索引
	public static final String SharedPreferences_Settings = "Settings"; // 应用更多的配置的key索引
	public static final String SharedPreferences_Settings_Default = "Settings_default"; // 应用更多的默认配置的key索引
	public static final String SharedPreferences_StartPic = "StartPic"; // 启动页的配置的key索引

	// 应用的存储SD卡的文件夹地址
	public static String CACHE_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + Save_RootFile
			+ File.separator + "cache/filecache/";
	public static String CACHE_IMG_PATH = Environment.getExternalStorageDirectory() + File.separator + Save_RootFile
			+ File.separator + "cache/imgcache/";
	public static String CACHE_IMG_TEMP_PATH = Environment.getExternalStorageDirectory() + File.separator
			+ Save_RootFile + File.separator + "cache/uploadimg/";
	public static String CACHE_SAVE_IMG_PATH = Environment.getExternalStorageDirectory() + File.separator
			+ Save_RootFile + File.separator + "Imgs/"; // 保存相册的路径
	public static String CACHE_FACE_IMG_PATH = Environment.getExternalStorageDirectory() + File.separator
			+ Save_RootFile + File.separator + "cache/face/";
	public static String NOMEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator
			+ Save_RootFile + File.separator + "cache/.nomedia/";

	public static String FACE_IMG_CONTAIN_PATH = "images/post/smile/";
	public static int DEFAULT_FACE_WIDTH = 80;
	public static int DEFAULT_AVATER_WIDTH = 80;
	public static int DEFAULT_LOG_AVATER_WIDTH = 150;

	// 文件存储类型
	public static final int CACHE_FILETYPE_FILE = 0; // 文件
	public static final int CACHE_FILETYPE_FACE_IMG = 1; // 表情
	public static final int CACHE_FILETYPE_SAVE_IMG = 2; // 保存相册图片
	public static final int CACHE_FILETYPE_IMG = 3; // 一般图片
	public static final int CACHE_FILETYPE_IMG_SCALESMALL = 4; // 缩小图片
	public static final int CACHE_FILETYPE_IMG_SCALEBIG = 5; // 放大图片

	// 网络连接
	public static final int TimeoutConnection = 10 * 1000; // 连接超时时间
	public static final int TimeoutSocket = 10 * 1000; // Socket超时时间

	public static final int Time_DELAY = 30 * 1000; // 推迟开始时间
	public static final int Time_PERIOD = 30 * 1000; // 循环连接时间

	public static final int StatusCode_OK = 200; // StatusCode 200正常

	// 帖子列表类型
	public static final int PostList_All = 0; // 默认全部
	public static final int PostList_Top = 1; // 置顶
	public static final int PostList_Digest = 2; // 精华
	public static final int ColumnList = 3; // 栏目列表 热版类似

	public static final String TabType_More_ID = "-100"; // 更多tab的FID

	public static final String TAG_NAVLIST = "navList"; // 导航列表
	public static final String TAG_MORELIST = "moreList"; // 更多列表
	public static final String TAG_PostListType = "PostListType"; // 默认全部

	public static final int TAG_ADAPTER_COLUMNLIST = 0; // 栏目列表

	public static final int Count_SubTabType = 6; // 二级目录的tab个数
	public static final int COUNT_HeadLine = 5; // 头条大图的个数

	// public static final long FILE_IMAGE_MAXSIZE = 500*1024; //图片上传文件的最大大小
	public static final long FILE_IMAGE_MAXSIZE = 200 * 1024; // 图片上传文件的最大大小

	// Activity的ResultCode
	public static int RESULT_TEXTSIZE = 10; // 字号设置的返回Code
	public static int RESULT_PAGECOUNT = 40; // 每页加载条数设置的返回Code
	public static int RESULT_THEMESHIN = 50; // 皮肤设置的返回Code
	public static int RESULT_COMMENT_DEFAULT = 20; // 返回 评论默认
	public static int RESULT_COMMENT_PARENT = 30; // 返回 评论@某人
	// QQ授权的返回
	public static int RESULT_QQ = 40; // 返回 QQ授权信息

	// 图片压缩方式
	public static int IMGTYPE_INYERCEPT = 0; // 缩放
	public static int IMGTYPE_SCALE = 1; // 截取
	public static int IMGTYPE_HOME = 2; // 首页放大
	public static int IMGTYPE_NORMAL = 3; // 原图

	// 圆角代销
	public static final float DEFAULT_ROUNDED = (float) 10.0;
	public static final float DEFAULT_AVATER = (float) 30.0;

	public static final boolean isCancelable = true;

	// 功能引导图
	public static int[] newFunctionHintPics = {};
}
