package com.example.xmlparsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Covid19Adapter extends RecyclerView.Adapter<Covid19Adapter.Covid19ViewHolder> {
   ArrayList<Covid19> list;
   Context c;

   public Covid19Adapter(ArrayList<Covid19> list, Context c) {
      this.list=list;
      this.c = c;
   }

   @NonNull
   @Override
   public Covid19ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.covid19_item, parent, false);
      return new Covid19ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull Covid19ViewHolder holder, int position) {
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

   public class Covid19ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      TextView seq;
      TextView decideCNT;
      TextView stateDT;
      ConstraintLayout constraintLayout;


      @Override
      public void onClick(View v) {
         int position = getAdapterPosition();
         Toast.makeText(c,list.get(position).getDecideCNT(),Toast.LENGTH_SHORT).show();
      }

      public Covid19ViewHolder(@NonNull View itemView) {
         super(itemView);
         seq = itemView.findViewById(R.id.textView11);
         decideCNT = itemView.findViewById(R.id.textView10);
         stateDT = itemView.findViewById(R.id.textView12);
         constraintLayout = (ConstraintLayout)itemView.findViewById(R.id.contentLayout);

         decideCNT.setOnClickListener(this);


      }



   }
}
