package com.example.graphic_thread_event;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Point> list;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));


    }

    private class MyView extends View {

        // 생성자
        public MyView(Main2Activity main2Activity) {
            super(main2Activity);

            // 스레드 생성
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    while(true){
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
            paint.setTextSize(100);
            paint.setColor(Color.rgb(100,100,0));


            // display size
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int dWidth = displayMetrics.widthPixels;
            int dHeight = displayMetrics.heightPixels;

            // Bimtmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.donut);
            bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);
            // random
            Random random = new Random();

            // many donut
            list = new ArrayList<>();
            for(int i=0;i<10;i++){
                Point pos = new Point(random.nextInt(dWidth),random.nextInt(dHeight));
                list.add(pos);
                canvas.drawBitmap(bitmap,pos.x,pos.y,paint);
            }

            // score text
            canvas.drawText("SCORE : " + score,100,100,paint);

        }

        // touch event

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    for(Point pos : list){
                        // 도넛을 눌렀을 때 도넛의 시작과 끝 안에 속해야 함
                        if(event.getX() >= pos.x && event.getX() <= pos.x + 200 &&
                            event.getY() >= pos.y && event.getY() <= pos.y+200)
                            score++;
                    }
                    break;
            }
            return true;
        }
    }
}


