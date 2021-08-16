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

        db.execSQL("create Table Userdetails(username Text primary key, password Text, balance Integer, score Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists Userdetails");
    }

    //    create new player
    public Boolean insertUserData(String username, String password) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues(); // keep things in pair
        // (Coloum, value)
        contentValue.put("username", username);
        contentValue.put("password", password);
        contentValue.put("balance", 1000); // initial balance 1000
        contentValue.put("score", 0);  // initial score 0
        long result = DB.insert("Userdetails", null, contentValue);

        return result != -1;

    }

    public Boolean updateUserData(String username,String password, Integer balance, Integer score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues(); // keep things in pair
        contentValue.put("password", password);
        contentValue.put("balance", balance);
        contentValue.put("score", score);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValue, "username=?", new String[]{username});
            return result != -1;
        }
        return false;
    }

    public Boolean loginCheck(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where username=? and password=?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void guestLogin() {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("username", "guest");
        contentValue.put("password", "guest");
        contentValue.put("balance", 1000); // initial balance 1000
        contentValue.put("score", 0);  // initial score 0
        Cursor cursor = DB.rawQuery("Select * from Userdetails where username=?", new String[]{"guest"});

        // check if guest already exists, if yes, delete it, before create a new guest account
        if (cursor.getCount() > 0) {
            DB.delete("Userdetails", "username=?", new String[]{"guest"});
        }
        DB.insert("Userdetails", null, contentValue);
    }

    public Users getUserByName(String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where username=?", new String[]{username});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Users user = new Users(username, cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            return  user;
        }
        return null;
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select username, balance, score from Userdetails order by score DESC", null) ;

        return cursor;

    }
}