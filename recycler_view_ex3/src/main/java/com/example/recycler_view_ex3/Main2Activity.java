package com.example.recycler_view_ex3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ArrayList<String> data = new ArrayList<>();
        data.add("장성필");
        data.add("최준배");
        data.add("윤병준");

        CustomAdapter adapter = new CustomAdapter();
        adapter.data = data;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnTouchMyListener(new CustomAdapter.OnTouchMyListener() {
            @Override
            public void OnTouch(View v, int pos) {
                Toast.makeText(getApplicationContext(),"HI : " + pos+1,Toast.LENGTH_SHORT).show();
            }
        });
    }





    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
        private ArrayList<String>data;
        OnTouchMyListener listener = null;

        public CustomAdapter(){

        }
        public CustomAdapter(ArrayList<String> data) {
            this.data = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener!=null)
                            listener.OnTouch(v,getAdapterPosition());
                    }
                });
            }
        }

        public void setOnTouchMyListener(OnTouchMyListener listener){
            this.listener = listener;
        }

        public interface OnTouchMyListener{
            void OnTouch(View v,int pos);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(data.get(position)+ (position+1));

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
