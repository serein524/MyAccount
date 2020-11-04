package com.swufe.myaccount;

public class Msg {
    public static  final int TYPE_RECEICED = 0;
    public static  final int TYPE_SEND = 1;

    private String category;
    private String content;
    private int type;
    public Msg(String category,String content,int type){
        this.category = category;
        this.content = content;
        this.type = type;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setType(int type){
        this.type = type;
    }
    public String getCategory(){
        return category;
    }//在后面设置文本内容时调用
    public String getContent(){
        return category+" "+content;
    }//在后面设置文本内容时调用
    public int getType(){
        return type;
    }//条件语句的判断依据
}


