package com.example.graphic_exam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<Point> points;
    int count = 0;
    int sum =5;
    int grade = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

       // TextView textView;

       // textView.
    }

    private class MyView extends View {

        public MyView(final Context context) {
            super(context);
            new Thread(){
                @Override
                public void run() {
                    while (true) {
                        super.run();
                        SystemClock.sleep(1000);
                        invalidate();

                    }
                }
            }.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            points = new ArrayList<>();

            // 디스플레이 크기
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int dWidth = metrics.widthPixels;
            int dHeight = metrics.heightPixels;

            // 비트맵 크기 조절
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.donut);
            bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);
           // canvas.drawBitmap(bitmap,200,200,paint);

            Random random = new Random();
            paint.setTextSize(100);

            for(int i=0;i<10;i++){
                Point pos = new Point(random.nextInt(dWidth),random.nextInt(dHeight));
                points.add(pos);
                canvas.drawBitmap(bitmap,points.get(i).x,points.get(i).y,paint);

            }
            canvas.drawText("Score : " + count + " / "+ sum,0,100,paint);
//           points.add(new Point(pos.x,pos.y));

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
             super.onTouchEvent(event);

             switch (event.getAction()){
                 case MotionEvent.ACTION_DOWN:
                 for(Point pos :points){
                     if( (event.getX()>=pos.x && event.getX() <= pos.x + 200)
                         && (event.getY() >= pos.y && event.getY() <= pos.y + 200 ) ){
                         count ++;
                         if(count >= sum) {
                             grade ++;
                             Toast.makeText(getApplicationContext(),"축하합니다. "+grade+"단계 성공 ",Toast.LENGTH_LONG).show();
                             sum += count;
                             count = 0;
                             break;
                         }
                     }
                 } // end of for
                     break;
             } // end of switch

            return true;

        }
    }
        // 스레드는 뷰의 생성자안에 만들면 됨
}
