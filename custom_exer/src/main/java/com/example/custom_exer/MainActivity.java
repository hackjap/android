package com.example.custom_exer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview1);
        ArrayList<Item>data = new ArrayList<>();

        // 데이터베이스 구현
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select subject,progress,created from mjcclass",null);

        while(cursor.moveToNext()){
            Item item = new Item();
            item.subject = cursor.getString(0);
            item.progress = cursor.getInt(1);
            item.updated = cursor.getString(2);
            data.add(item);
        }
        db.close();;

        MyListAdapter myListAdapter = new MyListAdapter(this,R.layout.custom_item,data);
        // 리스트뷰에 어뎁터 연결
        listView.setAdapter(myListAdapter);
    }

    public class MyListAdapter extends ArrayAdapter<Item> {

        Context context;
        int resource;
        ArrayList<Item> data;

        public MyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            // 뷰 파일 inflate
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, null);

            // xml 파일 연결
            TextView subject = (TextView)convertView.findViewById(R.id.cust_detail);
            TextView progress = (TextView)convertView.findViewById(R.id.cust_amount);
            TextView updated = (TextView)convertView.findViewById(R.id.cust_date);
            ImageView type = (ImageView)convertView.findViewById(R.id.cust_img1);
            // 각 뷰의 요소에 값 입력
            Item item = data.get(position);
            subject.setText(item.subject);
            updated.setText(item.updated);

            if(item.progress>=50){ // 성과율 50% 이상일떄
                progress.setText(""+item.progress+"%");
                progress.setTextColor(Color.BLUE);
                type.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.t,null));
                // 성과율에 따른 버튼 동작
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "good good good!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else{
                progress.setText(""+item.progress+"%");
                progress.setTextColor(Color.RED);
                type.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.f,null));
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "bad bad bad!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            // 각 리스트를 눌렀을떄 동작
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),(position+1)+"번쨰 리스트",Toast.LENGTH_SHORT).show();
                }
            });

                    // 이미지 크키 설정
            ViewGroup.LayoutParams params = type.getLayoutParams();
            params.width = 100;
            params.height =100;
                type.setLayoutParams(params);

            return convertView;
        }


    }
}