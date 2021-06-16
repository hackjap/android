package com.example.recycler_view_ex1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        recyclerView =(RecyclerView) findViewById(R.id.recyclerView);

        ArrayList<Item>list = new ArrayList<>();

        // DB구현
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select item,amount,created from expense",null);

        while (cursor.moveToNext()){
            Item item = new Item();
            item.amount = cursor.getInt(1);
            item.details = cursor.getString(0);
            item.updated = cursor.getString(2);

            list.add(item);

        }


        // recyclerView set
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        ArrayList<Item>list;

        public MyAdapter(ArrayList<Item>list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Item tmp = list.get(position);

            holder.details.setText(tmp.details);
            holder.amount.setText(""+tmp.amount);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
          TextView details;
          TextView amount;
          // TextView created;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

              this.details = (TextView)itemView.findViewById(android.R.id.text1);
              this.amount = (TextView)itemView.findViewById(android.R.id.text2);
              //this.created = (TextView)itemView.findViewById(android.R.id.text2);

        }
    }
}




