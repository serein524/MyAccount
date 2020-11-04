package com.swufe.myaccount;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class FrameActivity extends FragmentActivity {
    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome, rbtFunc, rbtSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fragment);
        Intent in = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("uname",in.getStringExtra("uname"));

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_home);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_msg);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_setting);
        mFragments[0].setArguments(bundle);
        mFragments[1].setArguments(bundle);
        mFragments[2].setArguments(bundle);
        Log.i("radioGroup", "fragment0"+mFragments[0] );
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();



        rbtHome = (RadioButton) findViewById(R.id.radioHome);
        rbtFunc = (RadioButton) findViewById(R.id.radioMsg);
        rbtSetting = (RadioButton) findViewById(R.id.radioSetting);
        radioGroup = (RadioGroup) findViewById(R.id.bottomGroup);
        initView();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup", "checkId=" + checkedId);
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                switch (checkedId) {
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                        case R.id.radioMsg:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    case R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void initView() {

        //定义底部标签图片大小和位置
        Drawable drawable_home = getResources().getDrawable(R.drawable.home);
        drawable_home.setBounds(0, 0, 50, 60);
        //设置图片在文字的哪个方向
        rbtHome.setCompoundDrawables(null, drawable_home, null, null);

        Drawable drawable_chat = getResources().getDrawable(R.drawable.chat);
        drawable_chat.setBounds(0, 0, 50, 60);
        rbtFunc.setCompoundDrawables(null, drawable_chat, null, null);

        Drawable drawable_personal = getResources().getDrawable(R.drawable.personal);
        drawable_personal.setBounds(0, 0, 50, 60);
        rbtSetting.setCompoundDrawables(null, drawable_personal, null, null);
    }



}
