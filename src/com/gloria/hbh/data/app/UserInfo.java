package com.gloria.hbh.data.app;

import org.json.JSONException;
import org.json.JSONObject;

import com.gloria.hbh.data.forum.BaseData;
import com.google.gson.JsonObject;

/*
 * 我的信息  的数据结构
 * 2012-09-13
 */
public class UserInfo extends BaseData{
	
//	public class UserInfoTypeConstants {
//		public static final int Type_Common = 0; //普通用户
//		public static final int Type_Business = 1; //商家
//	}
	
//	private int type = UserInfoTypeConstants.Type_Common;
	
	public class UserLoginTypeConstants {
		public static final int UserLoginType_COMMENT= 0; //普通登录
		public static final int UserLoginType_REGISTE = 1; //注册登录
		public static final int UserLoginType_SINA = 2; //新浪登录
		public static final int UserLoginType_TENCENT = 3; //腾讯登录
		
		public static final int Friend_delete= 4; //删除好友
		public static final int Friend_add = 5; //添加好友
		public static final int Friend_guanzu = 6; //好友关注
		public static final int Friend_quxiao = 7; //取消关注
	}
	private boolean isLogin = false; //是否登录
	private int log_type = UserLoginTypeConstants.UserLoginType_COMMENT;
	private int uid = 0;
	private String username = "";
	private String userhash = "";
	private String nickname = "";
	private String password = "";
	private boolean remeberPwd = false;
	private boolean autologin = false;
	private String avatar = "";
	private String mailAdr = "";
	private String qq = "";
	
	private String IP="";
	private String log_lasttime="";
	private int postnum;
	private int money;
	private String todaypost;
	private String weiwang;
	private String user_type;
	private String icon;
	
	private String follows;//关注数
	private String fans;//粉丝数
	private String friends;//好友数
	
	private String introduce;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getUserHash() {
		return userhash;
	}

	public void setUserHash(String userHash) {
		this.userhash = userHash;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getQQ() {
		return qq;
	}

	public void setQQ(String qq) {
		this.qq = qq;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public int getLog_type() {
		return log_type;
	}

	public void setLog_type(int log_type) {
		this.log_type = log_type;
	}

	public String getMailAdr() {
		return mailAdr;
	}

	public void setMailAdr(String mailAdr) {
		this.mailAdr = mailAdr;
	}

	public String getUserName(){
		return username;
	}
	
	public void setUserName(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public boolean getRemeberPwd(){
		return remeberPwd;
	}
	
	public void setRemeberPwd(boolean remeberPwd){
		this.remeberPwd = remeberPwd;
	}
	
	public boolean getAutologin(){
		return autologin;
	}
	
	public void setAutologin(boolean autologin){
		this.autologin = autologin;
	}
	
	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getLog_lasttime() {
		return log_lasttime;
	}

	public void setLog_lasttime(String log_lasttime) {
		this.log_lasttime = log_lasttime;
	}

	public int getPostnum() {
		return postnum;
	}

	public void setPostnum(int postnum) {
		this.postnum = postnum;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getTodaypost() {
		return todaypost;
	}

	public void setTodaypost(String todaypost) {
		this.todaypost = todaypost;
	}

	public String getWeiwang() {
		return weiwang;
	}

	public void setWeiwang(String weiwang) {
		this.weiwang = weiwang;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFollows() {
		return follows;
	}

	public void setFollows(String follows) {
		this.follows = follows;
	}

	public String getFans() {
		return fans;
	}

	public void setFans(String fans) {
		this.fans = fans;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getUid(){
		return uid;
	}
	
	public void setUid(int uid){
		this.uid = uid;
	}
	
	public static UserInfo getFromJsonObject(JsonObject jsonObject){
		UserInfo userInfo = new UserInfo();
		try {
			userInfo.setUserName(jsonObject.get("username").getAsString() );
			userInfo.setPassword(jsonObject.get("password").getAsString());
			userInfo.setUserHash(jsonObject.get("userhash").getAsString());
			userInfo.setRemeberPwd(jsonObject.get("remeberPwd").getAsBoolean());
			userInfo.setAutologin(jsonObject.get("autologin").getAsBoolean());
//			userInfo.setLogin(jsonObject.get("isLogin").getAsBoolean());
			userInfo.setUid(jsonObject.get("uid").getAsInt());
			
//			userInfo.setIcon(jsonObject.get("micon").getAsString());
//			userInfo.setIP(jsonObject.get("onlineip").getAsString());
//			userInfo.setMailAdr(jsonObject.get("email").getAsString() );
//			userInfo.setLog_lasttime(jsonObject.get("thisvisit ").getAsString());
//			userInfo.setPostnum(jsonObject.get("postnum ").getAsInt());
//			userInfo.setMoney(jsonObject.get("money ").getAsInt());
//			userInfo.setTodaypost(jsonObject.get("todaypost ").getAsString());
//			userInfo.setWeiwang(jsonObject.get("rvrc ").getAsString());			
//			userInfo.setUser_type(jsonObject.get("grouptitle ").getAsString());
			
		}catch (Exception e) {
		}
		return userInfo;
	}
	
	public static UserInfo getFromJsonString(String jsonstring)
	{
		UserInfo userInfo = new UserInfo();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonstring);
			userInfo.setUserName(jsonObject.getString("username"));
			userInfo.setIcon(jsonObject.getString("icon"));
			userInfo.setIP(jsonObject.getString("onlineip"));
			userInfo.setMailAdr(jsonObject.getString("email") );
			userInfo.setLog_lasttime(jsonObject.getString("thisvisit"));
			userInfo.setPostnum(jsonObject.getInt("postnum"));
			userInfo.setMoney(jsonObject.getInt("money"));
			userInfo.setTodaypost(jsonObject.getString("todaypost"));
			userInfo.setWeiwang(jsonObject.getString("rvrc"));			
			userInfo.setUser_type(jsonObject.getString("grouptitle"));
			userInfo.setUid(jsonObject.getInt("uid"));
			userInfo.setFans(jsonObject.getString("fans"));
			userInfo.setFollows(jsonObject.getString("follows"));
			userInfo.setFriends(jsonObject.getString("friends"));
			userInfo.setIntroduce(jsonObject.getString("introduce"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userInfo;
	}
}
