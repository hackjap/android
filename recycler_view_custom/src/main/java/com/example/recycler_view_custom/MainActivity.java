package com.example.recycler_view_custom;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        String names[] = {"Ahn,SS", "Ahn,DI", "Kim,KH", "Kim,TK", "Kim,AR", "LeeJH", "Lee,DS"};
        final ArrayList<String>list = new ArrayList<>();
        for(String n : names)
            list.add(n);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter2 myAdapter2 = new MyAdapter2(list);
        recyclerView.setAdapter(myAdapter2);

        myAdapter2.setOnMyTouchListener(new MyAdapter2.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int position) {
                Toast.makeText(getApplicationContext(),"클릭 : "+ list.get(position),Toast.LENGTH_SHORT).show();
            }
        });



    }

}