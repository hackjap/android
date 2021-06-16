package com.example.recycler_view_ex2;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1. 레이아웃 매너지
    // 2. ViewHolder
    // 3. 어뎁터 - recyclerview.Adapter를 상속
    // 4. extra) ItemDecoration을 통해 디자인 구성
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        String names[] = {"Ahn,SS", "Ahn,DI", "Kim,KH", "Kim,TK", "Kim,AR", "LeeJH", "Lee,DS"};
        final ArrayList<String> list = new ArrayList<>();
        for (String name : names)
            list.add(name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyItemDecoration());
        // 리스너 1 :
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                int position = rv.getChildAdapterPosition(child);
                StartToast("클릭"+list.get(position));
               // Log.e("클릭","클릭");
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
        class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
            // 데이터와 리스트 연결
            public ArrayList<String> list;

            public MyAdapter(ArrayList<String> list) {
                this.list = list;
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new MyViewHolder(view); // MyViewHolder에 view를 전달
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                // ViewHolder의 변수에 데이터 연결
                String name = list.get(position);
                holder.name.setText(name);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }

        }


        // ViewHolder에 연결할 변수를 선언하고 xml문서와 연결
        // 뷰들의 레퍼런스 변수들과 연결
        class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(android.R.id.text1);

                // 리스너2 : 뷰홀더를 통한 리스너 구현
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartToast(name.getText().toString());
                        //int position = getAdapterPosition();
                    }
                });
            }
        }

        class MyItemDecoration extends RecyclerView.ItemDecoration {

            @Override   // 아이템의 뒷면에
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override   // z축, 아이템의 윗면 화면의 뷰를 추가하는 목적
            public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int position = parent.getChildAdapterPosition(view);    // 현재 아이템뷰의 포지션을 가져옴
                if ((position + 1) % 2 == 0) {
                    outRect.set(30, 30, 30, 100);
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    outRect.set(30, 30, 30, 15);
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }

        public void StartToast(String msg){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }

}