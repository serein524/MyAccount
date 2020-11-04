package com.swufe.myaccount;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class classifyadapter extends ArrayAdapter {
    private static final String TAG = "ArrayAdapter";
    private Context context;
    private int recource;
    private ArrayList<HashMap<String,String>> list;
    public classifyadapter(Context context,
                          int resource,
                          ArrayList<HashMap<String,String>> list) {
        super(context, resource, list);
        this.list=list;
        this.context=context;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public HashMap<String, String> getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_classify_item,////LayoutInflater是用来找layout下xml布局文件，并且实例化
                    parent,
                    false);
        }
        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView category = (TextView) itemView.findViewById(R.id.category);
        TextView money = (TextView) itemView.findViewById(R.id.money);
        Log.i("adapter", "list"+map);
        category.setText(map.get("index"));
        money.setText(map.get("sum"));
        Log.i("adapter", "a+"+map.get("index")+map.get("sum"));
        return itemView;
    }
}

