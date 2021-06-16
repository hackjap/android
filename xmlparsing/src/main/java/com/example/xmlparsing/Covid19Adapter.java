package com.example.xmlparsing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Covid19Adapter extends RecyclerView.Adapter<Covid19Adapter.FineDustViewHolder> {
   ArrayList<Covid19> list;

   public Covid19Adapter(ArrayList<Covid19> list) {
      this.list=list;
   }

   @NonNull
   @Override
   public FineDustViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.covid19_item, parent, false);
      return new FineDustViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull FineDustViewHolder holder, int position) {
      /////////////

      if (position != 0) {
         int result = Integer.parseInt(list.get(position).getDecideCNT()) - Integer.parseInt(list.get(position + 1).getDecideCNT());
//

         holder.decideCNT.setText(list.get(position).getDecideCNT());
         holder.stateDT.setText(list.get(position).getStateDT());
         holder.seq.setText(result + "▲ ");
      }else{
         holder.constraintLayout.setVisibility(View.GONE);
      }

   }
   @Override
   public int getItemCount() {
      return list.size();
   }

   class FineDustViewHolder extends RecyclerView.ViewHolder{
      TextView seq;
      TextView decideCNT;
      TextView stateDT;
      ConstraintLayout constraintLayout;
      public FineDustViewHolder(@NonNull View itemView) {
         super(itemView);
         seq = itemView.findViewById(R.id.textView11);
         decideCNT = itemView.findViewById(R.id.textView10);
         stateDT = itemView.findViewById(R.id.textView12);
         constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.contentLayout);
      }
   }
}
