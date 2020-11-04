package com.swufe.myaccount;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private UserManager um;
    private ImageView iv_modify,iv_gocheck;
    private TextView tv_name,tvs_limit;
    private Button bt_logout;
    private Intent intent;
    private String uname;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uname = (String) getArguments().get("uname");
        initview();

        iv_gocheck = getView().findViewById(R.id.iv_gocheck);
        iv_modify = getView().findViewById(R.id.iv_modify);
        bt_logout = getView().findViewById(R.id.btn_logout);
        iv_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), myinfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("uname", uname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        iv_gocheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), check.class);
                Bundle bundle = new Bundle();
                bundle.putString("uname", uname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("uname",null);
                editor.apply();
                intent = new Intent(getContext(), MainActivity.class);
                Toast.makeText(getContext(),"注销成功",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
    private void initview(){
        tv_name = getView().findViewById(R.id.name);
        tv_name.setText(uname);
        um = new UserManager(getContext());
        String mlimit = um.findlimitByName(uname);
        tvs_limit = getActivity().findViewById(R.id.tvs_limit);
        tvs_limit.setText(mlimit);

    }
    @Override
    public void onResume(){
        super.onResume();
        um = new UserManager(getContext());
        String mlimit = um.findlimitByName(uname);
        tvs_limit = getActivity().findViewById(R.id.tvs_limit);
        tvs_limit.setText(mlimit);
    }
}

