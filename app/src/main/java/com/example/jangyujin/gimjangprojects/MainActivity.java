package com.example.jangyujin.gimjangprojects;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    int ITEM_SIZE = 0;
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

    private static String ID;

    String TAG="Input, 하루남음";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_NAME = "name";
    private static final String TAG_INPUT ="inputdate";
    private static final String TAG_OUTPUT ="outdate";
    private static final String TAG_CONTENT ="content";

    private static  HashMap<String, String> hashMap;

    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;
    private static String Food_number;
    Item[] Aitem;
    List<Item> items;

    RecyclerView recyclerView1;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArrayList = new ArrayList<>();
        items = new ArrayList<>();

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        Log.d("!!!!!!!1112222: ", ID);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


    /*    List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item("#1");
        item[1] = new Item("#2");
        item[2] = new Item("#3");
        item[3] = new Item("#4");
        item[4] = new Item("#5");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }*/

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));


        GetData task = new GetData();
        task.execute("http://wwhurin.dothome.co.kr/input.php");

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_one_day);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

      /*  List<Item> items1 = new ArrayList<>();
        Item[] item1 = new Item[ITEM_SIZE];
        item1[0] = new Item("##1##");
        item1[1] = new Item("##2##");
        item1[2] = new Item("##3##");
        item1[3] = new Item("##4##");
        item1[4] = new Item("##5##");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items1.add(item1[i]);
        }*/


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
                        intent1.putExtra("id", ID);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item_images:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(getApplicationContext(),RecActivity.class);
                        intent2.putExtra("id", ID);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item_location:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent3 = new Intent(getApplicationContext(),TipActivity.class);
                        intent3.putExtra("id", ID);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item_connect:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        Intent intent4 = new Intent(getApplicationContext(),ConnectActivity.class);
                        intent4.putExtra("id", ID);
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

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }




        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters="id="+ID;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                //httpURLConnection.connect();
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {

                //mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }


        private void showResult() {
            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                Aitem = new Item[jsonArray.length()];
                ITEM_SIZE = jsonArray.length();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString(TAG_NAME);
                    Food_number=item.getString("num");
                    Log.d("이름~~~~: ", name+""+Food_number);

                    hashMap = new HashMap<>();

                    hashMap.put(TAG_NAME, name);
                    hashMap.put("num", Food_number);
                    // hashMap.put(TAG_ADDRESS, address);

                    mArrayList.add(hashMap);
                    Aitem[i] = new Item(name);
                }

                for (int i = 0; i < ITEM_SIZE; i++) {
                    items.add(Aitem[i]);
                }

                recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));



            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }

}

