package com.gloria.hbh.data.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.gloria.hbh.data.app.TabInfo.TabInfoTypeConstants;

/**
 * 论坛信息
 * 
 * @author gejian 2013-1-23
 */
public class Forum {
	ForumInfo foruminfo = null; // 论坛基本信息
	ArrayList<TabInfo> main_layout = null; // 论坛UI布局相关信息

	public ForumInfo getForumInfo() {
		if (foruminfo == null) {
			foruminfo = new ForumInfo();
		}
		return foruminfo;
	}

	Forum() {
		foruminfo = new ForumInfo();
		main_layout = new ArrayList<TabInfo>(1);
	}

	public void setForumInfo(ForumInfo foruminfo) {
		this.foruminfo = foruminfo;
	}

	public ArrayList<TabInfo> getMainLayout() {
		if (main_layout == null) {
			main_layout = new ArrayList<TabInfo>(1);
		}
		return main_layout;
	}

	public TabInfo getTabMore() {
		if (main_layout != null) {
			for (TabInfo tabInfo : main_layout) {
				if (tabInfo.getTabType() == TabInfoTypeConstants.TAB_MORE) {
					return tabInfo;
				}
			}
		}
		return null;
	}

	public void setMainLayout(ArrayList<TabInfo> main_layout) {
		if (main_layout == null || main_layout.size() == 0) {
			return;
		}
		this.main_layout = main_layout;
	}

	@SuppressWarnings("unchecked")
	public static Forum getForum(HashMap<String, Object> root) {
		if (root == null) {
			return null;
		}
		Forum forum = new Forum();
		forum.setForumInfo(ForumInfo.getForumInfo((HashMap<String, Object>) root.get("forum_info")));
		forum.setMainLayout(getTabInfos((ArrayList<Object>) root.get("main_layout")));
		return forum;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<TabInfo> getTabInfos(ArrayList<Object> root) {
		// TODO Auto-generated method stub
		if (root == null) {
			return null;
		}
		ArrayList<TabInfo> tabInfos = new ArrayList<TabInfo>();
		for (int i = 0; i < root.size(); i++) {
			HashMap<String, Object> tab = (HashMap<String, Object>) root.get(i);
			tabInfos.add(TabInfo.getTabInfo(tab, i));
		}
		return tabInfos;
	}

}
