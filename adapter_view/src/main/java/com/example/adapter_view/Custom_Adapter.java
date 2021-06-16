package com.example.adapter_view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class Custom_Adapter extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__adapter);

        listView = (ListView)findViewById(R.id.cust_listview);

        ArrayList<Item>data = new ArrayList<>();
        /*
        data.add(new Item("용돈",50000,"2021-03-02"));
        data.add(new Item("식사",-6000,"2021-03-05"));
        data.add(new Item("알바",10000,"2021-03-09"));
        data.add(new Item("교통비",-4000,"2021-03-10"));
         */

        // DB 구현
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select item,amount,strftime('%Y-%m-%d',created)from expense",null);

        while(cursor.moveToNext()) {
            Item item = new Item();
            item.details = cursor.getString(0);
            item.amount = cursor.getInt(1);
            item.updated = cursor.getString(2);
            data.add(item);

        }
        db.close();
        // 어댑터만들기
        MyListAdapter adapter = new MyListAdapter(this,R.layout.custom_item,data);
        // 리스트뷰에 어댑터 연결
        listView.setAdapter(adapter);


    }

    class MyListAdapter extends ArrayAdapter<Item> {
        Context context;
        int resource;
        ArrayList<Item>data;

        public MyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> data) {
            super(context, resource, data);
            this.context = context;
            this.data = data;
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // 뷰 파일  inflate
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_item,null);
            }

            // 뷰 파일의 각 뷰 자바 변수(reference)와 연동
            TextView detail = (TextView)convertView.findViewById(R.id.cust_detail);
            TextView amount = (TextView)convertView.findViewById(R.id.cust_amount);
            TextView date = (TextView)convertView.findViewById(R.id.cust_date);
            ImageView type = (ImageView)convertView.findViewById(R.id.cust_img1);

            Item item = data.get(position);
            //각 뷰의 요소에 값 입력
            detail.setText(item.details);
            date.setText(item.updated);
            if(item.amount > 0){
                amount.setText(""+item.amount);
                type.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.t,null));
            }
            else{
                amount.setText(""+-item.amount);
                type.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.f,null));
            }
            // 이미지 크키 설정
            ViewGroup.LayoutParams params = type.getLayoutParams();
            params.width = 100;
            params.height =100;
            type.setLayoutParams(params);

           type.setOnClickListener(new View.OnClickListener(){

               @Override
               public void onClick(View v) {
                   Toast.makeText(getContext(),(position+1)+"번째 아이템",Toast.LENGTH_SHORT).show();

               }
           });

            detail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),(position+1)+"번째 항목",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

}
