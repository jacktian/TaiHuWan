package com.gloria.hbh.data.forum;

/**
 * �����ṩÿҳ��ʾ���������ܹ��ж���ҳ
 * @author gejian
 * 2013-1-23
 */

public class Page {
	
	public static int perPage=0;     //ÿҳ��ʾ������
	public static int curPage=1;		//��ǰ������ҳ
	public static int topic = 1;		//��������
	public static int total_page = 1;	//�ܹ�ҳ��
	
	public Page() {
		
	}
//	public Page(int perPage, int curPage,int topic) {
//		Page.perPage = perPage;
//		Page.curPage = curPage;
//		Page.topic = topic;
////		setTotal_page(); //����ҳ��
//	}
	
	public int getTopic() {
		if(topic == 0){
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
//		if(total_page == 0){
			setTotal_page();
//		}
		return total_page;
	}

	public void setTotal_page() {
		if(perPage !=0 && perPage > 0)
			total_page = (getTopic()+getPerPage()-1)/getPerPage();
	}

	public int getPerPage() {
		return perPage;
	}
	public int getCurPage() { 
		return curPage;
	}
}
