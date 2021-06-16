package com.example.mysql_login_example;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainContainer, resultContainer; // 기본 콘텐츠 , 결과 콘텐츠
    Button loginBtn, joinBtn;    // 로그인, 회원가입 버튼
    EditText emailEdit, passEdit, passConfirmEdit;   // 이메일,비밀번호,비밀번호 확인 edittext
    TextView title, resultTv;    // 제목, 결과 textview
    String email = "", pass = "", passConfrim = ""; // edittext에서 가져올 문자열
    int flag = 0;    // 로그인=0, 회원가입=1

    static RequestQueue requestQueue; //volley


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml 연결
        title = findViewById(R.id.tv_title);
        emailEdit = findViewById(R.id.et_login_id);
        passEdit = findViewById(R.id.et__login_pw);
        passConfirmEdit = findViewById(R.id.et__login_pw_confirm);
        loginBtn = findViewById(R.id.btn_login);
        joinBtn = findViewById(R.id.btn_join);
        mainContainer = findViewById(R.id.container_main);
        resultContainer = findViewById(R.id.container_result);
        resultTv = findViewById(R.id.tv_result);

        // btn 동작
        findViewById(R.id.login_check_btn).setOnClickListener(onClickListener);
        loginBtn.setOnClickListener(onClickListener);
        joinBtn.setOnClickListener(onClickListener);

        // volley
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }


    } // end of oncreate()


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login: {   // 로그인 카테고리 클릭시
                    flag = 0;
                    initEditText();
                    setLayout(flag);
                    break;
                }
                case R.id.btn_join: {
                    initEditText();
                    flag = 1;
                    setLayout(flag);
                    break;
                }
                case R.id.login_check_btn: {

                    email = emailEdit.getText().toString();
                    pass = passEdit.getText().toString();
                    passConfrim = passConfirmEdit.getText().toString();

                    // 유효성검사
                    Boolean loginValidation = flag == 0 && (email.length() > 0 && pass.length() > 0);
                    Boolean joinValidation = (flag == 1 && (email.length() > 0 && pass.length() > 0 && passConfrim.length() > 0))
                                                && pass.equals(passConfrim);

                    if (loginValidation || joinValidation) { // 예외처리( 공백 + 비밀번호 Confirm )

                        if (flag == 0) {
                            // 애뮬레이터의 localhost : 10.0.2.2
                            getDatabase("http://10.0.2.2:8081/AndroidAppEx/myLoginDB.jsp?email=" + email + "&pwd=" + pass);

                        } else {
                            getDatabase("http://10.0.2.2:8081/AndroidAppEx/myRegister.jsp?email=" + email + "&pwd=" + pass);
                        }

                        initEditText(); // edittext 초기화
                        break;

                    } else {
                        myToastMsg("올바른 내용을 작성해주세요.");
                    }
                } // end of switch
            }
        }
    };

    // 콘텐츠별 레이아웃 세팅
    private void setLayout(int flag) {
        if (flag == 0) {
            title.setText("로그인");
            resultContainer.setVisibility(View.GONE);
            loginBtn.setTextColor(Color.RED);
            joinBtn.setTextColor(Color.WHITE);
            mainContainer.setVisibility(View.VISIBLE);
            findViewById(R.id.et__login_pw_confirm).setVisibility(View.GONE);
        } else {
            title.setText("회원가입");
            mainContainer.setVisibility(View.VISIBLE);
            findViewById(R.id.et__login_pw_confirm).setVisibility(View.VISIBLE);
            resultContainer.setVisibility(View.GONE);
            loginBtn.setTextColor(Color.WHITE);
            joinBtn.setTextColor(Color.RED);
            findViewById(R.id.et__login_pw_confirm).setVisibility(View.VISIBLE);
        }

    }

    // edittext 초기화
    private void initEditText() {
        emailEdit.setText("");
        passEdit.setText("");
        passConfirmEdit.setText("");
    }

    // mysql db 데이터 호출
    private void getDatabase(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mainContainer.setVisibility(View.GONE);
                        resultContainer.setVisibility(View.VISIBLE);
                        resultTv.setText(response);
                        myToastMsg("서버연결성공");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myToastMsg("서버연결실패");

                    }
                });
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    // 토스트 메시지
    public void myToastMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}