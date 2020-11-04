package com.swufe.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class check extends AppCompatActivity {
private String uname;
private ListView listView;
private ImageView iv_backward;
private classifyadapter adapter;
private AccountManager am;
private ArrayList<HashMap<String, String>> all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check);
        //View view = getLayoutInflater().inflate(R.layout.activity_check,null);
        Intent intent = getIntent();
        uname = intent.getStringExtra("uname");
        listView = findViewById(R.id.list_vcheck);
        iv_backward = findViewById(R.id.iv_backward);
        iv_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        am = new AccountManager(this);
        all = am.classifiedlbyid(uname);//这里
        Log.i("check", "list"+all);
        adapter = new classifyadapter(check.this, R.layout.activity_classify_item, all);
        listView.setAdapter(adapter);
    }
}