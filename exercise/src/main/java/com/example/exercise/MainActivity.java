package com.example.exercise;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item>data;
    String details,amount;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.textView);
        data = new ArrayList<>();
        str = new String("gd");

        data.add(new Item("카페","3000"));

        run();
        tv.setText("Detail : "+data.get(1).details+"\nAmount : "+data.get(2).amount+"\n"+str);



    }


    public void run(){
        inputData();
        data.add(new Item("휴대폰","10000000"));
    }

    public void inputData(){
        String detail = "애슐리";
        String amount = "10,000";
        data.add(new Item(detail,amount));

        this.details = detail;
        this.amount = amount;
        str = new String("ㄴㄴ");
    }
}
