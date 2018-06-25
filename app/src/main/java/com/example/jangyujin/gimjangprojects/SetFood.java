package com.example.jangyujin.gimjangprojects;

import android.util.Log;

public class SetFood {
    String name;
    String num;
    String inputdate;
    String outdate;
    String content;

    SetFood(){

    }

    SetFood(String name, String num, String inputdate, String outdate, String content){
        Log.d("!!!!!!!!!!!!!!","된다!");
        this.name = name;
        this.num = num;
        this.inputdate = inputdate;
        this.outdate = outdate;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }

    public String getOutdate() {
        return outdate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
