package com.example.openweathermap;

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

        tv = (TextView) findViewById(R.id.tv_data);


        String strUrl = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=a20a8c757607016c465189248c9a61c9";

        new MyAsyncTask().execute(strUrl);


    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            try {
                tv.setText(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                Log.e("에러 : ", url.toString());
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    Log.e("에러 : ", "2");

                    String result = null, line = null;
                    InputStream is = conn.getInputStream();
                    InputStreamReader isReader = new InputStreamReader(is);
                    BufferedReader in = new BufferedReader(isReader);
                    while ((line = in.readLine()) != null) {
                        result += line;
                    }
                        Log.e("에러 : ", "2-2");
                        return result;




                //}
            } catch (Exception e) {
                // e.printStackTrace();
                Log.e("애러 : ", "3");
            }


            return null;
        }
    }
}


