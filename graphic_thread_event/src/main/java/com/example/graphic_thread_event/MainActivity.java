package com.example.graphic_thread_event;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    Point pos;
    int score = 0;
    ArrayList<Point>list; // step 4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }



    private class MyView extends View {
        public MyView(Context context) {
            super(context);

            // 스레드 생성
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    while(true){
                        SystemClock.sleep(1000);
                        invalidate(); // onDraw 메소드를 다시 불러줌

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


            // 디스플레이 크기
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int dWidth = metrics.widthPixels;
            int dHeight = metrics.heightPixels;

            //


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.donut);
            // Bitmap의 크기 조절
            bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);

            // random 위치(이미지의 폭도 고려해야 함. 여기서는 고려하지 않음)
            Random random = new Random();

            // step 4
            list = new ArrayList<>();
            for(int i=0;i<10;i++){
                Point pos = new Point(random.nextInt(dWidth),random.nextInt(dHeight));
                list.add(pos);
                canvas.drawBitmap(bitmap,pos.x,pos.y,paint);
            }

            // score 텍스트
            canvas.drawText("Score : "+ score,100,100,paint);


        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    // 도넛을 눌렀을 때 도넛의 시작과 끝 안에 속해야 함
                    for(Point pos : list){
                        if(event.getX() >= pos.x && event.getX() <= pos.x + 200  &&
                                event.getY() >= pos.y && event.getY() <= pos.y +200 )
                            score++;
                    }

                default:
                    break;

            } // end of switch



            return true;


        }
    }
}

