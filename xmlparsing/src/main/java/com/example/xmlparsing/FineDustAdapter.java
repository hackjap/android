package com.example.xmlparsing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FineDustAdapter extends RecyclerView.Adapter<FineDustAdapter.FineDustViewHolder> {
   ArrayList<FineDust> list;

   public FineDustAdapter( ArrayList<FineDust> list) {
      this.list=list;
   }

   @NonNull
   @Override
   public FineDustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fine_dust_item, parent, false);
      return new FineDustViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull FineDustViewHolder holder, int position) {
      holder.district.setText(list.get(position).getDistrict());  /////////////
      holder.value.setText(list.get(position).getValue());
      holder.issueDate.setText(list.get(position).getIssueDate());
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   class FineDustViewHolder extends RecyclerView.ViewHolder{
      TextView district;
      TextView value;
      TextView issueDate;

      public FineDustViewHolder(@NonNull View itemView) {
         super(itemView);
         value = itemView.findViewById(R.id.textView10);
         district = itemView.findViewById(R.id.textView11);
         issueDate = itemView.findViewById(R.id.textView12);
      }
   }
}
