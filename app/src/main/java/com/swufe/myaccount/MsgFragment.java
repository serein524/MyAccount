package com.swufe.myaccount;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MsgFragment extends Fragment {

private List<Msg> msgList = new ArrayList<>();
      private EditText inputText;
      private Button send,add;
      private RecyclerView msgRecyclerView;
      private MsgAdapter adapter;
      private UserManager um;
    private Spinner mySpinner;
    private ArrayAdapter<String> spinadapter;
    private List<String> list = new ArrayList<String>();
    private String category;
    private String uname;
    private String date;
    private String notes = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          View view = inflater.inflate(R.layout.fragment_msg,container);
          return view;
      }
      @Override
      public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
          uname = (String) getArguments().get("uname");
          initMsgs(uname);//自定义初始化数据的函数

          mySpinner = (Spinner) getView().findViewById(R.id.spin);
          spinadapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,list);
          //第三步：为适配器设置下拉列表下拉时的菜单样式。
          spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          //第四步：将适配器添加到下拉列表上
          mySpinner.setAdapter(spinadapter);
          //响应菜单被选中
          mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
              @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  //myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                  category = spinadapter.getItem(position);
//
             }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

              }
          });

          inputText = (EditText) getView().findViewById(R.id.input_text);
          add = (Button)getView().findViewById(R.id.radioAdd);
          send = (Button) getView().findViewById(R.id.send);
          msgRecyclerView = (RecyclerView) getView().findViewById(R.id.msg_recycler_view);
          LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
          msgRecyclerView.setLayoutManager(layoutManager);
          adapter = new MsgAdapter(msgList);
          msgRecyclerView.setAdapter(adapter);
          //type = Msg.TYPE_SEND;
          add.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final EditText et = new EditText(getContext());
                  new AlertDialog.Builder(getContext()).setTitle("请添加备注")
                          .setIcon(android.R.drawable.sym_def_app_icon)
                          .setView(et)
                          .setPositiveButton("添加备注", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {
                                  //按下确定键后的事件
                                  notes = et.getText().toString();
                                  Toast.makeText(getContext(), "您已添加备注", Toast.LENGTH_SHORT).show();
                              }
                          }).setNegativeButton("取消",null).show();
              }
          });
          send.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Date d = new Date();
                  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  date = df.format(d);
                  String money = inputText.getText().toString();
                  String tips = null;
                  if (!"".equals(money)&&isNumber(money)) {

                      String content = money+"元";
                      switch (category){
                          case "饮食":
                              tips = "民以食为天，该吃吃该喝喝";
                              break;
                          case "出行":
                              tips = "读万卷书，行万里路";
                              break;
                          case "娱乐":
                              tips = "好玩！";
                              break;
                          case "医疗":
                              tips = "注意身体哦，多喝热水";
                              break;
                          case "其他":
                              tips = "一些奇奇怪怪的支出";
                              break;
                          case "收入":
                              tips = "有钱啦有钱啦";
                              break;
                          default:
                              
                      }
                      Msg msg1 = new Msg(category,content, Msg.TYPE_SEND);
                      Msg msg2 = new Msg("",tips,Msg.TYPE_RECEICED);
                      msgList.add(msg1);
                      msgList.add(msg2);
                      AccountManager am = new AccountManager(getContext());
                      //date.trim():
                      am.add(uname,category,money,date,notes);
                      Log.i("MsgFragment", "用户名:"+uname );
                      Log.i("MsgFragment", "类别:"+category );
                      Log.i("MsgFragment", "金额:"+money );
                      Log.i("MsgFragment", "日期:"+date );
                      Log.i("MsgFragment", "备注:"+notes );
                      adapter.notifyItemChanged(msgList.size() - 1);//当输入账单时，刷新ListView中的显示，更新数据库
                      msgRecyclerView.scrollToPosition(msgList.size() - 1);//将ListView定位到最后一行
                      inputText.setText("");//消息发出后清空输入框中的内容
                      um = new UserManager(getContext());
                      String mlimit = um.findlimitByName(uname);
                      Float out = am.getOutput(am.queryallbyid(uname));
                      if(!mlimit.equals("暂未设置")&&out>Float.parseFloat(mlimit)){
                          Toast.makeText(getContext(), "已超出本月支出限额哦", Toast.LENGTH_SHORT).show();
                      }
                  }
                  else {
                      Toast.makeText(getContext(), "输入消费/收入金额哦", Toast.LENGTH_SHORT).show();
                  }
              }
          });

      }

              private void initMsgs(String name) {
                  Msg msg = new Msg("",name+"你好呀，快和我一起聊天记账吧", Msg.TYPE_RECEICED);
                  msgList.add(msg);
                  list.add("饮食");
                  list.add("出行");
                  list.add("娱乐");
                  list.add("医疗");
                  list.add("其他");
                  list.add("收入");
              }
    public  boolean isNumber(String s){//合法数字返回true
        String reg = "^[0-9]+(.[0-9]+)?$";
        return  s.matches(reg);
    }

          }