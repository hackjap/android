package com.example.volley_exam1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

// 1. requestque
// 2. url을 담는 stirng request
//    [ Get 방식 ]
//    4개의 메개변수 (요청방식,url,서버 응답시 처리할 내용,에러시 처리할 내용 )
//    [ post 방식 ]
public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView);
        btn = (Button)findViewById(R.id.button);

        // Requestqueue 초기화

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        if(requestQueue == null ){
            requestQueue = Volley.newRequestQueue(this);
        }


    }

    private void makeRequest() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=a20a8c757607016c465189248c9a61c9";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    tv.setText(error.getMessage());
            }
        }
        )
        {
            // Post 일 떄
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }
}
