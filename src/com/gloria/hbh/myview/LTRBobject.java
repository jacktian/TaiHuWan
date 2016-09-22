package com.gloria.hbh.myview;

/*
 * 扇形菜单的对象
 */
public class LTRBobject {
	public float left,top,right,bottom;

	public LTRBobject(float left, float top, float right, float bottom) {
		super();
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	/**
	 * @return the left
	 */
	public float getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(float left) {
		this.left = left;
	}

	/**
	 * @return the top
	 */
	public float getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(float top) {
		this.top = top;
	}

	/**
	 * @return the right
	 */
	public float getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(float right) {
		this.right = right;
	}

	/**
	 * @return the bottom
	 */
	public float getBottom() {
		return bottom;
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(float bottom) {
		this.bottom = bottom;
	}
	
}
