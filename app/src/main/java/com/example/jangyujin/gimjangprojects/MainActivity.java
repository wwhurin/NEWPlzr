package com.example.jangyujin.gimjangprojects;

import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    final int ITEM_SIZE = 5;
    private DrawerLayout mDrawerLayout;
    int index = 0;
    Button btn;
    ImageView imageView1 = null;
    RadioGroup orientation;
    ImageView img;
    Drawable ref;
    Drawable rec;
    Drawable tip;
    ImageButton button1,button2,button3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));

        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_one_day);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items1 = new ArrayList<>();
        Item[] item1 = new Item[ITEM_SIZE];
        item1[0] = new Item("##1##");
        item1[1] = new Item("##2##");
        item1[2] = new Item("##3##");
        item1[3] = new Item("##4##");
        item1[4] = new Item("##5##");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items1.add(item1[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_attachment:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(getApplicationContext(),RefActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item_images:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(getApplicationContext(),RecActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item_location:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(getApplicationContext(),TipActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item_connect:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent4 = new Intent(getApplicationContext(),ConnectActivity.class);
                        startActivity(intent4);
                        break;

                }

                return true;
            }
        });

        img = (ImageView)findViewById(R.id.Imageview1);

        ref = getResources().getDrawable(R.drawable.ref);
        rec = getResources().getDrawable(R.drawable.rec);


        orientation = (RadioGroup)findViewById(R.id.radioGroup1);
        orientation.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        if(group == orientation)
        {
            switch(checkedId)
            {
                case R.id.radio0:
                    img.setImageDrawable(ref);
                    break;
                case R.id.radio1:
                    img.setImageDrawable(rec);
                    break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}