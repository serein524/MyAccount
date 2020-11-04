package com.swufe.myaccount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //与数据库操作有关的变量
    public static final String DB_NAME="mydata.db";//数据库名称
    public static final String TB_NAME1="tb_users";//表名称
    public static final String TB_NAME2="tb_account";//表名称
    public static String TB_ACCOUNT;
    public static final int VERSION=1;//版本号,则是升级之后的,升级方法请看onUpgrade方法里面的判断

    public DBHelper(@Nullable Context context) {
        super(context,DB_NAME,null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       // int initDBVersion = 1;
        db.execSQL("create table "+TB_NAME1+"(id integer primary key autoincrement,uname text unique,phone text,password text,mlimit text)");
        db.execSQL("create table "+TB_NAME2+"(item integer primary key autoincrement,uname text,category text,money text,time text,notes text)");
        //onUpgrade(db, initDBVersion, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
