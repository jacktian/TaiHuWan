package com.gloria.hbh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.gloria.hbh.cachemanager.ConfigCache;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.datadispose.JsonMethed;
import com.gloria.hbh.sharecookies.ShareCookie;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
* �� �� �� : HttpUtils
* �� �� �ˣ� gejian
* ��     �ڣ�2013-1-30
* �� �� �ˣ�gejian
* ��    �ڣ�2013-1-30
* ��    ����Http  get��Post������
*/
public class HttpUtils {
	
	private static final String TAG = HttpUtils.class.getName();
	private static boolean DEBUG = false;  
	
	/*
	 * �������ϻ�ȡXML����
	 */
	public static InputStream readXMLDataFromInternet(String url){
		URL infoUrl = null;
		InputStream inStream = null;
		
		if(NetworkUtils.isNetworkAvailable()){				
			try {
				if(DEBUG)
					System.out.println(TAG+"URL-->"+url);
				infoUrl = new URL(url);
				URLConnection connection;
				connection = infoUrl.openConnection();
				connection.setConnectTimeout(BaseConstants.TimeoutConnection);
				connection.setReadTimeout(BaseConstants.TimeoutSocket);
				HttpURLConnection httpConnection = (HttpURLConnection)connection;
				int responseCode = httpConnection.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK){
					inStream = httpConnection.getInputStream();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return inStream;
	}
	
	/*
	 * ��ȡJson�ַ���
	 */
	public static String getJsonString(String url,boolean isRefresh){
		String result = "";
		if(!isRefresh){
			//���ȳ��Զ�ȡ����
	        String cacheConfigString = ConfigCache.getUrlCache(url);
	        //���ݽ���ж��Ƕ�ȡ���棬�������¶�ȡ
	        if (cacheConfigString != null) {
	            return cacheConfigString;
	        }
		}
        
		if(DEBUG)
			System.out.println(TAG+"URL-->"+url);
		//�ж��Ƿ������磬�еĻ�������ȡ���ݲ�����cache
		if(NetworkUtils.isNetworkAvailable()){
			HttpGet httpGetRequest = null;
			try {
				httpGetRequest = new HttpGet(new URI(url));
			
				// ����HttpPost����
				HttpParams httpParameters = new BasicHttpParams(); 
				HttpConnectionParams.setConnectionTimeout(httpParameters, BaseConstants.TimeoutConnection); 
				HttpConnectionParams.setSoTimeout(httpParameters, BaseConstants.TimeoutSocket); 
				httpGetRequest.setParams(httpParameters);	
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} 

			DefaultHttpClient httpclient = new DefaultHttpClient();  
			httpclient.setCookieStore(ShareCookie.getCookie());
			httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
			
			try{	
				HttpResponse httpResponse = httpclient.execute(httpGetRequest);
				if(httpResponse.getStatusLine().getStatusCode() == BaseConstants.StatusCode_OK){   // ���ӳɹ�
					result = EntityUtils.toString(httpResponse.getEntity(),"utf-8"); // �����Դ
					if(DEBUG)
						System.out.println(TAG+"---result-->"+result);
					 //�ɹ����أ��򱣴浽������Ϊ���滺���ļ�
                    ConfigCache.setUrlCache(result,url);
				}
			}catch(UnknownHostException ex2){
				result = "";
				ex2.printStackTrace();
			}
			catch (Exception e){
				result = "";
				e.printStackTrace();
			}
		}	
		return result;
	}
	
	/*
	 * ��ȡJson�ַ���
	 */
	public static String getJsonString(String url,boolean isRefresh,boolean isSaveCookies){
		String result = "";
		if(!isRefresh){
			//���ȳ��Զ�ȡ����
	        String cacheConfigString = ConfigCache.getUrlCache(url);
	        //���ݽ���ж��Ƕ�ȡ���棬�������¶�ȡ
	        if (cacheConfigString != null) {
	            return cacheConfigString;
	        }
		}
        
		if(DEBUG)
			System.out.println(TAG+"URL-->"+url);
		//�ж��Ƿ������磬�еĻ�������ȡ���ݲ�����cache
		if(NetworkUtils.isNetworkAvailable()){
			HttpGet httpGetRequest = null;
			try {
				httpGetRequest = new HttpGet(new URI(url));
			
				// ����HttpPost����
				HttpParams httpParameters = new BasicHttpParams(); 
				HttpConnectionParams.setConnectionTimeout(httpParameters, BaseConstants.TimeoutConnection); 
				HttpConnectionParams.setSoTimeout(httpParameters, BaseConstants.TimeoutSocket); 
				httpGetRequest.setParams(httpParameters);	
				httpGetRequest.setURI(new URI(url)); 

			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} 

			DefaultHttpClient httpclient = new DefaultHttpClient();  
			httpclient.setCookieStore(ShareCookie.getCookie());
			httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
			
			try{	
				HttpResponse httpResponse = httpclient.execute(httpGetRequest);
				if(httpResponse.getStatusLine().getStatusCode() == BaseConstants.StatusCode_OK){   // ���ӳɹ�
					result = EntityUtils.toString(httpResponse.getEntity(),"utf-8"); // �����Դ
					if(DEBUG)
						System.out.println(TAG+"---result-->"+result);
					 //�ɹ����أ��򱣴浽������Ϊ���滺���ļ�
                    ConfigCache.setUrlCache(result,url);
                    if(isSaveCookies){
                    	ShareCookie.clearCookie();
        				ShareCookie.setCookie(httpclient.getCookieStore());	
                    }
				}
			}catch(UnknownHostException ex2){
				result = "";
				ex2.printStackTrace();
			}
			catch (Exception e){
				result = "";
				e.printStackTrace();
			}
		}	
		return result;
	}
	
	/*
	 * ��ȡJson����
	 */
	public static JsonObject getJsonObject(String url,boolean isRefresh){
		String result = getJsonString(url,isRefresh);
		String filePath="";
		filePath = ConfigCache.SetUrlToFilePath(BaseConstants.CACHE_FILETYPE_FILE, url);
		File file = new File(filePath);
		try{
			if (result == null || result.equals("")) {  
				return null;
			}
			JsonObject jsonObject = JsonMethed.getJsonObject(JsonMethed.getJsonElement(result));
			return jsonObject;
		}catch (JsonParseException e) {
			e.printStackTrace();
			if (result != null &&FileUtils.isHasSDCard() && file.exists()) {  
				file.delete();
			}
		}
		return null;
	}
	
	/*
	 * ��ȡJsonArray
	 */
	public static JsonArray getJsonArray(String url,boolean isRefresh){
		String result = getJsonString(url,isRefresh);
		String filePath="";
		filePath = ConfigCache.SetUrlToFilePath(BaseConstants.CACHE_FILETYPE_FILE, url);
		File file = new File(filePath);
		try{
			if (result == null || result.equals("")) {  
				return null;
			}
			JsonArray jsonArray = JsonMethed.getJsonArray(JsonMethed.getJsonElement(result));
			return jsonArray;
		}catch (JsonParseException e) {
			e.printStackTrace();
			if (result != null &&FileUtils.isHasSDCard() && file.exists()) {  
				file.delete();
			}
		}
		return null;
	}
	
	/**
	 * Post������ȡhttp���
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getPostResult(String url,List<NameValuePair> params){
		String strResult = "";
		DefaultHttpClient httpClient=new DefaultHttpClient();
		httpClient.setCookieStore(ShareCookie.getCookie());
		if(DEBUG)
			System.out.println("Post url-->"+url);
		HttpPost httpRequest = new HttpPost(url);
		HttpParams httpParameters = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, BaseConstants.TimeoutConnection);
	    HttpConnectionParams.setSoTimeout(httpParameters, BaseConstants.TimeoutSocket);
	    httpRequest.setParams(httpParameters);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			InputStream is= httpClient.execute(httpRequest,ShareCookie.getHttpContext()).getEntity().getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"GBK"));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()){
				stringBuilder.append(line);
			}
			
			strResult = stringBuilder.toString();
			if(DEBUG)
				System.out.println("getPostResult-->"+strResult);
			return strResult;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Post������ȡhttp�������¼������
	 * @param url
	 * @param params
	 * @param isSaveCookie (�Ƿ񱣴�Cookies)
	 * @return
	 */
	public static String getPostResult(String url,List<NameValuePair> params,boolean isSaveCookie){
		String strResult = "";
		DefaultHttpClient httpClient=new DefaultHttpClient();
		httpClient.setCookieStore(ShareCookie.getCookie());
		if(DEBUG)
			System.out.println("Post  url-->"+url);
		HttpPost httpRequest = new HttpPost(url);
		HttpParams httpParameters = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParameters, BaseConstants.TimeoutConnection);
	    HttpConnectionParams.setSoTimeout(httpParameters, BaseConstants.TimeoutSocket);
	    httpRequest.setParams(httpParameters);
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
			InputStream is= httpClient.execute(httpRequest).getEntity().getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"GBK"));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			for (line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()){
				stringBuilder.append(line);
			}
			if(isSaveCookie){  //����ǵ�¼���򱣴�Cookies
				ShareCookie.clearCookie();
				ShareCookie.setCookie(httpClient.getCookieStore());	
			}
			strResult = stringBuilder.toString();
			if(DEBUG)
				System.out.println("getPostResult-->"+strResult);
			return strResult;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
