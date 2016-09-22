package com.gloria.hbh.sharecookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;

/**
* �� �� �� : ShareCookie
* �� �� �ˣ� gejian
* ��     �ڣ�2012-8-7
* �� �� �ˣ�gejian
* ��    �ڣ�2012-8-7
* ��    ����Cookie������
*/
public class ShareCookie
{
	private static CookieStore _cookie = null;
	private static BasicHttpContext _httpContext;
	
//	public static CookieStore addCookie(Cookie cookie)
//	{
//		
//		_cookie.addCookie(cookie);
//		return _cookie;
//	}
	
	public static CookieStore getCookie(){
		return _cookie;
	}
	
	public static void setCookie(CookieStore cookieStore)
	{
		_cookie = cookieStore;
	}
	
	
	public static BasicHttpContext getHttpContext()
	{
		if(_httpContext==null)
		{
			_httpContext = new BasicHttpContext();
			_httpContext.setAttribute(ClientContext.COOKIE_STORE, _cookie);
		}else{
			_httpContext.removeAttribute(ClientContext.COOKIE_STORE);
			_httpContext.setAttribute(ClientContext.COOKIE_STORE, _cookie);
		}
		return _httpContext;
	}
	
	public static void clearCookie()
	{
		_cookie = null;
	}
}
