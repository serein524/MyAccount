package com.swufe.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class myinfo extends AppCompatActivity {
private String uname;
private ImageView iv_backward;
private EditText et_name,et_tel,et_limit;
private TextView tv_set;
private UserManager um;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        Intent intent = getIntent();

        uname = intent.getStringExtra("uname");
        um = new UserManager(myinfo.this);

        String tel = um.findtelByName(uname);
        et_name = findViewById(R.id.et_name);
        et_tel = findViewById(R.id.et_tel);
        et_limit = findViewById(R.id.et_limit);
        tv_set = findViewById(R.id.tv_set);

        iv_backward = findViewById(R.id.iv_backward);
        et_name.setText(uname);
        et_tel.setText(tel);
        iv_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String tel = et_tel.getText().toString();
                String mlimit = et_limit.getText().toString();
                String x[] = {"uname","phone","mlimit"};
                String y[] = {name,tel,mlimit};
                um.update(uname,x,y);
                Toast.makeText(myinfo.this, "修改完成", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}