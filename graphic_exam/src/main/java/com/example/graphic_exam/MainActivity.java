package com.example.graphic_exam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final static int LINE = 1, CIRCLE =2, PICTURE=3;
    static int curShape = LINE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));



    }


    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       menu.add(0,1,0,"선 그리기");
       menu.add(0,2,0,"원 그리기");
       menu.add(0,3,0,"사진 올리기");
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 1:
                curShape = LINE; //선
                return true;
            case 2:
                // 원
                curShape=CIRCLE;
                return true;
            case 3:
                // image
                curShape=PICTURE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // view
    private static class MyGraphicView extends View{
        int startX = -1, startY = -1, stopX = -1, stopY = -1;
        public MyGraphicView(Context context) {
            super(context);

        }


        // 이벤트 처리


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX =(int)event.getX();
                    startY =(int)event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    stopX = (int)event.getX();
                    stopY = (int)event.getY();
                    this.invalidate();
                    break;


            }

            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
//            canvas.drawLine(100,100,300,300,paint);
            switch (curShape){
                case LINE:
                    canvas.drawLine(startX,startY,stopX,stopY,paint);
                    break;
                case CIRCLE:
                    int radius = (int)Math.sqrt(Math.pow(stopX-startX,2)
                        + Math.pow(stopY-startY,2));
                    canvas.drawCircle(startX,startY,radius,paint);
                    break;
                case PICTURE:
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img_register);
                    bitmap = Bitmap.createScaledBitmap(bitmap,stopX-startX,stopY-startY,true);
                    canvas.drawBitmap(bitmap,startX,startY,paint);
                    break;

            }


            /*
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            canvas.drawLine(10,10,300,10,paint);

            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5);

            paint.setColor(Color.RED);
            paint.setStrokeWidth(0);

            paint.setStyle(Paint.Style.FILL);
            Rect rect1 = new Rect(10,50,10+100,50+100);
            canvas.drawRect(rect1,paint);

            paint.setStyle(Paint.Style.STROKE);
            Rect rect2 = new Rect(130,50,130+100,50+100);
            canvas.drawRect(rect2,paint);

            RectF rect3 = new RectF(250,50,250+100,50+100);
            canvas.drawRoundRect(rect3,20,20,paint);

            canvas.drawCircle(60,220,50,paint);

            paint.setStrokeWidth(5);

            Path path1 = new Path();
            path1.moveTo(10,290);
            path1.lineTo(10+50,290+50);
            path1.lineTo(10+100,290);
            path1.lineTo(10+150,290+50);
            path1.lineTo(10+200,290);
            canvas.drawPath(path1,paint);

            paint.setStrokeWidth(0);
            paint.setTextSize(30);
            canvas.drawText("안드로이드",10,390,paint);

             */

        }
    }




}









