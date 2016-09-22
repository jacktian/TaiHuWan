package com.gloria.hbh.util;

/*
 * ����λ�ô�����
 * 2013-9-13
 * gloria
 */
public class GeographyUtil {
	
	static int ScreenWidth = 5976;
	static int ScreenHeight = 2250;
	
	static float minLon = 119.207750f;
	static float maxLon = 119.834400f;
	static float minLat = 31.660050f;
	static float maxLat = 31.867280f;
	
	static float scaleX = ((maxLon-minLon)*3600)/ScreenWidth; //-X����ÿ���ش���ľ���������
	static float scaleY = ((maxLat-minLat)*3600)/ScreenHeight;//-Y����ÿ���ش����γ��������
	
	static int minX = (int) (minLon*3600/scaleX);	//��������������
	static int minY = (int) (minLat*3600/scaleY);	//�������������϶�
	
	/*
	 * ���ݾ�γ�ȸ�����ĻXY
	 * 0:lon  1:lat
	 */
	public static float[] getGeographyByScreenXY(String X,String Y){
		float[] geographyXY = new float[2];
		geographyXY[0] = Integer.valueOf(X) * scaleX/3600 + minLon;
		geographyXY[1] = maxLat - Integer.valueOf(Y) * scaleY/3600;
		return geographyXY;
	}
	
	/*
	 * ���ݾ�γ�ȸ�����ĻXY
	 */
	public static int[] getScreenXYByGeography(float lat,float lon){
		int[] screenXY = new int[2];
		screenXY[0] = (int) ((lon - minLon)*3600/scaleX); 
		screenXY[1] = (int) ((maxLat - lat)*3600/scaleY);
		return screenXY;
	}

}
