package com.example.jangyujin.gimjangprojects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RefActivity extends Activity{
    DatePicker mDateS;
    DatePicker mDateF;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_NAME = "name";
    private static final String TAG_INPUT ="inputdate";
    private static final String TAG_OUTPUT ="outdate";
    private static final String TAG_CONTENT ="content";

    private static EditText fname;
    private static EditText content;

    static int ITEM_SIZE = 0;

    private static String ID;
    private static String Food_number=null;

    RecyclerView recyclerView;

    private String TAG="RefActivity";
    ArrayList<HashMap<String, String>> mArrayList;

    private static  HashMap<String, String> hashMap;
    String mJsonString;

    List<Item> items;
    Item[] Aitem;

    Button buttonInsert;

    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.ref_d);

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        Log.d("!!!!!!!1112222: ", ID);

        mDateS = (DatePicker)findViewById(R.id.datepicker_start);
        mDateF = (DatePicker)findViewById(R.id.datepicker_finish);
        fname=(EditText)findViewById(R.id.TName);
        content=(EditText)findViewById(R.id.Tcontent);

        //처음 DatePicker를 오늘 날짜로 초기화한다.

        //그리고 리스너를 등록한다.

        mDateS.init(mDateS.getYear(), mDateS.getMonth(), mDateS.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {
                        // TODO Auto-generated method stub

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                    }
                });

        mDateF.init(mDateF.getYear(), mDateF.getMonth(), mDateF.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {
                        // TODO Auto-generated method stub

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                    }
                });


        //값넣기 ==============================================================================
        buttonInsert = (Button)findViewById(R.id.Binsert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fname.getText().toString();
                String input = mDateS.getYear()+"-"+mDateS.getMonth()+"-"+mDateS.getDayOfMonth();
                String out = mDateF.getYear()+"-"+mDateF.getMonth()+"-"+mDateF.getDayOfMonth();
                Log.d("!!!@#################3", input+"!");
                String con = content.getText().toString();

                InsertData task = new InsertData();
                task.execute(name,input, out, con);

            }
        });


        //값 가져오기 ----------------------

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mArrayList = new ArrayList<>();
        items = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://wwhurin.dothome.co.kr/menu1.php");

      /*  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
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
*/
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RefActivity.this,
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
                    String sindate = item.getString("inputdate");
                    String soutdate = item.getString("outdate");
                    String scontent = item.getString("content");

                    //String num=item.getString("num")
                    Food_number=item.getString("num");

                    Log.d("이름~~~~: ", name+""+Food_number);

                    hashMap = new HashMap<>();

                    hashMap.put(TAG_NAME, name);
                    hashMap.put("num", Food_number);
                    // hashMap.put(TAG_ADDRESS, address);
                    SetFood settingfood = new SetFood(name, Food_number, sindate, soutdate, scontent);
                    mArrayList.add(hashMap);
                    Aitem[i] = new Item(name, Food_number, fname, content, mDateS, mDateF, settingfood);
                }


                for (int i = 0; i < ITEM_SIZE; i++) {
                    items.add(Aitem[i]);
                }

                recyclerView.setAdapter(new RecyclerAdapter2(getApplicationContext(), items, R.layout.rec_d));

            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }

    //값 넣기 ==============================================================================
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RefActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {//echo 해준 값 나오는곳
            super.onPostExecute(result);

            progressDialog.dismiss();
//            resultT.setText(result);//php에서 echo 해주는 내용 출력해준다
           /* if(result.equals("ok")){
                Log.d("od", "dddd");
                Intent intent=new Intent(LoginActivity.this, InputActivity.class);
                intent.putExtra("id", name);
                startActivity(intent);
                finish();
            }*/

            if(result!=null){
                Intent goi;
                goi = new Intent(getApplicationContext(), RefActivity.class);
                goi.putExtra("id", ID);
                startActivity(goi);
                finish();
            }
            Log.d(TAG, "POST response  - " + result);
        }



        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[0];
            String inputdate = (String)params[1];
            String outdate = (String)params[2];
            String content = (String)params[3];

            String serverURL;
            String postParameters;


                serverURL = "http://wwhurin.dothome.co.kr/menu1IN.php";
                postParameters = "name=" + name + "&inputdate=" + inputdate + "&outdate=" + outdate + "&content=" + content + "&id=" + ID;
                Log.d("~~~~~: ", ID);
           /*else{
                serverURL = "http://wwhurin.dothome.co.kr/upFood.php";
                postParameters = "name=" + name + "&inputdate=" + inputdate + "&outdate=" + outdate + "&content=" + content + "&id=" + ID+"&num="+Food_number;
                Log.d("sdfsdfsdf오예 ", ID);
            }
*/

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
