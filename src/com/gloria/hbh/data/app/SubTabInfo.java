package com.gloria.hbh.data.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.gloria.hbh.main.R;

/**
 * 顶部二级分栏名称，包含二级分栏数组
 * @author gejian
 * 2013-1-23
 */
public class SubTabInfo {
	
	public static final String TAG = SubTabInfo.class.getName();
	
	public class SubTabInfoTypeConstants {
		public static final int SUBCATE_RECENT_VIEWED_TOPICS = 0;
		public static final int SUBCATE_HEADLINE_IMAGE = 1; //头条小图
		public static final int SUBCATE_ACTIVITY = 2; //活动
		public static final int SUBCATE_COMMON = 3; //活动
		public static final int SUBCATE_TOURIST = 4; //吃住游 的景点
	}
	
//	public class GroupKeyConstants {
//		//更多的设置类型的类别
//		public static final String GROUPKEY_NAVIGATION = "导航"; 
//		public static final String GROUPKEY_MORE = "更多";  
//	}
	
	String id = ""; //表示类型对应的板块id号，此处格式比较灵活，如果是多个id号，中间用逗号隔开，也可以为经过rul encode编码后的中文字符
	String bid = ""; //表示顶部大图的id号，若bid不为空，表明此页面需要显示大图
	int type = SubTabInfoTypeConstants.SUBCATE_HEADLINE_IMAGE; //表示对应二级分栏的类型
	String name = ""; //表示对应二级分栏的名称
	
	String value = ""; //在更多时，设置对应的默认初始值
	ArrayList<SettingInfo> setting; //设置选项的列表
	
	String subtab_id = ""; //该Tab对应的Fid
	int subtab_res_backgroud = R.drawable.tabsub_btnbg_default; //tab的背景资源
	int subtab_view_ID = -1; //该Tab对应的View 的资源
	
	public String getId() {
		return id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		if(value == null){
			return;
		}
		this.value = value;
	}
	public void setId(String id) {
		if(id == null){
			return;
		}
		this.id = id;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		if(bid == null){
			return;
		}
		this.bid = bid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setType(String type) {
		if(type == null){
			return;
		}else if(type.equals("subcate_recent_viewed_topic")){
			this.type = SubTabInfoTypeConstants.SUBCATE_RECENT_VIEWED_TOPICS;
		}else if(type.equals("subcate_headline_image")){
			this.type = SubTabInfoTypeConstants.SUBCATE_HEADLINE_IMAGE;
		}else if(type.equals("subcate_activity")){
			this.type = SubTabInfoTypeConstants.SUBCATE_ACTIVITY;
		}else if(type.equals("subcate_common")){
			this.type = SubTabInfoTypeConstants.SUBCATE_COMMON;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name == null){
			return;
		}
		this.name = name;
	}
	public String getSubTabId() {
		return subtab_id;
	}
	public void setSubTabId(String subtab_id) {
		this.subtab_id = subtab_id;
	}
	public int getSubTabResBackgroud() {
		return subtab_res_backgroud;
	}
	public void setSubTabResBackgroud(int subtab_res_backgroud) {
		this.subtab_res_backgroud = subtab_res_backgroud;
	}
	public int getSubTabViewID() {
		return subtab_view_ID;
	}
	public void setSubTabViewID(int subtab_view_ID) {
		this.subtab_view_ID = subtab_view_ID;
	}
	
	@SuppressWarnings("unchecked")
	public static SubTabInfo getSubTabInfo(HashMap<String, Object> root) {
		if(root == null){
			return null;
		}
		SubTabInfo subTabInfo = new SubTabInfo();
		subTabInfo.setType((String)root.get("type"));
		subTabInfo.setName((String)root.get("name"));
		subTabInfo.setId((String)root.get("id"));
		subTabInfo.setBid((String)root.get("bid"));
		return subTabInfo;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<SubTabInfo> getSubTabInfos(ArrayList<Object> root) {
		// TODO Auto-generated method stub
		if(root == null){
			return null;
		}
		ArrayList<SubTabInfo> subTabInfos = new ArrayList<SubTabInfo>();
		for(int i = 0; i < root.size(); i++){
			HashMap<String, Object> subtab = (HashMap<String, Object>) root.get(i);
			subTabInfos.add(SubTabInfo.getSubTabInfo(subtab));
		}
		return subTabInfos;
	}
}
