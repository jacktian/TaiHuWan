package com.gloria.hbh.myview;

import android.app.Dialog;
import android.content.Context;

import com.gloria.hbh.main.R;

public class LoadingDialog {
	
	private Dialog waitDialog = null;
	public LoadingDialog(Context context,boolean isCancelable) {  //isCancelable ：true 可取消  false:不可取消
		waitDialog = new Dialog(context, R.style.TRANSDIALOG);  
		waitDialog.setContentView(R.layout.trans_dialog);  
		waitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);  
		waitDialog.setCancelable(isCancelable);
		waitDialog.setCanceledOnTouchOutside(false);
	}
	
	public void show(){
		waitDialog.show(); 
	}

	public void cancel() {
		waitDialog.cancel(); 
	}
	
	public void setTitle(String title) {
		waitDialog.setTitle(title); 
	}

}
