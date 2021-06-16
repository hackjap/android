package com.example.app_2021_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //1 : 변수 설정
    Button fButton1,fButton2;
    LinearLayout mainContainer;
    FragmentManager fragmentManager;
    FragmentOne fragmentOne;
    FragmentTwo fragmentTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2: xml 문서와 변수의 연결
        fButton1 = (Button)findViewById(R.id.button1);
        fButton2 = (Button)findViewById(R.id.button2);
        mainContainer = (LinearLayout)findViewById(R.id.main_container);
        // fm 관리자
        fragmentManager = getSupportFragmentManager();
        // fm 객체
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();

        // 3
        fButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container,fragmentOne);
                ft.commit();
            }
        });


        /*
        fButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container,fragmentTwo);
                ft.commit();
            }
        });
*/
        fButton2.setOnClickListener(this);

   }

    @Override
    public void onClick(View v) {

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.main_container,fragmentTwo);
            ft.commit();

    }
}
