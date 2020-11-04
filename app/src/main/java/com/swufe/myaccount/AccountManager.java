package com.swufe.myaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.swufe.myaccount.DBHelper.TB_NAME1;
import static com.swufe.myaccount.DBHelper.TB_NAME2;

public class AccountManager {
    private String TBNAME;
    private SQLiteDatabase db;
    private DBHelper dbHelper;      //数据库帮助工具类
    private ContentValues values;
    private Date now;

    public AccountManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME2;
    }

    public void add(String uname, String category, String money, String time, String notes) {
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("uname", uname);
        values.put("category", category);
        values.put("money", money);
        values.put("time", time);
        values.put("notes", notes);
        db.insert(TB_NAME2, null, values);
        db.close();
    }

    public void update(int item, String uname, String key, String value) {
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(key, value);
        db.update(TB_NAME2, values, "item=? and uname=?", new String[]{String.valueOf(item), uname});
        db.close();
    }

    public ArrayList<HashMap<String, String>> queryallbyid(String uname) {
        db = dbHelper.getWritableDatabase();
        String selsctQuery = "SELECT * from " + TB_NAME2 + " where uname = ?";
        Cursor cursor = db.rawQuery(selsctQuery, new String[]{uname});
        ArrayList<HashMap<String, String>> accountList = new ArrayList<HashMap<String, String>>();
        //Cursor cursor = db.query(TB_NAME2, null, null, new String[]{uname}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> account = new HashMap<String, String>();
                //遍历CURSOR对象，取出数据并打印
                int item = cursor.getInt(cursor.getColumnIndex("item"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String money = cursor.getString(cursor.getColumnIndex("money"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String notes = cursor.getString(cursor.getColumnIndex("notes"));
                account.put("item", item + "");
                account.put("category", category);
                account.put("money", money);
                account.put("time", time);
                account.put("notes", notes);
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }

    public ArrayList<HashMap<String, String>> classifiedlbyid(String uname) {

        float eat = 0, go = 0, fun = 0, medical = 0, other = 0, income = 0;

        ArrayList<HashMap<String, String>> aist = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, String>> all = queryallbyid(uname);
        String index[] = {"饮食", "出行", "娱乐", "医疗", "其他", "收入"};
        float sum[] = new float[index.length];
        for (int i = 0; i < all.size(); i++) {
            String category = all.get(i).get("category");
            switch (category) {
                case "饮食":
                    sum[0] += Float.parseFloat(all.get(i).get("money"));
                    break;
                case "出行":
                    sum[1] += Float.parseFloat(all.get(i).get("money"));
                    break;
                case "娱乐":
                    sum[2] += Float.parseFloat(all.get(i).get("money"));
                    break;
                case "医疗":
                    sum[3] += Float.parseFloat(all.get(i).get("money"));
                    break;
                case "其他":
                    sum[4] += Float.parseFloat(all.get(i).get("money"));
                    break;
                case "收入":
                    sum[5] += Float.parseFloat(all.get(i).get("money"));
                    break;
            }

        }
        //Log.i("percheck", "all"+index[2]+sum[2]);

        for (int j = 0; j < index.length; j++) {
            HashMap<String, String> classfy = new HashMap<String, String>();
            classfy.put("index", index[j]);
            classfy.put("sum", sum[j] + "元");
            aist.add(classfy);
            //Log.i("percheck", "all"+aist);
        }
        Log.i("check", "all" + aist);


        return aist;
    }

    public Float getOutput(ArrayList<HashMap<String, String>> list) {
        int month = 0;
        float output = 0;
        now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            try {
                month = df.parse(list.get(i).get("time")).getMonth();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (month == now.getMonth() && !list.get(i).get("category").equals("收入")) {
                output += Float.parseFloat(list.get(i).get("money"));
            }
        }
        return  output;
    }
    public Float getInput(ArrayList<HashMap<String, String>> list) {

        int month = 0;
        float input = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            try {
                month = df.parse(list.get(i).get("time")).getMonth();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (month == now.getMonth() && list.get(i).get("category").equals("收入")) {
                input += Float.parseFloat(list.get(i).get("money"));
            }
        }

        return input;
    }
}