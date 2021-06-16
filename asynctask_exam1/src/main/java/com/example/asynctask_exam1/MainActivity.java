package com.example.asynctask_exam1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        // 기본 서비스 url
        String serviceUrl = "http://api.openweathermap.org/data/2.5/weather";
        String appid = "a20a8c757607016c465189248c9a61c9"; // my api key
        String q = "Seoul";
        String strUrl = serviceUrl + "?q=" + q + "&appid=" + appid;
        Log.e("url : ",strUrl);

        new DownloadAPIDataTask().execute(strUrl);

    }

    //AsyncTask를 상속
    private class DownloadAPIDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {


            try{
                tv.setText(s);
            }catch (Exception e){
                tv.setText(e.getMessage());
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }




        @Override   // 백엔드
        protected String doInBackground(String... strings) {
            HttpURLConnection conn = null;
            Log.e("url : ","000");
            try { // try문에서 예외사항이 생기면 실행하지 않는다 로그조차도....
                URL url = new URL(strings[0]);
                Log.e("url : ","111");
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.e("url : ","221");
                    InputStream is = conn.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(inputStreamReader);
                    String line = null, result="";
                    while((line =in.readLine())!=null){
                        result += line;
                        Log.e("result : ", result);
                    }
                    return result;
                }
            } catch (Exception e) {
                tv.setText(e.getMessage());
                Log.e("url : ","44");
            }
            return null;
        }
    }
}
