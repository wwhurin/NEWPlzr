package com.example.jangyujin.gimjangprojects;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RefActivity extends Activity{
    DatePicker mDate;
    final int ITEM_SIZE = 6;
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.ref_d);

        mDate = (DatePicker)findViewById(R.id.datepicker_start);

        //처음 DatePicker를 오늘 날짜로 초기화한다.

        //그리고 리스너를 등록한다.

        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {
                        // TODO Auto-generated method stub

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                    }
                });
        mDate = (DatePicker)findViewById(R.id.datepicker_finish);

        //처음 DatePicker를 오늘 날짜로 초기화한다.

        //그리고 리스너를 등록한다.

        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {
                        // TODO Auto-generated method stub

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                    }
                });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item("#1");
        item[1] = new Item("#2");
        item[2] = new Item("#3");
        item[3] = new Item("#4");
        item[4] = new Item("#5");
        item[5] = new Item("#5");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter2(getApplicationContext(), items, R.layout.rec_d));
    }
}
