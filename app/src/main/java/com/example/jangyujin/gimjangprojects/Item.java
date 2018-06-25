package com.example.jangyujin.gimjangprojects;

import android.widget.DatePicker;
import android.widget.EditText;

public class Item {
    String title;
    String num;

    EditText TName;
    DatePicker mDateS, mDateF;
    EditText content;

    SetFood setFood;

    public SetFood getSetFood() {
        return setFood;
    }

    public EditText getTName() {
        return TName;
    }

    public DatePicker getmDateS() {
        return mDateS;
    }

    public DatePicker getmDateF() {
        return mDateF;
    }

    public EditText getContent() {
        return content;
    }

    String getTitle() {
        return this.title;
    }

    public String getNum() {
        return num;
    }

    Item(String title) {
        this.title = title;
    }

    Item(String title, String num) {
        this.title = title;
        this.num = num;
    }

    Item(String title, String num, EditText TName, EditText content, DatePicker mDateS, DatePicker mDateF, SetFood setFood){
        this.title = title;
        this.num = num;
        this.TName = TName;
        this.content = content;
        this.mDateS  = mDateS;
        this.mDateF = mDateF;
        this.setFood = setFood;
    }
}
