package com.example.server_exam;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //String testUrl = "http://www.google.co.kr";
        // String urlBuilder = "http://172.30.1.18:8081/AndroidAppEx/login.jsp?"
        //String login = "id=mjc&pwd=inbo";
        //String url = urlBuilder + login;

        //String url ="http://192.168.0.112:8081/AndroidAppEx/jsonPage.jsp";
        String url ="http://172.30.1.25:8081/AndroidAppEx/loginRegister.jsp?name=android?pwd=12345";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText("<response>\n"+response);

                    try{
                        /*
                        JSONObject mainObject = new JSONObject(response);
                        JSONArray result1 = mainObject.getJSONArray("List");
                        JSONObject result2 = result1.getJSONObject(0);
                        tv.append("\n\n <Parsing Result>\n name is "+result2.getString("name"));

                         */

                    }catch (Exception e){
                        tv.append(e.getMessage());
                    }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            tv.setText("애러!");

                    }
                });
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        tv.setText("연결실패");


    }
}
