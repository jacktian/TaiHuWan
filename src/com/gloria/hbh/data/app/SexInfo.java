package com.gloria.hbh.data.app;

/*
 * 性别的数据结构
 */
public class SexInfo {

	private int sex_id ; //1：男 2：女
	private String code = ""; //代码
	private String name = ""; // "男"
	public int getSex_id() {
		return sex_id;
	}
	public void setSex_id(int sex_id) {
		this.sex_id = sex_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
