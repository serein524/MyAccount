package com.swufe.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
private Button btnLogin,btnRegister;
private EditText etUsername,etPassword;
private SQLiteDatabase db;
private DBHelper dbHelper;      //数据库帮助工具类
private ContentValues values;
private String username;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_1);
        etPassword = findViewById(R.id.et_2);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        String name=sp.getString("uname",null);
        Log.i(TAG,"读取数据"+name);
        if(name!=null){
            Intent intent= new Intent(MainActivity.this, FrameActivity.class);
            intent.putExtra("uname",name);
            startActivity(intent);
        }




        btnLogin.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //保存本地用户账号数据
                SharedPreferences sp = getSharedPreferences("user", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("uname",username);
                editor.apply();

                UserManager um = new UserManager(MainActivity.this);
                if(!username.equals("")) {
                    String psw = um.findByName(username);
                    Log.i(TAG, "onclicked"+psw);
                    if(password.equals(psw)){
                        Intent intent= new Intent(MainActivity.this, FrameActivity.class);
                        intent.putExtra("uname",username);
                        startActivity(intent);
                    }else
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String phone = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Intent o = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(o);


            }
    });


    }
}