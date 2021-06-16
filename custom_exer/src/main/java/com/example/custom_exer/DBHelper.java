package com.example.custom_exer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
   public static final int VERSION=3;
   public DBHelper(Context context){
      super(context, "mydb2021", null, VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {

      String memoSQL = "create table mjcclass (" +
              "_id integer primary key autoincrement," +
              "subject text," +  // 과목
              "progress integer," + //진행율 : 0% ~ 100%
              "created datetime DEFAULT CURRENT_TIMESTAMP)";   //날짜
      db.execSQL(memoSQL);
      db.execSQL("insert into mjcclass(subject,progress,created)values('모바일프로그래밍2',80,'2021-03-20')");
      db.execSQL("insert into mjcclass(subject,progress,created)values('이동통신보안',30,'2021-02-25')");
      db.execSQL("insert into mjcclass (subject, progress) values ('해킹방어실습',70)");
      db.execSQL("insert into mjcclass (subject, progress) values ('보안운영및실습',40)");
      db.execSQL("insert into mjcclass (subject, progress) values ('캡스톤디자인',50)");

   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if(newVersion==VERSION){
            db.execSQL("drop table mjcclass");
            onCreate(db);
      }
   }
}
