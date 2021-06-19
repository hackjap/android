package com.example.thread_exam;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Main2Activity extends AppCompatActivity {

    //    ProgressBar pb1,pb2;
    Button btn;
    TextView textView;
    int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        pb1 = (ProgressBar)findViewById(R.id.progressBar);
//        pb2 = (ProgressBar)findViewById(R.id.progressBar2);
        btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mythread th1 = new Mythread();
                //tv1.setText("1번 진행률 ")
                th1.start();
            }
        });
    }


    class Mythread extends Thread {


        @Override
        public void run() {


            for(int i=0;i<100;i++) {
                SystemClock.sleep(1000);
                val++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("counter" + "\n" + val);
                    }
                });
            }
        }
    }

//    class MyThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            for(int i =pb1.getProgress();i<100; i+=2 ){
//                pb1.setProgress(pb1.getProgress()+2);
//                SystemClock.sleep(100);
//            }
//        }
//    }
}