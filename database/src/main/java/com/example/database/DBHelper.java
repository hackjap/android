package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 2;

  public DBHelper(@NonNull Context context){
      super(context,"mydb",null,DATABASE_VERSION);
  }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String testSQL="create table accountBook(" +
                "_id integer primary key autoincrement,"+
                "type text," +
                "title text," +
                "amount integer,"+
                "updated datetime default CURRENT_TIMESTAMP)";
        db.execSQL(testSQL);
        testSQL = "insert into accountBook(type,title,amount)values('수입','아르바이트',20000)";
        db.execSQL(testSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      if(newVersion == DATABASE_VERSION){
          db.execSQL("drop table accountBook");

          onCreate(db);
      }
    }
}


