package com.swufe.myaccount;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    String uname;
    ListView listView;
    TextView input, output;
    ArrayList<HashMap<String, String>> all;
    AccountManager am;
    AccountAdapter adapter;
    Date now;
    Intent intent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_home, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uname = (String) getArguments().get("uname");
        input = getView().findViewById(R.id.input);
        output = getView().findViewById(R.id.output);
        listView = getView().findViewById(R.id.list_view);
        now = new Date();
        am = new AccountManager(getContext());
        all = am.queryallbyid(uname);
        output.setText(am.getOutput(all) + "");
        input.setText(am.getInput(all) + "");
        //tv.setText(all.toString());
        adapter = new AccountAdapter(getContext(), R.layout.account_item, all);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new show());

    }


    //fragment跳转时的数据更新
    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {
            am = new AccountManager(getContext());
            all = am.queryallbyid(uname);
            output.setText(am.getOutput(all) + "");
            input.setText(am.getInput(all) + "");
            adapter = new AccountAdapter(getContext(), R.layout.account_item, all);
            listView.setAdapter(adapter);

            Toast.makeText(getContext(), "刷新嗷", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        am = new AccountManager(getContext());
        all = am.queryallbyid(uname);
        output.setText(am.getOutput(all) + "");
        input.setText(am.getInput(all) + "");
        adapter = new AccountAdapter(getContext(), R.layout.account_item, all);
        listView.setAdapter(adapter);
    }
    public class show implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 方法一：使用position定位到Hashmap，从中取得数据
            Object itemAtPosition = listView.getItemAtPosition(position);
            HashMap<String, String> map = (HashMap<String, String>) itemAtPosition;

            String notes = map.get("notes");
            String item = map.get("item");
            Log.i("Listview", "备注获取"+item );
            intent = new Intent(getContext(), showNote.class);
            intent.putExtra("item",item);
            intent.putExtra("uname",uname);
            intent.putExtra("notes",notes);
            startActivity(intent);
        }

    }


}