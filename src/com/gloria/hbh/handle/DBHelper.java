package com.gloria.hbh.handle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper是一个辅助类，用来管理数据库的创建和版本他，它提供两个方面的功能
 * 第一，getReadableDatabase()、getWritableDatabase()可以获得SQLiteDatabase对象，
 * 通过该对象可以对数据库进行操作 第二，提供了onCreate()、onUpgrade()两个回调函数，允许我们再创建和升级数据库时，进行自己的操作
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;

	/**
	 * 在SQLiteOpenHelper的子类当中，必须有该构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param name
	 *            数据库名称
	 * @param factory
	 * @param version
	 *            当前数据库的版本，值必须是整数并且是递增的状态
	 */

	String table_userlist = "userlist";
	String table_lastviewlist = "table_lastviewlist";
	String create_table_userlist = "create table if not exists " + table_userlist
			+ " (id int,name varchar(20),pwd varchar(20))";
	String create_table_lastviewlist = "create table if not exists " + table_lastviewlist
			+ "(tid varchar(10),title varchar(100),author varchar(50),views varchar(10),replies varchar(10)"
			+ " url varchar(100),date varchar(50))";

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		// 必须通过super调用父类当中的构造函数
		super(context, name, factory, version);
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	// 该函数是在第一次创建的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a database");
		// execSQL用于执行SQL语句
		db.execSQL(table_userlist); // 创建用户列表
		db.execSQL(create_table_lastviewlist); // 创建最近浏览列表
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("upgrade a database");
	}

	public void insertToUserList(int id, String name) {
		// TODO Auto-generated method stub
		System.out.println("insertToUserList");
		// 创建ContentValues对象
		ContentValues values = new ContentValues();
		// 向该对象中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
		values.put("id", id);
		values.put("name", "name");
		// 得到一个可写的SQLiteDatabase对象
		SQLiteDatabase sqliteDatabase = getWritableDatabase();
		// 调用insert方法，就可以将数据插入到数据库当中
		// 第一个参数:表名称
		// 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
		// 第三个参数：ContentValues对象
		sqliteDatabase.insert(table_userlist, null, values);
	}

	/*
	 * 插入记录到最近浏览列表
	 */
	public void insertToLastViewList(String tid, String title, String author, String views, String replies, String url,
			String date) {
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
