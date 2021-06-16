package com.example.recycler_view_custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    public ArrayList<String>list;
    public OnMyTouchListener listener = null;
    public MyAdapter2(ArrayList<String> list) {
        this.list = list;
    }


    public interface OnMyTouchListener{
        void onTouch(View v,int position);

    }


    public void setOnMyTouchListener(OnMyTouchListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = list.get(position);
        holder.name.setText(name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // viewholder
    class MyViewHolder extends RecyclerView.ViewHolder{ // Item에 대한 View를 책임짐
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // 중요
                    if(listener!=null)
                        listener.onTouch(v,position);
                }
            });
        }
    }
}
