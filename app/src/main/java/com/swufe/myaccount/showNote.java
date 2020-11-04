package com.swufe.myaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class showNote extends AppCompatActivity {
Intent in;
EditText et;
Button bt;
    private SQLiteDatabase db;
private DBHelper dbHelper;
private AccountManager am;
private String notes;
    private String uname,item;
    //private int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        in = getIntent();
        notes = in.getStringExtra("notes");
        uname = in.getStringExtra("uname");
        item = in.getStringExtra("item");
        et = findViewById(R.id.show);
        bt = findViewById(R.id.modify);
        et.setText(notes);
        et.setText(et.getText());
        bt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
               am = new AccountManager(showNote.this);
               String newnotes = et.getText().toString();
               am.update(Integer.parseInt(item),uname,"notes",newnotes);
                Toast.makeText(showNote.this, "修改成功", Toast.LENGTH_SHORT).show();

            }
        });
    }
}