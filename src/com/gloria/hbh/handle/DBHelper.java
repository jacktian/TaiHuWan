package com.gloria.hbh.handle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * SQLiteOpenHelper��һ�������࣬�����������ݿ�Ĵ����Ͱ汾�������ṩ��������Ĺ���
 * ��һ��getReadableDatabase()��getWritableDatabase()���Ի��SQLiteDatabase����ͨ���ö�����Զ����ݿ���в���
 * �ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ٴ������������ݿ�ʱ�������Լ��Ĳ���
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;

	/**
	 * ��SQLiteOpenHelper�����൱�У������иù��캯��
	 * @param context	�����Ķ���
	 * @param name		���ݿ�����
	 * @param factory
	 * @param version	��ǰ���ݿ�İ汾��ֵ���������������ǵ�����״̬
	 */
	
	String table_userlist = "userlist";
	String table_lastviewlist = "table_lastviewlist";
	String create_table_userlist = "create table if not exists "+table_userlist+" (id int,name varchar(20),pwd varchar(20))";
	String create_table_lastviewlist = "create table if not exists "+table_lastviewlist
			+"(tid varchar(10),title varchar(100),author varchar(50),views varchar(10),replies varchar(10)" 
			+" url varchar(100),date varchar(50))";
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		//����ͨ��super���ø��൱�еĹ��캯��
		super(context, name, factory, version);
	}
	
	public DBHelper(Context context, String name, int version){
		this(context,name,null,version);
	}

	public DBHelper(Context context, String name){
		this(context,name,VERSION);
	}

	//�ú������ڵ�һ�δ�����ʱ��ִ�У�ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a database");
		//execSQL����ִ��SQL���
		db.execSQL(table_userlist);  //�����û��б�
		db.execSQL(create_table_lastviewlist);  //�����������б�
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("upgrade a database");
	}
	
	public void insertToUserList(int id,String name) {
		// TODO Auto-generated method stub
		System.out.println("insertToUserList");
		// ����ContentValues����
		ContentValues values = new ContentValues();
		// ��ö����в����ֵ�ԣ����м���������ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ��
		values.put("id", id);
		values.put("name", "name");
		// �õ�һ����д��SQLiteDatabase����
		SQLiteDatabase sqliteDatabase = getWritableDatabase();
		// ����insert�������Ϳ��Խ����ݲ��뵽���ݿ⵱��
		// ��һ������:������
		// �ڶ���������SQl������һ�����У����ContentValues�ǿյģ���ô��һ�б���ȷ��ָ��ΪNULLֵ
		// ������������ContentValues����
		sqliteDatabase.insert(table_userlist, null, values);
	}
	
	/*
	 * �����¼���������б�
	 */
	public void insertToLastViewList(String tid,String title,String author,
			 String views,String replies,String url,String date) {
		// TODO Auto-generated method stub
		System.out.println("insertToUserList");
		ContentValues values = new ContentValues();
		values.put("tid", tid);
		values.put("title", "title");
		values.put("author", author);
		values.put("views", "views");
		values.put("replies", replies);
		values.put("url", "url");
		values.put("date", "date");
		SQLiteDatabase sqliteDatabase = getWritableDatabase();
		sqliteDatabase.insert(table_lastviewlist, null, values);
	}
	
}
