package com.example.jangyujin.gimjangprojects;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

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

public class RecActivity extends AppCompatActivity {
    private String TAG="RECOMMEND ACT";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_NAME = "menuname";
    private static final String TAG_INPUT ="inputdate";
    private static final String TAG_OUTPUT ="outdate";
    private static final String TAG_CONTENT ="content";

    static int ITEM_SIZE = 0;

    private static String ID;
    private static String Food_number;

    RecyclerView recyclerView;

    ArrayList<HashMap<String, String>> mArrayList;
    private static  HashMap<String, String> hashMap;

    String mJsonString;

    List<Item> items;
    Item[] Aitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_d);

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        Log.d("!!!!!!!1112222: ", ID);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mArrayList = new ArrayList<>();
        items = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://wwhurin.dothome.co.kr/menu2.php");

       /* mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                HashMap<String,String> map =(HashMap<String,String>)adapterView.getItemAtPosition(position);
                String name= map.get(TAG_NAME);
                Log.d("selected_item::::", name);
                Log.d("selected_item::::", Food_number);

                Intent intent1 = new Intent(ReconmmedActivity.this, ShowRecommendActivity.class);
                intent1.putExtra("id", ID);
                intent1.putExtra("menuname", map.get("menuname"));
                intent1.putExtra("img1", map.get("ing1"));
                intent1.putExtra("img2", map.get("ing2"));
                intent1.putExtra("img3", map.get("ing3"));
                intent1.putExtra("time", map.get("time"));
                intent1.putExtra("how", map.get("how"));
                startActivity(intent1);
                finish();

            }
        });*/

       /* List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item("#1");
        item[1] = new Item("#2");
        item[2] = new Item("#3");
        item[3] = new Item("#4");
        item[4] = new Item("#5");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.rec_d));*/
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RecActivity.this,
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
//            mTextViewResult.setText(result);
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
                    String ing1=item.getString("ingredient1");
                    String ing2=item.getString("ingredient2");
                    String ing3=item.getString("ingredient3");
                    String time=item.getString("time");
                    String how=item.getString("how");

                    Food_number=item.getString("num");
                    Log.d("이름~~~~: ", name+""+Food_number);

                    hashMap = new HashMap<>();

                    hashMap.put(TAG_NAME, name);
                    hashMap.put("num", Food_number);
                    hashMap.put("ing1", ing1);
                    hashMap.put("ing2", ing2);
                    hashMap.put("ing3", ing3);
                    hashMap.put("time", time);
                    hashMap.put("how", how);
                    // hashMap.put(TAG_ADDRESS, address);

                    mArrayList.add(hashMap);
                    Aitem[i] = new Item(name);
                }


                for (int i = 0; i < ITEM_SIZE; i++) {
                    items.add(Aitem[i]);
                }

                recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.rec_d));

            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }
}