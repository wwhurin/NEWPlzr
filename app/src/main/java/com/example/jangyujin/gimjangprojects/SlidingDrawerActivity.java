package com.example.jangyujin.gimjangprojects;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class SlidingDrawerActivity extends Activity implements View.OnClickListener {

    Button slideButton, b1, b2, b3;

    SlidingDrawer slidingDrawer;



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sliding_drawer);

        slideButton = (Button) findViewById(R.id.slideButton);

        slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);

        b1 = (Button) findViewById(R.id.Button01);

        b2 = (Button) findViewById(R.id.Button02);

        b3 = (Button) findViewById(R.id.Button03);

        b1.setOnClickListener(this);

        b2.setOnClickListener(this);

        b3.setOnClickListener(this);

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

            @Override

            public void onDrawerOpened() {

                slideButton.setText("V");

            }

        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

            @Override

            public void onDrawerClosed() {

                slideButton.setText("^");

            }

        });

    }

    @Override

    public void onClick(View v) {

        Button b = (Button) v;

        Toast.makeText(SlidingDrawerActivity.this,

                b.getText() + " is Clicked :)", Toast.LENGTH_SHORT).show();

    }

}
