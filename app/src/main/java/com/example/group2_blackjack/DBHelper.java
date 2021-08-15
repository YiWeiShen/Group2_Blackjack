package com.example.group2_blackjack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database

     */
    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table Userdetails(name Text primary key, contact Text, dob Text)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists Userdetails");
    }

//    create new player
    public Boolean insertuserdata(String username, String password){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues(); // keep things in pair
        // (Coloum, value)
        contentValue.put("username",username);
        contentValue.put("password", password);
        contentValue.put("balance", 1000); // initial balance 1000
        contentValue.put("score", 0);  // initial score 0
        long result = DB.insert("Userdetails",null,contentValue);

        return result != -1;

    }

    public Boolean updateuserdata(String name, Integer balance, Integer score ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues(); // keep things in pair
        contentValue.put("balance", balance);
        contentValue.put("score", score);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});

        if(cursor.getCount()>0){
            long result = DB.update("Userdetails",contentValue, "name=?", new String[]{name});
            return result != -1;
        }
        return false;
    }

    // do we need delete player?
    public Boolean deleteuserdata(String name){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});

        if(cursor.getCount()>0){
            long result = DB.delete("Userdetails", "name=?", new String[]{name});


            return result != -1;
        }
        return false;
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null) ;

        return cursor;

    }
}
