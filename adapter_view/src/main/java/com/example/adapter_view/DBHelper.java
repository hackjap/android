package com.example.adapter_view;

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

      String memoSQL="create table expense (" +
            "_id integer primary key autoincrement," +
            "item text," +
            "amount integer," +
            "created datetime DEFAULT CURRENT_TIMESTAMP);";
      db.execSQL(memoSQL);

      db.execSQL("insert into expense (item, amount, created)values ('세뱃돈',50000, '2021-01-01')");
      db.execSQL("insert into expense (item, amount) values ('알바',100000)");
      db.execSQL("insert into expense (item, amount) values ('점심',-7000)");
      db.execSQL("insert into expense (item, amount) values ('용돈',30000)");
      db.execSQL("insert into expense (item, amount) values ('교통비',-2000)");
      db.execSQL("insert into expense (item, amount) values ('커피',-3000)");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if(newVersion==VERSION){
            db.execSQL("drop table expense");
            onCreate(db);
      }
   }
}
