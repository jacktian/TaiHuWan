package com.gloria.hbh.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;

import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.constant.BaseConfig;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.data.app.SubTabInfo.SubTabInfoTypeConstants;
import com.gloria.hbh.data.forum.HandlinesBasicInfo;
import com.gloria.hbh.data.forum.PostInfo;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.ForumToast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
* 文 件 名 : Methods
* 创 建 人： gejian
* 日     期：2012-8-7
* 修 改 人：gejian
* 日    期：2012-8-7
* 描    述：程序的方法类
*/
@TargetApi(5)
public class Methods {
	
	/*
	 * 判断是否已添加桌面图标
	 */
	public static  void checkShortCut() {
		boolean isInstallShortCut =  PreferencesUtils.getBooleanPreferences(BaseConstants.Settings_NAME, 
				BaseConstants.SharedPreferences_isInstallShortCut, false);
		// 存在快捷方式或者不允许添加，return
		if (isInstallShortCut)
		  return;
		addShortcut();
		//保存配置
		PreferencesUtils.setBooleanPreferences(BaseConstants.Settings_NAME, 
				BaseConstants.SharedPreferences_isInstallShortCut, true);
	}
	
	/*
	 * 添加桌面图标
	 */
	public static void addShortcut(){ 
		Context c = BaseApplication.getInstance().getApplicationContext();
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); 
		// 快捷方式的名称 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,c. getResources().getString(R.string.app_name)); 
		// 不允许重复创建 
		shortcut.putExtra("duplicate", false); 
		String action = "com.android.action.install"; 
		Intent respondIntent = new Intent(c, c.getClass()); 
		respondIntent.setAction(action); 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, respondIntent); 
		// 快捷方式的图标 
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(c, R.drawable.ic_launcher); 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes); 

		c.sendBroadcast(shortcut); 
	}
	
	 //获取本地Ip
    public static String getLocalIpAddress() {  
    	try {  
    		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
    			NetworkInterface intf = en.nextElement();  
    			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
    				InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()) {  
    	                   return inetAddress.getHostAddress().toString();  
    	               }  
    	            }  
    	        }  
    	    } catch (SocketException ex) {  
       }  
    return null;  
    }  
    
//    //通过Service的类名来判断是否启动某个服务  
//    public static boolean IsStartPushService(Context context,String className){  
//    	ActivityManager myManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//    	ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
//    	for(int i = 0 ; i<runningService.size();i++){
//    		if(runningService.get(i).service.getClassName().toString().equals(className)){
//    			return true;
//    		}
//    	}
//    	return false;
//    }
    
    //返回11--30 10：20这样的日期格式
    public static String getDate(){  
    	Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
    	t.setToNow(); // 取得系统时间。
    	int month = t.month;
    	int day = t.monthDay;
    	int hour = t.hour;    // 0-23
    	int minute = t.minute;
    	DecimalFormat   df   =   new   DecimalFormat( "00");
    	return "更新于:  "+df.format(month)+"-"+df.format(day)+" "+df.format(hour)+":"+df.format(minute);
    }
    
	/*
	 * 获取String里面的<img> 的url列表
	 */
	public static  ArrayList<String> getContentPicUrlList(String contentstring){
		String tmpString=contentstring;
		ArrayList<String> picurl_list = new  ArrayList<String>(1);
				
		Pattern pattern1 = Pattern.compile("(onclick+=\".*?\")|(onload+=\".*?\")|(style+=\".*?\")|(border+=\".*?\")",Pattern.CASE_INSENSITIVE);   
		Matcher matcher1=pattern1.matcher(contentstring);   //找到<img> 标签中的非src的属性置空
		while (matcher1.find()){
			String imgTmpString=matcher1.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
			
		Pattern p=Pattern.compile("(?<=src=['\"])http://((?!/post/smile/).)*?(?=['\"])",Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(tmpString);  //找到非表情<img>标签中的url放到list中
		while (m.find()) {
			String imgTmpString=m.group();
			if(!picurl_list.contains(imgTmpString)){
				if(imgTmpString.contains("commonapi/images/back.gif")){
					continue;
				}
				picurl_list.add(imgTmpString);
			}
		}
		
		return picurl_list;
	}   
	
	/*
	 * 设置新的String
	 * 待解决 表情替换
	 */
	public static String  setNewContent(String contentstring){         //替换img标签   
		String tmpString=contentstring;
		Pattern pattern1 = Pattern.compile("(onclick+=\".*?\")|(onload+=\".*?\")|(style+=\".*?\")|(border+=\".*?\")|(smilieid+=\".*?\")|(alt+=\".*?\")",Pattern.CASE_INSENSITIVE);   
		Matcher matcher1=pattern1.matcher(contentstring);   //找到<img> 标签中的非src的属性置空
		while (matcher1.find()){
			String imgTmpString=matcher1.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
				
		Pattern pattern2 = Pattern.compile("<img[^<>]+src=['\"]http://[^<>]+>",Pattern.CASE_INSENSITIVE);    //匹配带有<img src="http://     >
		Matcher matcher2=pattern2.matcher(tmpString);   //找到非表情<img>标签置空
		while (matcher2.find()){
			String imgTmpString=matcher2.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
				
		//过滤<p dir=ltr>
		tmpString = tmpString.replaceAll("<p dir=ltr>", "");
				
		//替换表情  （对于表情的表达方式）
		tmpString = tmpString.replace(BaseConstants.FACE_IMG_CONTAIN_PATH,BaseConfig.url+BaseConstants.FACE_IMG_CONTAIN_PATH);  //替换表情的img标签   
		
//		tmpString = tmpString.replace("<img src=\"","<img src='");  
//		tmpString = tmpString.replace("\"    />","'    />");  
		return tmpString;
	}  
	
//	//替换@标签   
//	public static String  setNewContentOfWeiBo(String contentstring){         
//		String tmpString=contentstring;
//		//替换//@标签   
//		Pattern pattern1 = Pattern.compile("(//\\u005Bat\\u005D@(.+?)\\u005B/at\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher1=pattern1.matcher(contentstring);   //找到<img> 标签中的非src的属性置空
//		while (matcher1.find()){
//			String imgTmpString_old = matcher1.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[at]", "").replace("[/at]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//替换[wurl][/wurl]标签   
//		Pattern pattern2 = Pattern.compile("(\\u005Bwurl\\u005D(.+?)\\u005B/wurl\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher2=pattern2.matcher(tmpString);   //找到非表情<img>标签置空
//		while (matcher2.find()){
//			String imgTmpString_old = matcher2.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[wurl]", "").replace("[/wurl]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//替换##标签   
//		Pattern pattern4 = Pattern.compile("(#(.+?)#)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher4=pattern4.matcher(tmpString);   //找到非表情<img>标签置空
//		while (matcher4.find()){
//			String imgTmpString_old = matcher4.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_old+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		ArrayList<ImageInfo> faceInfos = null;
//		faceInfos = DataDispose.getWeiBoFaceTable(ExitApplication.getInstance().getApplicationContext());
//		//替换表情的图标：
//		Pattern pattern3 = Pattern.compile("(:em(\\d{1,4}):)",Pattern.CASE_INSENSITIVE);    //匹配带有<img src="http://     >
//		Matcher matcher3 = pattern3.matcher(tmpString);   //找到非表情<img>标签置空
//		while (matcher3.find()){
//			String imgTmpString=matcher3.group();
//			tmpString=tmpString.replace(imgTmpString, "<img src='"+getFacePath(imgTmpString,faceInfos)+"'/>");
//		}
//			
//		//替换@标签   
//		Pattern pattern5 = Pattern.compile("(\\u005Bat\\u005D@(.+?)\\u005B/at\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher5=pattern5.matcher(contentstring);   //找到<img> 标签中的非src的属性置空
//		while (matcher5.find()){
//			String imgTmpString_old = matcher5.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[at]", "").replace("[/at]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//过滤<p dir=ltr>
//		tmpString = tmpString.replaceAll("<p dir=ltr>", "");
//			
////		tmpString = tmpString.replace("static/image/smiley/", GlobalSource.url+"static/image/smiley/");  //替换表情的img标签   
//		return tmpString;
//	}  
	
//	/*
//	 * 由表情图片的Code获取图片路径
//	 */
//	public static String  getFacePath(String code,ArrayList<ImageInfo> faceInfos){  
//		 String path = "";
//		 for(ImageInfo faceInfo :faceInfos){
//			 if(faceInfo.getCode().equals(code)){
//				 path = faceInfo.getPath();
//				 return path;
//			 }
//		 }
//		 return  path;
//	  }
		    
//	//分享
//	public static void sharePost(String text,Context context){
//		Intent it = new Intent(Intent.ACTION_SEND);
//		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		it.putExtra(Intent.EXTRA_TEXT, text);
//		it.setType("text/plain");
//	    Intent newIntent = Intent.createChooser(it, "请选择");
//		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(newIntent);
//	}
		    
//	//首页tab字体换行大小
//	public static SpannableString changeFontSize(int screenWidth,String tmpString){
//		SpannableString ss=new SpannableString(tmpString); 
//		if(screenWidth == 480){
//			ss.setSpan(new AbsoluteSizeSpan(22), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		    ss.setSpan(new AbsoluteSizeSpan(11), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}else if(screenWidth == 320){
//		    ss.setSpan(new AbsoluteSizeSpan(21), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		    ss.setSpan(new AbsoluteSizeSpan(10), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}else if(screenWidth ==240){
//		    ss.setSpan(new AbsoluteSizeSpan(14), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		    ss.setSpan(new AbsoluteSizeSpan(8), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}else{
//		    ss.setSpan(new AbsoluteSizeSpan(21), 0, tmpString.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		    ss.setSpan(new AbsoluteSizeSpan(10), tmpString.indexOf("\n"), tmpString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		return ss;
//	}
		    
		    
	//预处理异常数据
	public static boolean disposeDataException(Object result){
		boolean isReturn = false;
		if(!NetworkUtils.isNetworkAvailable()){
			ForumToast.show(BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.hint_NoNetwork));
		    isReturn = true;
		}else if(NetworkUtils.isNetworkAvailable() && result == null){
			ForumToast.show(BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.hint_NetworkException));
		    isReturn = true;
		}
		return isReturn;
	}
	
//	//获取图片类型
//	public static int getTypeOfImage(String result){
//		int type = 1; //0:表情   1：一般图片
//		//......................
//		return type;
//	}
	
    
	
	/*
	 * // 验证邮箱的正则表达式
	 */
	public static boolean checkEmail(String email) {
    	// 验证邮箱的正则表达式     //电子邮件  
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
        Pattern regex = Pattern.compile(check);  
        Matcher matcher = regex.matcher(email);  
        boolean isMatched = matcher.matches();  
        return isMatched;
    }
	
	/*
	 * 检验手机号码的合法性
	 */
	public static boolean checkPhoneNo(String phoneNo) {
    	Pattern p = Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{8})$");        
        Matcher m = p.matcher(phoneNo);  
        boolean isMatched = m.matches();  
        return isMatched;    
	}

	  /*
     *QQ 授权是否满足条件 ,是否过期
     */
    public static boolean satisfyConditions() {
    	boolean isValid = false;
//    	if (BaseConfig.TOKEN_QQ != null) {
//    		isValid =  (!TextUtils.isEmpty(BaseConfig.TOKEN_QQ) && (BaseConfig.EXPIRES_IN_QQ != null 
//            		 && !BaseConfig.EXPIRES_IN_QQ.equals("0")
//            		 &&(System.currentTimeMillis() < Long.valueOf(BaseConfig.EXPIRES_IN_QQ))));
//        }
//    	if(!isValid){
//    		PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
// 				BaseConstants.SharedPreferences_TOKEN+"_"+BaseConstants.Log_QQ, "");
//    		PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
// 				BaseConstants.SharedPreferences_EXPIRES_IN+"_"+BaseConstants.Log_QQ, "0");
//    		PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
// 				BaseConstants.SharedPreferences_UID+"_"+BaseConstants.Log_QQ, "");
//    	}
    	return isValid;
	}
    
    /*
     * sina授权是否过期
     */
    public static boolean isSessionValid() {
    	boolean isValid = false;
//    	Weibo weibo = null;
//    	AccessToken accessToken = new AccessToken(BaseConfig.TOKEN_SINA, BaseConfig.CONSUMER_SECRET_SINA);
//    	accessToken.setExpiresIn(BaseConfig.EXPIRES_IN_SINA);
//        weibo = Weibo.getInstance();
//        weibo.setAccessToken(accessToken);
//        if (weibo != null && weibo.getAccessToken() != null) {
//        	isValid = (!TextUtils.isEmpty( weibo.getAccessToken() .getToken()) && ( weibo.getAccessToken() .getExpiresIn() == 0
//            		|| (System.currentTimeMillis() <  weibo.getAccessToken() .getExpiresIn())));
//        }
//        if(!isValid){
//        	PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
//    			BaseConstants.SharedPreferences_TOKEN+"_"+BaseConstants.Log_Sina, "");
//    		PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
//    			BaseConstants.SharedPreferences_EXPIRES_IN+"_"+BaseConstants.Log_Sina, "0");
//    		PreferencesUtils.setStringPreferences(BaseApplication.getInstance().getApplicationContext(), BaseConstants.Settings_Auther, 
//    			BaseConstants.SharedPreferences_UID+"_"+BaseConstants.Log_Sina, "");
//        }
        return isValid;
    }
    
    /*
     * 微博分享正文限制140个字
     */
	public static String getWeiBoContent(String content_tmp) {
		// TODO Auto-generated method stub
//		int num_share = BaseConfig.SHARE_TITLE_1_SINA.length();
//		int num = content_tmp.length();
//		if (num > (140-num_share)) {
//			String content =  content_tmp.substring(0, (137-num_share));
//			content = content+"...";
//			return content;
//		}else{
//			return content_tmp;
//		}
		return "";
	} 
	
	/*
	 * 最近浏览列表
	 */
	public static ArrayList<PostInfo> getLastViewsList() {
		ArrayList<PostInfo> postInfos = new ArrayList<PostInfo>(1);
		String lastviews =  PreferencesUtils.getStringPreferences(BaseConstants.LastViews_NAME, BaseConstants.SharedPreferences_LastViews, "");
		if(lastviews!=null && !lastviews.equals("")){
			//集合string转对象list  
			JsonArray jsonArray=null;
			try{
				jsonArray = JsonMethed.getJsonArray(JsonMethed.getJsonElement(lastviews));
			}catch (JsonParseException e) {
			}	
			if(jsonArray != null && jsonArray.size() != 0){
				for(int i =0;i<jsonArray.size();i++){
					JsonObject jsonObject = JsonMethed.getJsonObject(jsonArray.get(i));
					if(jsonObject != null){
						PostInfo postInfo = new PostInfo();
						postInfo = PostInfo.getPostBasicFromJsonObject(SubTabInfoTypeConstants.SUBCATE_RECENT_VIEWED_TOPICS,JsonMethed.getJsonObject(jsonObject.get("postBasic")));
						postInfos.add(postInfo);
					}
				}
			}
		}
		return postInfos;
	} 
	
	/*
	 * 最近浏览Tid列表
	 */
	public static ArrayList<String> getLastViewsTidList(ArrayList<PostInfo> postInfos) {
		ArrayList<String> tidlist = new ArrayList<String>(1);
		for(PostInfo postInfo:postInfos){
			tidlist.add(postInfo.getPostBasic().getTid());
		}
		return tidlist;
	}
	
	/*
	 * 保存最近浏览列表
	 */
	public static void recordLastViewsPost(PostInfo info){
		List<PostInfo> lastviewsList = null;
		Gson gson=new Gson();
		String jsonString_LastViews =  PreferencesUtils.getStringPreferences(BaseConstants.LastViews_NAME, BaseConstants.SharedPreferences_LastViews, "");
		if(jsonString_LastViews == null){
			lastviewsList = new ArrayList<PostInfo>();
			lastviewsList.add(0, info);
			String gsonString=gson.toJson(lastviewsList);
			PreferencesUtils.setStringPreferences(
  					BaseConstants.LastViews_NAME, BaseConstants.SharedPreferences_LastViews,gsonString);
		}else{
	        //集合string转对象list
			ArrayList<String> lastviewsTidList = new ArrayList<String>(1);
			lastviewsTidList = getLastViewsTidList(getLastViewsList());
			lastviewsList = getLastViewsList();
	        if(!lastviewsTidList.contains(info.getPostBasic().getTid())){
	        	lastviewsList.add(0, info);
	        }
			String gsonString=gson.toJson(lastviewsList);
			PreferencesUtils.setStringPreferences(
  					BaseConstants.LastViews_NAME, BaseConstants.SharedPreferences_LastViews,gsonString);
		 }
    }
	
	/*
	 * 保存最近浏览列表
	 */
	public static void recordLastViewsPost(HandlinesBasicInfo info){
		PostInfo postInfo = new PostInfo();
		postInfo.getPostBasic().setAuthor(info.getAuthor());
		postInfo.getPostBasic().setTid(info.getTid());
		postInfo.getPostBasic().setSubject(info.getTitle());
		postInfo.getPostBasic().setFid(info.getFid());
		postInfo.getPostBasic().setMicon(info.getImg());
		postInfo.getPostBasic().setUrl(info.getUrl());
		postInfo.getPostBasic().setAuthor(info.getAuthor());
		recordLastViewsPost(postInfo);
    }

	/*
	 * 获取相机拍照的图片地址
	 */
	public static String getCameraFileName() { 
    	String picPathString = "";
    	Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));
        String second= String.valueOf(c.get(Calendar.SECOND));
        String name = "IMG_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second;
        picPathString = Environment.getExternalStorageDirectory()+ File.separator+BaseConstants.Save_RootFile+File.separator+"cache/pic/";
        if(FileUtils.isHasSDCard()){
        	File destDir = new File(picPathString);
        	if (!destDir.exists())  {
             	destDir.mkdirs();
            }
        }
        picPathString = picPathString+name+".jpg";		
        return picPathString;
    }

	/*
     * 获取图片的旋转
     */
    public static String getOrientation(Uri mImageCaptureUri) {  
    	String orientation = "";
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();  
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找  
        if (cursor != null) {  
            cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了  
            orientation = cursor.getString(cursor  
                    .getColumnIndex("orientation"));// 获取旋转的角度  
            cursor.close();  
        } 
        return orientation;
    } 
    
    /*
     * 获取图片的旋转
     */
    public static String getOrientation(String imgPath) {  
    	ExifInterface exifInterface;
		try {
			exifInterface = new ExifInterface(imgPath);
			int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
	    	if (tag == ExifInterface.ORIENTATION_ROTATE_90) {//如果是旋转地图片则先旋转
	    		return "90";
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "0";
    }
    
    /*
     * 由URI的到绝对路径
     */
	public static String getAbsoluteImagePath(Activity activity,Uri uri){
        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery( uri,
                        proj,                 // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null);                 // Order-by clause (ascending by name)
        
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();         
        return cursor.getString(column_index);
    }

}