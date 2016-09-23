package com.gloria.hbh.data.forum;

/**
 * 本类提供每页显示的条数和总共有多少页
 * 
 * @author gejian 2013-1-23
 */

public class Page {

	public static int perPage = 0; // 每页显示的条数
	public static int curPage = 1; // 当前共多少页
	public static int topic = 1; // 总主题数
	public static int total_page = 1; // 总共页数

	public Page() {

	}
	// public Page(int perPage, int curPage,int topic) {
	// Page.perPage = perPage;
	// Page.curPage = curPage;
	// Page.topic = topic;
	//// setTotal_page(); //计算页数
	// }

	public int getTopic() {
		if (topic == 0) {
			topic = 1;
		}
		return topic;
	}

	public void setPerPage(int perPage) {
		Page.perPage = perPage;
	}

	public void setCurPage(int curPage) {
		Page.curPage = curPage;
	}

	public void setTopic(int topic) {
		Page.topic = topic;
	}

	public int getTotal_page() {
		// if(total_page == 0){
		setTotal_page();
		// }
		return total_page;
	}

	public void setTotal_page() {
		if (perPage != 0 && perPage > 0)
			total_page = (getTopic() + getPerPage() - 1) / getPerPage();
	}

	public int getPerPage() {
		return perPage;
	}

	public int getCurPage() {
		return curPage;
	}
}
