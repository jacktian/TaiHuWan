package com.gloria.hbh.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TextUtils {
	
	/*
     * ΢��������������120����
     */
	public static String getWeiBoContent(String content_tmp) {
		// TODO Auto-generated method stub
		int num = content_tmp.length();
		if (num > 120) {
			String content =  content_tmp.substring(0, 120);
			return content;
		}
		return content_tmp;
	} 
	
	/*
	 * ���ʱ��
	 */
	public static String  calTime(long endTime){
	    long between=endTime-System.currentTimeMillis();
	     int tmp=(int) (between/(1000*60*60*24));
	     between=between%(1000*60*60*24);
	     String timeStr="";
	     if(tmp>0)
	     {
		     timeStr+=String.valueOf(tmp)+"��";
	     }
	     
	     tmp=(int) (between/(1000*60*60));
	     between=between%(1000*60*60);
	     if(tmp>0)
	     {
		     timeStr+= String.valueOf(tmp)+"Сʱ";
	     }
	     
	     
	     tmp=(int) (between/(1000*60));
	     between=between%(1000*60);
	     if(tmp>0)
	     {
		     timeStr+=  String.valueOf(tmp)+"��";
	     }
	     
	     tmp=(int) (between/(1000));
	     if(tmp>0)
	     {
		     timeStr+=  String.valueOf(tmp)+"��";
	     }
	     return timeStr;
    }
	
	public static String getCurrentTime() {
		  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  dateformat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		  Date date = new Date(System.currentTimeMillis());
		  return "������£�"+dateformat.format(date);
	}
	
	public static String getCurrentTime2() {
		  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy��MM��dd��");
		  dateformat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		  Date date = new Date(System.currentTimeMillis());
		  return "������£�"+dateformat.format(date);
	}

}
