package com.gloria.hbh.data.forum;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * 展区的信息
 */
public class ExhibitionInfo {
	int id = -1;
	int level;
	String title = "";
	String subtitle = "";
	String detail = "";
	String point = "";
	ArrayList<String> images;
	ArrayList<ExhibitionInfo> sublist;
	static int count = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public ArrayList<ExhibitionInfo> getSublist() {
		return sublist;
	}

	public void setSublist(ArrayList<ExhibitionInfo> sublist) {
		this.sublist = sublist;
	}

	public static ArrayList<String> getImages(ArrayList<Object> root) {
		if (root == null) {
			return null;
		}
		ArrayList<String> images = new ArrayList<String>();
		for (int i = 0; i < root.size(); i++) {
			images.add((String) root.get(i));
		}
		return images;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<ExhibitionInfo> getExhibitionInfo(ArrayList<Object> root) {
		if (root == null) {
			return null;
		}
		ArrayList<ExhibitionInfo> exhibitionInfos = new ArrayList<ExhibitionInfo>();
		for (int i = 0; i < root.size(); i++) {
			HashMap<String, Object> info = (HashMap<String, Object>) root.get(i);
			ExhibitionInfo exhibitionInfo = new ExhibitionInfo();
			exhibitionInfo.setTitle((String) info.get("title"));
			exhibitionInfo.setLevel((Integer) info.get("level"));
			if ((ArrayList<String>) info.get("Objects") != null) {
				exhibitionInfo.setSublist(ExhibitionInfo.getExhibitionInfo((ArrayList<Object>) info.get("Objects")));
			} else {
				if ((String) info.get("subtitle") != null) {
					exhibitionInfo.setSubtitle((String) info.get("subtitle"));
				}
				if ((String) info.get("detail") != null) {
					exhibitionInfo.setDetail((String) info.get("detail"));
				}
				if ((String) info.get("point") != null) {
					exhibitionInfo.setPoint(((String) info.get("point")).trim().replace("{", "").replace("}", ""));
					exhibitionInfo.setId(count);
					count++;
				}
				if ((ArrayList<String>) info.get("images") != null) {
					exhibitionInfo.setImages(getImages((ArrayList<Object>) info.get("images")));
				}
			}
			exhibitionInfos.add(exhibitionInfo);
		}
		return exhibitionInfos;
	}
}
