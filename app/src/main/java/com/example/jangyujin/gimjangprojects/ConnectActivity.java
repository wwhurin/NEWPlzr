package com.example.jangyujin.gimjangprojects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectActivity extends Activity {

    String TAG="CODEACT";
    String ID;
    String Code;

    EditText idT, codeT;
    TextView CodeV;

    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        setContentView(R.layout.connect);

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");

        CodeV=(TextView) findViewById(R.id.MyCode);

        idT=(EditText) findViewById(R.id.TId);
        codeT=(EditText) findViewById(R.id.TCode);

        GetData getData=new GetData();
        getData.execute("http://wwhurin.dothome.co.kr/getCode.php");
        //Log.d("되아러ㅏ어ㅣ렁:", Code);
        //CodeV.setText(Code);

        Button buttonInsert = (Button)findViewById(R.id.BCon);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = idT.getText().toString();
                String con = codeT.getText().toString();

                InsertData task = new InsertData();
                task.execute(name, con);

            }
        });

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ConnectActivity.this,
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
                goi = new Intent(getApplicationContext(), MainActivity.class);
                goi.putExtra("id", ID);
                startActivity(goi);
                finish();
            }
            Log.d(TAG, "POST response  - " + result);
        }



        @Override
        protected String doInBackground(String... params) {

            String id = (String)params[0];
            String code = (String)params[1];

            String serverURL = "http://wwhurin.dothome.co.kr/updateCode.php";
            String postParameters = "name=" + id + "&code=" + code;
            Log.d("~~~~~: ", ID);


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


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           /* progressDialog = ProgressDialog.show(CodeActivity.class.getClass(),
                    "Please Wait", null, true, true);*/
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

            // progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "response  - !!!!!!!!!" + result);


            if (result == null) {

                //mTextViewResult.setText(errorString);
            } else {
                Code=result;
                CodeV.setText(result);
                Log.d("CHKDFJKD:", Code);
            }
        }

    }

}
