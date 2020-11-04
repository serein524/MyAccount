package com.swufe.myaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import static com.swufe.myaccount.DBHelper.TB_NAME1;
import static com.swufe.myaccount.DBHelper.TB_NAME2;

public class UserManager {

    private String TBNAME;
    private SQLiteDatabase db;
    private DBHelper dbHelper;      //数据库帮助工具类
    private ContentValues values;

    public UserManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME1;
    }
    public void add(String uname,String phone,String password){
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("uname",uname);
        values.put("phone",phone);
        values.put("password",password);
        values.put("mlimit","暂未设置");
        db.insert(TB_NAME1,null,values);
        db.close();

    }
    public String findByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "uname=?", new String[]{name}, null, null, null);
        String psw = null;
        if(cursor!=null && cursor.moveToFirst()){
            psw = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
        }
        db.close();
        return psw;
    }
    public String findtelByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "uname=?", new String[]{name}, null, null, null);
        String tel = null;
        if(cursor!=null && cursor.moveToFirst()){
            tel = cursor.getString(cursor.getColumnIndex("phone"));
            cursor.close();
        }
        db.close();
        return tel;
    }
    public String findlimitByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "uname=?", new String[]{name}, null, null, null);
        String mlimit = "暂未设置";
        if(cursor!=null && cursor.moveToFirst()){
            mlimit = cursor.getString(cursor.getColumnIndex("mlimit"));
            cursor.close();
        }
        db.close();
        return mlimit;
    }
    public void update(String uname,String []key,String []value){
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
        for(int i=0;i<key.length;i++){
            values.put(key[i],value[i]);
        }
        db.update(TB_NAME1,values,"uname=?",new String[]{uname});
        db.close();
    }

}
