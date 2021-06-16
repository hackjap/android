package com.example.recycler_view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

      String names[]={"Ahn,SS", "Ahn,DI", "Kim, KH", "Kim, TK", "Kim, AR", "Lee, JH", "Lee, DS"};
      ArrayList<String> list = new ArrayList<>();
      for(String name : names)
         list.add(name);

      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(new MyAdapter(list));
      recyclerView.addItemDecoration(new MyItemDecoration(list));
   }

   class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
      ArrayList<String> stud_names;
      public MyAdapter(ArrayList<String> list) {
         this.stud_names = list;
      }

      @NonNull
      @Override
      public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
         return new MyViewHolder(view);
      }

      @Override
      public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         String text = stud_names.get(position);
         holder.name.setText(text);
      }

      @Override
      public int getItemCount() {
         return stud_names.size();
      }
   }

   class MyViewHolder extends RecyclerView.ViewHolder{
      public TextView name;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         name = (TextView)itemView.findViewById(android.R.id.text1);
      }
   }

   class MyItemDecoration extends RecyclerView.ItemDecoration{
      ArrayList<String> list;
      char head_ch=64;

      public MyItemDecoration(ArrayList<String> list) {
         this.list = list;
      }

      @Override
      public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                                 @NonNull RecyclerView.State state) {
         super.getItemOffsets(outRect, view, parent, state);
         int position=parent.getChildAdapterPosition(view);
         if((position+1) %3 == 0)
            outRect.set(30,10,30,100);
         else
            outRect.set(30,10,30,10);
         view.setBackgroundColor(Color.argb(180, 180, 200, 200));
/*
         if(position==0){
            head_ch =list.get(0).charAt(0);
            outRect.set(30,90,30,10);
         } else if (list.get(position).charAt(0) != head_ch){
            head_ch = list.get(position).charAt(0);
            outRect.set(30,90,30,10);
         } else
            outRect.set(30,10,30,10);
         view.setBackgroundColor(Color.argb(180, 180, 200, 200));

 */
      }

      @Override
      public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
         super.onDraw(c, parent, state);
         int width = parent.getWidth();
         int height = parent.getHeight();

         Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.back11);
         c.drawBitmap(Bitmap.createScaledBitmap(tmp, width, height, true), 0, 0, null);
      }

      @Override
      public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
         super.onDrawOver(c, parent, state);
         int width = parent.getWidth();
         int height = parent.getHeight();
         Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_search, null);
         int drWidth = drawable.getIntrinsicWidth();
         int drHeight = drawable.getIntrinsicHeight();
         int x= width/2 -drWidth/2;
         int y =height/2-drHeight/2;
         c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_search), x, y, null);
      }
   }
}
