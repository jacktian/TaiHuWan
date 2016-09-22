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
* �� �� �� : Methods
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-7
* ��    ��������ķ�����
*/
@TargetApi(5)
public class Methods {
	
	/*
	 * �ж��Ƿ����������ͼ��
	 */
	public static  void checkShortCut() {
		boolean isInstallShortCut =  PreferencesUtils.getBooleanPreferences(BaseConstants.Settings_NAME, 
				BaseConstants.SharedPreferences_isInstallShortCut, false);
		// ���ڿ�ݷ�ʽ���߲�������ӣ�return
		if (isInstallShortCut)
		  return;
		addShortcut();
		//��������
		PreferencesUtils.setBooleanPreferences(BaseConstants.Settings_NAME, 
				BaseConstants.SharedPreferences_isInstallShortCut, true);
	}
	
	/*
	 * �������ͼ��
	 */
	public static void addShortcut(){ 
		Context c = BaseApplication.getInstance().getApplicationContext();
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); 
		// ��ݷ�ʽ������ 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,c. getResources().getString(R.string.app_name)); 
		// �������ظ����� 
		shortcut.putExtra("duplicate", false); 
		String action = "com.android.action.install"; 
		Intent respondIntent = new Intent(c, c.getClass()); 
		respondIntent.setAction(action); 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, respondIntent); 
		// ��ݷ�ʽ��ͼ�� 
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(c, R.drawable.ic_launcher); 
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes); 

		c.sendBroadcast(shortcut); 
	}
	
	 //��ȡ����Ip
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
    
//    //ͨ��Service���������ж��Ƿ�����ĳ������  
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
    
    //����11--30 10��20���������ڸ�ʽ
    public static String getDate(){  
    	Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
    	t.setToNow(); // ȡ��ϵͳʱ�䡣
    	int month = t.month;
    	int day = t.monthDay;
    	int hour = t.hour;    // 0-23
    	int minute = t.minute;
    	DecimalFormat   df   =   new   DecimalFormat( "00");
    	return "������:  "+df.format(month)+"-"+df.format(day)+" "+df.format(hour)+":"+df.format(minute);
    }
    
	/*
	 * ��ȡString�����<img> ��url�б�
	 */
	public static  ArrayList<String> getContentPicUrlList(String contentstring){
		String tmpString=contentstring;
		ArrayList<String> picurl_list = new  ArrayList<String>(1);
				
		Pattern pattern1 = Pattern.compile("(onclick+=\".*?\")|(onload+=\".*?\")|(style+=\".*?\")|(border+=\".*?\")",Pattern.CASE_INSENSITIVE);   
		Matcher matcher1=pattern1.matcher(contentstring);   //�ҵ�<img> ��ǩ�еķ�src�������ÿ�
		while (matcher1.find()){
			String imgTmpString=matcher1.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
			
		Pattern p=Pattern.compile("(?<=src=['\"])http://((?!/post/smile/).)*?(?=['\"])",Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(tmpString);  //�ҵ��Ǳ���<img>��ǩ�е�url�ŵ�list��
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
	 * �����µ�String
	 * ����� �����滻
	 */
	public static String  setNewContent(String contentstring){         //�滻img��ǩ   
		String tmpString=contentstring;
		Pattern pattern1 = Pattern.compile("(onclick+=\".*?\")|(onload+=\".*?\")|(style+=\".*?\")|(border+=\".*?\")|(smilieid+=\".*?\")|(alt+=\".*?\")",Pattern.CASE_INSENSITIVE);   
		Matcher matcher1=pattern1.matcher(contentstring);   //�ҵ�<img> ��ǩ�еķ�src�������ÿ�
		while (matcher1.find()){
			String imgTmpString=matcher1.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
				
		Pattern pattern2 = Pattern.compile("<img[^<>]+src=['\"]http://[^<>]+>",Pattern.CASE_INSENSITIVE);    //ƥ�����<img src="http://     >
		Matcher matcher2=pattern2.matcher(tmpString);   //�ҵ��Ǳ���<img>��ǩ�ÿ�
		while (matcher2.find()){
			String imgTmpString=matcher2.group();
			tmpString=tmpString.replace(imgTmpString, "");
		}
				
		//����<p dir=ltr>
		tmpString = tmpString.replaceAll("<p dir=ltr>", "");
				
		//�滻����  �����ڱ���ı�﷽ʽ��
		tmpString = tmpString.replace(BaseConstants.FACE_IMG_CONTAIN_PATH,BaseConfig.url+BaseConstants.FACE_IMG_CONTAIN_PATH);  //�滻�����img��ǩ   
		
//		tmpString = tmpString.replace("<img src=\"","<img src='");  
//		tmpString = tmpString.replace("\"    />","'    />");  
		return tmpString;
	}  
	
//	//�滻@��ǩ   
//	public static String  setNewContentOfWeiBo(String contentstring){         
//		String tmpString=contentstring;
//		//�滻//@��ǩ   
//		Pattern pattern1 = Pattern.compile("(//\\u005Bat\\u005D@(.+?)\\u005B/at\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher1=pattern1.matcher(contentstring);   //�ҵ�<img> ��ǩ�еķ�src�������ÿ�
//		while (matcher1.find()){
//			String imgTmpString_old = matcher1.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[at]", "").replace("[/at]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//�滻[wurl][/wurl]��ǩ   
//		Pattern pattern2 = Pattern.compile("(\\u005Bwurl\\u005D(.+?)\\u005B/wurl\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher2=pattern2.matcher(tmpString);   //�ҵ��Ǳ���<img>��ǩ�ÿ�
//		while (matcher2.find()){
//			String imgTmpString_old = matcher2.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[wurl]", "").replace("[/wurl]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//�滻##��ǩ   
//		Pattern pattern4 = Pattern.compile("(#(.+?)#)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher4=pattern4.matcher(tmpString);   //�ҵ��Ǳ���<img>��ǩ�ÿ�
//		while (matcher4.find()){
//			String imgTmpString_old = matcher4.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_old+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		ArrayList<ImageInfo> faceInfos = null;
//		faceInfos = DataDispose.getWeiBoFaceTable(ExitApplication.getInstance().getApplicationContext());
//		//�滻�����ͼ�꣺
//		Pattern pattern3 = Pattern.compile("(:em(\\d{1,4}):)",Pattern.CASE_INSENSITIVE);    //ƥ�����<img src="http://     >
//		Matcher matcher3 = pattern3.matcher(tmpString);   //�ҵ��Ǳ���<img>��ǩ�ÿ�
//		while (matcher3.find()){
//			String imgTmpString=matcher3.group();
//			tmpString=tmpString.replace(imgTmpString, "<img src='"+getFacePath(imgTmpString,faceInfos)+"'/>");
//		}
//			
//		//�滻@��ǩ   
//		Pattern pattern5 = Pattern.compile("(\\u005Bat\\u005D@(.+?)\\u005B/at\\u005D)",Pattern.CASE_INSENSITIVE);
//		Matcher matcher5=pattern5.matcher(contentstring);   //�ҵ�<img> ��ǩ�еķ�src�������ÿ�
//		while (matcher5.find()){
//			String imgTmpString_old = matcher5.group();
//			String imgTmpString_new ="";
//			imgTmpString_new = imgTmpString_old.replace("[at]", "").replace("[/at]", "");
//			imgTmpString_new = "<font color=#4878AC>"+imgTmpString_new+"</font>";
//			tmpString= tmpString.replace(imgTmpString_old, imgTmpString_new);
//		}
//			
//		//����<p dir=ltr>
//		tmpString = tmpString.replaceAll("<p dir=ltr>", "");
//			
////		tmpString = tmpString.replace("static/image/smiley/", GlobalSource.url+"static/image/smiley/");  //�滻�����img��ǩ   
//		return tmpString;
//	}  
	
//	/*
//	 * �ɱ���ͼƬ��Code��ȡͼƬ·��
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
		    
//	//����
//	public static void sharePost(String text,Context context){
//		Intent it = new Intent(Intent.ACTION_SEND);
//		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		it.putExtra(Intent.EXTRA_TEXT, text);
//		it.setType("text/plain");
//	    Intent newIntent = Intent.createChooser(it, "��ѡ��");
//		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(newIntent);
//	}
		    
//	//��ҳtab���廻�д�С
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
		    
		    
	//Ԥ�����쳣����
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
	
//	//��ȡͼƬ����
//	public static int getTypeOfImage(String result){
//		int type = 1; //0:����   1��һ��ͼƬ
//		//......................
//		return type;
//	}
	
    
	
	/*
	 * // ��֤�����������ʽ
	 */
	public static boolean checkEmail(String email) {
    	// ��֤�����������ʽ     //�����ʼ�  
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
        Pattern regex = Pattern.compile(check);  
        Matcher matcher = regex.matcher(email);  
        boolean isMatched = matcher.matches();  
        return isMatched;
    }
	
	/*
	 * �����ֻ�����ĺϷ���
	 */
	public static boolean checkPhoneNo(String phoneNo) {
    	Pattern p = Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{8})$");        
        Matcher m = p.matcher(phoneNo);  
        boolean isMatched = m.matches();  
        return isMatched;    
	}

	  /*
     *QQ ��Ȩ�Ƿ��������� ,�Ƿ����
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
     * sina��Ȩ�Ƿ����
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
     * ΢��������������140����
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
	 * �������б�
	 */
	public static ArrayList<PostInfo> getLastViewsList() {
		ArrayList<PostInfo> postInfos = new ArrayList<PostInfo>(1);
		String lastviews =  PreferencesUtils.getStringPreferences(BaseConstants.LastViews_NAME, BaseConstants.SharedPreferences_LastViews, "");
		if(lastviews!=null && !lastviews.equals("")){
			//����stringת����list  
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
	 * ������Tid�б�
	 */
	public static ArrayList<String> getLastViewsTidList(ArrayList<PostInfo> postInfos) {
		ArrayList<String> tidlist = new ArrayList<String>(1);
		for(PostInfo postInfo:postInfos){
			tidlist.add(postInfo.getPostBasic().getTid());
		}
		return tidlist;
	}
	
	/*
	 * �����������б�
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
	        //����stringת����list
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
	 * �����������б�
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
	 * ��ȡ������յ�ͼƬ��ַ
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
     * ��ȡͼƬ����ת
     */
    public static String getOrientation(Uri mImageCaptureUri) {  
    	String orientation = "";
        ContentResolver cr = BaseApplication.getInstance().getContentResolver();  
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// ����Uri�����ݿ�����  
        if (cursor != null) {  
            cursor.moveToFirst();// ���α��ƶ�����λ����Ϊ�����Uri�ǰ���ID��������Ψһ�Ĳ���Ҫѭ����ָ���һ��������  
            orientation = cursor.getString(cursor  
                    .getColumnIndex("orientation"));// ��ȡ��ת�ĽǶ�  
            cursor.close();  
        } 
        return orientation;
    } 
    
    /*
     * ��ȡͼƬ����ת
     */
    public static String getOrientation(String imgPath) {  
    	ExifInterface exifInterface;
		try {
			exifInterface = new ExifInterface(imgPath);
			int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
	    	if (tag == ExifInterface.ORIENTATION_ROTATE_90) {//�������ת��ͼƬ������ת
	    		return "90";
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "0";
    }
    
    /*
     * ��URI�ĵ�����·��
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