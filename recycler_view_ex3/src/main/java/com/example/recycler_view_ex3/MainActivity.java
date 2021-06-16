package com.example.recycler_view_ex3;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String>list;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (findViewById(R.id.recyclerView));

        String names[]={"장성필","최준배","윤병준","이유비"};

        list = new ArrayList<>();
        for(String name:names)
            list.add(name);


        MyAdapter myAdapter = new MyAdapter(this,R.layout.item,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

       myAdapter.setOnMyTouchListener(new MyAdapter.OnMyTouchListener() {
           @Override
           public void onTouch(View v, int position) {
               Toast.makeText(getApplicationContext(),"Item : "+(position+1),Toast.LENGTH_SHORT).show();
           }
       });

    }
}
