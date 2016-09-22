package com.gloria.hbh.data.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 底部TabButton功能  （一级菜单）
 * @author gejian
 * 2013-1-23
 */
public class TabInfo {
	
	public class TabInfoTypeConstants {
		public static final int TAB_SUBCATE_BELOW_NAVI = 0;//导航下面有二级分栏
		public static final int TAB_SUBCATE_ON_NAVI = 1;      //导航顶部点击下拉二级分栏
		public static final int TAB_SUBCATE_NONE = 2;            //没有二级分栏
		public static final int TAB_MORE = 3;            	//更多分栏
		public static final int TAB_MYINFO = 4;    			//我的资料分栏
	}

	public static final String TAG = TabInfo.class.getName();
	
	int tab_type = TabInfoTypeConstants.TAB_SUBCATE_BELOW_NAVI;//底部一级分类的类型
	String tab_name = "";//底部一级分类的名称
	String navi_name = "";//一级分类对应顶部名称
	ArrayList<SubTabInfo> subTabInfos = null; //顶部二级分栏名称，包含二级分栏数组
	
	private String tab_id = ""; //该Tab对应的Fid
//	private int tab_res_backgroud = R.drawable.tab_btnbg_default; //tab的背景资源
	private String tab_icon = "";
	private int tab_view_ID = -1; //该Tab对应的View 的资源
	private int index = 0; //该Tab对应的List的索引
	
	public TabInfo(){
		subTabInfos = new ArrayList<SubTabInfo>(1);
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getTab_icon() {
		if(tab_icon.equals("")){
			tab_icon = "tab_icon"+"_"+(getIndex()+1);
		}
		return tab_icon;
	}
	
	public String getTabId() {
		return tab_id;
	}

	public void setTabId(String tab_id) {
		this.tab_id = tab_id;
	}

//	public int getTabResBackgroud() {
//		return tab_res_backgroud;
//	}
//	
//	public int getTabResBackgroud(int i) {
//		return tab_res_backgroud;
//	}
//
//	public void setTabResBackgroud(int tab_res_backgroud) {
//		this.tab_res_backgroud = tab_res_backgroud;
//	}

	public int getTabViewID() {
		return tab_view_ID;
	}

	public void setTabViewID(int tab_view_ID) {
		this.tab_view_ID = tab_view_ID;
	}

	public String getTabName() {
		return tab_name;
	}

	public void setTabName(String tab_name) {
		this.tab_name = tab_name;
	}

	public int getTabType() {
		return tab_type;
	}

	public void setTabType(String tab_type) {
		if(tab_type.equals("tab_subcate_below_navi")){
			this.tab_type = TabInfoTypeConstants.TAB_SUBCATE_BELOW_NAVI;
		}else if(tab_type.equals("tab_subcate_on_navi")){
			this.tab_type = TabInfoTypeConstants.TAB_SUBCATE_ON_NAVI;
		}else if(tab_type.equals("tab_subcate_none")){
			this.tab_type = TabInfoTypeConstants.TAB_SUBCATE_NONE;
		}else if(tab_type.equals("tab_more")){
			this.tab_type = TabInfoTypeConstants.TAB_MORE;
		}else if(tab_type.equals("tab_myinfo")){
			this.tab_type = TabInfoTypeConstants.TAB_MYINFO;
		}
	}
	
	public void setTabType(int tab_type) {
		this.tab_type = tab_type;
	}

	public String getNaviName() {
		return navi_name;
	}

	public void setNaviName(String navi_name) {
		this.navi_name = navi_name;
	}

	public ArrayList<SubTabInfo> getSubTabInfos() {
		if(subTabInfos == null){
			subTabInfos = new ArrayList<SubTabInfo>(1);
		}
		return subTabInfos;
	}

	public void setSubTabInfos(ArrayList<SubTabInfo> subTabInfos) {
		if(subTabInfos == null){
			return;
		}
		this.subTabInfos = subTabInfos;
	}

	@SuppressWarnings("unchecked")
	public static TabInfo getTabInfo(HashMap<String, Object> root,int index) {
		if(root == null){
			return null;
		}
		TabInfo tabInfo = new TabInfo();
		tabInfo.setTabName((String)root.get("tab_name"));
		tabInfo.setTabType((String)root.get("tab_type"));
		tabInfo.setNaviName((String)root.get("navi_name"));
		tabInfo.setIndex(index);
		switch (tabInfo.getTabType()) {
		case TabInfoTypeConstants.TAB_MORE:
			tabInfo.setSubTabInfos(SubTabInfo.getSubTabInfos((ArrayList<Object>)root.get("more_list")));
			break;
		default:
			tabInfo.setSubTabInfos(SubTabInfo.getSubTabInfos((ArrayList<Object>)root.get("subcategory")));
			break;
		}
		return tabInfo;
	}
}
