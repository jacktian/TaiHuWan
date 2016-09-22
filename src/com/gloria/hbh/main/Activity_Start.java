package com.gloria.hbh.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.ImageView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.gloria.hbh.application.BaseApplication;
import com.gloria.hbh.data.app.Plist;
import com.gloria.hbh.data.app.UpdateInfo;
import com.gloria.hbh.data.forum.ImageInfo.ImgScaleTypeConstants;
import com.gloria.hbh.util.ImageUtils;
import com.gloria.hbh.util.Methods;
import com.gloria.hbh.util.NetworkUtils;
import com.gloria.hbh.util.ScreenUtils;

/**
* �� �� �� : Start_Activity
* �� �� �ˣ� gejian
* ��     �ڣ�2013-1-15
* �� �� �ˣ�gejian
* ��    �ڣ�2013-1-15
* ��    ��������ҳ��
*/
public class Activity_Start extends Activity_Base {
	
//	LinearLayout layout;
	ImageView item_img;
	
	UpdateInfo updateInfo;  //������Ϣ
	long stime, etime;  //��ʼ������ʱ��
	private final int DIALOG_NONETWORK = 0; // ��ʾ������
	private final int DIALOG_VERSIONUPDATE = 1; // ��ʾ�汾����
	private boolean islogin = false; //�Ƿ��¼
	int ret = 1; //����1���ǿ���ʹ�� 0������ʹ��
	
	String img_url = "";
	String filePath = "";
	
	private final Timer timer = new Timer();
	private TimerTask task;
	boolean isStart = true;
	 
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_start);
	    
        stime = System.currentTimeMillis();
        
        item_img = (ImageView) findViewById(R.id.item_img);
        
        img_url = Plist.getInstance().getAppInfo().getLaunchImage().getUrl();
        filePath = ImageUtils.IsSaveImageFileExist(img_url,ScreenUtils.getInstance().getWidth(),
				ScreenUtils.getInstance().getHeight(), ImgScaleTypeConstants.IMGTYPE_SCALE);
        
//        if(!filePath.equals("")){
//       	 	Bitmap bitmap = ImageUtils.getBitmapByPath(filePath);
//       	 	item_img.setImageBitmap(bitmap);
//        }else{
        	item_img.setBackgroundResource(R.drawable.backgroud);
//        }
        
//		new StartRequestTask().execute();
        //�첽����
	    task = new TimerTask() {
        	 public void run() {
        		Message message = new Message();
        		message.what = 1;
        		handler.sendMessage(message);
        	}
        };
        timer.schedule(task, 100, 500);
        
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			if(isStart){
				isStart = false;
				new StartRequestTask().execute();
			}
			super.handleMessage(message);
		}
	};
	
	protected  void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

//    public void  gotoMainActivity(){
//    	if(ret == 0){
//    		BaseApplication.getInstance().exit();
//    		return;
//    	}
//    	
//    	etime = System.currentTimeMillis();
//    	long time = etime -stime;
//    	if(time < 2*1000){
//    		try {
//				Thread.sleep(2*1000-time);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//    	}
//    	
//    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//    	// �Ƿ�������������˿�ݷ�ʽ
//    	boolean isFirstOpenSoft = sp.getBoolean("isFirstOpenSoft",true);
//    	// ���ڿ�ݷ�ʽ���߲�������ӣ�return
//    	if (isFirstOpenSoft){
//    		// ���ò�����ʾ��ӿ�ݷ�ʽ
//        	Editor editor = sp.edit();
//        	editor.putBoolean("isFirstOpenSoft", false);
//        	editor.commit();
//        	Intent intent = new Intent();
//    		intent.setClass(Activity_Start.this, Activity_NewFunctionHint.class);
//        	intent.putExtra("type", "FromStart");
//        	startActivity(intent);
//    	}else{
//    		Intent select_i = new Intent();
////    		select_i.setClass(Activity_Start.this, Activity_Main.class);
//    		select_i.setClass(Activity_Start.this, Activity_Guide.class);
//			startActivity(select_i);
//    	}
//    }
	
	protected void gotoGuideActivity() {
    	if(ret == 0){
		BaseApplication.getInstance().exit();
		return;
    	}
	
    	etime = System.currentTimeMillis();
    	long time = etime -stime;
    	if(time < 2*1000){
    		try {
    			Thread.sleep(2*1000-time);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
		}
	
    	//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	// �Ƿ�������������˿�ݷ�ʽ
    	//boolean isFirstOpenSoft = sp.getBoolean("isFirstOpenSoft",true);
    	// ���ڿ�ݷ�ʽ���߲�������ӣ�return
//		if (isFirstOpenSoft){
//			// ���ò�����ʾ��ӿ�ݷ�ʽ
//			Editor editor = sp.edit();
//			editor.putBoolean("isFirstOpenSoft", false);
//			editor.commit();
//			Intent intent = new Intent();
//			intent.setClass(Activity_Start.this, Activity_NewFunctionHint.class);
//			intent.putExtra("type", "FromStart");
//			startActivity(intent);
//			finish();
//		}else{
//			Intent select_i = new Intent();
////			select_i.setClass(Activity_Start.this, Activity_Main.class);
//			select_i.setClass(Activity_Start.this, Activity_Guide.class);
//			startActivity(select_i);
//			finish();
			
			
			Intent intent = new Intent();
	        intent.setClass(Activity_Start.this, Activity_Main.class);
	        intent.putExtra("type", Activity_Main.TYPE_INFO);
	        startActivity(intent);
			
		//}
	}
    
    //��ʾ�Ի���
    @SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DIALOG_NONETWORK:
			return new AlertDialog.Builder(this).setTitle(R.string.hint_Msg)
					.setMessage(R.string.msg_isSetNetwork).setPositiveButton(R.string.ok,networkListener)
					.setNegativeButton(R.string.cancel,gotoGuideListener).create();
		case DIALOG_VERSIONUPDATE:
			return new AlertDialog.Builder(this).setTitle(R.string.hint_Msg)
					.setMessage(updateInfo.getUpdateContent()).setPositiveButton(R.string.ok,gotoUpdateListener)
					.setNegativeButton(R.string.cancel,gotoUpdateListener).create();
		default:
			return super.onCreateDialog(id);
		}
	}
    
    //�Ի�������¼�
	DialogInterface.OnClickListener networkListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));  //����������Ľ���
		}
	};
//	DialogInterface.OnClickListener gotoMainListener = new DialogInterface.OnClickListener() {
//		public void onClick(DialogInterface dialog, int which) {
//			gotoMainActivity();
//		}
//	};
	DialogInterface.OnClickListener gotoGuideListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			gotoGuideActivity();
		}
	};
	DialogInterface.OnClickListener gotoUpdateListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.getApkUrl()));  
            startActivity(intent);
		}
	};
	
	
	/**
	 * �첽����
	 */
	 public  class StartRequestTask extends AsyncTask<Void, Void, Boolean> {     //�첽����
		protected void onPostExecute(Boolean result) {
			if(!NetworkUtils.isNetworkAvailable()){
//				ForumToast.show(getString(R.string.hint_NoNetwork));
			    showDialog(DIALOG_NONETWORK);
			    return;
			}
			if(Methods.disposeDataException(result)){
				return;
			}
//			etime =System.currentTimeMillis();
			if((Boolean) result){  //�Զ����£���ת�������г�
				showDialog(DIALOG_VERSIONUPDATE);
			}else{
				gotoGuideActivity();	
			}
		}
		
		protected Boolean doInBackground(Void... params) {	
			boolean isUpdate = false; //false:û����
			
//			String isAvailable = DataDispose.IsAvailable();
//			isAvailable = "";
//			JsonObject jsonObject = JsonMethed.getJsonObject(JsonMethed.getJsonElement(isAvailable));
//			if(jsonObject != null){
//				ret = JsonMethed.getJsonInt(jsonObject.get("ret"));
//				if(ret == 0){
//					return isUpdate;
//				}
//			}
			
//			//������
//			try {
//				InputStream is = HttpUtils.readXMLDataFromInternet(BaseConfig.checkupdate);
//				BufferedReader in = new BufferedReader(new InputStreamReader(is));
//				StringBuffer buffer = new StringBuffer();
//				String line = "";
//				while ((line = in.readLine()) != null){
//				  buffer.append(line);
//				}
//				String json_update = buffer.toString();
//				JsonObject jsonObject_update = JsonMethed.getJsonObject(JsonMethed.getJsonElement(json_update));
//				updateInfo = UpdateInfo.getFromJsonObject(jsonObject_update);
//				PackageManager pm = Activity_Start.this.getPackageManager();  
//		    	PackageInfo pi = pm.getPackageInfo(Activity_Start.this.getPackageName(), 0);
//		    	if(updateInfo != null && (updateInfo.getLastVerCode() > pi.versionCode)) {
//		    		isUpdate =  true;
//		    	} 
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
		    return isUpdate;
		}
	}

}