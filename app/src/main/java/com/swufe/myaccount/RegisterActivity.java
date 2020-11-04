package com.swufe.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.swufe.myaccount.DBHelper.TB_NAME1;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername,edtPhone,edtPassword;
    private Button btnConfirm;
    private SQLiteDatabase db;
    private DBHelper dbHelper;      //数据库帮助工具类
    private ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        String uname = "" + bundle.getString("uname");
//        String phone = "" + bundle.getString("phone");
//        String password = "" + bundle.getString("password");

        edtUsername = findViewById(R.id.edt_1);
        edtPhone = findViewById(R.id.edt_2);
        edtPassword = findViewById(R.id.edt_3);
        btnConfirm = findViewById(R.id.btn_regis);
        btnConfirm.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
        String uname = edtUsername.getText().toString();
        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();
        UserManager um = new UserManager(RegisterActivity.this);
        if(!uname.equals("")) {
            um.add(uname, phone, password);
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(RegisterActivity.this, "用户名为空", Toast.LENGTH_SHORT).show();
    }
});
    }
}